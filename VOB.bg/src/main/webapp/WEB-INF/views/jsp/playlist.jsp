<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Your Playlist</title>
</head>
<body>
	<div id="top">  
		<%@ include file="logo.jsp" %>
	</div>
	<div id="menu">  
		<%@ include file="menu.jsp" %>
	</div>
	
	<c:if test="${ username == sessionScope.user.username }">
		<h2>Make new playlist</h2>
		<form action="playlist" method="post">
			Playlist name: <input type="text" name="name" required/> <br>
			<input type="submit"/>
		</form>
	</c:if>
	
	<h2>Videos in ${ username } palylist</h2>
	<c:forEach var="video" items= "${ videos }">
		<c:if test="${ videos =! null }">
			<div>
				<div>
					<h3>
							${video.name}
					</h3>	
				</div>
				<div>
					<video width="220" height="200" controls>
						 <source src= "/VOB.bg/videos/${video.path }" type="video/mp4"> 
						 Your browser does not support the videotag. 
					</video>
					<form action="removevideoplaylist" method="post">
						<input type="hidden" name="videoid" value="${ video.id }"/>
						<input type="submit" name="removevideo" value="Remove from playlist" />
					</form>
				</div>
			</div>
		</c:if>
	</c:forEach>
	
	
	
</body>
</html>