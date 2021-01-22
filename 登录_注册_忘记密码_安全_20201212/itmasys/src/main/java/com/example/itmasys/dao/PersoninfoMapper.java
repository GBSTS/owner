package com.example.itmasys.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.example.itmasys.entity.Personinfo;

@Mapper
public interface PersoninfoMapper {

	/**
     * 查询所有记录
     *
     * @return 返回集合，没有返回空List
     */
	List<Personinfo> listAll();


	/**
     * 根据主键查询
     *
     * @param id 主键
     * @return 返回记录，没有返回null
     */
	Personinfo getById(Integer userid);
	
	/**
     * 新增，插入所有字段
     *
     * @param personinfo 新增的记录
     * @return 返回影响行数
     */
	int insert(Personinfo personinfo);
	
	/**
     * 新增，忽略null字段
     *
     * @param personinfo 新增的记录
     * @return 返回影响行数
     */
	int insertIgnoreNull(Personinfo personinfo);
	
	/**
     * 修改，修改所有字段
     *
     * @param personinfo 修改的记录
     * @return 返回影响行数
     */
	int update(Personinfo personinfo);
	
	/**
     * 修改，忽略null字段
     *
     * @param personinfo 修改的记录
     * @return 返回影响行数
     */
	int updateIgnoreNull(Personinfo personinfo);
	
	/**
     * 删除记录
     *
     * @param personinfo 待删除的记录
     * @return 返回影响行数
     */
	int delete(Personinfo personinfo);
	
}