<html>
	<head>
		<title>Change in order list</title>
		<style>
			.error {
				color: red;
			}
		</style>
	</head>
	
	<body>
		<h1>New Order</h1>
		<form:form modelAttribute="order" method="POST" action="order_db/edit.html">
			Customer <br />
			<form:select path="customer">
				<form:options items="${customerList}" itemLabel="lastname" itemValue="id" />
			</form:select>
			
			Saler <br />
			<form:select path="customer">
				<form:options items="${salerList}" itemLabel="lastname" itemValue="id" />
			</form:select>
		
			Total amount <form:input path="totalAmount"/><br />
			<form:errors path="totalAmount" cssClass="error"/>
			<br /><br />
			
			<input type="submit" value="Create">
		</form:form>
	</body>
</html>