<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Error</title>
</head>
<body>
	 <div>
     	<h1>Error!</h1>
    	<h2>${requestScope.exception.message }</h2>
    </div>
    <div>
    	<c:choose>
			<c:when test="${ requestScope.newSession }">
				<a href="/VOB.bg/">Return to login.</a>
			</c:when>
			<c:otherwise>
				<a href="/VOB.bg/main">Return to main.</a>
			</c:otherwise>
		</c:choose>    	   
    </div>
</body>
</html>