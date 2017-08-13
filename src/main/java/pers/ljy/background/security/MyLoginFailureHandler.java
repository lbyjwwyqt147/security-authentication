package pers.ljy.background.security;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import pers.ljy.background.share.result.ApiResultCode;
import pers.ljy.background.share.result.ApiResultView;
import pers.ljy.background.share.utils.SecurityReturnJson;

/***
 * 文件名称: MyLoginFailureHandler.java
 * 文件描述: 用户登录失败后需要做的业务操作
 * 
 * 公 司: 
 * 内容摘要: 
 * 其他说明:
 * 完成日期:2017年06月12日 
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
public class MyLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	private final static Logger LOGGER = LoggerFactory.getLogger(MyLoginFailureHandler.class);
	@Override  
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,  
	            AuthenticationException exception) throws IOException, ServletException {  
		LOGGER.info("登陆失败.");
	    ConcurrentMap <String, String> map = new ConcurrentHashMap<>();
        map.put("exception", exception.getLocalizedMessage());
        ApiResultView view = new ApiResultView(ApiResultCode.FAIL.getCode(), ApiResultCode.FAIL.getMsg(), map);
        SecurityReturnJson.writeJavaScript(response, view);

	  // super.onAuthenticationFailure(request, response, exception);  
	        
	}  

}
