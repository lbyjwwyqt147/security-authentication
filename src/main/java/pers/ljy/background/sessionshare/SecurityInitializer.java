package pers.ljy.background.sessionshare;

import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.stereotype.Component;

/***
 * 文件名称: SecurityInitializer.java
 * 文件描述: 实现seesion共享 
 * 
 * 公 司: 
 * 内容摘要: 
 * 其他说明: 
 *       
 * 完成日期:2017年08月15日 
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Component
public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {

	public SecurityInitializer() {
		//super()的第二个参数，是自定义的 RedisHttpSessionConfig文件。添加这个配置文件后，Spring Security就会把Session放到Redis中，这样基于Spring Security的项目也可以实现Session共享了
        super(SecurityConfig.class, RedisHttpSessionConfig.class);
    }
}
