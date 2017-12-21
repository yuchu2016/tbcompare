package com.octopus.tbcompare.service;

import com.octopus.tbcompare.TbcompareApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest(classes = TbcompareApplication.class)
@RunWith(SpringRunner.class)
public class TypeServiceTest {
    @Autowired
    private TypeService typeService;
    @Test
    public void getByDb() {
        typeService.getByDb(1);
    }
}