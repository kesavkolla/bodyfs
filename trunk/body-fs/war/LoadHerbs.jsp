<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.net.URL"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="au.com.bytecode.opencsv.CSVReader"%>
<%@page import="au.com.bytecode.opencsv.bean.CsvToBean"%>
<%@page import="com.bodyfs.model.Herb"%>
<%@page import="au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy"%>
<%@page import="com.bodyfs.dao.IHerbDAO"%>
<%@page import="org.zkoss.zkplus.spring.SpringUtil"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
	try {
		Load(out);
	} catch (final Exception e) {
		out.println("<pre>");
		e.printStackTrace(response.getWriter());
		out.println("</pre>");
	}
%>
</body>
</html>
<%!private void Load(final JspWriter out) throws Exception {
		final URL url = new URL(
				"http://spreadsheets.google.com/tq?tqx=out:csv&tq=select%20*&key=0AjqbPFmmDPKRdEY4ak5DUEk1dnl2REp3U3RiakZtZVE");
		final BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		CSVReader reader = new CSVReader(in);
		reader.readNext();
		reader.readNext();
		final ColumnPositionMappingStrategy<Herb> strat = new ColumnPositionMappingStrategy<Herb>();
		String columns[] = new String[] { "pinyin", "latin", "taste", "temperature", "action", "category", "meridians",
				"contraindications", "txtext", "herbtype", "commonName", "referencePage", "comment", "", "", "",
				"distributor", "inven" };
		strat.setColumnMapping(columns);
		strat.setType(Herb.class);
		CsvToBean<Herb> csv = new CsvToBean<Herb>();
		final WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this
				.getServletContext());
		final IHerbDAO herbDAO = (IHerbDAO) context.getBean("herbDAO");
		herbDAO.deleteAll();
		for (final Herb herb : csv.parse(strat, reader)) {
			out.println("Loading: " + herb.getPinyin());
			herbDAO.addHerb(herb);
			out.println("   Saved with id: " + herb.getId() + "<br />");
		}
	}%>