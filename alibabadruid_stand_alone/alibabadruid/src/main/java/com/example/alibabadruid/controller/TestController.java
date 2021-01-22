package com.example.alibabadruid.controller;

import com.example.alibabadruid.dao.DepartmentDao;
import com.example.alibabadruid.entity.Department;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {
    @Resource
    DepartmentDao departmentDao;

    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    public Integer insert(String departmentName){
        Department department = new Department();
        department.setDepartmentName(departmentName);
        return departmentDao.addDepartment(department);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Integer delete(Integer id){
        return departmentDao.deleteById(id);
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public Integer update(Department department){
        return departmentDao.updateDepartment(department);
    }

    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public Department select(Integer id){
        return departmentDao.queryById(id);
    }
}
