<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>All Videos</title>
</head>
<body>
	<div id="top">  
		<%@ include file="logo.jsp" %>
	</div>
	<div id="menu">  
		<%@ include file="menu.jsp" %>
	</div>

	<h2>Most Recent</h2>
	
	<form action="videos" method="post" id="type">
    	<select name="type">
 			<option value="date">Order by date</option>
 			<option value="views">Order by views</option>
 			<option value="like">Order by likes</option>
		</select>
    	<input type="submit" value="Order">
    </form>
    
	<c:forEach var="video" items= "${ allVideos }">
		<div>
			<div>
				<h3>
		<a href = "view/${video.id}">${video.name}</a>
				</h3>	
			</div>
			<div>
				<video width="220" height="150" controls>
					 <source src= "videos/${video.path}" type="video/mp4">
					 Your browser does not support the videotag. 
				</video>
				<form action="videotoplaylist" method="post">
					<input type="hidden" name="videoid" value="${ video.id }"/>
					<input type="submit" name="addvideo" value="Add to playlist" />
				</form>
			</div>
		</div>
	</c:forEach>
</body>
</html>