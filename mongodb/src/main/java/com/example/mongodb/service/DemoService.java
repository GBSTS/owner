package com.example.mongodb.service;

import com.example.mongodb.entity.Demo;

import java.util.List;

public interface DemoService {

    void saveDemo(Demo demo);

    void removeDemo(Long id);

    void updateDemo(Demo demo);

    Demo findDemoById(Long id);

    List<Demo> findDemoByUrl(String url);
}