package com.octopus.tbcompare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2017-12-20
 * Time: 10:21
 */
@Controller
public class IndexController {
    @RequestMapping("/")
    public String index(){
        return "index";
    }

}
