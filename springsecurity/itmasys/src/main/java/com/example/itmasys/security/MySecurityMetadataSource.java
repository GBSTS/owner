package com.example.itmasys.security;

import com.example.itmasys.dao.RightsMapper;
import com.example.itmasys.entity.Rights;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * Created by wxb on 2018/10/26 0026.
 * 自定义的元数据源类，用来提供鉴权过程中，访问资源所需的角色
 */
@Component
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Resource
    RightsMapper rightsMapper;
    //本方法返回访问资源所需的角色集合
    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        //从object中得到需要访问的资源，即网址
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        System.out.println("--------------------------");
        System.out.println("requestUrl:"+requestUrl);
        //从数据库中得到所有资源，以及对应的角色
        List<Rights> rightsList = rightsMapper.selectAllRigths();
        for (Rights rights : rightsList) {
            //首先进行地址匹配
            if (antPathMatcher.match(rights.getUrl(), requestUrl)
                    && rights.getRightsArray().length > 0) {
                return SecurityConfig.createList(rights.getRightsArray());
            }
        }
        //匹配不成功返回一个特殊的ROLE_NONE
        return SecurityConfig.createList("ROLE_NONE");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
