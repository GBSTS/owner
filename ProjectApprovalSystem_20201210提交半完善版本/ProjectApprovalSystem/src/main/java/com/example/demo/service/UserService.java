package com.example.demo.service;

import com.example.demo.model.User;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserService {
    //查询该用户是否存在
    public boolean isExist(String email,String identity);
    //查询该用户是否已经激活
    public boolean isAlive(String email,String identity);
    //查询邮箱、密码、身份是否匹配
    public boolean isCanLog(String email,String password, String identity);
    //重新注册
    public boolean reRegistration(String account,String password,String email,String phone,String identity,String code);
    //记录注册的信息
    public boolean recordRegistration(String account,String password,String email,String phone,String identity,String code);
    //验证激活码并激活该用户
    public boolean validateAndActivateUsers(String email,String emailCode,String identity);
    public User getUserByUserId(int userId);
    public int getUserIdByEmailAndIdentity(String email,String identity);
}
