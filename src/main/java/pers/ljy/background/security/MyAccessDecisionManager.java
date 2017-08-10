package pers.ljy.background.security;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import pers.ljy.background.share.exception.BusinessException;

/***
 * 文件名称: MyAccessDecisionManager.java
 * 文件描述: 验证用户是否拥有所请求菜单资源的权限 资源认证器
 * 
 * 公 司: 
 * 内容摘要: 
 * 其他说明:
 *       接口AccessDecisionManager也是必须实现的。 decide方法里面写的就是授权策略了，，需要什么策略，可以自己写其中的策略逻辑。
 *       通过就返回，不通过抛异常就行了，spring security会自动跳到权限不足页面（配置文件上配的）。 
 *       
 * 完成日期:2017年05月15日 
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Component
public class MyAccessDecisionManager implements AccessDecisionManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(MyUserDetailService.class); 

	@Autowired
	private AuthenticationManager authenticationManager; 
	
	/**
	 * decide()方法在url请求时才会调用，服务器启动时不会执行这个方法
     *
     * @param configAttributes 装载了请求的url允许的角色数组 。这里是从MyInvocationSecurityMetadataSource里的loadResourceDefine方法里的atts对象取出的角色数据赋予给了configAttributes对象
     * @param object url
     * @param authentication 装载了从数据库读出来的权限(角色) 数据。这里是从MyUserDetailService里的loadUserByUsername方法里的grantedAuths对象的值传过来给 authentication 对象,简单点就是从spring的全局缓存SecurityContextHolder中拿到的，里面是用户的权限信息
     *  
     * 
	 */
	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		LOGGER.info("================ 每次请求后台 都会进入效验用户所拥有的 资源权限 ===================== ");
		//如果访问资源不需要任何权限则直接通过  
		if (configAttributes == null) {
            return;
        }
		//mmp  这里为什么取不到登录人的权限呢  取出来的全是 ROLE_ANONYMOUS(匿名状态)
        SecurityContext securityContext = SecurityContextHolder.getContext();
		authentication = SecurityContextHolder.getContext().getAuthentication();
		//FilterInvocation filterInvocation = (FilterInvocation) object; 
	//	HttpSession httpSession = filterInvocation.getHttpRequest().getSession();
		//authentication = (Authentication) httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
		//重新认证，切记要用明文密码  
	    /*if(authentication == null){
	      authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("admin","123456"));
          SecurityContextHolder.getContext().setAuthentication(authentication);  
        } 
		*/
        //所请求的资源拥有的权限(一个资源对多个权限)
        Iterator<ConfigAttribute> iterator = configAttributes.iterator();
        //遍历configAttributes看用户是否有访问资源的权限  
        while (iterator.hasNext()) {
            ConfigAttribute configAttribute = iterator.next();
            //访问所请求资源所需要的权限
            String needRole = configAttribute.getAttribute();
            //用户所拥有的权限authentication 
            //如果 ga.getAuthority()== ROLE_ANONYMOUS 
            for (GrantedAuthority ga : authentication.getAuthorities()) {
            	LOGGER.info("-----------给用户所赋予的权限：" + ga.getAuthority() + "," + " 访问资源具有的权限：" +needRole);
                //ga 为用户所被赋予的权限。 needPermission 为访问相应的资源应该具有的权限。
                //判断两个请求的url页面的权限和用户的权限是否相同，如相同，允许访问
            	if (needRole.equals(ga.getAuthority())) {
                    return;
                }
            }
        }
        // 注意：执行这里，后台是会抛异常的，但是界面会跳转到所配的access-denied-page页面
        throw new AccessDeniedException("没有权限访问！");
	}

	@Override
	public boolean supports(ConfigAttribute arg0) {
		return true;
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

}
