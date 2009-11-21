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

public class ApplicationFilter implements Filter{
	public void init(FilterConfig arg0) throws ServletException {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
	    FilterChain filterChain) throws IOException, ServletException {

	HttpServletRequest req = (HttpServletRequest)request;
	HttpServletResponse res = (HttpServletResponse)response;
	HttpSession session = req.getSession(true);
	LoginInfo userInfo = (LoginInfo)session.getAttribute("LOGIN_CREDENTIALS");
	if(req.getServletPath().contains("logout")){
		session.removeAttribute("LOGIN_CREDENTIALS");
		session.invalidate();
		String url = req.getContextPath() + "/login.zul";
		res.sendRedirect(url);
		return;
	}
	
	System.out.println("AplicationFilter userInfo="+userInfo);
	if (userInfo == null && req.getServletPath().contains(".zul") && !req.getServletPath().equals("/login.zul")) {
    
		String url = req.getContextPath() + "/login.zul";
		res.sendRedirect(url);
		return;
	}
		
	filterChain.doFilter(request, response);
    }

}
