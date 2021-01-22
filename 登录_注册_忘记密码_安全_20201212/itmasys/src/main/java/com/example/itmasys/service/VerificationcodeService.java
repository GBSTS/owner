package com.example.itmasys.service;

import com.example.itmasys.entity.Verificationcode;
import com.example.itmasys.dao.VerificationcodeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class VerificationcodeService {

    @Resource
    private VerificationcodeDao verificationcodeMapper;

    /**
     * 查询所有记录
     *
     * @return 返回集合，没有返回空List
     */
    public List<Verificationcode> listAll() {
        return verificationcodeMapper.listAll();
    }


    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 返回记录，没有返回null
     */
    public Verificationcode getById(Integer id) {
        return verificationcodeMapper.getById(id);
    }

    /**
     * 根据email,code,nowdate查询
     *
     * @return 返回记录，没有返回null
     */
    public Verificationcode getByEamilAndCodeAndNowdate(Verificationcode verificationcode) {
        return verificationcodeMapper.getByEamilAndCodeAndNowdate(verificationcode);
    }

    /**
     * 新增，插入所有字段
     *
     * @param verificationcode 新增的记录
     * @return 返回影响行数
     */
    public int insert(Verificationcode verificationcode) {
        return verificationcodeMapper.insert(verificationcode);
    }

    /**
     * 新增，忽略null字段
     *
     * @param verificationcode 新增的记录
     * @return 返回影响行数
     */
    public int insertIgnoreNull(Verificationcode verificationcode) {
        return verificationcodeMapper.insertIgnoreNull(verificationcode);
    }

    /**
     * 修改，修改所有字段
     *
     * @param verificationcode 修改的记录
     * @return 返回影响行数
     */
    public int update(Verificationcode verificationcode) {
        return verificationcodeMapper.update(verificationcode);
    }

    /**
     * 修改，忽略null字段
     *
     * @param verificationcode 修改的记录
     * @return 返回影响行数
     */
    public int updateIgnoreNull(Verificationcode verificationcode) {
        return verificationcodeMapper.updateIgnoreNull(verificationcode);
    }

    /**
     * 删除记录
     *
     * @param verificationcode 待删除的记录
     * @return 返回影响行数
     */
    public int delete(Verificationcode verificationcode) {
        return verificationcodeMapper.delete(verificationcode);
    }

}