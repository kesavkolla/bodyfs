<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.bodyfs.dao.IPersonDAO"%>
<%@page import="java.util.Collection"%>
<%@page import="com.bodyfs.model.PatientVisit"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Calendar"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
	WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(session
			.getServletContext());
	final IPersonDAO personDAO = (IPersonDAO) context.getBean("personDAO");
	final List<PatientVisit> visits = (List<PatientVisit>) personDAO.GetPatientVisits(1L);
	Calendar cal = Calendar.getInstance();
	cal.add(Calendar.DATE, -10);
	visits.get(0).setVisitDate(cal.getTime());
	personDAO.createPatientVisit(visits.get(0));
%>
</body>
</html>