package com.example.demo.service;

import com.example.demo.model.Apply;
import com.example.demo.model.Item;

import java.util.List;

public interface ApplyService {
    public List<Apply> getApplyByUserId(int userId);
    public boolean submitApply(Apply apply);
    public boolean canApply(int userId,int itemId);
    public boolean drwithdrawApply(int userId,int applyId);
    public boolean canCancel(int userId,int itemId);
    public int getApplyIdByUserIdAndItemId(int userId,int itemId);
    public List<Apply> getOwnerItems(int userId);
}
