<html>
	<head>
		<title>Change in order list</title>
		<style>
			.error {
				color: red;
			}
		</style>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	</head>
	
	<body>
		<h1>New Order</h1>
		<form:form modelAttribute="order" method="POST" action="order_db/edit.html">
			Customer <br />
			<form:select path="customer">
				<form:options items="${customerList}" itemLabel="data" itemValue="id" />
			</form:select>
			
			Saler <br />
			<form:select path="customer">
				<form:options items="${salerList}" itemLabel="data" itemValue="id" />
			</form:select>
		
			Goods to bye <form:input path="goods"/><br />
			<form:errors path="goods" cssClass="error"/>
			<br /><br />
		
			Total amount <form:input path="totalAmount"/><br />
			<form:errors path="totalAmount" cssClass="error"/>
			<br /><br />
			
			<input type="submit" value="Create">
		</form:form>
	</body>
</html>