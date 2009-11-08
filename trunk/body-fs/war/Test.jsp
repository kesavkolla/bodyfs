<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.bodyfs.model.Person"%>
<%@page import="com.bodyfs.PMF"%>
<%@page import="com.bodyfs.model.FamilyMedHistory"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
	final Person person = PMF.get().getPersistenceManager().getObjectById(Person.class, 1);
	System.out.println(person);
	FamilyMedHistory fmh = new FamilyMedHistory();
	fmh.setPersonId(person.getId());
	fmh.setAlcoholism(true);
	fmh.setArteriosclerosis(true);
	fmh = PMF.get().getPersistenceManager().makePersistent(fmh);
	System.out.println(fmh.getId());
%>
</body>
</html>