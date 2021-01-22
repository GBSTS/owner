package com.example.yzm.controller;

import com.example.yzm.entity.IVerifyCode;
import com.example.yzm.service.verifycode.IVerifyCodeGenService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class MainController {

    @Resource
    private IVerifyCodeGenService iVerifyCodeGenService;

    @RequestMapping("/")
    public String test(){
        return "yzm";
    }

    @GetMapping("/IVerifyCode")
    public void verifyCode(HttpServletRequest request, HttpServletResponse response, Long timestamp) {
        try {
            //设置长宽
            IVerifyCode IVerifyCode = iVerifyCodeGenService.generate(80, 28);
            IVerifyCode.setExpireTime(timestamp);
            String code = IVerifyCode.getCode();
            //将VerifyCode绑定session
            request.getSession().setAttribute("VerifyCode", code);//当需要验证验证码是否正确时只需要从session对象中取出并进行对比
            //设置响应头
            response.setHeader("Pragma", "no-cache");
            //设置响应头
            response.setHeader("Cache-Control", "no-cache");
            //在代理服务器端防止缓冲
            response.setDateHeader("Expires", 0);
            //设置响应内容类型
            response.setContentType("image/jpeg");
            response.getOutputStream().write(IVerifyCode.getImgBytes());
            response.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
