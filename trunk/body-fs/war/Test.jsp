<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
	final WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this
			.getServletContext());
	final IHerbDAO herbDAO = (IHerbDAO) context.getBean("herbDAO");
	final HerbPanel panel = new HerbPanel();
	final List<Long> herbs = new ArrayList<Long>();
	herbs.add(788L);
	herbs.add(790L);
	final List<Double> portions = new ArrayList<Double>();
	portions.add(1.2);
	portions.add(2.3);
	panel.setHerbs(herbs);
	panel.setPortions(portions);
	panel.setName("First Panel");
	herbDAO.createPanel(panel);
%>
</body>

<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="com.bodyfs.dao.IHerbDAO"%>
<%@page import="com.bodyfs.model.HerbPanel"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%></html>