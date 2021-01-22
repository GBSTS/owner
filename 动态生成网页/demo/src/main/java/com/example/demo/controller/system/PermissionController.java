package com.example.demo.controller.system;

import com.example.demo.pojo.BaseAdminUser;
import com.example.demo.service.AdminPermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("permission")
public class PermissionController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AdminPermissionService permissionService;

    /**
     *
     * 功能描述: 获取登陆用户的权限
     *
     * @param:
     * @return:
     * @auther: youqing
     * @date: 2018/12/4 9:48
     */
    @GetMapping("getUserPerms")
    @ResponseBody
    public Map<String, Object> getUserPerms(){
        logger.info("获取登陆用户的权限");
        Map<String, Object> data = new HashMap<>();
//        BaseAdminUser user = (BaseAdminUser) SecurityUtils.getSubject().getPrincipal();
        BaseAdminUser user = new BaseAdminUser(1,"admin","admin",1,"13411182215","2018-11-22 10:57:33",1);
//        BaseAdminUser user = new BaseAdminUser(2,"jackson","jackson",2,"19563648695","2018-11-22 10:57:33",1);
        data = permissionService.getUserPerms(user);
        return data;
    }
}
