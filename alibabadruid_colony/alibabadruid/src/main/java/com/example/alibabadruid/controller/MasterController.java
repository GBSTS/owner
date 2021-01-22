package com.example.alibabadruid.controller;

import com.example.alibabadruid.entity.master.Rights;
import com.example.alibabadruid.service.master.MasterRightsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/master")
public class MasterController {
    @Resource
    MasterRightsService masterRightsService;

    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    public Integer insert(Rights rights){
        return masterRightsService.insertIgnoreNull(rights);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Integer delete(Integer rightid){
        Rights rights = new Rights();
        rights.setRightid(rightid);
        return masterRightsService.delete(rights);
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public Integer update(Rights rights){
        return masterRightsService.updateIgnoreNull(rights);
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public Rights query(Integer rightid){
        return masterRightsService.getById(rightid);
    }
}
