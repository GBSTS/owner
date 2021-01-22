package com.example.demo2.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo2.dto.ADto;
import com.example.demo2.dto.ListDto;
import com.example.demo2.json.InfoData;
import com.example.demo2.json.InfoResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @RequestMapping("/")
    public String layui_table(){
        return "layui_table";
    }

    @PostMapping("/getdata")
    @ResponseBody
    public String getdata(){
        InfoResponse infoResponse = new InfoResponse();
        List<InfoData> listOfInfoData = new ArrayList<>();
        listOfInfoData.add(new InfoData("a1","a2","a3"));
        listOfInfoData.add(new InfoData("b1","b2","b3"));
        listOfInfoData.add(new InfoData("c1","c2","c3"));
        infoResponse.setListOfInfoData(listOfInfoData);
        return JSON.toJSONString(infoResponse);
    }

    @PostMapping("/updatedata")
    @ResponseBody
    public String updatedata(ListDto data){
        System.out.println("------------------------>"+data.toString());
//        List<ADto> aDtoList = (ArrayList)data.getAdtos();
//        for(int i=0;i<aDtoList.size();i++){
//            ADto aDto = aDtoList.get(i);
//            System.out.println("------------------>");
//            System.out.println(aDto.toString());
//        }
        return "OK";
    }

    @PostMapping("/deletedata")
    @ResponseBody
    public String deletedata(ListDto data){
        System.out.println("------------------------>"+data.toString());
//        List<ADto> aDtoList = (ArrayList)data.getAdtos();
//        for(int i=0;i<aDtoList.size();i++){
//            ADto aDto = aDtoList.get(i);
//            System.out.println("------------------>");
//            System.out.println(aDto.toString());
//        }
        return "OK";
    }
}
