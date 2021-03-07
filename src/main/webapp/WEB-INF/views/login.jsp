<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<form action="<c:url value="login"/>" method="POST">
	<table>
		<c:if test="${not empty errorMsg}">
			<div style="color:#ff0000">
				<h3>${errorMsg }</h3>
			</div>
		</c:if>
		
		<c:if test="${not empty logoutMsg}">
			<div style="color:#0000ff">
				<h3>${logoutMsg }</h3>
			</div>
		</c:if>
		<tr>
			<td>User</td>
			<td><input type="text" name="username"/></td>
		</tr>
		<tr>
			<td>Password</td>
			<td><input type="password" name="password"/></td>
		</tr>
		<tr>
			<td colspan='2'><input name="submit" type="submit" value="Login"/></td>
		</tr>
		<tr><td>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		</td></tr>
	</table>
</form>

</body>
</html>