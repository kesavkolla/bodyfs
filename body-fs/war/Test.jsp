<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.bodyfs.dao.IPatientVisitDAO"%>
<%@page import="com.bodyfs.model.PatientDiagnosis"%>
<%@page import="java.util.Date"%>
<%@page import="com.bodyfs.model.PatientTreatment"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
	final WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this
			.getServletContext());
	final IPatientVisitDAO visitDAO = (IPatientVisitDAO) ctx.getBean("patientVisitDAO");
	for (final Date date : visitDAO.getPatientVisitDates(1L)) {
		final PatientTreatment treatment = new PatientTreatment();
		treatment.setPersonId(1L);
		treatment.setVisitDate(date);
		visitDAO.createPatientTreatment(treatment);
	}
%>
</body>
</html>