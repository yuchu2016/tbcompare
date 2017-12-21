package com.octopus.tbcompare.service;

import com.octopus.tbcompare.TbcompareApplication;
import com.octopus.tbcompare.enums.DatabaseEnum;
import com.octopus.tbcompare.pojo.Databases;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@SpringBootTest(classes = TbcompareApplication.class)
@RunWith(SpringRunner.class)
public class DatabaseServiceTest {

    private static final Logger log = LoggerFactory.getLogger(DatabaseServiceTest.class);
    @Autowired
    private DatabaseService databaseService;
    @Test
    public void getAll() {
        databaseService.getAll();
    }

    @Test
    public void add() {
        Databases database = new Databases();
        database.setName("mysql");
        database.setDriver(DatabaseEnum.MYSQL.getClassName());
        database.setUrl("jdbc:mysql:///test");
        database.setUsername("root");
        database.setPassword("1234");
        databaseService.add(database);
    }
}