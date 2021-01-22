package com.example.demo.aspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Controller;

@Controller
@Aspect
public class TestAspect {
    @Pointcut("execution(public * com.example.demo.controller.AopController.Harden(..))")
    public void pointcut(){}

    @AfterThrowing(value = "pointcut()", throwing = "ex")
    public void afterthrowing(NullPointerException ex){
        System.out.println("throwing exception");
        System.out.println(ex);
    }
}
