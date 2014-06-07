/**
 * 
 */
package com.prax.framework.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.prax.core.domain.entity.Domain;
import com.prax.core.domain.service.DomainService;
import com.prax.framework.base.model.UCN;
import com.prax.framework.context.Context;
import com.prax.framework.context.Scope;
import com.prax.framework.context.impl.HttpContext;
import com.prax.framework.context.impl.SystemContext;

/**
 * @author Huanan
 * 
 */
public class DomainFilter implements Filter, ApplicationContextAware {

	private ApplicationContext applicationContext;

	private DomainService domainService;

	private ServletContext servletContext;

	/**
	 * @throws
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse,
	 * javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		if (request.getAttribute(DomainFilter.class.getName()) != null) {
			chain.doFilter(request, response);
		}
		request.setAttribute(DomainFilter.class.getName(), true);

		UCN domain = checkDomain(request);

		if (domain == null) {
			if (response instanceof HttpServletResponse) {
				HttpServletResponse res = (HttpServletResponse) response;
				res.sendError(400, "Domain is not found");
			}
			return;
		}
		if (request instanceof HttpServletRequest) {
			HttpServletRequest req = (HttpServletRequest) request;
			new HttpContext(servletContext, req).getSession(true);
		}
		else {
			new SystemContext();
		}

		Context.getSessionScope().put(Scope.CURRENT_DOMAIN, domain);

		chain.doFilter(request, response);
	}

	/**
	 * 
	 * @param request
	 * @return A FirmConfig instance.
	 * @throws FirmDisabledException
	 */
	private UCN checkDomain(ServletRequest request) {
		Domain domain = domainService.getRoot();
		if (domain == null) {
			return null;
		}
		return domain;
	}

	public static String getCookieValue(HttpServletRequest request, String name) {
		Cookie[] cookies;
		if ((cookies = request.getCookies()) == null)
			return null;
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(name)) {
				return cookie.getValue();
			}
		}
		return null;
	}

	/**
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig conf) throws ServletException {
		servletContext = conf.getServletContext();
	}

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void setDomainService(DomainService domainService) {
		this.domainService = domainService;
	}

}