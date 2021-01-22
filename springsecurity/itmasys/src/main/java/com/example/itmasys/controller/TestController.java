package com.example.itmasys.controller;

import com.example.itmasys.service.RolesService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class TestController {

    @RequestMapping("/test")
    public String common_test(){
        return "/test";
    }

    @RequestMapping("/approved/test")
    public String approved_test(){
        return "/approved/test";
    }

    @RequestMapping("/company/test")
    public String company_test(){
        return "/company/test";
    }

    @RequestMapping("/implemeter/test")
    public String implemeter_test(){
        return "/implemeter/test";
    }
}
