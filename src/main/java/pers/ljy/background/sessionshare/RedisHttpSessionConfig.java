package pers.ljy.background.sessionshare;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/***
 * 文件名称: RedisHttpSessionConfig.java
 * 文件描述: 实现seesion共享 
 * 
 * 公 司: 
 * 内容摘要: 
 * 其他说明: session 和redis 关联
 * 
 *        1：EnableRedisHttpSession创建了一个名为springSessionRepositoryFilter的Spring Bean来实现过滤器。这个由Spring Session实现的过滤器是负责替换HttpSession的实现。在这种情况下，Spring Session由redis支持。
 *        2：然后创建了一个RedisConnectionFactory来连接Spring Session到地址是localhost，端口为6379的redis服务器。更多关于配置Spring Data Redis的信息可以参考这个文档。
 *       
 * 完成日期:2017年08月15日 
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Configuration  
@EnableRedisHttpSession // @EnableRedisHttpSession这个注解就是最重要的东西，加了它之后，spring生产一个新的拦截器，用来实现Session共享的操作
public class RedisHttpSessionConfig {
	
	/**
	 * Spring根据配置文件中的配置连到Redis。
	 * @return
	 */
	 @Bean  
     public JedisConnectionFactory connectionFactory() {  
           return new JedisConnectionFactory();   
     }  

}
