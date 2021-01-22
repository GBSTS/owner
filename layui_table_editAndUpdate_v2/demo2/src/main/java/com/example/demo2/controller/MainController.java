package com.example.demo2.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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

/**
 * 对于插入和删除可能出现的新添加行问题
 * 可以通过后端提供所有数据时，数据包含该记录的唯一标识
 * 而在前端的表格中为该记录的唯一标识设置一个字段，但是默认不显示
 * 而在添加新的一行是个行对应的记录的唯一标识设置为null
 * 然后在执行更新或删除操作时，后端通过分析返回的记录的唯一标识是否为null来执行不同的操作
 * 记录的唯一标识通过自增实现
 * 更新：
 *      1.记录的唯一标识为null
 *          执行insert操作
 *      2.记录的唯一标识不为null
 *          执行update操作
 * 删除：
 *      1.记录的唯一标识为null
 *          忽略给记录，不执行sql操作
 *      2.记录的唯一标识不为null
 *          执行delete操作
 * 原理：layui table 允许数据为""
 *      JSON.toJavaObject()允许变量中的变量的值为null
 */


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
        listOfInfoData.add(new InfoData(1,"a2","a3"));
        listOfInfoData.add(new InfoData(2,"b2","b3"));
        listOfInfoData.add(new InfoData(3,"c2","c3"));
        infoResponse.setListOfInfoData(listOfInfoData);
        return JSON.toJSONString(infoResponse);
    }

    @PostMapping("/deletedata")
    @ResponseBody
    public String deletedata(@RequestParam("strObj") String strObj){
        JSONArray detail = JSON.parseArray(strObj);
        for (int i=0; i<detail.size();i++){
            if(detail.get(i)!=null||!detail.get(i).equals("")){
                JSONObject object = detail.getJSONObject(i);
                ADto aDto = JSON.toJavaObject(object, ADto.class);
                System.out.println(aDto.toString());
            }
        }
        return "OK";
    }

    @PostMapping("/updatedata")
    @ResponseBody
    public String updatedata(@RequestParam("strObj") String strObj){
        JSONArray detail = JSON.parseArray(strObj);
        for (int i=0; i<detail.size();i++){
            if(detail.get(i)!=null||!detail.get(i).equals("")){
                JSONObject object = detail.getJSONObject(i);
                ADto aDto = null;
                try{
                    aDto = JSON.toJavaObject(object, ADto.class);
                }catch (Exception exception){
                    return "输入格式异常";
                }
                System.out.println(aDto.toString());
            }
        }
        return "OK";
    }
}
