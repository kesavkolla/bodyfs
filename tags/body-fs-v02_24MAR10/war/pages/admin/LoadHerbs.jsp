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
<%@page import="com.bodyfs.model.Diagnosis"%>
<%@page import="com.bodyfs.PMF"%>
<%@page import="com.bodyfs.model.BodyfsCounters"%>
<%@page import="net.sf.jsr107cache.Cache"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Load Herbs</title>
</head>
<body>
<form action="LoadHerbs.jsp" method="POST"><input type="submit" name="btnDelete" value="Delete Data" /> <br />
<input type="submit" name="btnLoad1" value="Load Herbs1" /> <input type="submit" name="btnLoad2" value="Load Herbs2" />
<br />
<input type="submit" name="btnInstHerbs" value="Insert Herbs" /> <input type="submit" name="btnInsFormulas"
	value="Insert Formulas" /><br />
<input type="submit" name="btnLoadDiag" value="Load Diagnosis" /> <input type="submit" name="btnInsDiag"
	value="Insert Diagnosis" /><br />
<input type="submit" name="btnClean" value="Clean Cache" /></form>
</body>
</html>
<%
	final WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this
			.getServletContext());
	final IHerbDAO herbDAO = ctx.getBean(IHerbDAO.class);
	final Cache cache = ctx.getBean(Cache.class);

	try {
		if (request.getParameter("btnDelete") != null) {
			deleteData(out);
			return;
		}
		if (request.getParameter("btnLoad1") != null) {
			final Map<String, List<String>> formulas = new HashMap<String, List<String>>();
			ReadFormulas(
					new URL(
							"http://spreadsheets.google.com/pub?key=t9joTGekIf2xpIMk_f-EjYA&single=true&gid=0&output=csv"),
					formulas, out);
			cache.put("HERBSLOADDATA", formulas);
			return;
		}
		if (request.getParameter("btnLoad2") != null) {
			final Map<String, List<String>> formulas = (Map<String, List<String>>) cache.get("HERBSLOADDATA");
			if (formulas == null) {
				out.println("Click on Load Herbs1 before this <br />");
				return;
			}
			ReadFormulas(
					new URL(
							"http://spreadsheets.google.com/pub?key=t9joTGekIf2xpIMk_f-EjYA&single=true&gid=1&output=csv"),
					formulas, out);
			cache.put("HERBSLOADDATA", formulas);
		}
		if (request.getParameter("btnInstHerbs") != null) {
			insertHerbs(herbDAO, cache, out);
			return;
		}
		if (request.getParameter("btnInsFormulas") != null) {
			LoadFormulas(herbDAO, cache, out);
			return;
		}
		if (request.getParameter("btnLoadDiag") != null) {
			LoadDiagnosis(herbDAO, cache, out);
			return;
		}
		if (request.getParameter("btnInsDiag") != null) {
			InsertDiagnosis(herbDAO, cache, out);
			return;
		}
		if (request.getParameter("btnClean") != null) {
			cleanCache(cache, out);
			return;
		}

		//LoadFormulas(herbDAO, out);
		//LoadDiagnosis(herbDAO, out);
	} catch (final Exception e) {
		out.println("<pre>");
		e.printStackTrace(response.getWriter());
		out.println("</pre>");
	}
%>
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
		out.println("Finished Reading data from: " + herbsURL.toString());
	}

	private void insertHerbs(final IHerbDAO herbDAO, final Cache cache, JspWriter out) throws Exception {
		final Map<String, List<String>> formulas = (Map<String, List<String>>) cache.get("HERBSLOADDATA");
		if (formulas == null) {
			out.println("Invoke Load Herbs2 before this");
			return;
		}
		final Set<String> uherbs = new HashSet();
		for (final Map.Entry<String, List<String>> formula : formulas.entrySet()) {
			for (final String herb : formula.getValue()) {
				uherbs.add(herb);
			}
		}
		final Map<String, Long> herbsMap = new HashMap<String, Long>(uherbs.size());
		for (final String sherb : uherbs) {
			final Herb herb = new Herb();
			herb.setName(sherb);
			herbDAO.createHerb(herb);
			herbsMap.put(sherb, herb.getId());
		}
		cache.put("HERBSMAP", herbsMap);
		out.println("Successfully loaded : " + uherbs.size() + " herbs <br />");
	}

	private void LoadFormulas(final IHerbDAO herbDAO, final Cache cache, JspWriter out) throws Exception {
		final Map<String, List<String>> formulas = (Map<String, List<String>>) cache.get("HERBSLOADDATA");
		if (formulas == null) {
			out.println("Invoke Insert Herbs before this<br />");
			return;
		}
		final Map<String, Long> herbsMap = (Map<String, Long>) cache.get("HERBSMAP");
		if (herbsMap == null) {
			out.println("Invoke Insert Herbs before this<br />");
			return;
		}
		for (final Map.Entry<String, List<String>> entry : formulas.entrySet()) {
			final HerbFormula formula = new HerbFormula();
			formula.setName(entry.getKey());
			final List<Long> herbids = new ArrayList<Long>(entry.getValue().size());
			for (final String sherb : entry.getValue()) {
				herbids.add(herbsMap.get(sherb));
			}
			formula.setHerbs(herbids);
			herbDAO.createFormula(formula);
		}
		cache.remove("HERBSLOADDATA");
		cache.remove("HERBSMAP");
		out.println("Finished creating formulas <br />");

	}

	private void LoadDiagnosis(final IHerbDAO herbDAO, final Cache cache, final JspWriter out) throws Exception {
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
				diag.setName(diagName);
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
		cache.put("DIAGNOSISDATA", diagnoses);
		out.println("Loaded diagnosis <br />");
	}

	private void InsertDiagnosis(final IHerbDAO herbDAO, final Cache cache, final JspWriter out) throws Exception {
		final Map<String, Diagnosis> diagnoses = (Map<String, Diagnosis>) cache.get("DIAGNOSISDATA");
		if (diagnoses == null) {
			out.println("Invoke Load Diagnosis before this<br />");
			return;
		}
		for (final Diagnosis diag : diagnoses.values()) {
			if (diag.getFormulas() == null || diag.getFormulas().size() <= 0) {
				continue;
			}
			herbDAO.createDiagnosis(diag);
		}
		cache.remove("DIAGNOSISDATA");
		out.println("Finished creating diagnoses<br />");
	}

	private void cleanCache(final Cache cache, final JspWriter out) throws Exception {
		if (cache.containsKey("DIAGNOSISDATA")) {
			cache.remove("DIAGNOSISDATA");
		}
		if (cache.containsKey("HERBSLOADDATA")) {
			cache.remove("HERBSLOADDATA");
		}
		if (cache.containsKey("HERBSMAP")) {
			cache.remove("HERBSMAP");
		}
		out.println("Cleanup finished<br />");
	}

	private void deleteData(final JspWriter out) throws Exception {
		PMF.get().getPersistenceManager().newQuery(Herb.class).deletePersistentAll();
		out.println("Deleted Herbs <br />");
		PMF.get().getPersistenceManager().newQuery(HerbFormula.class).deletePersistentAll();
		out.println("Deleted Formulas <br />");
		PMF.get().getPersistenceManager().newQuery(Diagnosis.class).deletePersistentAll();
		out.println("Deleted Diagnoses <br />");
		PMF.get().getPersistenceManager().newQuery(BodyfsCounters.class).deletePersistentAll();
		out.println("Deleted BodyfsCounters <br />");
	}%>