package com.example.demo.controller;
 
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
/**
* @desc: 核心业务模块
* @author: CSH
**/
@RestController
@RequestMapping("/aopController")
public class AopController {
 
    @RequestMapping(value = "/Curry")
    public void Curry(){
        System.out.println("库里上场打球了！！");
    }
 
    @RequestMapping(value = "/Harden")
    public void Harden(){
        System.out.println("哈登上场打球了！！");
    }
    
    @RequestMapping(value = "/Antetokounmpo")
    public void Antetokounmpo(){
        System.out.println("字母哥上场打球了！！");
    }
 
    @RequestMapping(value = "/Jokic")
    public void Jokic(){
        System.out.println("约基奇上场打球了！！");
    }
 
    @RequestMapping(value = "/Durant")
    public void Durant(){
        System.out.println("杜兰特上场打球了！！");
    }

    @RequestMapping(value = "/Durant3/{point}/{param}")
    public void Durant3(@PathVariable("point")  int point, @PathVariable("param") String param){
        System.out.println("杜兰特上场打球了！！");
    }
}