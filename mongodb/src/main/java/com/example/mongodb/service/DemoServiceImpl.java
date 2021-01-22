package com.example.mongodb.service;

import com.example.mongodb.entity.Demo;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 描述: Demo DAO 实现
 *
 * @author yanpenglei
 * @create 2018-02-03 16:57
 **/
@Component
public class DemoServiceImpl implements DemoService {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public void saveDemo(Demo demo) {
        mongoTemplate.save(demo);
    }

    @Override
    public void removeDemo(Long id) {
        Criteria criteria = Criteria.where("id").is(id);
        Query query = new Query(criteria);
        mongoTemplate.remove(query,Demo.class);
    }

    @Override
    public void updateDemo(Demo demo) {
        Criteria criteria = Criteria.where("id").is(demo.getId());
        Query query = new Query(criteria);
        Update update= new Update().set("title", demo.getTitle()).set("description", demo.getDescription())
                .set("by", demo.getBy()).set("url", demo.getUrl());
        mongoTemplate.updateFirst(query,update,Demo.class);
    }

    @Override
    public Demo findDemoById(Long id) {
        Criteria criteria = Criteria.where("id").is(id);
        Query query = new Query(criteria);
        Demo demo = mongoTemplate.findOne(query, Demo.class);
        return demo;
    }

    @Override
    public List<Demo> findDemoByUrl(String url) {
        Criteria criteria = Criteria.where("url").is(url);
        Query query = new Query(criteria);
        return mongoTemplate.find(query,Demo.class);
    }

    static {
        StringUtils.isNumeric("");
    }
}