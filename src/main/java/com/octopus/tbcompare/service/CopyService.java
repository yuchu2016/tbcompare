package com.octopus.tbcompare.service;

import com.google.common.collect.Lists;
import com.octopus.tbcompare.pojo.Databases;
import com.octopus.tbcompare.repository.DatabaseRepository;
import com.octopus.tbcompare.util.ListUtil;
import com.octopus.tbcompare.vo.ColumnCompareVo;
import com.octopus.tbcompare.vo.ColumnVo;
import com.microsoft.sqlserver.jdbc.SQLServerConnection;
import com.octopus.tbcompare.vo.TableVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2017-12-20
 * Time: 12:42
 */

@Service
public class CopyService {

    private static final Logger log = LoggerFactory.getLogger(CopyService.class);
    @Autowired
    private DatabaseRepository databaseRepository;

    public List<ColumnVo> getColumnList(Integer id,String tableName) throws Exception{
        Databases databases = databaseRepository.findOne(id);
        return getTableStructure(databases,tableName);
    }

    public List<TableVo> getTableList(Integer id)throws Exception{
        Databases databases = databaseRepository.findOne(id);
        return getDBStructure(databases);
    }
    public void createTable(Integer id,List<ColumnVo> list,String tableName) throws Exception{
        Databases databases = databaseRepository.findOne(id);
        Connection connection = getConnection(databases);
        PreparedStatement ps;
        StringBuffer sb=new StringBuffer();
        sb.append("create table ");
        sb.append(tableName);
        sb.append(" (");
        for (int i=0;i<list.size();i++) {
            sb.append(list.get(i).getName()+" ");
            sb.append(list.get(i).getType());
            sb.append(StringUtils.isNotBlank(list.get(i).getSize())?"("+ list.get(i).getSize()+")":" " );
            sb.append(list.get(i).getIsPrimary()?"primary key":"");
            if (i<list.size()-1) {
                sb.append((list.get(i).getIsPrimary() || list.get(i).getIsNull()) ? " , " : "not null,");
            }
            else{
                sb.append((list.get(i).getIsPrimary() || list.get(i).getIsNull()) ? " " : "not null");
            }

        }
        sb.append(")");
        String sql = sb.toString();
        log.info("sql语句为：{}",sql);

        ps = connection.prepareStatement(sql);
        ps.execute();
        ps.close();
        connection.close();
    }

    public ColumnCompareVo getCompareColumnList(Integer basicDBId,Integer targetDBId,String basicTBName,String targetTBName) throws Exception{
        ColumnCompareVo compareVo = new ColumnCompareVo();
        List<ColumnVo> fromColumnList = getColumnList(basicDBId,basicTBName);
        List<ColumnVo> toColumnList = getColumnList(targetDBId,targetTBName);
        List<String> columnNameList = compareWithName(basicDBId,targetDBId,basicTBName,targetTBName);
        for (String s : columnNameList) {
            for (ColumnVo columnVo : fromColumnList) {
                if (columnVo.getName().toLowerCase().equals(s)){
                    compareVo.getFromList().add(columnVo);
                }
            }
            for (ColumnVo columnVo : toColumnList) {
                if (columnVo.getName().toLowerCase().equals(s)){
                    compareVo.getToList().add(columnVo);
                }
            }
        }
        return compareVo;
    }


    private Connection getConnection(Databases database) {
        Connection connection =null;
        Properties props = new Properties();
        try {
            Class.forName(database.getDriver());
            props.setProperty("user", database.getUsername());
            props.setProperty("password", database.getPassword());
            props.setProperty("remarks", "true"); //设置可以获取remarks信息
            props.setProperty("useInformationSchema", "true");//设置可以获取tables remarks信息
            connection= DriverManager.getConnection(database.getUrl(),props);
            return connection;
        } catch (Exception ex) {
            System.out.println("2:"+ex.getMessage());
        }
        return connection;
    }

