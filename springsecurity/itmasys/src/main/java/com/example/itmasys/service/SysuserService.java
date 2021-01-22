package com.example.itmasys.service;

import com.example.itmasys.dao.SysuserMapper;
import com.example.itmasys.security.bean.SysuserBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysuserService {

    @Resource
    private SysuserMapper sysuserMapper;

    public SysuserBean selectByUsername(String username) {
    	return sysuserMapper.selectByUsername(username);
    }


}