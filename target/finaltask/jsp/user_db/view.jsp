<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>

<html>
	<head>
		<title>User Database</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	</head>
	
	<body>
		<h1 align="center">Список пользователей</h1><br />
		
		<div align="right">
			<a href="<c:url value="/order_db/view.html"/>">Список заказов</a>
			<div>    </div>
			<a href="<c:url value="/order_db/edit.html"/>">Создать заказ</a>
		</div>

		<table border="1">
			<tr>
				<th>Номер п/п</th>
				<th>ФИО пользователя</th>
				<th>Возраст</th>
				<th>Ссылки</th>
			</tr>
			<c:forEach items="${userList}" var="user">
                <tr>
                	<td align="center">${user.getId()}</td>
                    <td>${user.getData()}</td> 
                	<td>${user.getAge()}</td>
                	<td><a href="<c:url value="/user_db/update?id=${user.getId()}"/>">Изменить</a>  <a href="<c:url value="/user_db/delete.html?id=${user.getId()}"/>">Удалить</a></td>
                </tr>
            </c:forEach>
		</table>
		
		<br />
		
		<div align="right">
			<a href="<c:url value="/user_db/edit.html"/>">Создать пользователя</a>
		</div>
	</body>
</html>