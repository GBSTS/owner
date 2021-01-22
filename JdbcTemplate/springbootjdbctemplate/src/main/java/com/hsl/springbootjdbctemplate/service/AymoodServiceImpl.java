package com.hsl.springbootjdbctemplate.service;

import com.hsl.springbootjdbctemplate.entity.Aymood;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
public class AymoodServiceImpl implements AymoodService{
    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * 查询单个值
     * @param content
     * @return
     */
    @Override
    public String findIdByContent(String content) {
        String sql="select id from aymood where content = ?";
        String str= jdbcTemplate.queryForObject(sql, String.class,content);
        return str;
    }

    /**
     * 查询一个对象
     * @param id
     * @return
     */
    @Override
    public Aymood findAymoodById(int id) {
        String sql="select * from aymood where id = ?";
        return jdbcTemplate.query(sql,(ResultSet rs)->{
            Aymood userInfo = null;
            if(rs.next()){
                userInfo = new Aymood();
                userInfo.setId(rs.getInt("id"));
                userInfo.setContent(rs.getString("content"));
            }
            return userInfo;
        },id);
    }

    /**
     * 查询多个对象
     * @param id
     * @return
     */
    @Override
    public List<Aymood> findAymoodByConditions(int id) {
        String sql="select * from aymood where id>?";
        return jdbcTemplate.query(sql,(ResultSet rs)->{
            List<Aymood> list = new ArrayList<>();
            while(rs.next()){
                Aymood userInfo = new Aymood();
                userInfo.setId(rs.getInt("id"));
                userInfo.setContent(rs.getString("content"));
                list.add(userInfo);
            }
            return list;
        },id);
    }

    /**
     * 添加一个对象
     * @param aymood
     * @return
     */
    @Override
    public boolean insertAymood(Aymood aymood) {
        String sql="insert into aymood values(?,?)";
        return jdbcTemplate.update(sql,new Object[]{aymood.getId(),aymood.getContent()})>0;
    }

    /**
     * 修改一个对象
     * @param aymood
     * @return
     */
    @Override
    public boolean updateAymoodById(Aymood aymood) {
        String sql="update aymood set content=? where id =?";
        return jdbcTemplate.update(sql,new Object[]{aymood.getContent(),aymood.getId()})>0;
    }

    /**
     * 删除一个对象
     * @param id
     * @return
     */
    @Override
    public boolean deleteAymoodById(int id) {
        String sql="delete from aymood where id=?";
        return jdbcTemplate.update(sql,new Object[]{id})>0;
    }
}
