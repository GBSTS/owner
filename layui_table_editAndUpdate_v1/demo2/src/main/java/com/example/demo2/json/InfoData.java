package com.example.demo2.json;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class InfoData{
    @JSONField(name = "col1")
    private String col1;
    @JSONField(name = "col2")
    private String col2;
    @JSONField(name = "col3")
    private String col3;

    public InfoData(String col1, String col2, String col3) {
        this.col1 = col1;
        this.col2 = col2;
        this.col3 = col3;
    }
}
