<%@ page language="java" contentType="text/html" session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.bodyfs.dao.IHerbDAO"%>
<%@page import="com.bodyfs.dao.impl.HerbDAO"%>
<%@page import="com.bodyfs.model.Herb"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<title>Insert title here</title>
</head>
<body>
<%
	final WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this
			.getServletContext());
	final IHerbDAO herbDAO = ctx.getBean(IHerbDAO.class);
	for (int i = 5056; i <= (5056 + 1000); i++) {
		final Herb herb = herbDAO.getHerbById(new Long(i));
		herb.setName(herb.getName());
		herbDAO.createHerb(herb);
	}
%>
</body>
</html>