<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.bodyfs.dao.IHerbDAO"%>
<%@page import="com.bodyfs.model.Herb"%>
<%@page import="com.bodyfs.model.HerbFormula"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Arrays"%>
<%@page import="javax.jdo.PersistenceManagerFactory"%>
<%@page import="com.bodyfs.PMF"%>
<%@page import="javax.jdo.Query"%>
<%@page import="com.google.appengine.api.datastore.Cursor"%>
<%@page import="org.datanucleus.store.appengine.query.JDOCursorHelper"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
	final WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this
			.getServletContext());
	final PersistenceManagerFactory pmf = PMF.get();

	final Query query = pmf.getPersistenceManager().newQuery(HerbFormula.class);
	query.setRange(0, 100);
	List<HerbFormula> results = null;
	if (session.getAttribute("CURSOR_STRING") == null) {
		results = (List<HerbFormula>) query.execute();
		final Cursor cursor = JDOCursorHelper.getCursor(results);
		session.setAttribute("CURSOR_STRING", cursor.toWebSafeString());
	} else {
		final Cursor cursor = Cursor.fromWebSafeString(session.getAttribute("CURSOR_STRING").toString());
		final Map<String, Object> extensionMap = new HashMap<String, Object>();
		extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
		query.setExtensions(extensionMap);
		results = (List<HerbFormula>) query.execute();
		final Cursor cursor1 = JDOCursorHelper.getCursor(results);
		session.setAttribute("CURSOR_STRING", cursor1.toWebSafeString());
	}
	for (final HerbFormula formula : results) {
		out.println(formula.getName());
		out.println("<br />");
	}
%>
</body>
</html>