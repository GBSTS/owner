package com.example.demo.service;

import com.example.demo.dto.PermissionDTO;
import com.example.demo.pojo.BaseAdminPermission;
import com.example.demo.pojo.BaseAdminUser;

import java.util.List;
import java.util.Map;

/**
 * @Title: PermissionService
 * @Description:
 * @author: youqing
 * @version: 1.0
 * @date: 2018/11/30 9:44
 */
public interface AdminPermissionService {

    /**
     *
     * 功能描述: 获取当前登陆用户的权限
     *
     * @param:
     * @return:
     * @auther: youqing
     * @date: 2018/12/4 13:51
     */
    Map<String, Object> getUserPerms(BaseAdminUser user);
    List<PermissionDTO> getPermissionListByPId(Integer pid);
    BaseAdminPermission selectByPrimaryKey(Integer id);

}
