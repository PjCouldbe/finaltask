<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
	<head>
		<title>User Database</title>
	</head>
	
	<body>
		<h1 ALIGN="center">Список пользователей</h1><br />
		<div align="right">
			<a href="http://localhost:8080/finaltask/order_db_view.html">Список заказов</a>
			<div>    </div>
			<a href="http://localhost:8080/finaltask/order_create.html">Создать заказ</a>
		</div>
		
		<ol><%for(db.model.User user : ${userList}) {%>
			<li><p>
				<%= user.getFirstname()%> <%= user.getLastname()%> <%= user.getMiddlename()%>, <%= user.getAge()%>
			</p></li>			
		<%}%></ol>
	</body>
</html>