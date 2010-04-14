<%@ page language="java" contentType="text/html" session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.bodyfs.dao.IPatientVisitDAO"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.TimeZone"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<title>Insert title here</title>
</head>
<body>
<%
	final WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this
			.getServletContext());
	final IPatientVisitDAO visitDAO = ctx.getBean(IPatientVisitDAO.class);
	final Collection<Date> dates = visitDAO.getPatientVisitDates(3L);
	final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	sdf.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
	for (final Date date : dates) {
		out.println(date.getTime());
		out.println("&nbsp;&nbsp;");
		out.println(sdf.format(date));
		out.println("<br />");
	}
%>
</body>
</html>