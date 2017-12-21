package com.octopus.tbcompare.service;

import com.octopus.tbcompare.enums.DatabaseEnum;
import com.octopus.tbcompare.pojo.Databases;
import com.octopus.tbcompare.pojo.Mapper;
import com.octopus.tbcompare.repository.DatabaseRepository;
import com.octopus.tbcompare.repository.MapperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2017-12-20
 * Time: 15:21
 */
@Service
public class MapperService {

    @Autowired
    private MapperRepository mapperRepository;
    @Autowired
    private DatabaseRepository databaseRepository;

    public String getTypeName(Integer fromId,Integer toId,String typeName){
        try {
            typeName = typeName.toUpperCase();
            Mapper mapper = getByType(fromId, typeName);
            String targetType = getType(toId, mapper);
            return targetType.toUpperCase();
        }catch (Exception e){
            return null;
        }
    }

    private Mapper getByType(Integer fromId,String typeName){
        Databases databases = databaseRepository.findOne(fromId);
        if (databases.getDriver().equals(DatabaseEnum.MYSQL.getClassName())){
            return mapperRepository.findByMsType(typeName);
        }
        if (databases.getDriver().equals(DatabaseEnum.SQL_SERVER.getClassName())){
            return mapperRepository.findBySsType(typeName);
        }
        if (databases.getDriver().equals(DatabaseEnum.POSTGRESQL.getClassName())){
            return mapperRepository.findByPgType(typeName);
        }
        return null;
    }

    private String getType(Integer toId,Mapper mapper){
        Databases databases = databaseRepository.findOne(toId);
        if (databases.getDriver().equals(DatabaseEnum.MYSQL.getClassName())){
            return mapper.getMsType();
        }
        if (databases.getDriver().equals(DatabaseEnum.SQL_SERVER.getClassName())){
            return mapper.getSsType();
        }
        if (databases.getDriver().equals(DatabaseEnum.POSTGRESQL.getClassName())){
            return mapper.getPgType();
        }
        return null;
    }
}
