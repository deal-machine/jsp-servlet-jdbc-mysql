<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:if test="${user != null}">
		<input type="hidden" name="id" value="<c:out value='${user.id}' />" />
	</c:if>

	<fieldset >
		<h1>User Name: ${ user.name }</h1>
	</fieldset>

	<fieldset >
		<h2>User Email: ${ user.email }</h2>
	</fieldset>

	<fieldset>
		<h2>User Country: ${user.country} </h2>
			
	</fieldset>
</body>
</html>