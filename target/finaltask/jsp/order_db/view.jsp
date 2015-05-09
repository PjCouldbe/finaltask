<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
	<head>
		<title>Order Database</title>
	</head>
	
	<body>
		<h1 ALIGN="center">Список заказов</h1><br />
		<div align="right">
			<a href="http://localhost:8080/finaltask/user_db/view.html">Список пользователей</a>
		</div>
		
		<ol><%for(db.model.Order order : ${orderList}) {%>
			<li><p>
				<%= order.getFirstname()%> <%= order.getLastname()%> <%= order.getMiddlename()%>, 
				<%= order.getAge()%>
			</p></li>			
		<%}%></ol>
		
		<br />
		
		<div align="right">
			<a href="http://localhost:8080/finaltask/order_db/edit.html">Создать заказ</a>
		</div>
	</body>
</html>