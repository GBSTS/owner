package com.example.demo.service;

import com.example.demo.model.Apply;
import com.example.demo.model.Company;

import java.util.List;

public interface CompanyService {
    public Company getApplyByUserId(int userId);
    public boolean insertApply(int userId);
}
