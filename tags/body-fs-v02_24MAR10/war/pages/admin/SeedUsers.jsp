<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.bodyfs.dao.IPersonDAO"%>
<%@page import="com.bodyfs.model.Person"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.bodyfs.model.Gender"%>
<%@page import="com.bodyfs.model.LoginInfo"%>
<%@page import="com.bodyfs.model.PersonType"%>
<%@page import="com.bodyfs.dao.ILoginDAO"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
	final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	final WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this
			.getServletContext());
	final IPersonDAO personDAO = ctx.getBean(IPersonDAO.class);
	final Person person = new Person();
	person.setFirstName("Administrator");
	person.setLastName("BodyFengShui");
	person.setEmail("kesavkolla+bodyfs@gmail.com");
	person.setDateOfBirth(sdf.parse("08/14/1973"));
	person.setGender(Gender.MALE);
	person.setSSN("111-11-1111");
	person.setPersonType(PersonType.EMPLOYEE);

	personDAO.createPerson(person);
	out.println("Create person with id <b>" + person.getId() + "</b><br />");

	final LoginInfo login = new LoginInfo();
	login.setPersonId(person.getId());
	login.setUserid("admin");
	login.setPassword("hliferocks");
	final ILoginDAO loginDAO = ctx.getBean(ILoginDAO.class);
	loginDAO.createNewLogin(login);
	out.println("Created login");
%>
</body>
</html>