package com.example.yzm.controller;

import com.example.yzm.util.RSA;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MainController {

    @RequestMapping("/")
    public String test(){
        return "yzm";
    }

    @PostMapping("/submit")
    public void verifyCode(HttpServletRequest request, HttpServletResponse response, String password) {
        try {
            System.out.println("password-sha256:\n"+ password);
            System.out.println("password:\n"+ RSA.decrypt(password,RSA.PRIVATEKEY));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
