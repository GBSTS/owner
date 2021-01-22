package com.example.itmasys.security;

import com.example.itmasys.security.handler.MyAccessDeniedHandler;
import com.example.itmasys.security.handler.MyAuthenticationFailureHandler;
import com.example.itmasys.security.handler.MyAuthenticationSucessHandler;
import com.example.itmasys.security.service.MyUserDetailsService;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import javax.annotation.Resource;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Resource
    MyUserDetailsService myUserDetailsService;
    @Resource
    MyAccessDecisionManager myAccessDecisionManager;
    @Resource
    MySecurityMetadataSource mySecurityMetadataSource;
    @Resource
    MyAccessDeniedHandler myAccessDeniedHandler;
    @Resource
    private MyAuthenticationSucessHandler myAuthenticationSucessHandler;
    @Resource
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //加入数据库验证类，下面的语句实际上在验证链中加入了一个DaoAuthenticationProvider
        auth.userDetailsService(myUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //数据库动态鉴权
                .authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setSecurityMetadataSource(mySecurityMetadataSource);
                        object.setAccessDecisionManager(myAccessDecisionManager);
                        return object;
                    }
                })
                .and()
                .formLogin().loginPage("/login").loginProcessingUrl("/submitlogin")
                .successHandler(myAuthenticationSucessHandler)//配置登录成功
                .failureHandler(myAuthenticationFailureHandler)//配置登录失败
                .permitAll()
//                1.自定义参数名称，与login.html中的参数对应
                .usernameParameter("username").passwordParameter("password")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login").permitAll()
                .and()
                .csrf().disable()
                .exceptionHandling().accessDeniedHandler(myAccessDeniedHandler);

    }
}
