<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>

<html>
	<head>
		<title>Order Database</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	</head>
	
	<body>
		<h1 ALIGN="center">Список заказов</h1><br />
		
		<div align="right">
			<a href="<c:url value="/user_db/view.html"/>">Список пользователей</a>
		</div>
		
		
		<table border="1">
			<tr>
				<th>Номер заказа</th>
				<th>ФИО покупателя</th>
				<th>ФИО продавца</th>
				<th>Купленные товары</th>
				<th>Общая стоимость</th>
				<th>Ссылки</th>
			</tr>
			<c:forEach items="${orderList}" var="order">
                <tr>
                	<td align="center">${order.getId()}</td>
                    <td>${order.getCustomerData()}</td> 
                	<td>${order.getSalerData()}</td>
                	<td>${order.getGoods()}</td>
                	<td>${order.getTotalAmount()}</td>
                	<td><a href="<c:url value="/order_db/update?id=${order.getId()}"/>">Изменить</a>  <a href="<c:url value="/order_db/delete?id=${order.getId()}"/>">Удалить</a></td>
                </tr>
            </c:forEach>
		</table>
		
		<br />
		
		<div align="right">
			<a href="<c:url value="/order_db/edit.html"/>">Создать заказ</a>
		</div>
	</body>
</html>