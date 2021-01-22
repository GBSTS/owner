package com.example.itmasys.service;

import com.example.itmasys.entity.Role;
import com.example.itmasys.dao.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleService {

    @Resource
    private RoleMapper roleMapper;

    /**
     * 查询所有记录
     *
     * @return 返回集合，没有返回空List
     */
    public List<Role> listAll() {
    	return roleMapper.listAll();
    }


    /**
     * 根据角色名查询
     *
     * @param rolename 主键
     * @return 返回记录，没有返回null
     */
    public Role getByRolename(String rolename) {
    	return roleMapper.getByRolename(rolename);
    }

    /**
     * 根据主键查询
     *
     * @param roleid 主键
     * @return 返回记录，没有返回null
     */
    public Role getById(Integer roleid) {
        return roleMapper.getById(roleid);
    }
	
    /**
     * 新增，插入所有字段
     *
     * @param role 新增的记录
     * @return 返回影响行数
     */
    public int insert(Role role) {
    	return roleMapper.insert(role);
    }
	
    /**
     * 新增，忽略null字段
     *
     * @param role 新增的记录
     * @return 返回影响行数
     */
    public int insertIgnoreNull(Role role) {
    	return roleMapper.insertIgnoreNull(role);
    }
	
    /**
     * 修改，修改所有字段
     *
     * @param role 修改的记录
     * @return 返回影响行数
     */
    public int update(Role role) {
    	return roleMapper.update(role);
    }
	
    /**
     * 修改，忽略null字段
     *
     * @param role 修改的记录
     * @return 返回影响行数
     */
    public int updateIgnoreNull(Role role) {
    	return roleMapper.updateIgnoreNull(role);
    }
	
    /**
     * 删除记录
     *
     * @param role 待删除的记录
     * @return 返回影响行数
     */
    public int delete(Role role) {
    	return roleMapper.delete(role);
    }
	
}