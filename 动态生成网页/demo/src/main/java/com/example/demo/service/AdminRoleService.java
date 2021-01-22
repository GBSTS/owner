package com.example.demo.service;

import com.example.demo.pojo.BaseAdminRole;
import com.example.demo.pojo.BaseAdminUser;

import java.util.Map;

/**
 * @Title: PermissionService
 * @Description:
 * @author: youqing
 * @version: 1.0
 * @date: 2018/11/30 9:44
 */
public interface AdminRoleService {

   public BaseAdminRole selectByPrimaryKey(Integer roleId);

}
