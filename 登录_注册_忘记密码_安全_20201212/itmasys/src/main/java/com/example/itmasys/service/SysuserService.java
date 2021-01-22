package com.example.itmasys.service;

import com.example.itmasys.dao.SysuserMapper;
import com.example.itmasys.entity.Sysuser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysuserService {

    @Resource
    private SysuserMapper sysuserMapper;

    /**
     * 查询所有记录
     *
     * @return 返回集合，没有返回空List
     */
    public List<Sysuser> listAll() {
    	return sysuserMapper.listAll();
    }


    /**
     * 根据主键查询
     *
     * @param userid 主键
     * @return 返回记录，没有返回null
     */
    public Sysuser getById(Integer userid) {
    	return sysuserMapper.getById(userid);
    }

    /**
     * 根据用户名查询
     *
     * @param username 用户名
     * @return 返回记录，没有返回null
     */
    public Sysuser findUserByName(String username) {
        return sysuserMapper.getByName(username);
    }

    /**
     * 新增，插入所有字段
     *
     * @param sysuser 新增的记录
     * @return 返回影响行数
     */
    public int insert(Sysuser sysuser) {
    	return sysuserMapper.insert(sysuser);
    }
	
    /**
     * 新增，忽略null字段
     *
     * @param sysuser 新增的记录
     * @return 返回影响行数
     */
    public int insertIgnoreNull(Sysuser sysuser) {
    	return sysuserMapper.insertIgnoreNull(sysuser);
    }
	
    /**
     * 修改，修改所有字段
     *
     * @param sysuser 修改的记录
     * @return 返回影响行数
     */
    public int update(Sysuser sysuser) {
    	return sysuserMapper.update(sysuser);
    }
	
    /**
     * 修改，忽略null字段
     *
     * @param sysuser 修改的记录
     * @return 返回影响行数
     */
    public int updateIgnoreNull(Sysuser sysuser) {
    	return sysuserMapper.updateIgnoreNull(sysuser);
    }
	
    /**
     * 删除记录
     *
     * @param sysuser 待删除的记录
     * @return 返回影响行数
     */
    public int delete(Sysuser sysuser) {
    	return sysuserMapper.delete(sysuser);
    }


}