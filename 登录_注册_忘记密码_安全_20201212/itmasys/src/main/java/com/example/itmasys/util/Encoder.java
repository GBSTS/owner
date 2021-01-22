package com.example.itmasys.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Encoder {
    public static String bCryptPasswordEncoder(String password){//获取加密后的密码
        return new BCryptPasswordEncoder().encode(password);
    }
//    public static void main(String []argc){
//        System.out.println(new BCryptPasswordEncoder().encode("company"));
//    }
}
