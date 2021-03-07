<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
	<title>Home</title>
</head>
<body>
	<p><a href="${pageContext.request.contextPath }/offers">Show current offer</a></p>
	<!-- pageContext.request.contextPath : contextRoot -->
	<!-- click -> db연동 -->
	
	<p><a href="${pageContext.request.contextPath }/createoffer">Add a new offer</a></p>
	<c:if test="${pageContext.request.userPrincipal.name != null}">
		<a href="javascript:document.getElementById('logout').submit()">Logout</a>
	</c:if>
	
	<form id="logout" action="<c:url value="/logout"/>" method="post">
		<input type="hidden" name="${_csrf.parameterName }"
		value="${_csrf.token }"/>
	</form>
</body>
</html>
