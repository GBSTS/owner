package com.example.demo.service.impl;


import com.example.demo.dto.PermissionDTO;
import com.example.demo.pojo.BaseAdminPermission;
import com.example.demo.pojo.BaseAdminRole;
import com.example.demo.pojo.BaseAdminUser;
import com.example.demo.service.AdminPermissionService;
import com.example.demo.service.AdminRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: PermissionServiceImpl
 * @Description:
 * @author: youqing
 * @version: 1.0
 * @date: 2018/11/30 9:44
 */
@Service
public class AdminRoleServiceImpl implements AdminRoleService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public BaseAdminRole selectByPrimaryKey(Integer roleId) {
        String sql="select * from base_admin_role where id = ?";
        return jdbcTemplate.query(sql,(ResultSet rs)->{
            BaseAdminRole baseAdminRole = null;
            if(rs.next()){
                baseAdminRole = new BaseAdminRole();
                baseAdminRole.setId(rs.getInt("id"));
                baseAdminRole.setRoleName(rs.getString("role_name"));
                baseAdminRole.setRoleDesc(rs.getString("role_desc"));
                baseAdminRole.setPermissions(rs.getString("permissions"));
                baseAdminRole.setCreateTime(rs.getString("create_time"));
                baseAdminRole.setUpdateTime(rs.getString("update_time"));
                baseAdminRole.setRoleStatus(rs.getInt("role_status"));
            }
            return baseAdminRole;
        },roleId);
    }
}
