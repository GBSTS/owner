package com.example.itmasys.security.handler;

import com.example.itmasys.security.bean.ResponseBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 处理Spring Security 中传出的AccessDeniedException
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        switch (accessDeniedException.getMessage()){
            case "你没有访问的权限!":
                response.sendError(response.SC_FORBIDDEN, "您的权限无法访问该资源");
                break;
            case "你访问的url不存在!":
                response.sendError(response.SC_NOT_FOUND, "你访问的url不存在");
                break;
            default:
                response.sendError(response.SC_INTERNAL_SERVER_ERROR, "出现未知情况，请联系程序员处理");
                break;
        }

//        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//        response.setContentType("application/json;charset=UTF-8");
//        PrintWriter out = response.getWriter();
//        ResponseBean info = new ResponseBean(500, accessDeniedException.getMessage(), null);
//        out.write(new ObjectMapper().writeValueAsString(info));
//        out.flush();
//        out.close();
    }
}
