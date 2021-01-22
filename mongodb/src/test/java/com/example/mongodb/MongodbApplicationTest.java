package com.example.mongodb;

import com.alibaba.fastjson.JSONObject;
import com.example.mongodb.service.DemoService;
import com.example.mongodb.entity.Demo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MongodbApplication.class)
class MongodbApplicationTest {
    @Autowired
    private DemoService demoService;

    @Test
    public void saveDemoTest() {

        Demo demo = new Demo();
        demo.setId(1L);
        demo.setTitle("Spring Boot 中使用 MongoDB");
        demo.setDescription("关注公众号，搜云库，专注于开发技术的研究与知识分享");
        demo.setBy("souyunku");
        demo.setUrl("http://www.souyunku.com");
        demoService.saveDemo(demo);

        demo = new Demo();
        demo.setId(2L);
        demo.setTitle("Spring Boot 中使用 MongoDB");
        demo.setDescription("关注公众号，搜云库，专注于开发技术的研究与知识分享");
        demo.setBy("souyunku");
        demo.setUrl("http://www.souyunku.com");
        demoService.saveDemo(demo);
    }

    @Test
    public void removeDemoTest() {
        demoService.removeDemo(2L);
    }

    @Test
    public void updateDemoTest() {

        Demo demo = new Demo();
        demo.setId(1L);
        demo.setTitle("Spring Boot 中使用 MongoDB 更新数据");
        demo.setDescription("关注公众号，搜云库，专注于开发技术的研究与知识分享");
        demo.setBy("souyunku");
        demo.setUrl("http://www.souyunku.com");

        demoService.updateDemo(demo);
    }

    @Test
    public void findDemoByIdTest() {

        Demo demo = demoService.findDemoById(1L);
    }

    @Test
    public void findDemoByUrlTest() {

        List<Demo> demo = demoService.findDemoByUrl("http://www.souyunku.com");
    }
}