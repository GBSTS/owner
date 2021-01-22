package com.example.demo.util;

import java.util.Random;

public class UtilClass {
    public static String getVerificationCode(){
        new Random().nextInt(9999);
        return new Random().nextInt(9999)+"";
    }
}
