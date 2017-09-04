package pers.ljy.background.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class MySessionAuthenticationStrategy extends ConcurrentSessionControlAuthenticationStrategy {

	private Integer MAX_USERS = 1;
	SessionRegistry srRegistry;
	public MySessionAuthenticationStrategy(SessionRegistry sessionRegistry) {
		super(sessionRegistry);
		this.srRegistry = sessionRegistry;
	}
	
	@Override
	public void onAuthentication(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
	    System.out.println("MySessionAuthenticationStrategy 同一个帐号只能登录一次 ");
		if (srRegistry.getAllPrincipals().size() > MAX_USERS) {
	        throw new SessionAuthenticationException("Maximum number of users exceeded");
	    }
	    super.onAuthentication(authentication, request, response);
	}

}
