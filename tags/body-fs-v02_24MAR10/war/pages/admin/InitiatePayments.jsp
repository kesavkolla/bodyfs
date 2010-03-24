<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.bodyfs.dao.IPaymentDAO"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
	final WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this
			.getServletContext());
	final IPaymentDAO paymentDAO = ctx.getBean(IPaymentDAO.class);
	MasterService service = new MasterService();
	service.setServiceName("a");
	service.setCharge(new Double(100));
	paymentDAO.addMasterService(service);
	out.println("Create person with id <b>" + service.getId() + "</b><br />");

	service = new MasterService();
	service.setServiceName("b");
	service.setCharge(new Double(100));
	paymentDAO.addMasterService(service);
	out.println("Create person with id <b>" + service.getId() + "</b><br />");

	service = new MasterService();
	service.setServiceName("c");
	service.setCharge(new Double(100));
	paymentDAO.addMasterService(service);
	out.println("Create person with id <b>" + service.getId() + "</b><br />");

	service = new MasterService();
	service.setServiceName("d");
	service.setCharge(new Double(100));
	paymentDAO.addMasterService(service);
	out.println("Create person with id <b>" + service.getId() + "</b><br />");

	service = new MasterService();
	service.setServiceName("e");
	service.setCharge(new Double(100));
	paymentDAO.addMasterService(service);
	out.println("Create person with id <b>" + service.getId() + "</b><br />");
%>
</body>

<%@page import="com.bodyfs.model.payments.MasterService"%></html>