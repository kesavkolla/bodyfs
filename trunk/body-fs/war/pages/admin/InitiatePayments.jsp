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
	{
		final MasterService service = new MasterService();
		service.setServiceName("Cupping");
		service.setCharge(115.0);
		paymentDAO.addMasterService(service);
		out.println("Added service with id: " + service.getId());
	}

	{
		final MasterService service = new MasterService();
		service.setServiceName("Acupuncture");
		service.setCharge(80.0);
		paymentDAO.addMasterService(service);
		out.println("Added service with id: " + service.getId());
	}

	{
		final MasterService service = new MasterService();
		service.setServiceName("Massage");
		service.setCharge(35.0);
		paymentDAO.addMasterService(service);
		out.println("Added service with id: " + service.getId());
	}

	{
		final MasterService service = new MasterService();
		service.setServiceName("Re-Exam");
		service.setCharge(75.0);
		paymentDAO.addMasterService(service);
		out.println("Added service with id: " + service.getId());
	}

	{
		final MasterService service = new MasterService();
		service.setServiceName("Herbal Treatment");
		service.setCharge(12.0);
		paymentDAO.addMasterService(service);
		out.println("Added service with id: " + service.getId());
	}

	{
		final MasterService service = new MasterService();
		service.setServiceName("Powder");
		service.setCharge(12.0);
		service.setHidden(true);
		paymentDAO.addMasterService(service);
		out.println("Added service with id: " + service.getId());
	}

	{
		final MasterService service = new MasterService();
		service.setServiceName("Pill");
		service.setCharge(12.0);
		service.setHidden(true);
		paymentDAO.addMasterService(service);
		out.println("Added service with id: " + service.getId());
	}

	{
		final MasterService service = new MasterService();
		service.setServiceName("Capsule");
		service.setCharge(36.0);
		service.setHidden(true);
		paymentDAO.addMasterService(service);
		out.println("Added service with id: " + service.getId());
	}

	{
		final MasterService service = new MasterService();
		service.setServiceName("Other");
		service.setCharge(12.0);
		service.setHidden(true);
		paymentDAO.addMasterService(service);
		out.println("Added service with id: " + service.getId());
	}
%>
</body>

<%@page import="com.bodyfs.model.payments.MasterService"%></html>