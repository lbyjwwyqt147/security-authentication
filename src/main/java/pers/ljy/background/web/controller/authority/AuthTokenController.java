package pers.ljy.background.web.controller.authority;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pers.ljy.background.jwt.JwtTokenUtils;
import pers.ljy.background.share.result.ApiResultView;

/***
 * 权限 action 提供刷新token 获取新的token方法
 * @author ljy
 *
 */
@RestController
public class AuthTokenController {

	   @Autowired
	   private JwtTokenUtils jwtTokenUtil;

	   @Value("${jwt.header}")
	   private String tokenHeader;

	   @Value("${jwt.tokenHead}")
	   private String tokenHead;
	   
	   /**
	    * 刷新token
	    * @param request
	    * @return
	    * @throws AuthenticationException
	    */
	   @RequestMapping(value = "auth/refreshToken", method = RequestMethod.GET)
	   public ApiResultView refreshAndGetAuthenticationToken(
	            HttpServletRequest request) throws AuthenticationException{
	        String token = request.getHeader(tokenHeader);
	        final String authToken = token.substring(tokenHead.length());
	        String refreshedToken = jwtTokenUtil.refreshToken(authToken);
	        ConcurrentMap<String, String> map = new ConcurrentHashMap<>();
	        map.put("token", tokenHead+refreshedToken);
	        return new ApiResultView(map);
	    }
}
