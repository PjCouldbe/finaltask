<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
	<head>
		<title>User Database</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	</head>
	
	<body>
		<h1 align="center">Список пользователей</h1><br />
		
		<div align="right">
			<a href="/order_db_view.html">Список заказов</a>
			<div>    </div>
			<a href="/order_create.html">Создать заказ</a>
		</div>
		
		<ol><%for(db.model.User user : ${userList}) {%>
			<li><p>
				<%= user.getData()%>, <%= user.getAge()%>
			</p></li>			
		<%}%></ol>
		
		<br />
		
		<div align="right">
			<a href="/user_db/edit.html">Создать пользователя</a>
		</div>
	</body>
</html>