package pers.ljy.background.security;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;


/***
 * 配置类
 * 
 * @EnableWebSecurity: 禁用Boot的默认Security配置，配合@Configuration启用自定义配置（需要扩展WebSecurityConfigurerAdapter）
 * @EnableGlobalMethodSecurity(prePostEnabled = true): 启用Security注解，例如最常用的@PreAuthorize
 * configure(HttpSecurity): Request层面的配置，对应XML Configuration中的<http>元素
 * configure(WebSecurity): Web层面的配置，一般用来配置无需安全检查的路径
 * configure(AuthenticationManagerBuilder): 身份验证配置，用于注入自定义身份验证Bean和密码校验规则
 *
 * @author ljy
 *
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启方法级别的权限注解
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	 @Autowired
     private MyUserDetailService myUserDetailService;
	 @Autowired
	 private MyFilterSecurityInterceptor myFilterSecurityInterceptor;
	// @Resource
	// private SessionRegistry sessionRegistry;
	 
	 
     @Bean
     public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
     }
     

     @Override
     protected void configure(HttpSecurity http) throws Exception {
    	 
    	 /**
    	  * 通过项目访问的配置(同域)
    	  */
/*    	 http.csrf().disable()
    	   .authorizeRequests()
           .antMatchers("/","index","/login","/hello","/css/**","/js/**","/security/api/v1/users/logins","/security/api/v1/users/signins","/security/api/v1/resourceMenus/*","/security/api/v1/userRolers/y")//允许访问
           .permitAll()
           .anyRequest().authenticated()
           .and()
	         .exceptionHandling().authenticationEntryPoint(myAuthenticationFailureHandler()) 
           .and()
           .formLogin()
           .loginPage("/login")// 登陆
           .defaultSuccessUrl("/hello") //登陆成功后跳转
           .permitAll()
           .and()
           .logout()
           .permitAll();
    	   http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);*/
    	 
    	   
    	   
    	   /**
    	    * 前后分离 跨越访问配置
    	    */
    	// http.addFilterBefore(simpleCORSFilter, ChannelProcessingFilter.class); 跨域

    	 http.csrf().disable()
		         .authorizeRequests()
		         .antMatchers("/security/api/v1/users/logins","/security/api/v1/users/signins","/security/api/v1/resourceMenus/*").permitAll()//访问：这些路径 无需登录认证权限
		         .anyRequest().authenticated() //其他所有资源都需要认证，登陆后访问
		         //.antMatchers("/resources").hasAuthority("ADMIN") //登陆后之后拥有“ADMIN”权限才可以访问/hello方法，否则系统会出现“403”权限不足的提示
		         .and().exceptionHandling().accessDeniedHandler(myAccessDeniedHandler()) //无权限,权限不足访问 使用myAccessDeniedHandler()做业务处理。
		         .and()
		         .exceptionHandling().authenticationEntryPoint(myLoginAuthenticationFailureHandler())    //未登录状态(没有登录)下 使用myLoginAuthenticationFailureHandler()做业务处理。
		         
		  .and()
		         .formLogin()
		         .loginProcessingUrl("/security/api/v1/users/logins")
		         .usernameParameter("userName") //登录用户
	             .passwordParameter("userPwd") //登录密码
	             .defaultSuccessUrl("/")  //登录成功路径
	             .failureUrl("/") //登录失败路径
		         .loginPage("/")//指定登录页是”/”
		         .permitAll()
		         .successHandler(loginSuccessHandler()) //登录成功后可使用loginSuccessHandler()做业务处理，可选。
		         .failureHandler(loginFailureHandler()) //登录失败 业务处理
		  .and()
		         .logout()
		         .logoutUrl("/logout")
		         .logoutSuccessUrl("/") //退出登录后的默认网址是”/home”
		         
		         .permitAll()
		         .invalidateHttpSession(true)
		         .and()
		         .rememberMe()//登录后记住用户，下次自动登录,数据库中必须存在名为persistent_logins的表
		         .tokenValiditySeconds(1209600);
		  http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
		  //session 失效跳转 参数为要跳转到的页面url
	    //  http.sessionManagement().invalidSessionStrategy(invalidSessionStrategy);
	      //只允许一个用户登录,如果同一个帐号两次登录，那么第一个账户将被提下线，跳转到登录页面
	    //  http.sessionManagement().sessionAuthenticationStrategy(mySessionAuthenticationFailureHandler());

    	 
    	 
    	 
    /*    http.csrf().disable()   //关闭 csrf
                .authorizeRequests()
                //.antMatchers( "/security/api/v1/users/logins","/security/api/v1/users/signins","/security/api/v1/users","/security/api/v1/roles","/security/api/v1/userRolers","/security/api/v1/userRolers/y","/security/api/v1/userRolers/n","/security/api/v1/resourceMenus","/security/api/v1/resourceMenus/*","/security/api/v1/roleMenus","/security/api/v1/roleMenus/*")
                .antMatchers( "/security/api/v1/users/logins","/security/api/v1/users/signins","/security/api/v1/resourceMenus/*")
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
                .defaultSuccessUrl("/")  //登录成功路径
                .failureUrl("/") //登录失败路径
                .permitAll()
               // .successHandler(loginSuccessHandler()) //登录成功后可使用loginSuccessHandler()存储用户信息，可选。
         .and()
                .logout()
                .logoutUrl("/")
                .logoutSuccessUrl("/") //退出登录后的默认网址是”/home”
                .permitAll()
                .invalidateHttpSession(true)
                .and()
                .rememberMe()//登录后记住用户，下次自动登录,数据库中必须存在名为persistent_logins的表
                .tokenValiditySeconds(1209600);
        //在正确的位置添加我们自定义的过滤器 
        http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
        //session 失效跳转 参数为要跳转到的页面url
        http.sessionManagement().invalidSessionUrl("/login");
        //只允许一个用户登录,如果同一个帐号两次登录，那么第一个账户将被提下线，跳转到登录页面
        http.sessionManagement().maximumSessions(1).expiredUrl("/login");*/

     }

     @Autowired
     public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //指定密码加密所使用的加密器为 bCryptPasswordEncoder()
        //需要将密码加密后写入数据库
    	 auth.userDetailsService(myUserDetailService).passwordEncoder(bCryptPasswordEncoder()); 
    	 auth.eraseCredentials(false);
     }


     /**
      * 注册登录成功的bean
      * @return
      */
     @Bean
     public MyLoginSuccessHandler loginSuccessHandler() {
        return new MyLoginSuccessHandler();
     }
	
     /**
      * 注册登录失败的bean
      * @return
      */
     @Bean
     public MyLoginFailureHandler loginFailureHandler() {
        return new MyLoginFailureHandler();
     }
	
     
     /**
      * 注册未登录的bean
      * @return
      */
     @Bean
     public MyLoginAuthenticationFailureHandler myLoginAuthenticationFailureHandler() {
        return new MyLoginAuthenticationFailureHandler("/");
     }
     
     /**
      * 注册session认证失败的bean
      * @return
      */
     @Bean
     public ConcurrentSessionControlAuthenticationStrategy mySessionAuthenticationFailureHandler() {
    	 /*ConcurrentSessionControlAuthenticationStrategy concurrentSession = new MySessionAuthenticationFailureHandler(sessionRegistry);
    	 concurrentSession.setExceptionIfMaximumExceeded(true);
    	 return concurrentSession;*/
    	 return null;
     }
     
     /**
      * 注册认证权限不足的bean
      * @return
      */
     @Bean
     public AccessDeniedHandler myAccessDeniedHandler(){
    	 return new MyAccessDeniedHandler();
     }
     
     
     
     
}
