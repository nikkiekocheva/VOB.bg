<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Search result</title>
</head>
<body>
	<div id="top">  
		<%@ include file="logo.jsp" %>
	</div>
	<div id="menu">  
		<%@ include file="menu.jsp" %>
	</div>
	
	<h2>Found:</h2>
	<c:forEach var="object" items="${ found }">
		<c:if test="${ type > 1 }">
			<p>${ object.name }</p>
			
		</c:if>
		<c:if test="${ type == 1 }">
			<a href="/VOB.bg/profile/${object.username }">${ object.username }</a>
		</c:if>
		
	<br><hr>
	</c:forEach>

</body>
</html>