<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User Management</title>
</head>
<body>
	<div>
		<h1>User Management</h1>
		<h2>
			<a href="new">Add new User</a>
			&nbsp;&nbsp;&nbsp;
			<a href="list">List all users</a>
		</h2>
	</div>
	<div>
		<table>
			<caption>List of users</caption>
			<tr>
				<th>Id</th>
				<th>Name</th>
				<th>Email</th>
				<th>Country</th>
				<th>Options</th>
			</tr>
			<c:forEach var="user" items="${ listUser }">
				<tr>
					<td> <c:out value="${user.id}" /> </td>
					<td> <c:out value="${user.name}" /> </td>
					<td> <c:out value="${user.email}" /> </td>
					<td> <c:out value="${user.country}" /> </td>
					<td>
						<a href="edit?id=<c:out value='${user.id}' />">Edit</a>
						&nbsp;&nbsp;&nbsp;
						<a href="delete?id=<c:out value='${user.id}' /> ">Delete</a>
					</td>
				</tr>
				</c:forEach>
		</table>
	</div>
</body>
</html>