package com.example.demo.service.Impl;

import com.example.demo.model.Company;
import com.example.demo.model.Item;
import com.example.demo.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceImpl  implements ItemService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Item> getItemsByTwoCondition(int itemId,int companyId) {
        if(itemId!=-1&&companyId!=-1){
            String sql="select * from item where itemId=? and companyId=?";
            return jdbcTemplate.query(sql,(ResultSet rs)->{
                List<Item> list = new ArrayList<>();
                while(rs.next()){
                    Item item = new Item();
                    item.setItemId(rs.getInt("itemId"));
                    item.setItemName(rs.getString("itemName"));
                    item.setCompanyId(rs.getInt("companyId"));
                    item.setCompanyName(rs.getString("companyName"));
                    item.setCurrentHumanNumber(rs.getInt("currentHumanNumber"));
                    item.setItemDeadline(rs.getDate("itemDeadline"));
                    item.setItemDetailed(rs.getString("itemDetailed"));
                    item.setItemMoney(rs.getInt("itemMoney"));
                    list.add(item);
                }
                return list;
            },itemId,companyId);
        }else if(itemId==-1 && companyId!=-1){
            String sql="select * from item where companyId=?";
            return jdbcTemplate.query(sql,(ResultSet rs)->{
                List<Item> list = new ArrayList<>();
                while(rs.next()){
                    Item item = new Item();
                    item.setItemId(rs.getInt("itemId"));
                    item.setItemName(rs.getString("itemName"));
                    item.setCompanyId(rs.getInt("companyId"));
                    item.setCompanyName(rs.getString("companyName"));
                    item.setCurrentHumanNumber(rs.getInt("currentHumanNumber"));
                    item.setItemDeadline(rs.getDate("itemDeadline"));
                    item.setItemDetailed(rs.getString("itemDetailed"));
                    item.setItemMoney(rs.getInt("itemMoney"));
                    list.add(item);
                }
                return list;
            },companyId);
        }else if(companyId==-1 && itemId!=-1){
            String sql="select * from item where itemId=?";
            return jdbcTemplate.query(sql,(ResultSet rs)->{
                List<Item> list = new ArrayList<>();
                while(rs.next()){
                    Item item = new Item();
                    item.setItemId(rs.getInt("itemId"));
                    item.setItemName(rs.getString("itemName"));
                    item.setCompanyId(rs.getInt("companyId"));
                    item.setCompanyName(rs.getString("companyName"));
                    item.setCurrentHumanNumber(rs.getInt("currentHumanNumber"));
                    item.setItemDeadline(rs.getDate("itemDeadline"));
                    item.setItemDetailed(rs.getString("itemDetailed"));
                    item.setItemMoney(rs.getInt("itemMoney"));
                    list.add(item);
                }
                return list;
            },itemId);
        }else{
            String sql="select * from item";
            return jdbcTemplate.query(sql,(ResultSet rs)->{
                List<Item> list = new ArrayList<>();
                while(rs.next()){
                    Item item = new Item();
                    item.setItemId(rs.getInt("itemId"));
                    item.setItemName(rs.getString("itemName"));
                    item.setCompanyId(rs.getInt("companyId"));
                    item.setCompanyName(rs.getString("companyName"));
                    item.setCurrentHumanNumber(rs.getInt("currentHumanNumber"));
                    item.setItemDeadline(rs.getDate("itemDeadline"));
                    item.setItemDetailed(rs.getString("itemDetailed"));
                    item.setItemMoney(rs.getInt("itemMoney"));
                    list.add(item);
                }
                return list;
            });
        }
    }

    @Override
    public Item getItemByItemId(int itemId) {
        String sql="select * from item where itemId=?";
        return jdbcTemplate.query(sql,(ResultSet rs)->{
            List<Item> list = new ArrayList<>();
            while(rs.next()){
                Item item = new Item();
                item.setItemId(rs.getInt("itemId"));
                item.setItemName(rs.getString("itemName"));
                item.setCompanyId(rs.getInt("companyId"));
                item.setCompanyName(rs.getString("companyName"));
                item.setCurrentHumanNumber(rs.getInt("currentHumanNumber"));
                item.setItemDeadline(rs.getDate("itemDeadline"));
                item.setItemDetailed(rs.getString("itemDetailed"));
                item.setItemMoney(rs.getInt("itemMoney"));
                list.add(item);
            }
            return list.get(0);
        },itemId);
    }

    @Override
    public boolean insertItem(Item item, Company company) {
        String sql="insert into item set itemName=?,companyId=?,companyName=?,currentHumanNumber=?,itemDeadline=?,itemDetailed=?,itemMoney=?";
        return jdbcTemplate.update(sql,new Object[]{item.getItemName(),company.getCompanyId(),company.getCompanyName(),item.getCurrentHumanNumber(),item.getItemDeadline(),item.getItemDetailed(),item.getItemMoney()})>0;
    }

    @Override
    public int getCompanyIdByItemId(int itemId) {
        String sql="select * from item where itemId=?";
        return jdbcTemplate.query(sql,(ResultSet rs)->{
            List<Item> list = new ArrayList<>();
            while(rs.next()){
                Item item = new Item();
                item.setItemId(rs.getInt("itemId"));
                item.setItemName(rs.getString("itemName"));
                item.setCompanyId(rs.getInt("companyId"));
                item.setCompanyName(rs.getString("companyName"));
                item.setCurrentHumanNumber(rs.getInt("currentHumanNumber"));
                item.setItemDeadline(rs.getDate("itemDeadline"));
                item.setItemDetailed(rs.getString("itemDetailed"));
                item.setItemMoney(rs.getInt("itemMoney"));
                list.add(item);
            }
            return list.get(0).getCompanyId();
        },itemId);
    }
}
