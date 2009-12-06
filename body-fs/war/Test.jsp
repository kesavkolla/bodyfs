<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.Collection"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Calendar"%>
<%@page import="com.bodyfs.dao.IMPIDao"%>
<%@page import="org.springframework.orm.jdo.JdoTemplate"%>
<%@page import="com.bodyfs.PMF"%>
<%@page import="org.springframework.orm.jdo.JdoCallback"%>
<%@page import="java.util.Date"%>
<%@page import="javax.jdo.PersistenceManager"%>
<%@page import="com.bodyfs.model.MPIData"%>
<%@page import="javax.jdo.Query"%>
<%@page import="javax.jdo.JDOException"%>
<%@page import="org.zkoss.json.JSONArray"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
	WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(session
			.getServletContext());
	final MPIData data1 = new MPIData();
	data1.setId(1L);

	final IMPIDao mpiDao = (IMPIDao) context.getBean("MPIDao");
	final JdoTemplate jdoTemplate = new JdoTemplate(PMF.get());
	Collection<Date> dates = getExamDates(jdoTemplate, 1L);
	for (final Date dt : dates) {
		out.println(dt.getTime());
		out.println("<br />");
	}

	out.println(JSONArray.toJSONString(dates.toArray()));
	out.println("<br />");

	final MPIData data = getDataByDate(jdoTemplate, 1L, new Date(1260064722938L));
%>
<table>
	<tr>
		<td>ExamDate</td>
		<td><%=data.getExamDate().getTime()%></td>
	</tr>
	<tr>
		<td>LU1</td>
		<td><%=data.getLU1()%></td>
	</tr>
	<tr>
		<td>LU2</td>
		<td><%=data.getLU2()%></td>
	</tr>
	<tr>
		<td>P1</td>
		<td><%=data.getP1()%></td>
	</tr>
	<tr>
		<td>P2</td>
		<td><%=data.getP2()%></td>
	</tr>
</table>

</body>
</html>
<%!public MPIData getDataByDate(final JdoTemplate jdoTemplate, final Long id, final Date examDate) {
		return jdoTemplate.execute(new JdoCallback<MPIData>() {
			@Override
			public MPIData doInJdo(final PersistenceManager pm) throws JDOException {
				final Query query = pm.newQuery(MPIData.class);
				query.setUnique(true);
				query.setOrdering("examDate desc");
				query.setRange(0L, 1L);
				query.setFilter("personId==pid" + ((examDate != null) ? "&& examDate==pdate" : ""));
				query.declareParameters(Long.class.getName() + " pid"
						+ (examDate != null ? ", java.util.Date pdate" : ""));
				if (examDate != null) {
					return (MPIData) query.execute(id, examDate);
				} else {
					return (MPIData) query.execute(id);
				}
			}
		});
	}

	public Collection<Date> getExamDates(JdoTemplate jdoTemplate, final Long id) {
		return jdoTemplate.executeFind(new JdoCallback<Collection<Date>>() {
			@Override
			public Collection<Date> doInJdo(final PersistenceManager pm) throws JDOException {
				final Query query = pm.newQuery(MPIData.class);
				query.setResultClass(String.class);
				query.setOrdering("examDate desc");
				query.setResult("examDate");
				return (Collection<Date>) query.execute();
			}
		});
	}%>