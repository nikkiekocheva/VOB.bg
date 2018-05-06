<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="http://localhost:8080/VOB.bg/playlistvideos">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="top">  
		<%@ include file="logo.jsp" %>
	</div>
	<div id="menu">  
		<%@ include file="menu.jsp" %>
	</div>
	<!-- SHOW ALL VIDEOS IN PLAYLIST -->
	<h2>Videos in ${ playlist.name } playlist:</h2>
	<c:forEach var="video" items= "${ videos }">
		<c:if test="${ videos =! null }">
		<div style="display: inline-block; border:medium;">
			<div>
				<h3>
					${video.name}
				</h3>	
			</div>
			<div>
				<a href = "/VOB.bg/view/${video.id}"><img alt="videoImg" src="/VOB.bg/img/${ video.imagePath } " width="200" height="150" border="2"></a>
			</div>
		</div>
		</c:if>
	</c:forEach>
	
	
</body>
</html>