package com.octopus.tbcompare.service;

import com.octopus.tbcompare.TbcompareApplication;
import com.octopus.tbcompare.repository.MapperRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest(classes = TbcompareApplication.class)
@RunWith(SpringRunner.class)
public class MapperServiceTest {

    @Autowired
    private MapperService mapperService;

    @Autowired
    private MapperRepository mapperRepository;
    @Test
    public void getTypeName() {
        //System.out.println(mapperService.getTypeName(1,5,"varchar"));
        System.out.println(mapperRepository.findByMsType("varchar"));
    }
}