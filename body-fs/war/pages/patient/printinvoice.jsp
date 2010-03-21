<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.bodyfs.ui.util.CommonUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.bodyfs.dao.IPersonDAO"%>
<%@page import="com.bodyfs.dao.IPaymentDAO"%>
<%@page import="com.bodyfs.model.Person"%>
<%@page import="com.bodyfs.model.GeneralInfo"%>
<%@page import="java.util.Collection"%>
<%@page import="com.bodyfs.model.payments.PatientService"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.bodyfs.model.payments.MasterService"%>
<%@page import="java.util.List"%>
<%@page import="java.io.IOException"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Print Invoice</title>
<link rel="stylesheet" href="/css/ui.daterangepicker.css" type="text/css" />
<link rel="stylesheet" href="/css/redmond/jquery-ui-1.7.2.custom.css" type="text/css" title="ui-theme" />
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/jquery-ui.min.js"></script>
<script type="text/javascript" src="/scripts/daterangepicker.jQuery.js"></script>
<script type="text/javascript">
jQuery(function($) {
	$("#dateRange").daterangepicker({
		datepickerOptions: {
			changeMonth: true
			, changeYear: true
			, maxDate: '+0D'
 		}
		, dateFormat: "mm/dd/yy"
		, latestDate: "today"
		, closeOnSelect: true
	});
	$("#frmDate").submit(function() {
		if($("#dateRange").val() == "Choose a Date") {
			alert("Please specfify the date range");
			return false;
		}
	});
});
</script>
<style type="text/css">
body {
	font-family: Arial;
	font-size: 12pt;
}

* {
	font-size: 12pt;
	font-family: Arial;
}

.label {
	font-weight: bolder;
}
</style>
</head>
<body>
<div><img src="/img/invbanner.jpeg" alt="logo" title="logo" /></div>
<br />
<%
	//If the parameter doesn't have daterange then show the date picker other wise show the invoice page
	if (request.getParameter("dateRange") == null || request.getParameter("dateRange").trim().length() <= 0) {
%>
<form id="frmDate" action="">Select the date: <input type="text" value="Choose a Date" id="dateRange" size="25"
	name="dateRange" /> <%
 	if (request.getParameter("id") != null) {
 %> <input type="hidden" name="id" value="<%=request.getParameter("id")%>" /> <%
 	}
 %> <input type="submit" value="Go" /></form>
</body>
</html>
<%
	return;
	}
%>
<%-- Do the invoice calculations  --%>
<%--Validate the patient id --%>
<%
	final Long patid = CommonUtils.getPatientId(request);
	if (patid == null) {
		out.println("Error in retrieving patinet id </body></html>");
		return;
	}

	//Get the spring context
	final WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this
			.getServletContext());
	final IPersonDAO personDAO = ctx.getBean(IPersonDAO.class);
	final IPaymentDAO paymentDAO = ctx.getBean(IPaymentDAO.class);

	//Get all the required data
	final Person person = personDAO.getPerson(patid);
	final GeneralInfo ginfo = personDAO.getGeneralInfo(patid);
	final String[] arrDates = request.getParameter("dateRange").split("-");
	final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	final Date startDate = sdf.parse(arrDates[0].trim());
	Date endDate = null;
	if (arrDates.length > 1) {
		endDate = sdf.parse(arrDates[1].trim());
	}
	final Collection<PatientService> patServices = paymentDAO.getServicesByDateRange(patid, startDate, endDate);
	final Collection<MasterService> serviceList = paymentDAO.getAllServices();
%>
<table width="950px" style="border: 2px solid black; border-collapse: collapse" border="1" cellpadding="10">
	<tr>
		<td><span class="label">Patient Name: </span><%=person.getDisplayName()%> <br />
		<span class="label">Address:</span><%=getAddress(ginfo)%><br />
		<span class="label">Account Number:</span><%=person.getId()%></td>
		<td><span class="label">Tax ID:</span>20-3596403<br />
		<span class="label">Provider Id:</span>AC 9907<br />
		<span class="label">Diagnosis Codes:</span>719.49 (Pain in Joint, Multiple Sites)</td>
	</tr>
