/* $Id$ */
package com.bodyfs.framework.filters;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.zkoss.zk.ui.sys.SessionsCtrl;

import com.bodyfs.Constants;
import com.bodyfs.model.LoginInfo;
import com.bodyfs.model.PersonType;

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
		if (req.getHeader("User-Agent") != null && req.getHeader("User-Agent").contains("chromeframe")) {
			res.addHeader("X-UA-Compatible", "chrome=1");
		}

		if (!req.getServletPath().contains(".zul") || req.getServletPath().equals("/login.zul")) {
			filterChain.doFilter(request, response);
			return;
		}

		StringBuffer url = new StringBuffer(req.getRequestURI().length());
		final HttpSession session = req.getSession(false);
		PersonType personType = null;
		if (session != null) {
			personType = (PersonType) session.getAttribute(Constants.SESSION_PERSON_TYPE);
		}
		if (req.getRequestURI().endsWith("login.zul") || req.getRequestURI().endsWith("index.zul")
				|| req.getRequestURI().endsWith("login.zu") || req.getRequestURI().endsWith("logout.zul")) {
			url.append(req.getContextPath()).append("/login.zul");
		} else if (req.getRequestURI().contains("/signin/")
				&& (personType == null || personType != PersonType.SIA_AGENT)) {
			url.append(req.getContextPath()).append("/login.zul");
			res.sendRedirect(url.toString());
			return;
		} else {
			url.append(req.getContextPath()).append("/login.zul");
			url.append("?redirect=").append(req.getRequestURI());
			if (req.getQueryString() != null && req.getQueryString().length() > 0) {
				url.append(URLEncoder.encode("?" + req.getQueryString(), "US-ASCII"));
			}
		}

		if (session == null) {
			res.sendRedirect(url.toString());
			return;
		}

		final LoginInfo userInfo = (LoginInfo) session.getAttribute(Constants.SESSION_LOGIN_CRED);
		if (userInfo == null) {
			res.sendRedirect(url.toString());
			return;
		}

		if (req.getServletPath().contains("logout")) {
			session.removeAttribute("LOGIN_CREDENTIALS");
			session.invalidate();
			if (SessionsCtrl.getCurrentCtrl() != null) {
				SessionsCtrl.getCurrentCtrl().invalidateNow();
			}
			res.sendRedirect(url.toString());
			return;
		}

		filterChain.doFilter(request, response);
	}
}
