<%@ page language="java" contentType="text/html" session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.bodyfs.dao.IPersonDAO"%>
<%@page import="org.zkoss.json.JSONObject"%>
<%@page import="org.zkoss.json.parser.JSONParser"%>
<%@page import="com.bodyfs.model.Person"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<title>Insert title here</title>
</head>
<body>
<%
	final WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this
			.getServletContext());
	final String data = "{\"PatientID\":461,\"FirstName\":\"Barbara\",\"LastName\":\"Lee\"}";
	final JSONParser parser = new JSONParser();
	final JSONObject person = (JSONObject) parser.parse(data);
	final IPersonDAO personDAO = ctx.getBean(IPersonDAO.class);
	final Person p = personDAO.getByName((String) person.get("FirstName"), (String) person.get("LastName"),
			(String) person.get("MiddleName"));
	out.println(p);
%>
</body>
</html>