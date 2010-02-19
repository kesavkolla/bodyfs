<%@ page language="java" contentType="text/html" session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.bodyfs.dao.IHerbDAO"%>
<%@page import="com.bodyfs.dao.ICounterDAO"%>
<%@page import="com.bodyfs.model.Diagnosis"%>
<%@page import="java.util.Arrays"%>
<%@page import="org.zkoss.json.JSONObject"%>
<%@page import="org.zkoss.json.JSONArray"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<title>Insert title here</title>
</head>
<body>
<%
	final WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this
			.getServletContext());
	final JSONObject obj = new JSONObject();
	obj.put("id", 10);
	obj.put("name", "Kesav kolla");
	JSONArray arr = new JSONArray();
	for (int i = 0; i < 10; i++) {
		JSONObject o = new JSONObject();
		o.put("id", i);
		o.put("name", "Name: " + i);
		arr.add(o);
	}
	obj.put("formulas", arr);
	out.println(obj.toJSONString());
%>
</body>
</html>