package com.example.itmasys.dao;

import com.example.itmasys.entity.Rights;
import com.example.itmasys.entity.Roles;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RightsMapper {

	List<Rights> selectAllRigths();
	
}