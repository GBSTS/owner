package com.example.itmasys.security.service;

import com.example.itmasys.entity.Sysuser;
import com.example.itmasys.service.SysuserService;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MyUserDetailsService implements UserDetailsService {
    @Resource
    private SysuserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //这里可以可以通过username（登录时输入的用户名）然后到数据库中找到对应的用户信息，并构建成我们自己的UserInfo来返回。
        Sysuser sysUser = userService.findUserByName(username);
        //由于权限参数不能为空，所以这里先使用AuthorityUtils.commaSeparatedStringToAuthorityList方法模拟一个admin的权限，该方法可以将逗号分隔的字符串转换为权限集合。
        if(sysUser==null)//当用户不存在时
            throw new UsernameNotFoundException("用户不存在");
        //数据库中的密码是加密后的
        switch(sysUser.getRoleid()){
            case 1:
                return new User(username, sysUser.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_COMPANY"));
            case 2:
                return new User(username, sysUser.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_APPROVED"));
            case 3:
                return new User(username, sysUser.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_IMPLEMENTER"));
        }
        return new User(username, sysUser.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));
    }
}