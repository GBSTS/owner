package com.example.demo.json.apply;

import com.alibaba.fastjson.annotation.JSONField;
import com.example.demo.model.Apply;
import lombok.Data;

import java.util.Date;

@Data
public class ApplyHistoryData {
    @JSONField(name = "applyId")
    private int applyId;
    @JSONField(name = "itemId")
    private int itemId;
    @JSONField(name = "itemName")
    private String itemName;
    @JSONField(name = "itemMoney")
    private int itemMoney;
    @JSONField(name = "itemDetailed")
    private String itemDetailed;
    @JSONField(name = "state")
    private String state;
    @JSONField(name = "reason")
    private String reason;

    public ApplyHistoryData(Apply apply) {
        this.applyId = apply.getApplyId();
        this.itemId = apply.getItemId();
        this.itemName = apply.getItemName();
        this.itemMoney = apply.getItemMoney();
        this.itemDetailed = apply.getItemDetailed();
        this.state = apply.getState();
        this.reason = apply.getReason();
    }
}
