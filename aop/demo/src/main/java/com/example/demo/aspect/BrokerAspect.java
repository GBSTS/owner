package com.example.demo.aspect;
 
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
 
/**
* @desc: 经纪人切面
* @author: CSH
**/
@Aspect
@Component
public class BrokerAspect {

    /**
     * 执行顺序
     * @Around->@Before->ProceedingJoinPoint对象.proceed()->@Around->@After->@AfterReturning
     * 其中ProceedingJoinPoint对象为当前执行的方法对象，proceed()函数为执行方法对象定对应的方法
     */

    /**
     * 定义切入点，切入点为com.example.demo.aop.AopController中的Curry函数
     *通过@Pointcut注解声明频繁使用的切点表达式
     */
    //表示当前类下的全部函数
    //@Pointcut("execution(public * com.example.demo.controller.AopController.*(..))")
    @Pointcut("execution(public * com.example.demo.controller.AopController.Curry(..))")
    public void BrokerAspect1(){}

    /**
    * @description  在连接点执行之前执行的通知
    */
    @Before("BrokerAspect1()")
    public void doBeforeGame(){
        System.out.println("经纪人正在处理球星赛前事务！");
    }
 
    /**
     * @description  在连接点执行之后执行的通知（返回通知和异常通知的异常）
     */
    @After("BrokerAspect1()")
    public void doAfterGame(){
        System.out.println("经纪人为球星表现疯狂鼓掌！");
    }

    /**
     * @description  在连接点执行之后执行的通知（返回通知）
     */
    @AfterReturning("BrokerAspect1()")
    public void doAfterReturningGame(){
        System.out.println("返回通知：经纪人为球星表现疯狂鼓掌！");
    }

    /**
     * @description  在连接点执行之后执行的通知（异常通知）
     */
    @AfterThrowing("BrokerAspect1()")
    public void doAfterThrowingGame(){
        System.out.println("异常通知：球迷要求退票！");
    }

    /**
     * 定义切入点，切入点为com.example.demp.controller.AopController中的Durant函数
     * 通过@pointcut注解声明频繁使用的切点表达式
     */
    @Pointcut("execution(public * com.example.demo.controller.AopController.Durant(..))")
    public void BrokerAspect2(){}

    /**
     * @description  使用环绕通知
     */
    @Around("BrokerAspect2()")
    public void doAroundGame(ProceedingJoinPoint pjp) throws Throwable {
        try{
            //相当于@Before
            System.out.println("经纪人正在处理球星赛前事务！");
            pjp.proceed();
            //相当于@After
            System.out.println("返回通知：经纪人为球星表现疯狂鼓掌！");
        }
        catch(Throwable e){
            System.out.println("异常通知：球迷要求退票！");
        }
    }

    /**
     * 定义切入点，切入点为com.example.demo.aop.AopController中的Durant函数
     *通过@Pointcut注解声明频繁使用的切点表达式
     */
    @Pointcut("execution(public * com.example.demo.controller.AopController.Durant3(int,String)) && args(point,param))")
    public void BrokerAspect3(int point, String param){

    }

    /**
     * @description  使用环绕通知
     */
    @Around("BrokerAspect3(point,param)")
    public void doAroundGameData(ProceedingJoinPoint pjp,int point, String param) throws Throwable {
        try{
            System.out.println("球星上场前热身！");
            pjp.proceed();
            System.out.println("球星本场得到" + point + "分" + param );
        }
        catch(Throwable e){
            System.out.println("异常通知：球迷要求退票！");
        }
    }
}