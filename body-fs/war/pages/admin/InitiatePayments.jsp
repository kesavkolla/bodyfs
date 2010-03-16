<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.bodyfs.dao.IPaymentsDAO"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
	final WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this
			.getServletContext());
	final IPaymentsDAO paymentDAO = ctx.getBean(IPaymentsDAO.class);
	MasterServices service = new MasterServices();
	service.setServiceName("a");
	service.setServiceId("SERVICE_A");
	service.setCharge(new Double(100));
	paymentDAO.addMasterService(service);
	out.println("Create person with id <b>" + service.getServiceId() + "</b><br />");
	
	service = new MasterServices();
	service.setServiceName("b");
	service.setServiceId("SERVICE_B");
	service.setCharge(new Double(100));
	paymentDAO.addMasterService(service);
	out.println("Create person with id <b>" + service.getServiceId() + "</b><br />");
	
	service = new MasterServices();
	service.setServiceName("c");
	service.setServiceId("SERVICE_C");
	service.setCharge(new Double(100));
	paymentDAO.addMasterService(service);
	out.println("Create person with id <b>" + service.getServiceId() + "</b><br />");

	service = new MasterServices();
	service.setServiceName("d");
	service.setServiceId("SERVICE_D");
	service.setCharge(new Double(100));
	paymentDAO.addMasterService(service);
	out.println("Create person with id <b>" + service.getServiceId() + "</b><br />");
	
	service = new MasterServices();
	service.setServiceName("e");
	service.setServiceId("SERVICE_E");
	service.setCharge(new Double(100));
	paymentDAO.addMasterService(service);
	out.println("Create person with id <b>" + service.getServiceId() + "</b><br />");

%>
</body>

<%@page import="com.bodyfs.model.payments.MasterServices"%></html>