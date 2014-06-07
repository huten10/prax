/**
 * 
 */
package com.prax.wechat.oauth2;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.http.AccessTokenRequiredException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

import com.prax.core.user.entity.User;
import com.prax.core.user.service.UserService;

/**
 * @author Huanan
 * 
 */
public class WechatAuthenticationFilter extends GenericFilterBean implements ApplicationEventPublisherAware {

	protected ApplicationEventPublisher eventPublisher;

	public OAuth2RestOperations restTemplate;

	public UserService userService;

	public void setRestTemplate(OAuth2RestOperations restTemplate) {
		this.restTemplate = restTemplate;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void afterPropertiesSet() {
		Assert.state(restTemplate != null, "Supply a rest-template");
		Assert.state(userService != null, "Supply a user-service");
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
			ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		if (!requiresAuthentication(request, response)) {
			chain.doFilter(request, response);

			return;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Request is to process authentication");
		}

		Authentication authResult;

		try {
			authResult = attemptAuthentication(request, response);
			if (authResult == null) {
				// return immediately as subclass has indicated that it hasn't completed authentication
				return;
			}
		}
		catch (InternalAuthenticationServiceException failed) {
			logger.error("An internal error occurred while trying to authenticate the user.", failed);
			unsuccessfulAuthentication(request, response, failed);

			return;
		}
		catch (AuthenticationException failed) {
			// Authentication failed
			unsuccessfulAuthentication(request, response, failed);

			return;
		}

		SecurityContextHolder.getContext().setAuthentication(authResult);
		// Fire event
		if (this.eventPublisher != null) {
			eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(authResult, this.getClass()));
		}

		chain.doFilter(request, response);

	}

	protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
		String uri = request.getRequestURI();
		int pathParamIndex = uri.indexOf(';');

		if (pathParamIndex > 0) {
			uri = uri.substring(0, pathParamIndex);
		}

		if (uri.equals(request.getContextPath() + "/")) {
			return false;
		}

		String userAgent = request.getHeader("user-agent");
		if (userAgent != null && userAgent.toLowerCase().contains("micromessenger")) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication != null && authentication.isAuthenticated()) {
				return false;
			}
			else {
				return true;
			}
		}
		return false;
	}

	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		OAuth2AccessToken accessToken = restTemplate.getAccessToken();

		String openId = (String) accessToken.getAdditionalInformation().get("openid");

		User user = userService.getByExtenralId(openId, new String[] { "profile" });
		if (user != null) {
			WechatAuthentication result = new WechatAuthentication(user, user.getAuthorities());
			return result;
		}
		throw new BadCredentialsException("Could not obtain user details from token");

	}

	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		if (failed instanceof AccessTokenRequiredException) {
			// Need to force a redirect via the OAuth client filter, so rethrow here
			throw failed;
		}
		else {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed: " + failed.getMessage());
		}
	}

	public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}

}