package com.example.itmasys.security.service;

import com.example.itmasys.dao.RolesMapper;
import com.example.itmasys.dao.SysuserMapper;
import com.example.itmasys.entity.Roles;
import com.example.itmasys.security.bean.SysuserBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxb on 2018/10/23 0023.
 * UserDetailsService的实现类，用于在程序中引入一个自定义的AuthenticationProvider，实现数据库访问模式的验证
 *
 */
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Resource
    SysuserMapper mapper;
    @Resource
    RolesMapper rolesMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysuserBean userBean = mapper.selectByUsername(username);
        if (userBean == null) {
            throw new UsernameNotFoundException("数据库中无此用户！");
        }
        String string[] = userBean.getRoleid().split(",");
        List<Roles> rolesList = new ArrayList<>();
        for(String str : string){
            Roles roles = rolesMapper.selectByRoleid(Integer.parseInt(str));
            rolesList.add(roles);
        }
        userBean.setRolesList(rolesList);

        return userBean;
    }
}
