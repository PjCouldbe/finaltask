<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
	<head>
		<title>Order Database</title>
	</head>
	
	<body>
		<h1 ALIGN="center">Список заказов</h1><br />
		<div align="right">
			<a href="/user_db/view.html">Список пользователей</a>
		</div>
		
		<ol><%for(db.model.OrderView order : ${orderList}) {%>
			<li><p>
				<%= order.getCustomerData()%> <%= order.getSalerData()%> <%= order.getGoods()%>, 
				<%= order.getTotalAmount()%>
			</p></li>			
		<%}%></ol>
		
		<br />
		
		<div align="right">
			<a href="/order_db/edit.html">Создать заказ</a>
		</div>
	</body>
</html>