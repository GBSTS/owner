package com.example.itmasys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ForgetPasswordController {

    @RequestMapping("/forgetpassword")
    public String forgetpassword(){
        return "/forgetpassword";
    }

}