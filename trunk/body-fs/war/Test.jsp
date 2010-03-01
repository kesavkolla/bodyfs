<%@ page language="java" contentType="text/html" session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.bodyfs.dao.IHerbDAO"%>
<%@page import="com.bodyfs.dao.impl.HerbDAO"%>
<%@page import="com.bodyfs.dao.ICounterDAO"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<title>Insert title here</title>
</head>
<body>
<%
	final WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this
			.getServletContext());
	final ICounterDAO counterDAO = ctx.getBean(ICounterDAO.class);
	counterDAO.createCount(ICounterDAO.DIAGNOSIS_COUNTER, 3);
	counterDAO.createCount(ICounterDAO.HERBFORUMLA_COUNTER, 1000);
%>
</body>
</html>