package com.cexper.cexperiment.dao;

import  com.cexper.cexperiment.model.Cexperimentsteps;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CexperimentstepsDao {

    //    //添加
//    public  void insertCexperimentsteps(@Param("cexperimentsteps") Cexperimentsteps cexperimentsteps);
//    //删除
//    public void deleteCexperimentsteps(int id);
//    //修改 更新
//    public void updateCexperimentsteps(@Param("cexperimentsteps") Cexperimentsteps cexperimentsteps);
    //查询   根据id查找唯一的实验步骤记录
    public Cexperimentsteps findCexperimentstepsById(int id);

    //查询   根据ceid查找唯一的实验步骤记录
    public List<Cexperimentsteps> findCexperimentstepsByCeid(int ceid);
}
