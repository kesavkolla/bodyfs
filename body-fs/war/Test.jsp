<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.bodyfs.dao.IHerbDAO"%>
<%@page import="com.bodyfs.model.Herb"%>
<%@page import="com.bodyfs.model.HerbFormula"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
	final WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this
	.getServletContext());
	final IHerbDAO herbDAO = ctx.getBean(IHerbDAO.class);
	/*for (int i = 0; i < 10; i++) {
		final Herb herb = new Herb();
		herb.setCommonName("Herb " + i);
		herbDAO.addHerb(herb);
	}*/
	for (int i = 0; i < 1000; i++) {
		final HerbFormula formula = new HerbFormula();
		formula.setId(16L + i);
		formula.setName("Formula: " + (i + 2));
		formula.setDescription("Formula" + (i + 2) + " Description");
		final List<Long> herbs = new ArrayList<Long>(2);
		herbs.add(5L);
		herbs.add(6L);
		formula.setHerbs(herbs);
		herbDAO.addFormula(formula);
	}
%>
</body>
</html>