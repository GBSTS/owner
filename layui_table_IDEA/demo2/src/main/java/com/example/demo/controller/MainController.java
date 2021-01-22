package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.json.InfoData;
import com.example.demo.json.InfoResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    @RequestMapping("/")
    public String test(){
        return "test1";
    }
    @RequestMapping(value = "/getData", method = RequestMethod.POST)
    @ResponseBody
    public String getData(@RequestParam("field") String field, @RequestParam("order") String order){
        InfoResponse infoResponse = new InfoResponse();
        infoResponse.setCode(0);
        infoResponse.setMsg("");
        List<InfoData> listOfInfoData = new ArrayList<InfoData>();
        //模拟排序
        if(order.equals("desc")){
            listOfInfoData.add(new InfoData("/img/2034043.jpg","1","desc","c",
                    "e","500g","2001-12-11","2002-01-10","sssssssssssssss"));
        }else if(order.equals("asc")){
            listOfInfoData.add(new InfoData("/img/2034043.jpg","1","asc","c",
                    "e","500g","2001-12-11","2002-01-10","sssssssssssssss"));
        }else if(order.equals("")){
            listOfInfoData.add(new InfoData("/img/2034043.jpg","1","null","c",
                    "e","500g","2001-12-11","2002-01-10","sssssssssssssss"));
        }
        listOfInfoData.add(new InfoData("/img/2034043.jpg","1","dsdfsadfas","c",
                "e","500g","2001-12-11","2002-01-10","sssssssssssssss"));
        listOfInfoData.add(new InfoData("/img/2034171.jpg","2","d","c",
                "e","500g","2001-12-11","2002-01-10","sss"));
        listOfInfoData.add(new InfoData("/img/2034043.jpg","3","d","c",
                "e","500g","2001-12-11","2002-01-10","sss"));
        listOfInfoData.add(new InfoData("/img/2034171.jpg","4","d","c",
                "e","500g","2001-12-11","2002-01-10","sss"));
        listOfInfoData.add(new InfoData("/img/2034043.jpg","5","d","c",
                "e","500g","2001-12-11","2002-01-10","sss"));
        listOfInfoData.add(new InfoData("/img/2034171.jpg","6","d","c",
                "e","500g","2001-12-11","2002-01-10","sss"));
        listOfInfoData.add(new InfoData("/img/2034043.jpg","7","d","c",
                "e","500g","2001-12-11","2002-01-10","sss"));
        listOfInfoData.add(new InfoData("/img/2034171.jpg","8","d","c",
                "e","500g","2001-12-11","2002-01-10","sss"));
        listOfInfoData.add(new InfoData("/img/2034043.jpg","9","d","c",
                "e","500g","2001-12-11","2002-01-10","sss"));
        listOfInfoData.add(new InfoData("/img/2034171.jpg","10","d","c",
                "e","500g","2001-12-11","2002-01-10","sss"));
        listOfInfoData.add(new InfoData("/img/2034043.jpg","11","d","c",
                "e","500g","2001-12-11","2002-01-10","sss"));
        listOfInfoData.add(new InfoData("/img/2034171.jpg","12","d","c",
                "e","500g","2001-12-11","2002-01-10","sss"));
        listOfInfoData.add(new InfoData("/img/2034043.jpg","13","d","c",
                "e","500g","2001-12-11","2002-01-10","sss"));
        listOfInfoData.add(new InfoData("/img/2034171.jpg","14","d","c",
                "e","500g","2001-12-11","2002-01-10","sss"));
        listOfInfoData.add(new InfoData("/img/2034043.jpg","15","d","c",
                "e","500g","2001-12-11","2002-01-10","sss"));
        listOfInfoData.add(new InfoData("/img/2034171.jpg","16","d","c",
                "e","500g","2001-12-11","2002-01-10","sss"));
        infoResponse.setListOfInfoData(listOfInfoData);
        return JSON.toJSONString(infoResponse);
    }
}
