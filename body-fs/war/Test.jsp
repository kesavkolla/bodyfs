<%@ page language="java" contentType="text/html" session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.bodyfs.dao.IHerbDAO"%>
<%@page import="com.bodyfs.dao.ICounterDAO"%>
<%@page import="com.bodyfs.model.Diagnosis"%>
<%@page import="java.util.Arrays"%>
<%@page import="org.zkoss.json.JSONObject"%>
<%@page import="org.zkoss.json.JSONArray"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<title>Insert title here</title>
</head>
<body>
<%
	final WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this
			.getServletContext());
	final ICounterDAO counterDAO = ctx.getBean(ICounterDAO.class);
	counterDAO.resetCount(ICounterDAO.DIAGNOSIS_COUNTER, 2);
%>
</body>
</html>