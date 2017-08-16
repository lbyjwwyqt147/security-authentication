package pers.ljy.background.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

/***
 * 自定义跨域访问过滤器
 * @author ljy
 *
 */
@Component
public class SimpleCORSFilter implements Filter {

	 public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
	        HttpServletResponse response = (HttpServletResponse) servletResponse;
	        /* 设置浏览器跨域访问 */
	        response.setHeader("Access-Control-Allow-Origin", "*");//支持全域名访问，不安全，部署后需要固定限制为客户端网址
	        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE,PUT");//支持的http 动作
	        response.setHeader("Access-Control-Max-Age", "3600");
	        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization,x-auth-token");//相应头
	        response.setHeader("Access-Control-Allow-Credentials","true"); //跨域cookie设置
	        filterChain.doFilter(servletRequest, servletResponse);
	 }

	 public void init(FilterConfig filterConfig) {
	    	
	 }

	 public void destroy() {
	    	
	 }
	
}
