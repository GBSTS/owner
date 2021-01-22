package com.hsl.springbootjdbctemplate.controller;

import com.hsl.springbootjdbctemplate.entity.Aymood;
import com.hsl.springbootjdbctemplate.service.AymoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {
    @Autowired
    AymoodService aymoodService;

    //查询单个值
    @RequestMapping("/jdbcTemplate_oneObj")
    public String test(){
        return aymoodService.findAymoodById(5).toString();
    }
    //查询一个对象
    @RequestMapping("/jdbcTemplate_oneData")
    public String test1(){
        return aymoodService.findIdByContent("1");
    }
    //查询多个对象
    @RequestMapping("/jdbcTemplate_mulObj")
    public String test2(){
        List<Aymood> aymoodList = aymoodService.findAymoodByConditions(5);
        String str="";
        for (int i = 0; i < aymoodList.size(); i++) {
            Aymood s = (Aymood)aymoodList.get(i);
            str+=s.toString()+"<br />";
    }
        return str;
    }
    //添加一个对象
    @RequestMapping("/jdbcTemplate_insertObj")
    public String test3(){
        return aymoodService.insertAymood(new Aymood(9,"8"))+"";
    }
    //修改一个对象
    @RequestMapping("/jdbcTemplate_updateObj")
    public String test4(){
        return aymoodService.updateAymoodById(new Aymood(6,"11"))+"";
    }
    //删除一个对象
    @RequestMapping("/jdbcTemplate_deleteObj")
    public String test5(){
        return aymoodService.deleteAymoodById(7)+"";
    }
}
