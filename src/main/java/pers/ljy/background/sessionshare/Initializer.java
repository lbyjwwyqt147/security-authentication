package pers.ljy.background.sessionshare;

import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;
import org.springframework.stereotype.Component;

/***
 * 文件名称: Initializer.java
 * 文件描述: 
 * 
 * 公 司: 
 * 内容摘要: 
 * 其他说明: session 和redis 关联
 * 
 *       通过继承AbstractHttpSessionApplicationInitializer类，我们确保了springSessionRepositoryFilter在springSecurityFilterChain之前调用。
 *       
 * 完成日期:2017年08月15日 
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Component
public class Initializer extends AbstractHttpSessionApplicationInitializer {

}
