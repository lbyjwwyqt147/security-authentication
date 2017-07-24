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
	       // String origin = (String) servletRequest.getRemoteHost()+":"+servletRequest.getRemotePort();
	        /* 设置浏览器跨域访问 */
	        response.setHeader("Access-Control-Allow-Origin", "*");
	        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE,PUT");
	        response.setHeader("Access-Control-Max-Age", "3600");
	        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization");
	        response.setHeader("Access-Control-Allow-Credentials","true");
	        filterChain.doFilter(servletRequest, servletResponse);
	 }

	 public void init(FilterConfig filterConfig) {
	    	
	 }

	 public void destroy() {
	    	
	 }
	
}
