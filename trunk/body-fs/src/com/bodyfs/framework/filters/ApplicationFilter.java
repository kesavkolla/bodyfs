/* $Id$ */
package com.bodyfs.framework.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bodyfs.model.LoginInfo;

/**
 * 
 * @author kesav
 * 
 */
public class ApplicationFilter implements Filter {
	public void init(final FilterConfig config) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException,
			ServletException {

		final HttpServletRequest req = (HttpServletRequest) request;
		final HttpServletResponse res = (HttpServletResponse) response;

		if (!req.getServletPath().contains(".zul") || req.getServletPath().equals("/login.zul")) {
			filterChain.doFilter(request, response);
			return;
		}
		final String url = req.getContextPath() + "/login.zul";
		final HttpSession session = req.getSession(false);
		if (session == null) {
			res.sendRedirect(url);
			return;
		}
		final LoginInfo userInfo = (LoginInfo) session.getAttribute("LOGIN_CREDENTIALS");
		if (userInfo == null) {
			res.sendRedirect(url);
			return;
		}

		if (req.getServletPath().contains("logout")) {
			session.removeAttribute("LOGIN_CREDENTIALS");
			session.invalidate();
			res.sendRedirect(url);
			return;
		}

		filterChain.doFilter(request, response);
	}

}
