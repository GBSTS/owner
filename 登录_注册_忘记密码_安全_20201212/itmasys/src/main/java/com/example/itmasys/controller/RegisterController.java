package com.example.itmasys.controller;

import com.example.itmasys.entity.Personinfo;
import com.example.itmasys.entity.Register;
import com.example.itmasys.entity.Sysuser;
import com.example.itmasys.security.facade.IAuthenticationFacade;
import com.example.itmasys.service.PersoninfoService;
import com.example.itmasys.service.RegisterService;
import com.example.itmasys.service.RoleService;
import com.example.itmasys.service.SysuserService;
import com.example.itmasys.service.email.SendJunkMailService;
import com.example.itmasys.util.Encoder;
import com.example.itmasys.util.GeneRandNum;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class RegisterController {

    @Resource
    private IAuthenticationFacade authenticationFacade;
    @Resource
    private RoleService roleService;
    @Resource
    private PersoninfoService personinfoService;
    @Resource
    private RegisterService registerService;
    @Resource
    private SendJunkMailService sendJunkMailService;
    @Resource
    private SysuserService sysuserService;

    @RequestMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/submitregister")
    public String submitregister(String username, String password, String phone, String email, String rolename){
        Integer roleid = roleService.getByRolename(rolename).getRoleid();
        Integer randomnumber = GeneRandNum.generating_random_numbers();
        String passwordEncoder = Encoder.bCryptPasswordEncoder(password);
        registerService.insertIgnoreNull(new Register(username,passwordEncoder, phone, email, roleid,randomnumber));
        Integer registerid = registerService.getByUsername(username).getRegisterid();
        String sendStr="你现在正在注册项目管理系统的账号，如果是你本人的操作请点击下列链接完成验证。"+"http://127.0.0.1:8080/registrationactivation?registerid="+registerid+"&randomnumber="+randomnumber+"。如果不是请忽略。";
        sendJunkMailService.sendJunkMail(email,"项目管理系统注册激活",sendStr);
        return "redirect:/login";
    }

    @RequestMapping("/registrationactivation")
    public String registrationactivation(Integer registerid, Integer randomnumber){
        Register register = registerService.getByRegisteridAndRandomnumber(registerid, randomnumber);
        if(register==null)
            return "redirect:/login";
        sysuserService.insertIgnoreNull(new Sysuser(register.getUsername(),register.getPassword(),register.getRoleid()));
        Sysuser sysuser = sysuserService.findUserByName(register.getUsername());
        personinfoService.insertIgnoreNull(new Personinfo(sysuser.getUserid(),register.getUsername(),register.getPhone(),register.getEmail(),null,null,register.getRoleid()));
        registerService.delete(register);
        return "redirect:/login";
    }

}
