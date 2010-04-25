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
<%@page import="com.bodyfs.Constants"%>
<%@page import="java.io.IOException"%>
<%@page import="com.bodyfs.model.payments.PatientPaymentPlan"%>
<%@page import="org.zkoss.zk.ui.Sessions"%>
<%@page import="org.zkoss.zk.ui.Session"%>
<%@page import="java.text.DecimalFormat"%><html>
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
	if($("#dateRange").length > 0) {
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
	}
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

table {
	border: 2px solid black;
	border-collapse: collapse;
}
</style>
</head>
<body>
<div style="width: 950px; margin-left: auto; margin-right: auto">
<div><img src="/img/invbanner.jpeg" alt="logo" title="logo" /></div>
<br />
<%
	//If the parameter doesn't have daterange then show the date picker other wise show the invoice page
	if (request.getParameter("dateRange") == null || request.getParameter("dateRange").trim().length() <= 0) {
%>
<form id="frmDate" action="">Select the date: <input type="text" value="Choose a Date" id="dateRange" size="25"
	name="dateRange" /> <%
 	if (CommonUtils.getPatientId(request) != null) {
 %> <input type="hidden" name="id" value="<%=CommonUtils.getPatientId(request)%>" /> <%
 	}
 %> <input type="submit" value="Go" /></form>
</div>
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
	final DecimalFormat numFormat = new DecimalFormat("#,##0.0#");
	final IPersonDAO personDAO = ctx.getBean(IPersonDAO.class);
	final IPaymentDAO paymentDAO = ctx.getBean(IPaymentDAO.class);

	//Get all the required data
	final Person person = personDAO.getPerson(patid);
	final GeneralInfo ginfo = personDAO.getGeneralInfo(patid);
	final String[] arrDates = request.getParameter("dateRange").split("-");
	final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	final SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	final Date startDate = sdf1.parse(arrDates[0].trim() + " 00:00:00");
	Date endDate = null;
	if (arrDates.length > 1) {
		endDate = sdf1.parse(arrDates[1].trim() + " 23:59:59");
	} else {
		endDate = sdf1.parse(arrDates[0].trim() + " 23:59:59");
	}
	final Collection<PatientPaymentPlan> plan = paymentDAO.getAllPlans(patid);
	if (plan.size() <= 0) {
		out.println("<b>Can not find any payment plnas for this date range</b>");
		out.println("</body></html>");
		return;
	}

	final Collection<PatientService> patServices = paymentDAO.getServicesByDateRange(patid, startDate, endDate);
	final Collection<MasterService> serviceList = paymentDAO.getAllServices();
	float discount = plan.iterator().next().getDiscount();
%>

<table width="950px" border="1" cellpadding="10">
	<tr>
		<td><span class="label">Patient Name: </span><%=person.getDisplayName()%> <br />
		<span class="label">Account Number:</span><%=person.getId()%><br />
		<span class="label">Address:</span><%=getAddress(ginfo)%>
		</td>
		<td><span class="label">Tax ID:</span>20-3596403<br />
		<span class="label">Provider Id:</span>AC 9907<br />
		<span class="label">Diagnosis Codes:</span>719.49 (Pain in Joint, Multiple Sites)</td>
	</tr>
</table>
<br />
<br />
<table width="950px" border="1" cellpadding="10">
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
			double totalCharge = 0;
			for (final PatientService patService : patServices) {
				final MasterService mservice = getService(patService.getServiceId(), serviceList);
				if (mservice == null) {
					continue;
				}
				totalCharge += printService(patService, mservice, sdf, numFormat, out);
			}
		%>
	</tbody>
</table>
<br />
<br />
<table width="425px" style="margin-left: auto;" border="1" cellpadding="10">
	<tr>
		<td>Sub Total</td>
		<td style='text-align: right;'>$<%=numFormat.format(totalCharge)%></td>
	</tr>
	<tr>
		<td>Discount %</td>
		<td style='text-align: right;'><%=discount%>%</td>
	</tr>
	<tr>
		<td>Total Payments</td>
		<td style='text-align: right;'>$<%=numFormat.format(totalCharge * (1 - discount / 100.0))%></td>
	</tr>
</table>
<br />
<br />
<div style="text-align: center; width: 950px">
<button onclick="window.print()">Print</button>
</div>
</div>
</body>
</html>
<%!private String getAddress(final GeneralInfo ginfo) {
		if (ginfo == null) {
			return "";
		}
		final StringBuilder buffer = new StringBuilder();
		if (ginfo.getStreet() != null) {
			buffer.append(ginfo.getStreet() +"<br />");
		}
		buffer.append(" &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ");
		buffer.append(ginfo.getCity()== null ? " " : ginfo.getCity());
		buffer.append(ginfo.getState()== null ? "" : ", "+ginfo.getState());
		buffer.append(ginfo.getZipcode()== null ? "" : " - "+ginfo.getZipcode() );
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

	private double printService(final PatientService patientService, final MasterService mservice,
			final SimpleDateFormat sdf, final DecimalFormat numFormat, final JspWriter out) throws IOException {
		final StringBuilder buffer = new StringBuilder();
		final double total = mservice.getCharge() * patientService.getNumServices();
		//Accupuncture codes 97810 + 97811
		if (mservice.getServiceName().equalsIgnoreCase("Acupuncture")
				|| mservice.getServiceName().equalsIgnoreCase("cupping")) {
			if (total > 45) {
				//split needed
				buffer.append("<tr>");
				buffer.append("<td>").append(sdf.format(patientService.getVisitDate())).append("</td>");
				buffer.append("<td>").append(mservice.getServiceName()).append("</td>");
				buffer.append("<td>97810</td>");
				buffer.append("<td>").append("1.0").append("</td>");
				buffer.append("<td style='text-align: right;'>").append("45.0").append("</td>");
				buffer.append("<td style='text-align: right;'>").append("45.0").append("</tr>");
				buffer.append("</tr>");

				//(total - 45)/35 will give the quantity of remaining
				double qty = (total - 45) / 35;
				buffer.append("<tr>");
				buffer.append("<td>").append(sdf.format(patientService.getVisitDate())).append("</td>");
				buffer.append("<td>").append(mservice.getServiceName()).append("</td>");
				buffer.append("<td>97811</td>");
				buffer.append("<td>").append(qty).append("</td>");
				buffer.append("<td style='text-align: right;'>").append("35.0").append("</td>");
				buffer.append("<td style='text-align: right;'>").append((qty * 35)).append("</tr>");
				buffer.append("</tr>");

			} else {
				buffer.append("<tr>");
				buffer.append("<td>").append(sdf.format(patientService.getVisitDate())).append("</td>");
				buffer.append("<td>").append(mservice.getServiceName()).append("</td>");
				buffer.append("<td>97810</td>");
				buffer.append("<td>").append(patientService.getNumServices()).append("</td>");
				buffer.append("<td style='text-align: right;'>").append(mservice.getCharge()).append("</td>");
				buffer.append("<td style='text-align: right;'>").append(numFormat.format(total)).append("</tr>");
				buffer.append("</tr>");
			}
			out.println(buffer.toString());
			return total;
		}

		buffer.append("<tr>");
		buffer.append("<td>").append(sdf.format(patientService.getVisitDate())).append("</td>");
		buffer.append("<td>").append(mservice.getServiceName()).append("</td>");
		if (mservice.getServiceName().equalsIgnoreCase("Re-Exam")) {
			buffer.append("<td>97810</td>");
		} else if (mservice.getServiceName().equalsIgnoreCase("Herbal Treatment")) {
			buffer.append("<td>99070</td>");
		} else if (mservice.getServiceName().equalsIgnoreCase("Powder")) {
			buffer.append("<td>99070</td>");
		} else if (mservice.getServiceName().equalsIgnoreCase("Capsul")) {
			buffer.append("<td>99070</td>");
		} else {
			buffer.append("<td>99070</td>");
		}

		buffer.append("<td>").append(patientService.getNumServices()).append("</td>");
		buffer.append("<td style='text-align: right;'>").append(mservice.getCharge()).append("</td>");
		buffer.append("<td style='text-align: right;'>").append(numFormat.format(total)).append("</tr>");
		buffer.append("</tr>");
		out.println(buffer.toString());
		return total;

	}%>