<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

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
		<h1>Change Order</h1>
		<form:form method="POST">
			Customer <br />
			<form:select path="customerId" value="${order.getCustomerData()}">
				<form:options items="${customerList}" itemLabel="data" itemValue="id" />
			</form:select>
			
			<br /><br />
			
			Saler <br />
			<form:select path="salesPersonId" value="${order.getSalerData()}">
				<form:options items="${salerList}" itemLabel="data" itemValue="id" />
			</form:select>
		
			<br /><br />
		
			Goods to bye <form:input path="goods" value="${order.getGoods()}"/><br />
			<form:errors path="goods" cssClass="error"/>
			<br /><br />
		
			Total amount <form:input path="totalAmount" value="${order.getTotalAmount()}"/><br />
			<form:errors path="totalAmount" cssClass="error"/>
			<br /><br />
			
			<input type="submit" value="Submit">
		</form:form>
	</body>
</html>