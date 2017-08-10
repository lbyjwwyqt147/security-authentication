package pers.ljy.background.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import pers.ljy.background.model.SysUsersAccountEntity;
import pers.ljy.background.share.result.ApiResultView;

/***
 * 文件名称: LoginSuccessHandler.java
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
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginSuccessHandler.class); 
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		//获得授权后可得到用户信息  
		User userDetails =  (User) authentication.getPrincipal();
       /* Set<SysRole> roles = userDetails.getSysRoles();*/
        //输出登录提示信息
        LOGGER.info("用户" + userDetails.getUsername()+ " 登录");

        LOGGER.info("IP :" + this.getIpAddress(request));
        
        response.setContentType("application/json;charset=UTF-8");
        
       /* response.setCharacterEncoding("UTF-8");  
        response.setContentType("application/json");  
        response.getWriter().println("{\"status\":0,\"msg\":\"登录成功\"}");  
        response.getWriter().flush();
        response.getWriter().close();*/
        
        
        PrintWriter writer = response.getWriter();
        String returnStr = "{\"status\":0,\"msg\":\"登录成功\"}";  
        System.out.println(this.getClass().toString()+":"+returnStr);  
        writer.write(returnStr);  
        writer.flush();  
        writer.close(); 
        
       /* String returnStr = "0";  
        
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control","no-store, max-age=0, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");

        PrintWriter out = response.getWriter();
        //out.write(JSON.toJSONString(returnStr, SerializerFeature.DisableCircularReferenceDetect));//必须加上第二个参数。忽略循环引用
        out.write(returnStr);
        out.flush();
        out.close();*/
    

        
        
		super.onAuthenticationSuccess(request, response, authentication);
	}
	
	/**
	 * 获取IP地址
	 * @param request
	 * @return
	 */
	private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
