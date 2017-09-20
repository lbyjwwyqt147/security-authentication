package pers.ljy.background.jwt;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import pers.ljy.background.share.exception.BusinessException;
import pers.ljy.background.share.redis.RedisService;
import pers.ljy.background.share.result.ApiResultCode;
import pers.ljy.background.share.result.ApiResultView;
import pers.ljy.background.share.utils.DateUtils;
import pers.ljy.background.share.utils.SecurityReturnJson;

/***
 * 集成JWT和Spring Security
 * 
 * 每次请求接口时 就会进入这里验证token 是否合法
 * 
 * token 如果用户一直在操作，则token 过期时间会叠加    如果超过设置的过期时间未操作  则token 失效 需要重新登录
 * 
 * @author ljy
 *
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

	@Autowired
    private UserDetailsService userDetailsService;

	@Autowired
	private RedisService redisService;
	
    @Autowired
    private JwtTokenUtils jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;
    
    @Value("${jwt.expiration}")
    private Long expiration;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws ServletException, IOException {
    	
	    	String authHeader = request.getHeader(this.tokenHeader);
	        if (authHeader != null && authHeader.startsWith(tokenHead)) {
	              final String authToken = authHeader.substring(tokenHead.length()); // The part after "Bearer-"
	              //如果在token过期之前触发接口,我们更新token过期时间，token值不变只更新过期时间
	              Date createTokenDate = jwtTokenUtil.getCreatedDateFromToken(authToken);  //获取token生成时间
	              Long differSeconds = DateUtils.getDistanceSeconds(System.currentTimeMillis(), createTokenDate.getTime());
	              boolean isExpire = expiration > differSeconds;
	              if(isExpire){  //如果 请求接口时间在token 过期之前 则更新token过期时间
	            	 String token = jwtTokenUtil.restTokenExpired(authToken,expiration+differSeconds);
	            	 System.out.println(token);
	              }
	              System.out.println(DateUtils.format(jwtTokenUtil.getExpirationDateFromToken(authToken)));
	              String username = jwtTokenUtil.getUsernameFromToken(authToken);
	              Long userId = jwtTokenUtil.getUserIdFromToken(authToken);
	              //获取当前用户在redis 中的token
	              String tokenRedisKey = "security-jwt-"+userId+"-token";
	              String redisToken = (String) redisService.get(tokenRedisKey);
	              //如果token 未过期(存在)就验证是否合法    不存在则直接返回不合法
	              if(StringUtils.isNotBlank(redisToken)){
	            	  logger.info("checking authentication " + username);

	            	  if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

	            		  //得到用户信息
	            		  JWTUserDetails userDetails = (JWTUserDetails) this.userDetailsService.loadUserByUsername(username);
                          
	            		  //验证token 是否合法
	            		  if (jwtTokenUtil.validateToken(authToken, userDetails)) {
	            			  UsernamePasswordAuthenticationToken userAuthentication = new UsernamePasswordAuthenticationToken(
	            					  userDetails, null, userDetails.getAuthorities());
	            			  userAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
	            					  request));
	            			  logger.info("authenticated user " + username + ", setting security context");
	            			  SecurityContextHolder.getContext().setAuthentication(userAuthentication);
	            		  }
	            	  }
	          }else{
	        	  logger.info(" redis 中token 已经过期,需要重新登录.");
	        	  
	        	  //throw new BusinessException("请重新登录系统.");
	        	  //返回json形式的错误信息  
	             // ApiResultView view = new ApiResultView(ApiResultCode.ERROR.getCode(),"请重新登录进入系统.", null);
	             // SecurityReturnJson.writeJavaScript(response, view);
	          }
	    } 
        chain.doFilter(request, response);
    }

}
