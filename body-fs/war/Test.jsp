<%@ page language="java" contentType="text/html" session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.bodyfs.PMF"%>
<%@page import="com.bodyfs.model.QuickPatient"%>
<%@page import="java.util.concurrent.ArrayBlockingQueue"%>
<%@page import="com.bodyfs.dao.IPersonDAO"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<title>Insert title here</title>
</head>
<body>
<%
	final WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this
			.getServletContext());
	final IPersonDAO personDAO = ctx.getBean(IPersonDAO.class);
	out.println(personDAO.getQuickList());
%>
</body>
</html>