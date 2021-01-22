package com.example.itmasys.service;

import com.example.itmasys.entity.Personinfo;
import com.example.itmasys.dao.PersoninfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PersoninfoService {

    @Resource
    private PersoninfoMapper personinfoMapper;

    /**
     * 查询所有记录
     *
     * @return 返回集合，没有返回空List
     */
    public List<Personinfo> listAll() {
    	return personinfoMapper.listAll();
    }


    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 返回记录，没有返回null
     */
    public Personinfo getById(Integer userid) {
    	return personinfoMapper.getById(userid);
    }
	
    /**
     * 新增，插入所有字段
     *
     * @param personinfo 新增的记录
     * @return 返回影响行数
     */
    public int insert(Personinfo personinfo) {
    	return personinfoMapper.insert(personinfo);
    }
	
    /**
     * 新增，忽略null字段
     *
     * @param personinfo 新增的记录
     * @return 返回影响行数
     */
    public int insertIgnoreNull(Personinfo personinfo) {
    	return personinfoMapper.insertIgnoreNull(personinfo);
    }
	
    /**
     * 修改，修改所有字段
     *
     * @param personinfo 修改的记录
     * @return 返回影响行数
     */
    public int update(Personinfo personinfo) {
    	return personinfoMapper.update(personinfo);
    }
	
    /**
     * 修改，忽略null字段
     *
     * @param personinfo 修改的记录
     * @return 返回影响行数
     */
    public int updateIgnoreNull(Personinfo personinfo) {
    	return personinfoMapper.updateIgnoreNull(personinfo);
    }
	
    /**
     * 删除记录
     *
     * @param personinfo 待删除的记录
     * @return 返回影响行数
     */
    public int delete(Personinfo personinfo) {
    	return personinfoMapper.delete(personinfo);
    }
	
}