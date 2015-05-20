<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>

<html>
	<head>
		<title>Change in order list</title>
		<style>
			.error {
				color: red;
			}
		</style>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	</head>
	
	<body>
		<h1>New Order</h1>
		<form:form modelAttribute="order" method="POST">
			Customer <br />
			<form:select path="customerId">
				<form:options items="${customerList}" itemLabel="data" itemValue="id" />
			</form:select>
			
			<br /><br />
			
			Saler <br />
			<form:select path="salesPersonId">
				<form:options items="${salerList}" itemLabel="data" itemValue="id" />
			</form:select>
		
			<br /><br />
		
			Goods to bye <form:input path="goods"/><br />
			<form:errors path="goods" cssClass="error"/>
			<br /><br />
		
			Total amount <form:input path="totalAmount"/><br />
			<form:errors path="totalAmount" cssClass="error"/>
			<br /><br />
			
			<input type="submit" value="Submit">
		</form:form>
	</body>
</html>