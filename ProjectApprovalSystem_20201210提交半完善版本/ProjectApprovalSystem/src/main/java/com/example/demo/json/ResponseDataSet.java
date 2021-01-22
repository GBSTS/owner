package com.example.demo.json;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

@Data
public class ResponseDataSet<T> {
    @JSONField(name = "code")
    private int code=0;
    @JSONField(name = "msg")
    private String msg="";
    @JSONField(name = "count")
    private int count;
    @JSONField(name = "data")
    private List<T> listOfInfoData;

}
