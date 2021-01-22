package com.example.demo2.json;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

@Data
public class InfoResponse {
    @JSONField(name = "code")
    private int code=0;
    @JSONField(name = "msg")
    private String msg="";
    @JSONField(name = "data")
    private List<InfoData> listOfInfoData;

}
