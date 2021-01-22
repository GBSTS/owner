package com.example.itmasys.controller;

import com.example.itmasys.entity.Sysuser;
import com.example.itmasys.entity.Verificationcode;
import com.example.itmasys.security.facade.IAuthenticationFacade;
import com.example.itmasys.service.*;
import com.example.itmasys.service.email.SendJunkMailService;
import com.example.itmasys.util.Encoder;
import com.example.itmasys.util.GeneRandNum;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;

@Controller
public class ForgetPasswordController {

    @Resource
    private SysuserService sysuserService;
    @Resource
    private VerificationcodeService verificationcodeService;
    @Resource
    private SendJunkMailService sendJunkMailService;

    @RequestMapping("/forgetpassword")
    public String forgetpassword(){
        return "forgetpassword";
    }

    @PostMapping("/submitforgetpassword")
    @ResponseBody
    public String submitforgetpassword(String username, String email, String yzm, String password){
        Verificationcode verificationcode = verificationcodeService.getByEamilAndCodeAndNowdate(new Verificationcode(email,Integer.parseInt(yzm),new Integer(new Date().getTime()/1000+"")));
        if(verificationcode==null){
            return "重置密码失败";
        }
        Sysuser sysuser = sysuserService.findUserByName(username);
        sysuser.setPassword(Encoder.bCryptPasswordEncoder(password));
        sysuserService.updateIgnoreNull(sysuser);
        verificationcodeService.delete(verificationcode);
        return "重置密码成功";
    }
    @PostMapping("/getforgetpasswordnumber")
    @ResponseBody
    public String getforgetpasswordnumber(String email){
        Integer yzm = GeneRandNum.generating_random_numbers();
        String sendStr = "您现在正在重置项目管理系统的账号密码，这是你本次重置密码的验证码："+yzm+"。有效时间为5分钟。如果不是您本人的操作，请忽略。";
        sendJunkMailService.sendJunkMail(email,"项目管理系统-重置密码",sendStr);
        verificationcodeService.insertIgnoreNull(new Verificationcode(email,yzm,new Integer(new Date().getTime()/1000+"")));
        return "验证码已经发送，请注意查看。";
    }

}
