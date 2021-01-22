package com.example.itmasys.security.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

/**
 * 登录成功后根据登录的角色确定跳转的页面
 */
@Component
public class MyAuthenticationSucessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        //当前用户拥有的角色
        Collection<GrantedAuthority> grantedAuthorityCollection = (Collection<GrantedAuthority>)authentication.getAuthorities();
        Iterator<GrantedAuthority> grantedAuthorityIterator = grantedAuthorityCollection.iterator();
        while(grantedAuthorityIterator.hasNext()){
            GrantedAuthority grantedAuthority = grantedAuthorityIterator.next();
            switch (grantedAuthority.toString()){//获取当前Authority包含的角色
                case "ROLE_ADMIN":
                    response.sendRedirect("/test");
                    break;
                case "ROLE_APPROVED":
                    response.sendRedirect("/approved/test");
                    break;
                case "ROLE_COMPANY":
                    response.sendRedirect("/company/test");
                    break;
                case "ROLE_IMPLEMETER":
                    response.sendRedirect("/implemeter/test");
                    break;
                case "ROLE_USER":
                    response.sendRedirect("/test");
                    break;
            }
        }
    }
}