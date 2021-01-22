package com.example.swagger2.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

//@ApiIgnore//在swagger2文档中不显示该接口类
@Api(tags = "测试模块")
@Controller
public class TestController {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public String common_test(){
        return "/test";
    }

    @RequestMapping(value = "/approved/test", method = RequestMethod.GET)
    @ResponseBody
    public String approved_test(){
        return "/approved/test";
    }

    @RequestMapping(value = "/company/test", method = RequestMethod.GET)
    @ResponseBody
    public String company_test(){
        return "/company/test";
    }

    @RequestMapping(value = "/implemeter/test", method = RequestMethod.GET)
    @ResponseBody
    public String implemeter_test(){
        return "/implemeter/test";
    }
}
