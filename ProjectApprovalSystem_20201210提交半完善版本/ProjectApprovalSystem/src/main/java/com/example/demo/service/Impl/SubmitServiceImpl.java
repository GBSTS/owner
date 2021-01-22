package com.example.demo.service.Impl;

import com.example.demo.model.Apply;
import com.example.demo.model.Submit;
import com.example.demo.service.FileUploadService;
import com.example.demo.service.SubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
public class SubmitServiceImpl implements SubmitService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public boolean insertSubmit(int userId,int itemId, String itemName, String submitDate, int fileId, String submitIntroduction,int companyId) {
        String sql="insert into submit set userId=?,itemId=?,itemName=?,submitDate=?,fileId=?,submitIntroduction=?,companyId=?";
        return jdbcTemplate.update(sql,new Object[]{userId,itemId,itemName,submitDate,fileId,submitIntroduction,companyId})>0;
    }

    @Override
    public List<Submit> getSubmitByItemId(int itemId) {
        String sql="select * from submit where itemId=?";
        return jdbcTemplate.query(sql,(ResultSet rs)->{
            List<Submit> list = new ArrayList<>();
            while(rs.next()){
                Submit apply = new Submit(rs.getInt("userId"),
                        rs.getInt("submitId"),rs.getInt("itemId"),
                        rs.getString("itemName"),rs.getString("submitDate"),
                        rs.getInt("fileId"),rs.getString("submitIntroduction"));
                list.add(apply);
            }
            return list;
        },itemId);
    }

    @Override
    public List<Submit> getSubmitByUserId(int userId) {
        String sql="select * from submit where userId=?";
        return jdbcTemplate.query(sql,(ResultSet rs)->{
            List<Submit> list = new ArrayList<>();
            while(rs.next()){
                Submit apply = new Submit(rs.getInt("userId"),
                        rs.getInt("submitId"),rs.getInt("itemId"),
                        rs.getString("itemName"),rs.getString("submitDate"),
                        rs.getInt("fileId"),rs.getString("submitIntroduction"));
                list.add(apply);
            }
            return list;
        },userId);
    }
}
