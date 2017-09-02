package pers.ljy.background.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

/***
 * session 失效处理
 * @author ljy
 *
 */
@Component
public class MySessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {
	private static final Logger LOGGER = LoggerFactory.getLogger(MySessionInformationExpiredStrategy.class);

	@Override
	public void onExpiredSessionDetected(SessionInformationExpiredEvent eventØ) throws IOException, ServletException {
		LOGGER.info("ConcurrentSessionControlAuthenticationStrategy");
		
	}



	
	

}