    private List<TableVo> getDBStructure(Databases database)throws Exception{
        List<TableVo> tableVoList = Lists.newArrayList();
        Connection connection= getConnection(database);
        String catalog = connection.getCatalog(); //catalog 其实也就是数据库名
        DatabaseMetaData dbMetaData = connection.getMetaData();
        ResultSet tablesResultSet = dbMetaData.getTables(catalog,null,null,new String[]{"TABLE"});
        while(tablesResultSet.next()){
            TableVo table = new TableVo();
            table.setName(tablesResultSet.getString("TABLE_NAME"));
            tableVoList.add(table);
        }
        return tableVoList;
    }

    private List<ColumnVo> getTableStructure(Databases database, String tableName)throws Exception{
        Connection connection= getConnection(database);
        String catalog = connection.getCatalog(); //catalog 其实也就是数据库名
        DatabaseMetaData dbMetaData = connection.getMetaData();

        ResultSet columnResultSet = dbMetaData.getColumns(catalog,null,tableName,null);

        ResultSet primaryKeyResultSet = dbMetaData.getPrimaryKeys(catalog,null,tableName);
        List<String> pkNameList=new ArrayList<>();
        while(primaryKeyResultSet.next()){
            pkNameList.add(primaryKeyResultSet.getString("COLUMN_NAME"));
        }
        List<ColumnVo> columnList = new ArrayList<>();
        while (columnResultSet.next()){
            ColumnVo column = new ColumnVo();
            column.setName(columnResultSet.getString("COLUMN_NAME"));
            column.setType(columnResultSet.getString("TYPE_NAME"));
            String remarks = columnResultSet.getString("REMARKS");
            column.setRemarks(remarks);
            if (null==remarks){
                column.setRemarks("");
            }
            column.setSize(columnResultSet.getString("COLUMN_SIZE"));
            column.setIsNull(columnResultSet.getBoolean("NULLABLE"));
            column.setIsPrimary(pkNameList.contains(column.getName()));
            columnList.add(column);
        }
        if (connection instanceof SQLServerConnection ){
           //todo
            columnList = getSQLServerRemarks(connection,columnList,tableName);
        }
        return columnList;
    }

    /**
     * sql:SELECT  B.name AS column_name, C.value AS column_description
     FROM sys.tables A
     INNER JOIN sys.columns B ON B.object_id = A.object_id
     LEFT JOIN sys.extended_properties C ON C.major_id = B.object_id AND C.minor_id = B.column_id
     WHERE A.name = 'captured_columns'
     * @param connection
     * @param columnVoList
     * @param tableName
     * @return
     */
    private List<ColumnVo> getSQLServerRemarks(Connection connection,List<ColumnVo> columnVoList,String tableName)throws Exception{

        PreparedStatement ps=null;
        ResultSet rs = null;
        try {
            String sql = "SELECT  B.name AS column_name,CONVERT(varchar(200), C.value)  AS column_descriptio FROM sys.tables A  INNER JOIN sys.columns B ON B.object_id = A.object_id LEFT JOIN sys.extended_properties C ON C.major_id = B.object_id AND C.minor_id = B.column_id WHERE A.name = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1,tableName);
            rs = ps.executeQuery();
            while (rs.next()){
                for (ColumnVo columnVo : columnVoList) {
                    if (columnVo.getName().equals(rs.getString(1))){
                        columnVo.setRemarks(rs.getString(2));
                    }else {
                        columnVo.setRemarks("");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            rs.close();
            ps.close();
            connection.close();
        }

        return columnVoList;
    }
    private List<String> compareWithName(Integer basicDBId,Integer targetDBId,String basicTBName,String targetTBName )throws Exception{
        Databases basicDB = databaseRepository.findOne(basicDBId);
        Databases targetDB = databaseRepository.findOne(targetDBId);
        List<String> basicDBColumnNames = new ArrayList<>();
        getTableStructure(basicDB,basicTBName).forEach(tb->basicDBColumnNames.add(tb.getName()));
        List<String> targetDBColumnNames = new ArrayList<>();
        getTableStructure(targetDB,targetTBName).forEach(tb->targetDBColumnNames.add(tb.getName()));
        List<String> diffColumnsName = new ListUtil<String>().getDiff(basicDBColumnNames,targetDBColumnNames);
        diffColumnsName.forEach(s -> s.toLowerCase());
        diffColumnsName = diffColumnsName.stream().map(s -> s.toLowerCase()).collect(Collectors.toList());
        return diffColumnsName;
    }

}
