package pers.ljy.background.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.stereotype.Component;

@Component
public class MyConcurrentSessionControlAuthenticationStrategy extends ConcurrentSessionControlAuthenticationStrategy {
	private static final Logger LOGGER = LoggerFactory.getLogger(MyConcurrentSessionControlAuthenticationStrategy.class);

	public MyConcurrentSessionControlAuthenticationStrategy(SessionRegistry sessionRegistry) {
		super(sessionRegistry);
	}

	@Override
	public void onAuthentication(Authentication authentication, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.info("MyConcurrentSessionControlAuthenticationStrategy ............. ");
		super.onAuthentication(authentication, request, response);
	}
	
	

}
