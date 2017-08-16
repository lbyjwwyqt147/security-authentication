package pers.ljy.background.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import pers.ljy.background.sessionshare.HttpSessionManagement;
import pers.ljy.background.share.result.ApiResultCode;
import pers.ljy.background.share.result.ApiResultView;
import pers.ljy.background.share.utils.SecurityReturnJson;


/***
 * 文件名称: MyFilterSecurityInterceptor.java
 * 文件描述: 过滤用户请求
 * 
 * 公 司: 
 * 内容摘要: 
 * 其他说明:   继承AbstractSecurityInterceptor、实现Filter是必须的。
 *          首先，登陆后，每次访问资源都会被这个拦截器拦截，会执行doFilter这个方法，这个方法调用了invoke方法，其中fi断点显示是一个url（可能重写了toString方法吧，但是里面还有一些方法的），
 *          最重要的是beforeInvocation这个方法，它首先会调用MyInvocationSecurityMetadataSource类的getAttributes方法获取被拦截url所需的权限，
 *          在调用MyAccessDecisionManager类decide方法判断用户是否够权限。弄完这一切就会执行下一个拦截器。
 * 完成日期:2017年05月15日 
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Component
public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter{
    @Autowired
    private FilterInvocationSecurityMetadataSource securityMetadataSource;
    @Autowired
    private HttpSessionManagement httpSession;


    @Autowired
    public void setMyAccessDecisionManager(MyAccessDecisionManager myAccessDecisionManager) {
        super.setAccessDecisionManager(myAccessDecisionManager);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
	 * 登录后，每次访问资源都通过这个拦截器拦截  
	 */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        FilterInvocation fi = new FilterInvocation(request, response, chain);
        invoke(fi);
    }


    /**
     * 拦截请求处理
     * @param fi
     * @throws IOException
     * @throws ServletException
     */
    public void invoke(FilterInvocation fi) throws IOException, ServletException {
    	
    	 //获取session
         if(httpSession.getHttpSession(fi.getHttpRequest(), fi.getHttpResponse()) != null){

        	//fi里面有一个被拦截的url
            //里面调用MyInvocationSecurityMetadataSource的getAttributes(Object object)这个方法获取fi对应的所有权限
            //再调用MyAccessDecisionManager的decide方法来校验用户的权限是否足够
            InterceptorStatusToken token = super.beforeInvocation(fi);
            try {
                //执行下一个拦截器
                fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
            } finally {
                super.afterInvocation(token, null);
            }
         }else {
        	 ApiResultView view = new ApiResultView(ApiResultCode.FAIL.getCode(), "登录超时.", null);
             SecurityReturnJson.writeJavaScript(fi.getHttpResponse(), view);
		}
         
    }
    
  
    @Override
    public void destroy() {

    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }
}

