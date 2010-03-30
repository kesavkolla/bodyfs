<%@ page language="java" contentType="text/html" session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.bodyfs.dao.IPaymentDAO"%>
<%@page import="com.bodyfs.model.payments.PatientPaymentPlan"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<title>Insert title here</title>
</head>
<body>
<%
	final WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this
			.getServletContext());
	final IPaymentDAO paymentDAO = ctx.getBean(IPaymentDAO.class);
	final PatientPaymentPlan plan = paymentDAO.getPlayById(764L);
	plan.setArchive(false);
	plan.setActive(false);
	paymentDAO.createPaymentPlan(plan);
%>
</body>
</html>