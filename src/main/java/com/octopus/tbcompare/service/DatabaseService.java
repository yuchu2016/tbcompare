package com.octopus.tbcompare.service;

import com.octopus.tbcompare.pojo.Databases;
import com.octopus.tbcompare.repository.DatabaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2017-12-20
 * Time: 9:46
 */
@Service
public class DatabaseService {
    @Autowired
    private DatabaseRepository databaseRepository;

    public List<Databases> getAll(){
        return databaseRepository.findAll();
    }

    public Databases getById(Integer id){
        return databaseRepository.findOne(id);
    }
    public void add(Databases database){
        databaseRepository.save(database);
    }

    public void update(Databases databases){
        databaseRepository.save(databases);
    }

    public void delete(Integer id){
        databaseRepository.delete(id);
    }
}
