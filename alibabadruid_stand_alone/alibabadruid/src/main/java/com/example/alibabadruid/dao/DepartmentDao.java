package com.example.alibabadruid.dao;

import com.example.alibabadruid.entity.Department;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Value;

@Mapper
public interface DepartmentDao {

    @Insert("INSERT INTO `Department` (`departmentName`) VALUES (#{departmentName})")
    public int addDepartment(Department department);

    @Delete("DELETE FROM  department where id=#{id}")
    public int deleteById(Integer id);

    @Update("UPDATE `Department` SET `departmentName`=#{departmentName} WHERE (`id`=#{id})")
    public int updateDepartment(Department department);

    @Select("SELECT  * from department where id=#{id}")
    public Department queryById(@Param("id") Integer id);


}
