<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.io.PrintWriter"%>
<%@page import="com.google.appengine.api.datastore.DatastoreServiceFactory"%>
<%@page import="com.google.appengine.api.datastore.DatastoreService"%>
<%@page import="com.google.appengine.api.datastore.Query"%>
<%@page import="com.google.appengine.api.datastore.Entity"%>
<%@page import="java.util.List"%>
<%@page import="com.google.appengine.api.datastore.Key"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.google.appengine.api.datastore.Query.FilterOperator"%>
<%@page import="java.util.Calendar"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cleanup Sessions</title>
</head>
<body>
<%
	try {
		final DatastoreService dataService = DatastoreServiceFactory.getDatastoreService();

		for (;;) {
			final Query query = new Query("_ah_SESSION");
			try {
				final Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, 0 - Integer.parseInt(request.getParameter("days")));
				query.addFilter("_expires", Query.FilterOperator.LESS_THAN, cal.getTime().getTime());
			} catch (final Exception e) {
			}

			final List<Key> keys = new ArrayList<Key>(1000);
			for (final Entity entity : dataService.prepare(query).asIterable()) {
				keys.add(entity.getKey());
			}
			out.println("No.of entries to delete: " + keys.size());
			if (keys.size() > 0) {
				for (int i = 0, len = keys.size(); i < len; i = i + 100) {
					int start = i;
					int end = keys.size() > (i + 100) ? (i + 100) : keys.size();
					dataService.delete(keys.subList(start, end));
					out.println(String
							.format("Deleted %1$d-%2$d  Count:%3$d <br />", start, end, (end - start)));
				}
			} else {
				break;
			}
		}
	} catch (final Exception e) {
		out.println("<pre>");
		e.printStackTrace(new PrintWriter(out));
		out.flush();
		out.println("</pre>");
	}
%>
</body>
</html>