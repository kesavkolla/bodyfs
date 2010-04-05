<%@ page language="java" contentType="text/html" session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.bodyfs.dao.IPersonDAO"%>
<%@page import="net.sf.jsr107cache.Cache"%>
<%@page import="com.bodyfs.ui.QuickPatientListComposer"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<title>Insert title here</title>
</head>
<body>
<%
	final WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this
			.getServletContext());
	final IPersonDAO personDAO = ctx.getBean(IPersonDAO.class);
	final Cache cache = ctx.getBean(Cache.class);
	out.println(cache.get(QuickPatientListComposer.QUICK_PATIENT_LIST));
%>
</body>
</html>