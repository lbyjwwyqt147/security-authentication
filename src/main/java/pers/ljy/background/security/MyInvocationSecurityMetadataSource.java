package pers.ljy.background.security;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import pers.ljy.background.model.SysResourceMenusEntity;
import pers.ljy.background.service.authority.SysResourceMenusService;

/***
 * 文件名称: MyInvocationSecurityMetadataSource.java (系统启动就会加载)
 * 文件描述: 加载资源与权限的对应关系
 * 
 * 容器启动加载顺序：1：调用loadResourceDefine()方法  2：调用supports()方法   3：调用getAllConfigAttributes()方法
 * 
 * 公 司: 
 * 内容摘要: 
 * 其他说明:
 *       实现FilterInvocationSecurityMetadataSource接口也是必须的。 首先，这里从数据库中获取信息。 其中loadResourceDefine方法不是必须的，
 *       这个只是加载所有的资源与权限的对应关系并缓存起来，避免每次获取权限都访问数据库（提高性能），然后getAttributes根据参数（被拦截url）返回权限集合。
 *       这种缓存的实现其实有一个缺点，因为loadResourceDefine方法是放在构造器上调用的，而这个类的实例化只在web服务器启动时调用一次，那就是说loadResourceDefine方法只会调用一次，
 *       如果资源和权限的对应关系在启动后发生了改变，那么缓存起来的权限数据就和实际授权数据不一致，那就会授权错误了。但如果资源和权限对应关系是不会改变的，这种方法性能会好很多。
 *       要想解决 权限数据的一致性 可以直接在getAttributes方法里面调用数据库操作获取权限数据，通过被拦截url获取数据库中的所有权限，封装成Collection<ConfigAttribute>返回就行了。（灵活、简单）
 * 
 * 完成日期:2016年11月16日 
 * 修改记录:
 * @version 1.0
 * @author liujunyi
 */
@Component
public class MyInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	private static final Logger LOGGER = LoggerFactory.getLogger(MyInvocationSecurityMetadataSource.class);
    //存放资源配置对象
	private static ConcurrentMap<String, Collection<ConfigAttribute>> resourceMap = null;
	@Autowired
	private SysResourceMenusService sysResourceMenusService;
	
	 /**
     * 初始化资源 ,加载所有url和权限（或角色）的对应关系，  web容器启动就会执行
     */
    @PostConstruct
    public void loadResourceDefine() {
        LOGGER.info(" web容器启动时就会 开始加载资源与权限的对应关系!!!");
    	try {
    		   if (resourceMap == null) {
    	            resourceMap = new ConcurrentHashMap<>();
    	            //容器启动时,获取全部系统菜单资源信息
    	            CopyOnWriteArrayList<SysResourceMenusEntity> resources = sysResourceMenusService.selectByMenuTypeNotIn();
    	            for (SysResourceMenusEntity resource : resources) {
    	                Collection<ConfigAttribute> configAttributes = new CopyOnWriteArrayList<ConfigAttribute>();
    	                //和类：MyUserDetailService 的       authSet.add(new SimpleGrantedAuthority(sysResourceMenusEntity.getAuthorizedSigns()))参数 一致
    	                ConfigAttribute configAttribute = new SecurityConfig("ROLE_"+resource.getAuthorizedSigns());
    	                configAttributes.add(configAttribute);
    	                //资源模块对应的url 地址
    	                resourceMap.put(resource.getMenuUrl(), configAttributes);
    	            }
    	        }
		} catch (Exception e) {
			e.printStackTrace();
			 LOGGER.info(" 开始加载资源与权限的对应关系出现异常!!!");
		}
     
    }

	
	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	/**
	 * 参数是要访问的url，返回这个url对于的所有权限（或角色）
	 * 每次请求后台就会调用 得到请求所拥有的权限
	 * 这个方法在url请求时才会调用，服务器启动时不会执行这个方法 
     * getAttributes这个方法会根据你的请求路径去获取这个路径应该是有哪些权限才可以去访问。
	 * 
	 */
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		if (resourceMap == null)loadResourceDefine();
		FilterInvocation filterInvocation = (FilterInvocation) object; 
		// 获取用户请求的url地址
        String requestUrl = filterInvocation.getRequestUrl();
        // 返回当前 url  所需要的权限
        return resourceMap.get(requestUrl);
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

}
