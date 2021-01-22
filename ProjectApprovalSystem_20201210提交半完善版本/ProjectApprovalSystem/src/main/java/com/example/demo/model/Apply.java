package com.example.demo.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
public class Apply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int applyId;
    private int itemId;
    private String itemName;
    private int itemMoney;
    private String itemDetailed;
    private String state;
    private String reason;
    private int userId;
    private String account;
    private String finish;

    public Apply(int applyId, int itemId, String itemName, int itemMoney, String itemDetailed, String state, String reason, int userId, String account,String finish) {
        this.applyId = applyId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemMoney = itemMoney;
        this.itemDetailed = itemDetailed;
        this.state = state;
        this.reason = reason;
        this.userId = userId;
        this.account = account;
        this.finish = finish;
    }
}