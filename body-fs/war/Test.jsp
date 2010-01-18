<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.orm.jdo.JdoTemplate"%>
<%@page import="com.bodyfs.PMF"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.sql.Date"%><meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
	final WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this
			.getServletContext());
	final JdoTemplate jdoTemplate = new JdoTemplate(PMF.get());
	final Map<String, Object> vals = new HashMap<String, Object>();
	out.println(jdoTemplate.find(PatientVisit.class, "personId==pid and visitDate==pdate",
			"Long pid, java.util.Date pdate", 1L, new Date(1262188454883L)));
%>
</body>

<%@page import="com.bodyfs.model.PatientVisit"%>
