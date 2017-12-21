package com.octopus.tbcompare.controller;

import com.octopus.tbcompare.common.ServerResponse;
import com.octopus.tbcompare.service.CopyService;
import com.octopus.tbcompare.service.DatabaseService;
import com.octopus.tbcompare.vo.ColumnCompareVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2017-12-21
 * Time: 9:37
 */
@Controller
@RequestMapping("/database")
public class CompareController {

    private static final Logger log = LoggerFactory.getLogger(CompareController.class);
    @Autowired
    private DatabaseService databaseService;

    @Autowired
    private CopyService copyService;

    @RequestMapping("/compare")
    public String compare(Model model){
        model.addAttribute("databaseList",databaseService.getAll());
        return "compare/index";
    }

    @PostMapping("/getCompareList")
    @ResponseBody
    public ServerResponse getCompare(Integer id,Integer toId,String fromTableName,String toTableName){
        String errMsg;
        try {
            ColumnCompareVo compareVo = copyService.getCompareColumnList(id,toId,fromTableName, toTableName);
            return ServerResponse.createBySuccess(compareVo);
        }catch (Exception e){
            errMsg = e.getMessage();
            log.error("表结构比较失败，原因为：",e.getMessage());
        }
        return ServerResponse.createByErrorMessage(errMsg);
    }
}
