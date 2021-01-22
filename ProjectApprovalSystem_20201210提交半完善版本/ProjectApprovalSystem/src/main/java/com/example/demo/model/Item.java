package com.example.demo.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int itemId;
    private String itemName;
    private int companyId;
    private String companyName;
    private int currentHumanNumber;
    private Date itemDeadline;
    private String itemDetailed;
    private int itemMoney;

    public Item(int itemId, String itemName, int companyId, String companyName, int currentHumanNumber, Date itemDeadline, String itemDetailed, int itemMoney) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.companyId = companyId;
        this.companyName = companyName;
        this.currentHumanNumber = currentHumanNumber;
        this.itemDeadline = itemDeadline;
        this.itemDetailed = itemDetailed;
        this.itemMoney = itemMoney;
    }

    public Item() {

    }
}