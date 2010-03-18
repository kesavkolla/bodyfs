<%@ page language="java" contentType="text/html" session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.bodyfs.dao.IPatientVisitDAO"%>
<%@page import="java.util.Date"%>
<%@page import="com.bodyfs.model.PatientPrescription"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<title>Insert title here</title>
</head>
<body>
<%
	final WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this
			.getServletContext());
	final IPatientVisitDAO visitDAO = ctx.getBean(IPatientVisitDAO.class);
	for (final Date visitDate : visitDAO.getPatientVisitDates(20L)) {
		final PatientPrescription presc = new PatientPrescription();
		presc.setPersonId(20L);
		presc.setVisitDate(visitDate);
		visitDAO.createPatientPrescription(presc);
	}
%>
</body>
</html>