</table>
<br />
<br />
<table width="950px" style="border: 2px solid black; border-collapse: collapse" border="1" cellpadding="10">
	<thead>
		<tr>
			<th>Service Date</th>
			<th>Description</th>
			<th>Code</th>
			<th>Qty</th>
			<th>Unit Charge</th>
			<th>Total</th>
		</tr>
	</thead>
	<tbody>
		<%
			for (final PatientService patService : patServices) {
				final MasterService mservice = getService(patService.getServiceId(), serviceList);
				if (mservice == null) {
					continue;
				}
				printService(patService, mservice, sdf, out);
			}
		%>
	</tbody>
</table>
<br />
<br />
<div style="text-align: center; width: 950px">
<button onclick="window.print()">Print</button>
</div>
</body>
</html>
<%!private String getAddress(final GeneralInfo ginfo) {
		if (ginfo == null) {
			return "";
		}
		final StringBuilder buffer = new StringBuilder();
		if (ginfo.getStreet() != null) {
			buffer.append(ginfo.getStreet());
		}
		return buffer.toString();
	}

	private MasterService getService(final Long serviceId, final Collection<MasterService> serviceList) {
		for (final MasterService mservice : serviceList) {
			if (mservice.getId().equals(serviceId)) {
				return mservice;
			}
		}
		return null;
	}

	private void printService(final PatientService patientService, final MasterService mservice,
			final SimpleDateFormat sdf, final JspWriter out) throws IOException {
		final StringBuilder buffer = new StringBuilder();
		final double total = mservice.getCharge() * patientService.getNumServices();
		System.out.println(mservice.getServiceName());
		//Accupuncture codes 97810 + 97811
		if (mservice.getServiceName().equalsIgnoreCase("Acupuncture")
				|| mservice.getServiceName().equalsIgnoreCase("cupping")) {
			if (total > 45) {
				//split needed
				buffer.append("<tr>");
				buffer.append("<td>").append(sdf.format(patientService.getVisitDate())).append("</td>");
				buffer.append("<td>").append(mservice.getServiceName()).append("</td>");
				buffer.append("<td>9780</td>");
				buffer.append("<td>").append("1.0").append("</td>");
				buffer.append("<td>").append("45.0").append("</td>");
				buffer.append("<td>").append("45.0").append("</tr>");
				buffer.append("</tr>");

				//(total - 45)/35 will give the quantity of remaining
				double qty = (total - 45) / 35;
				buffer.append("<tr>");
				buffer.append("<td>").append(sdf.format(patientService.getVisitDate())).append("</td>");
				buffer.append("<td>").append(mservice.getServiceName()).append("</td>");
				buffer.append("<td>9781</td>");
				buffer.append("<td>").append(qty).append("</td>");
				buffer.append("<td>").append("35").append("</td>");
				buffer.append("<td>").append((qty * 35)).append("</tr>");
				buffer.append("</tr>");

			} else {
				buffer.append("<tr>");
				buffer.append("<td>").append(sdf.format(patientService.getVisitDate())).append("</td>");
				buffer.append("<td>").append(mservice.getServiceName()).append("</td>");
				buffer.append("<td>9780</td>");
				buffer.append("<td>").append(patientService.getNumServices()).append("</td>");
				buffer.append("<td>").append(mservice.getCharge()).append("</td>");
				buffer.append("<td>").append(total).append("</tr>");
				buffer.append("</tr>");
			}
			out.println(buffer.toString());
			return;
		}

		if (mservice.getServiceName().equalsIgnoreCase("pill")) {
			buffer.append("<tr>");
			buffer.append("<td>").append(sdf.format(patientService.getVisitDate())).append("</td>");
			buffer.append("<td>").append(mservice.getServiceName()).append("</td>");
			buffer.append("<td>99070</td>");
			buffer.append("<td>").append(patientService.getNumServices()).append("</td>");
			buffer.append("<td>").append(mservice.getCharge()).append("</td>");
			buffer.append("<td>").append(total).append("</tr>");
			buffer.append("</tr>");
			out.println(buffer.toString());
			return;
		}

	}%>