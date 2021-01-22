package com.example.demo.service.Impl;

import com.example.demo.model.Item;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public boolean isExist(String email, String identity) {
        String sql="select * from user where email=? and identity=?";
        List<User> userList=jdbcTemplate.query(sql,(ResultSet rs)->{
            List<User> list = new ArrayList<>();
            while(rs.next()){
                User user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setAccount(rs.getString("account"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setState(rs.getString("state"));
                user.setIdentity(rs.getString("identity"));
                user.setCode(rs.getString("code"));
                list.add(user);
            }
            return list;
        },email,identity);
        return userList.size()==0 ? false : true;
    }

    @Override
    public boolean isAlive(String email, String identity) {
        String sql="select * from user where email=? and identity=?";
        List<User> userList=jdbcTemplate.query(sql,(ResultSet rs)->{
            List<User> list = new ArrayList<>();
            while(rs.next()){
                User user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setAccount(rs.getString("account"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setState(rs.getString("state"));
                user.setIdentity(rs.getString("identity"));
                user.setCode(rs.getString("code"));
                list.add(user);
            }
            return list;
        },email,identity);
        return userList.get(0).getState().equals("False") ? false : true;
    }

    @Override
    public boolean reRegistration(String account, String password, String email, String phone, String identity, String code) {
        String sql="update user set account=?,password=?,phone=?,state=?,code=? where email=? and identity =?";
        return jdbcTemplate.update(sql,new Object[]{account,password,phone,"False",code,email,identity})>0;
    }

    @Override
    public boolean isCanLog(String email, String password, String identity) {
        String sql="select * from user where email=? and identity=? and password=?";
        List<User> userList=jdbcTemplate.query(sql,(ResultSet rs)->{
            List<User> list = new ArrayList<>();
            while(rs.next()){
                User user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setAccount(rs.getString("account"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setState(rs.getString("state"));
                user.setIdentity(rs.getString("identity"));
                user.setCode(rs.getString("code"));
                list.add(user);
            }
            return list;
        },email,identity,password);
        return userList.size()==0 ? false : true;
    }

    @Override
    public boolean recordRegistration(String account, String password, String email, String phone, String identity,String code) {
        String sql="insert into user set account=?,password=?,email=?,phone=?,identity=?,state=?,code=?";
        return jdbcTemplate.update(sql,new Object[]{account,password,email,phone,identity,"False",code})>0;
    }

    @Override
    public boolean validateAndActivateUsers(String email, String emailCode, String identity) {
        String sql="select * from user where email=? and identity=? and code=?";
        List<User> userList=jdbcTemplate.query(sql,(ResultSet rs)->{
            List<User> list = new ArrayList<>();
            while(rs.next()){
                User user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setAccount(rs.getString("account"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setState(rs.getString("state"));
                user.setIdentity(rs.getString("identity"));
                user.setCode(rs.getString("code"));
                list.add(user);
            }
            return list;
        },email,identity,emailCode);
        if(userList.size()==0)
            return false;
        else
            sql="update user set state='True',code='' where email =? and identity=?";
        return jdbcTemplate.update(sql,new Object[]{email,identity})>0;
    }

    @Override
    public User getUserByUserId(int userId) {
        String sql="select * from user where userId=?";
        return jdbcTemplate.query(sql,(ResultSet rs)->{
            List<User> list = new ArrayList<>();
            while(rs.next()){
                User user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setAccount(rs.getString("account"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setState(rs.getString("state"));
                user.setIdentity(rs.getString("identity"));
                user.setCode(rs.getString("code"));
                list.add(user);
            }
            return list.get(0);
        },userId);
    }

    @Override
    public int getUserIdByEmailAndIdentity(String email, String identity) {
        String sql="select * from user where email=? and identity=?";
        return jdbcTemplate.query(sql,(ResultSet rs)->{
            List<User> list = new ArrayList<>();
            while(rs.next()){
                User user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setAccount(rs.getString("account"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setState(rs.getString("state"));
                user.setIdentity(rs.getString("identity"));
                user.setCode(rs.getString("code"));
                list.add(user);
            }
            return list.get(0);
        },email,identity).getUserId();
    }
}
