<%@ page language="java" contentType="text/html" session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.bodyfs.dao.IPatientVisitDAO"%>
<%@page import="com.bodyfs.model.PatientVisit"%>
<%@page import="com.bodyfs.model.PatientDiagnosis"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<title>Insert title here</title>
</head>
<body>
<%
	final WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this
			.getServletContext());
	final IPatientVisitDAO visitDAO = ctx.getBean(IPatientVisitDAO.class);
	final PatientDiagnosis diag = visitDAO.getPatientDiagnosisByDate(620L, null);
	diag.setDiagnosisData("");
	visitDAO.createPatientDiagnosis(diag);
%>
</body>
</html>