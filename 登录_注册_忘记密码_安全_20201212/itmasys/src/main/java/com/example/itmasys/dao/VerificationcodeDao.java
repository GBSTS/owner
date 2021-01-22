package com.example.itmasys.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.example.itmasys.entity.Verificationcode;

@Mapper
public interface VerificationcodeDao {

    /**
     * 查询所有记录
     *
     * @return 返回集合，没有返回空List
     */
    List<Verificationcode> listAll();


    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 返回记录，没有返回null
     */
    Verificationcode getById(Integer id);

    /**
     * 根据email,code,nowdate查询
     *
     * @return 返回记录，没有返回null
     */
    Verificationcode getByEamilAndCodeAndNowdate(Verificationcode verificationcode);

    /**
     * 新增，插入所有字段
     *
     * @param verificationcode 新增的记录
     * @return 返回影响行数
     */
    int insert(Verificationcode verificationcode);

    /**
     * 新增，忽略null字段
     *
     * @param verificationcode 新增的记录
     * @return 返回影响行数
     */
    int insertIgnoreNull(Verificationcode verificationcode);

    /**
     * 修改，修改所有字段
     *
     * @param verificationcode 修改的记录
     * @return 返回影响行数
     */
    int update(Verificationcode verificationcode);

    /**
     * 修改，忽略null字段
     *
     * @param verificationcode 修改的记录
     * @return 返回影响行数
     */
    int updateIgnoreNull(Verificationcode verificationcode);

    /**
     * 删除记录
     *
     * @param verificationcode 待删除的记录
     * @return 返回影响行数
     */
    int delete(Verificationcode verificationcode);

}