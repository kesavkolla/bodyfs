<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
	final HttpSession session1 = request.getSession(false);
	if (session1 == null) {
		out.println("<h1>Session is not yet created<h1>");
	} else {
%>
<table width="100%">
	<caption>Session Items</caption>
	<tr>
		<th>Key</th>
		<th>Value</th>
	</tr>
	<tr>
		<td>Size</td>
		<td><%=getSize(session1)%></td>
	</tr>
	<%
		for (final Enumeration en = session1.getAttributeNames(); en.hasMoreElements();) {
				final String key = en.nextElement().toString();
				final Object value = session1.getAttribute(key);
				out.println("<tr><td>");
				out.println(key);
				out.println("</td><td>");
				out.println(value);
				out.println("</td></tr>");
			}
		}
	%>
</table>
</body>

<%@page import="java.util.Enumeration"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<%@page import="java.io.ObjectOutputStream"%></html>
<%!private long getSize(final HttpSession session) {
		if (session == null) {
			return 0;
		}
		try {
			final ByteArrayOutputStream stream = new ByteArrayOutputStream();
			final ObjectOutputStream out = new ObjectOutputStream(stream);
			for (final Enumeration en = session.getAttributeNames(); en.hasMoreElements();) {
				out.writeObject(session.getAttribute(en.nextElement().toString()));
			}
			out.close();
			long size = stream.size();
			stream.close();
			return size;
		} catch (final Exception e) {
			return 0;
		}
	}%>