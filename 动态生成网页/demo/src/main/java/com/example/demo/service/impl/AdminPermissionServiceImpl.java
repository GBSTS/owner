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
public class AdminPermissionServiceImpl implements AdminPermissionService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    AdminRoleService adminRoleService;

    @Autowired
    AdminPermissionService adminPermissionService;

    @Override
    public Map <String, Object> getUserPerms(BaseAdminUser user) {
        Map<String, Object> data = new HashMap<>();
        Integer roleId = user.getRoleId();

        BaseAdminRole role = adminRoleService.selectByPrimaryKey(roleId);
        if (null != role ) {
            String permissions = role.getPermissions();

            String[] ids = permissions.split(",");
            List<PermissionDTO> permissionList = new ArrayList <>();
            for (String id : ids) {
                // 角色对应的权限数据
                BaseAdminPermission perm = adminPermissionService.selectByPrimaryKey(Integer.parseInt(id));
                if (null != perm ) {
                    // 授权角色下所有权限
                    PermissionDTO permissionDTO = new PermissionDTO();
                    permissionDTO.setId(perm.getId());
                    permissionDTO.setName(perm.getName());
                    permissionDTO.setPid(perm.getPid());
                    permissionDTO.setDescpt(perm.getDescpt());
                    permissionDTO.setUrl("");
                    permissionDTO.setCreateTime(perm.getCreateTime());
                    permissionDTO.setUpdateTime(perm.getUpdateTime());
                    permissionDTO.setDelFlag(perm.getDelFlag());
                    //获取子权限
                    List<PermissionDTO> childrens = adminPermissionService.getPermissionListByPId(perm.getId());
                    permissionDTO.setChildrens(childrens);
                    permissionList.add(permissionDTO);
                }
            }
            data.put("perm",permissionList);
        }

        return data;
    }

    @Override
    public List<PermissionDTO> getPermissionListByPId(Integer pid) {
        String sql="select * from base_admin_permission where pid=?";
        return jdbcTemplate.query(sql,(ResultSet rs)->{
            List<PermissionDTO> list = new ArrayList<>();
            while(rs.next()){
                PermissionDTO userInfo = new PermissionDTO();
                userInfo.setId(rs.getInt("id"));
                userInfo.setName(rs.getString("name"));
                userInfo.setPid(rs.getInt("pid"));
                userInfo.setPname(rs.getString("name"));
                userInfo.setDescpt(rs.getString("descpt"));
                userInfo.setUrl(rs.getString("url"));
                userInfo.setCreateTime(rs.getString("create_time"));
                userInfo.setUpdateTime(rs.getString("update_time"));
                userInfo.setDelFlag(rs.getInt("del_flag"));
                list.add(userInfo);
            }
            return list;
        },pid);
    }

    @Override
    public BaseAdminPermission selectByPrimaryKey(Integer id) {
        String sql="select * from base_admin_permission where id = ?";
        return jdbcTemplate.query(sql,(ResultSet rs)->{
            BaseAdminPermission baseAdminPermission = null;
            if(rs.next()){
                baseAdminPermission = new BaseAdminPermission();
                baseAdminPermission.setId(rs.getInt("id"));
                baseAdminPermission.setName(rs.getString("name"));
                baseAdminPermission.setPid(rs.getInt("pid"));
                baseAdminPermission.setDescpt(rs.getString("descpt"));
                baseAdminPermission.setUrl(rs.getString("url"));
                baseAdminPermission.setCreateTime(rs.getString("create_time"));
                baseAdminPermission.setUpdateTime(rs.getString("update_time"));
                baseAdminPermission.setDelFlag(rs.getInt("del_flag"));
            }
            return baseAdminPermission;
        },id);
    }
}
