package com.example.demo.json.demand;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

@Data
public class OwnerItemSubmitData {
    @JSONField(name = "submitId")
    private int submitId;
    @JSONField(name = "itemId")
    private int itemId;
    @JSONField(name = "itemName")
    private String itemName;
    @JSONField(name = "submitDate")
    private String submitDate;
    @JSONField(name = "fileId")
    private String fileId;
    @JSONField(name = "submitIntroduction")
    private String submitIntroduction;
}
