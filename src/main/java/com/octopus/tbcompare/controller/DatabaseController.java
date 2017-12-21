package com.octopus.tbcompare.controller;

import com.octopus.tbcompare.common.ServerResponse;
import com.octopus.tbcompare.enums.DatabaseEnum;
import com.octopus.tbcompare.pojo.Databases;
import com.octopus.tbcompare.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2017-12-20
 * Time: 9:48
 */
@Controller
@RequestMapping("/database")
public class DatabaseController {

    @Autowired
    private DatabaseService databaseService;
    @RequestMapping("/all")
    public String index(Model model){
        List<Databases> databasesList = databaseService.getAll();
        model.addAttribute("databaseList",databasesList);
        return "database/all";
    }
    @RequestMapping("/addView")
    public String addView(Model model){
        model.addAttribute("mysql",DatabaseEnum.MYSQL);
        model.addAttribute("sqlserver",DatabaseEnum.SQL_SERVER);
        model.addAttribute("postgrepsql",DatabaseEnum.POSTGRESQL);
        return "database/addView";
    }
    @PostMapping("/add")
    public String add(Databases databases){
        databaseService.add(databases);
        return "redirect:/database/all";
    }

    @RequestMapping("/editView")
    public String editView(Integer id,Model model){
        Databases databases = databaseService.getById(id);
        model.addAttribute("database",databases);
        model.addAttribute("mysql",DatabaseEnum.MYSQL);
        model.addAttribute("sqlserver",DatabaseEnum.SQL_SERVER);
        model.addAttribute("postgrepsql",DatabaseEnum.POSTGRESQL);
        return "/database/edit";
    }

    @PostMapping("/edit")
    public String edit(Databases databases){
        databaseService.update(databases);
        return "redirect:/database/all";
    }

    @RequestMapping("/delete")
    public String delete(Integer id){
        databaseService.delete(id);
        return "redirect:/database/all";
    }

}
