package com.example.demo.service;

import com.example.demo.model.Submit;

import java.util.List;

public interface SubmitService {
    public boolean insertSubmit(int userId,int itemId,String itemName,String submitDate,int fileId,String submitIntroduction,int companyId);
    public List<Submit> getSubmitByItemId(int itemId);
    public List<Submit> getSubmitByUserId(int UserId);
}
