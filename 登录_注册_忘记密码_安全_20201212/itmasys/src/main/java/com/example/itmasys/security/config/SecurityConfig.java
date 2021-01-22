package com.example.itmasys.security.config;

import com.example.itmasys.security.handler.MyAuthenticationFailureHandler;
import com.example.itmasys.security.handler.MyAuthenticationSucessHandler;
import com.example.itmasys.security.service.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    MyUserDetailsService myUserDetailsService;

    @Resource
    private AuthenticationProvider provider;  //注入我们自己的AuthenticationProvider

    @Resource
    private MyAuthenticationSucessHandler myAuthenticationSucessHandler;

    @Resource
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 关闭csrf防护
                .csrf().disable()
                .headers().frameOptions().disable();
        http
                //登录处理
                .formLogin() //表单方式，或httpBasic
                .loginPage("/login")//设置登录链接
                .loginProcessingUrl("/submitlogin")//设置登录数据传输接口
                .successHandler(myAuthenticationSucessHandler)//配置登录成功
                .failureHandler(myAuthenticationFailureHandler)//配置登录失败
                .permitAll();//表示不需要权限
        http
                .authorizeRequests() // 授权配置
                //无需权限访问
                .antMatchers( "/**/*.*").permitAll()//表示不需要权限
                .antMatchers( "/register","/submitregister","/registrationactivation").permitAll()
                .antMatchers( "/forgetpassword","/submitforgetpassword","/getforgetpasswordnumber").permitAll()
                .antMatchers("/company/**").hasRole("COMPANY")//公司代表
                .antMatchers("/approved/**").hasRole("APPROVED")//审批人员
                .antMatchers("/implementer/**").hasRole("IMPLEMENTER")//工程师
                //其他接口需要登录后才能访问
                .anyRequest().authenticated();

        //session管理
        //session失效后跳转
        http.sessionManagement().invalidSessionUrl("/login");

        //单用户登录，如果有一个登录了，同一个用户在其他地方不能登录
        http.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true);
        //退出删除cookie
        http.logout()
                .permitAll()
                .logoutUrl("/logout")  //执行注销的url
                .invalidateHttpSession(true) // 指定是否在注销时让httpSession无效
                .deleteCookies("JESSIONID")  // 清除cookie
                .logoutSuccessUrl("/login"); // 注销成功后跳转的url

        //解决中文乱码问题
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter, CsrfFilter.class);

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                //用户认证处理
                .userDetailsService(myUserDetailsService)
                //密码处理
                .passwordEncoder(passwordEncoder());
        auth
                .authenticationProvider(provider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {//密码加密器
        return new BCryptPasswordEncoder();
    }

}
