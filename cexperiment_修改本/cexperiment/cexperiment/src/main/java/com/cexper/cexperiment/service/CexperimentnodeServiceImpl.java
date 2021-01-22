package com.cexper.cexperiment.service;

import com.cexper.cexperiment.dao.CexperimentnodeDao;
import com.cexper.cexperiment.model.Cexperimentnode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CexperimentnodeServiceImpl implements CexperimentnodeService {

    @Resource
    private CexperimentnodeDao cexperimentnodeDao;

    @Override
    public Cexperimentnode findCexperimentnodeById(int id) {
        return cexperimentnodeDao.findCexperimentnodeById(id);
    }
}
