package com.example.demo.json;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class InfoData{
    @JSONField(name = "imgUrl")
    private String imgUrl;
    @JSONField(name = "productId")
    private String productId;
    @JSONField(name = "productName")
    private String productName;
    @JSONField(name = "productLocal")
    private String productLocal;
    @JSONField(name = "productValue")
    private String productValue;
    @JSONField(name = "productUnit")
    private String productUnit;
    @JSONField(name = "expectedMatureDate")
    private String expectedMatureDate;
    @JSONField(name = "shelvesDate")
    private String shelvesDate;
    @JSONField(name = "productIntroduction")
    private String productIntroduction;

    public InfoData(String imgUrl, String productId, String productName, String productLocal, String productValue, String productUnit, String expectedMatureDate, String shelvesDate, String productIntroduction) {
        this.imgUrl = imgUrl;
        this.productId = productId;
        this.productName = productName;
        this.productLocal = productLocal;
        this.productValue = productValue;
        this.productUnit = productUnit;
        this.expectedMatureDate = expectedMatureDate;
        this.shelvesDate = shelvesDate;
        this.productIntroduction = productIntroduction;
    }


}
