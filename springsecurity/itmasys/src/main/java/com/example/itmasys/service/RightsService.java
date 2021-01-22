package com.example.itmasys.service;

import com.example.itmasys.dao.RightsMapper;
import com.example.itmasys.dao.RolesMapper;
import com.example.itmasys.entity.Rights;
import com.example.itmasys.entity.Roles;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RightsService {

    @Resource
    private RightsMapper rightsMapper;

    public List<Rights> selectByRoleid() {
    	return rightsMapper.selectAllRigths();
    }


}