package com.octopus.tbcompare.controller;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.octopus.tbcompare.common.ServerResponse;
import com.octopus.tbcompare.enums.DatabaseEnum;
import com.octopus.tbcompare.pojo.Databases;
import com.octopus.tbcompare.pojo.TransType;
import com.octopus.tbcompare.pojo.Type;
import com.octopus.tbcompare.service.*;
import com.octopus.tbcompare.util.DBTypeUtil;
import com.octopus.tbcompare.vo.ColumnVo;
import com.octopus.tbcompare.vo.TableVo;
import org.apache.catalina.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2017-12-20
 * Time: 12:31
 */
@Controller
@RequestMapping("/database")
public class CopyController {
    private static final Logger log = LoggerFactory.getLogger(CopyController.class);

    @Autowired
    private MapperService mapperService;
    @Autowired
    private CopyService copyService;
    @Autowired
    private DatabaseService databaseService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TransTypeService transTypeService;
    @RequestMapping("/copy")
    public String index(Model model){
        model.addAttribute("databaseList",databaseService.getAll());
        return "copy/index";
    }
    @PostMapping("/getTableList")
    @ResponseBody
    public ServerResponse getTableList(Integer id){
        try {
            List<TableVo> tableVoList = copyService.getTableList(id);
            return ServerResponse.createBySuccess(tableVoList);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return ServerResponse.createByErrorMessage("获取表列表失败");
    }

    @PostMapping("/getColumnList")
    @ResponseBody
    public ServerResponse getColumnList(Integer id,String tableName,Integer toId){
        try {
            List<ColumnVo> columnVoList = copyService.getColumnList(id,tableName);
            for (ColumnVo columnVo:columnVoList) {
                Databases fromDb = databaseService.getById(id);
                Databases toDb = databaseService.getById(toId);

                int from = DBTypeUtil.getDbType(fromDb);
                int to = DBTypeUtil.getDbType(toDb);

                TransType transType = transTypeService.findType(from,to,columnVo.getType().toUpperCase());
                //columnVo.setType(mapperService.getTypeName(id,toId,columnVo.getType()));
                //todo
                if (null!=transType){
                    columnVo.setType(transType.getToType());
                    if (!transType.getLegalSize()){
                        columnVo.setSize("");
                    }
                }
            }
            return ServerResponse.createBySuccess(columnVoList);
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return ServerResponse.createByErrorMessage("获取字段列表失败");
    }

    @PostMapping("/getColumn")
    @ResponseBody
    public ServerResponse getColumn(Integer toId){

        //To-do
//        List<Type> typeList = new ArrayList<>();
//        typeList.add(new Type(1,1,"varchar"));
//        typeList.add(new Type(2,1,"char"));
//        typeList.add(new Type(3,1,"int"));
//        typeList.add(new Type(4,1,"text"));
        List<Type> typeList = typeService.getByDb(toId);
        return ServerResponse.createBySuccess(typeList);
    }

    @PostMapping("/createTable")
    @ResponseBody
    public ServerResponse createTable(Integer toId,String tableName,String data){
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonArray jsonArray = parser.parse(data).getAsJsonArray();
        ArrayList<ColumnVo> columnVoArrayList = new ArrayList<>();
        String errMsg;
        //循环遍历
        for (JsonElement column : jsonArray) {
            ColumnVo columnVo = gson.fromJson(column, new TypeToken<ColumnVo>() {}.getType());
            columnVoArrayList.add(columnVo);
        }

        log.info(columnVoArrayList.toString());
        //TODO
        try {
            copyService.createTable(toId, columnVoArrayList, tableName);
            return ServerResponse.createBySuccessMessage("表创建成功");
        }catch (Exception e){
            log.error("创建sql失败,{}",e.getMessage());
            errMsg = e.getMessage();
        }
        return ServerResponse.createByErrorMessage("表创建失败,原因为："+errMsg);
    }
}
