package com.example.itmasys.dao;

import com.example.itmasys.entity.Sysuser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysuserMapper {

	/**
     * 查询所有记录
     *
     * @return 返回集合，没有返回空List
     */
	List<Sysuser> listAll();


	/**
     * 根据主键查询
     *
     * @param userid 主键
     * @return 返回记录，没有返回null
     */
	Sysuser getById(Integer userid);

	/**
	 * 根据用户名查询
	 *
	 * @param username 用户名
	 * @return 返回记录，没有返回null
	 */
	Sysuser getByName(String username);
	
	/**
     * 新增，插入所有字段
     *
     * @param sysuser 新增的记录
     * @return 返回影响行数
     */
	int insert(Sysuser sysuser);
	
	/**
     * 新增，忽略null字段
     *
     * @param sysuser 新增的记录
     * @return 返回影响行数
     */
	int insertIgnoreNull(Sysuser sysuser);
	
	/**
     * 修改，修改所有字段
     *
     * @param sysuser 修改的记录
     * @return 返回影响行数
     */
	int update(Sysuser sysuser);
	
	/**
     * 修改，忽略null字段
     *
     * @param sysuser 修改的记录
     * @return 返回影响行数
     */
	int updateIgnoreNull(Sysuser sysuser);
	
	/**
     * 删除记录
     *
     * @param sysuser 待删除的记录
     * @return 返回影响行数
     */
	int delete(Sysuser sysuser);
	
}