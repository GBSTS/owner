package com.example.demo.json.apply;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

@Data
public class ProvideAppliedItemData {
    @JSONField(name = "itemId")
    private int itemId;
    @JSONField(name = "itemName")
    private String itemName;
    @JSONField(name = "companyId")
    private int companyId;
    @JSONField(name = "companyName")
    private String companyName;
    @JSONField(name = "currentHumanNumber")
    private int currentHumanNumber;
    @JSONField(name = "itemDeadline")
    private Date itemDeadline;
    @JSONField(name = "itemDetailed")
    private String itemDetailed;
}
