package pers.ljy.background.security;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import pers.ljy.background.share.result.ApiResultView;
import pers.ljy.background.share.result.BaseApiResultView;
import pers.ljy.background.share.utils.SecurityReturnJson;

/***
 * 文件名称: MyAccessDeniedHandler.java
 * 文件描述: 自定义权限不足 需要做的业务操作
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
public class MyAccessDeniedHandler implements AccessDeniedHandler {
	private final static Logger LOGGER = LoggerFactory.getLogger(MyAccessDeniedHandler.class);

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		LOGGER.info("你访问的资源 权限不足.");
        //返回json形式的错误信息  
        ConcurrentMap <String, String> map = new ConcurrentHashMap<>();
        map.put("authException", accessDeniedException.getLocalizedMessage());
        ApiResultView view = new ApiResultView(BaseApiResultView.AUTHENTICATIONFALL, map);
        SecurityReturnJson.writeJavaScript(response, view);

	}

}
