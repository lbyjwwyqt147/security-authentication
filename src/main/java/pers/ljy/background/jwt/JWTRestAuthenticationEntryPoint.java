package pers.ljy.background.jwt;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import pers.ljy.background.share.result.ApiResultView;
import pers.ljy.background.share.utils.SecurityReturnJson;

/***
 * JWT 认证失败时 访问
 * @author ljy
 *
 */
@Component
public class JWTRestAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private static final Logger LOGGER = Logger.getLogger(JWTRestAuthenticationEntryPoint.class);
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		LOGGER.info("经过JWT认证后得知：你访问的资源 权限不足.");
        //返回json形式的错误信息  
        ConcurrentMap <String, Object> map = new ConcurrentHashMap<>();
        map.put("timestamp", System.currentTimeMillis());
        map.put("method",request.getMethod());
        map.put("path",request.getRequestURL().toString() );
        map.put("authException", authException.getLocalizedMessage());
        map.put("message", "无效的 token 值 ");
        ApiResultView view = new ApiResultView(401,"你无权限访问.", map);
        SecurityReturnJson.writeJavaScript(response, view);

	}

}
