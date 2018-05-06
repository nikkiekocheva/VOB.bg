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
	<div id="menu">  
		<%@ include file="menu.jsp" %>
	</div>
	
	<h2>Found:</h2>
	<c:forEach var="object" items="${ found }">
		<c:if test="${ type == 'user' }">
			<a href="/VOB.bg/profile/${ object.username }"> ${ object.username } </a>
		</c:if>
		
		<c:if test="${ type == 'video' }">
			<a href="/VOB.bg/views/${ object.name }"> ${ object.name } </a>
		</c:if>
		
		<c:if test="${ type == 'playlist' }">
			<p>${ object.name }</p>
			<a href="/VOB.bg/playlist/${ object.name }"> ${ object.name } </a>
		</c:if>
		
	<br><hr>
	</c:forEach>
	<c:if test=" ${ nothingfound }">
		<h5>No</h5>
	</c:if>
</body>
</html>