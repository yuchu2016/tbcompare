package com.octopus.tbcompare.service;

import com.octopus.tbcompare.enums.DatabaseEnum;
import com.octopus.tbcompare.enums.DbIdEnum;
import com.octopus.tbcompare.pojo.Databases;
import com.octopus.tbcompare.pojo.Type;
import com.octopus.tbcompare.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2017-12-20
 * Time: 16:57
 */
@Service
public class TypeService {
    @Autowired
    private TypeRepository typeRepository;
    @Autowired
    private DatabaseService databaseService;

    public List<Type> getByDb(Integer id){
        Databases databases = databaseService.getById(id);
        if (databases.getDriver().equals(DatabaseEnum.MYSQL.getClassName())){
            return typeRepository.findByDb(DbIdEnum.MYSQL.getId());
        }
        if (databases.getDriver().equals(DatabaseEnum.SQL_SERVER.getClassName())){
            return typeRepository.findByDb(DbIdEnum.SQLSERVER.getId());
        }
        if (databases.getDriver().equals(DatabaseEnum.POSTGRESQL.getClassName())){
            return typeRepository.findByDb(DbIdEnum.POSTGRESQL.getId());
        }
        return null;
    }
}
