<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
	<head>
		<title>Change in user list</title>
		<style>
			.error {
				color: red;
			}
		</style>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	</head>
	
	<body>
		<h1>New User</h1>
		<form:form modelAttribute="user" method="POST">
			Firstname: <form:input path="firstname"/><br />
			<form:errors path="firstname" cssClass="error"/>
			<br /><br />
				
			Lastname <form:input path="lastname"/><br />
			<form:errors path="lastname" cssClass="error"/>
			<br /><br />
		
			Middlename <form:input path="middlename"/><br />
			<form:errors path="middlename" cssClass="error"/>
			<br /><br />
			
			Age <form:input path="age"/><br />
			<form:errors path="age" cssClass="error"/>
			<br /><br />
			
			<input type="submit" value="Submit">
		</form:form>
	</body>
</html>