package com.example.itmasys.service;

import com.example.itmasys.dao.RolesMapper;
import com.example.itmasys.entity.Roles;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RolesService {

    @Resource
    private RolesMapper rolesMapper;

    public Roles selectByRoleid(Integer roleid) {
    	return rolesMapper.selectByRoleid(roleid);
    }


}