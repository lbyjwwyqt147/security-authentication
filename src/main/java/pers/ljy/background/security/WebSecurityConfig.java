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
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/resources", "/login", "/home/**").permitAll()//访问：这些路径 无需登录认证权限
                .anyRequest().authenticated() //其他所有资源都需要认证，登陆后访问
                //.antMatchers("/resources").hasAuthority("ADMIN") //登陆后之后拥有“ADMIN”权限才可以访问/hello方法，否则系统会出现“403”权限不足的提示
         .and()
                .formLogin()
                .loginPage("/")//指定登录页是”/”
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
