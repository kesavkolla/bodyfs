<%@ page language="java" contentType="text/html" session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.bodyfs.dao.IPaymentDAO"%>
<%@page import="java.util.Collection"%>
<%@page import="com.bodyfs.model.payments.PatientService"%>
<%@page import="java.util.Date"%>
<%@page import="com.bodyfs.model.payments.MasterService"%>
<%@page import="org.zkoss.json.JSONArray"%>
<%@page import="org.zkoss.json.JSONObject"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<title>Insert title here</title>
</head>
<body>
<%
	final WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this
			.getServletContext());
	final IPaymentDAO paymentDAO = (IPaymentDAO) ctx.getBean(IPaymentDAO.class);
	final Collection<PatientService> services = paymentDAO.getServicesByVisitDate(1L, new Date(1268712381789L));
	out.println(paymentDAO.getMasterServicesList());
	out.println("<br />");
	out.println(services);
	out.println("<br />");
	out.println(getServiceData(services, paymentDAO.getMasterServicesList()));
%>
</body>
</html>
<%!@SuppressWarnings("unchecked")
	private String getServiceData(final Collection<PatientService> services,
			final Collection<MasterService> servicesList) {
		final JSONArray arrServices = new JSONArray();
		for (final PatientService service : services) {
			// Check this service exists in servicesList
			boolean exists = false;
			for (final MasterService mservice : servicesList) {
				System.out.println("sid: " + service.getServiceId() + "\tmid: " + mservice.getId());
				if (service.getServiceId().equals(mservice.getId())) {
					exists = true;
					break;
				}
			}
			if (exists) {
				final JSONObject obj = new JSONObject();
				obj.put("id", service.getServiceId());
				obj.put("count", service.getNumServices());
				arrServices.add(obj);
			}
		}
		return arrServices.toJSONString();
	}%>