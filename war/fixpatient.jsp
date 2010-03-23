<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.bodyfs.dao.IPatientVisitDAO"%>
<%@page import="java.util.Date"%>
<%@page import="com.bodyfs.model.PatientDiagnosis"%>
<%@page import="com.bodyfs.model.PatientTreatment"%>
<%@page import="com.bodyfs.model.PatientPrescription"%>
<%@page import="com.bodyfs.dao.IPersonDAO"%>
<%@page import="com.bodyfs.model.Person"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
	/*Long patid = -1L;
	try {
		patid = Long.parseLong(request.getParameter("patid"));
	} catch (final Exception e) {
		patid = -1L;
	}
	if (patid == -1L) {
		out.println("<b>Invalid patient id</b>");
		return;
	}*/
	final WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this
			.getServletContext());
	final IPersonDAO personDAO = (IPersonDAO) ctx.getBean(IPersonDAO.class);
	final IPatientVisitDAO visitDAO = (IPatientVisitDAO) ctx.getBean("patientVisitDAO");

	for (final Person person : personDAO.getAll()) {
		final Long patid = person.getId();
		for (final Date date : visitDAO.getPatientVisitDates(patid)) {
			//Check for the existance of diagnostics
			if (visitDAO.getPatientDiagnosisByDate(patid, date) == null) {
				final PatientDiagnosis diagnosis = new PatientDiagnosis();
				diagnosis.setPersonId(patid);
				diagnosis.setVisitDate(date);
				visitDAO.createPatientDiagnosis(diagnosis);
				out.println("<br />created a new diagnosis with id: " + diagnosis.getId());
			}
			//Check for treatement
			if (visitDAO.getPatientTreatmentByDate(patid, date) == null) {
				final PatientTreatment treatment = new PatientTreatment();
				treatment.setPersonId(patid);
				treatment.setVisitDate(date);
				visitDAO.createPatientTreatment(treatment);
				out.println("<br />created a new treatment wit id: " + treatment.getId());
			}
			//Check for prescription
			if (visitDAO.getPatientPrescriptionByDate(patid, date) == null) {
				final PatientPrescription presc = new PatientPrescription();
				presc.setPersonId(patid);
				presc.setVisitDate(date);
				visitDAO.createPatientPrescription(presc);
			}
		}
	}
%>
</body>
</html>