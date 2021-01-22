package com.hsl.springbootjdbctemplate.service;

import com.hsl.springbootjdbctemplate.entity.Aymood;

import java.util.List;

public interface AymoodService {
    //查询单个值
    public String findIdByContent(String content);
    //查询一个对象
    public Aymood findAymoodById(int id);
    //查询多个对象
    public List<Aymood> findAymoodByConditions(int id);
    //添加一个对象
    public boolean insertAymood(Aymood aymood);
    //修改一个对象
    public boolean updateAymoodById(Aymood aymood);
    //删除一个对象
    public boolean deleteAymoodById(int id);
}
