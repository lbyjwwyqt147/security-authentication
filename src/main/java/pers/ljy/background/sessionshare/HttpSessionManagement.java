package pers.ljy.background.sessionshare;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.session.web.http.HttpSessionStrategy;
import org.springframework.stereotype.Component;

import pers.ljy.background.security.MyFilterSecurityInterceptor;
import pers.ljy.background.share.redis.RedisService;

@Component
public class HttpSessionManagement {
	private static final Logger LOGGER = Logger.getLogger(MyFilterSecurityInterceptor.class);
    @Autowired
    private HttpSessionStrategy httpSession;
    @Autowired
    private RedisService redisService;
    
    /**
     * 获取session
     * @param request
     * @param response
     * @return
     */
    public HttpSession getHttpSession(HttpServletRequest request,HttpServletResponse response){
   	    //每个请求都要添加一个"SESSION"参数
        String sessionId = request.getParameter("SESSION");
        //使用cookie 传值获取sessionId 的方法  我由于前端传递cookie到后台问题没有解决到 就没有用这种方式获取
       // String sessionId = httpSession.getRequestedSessionId(fi.getRequest());
        LOGGER.info("请求的 sessionId：" +sessionId);
        HttpSession sHttpSession = request.getSession();
   	    SecurityContext securityContext = (SecurityContext) sHttpSession.getAttribute("SPRING_SECURITY_CONTEXT");

        
        Object object2 = redisService.hmGet("spring:session:sessions:"+sessionId, "sessionAttr:SPRING_SECURITY_CONTEXT");


        HttpSession session = (HttpSession) redisService.get(sessionId);
        if(session != null){
        	 //SecurityContext securityContext = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
            // Authentication authentication = securityContext.getAuthentication();
        }
        return session;
    }
}
