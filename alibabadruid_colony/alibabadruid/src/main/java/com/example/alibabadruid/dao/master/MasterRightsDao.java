package com.example.alibabadruid.dao.master;

import java.util.List;

import com.example.alibabadruid.entity.master.Rights;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MasterRightsDao {

	/**
     * 查询所有记录
     *
     * @return 返回集合，没有返回空List
     */
	List<Rights> listAll();


	/**
     * 根据主键查询
     *
     * @param rightid 主键
     * @return 返回记录，没有返回null
     */
	Rights getById(Integer rightid);
	
	/**
     * 新增，插入所有字段
     *
     * @param rights 新增的记录
     * @return 返回影响行数
     */
	int insert(Rights rights);
	
	/**
     * 新增，忽略null字段
     *
     * @param rights 新增的记录
     * @return 返回影响行数
     */
	int insertIgnoreNull(Rights rights);
	
	/**
     * 修改，修改所有字段
     *
     * @param rights 修改的记录
     * @return 返回影响行数
     */
	int update(Rights rights);
	
	/**
     * 修改，忽略null字段
     *
     * @param rights 修改的记录
     * @return 返回影响行数
     */
	int updateIgnoreNull(Rights rights);
	
	/**
     * 删除记录
     *
     * @param rights 待删除的记录
     * @return 返回影响行数
     */
	int delete(Rights rights);
	
}