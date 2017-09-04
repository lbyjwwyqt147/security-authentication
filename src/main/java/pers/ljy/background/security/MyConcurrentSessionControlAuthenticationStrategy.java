package pers.ljy.background.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class MyConcurrentSessionControlAuthenticationStrategy extends ConcurrentSessionControlAuthenticationStrategy {
	private static final Logger LOGGER = LoggerFactory.getLogger(MyConcurrentSessionControlAuthenticationStrategy.class);
	private Integer MAX_USERS = 1;
	SessionRegistry srRegistry;
	public MyConcurrentSessionControlAuthenticationStrategy(SessionRegistry sessionRegistry) {
		super(sessionRegistry);
		this.srRegistry = sessionRegistry;
	}

	/*@Override
	public void onAuthentication(Authentication authentication, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.info("MyConcurrentSessionControlAuthenticationStrategy ............. ");
		
		super.onAuthentication(authentication, request, response);
	}*/
	
	@Override
	public void onAuthentication(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
	    System.out.println("MySessionAuthenticationStrategy 同一个帐号只能登录一次 ");
		if (srRegistry.getAllPrincipals().size() > MAX_USERS) {
	        throw new SessionAuthenticationException("Maximum number of users exceeded");
	    }
	    super.onAuthentication(authentication, request, response);
	}


}
