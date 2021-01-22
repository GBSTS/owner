package com.example.demo.service.Impl;

import com.example.demo.model.Apply;
import com.example.demo.model.Company;
import com.example.demo.service.ApplyService;
import com.example.demo.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Company getApplyByUserId(int userId) {
        String sql="select * from company where userId=?";
        return jdbcTemplate.query(sql,(ResultSet rs)->{
            List<Company> list = new ArrayList<>();
            while(rs.next()){
                Company apply = new Company(rs.getInt("companyId"),
                        rs.getString("companyName"),
                        rs.getInt("userId"),rs.getString("userId"));
                list.add(apply);
            }
            if(list.size()>0)
                return list.get(0);
            else
                return null;
        },userId);
    }

    @Override
    public boolean insertApply(int userId) {
        String sql="insert into company set companyName='company',userId=?,companyBriefIntroduction='comapnyIntroduction'";
        return jdbcTemplate.update(sql,new Object[]{userId})>0;
    }
}
