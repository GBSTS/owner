package com.example.itmasys.controller;

import com.example.itmasys.security.facade.IAuthenticationFacade;
import com.example.itmasys.service.PersoninfoService;
import com.example.itmasys.service.RegisterService;
import com.example.itmasys.service.RoleService;
import com.example.itmasys.service.email.SendJunkMailService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class TestController {

    @Resource
    private IAuthenticationFacade authenticationFacade;
    @Resource
    private RoleService roleService;
    @Resource
    private PersoninfoService personinfoService;
    @Resource
    private RegisterService registerService;
    @Resource
    private SendJunkMailService sendJunkMailService;

    @RequestMapping("/test")
    @ResponseBody
    public String test(){
        Authentication authentication = authenticationFacade.getAuthentication();//获取当前登录用户信息
        return authentication.toString();
    }
}
