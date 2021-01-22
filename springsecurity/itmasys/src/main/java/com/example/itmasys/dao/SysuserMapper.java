package com.example.itmasys.dao;

import com.example.itmasys.security.bean.SysuserBean;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysuserMapper {

	SysuserBean selectByUsername(String username);
	
}