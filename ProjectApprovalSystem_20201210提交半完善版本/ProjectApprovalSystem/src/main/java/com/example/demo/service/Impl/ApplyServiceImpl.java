package com.example.demo.service.Impl;

import com.example.demo.model.Apply;
import com.example.demo.model.Item;
import com.example.demo.service.ApplyService;
import com.example.demo.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApplyServiceImpl implements ApplyService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Apply> getApplyByUserId(int userId) {
        String sql="select * from apply where userId=?";
        return jdbcTemplate.query(sql,(ResultSet rs)->{
            List<Apply> list = new ArrayList<>();
            while(rs.next()){
                Apply apply = new Apply(rs.getInt("applyId"),
                        rs.getInt("itemId"),rs.getString("itemName"),
                        rs.getInt("itemMoney"),rs.getString("itemDetailed"),
                        rs.getString("state"),rs.getString("reason"),
                        rs.getInt("userId"),rs.getString("account"),"");
                list.add(apply);
            }
            return list;
        },userId);
    }

    @Override
    public boolean submitApply(Apply apply) {
        String sql="insert into apply set itemId=?,itemName=?,itemMoney=?,itemDetailed=?,state=?,reason=?,userId=?,account=?";
        return jdbcTemplate.update(sql,new Object[]{apply.getItemId(),apply.getItemName(),apply.getItemMoney(),apply.getItemDetailed(),apply.getState(),apply.getReason(),apply.getUserId(),apply.getAccount()})>0;
    }

    @Override
    public boolean canApply(int userId, int itemId) {
        String sql="select * from apply where userId=? and itemId=?";
        List<Apply> applyList = jdbcTemplate.query(sql,(ResultSet rs)->{
            List<Apply> list = new ArrayList<>();
            while(rs.next()){
                Apply apply = new Apply(rs.getInt("applyId"),
                        rs.getInt("itemId"),rs.getString("itemName"),
                        rs.getInt("itemMoney"),rs.getString("itemDetailed"),
                        rs.getString("state"),rs.getString("reason"),
                        rs.getInt("userId"),rs.getString("account"),"");
                list.add(apply);
            }
            return list;
        },userId,itemId);
        if(applyList.size()!=0)
            return applyList.get(applyList.size()-1).getState().equals("拒绝申请");
        else
            return true;
    }

    @Override
    public boolean drwithdrawApply(int userId, int applyId) {
        String sql="delete from apply where userId=? and applyId =?";
        return jdbcTemplate.update(sql,new Object[]{userId,applyId})>0;
    }

    @Override
    public boolean canCancel(int userId, int itemId) {
        String sql="select * from apply where userId=? and itemId=? and state='审批通过'";
        List<Apply> applyList = jdbcTemplate.query(sql,(ResultSet rs)->{
            List<Apply> list = new ArrayList<>();
            while(rs.next()){
                Apply apply = new Apply(rs.getInt("applyId"),
                        rs.getInt("itemId"),rs.getString("itemName"),
                        rs.getInt("itemMoney"),rs.getString("itemDetailed"),
                        rs.getString("state"),rs.getString("reason"),
                        rs.getInt("userId"),rs.getString("account"),"");
                list.add(apply);
            }
            return list;
        },userId,itemId);
        if(applyList.size()!=0)
            return !applyList.get(applyList.size()-1).getState().equals("审批通过");
        else
            return true;
    }

    @Override
    public int getApplyIdByUserIdAndItemId(int userId, int itemId) {
        String sql="select * from apply where userId=? and itemId=?";
        Apply endApply = jdbcTemplate.query(sql,(ResultSet rs)->{
            List<Apply> list = new ArrayList<>();
            while(rs.next()){
                Apply apply = new Apply(rs.getInt("applyId"),
                        rs.getInt("itemId"),rs.getString("itemName"),
                        rs.getInt("itemMoney"),rs.getString("itemDetailed"),
                        rs.getString("state"),rs.getString("reason"),
                        rs.getInt("userId"),rs.getString("account"),"");
                list.add(apply);
            }
            return list.get(0);
        },userId,itemId);
        return endApply.getApplyId();
    }

    @Override
    public List<Apply> getOwnerItems(int userId) {
        String sql="select * from apply where userId=? and state='审批通过' and finish='False'";
        return jdbcTemplate.query(sql,(ResultSet rs)->{
            List<Apply> list = new ArrayList<>();
            while(rs.next()){
                Apply apply = new Apply(rs.getInt("applyId"),
                        rs.getInt("itemId"),rs.getString("itemName"),
                        rs.getInt("itemMoney"),rs.getString("itemDetailed"),
                        rs.getString("state"),rs.getString("reason"),
                        rs.getInt("userId"),rs.getString("account"),"");
                list.add(apply);
            }
            return list;
        },userId);
    }
}
