package pers.ljy.background.security;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import pers.ljy.background.jwt.JWTUserDetails;
import pers.ljy.background.jwt.JwtTokenUtils;
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
	private JwtTokenUtils jwtTokenUtils;
	
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
        
        //输出登录提示信息
        LOGGER.info("用户：" + userDetails.getUsername()+ " 成功登录系统.");

        LOGGER.info("请求 IP :" + ((WebAuthenticationDetails) authentication
                .getDetails()).getRemoteAddress());
        
        String sessionId = request.getSession().getId();
        
        LOGGER.info("sessionId :" + request.getSession().getId());
        
        //当用户登录验证成功后 注册session(踢出在线用户时要用)
        sessionRegistry.registerNewSession(sessionId,authentication.getPrincipal());
        
        ConcurrentMap <String, String> map = new ConcurrentHashMap<>();
        // 登陆成功后返回一个加密token  以后通过token进行权限验证
        final String token = jwtTokenUtils.generateAccessToken(userDetails);
        LOGGER.info("x-auth-token :" + token);
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
