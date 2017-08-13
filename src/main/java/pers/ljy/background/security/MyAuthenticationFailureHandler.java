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
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import pers.ljy.background.share.result.ApiResultView;
import pers.ljy.background.share.result.BaseApiResultView;
import pers.ljy.background.share.utils.SecurityReturnJson;

/***
 * 文件名称: MyAuthenticationFailureHandler.java
 * 文件描述: 没有资源访问权限需要做的业务操作
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
public class MyAuthenticationFailureHandler extends LoginUrlAuthenticationEntryPoint {
	private final static Logger LOGGER = LoggerFactory.getLogger(MyAuthenticationFailureHandler.class);
	
	public MyAuthenticationFailureHandler(String loginFormUrl) {
		super(loginFormUrl);
	}

	/**
	 * 当访问的资源没有权限，会调用这里  
	 */
    @Override  
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)  
                throws IOException, ServletException {  
	
	    LOGGER.info("你无访问资源的权限.");
        //返回json形式的错误信息  
        ConcurrentMap <String, String> map = new ConcurrentHashMap<>();
        map.put("authException", authException.getLocalizedMessage());
        ApiResultView view = new ApiResultView(BaseApiResultView.AUTHENTICATIONFALL, map);
        SecurityReturnJson.writeJavaScript(response, view);

    }  

}
