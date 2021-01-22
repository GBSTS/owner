package com.example.demo.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
public class Submit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int submitId;
    private int itemId;
    private String itemName;
    private String submitDate;
    private int fileId;
    private String submitIntroduction;
    private int userId;

    public Submit(int userId,int submitId, int itemId, String itemName, String submitDate, int fileId, String submitIntroduction) {
        this.userId = userId;
        this.submitId = submitId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.submitDate = submitDate;
        this.fileId = fileId;
        this.submitIntroduction = submitIntroduction;
    }
}