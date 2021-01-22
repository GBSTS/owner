package com.example.itmasys.dao;

import com.example.itmasys.entity.Roles;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RolesMapper {

	Roles selectByRoleid(Integer roleid);
	
}