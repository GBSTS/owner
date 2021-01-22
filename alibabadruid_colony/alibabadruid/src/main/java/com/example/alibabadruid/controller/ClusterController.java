package com.example.alibabadruid.controller;

import com.example.alibabadruid.dao.cluster.ClusterRightsDao;
import com.example.alibabadruid.entity.cluster.Rights;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/cluster")
public class ClusterController {
    @Resource
    ClusterRightsDao clusterRightsDao;

    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    public Integer insert(Rights rights){
        return clusterRightsDao.insertIgnoreNull(rights);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Integer delete(Integer rightid){
        Rights rights = new Rights();
        rights.setRightid(rightid);
        return clusterRightsDao.delete(rights);
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public Integer update(Rights rights){
        return clusterRightsDao.updateIgnoreNull(rights);
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public Rights query(Integer rightid){
        return clusterRightsDao.getById(rightid);
    }
}
