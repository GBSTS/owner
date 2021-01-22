package com.example.itmasys.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.example.itmasys.entity.Role;

@Mapper
public interface RoleMapper {

	/**
     * 查询所有记录
     *
     * @return 返回集合，没有返回空List
     */
	List<Role> listAll();


	/**
     * 根据主键查询
     *
     * @param roleid 主键
     * @return 返回记录，没有返回null
     */
	Role getById(Integer roleid);

	/**
	 * 根据角色名查询
	 *
	 * @param rolename 主键
	 * @return 返回记录，没有返回null
	 */
	Role getByRolename(String rolename);

	/**
     * 新增，插入所有字段
     *
     * @param role 新增的记录
     * @return 返回影响行数
     */
	int insert(Role role);
	
	/**
     * 新增，忽略null字段
     *
     * @param role 新增的记录
     * @return 返回影响行数
     */
	int insertIgnoreNull(Role role);
	
	/**
     * 修改，修改所有字段
     *
     * @param role 修改的记录
     * @return 返回影响行数
     */
	int update(Role role);
	
	/**
     * 修改，忽略null字段
     *
     * @param role 修改的记录
     * @return 返回影响行数
     */
	int updateIgnoreNull(Role role);
	
	/**
     * 删除记录
     *
     * @param role 待删除的记录
     * @return 返回影响行数
     */
	int delete(Role role);
	
}