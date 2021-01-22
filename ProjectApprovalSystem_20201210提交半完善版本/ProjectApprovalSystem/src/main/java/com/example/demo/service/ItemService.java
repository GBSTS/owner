package com.example.demo.service;

import com.example.demo.model.Company;
import com.example.demo.model.Item;

import java.util.List;

public interface ItemService {
    //通过所有条件查询数据
    public List<Item> getItemsByTwoCondition(int itemId, int companyId);
    public Item getItemByItemId(int itemId);
    public boolean insertItem(Item item, Company company);
    public int getCompanyIdByItemId(int itemId);
}
