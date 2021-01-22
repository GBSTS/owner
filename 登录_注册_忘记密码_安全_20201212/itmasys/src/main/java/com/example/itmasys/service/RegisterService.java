package com.example.itmasys.service;

import com.example.itmasys.entity.Register;
import com.example.itmasys.dao.RegisterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RegisterService {

    @Resource
    private RegisterMapper registerMapper;

    /**
     * 查询所有记录
     *
     * @return 返回集合，没有返回空List
     */
    public List<Register> listAll() {
    	return registerMapper.listAll();
    }


    /**
     * 根据主键查询
     *
     * @param registerid
     * @return 返回记录，没有返回null
     */
    public Register getById(Integer registerid) {
    	return registerMapper.getById(registerid);
    }

    /**
     * 根据用户名查询
     *
     * @param username
     * @return 返回记录，没有返回null
     */
    public Register getByUsername(String username) {
        return registerMapper.getByUsername(username);
    }

    /**
     * 根据主键和验证码查询
     *
     * @param registerid
     * @param randomnumber
     * @return 返回记录，没有返回null
     */
    public Register getByRegisteridAndRandomnumber(Integer registerid, Integer randomnumber) {
        return registerMapper.getByRegisteridAndRandomnumber(registerid,randomnumber);
    }
	
    /**
     * 新增，插入所有字段
     *
     * @param register 新增的记录
     * @return 返回影响行数
     */
    public int insert(Register register) {
    	return registerMapper.insert(register);
    }
	
    /**
     * 新增，忽略null字段
     *
     * @param register 新增的记录
     * @return 返回影响行数
     */
    public int insertIgnoreNull(Register register) {
    	return registerMapper.insertIgnoreNull(register);
    }
	
    /**
     * 修改，修改所有字段
     *
     * @param register 修改的记录
     * @return 返回影响行数
     */
    public int update(Register register) {
    	return registerMapper.update(register);
    }
	
    /**
     * 修改，忽略null字段
     *
     * @param register 修改的记录
     * @return 返回影响行数
     */
    public int updateIgnoreNull(Register register) {
    	return registerMapper.updateIgnoreNull(register);
    }
	
    /**
     * 删除记录
     *
     * @param register 待删除的记录
     * @return 返回影响行数
     */
    public int delete(Register register) {
    	return registerMapper.delete(register);
    }
	
}