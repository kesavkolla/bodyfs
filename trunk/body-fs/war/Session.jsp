<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.zkoss.zk.ui.sys.SessionsCtrl"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
	try {
		out.println("ctrl: " + SessionsCtrl.getCurrentCtrl() + "<br />");
		if (SessionsCtrl.getCurrentCtrl() != null) {
			out.println("isInvalidated: " + SessionsCtrl.getCurrentCtrl().isInvalidated() + "<br />");
		}
		out.println("Session Count: " + SessionsCtrl.getCount());
	} catch (final Exception e) {
		e.printStackTrace(response.getWriter());
	}
%>

</body>
</html>