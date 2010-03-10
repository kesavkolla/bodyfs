<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.net.URL"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="au.com.bytecode.opencsv.CSVReader"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.bodyfs.dao.IHerbDAO"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashSet"%>
<%@page import="com.bodyfs.model.Herb"%>
<%@page import="com.bodyfs.model.HerbFormula"%>
<%@page import="com.bodyfs.model.Diagnosis"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Load Herbs</title>
</head>
<body>
<%
	final WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this
			.getServletContext());
	final IHerbDAO herbDAO = ctx.getBean(IHerbDAO.class);

	try {
		LoadFormulas(herbDAO, out);
		LoadDiagnosis(herbDAO, out);
	} catch (final Exception e) {
		out.println("<pre>");
		e.printStackTrace(response.getWriter());
		out.println("</pre>");
	}
%>
</body>
</html>
<%!private void ReadFormulas(URL herbsURL, Map<String, List<String>> formulas, JspWriter out) throws Exception {
		final BufferedReader herbsin = new BufferedReader(new InputStreamReader(herbsURL.openStream()));
		final CSVReader reader = new CSVReader(herbsin);
		final String[] arrformula = reader.readNext();
		String[] data = null;
		while ((data = reader.readNext()) != null) {
			for (int i = 0; i < data.length; i++) {
				if (data[i] == null || data[i].trim().length() <= 0) {
					continue;
				}
				List<String> herbs = null;
				final String sformula = arrformula[i].trim();
				if (formulas.containsKey(sformula)) {
					herbs = formulas.get(sformula);
				} else {
					herbs = new ArrayList<String>();
					formulas.put(sformula, herbs);
				}
				herbs.add(data[i].trim());
			}
		}
		reader.close();
		herbsin.close();
	}

	private void LoadFormulas(final IHerbDAO herbDAO, JspWriter out) throws Exception {
		final Map<String, List<String>> formulas = new HashMap<String, List<String>>();
		ReadFormulas(new URL(
				"http://spreadsheets.google.com/pub?key=t9joTGekIf2xpIMk_f-EjYA&single=true&gid=0&output=csv"),
				formulas, out);
		ReadFormulas(new URL(
				"http://spreadsheets.google.com/pub?key=t9joTGekIf2xpIMk_f-EjYA&single=true&gid=1&output=csv"),
				formulas, out);
		if (formulas.size() > 0) {
			return;
		}
		final Set<String> uherbs = new HashSet();
		for (final Map.Entry<String, List<String>> formula : formulas.entrySet()) {
			for (final String herb : formula.getValue()) {
				uherbs.add(herb);
			}
		}
		final Map<String, Herb> herbsMap = new HashMap<String, Herb>(uherbs.size());
		for (final String sherb : uherbs) {
			final Herb herb = new Herb();
			herb.setName(sherb);
			herbDAO.createHerb(herb);
			herbsMap.put(sherb, herb);
		}
		uherbs.clear();

		for (final Map.Entry<String, List<String>> entry : formulas.entrySet()) {
			final HerbFormula formula = new HerbFormula();
			formula.setName(entry.getKey());
			final List<Long> herbids = new ArrayList<Long>(entry.getValue().size());
			for (final String sherb : entry.getValue()) {
				final Herb herb = herbsMap.get(sherb);
				herbids.add(herb.getId());
			}
			formula.setHerbs(herbids);
			herbDAO.createFormula(formula);
		}

	}

	private void LoadDiagnosis(final IHerbDAO herbDAO, final JspWriter out) throws Exception {
		final URL diagURL = new URL(
				"http://spreadsheets.google.com/pub?key=tAgJB_7ZPaeR8XvCBF-hkqQ&single=true&gid=0&output=csv");
		final BufferedReader herbsin = new BufferedReader(new InputStreamReader(diagURL.openStream()));
		final CSVReader reader = new CSVReader(herbsin);
		reader.readNext();
		final Map<String, Diagnosis> diagnoses = new HashMap<String, Diagnosis>();
		String[] data = null;
		while ((data = reader.readNext()) != null) {
			final String diagName = data[0].trim();
			Diagnosis diag = null;
			if (diagnoses.containsKey(diagName)) {
				diag = diagnoses.get(diagName);
			} else {
				diag = new Diagnosis();
				diagnoses.put(diagName, diag);
			}
			long formula = herbDAO.getFormulaIdByName(data[1].trim());
			if (formula > 0) {
				diag.addFormula(formula);
			}
			if (data.length > 2 && data[2] != null && data[2].trim().length() > 0) {
				diag.setDescription(data[2].trim());
			}
		}
		for (final Diagnosis diag : diagnoses.values()) {
			if (diag.getFormulas() == null || diag.getFormulas().size() <= 0) {
				continue;
			}
			herbDAO.createDiagnosis(diag);
		}
	}%>