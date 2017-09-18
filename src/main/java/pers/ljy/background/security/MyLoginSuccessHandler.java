package pers.ljy.background.security;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import pers.ljy.background.jwt.JWTUserDetails;
import pers.ljy.background.jwt.JwtTokenUtils;
import pers.ljy.background.share.redis.RedisService;
import pers.ljy.background.share.result.ApiResultCode;
import pers.ljy.background.share.result.ApiResultView;
import pers.ljy.background.share.utils.SecurityReturnJson;

/***
 * 文件名称: MyLoginSuccessHandler.java
 * 文件描述: 用户登录成功后需要做的业务操作
 * 
 * 公 司: 
 * 内容摘要: 
 * 其他说明:
 * 完成日期:2017年06月12日 
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
public class MyLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(MyLoginSuccessHandler.class); 
	
	
	@Resource
    private SessionRegistry sessionRegistry;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private JwtTokenUtils jwtTokenUtils;
	
	
	
	@Value("${jwt.header}")
	private String tokenHeader;

	@Value("${jwt.tokenHead}")
	private String tokenHead;
	
	@Value("${jwt.expiration}")
	private Long expiration;
	
	/**
	 * 登陆成功后会调用这里
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		//获得授权后可得到用户信息   如果不使用jwt 则取消注释 
		//User userDetails =  (User) authentication.getPrincipal();
      
		//使用jwt 
		JWTUserDetails userDetails =  (JWTUserDetails) authentication.getPrincipal();
		
		//将身份 存储到SecurityContext里
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
		
        request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
        request.getSession().setAttribute("SESSION_OPERATOR", userDetails);
        
        String tokenRedisKey = "security-jwt-"+userDetails.getUserId()+"-token";
        
        //输出登录提示信息
        LOGGER.info("用户：" + userDetails.getUsername()+ " 成功登录系统.");

        LOGGER.info("请求 IP :" + ((WebAuthenticationDetails) authentication
                .getDetails()).getRemoteAddress());
        
        String sessionId = request.getSession().getId();
        
        LOGGER.info("sessionId :" + request.getSession().getId());
        
        //当用户登录验证成功后 注册session(踢出在线用户时要用)
        sessionRegistry.registerNewSession(sessionId,authentication.getPrincipal());
        
        ConcurrentMap <String, String> map = new ConcurrentHashMap<>();
        //检查当前登录人的 token 是否存在     如果token已经在了 就返回以前的token  如果不存在才返回新的token  这样可以保证一个uid只能有一个有效token，不会出现多个token同时指向一个uid。
        String token = (String) redisService.get(tokenRedisKey);
        if(StringUtils.isNotBlank(token)){
        	
        }else {
        	// 登陆成功后返回一个加密token  以后通过token进行权限验证
            token = tokenHead+jwtTokenUtils.generateAccessToken(userDetails);
            
        	 //登录成功后将用户的uid 和token 存放在redis中 或者数据库中   我这里存放到redis中
            redisService.set(tokenRedisKey, token, expiration);
		}
        
      
        LOGGER.info(tokenHeader+" :" + token);
        map.put("token",token);
        map.put("SESSION", sessionId);
        Cookie sessionCookie = new Cookie("SESSION",sessionId);
        Cookie tokenCookie = new Cookie("x-auth-token",token);
        response.addCookie(sessionCookie);
        response.addCookie(tokenCookie);
        response.addHeader("x-auth-token",token);
        
        
       
        
        
        ApiResultView view = new ApiResultView(ApiResultCode.SUCCESS.getCode(), ApiResultCode.SUCCESS.getMsg(), map);
        SecurityReturnJson.writeJavaScript(response, view);
        
		// super.onAuthenticationSuccess(request, response, authentication);
	}
	

}
