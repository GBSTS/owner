package com.example.itmasys.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.example.itmasys.entity.Register;

@Mapper
public interface RegisterMapper {

	/**
     * 查询所有记录
     *
     * @return 返回集合，没有返回空List
     */
	List<Register> listAll();


	/**
     * 根据主键查询
     *
     * @param registerid 主键
     * @return 返回记录，没有返回null
     */
	Register getById(Integer registerid);

	/**
	 * 根据用户名查询
	 *
	 * @param username 主键
	 * @return 返回记录，没有返回null
	 */
	Register getByUsername(String username);

	/**
	 * 根据主键和验证码查询
	 *
	 * @param registerid
	 * @param randomnumber
	 * @return 返回记录，没有返回null
	 */
	Register getByRegisteridAndRandomnumber(Integer registerid, Integer randomnumber);
	
	/**
     * 新增，插入所有字段
     *
     * @param register 新增的记录
     * @return 返回影响行数
     */
	int insert(Register register);
	
	/**
     * 新增，忽略null字段
     *
     * @param register 新增的记录
     * @return 返回影响行数
     */
	int insertIgnoreNull(Register register);
	
	/**
     * 修改，修改所有字段
     *
     * @param register 修改的记录
     * @return 返回影响行数
     */
	int update(Register register);
	
	/**
     * 修改，忽略null字段
     *
     * @param register 修改的记录
     * @return 返回影响行数
     */
	int updateIgnoreNull(Register register);
	
	/**
     * 删除记录
     *
     * @param register 待删除的记录
     * @return 返回影响行数
     */
	int delete(Register register);
	
}