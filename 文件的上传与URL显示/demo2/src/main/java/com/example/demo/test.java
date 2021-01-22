package com.example.demo;

import java.io.File;

public class test {
    public static void main(String []args){
        String strPath=DemoApplication.class.getClass().getResource("/").getPath();
        File f = new File(strPath);
        System.out.println("第一种：获取类加载的根路径");
        System.out.println(f);
        System.out.println(strPath);
        System.out.println(DemoApplication.class.getClass().getResource("/").getPath().substring(1));
    }
}
