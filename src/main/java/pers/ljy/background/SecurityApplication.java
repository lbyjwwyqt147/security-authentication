package pers.ljy.background;

import javax.security.auth.message.config.AuthConfigFactory;

import org.apache.catalina.authenticator.jaspic.AuthConfigFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement  //@EnableTransactionManagement 开启事务支持后，然后在访问数据库的Service方法上添加注解 @Transactional 便可
@SpringBootApplication 
public class SecurityApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityApplication.class);
	public static void main(String[] args) {
		//tomcat8.5 需要这么设置
		if (AuthConfigFactory.getFactory() == null) {
            AuthConfigFactory.setFactory(new AuthConfigFactoryImpl());
        }
		ApplicationContext ctx = SpringApplication.run(SecurityApplication.class, args);
		String[] activeProfiles = ctx.getEnvironment().getActiveProfiles();  
	    for (String profile : activeProfiles) {  
	    	LOGGER.warn("Spring Boot 使用profile为:{}" , profile);  
	    }
	}
}

