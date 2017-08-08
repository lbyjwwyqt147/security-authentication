package pers.ljy.background.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/***
 * 配置类
 * @author ljy
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	 @Autowired
     private MyUserDetailService myUserDetailService;
	 @Autowired
	 private MyFilterSecurityInterceptor myFilterSecurityInterceptor;
	 
     @Bean
     public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
     }

     @Override  
     public AuthenticationManager authenticationManagerBean() throws Exception {  
        return super.authenticationManagerBean();  
     }  
	    

     @Override
     protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()   //关闭 csrf
                .authorizeRequests()
                .antMatchers( "/security/api/v1/users/logins","/security/api/v1/users/signins","/security/api/v1/users","/security/api/v1/roles","/security/api/v1/userRolers","/security/api/v1/userRolers/y","/security/api/v1/userRolers/n","/security/api/v1/resourceMenus","/security/api/v1/resourceMenus/*","/security/api/v1/roleMenus","/security/api/v1/roleMenus/*")
                .permitAll()//访问：这些路径 无需登录认证权限
                .anyRequest().authenticated() //其他所有资源都需要认证，登陆后访问
                //.antMatchers("/resources").hasAuthority("ADMIN") //登陆后之后拥有“ADMIN”权限才可以访问/hello方法，否则系统会出现“403”权限不足的提示
         .and()
                .formLogin()
                .loginPage("/")//指定登录页是”/”
                //.loginProcessingUrl("/security/api/v1/users/logins") //登录处理url
                .loginProcessingUrl("/users/logins")
                .usernameParameter("userName") //登录用户
                .passwordParameter("userPwd") //登录密码
                .defaultSuccessUrl("http://localhost:63342/access/pages/index.html?_ijt=n5afdotmh10bc7i9lg053v3h0r")  //登录成功路径
                .failureUrl("/") //登录失败路径
                .permitAll()
                .successHandler(loginSuccessHandler()) //登录成功后可使用loginSuccessHandler()存储用户信息，可选。
         .and()
                .logout()
                .logoutUrl("/admin/logout")
                .logoutSuccessUrl("/") //退出登录后的默认网址是”/home”
                .permitAll()
                .invalidateHttpSession(true)
                .and()
                .rememberMe()//登录后记住用户，下次自动登录,数据库中必须存在名为persistent_logins的表
                .tokenValiditySeconds(1209600);
        //在正确的位置添加我们自定义的过滤器 
        http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
        //session 失效跳转 参数为要跳转到的页面url
        http.sessionManagement().invalidSessionUrl("/");
        //只允许一个用户登录,如果同一个帐号两次登录，那么第一个账户将被提下线，跳转到登录页面
        http.sessionManagement().maximumSessions(1).expiredUrl("/");

     }

     @Autowired
     public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //指定密码加密所使用的加密器为passwordEncoder()
        //需要将密码加密后写入数据库
        auth.userDetailsService(myUserDetailService).passwordEncoder(passwordEncoder());  
        auth.eraseCredentials(false); 
     }

     @Bean
     public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
     }

     @Bean
     public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
     }
	
}
