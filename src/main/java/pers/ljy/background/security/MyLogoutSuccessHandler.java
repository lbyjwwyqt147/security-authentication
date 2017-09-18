package pers.ljy.background.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import pers.ljy.background.jwt.JWTUserDetails;
import pers.ljy.background.jwt.JwtTokenUtils;
import pers.ljy.background.share.redis.RedisService;
import pers.ljy.background.share.result.ApiResultCode;
import pers.ljy.background.share.result.ApiResultView;
import pers.ljy.background.share.utils.SecurityReturnJson;

/***
 * 文件名称: MyLogoutSuccessHandler.java
 * 文件描述: 退出系统 需要做的业务操作
 * 
 * 公 司: 
 * 内容摘要: 
 * 其他说明:
 * 完成日期:2017年08月10日 
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(MyLogoutSuccessHandler.class);

	@Autowired
	private RedisService redisService;
	@Autowired
	private JwtTokenUtils jwtTokenUtil;
	@Value("${jwt.header}")
	private String tokenHeader;
	@Value("${jwt.tokenHead}")
	private String tokenHead;
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		 LOGGER.info("成功退出系统.");
		 //session 失效
		 request.getSession().invalidate();
		 String authHeader = request.getHeader(this.tokenHeader);
	     if (authHeader != null && authHeader.startsWith(tokenHead)) {
	         final String authToken = authHeader.substring(tokenHead.length());
			 //使用jwt 
		     JWTUserDetails userDetails =  jwtTokenUtil.getUserFromToken(authToken);
			 if(userDetails != null){
				 //token 失效  从redis中移除
			     String tokenRedisKey = "security-jwt-"+userDetails.getUserId()+"-token";
			     redisService.remove(tokenRedisKey);
			 }
	     }
		 ApiResultView view = new ApiResultView(ApiResultCode.SUCCESS.getCode(), ApiResultCode.SUCCESS.getMsg(), null);
	     SecurityReturnJson.writeJavaScript(response, view);
		 
	}

}
