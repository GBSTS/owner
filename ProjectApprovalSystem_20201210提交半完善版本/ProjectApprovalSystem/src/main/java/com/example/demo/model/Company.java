package com.example.demo.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int companyId;
    private String companyName;
    private int userId;
    private String companyBriefIntroduction;

    public Company(int companyId, String companyName, int userId, String companyBriefIntroduction) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.userId = userId;
        this.companyBriefIntroduction = companyBriefIntroduction;
    }
}