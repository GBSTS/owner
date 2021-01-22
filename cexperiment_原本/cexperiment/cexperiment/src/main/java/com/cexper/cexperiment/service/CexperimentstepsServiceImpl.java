package com.cexper.cexperiment.service;

import com.cexper.cexperiment.dao.CexperimentstepsDao;
import com.cexper.cexperiment.model.Cexperimentsteps;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CexperimentstepsServiceImpl implements  CexperimentstepsService{

    @Resource
    private CexperimentstepsDao cexperimentstepsDao;

    @Override
    public Cexperimentsteps findCexperimentstepsById(int id) {
        return cexperimentstepsDao.findCexperimentstepsById(id);
    }

    @Override
    public List<Cexperimentsteps> findCexperimentstepsByCeid(int ceid) {
        return cexperimentstepsDao.findCexperimentstepsByCeid(ceid);
    }
}
