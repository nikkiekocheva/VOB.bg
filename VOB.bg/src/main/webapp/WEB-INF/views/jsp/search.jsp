<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
	
	<ul class="list-group" style="width:350px; position:relative; left:450px;">
	 <li class="list-group-item text-muted" style="font-size: 18px; color: #2e6da4; text-align: center;"> Found:</li>
		<c:if test="${fn:length(found) < 1}">
				 <h4 style="font-size: 18px; color: #2e6da4; text-align: center;"> Sorry, none results.</h4>
			</c:if>
		<c:forEach var="object" items="${ found }">
			
		<c:if test="${ type == 'user' }">
		 <li class="list-group-item text-muted" style="font-size: 18px; color: #2e6da4; text-align: center;">
			<a href = "/VOB.bg/profile/${ object.username }"> ${ object.username }</a></li>
		</c:if>
		<c:if test="${ type == 'video' }">
		<li class="list-group-item text-muted" style="font-size: 18px; color: #2e6da4; text-align: center;">
			<a href="/VOB.bg/views/${ object.name }"> ${ object.name } </a>
			</li>
		</c:if>
		
		<c:if test="${ type == 'playlist' }">
			<li class="list-group-item text-muted" style="font-size: 18px; color: #2e6da4; text-align: center;"><a href="/VOB.bg/playlist/${ object.name }"> ${ object.name } </a>
		</li>
		</c:if>
	</c:forEach>
	</ul>
</body>
</html>