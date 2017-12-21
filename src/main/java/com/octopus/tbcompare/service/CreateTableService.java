package com.octopus.tbcompare.service;

import com.octopus.tbcompare.vo.ColumnVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2017-12-20
 * Time: 16:08
 */
public class CreateTableService {
    @Autowired
    private CopyService copyService;
    public void createTable(Integer id,List<ColumnVo> list){

    }
}
