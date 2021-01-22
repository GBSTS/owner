package com.cexper.cexperiment.dao;

import com.cexper.cexperiment.model.Cexperimentnode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CexperimentnodeDao {   //实验手册表数据访问层

//    //添加
//    public  void insertcexperimentnode(@Param("cexperimentnode") Cexperimentnode cexperimentnode);
//    //删除
//    public void deletecexperimentnode(int id);
//    //修改 更新
//    public void updatecexperimentnode(@Param("cexperimentnode") Cexperimentnode cexperimentnode);
    //查询
    public  Cexperimentnode findCexperimentnodeById(int id);

}
