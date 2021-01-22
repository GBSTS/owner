package com.example.demo.controller;

import com.example.demo.service.CompanyService;
import com.example.demo.service.UserService;
import com.example.demo.service.email.SendJunkMailService;
import com.example.demo.util.UtilClass;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class CurrencyHTMLController {
    @Resource
    private SendJunkMailService sendJunkMailService;
    @Resource
    private UserService userService;
    @Resource
    private CompanyService companyService;

    @RequestMapping("/")
    public String mr(){
        return "login";
    }

    @RequestMapping("/login")
    public String getLogin(){
        return "login";
    }

    @PostMapping("/submitLoginInfo")
    @ResponseBody
    public String dealLoginInfo(HttpSession session, @RequestParam("email") String email, @RequestParam("password")  String password, @RequestParam("identity") String identity){
        if(userService.isExist(email, identity)) {
            if (userService.isAlive(email, identity)) {
                if (userService.isCanLog(email, password, identity)) {
                    session.setAttribute("userId",userService.getUserIdByEmailAndIdentity(email, identity));
                    switch (identity) {
                        case "001":
//                            return "请在URL中输入http://localhost:8080/apply/mainshow";
                        return "redirect:/apply/mainshow";
                        case "002":
//                            return "请在URL中输入http://localhost:8080/approval/mainshow";
                        return "redirect:/approval/mainshow";
                    }
                } else {
                    return "邮箱或者密码错误！";
                }
            } else {
                return "用户没有激活,请登录个人邮箱进行激活！";
            }
        }else{
            return "用户不存在！";
        }
        return null;
    }

    @RequestMapping("/register")
    public String getRegister(){
        return "register";
    }

    @PostMapping("/submitRegisterInfo")
    @ResponseBody
    public String dealRegisterInfo(@RequestParam("account") String account,@RequestParam("password") String password,@RequestParam("email") String email,@RequestParam("phone") String phone,@RequestParam("identity") String identity,String reRegister){

        /**
         * 判断当前是否是否已经注册
         */
        if(reRegister.equals("False"))
            if(userService.isExist(email, identity)){
                if(userService.isAlive(email, identity))
                    return "该用户已经注册并且激活，如果您需要重新注册请联系开发人员";
                else
                    return "该用户已经注册但没有激活，请问是否重新注册";
            }
        String emailCode=UtilClass.getVerificationCode();
        String emailContent = "你现在正在注册项目管理系统的账号，如果是你本人的操作请点击下列链接完成验证。"+"http://127.0.0.1:8080/completeRegistrationVerification?email="+email+"&emailCode="+emailCode+"&identity="+identity+"。如果不是请忽略。";
        if(userService.isExist(email, identity)&&!userService.isAlive(email, identity))
            userService.reRegistration(account, password, email, phone, identity,emailCode);
        else
            userService.recordRegistration(account, password, email, phone, identity,emailCode);
        if(identity.equals("002"))
            companyService.insertApply(userService.getUserIdByEmailAndIdentity(email, identity));
        sendJunkMailService.sendJunkMail(email,"项目管理系统注册邮件",emailContent);
        return "redirect:/login";
    }

    @RequestMapping("/completeRegistrationVerification")
    public String completeRegistrationVerification(@RequestParam("email")String email,@RequestParam("emailCode") String emailCode,@RequestParam("identity") String identity){
        userService.validateAndActivateUsers(email, emailCode, identity);
        return "redirect:/login";
    }

}
