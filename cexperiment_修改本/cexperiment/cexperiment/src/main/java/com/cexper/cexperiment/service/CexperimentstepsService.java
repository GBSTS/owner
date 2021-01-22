package com.cexper.cexperiment.service;


import com.cexper.cexperiment.model.Cexperimentsteps;

import java.util.List;

public interface CexperimentstepsService {
    //根据ID查询实验手册表记录
    public Cexperimentsteps findCexperimentstepsById(int id);

    //根据CEID查询实验手册表记录（可能有多条）
    public List<Cexperimentsteps> findCexperimentstepsByCeid(int ceid);

}
