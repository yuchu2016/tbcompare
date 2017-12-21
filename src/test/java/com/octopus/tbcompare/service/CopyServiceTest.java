package com.octopus.tbcompare.service;

import com.octopus.tbcompare.TbcompareApplication;
import com.octopus.tbcompare.enums.DatabaseEnum;
import com.octopus.tbcompare.pojo.Databases;
import com.octopus.tbcompare.vo.ColumnCompareVo;
import com.octopus.tbcompare.vo.ColumnVo;
import com.octopus.tbcompare.vo.TableVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = TbcompareApplication.class)
@RunWith(SpringRunner.class)
public class CopyServiceTest {

    private static final Logger log = LoggerFactory.getLogger(CopyServiceTest.class);
    @Autowired
    private CopyService copyService;

    @Test
    public void testGetColumn() throws Exception{
        List<ColumnVo> columnVoList = copyService.getColumnList(1,"tb_test");
        for (ColumnVo columnVo : columnVoList) {
            System.out.println(columnVo);
        }
    }
    @Test
    public void testGetTable() throws Exception{
        List<TableVo> tableVoList = copyService.getTableList(1);
        for (TableVo tableVo : tableVoList) {
            System.out.println(tableVo);
        }
    }

//    @Test
//    public void testCreatePgTable() throws Exception{
//        List<ColumnVo> list = new ArrayList<>();
//        list.add(new ColumnVo("id","int4","",false,true));
//        list.add(new ColumnVo("name","varchar","255",true,false));
//        list.add(new ColumnVo("age","int4","",false,false));
//        copyService.createTable(6,list,"test");
//    }
//    @Test
//    public void testCreateSsTable() throws Exception{
//        List<ColumnVo> list = new ArrayList<>();
//        list.add(new ColumnVo("id","int","",false,true));
//        list.add(new ColumnVo("name","nvarchar","255",true,false));
//        list.add(new ColumnVo("age","int","",false,false));
//        copyService.createTable(5,list,"test");
//    }

    @Test
    public void compareWithNameTest() throws Exception{
        ColumnCompareVo columnCompareVo= copyService.getCompareColumnList(1,5,"t_qunar_des","T_Qunar_Line");
        log.info(columnCompareVo.getFromList().toString());
        log.info(columnCompareVo.getToList().toString());
    }

}