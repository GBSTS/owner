package com.example.swagger2.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Api(tags = "登录模块")
@Controller
public class LoginController {

    @ApiOperation(value = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request", value = "HttpServletRequest"),
            @ApiImplicitParam(name = "response", value = "HttpServletResponse"),
            @ApiImplicitParam(name = "model", value = "Model")
    })
    @RequestMapping(value = {"/","/login"}, method = {RequestMethod.GET})
    @ResponseBody
    public String login(HttpServletRequest request, HttpServletResponse response, Model model){
        return "/login";
    }

}
