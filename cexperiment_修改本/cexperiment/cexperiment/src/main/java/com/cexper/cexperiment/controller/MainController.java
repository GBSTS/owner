package com.cexper.cexperiment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class MainController {

    //访问并打开cexperiment.html页面
    @RequestMapping("/cexper")
    public String ceperiment(){
        return "cexperiment";
    }

    //访问并打开cexperiment.html页面
    @RequestMapping("/cexper1")
    public String ceperiment1(){
        return "cexperiment1";
    }

    //访问并打开cexperimentops.html页面
    @RequestMapping("/cexperops")
    public String cexperimentops(){
        return "cexperimentops";
    }


}
