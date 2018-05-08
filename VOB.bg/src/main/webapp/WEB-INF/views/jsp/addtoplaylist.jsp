<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add video to playlist</title>
</head>
<body>
<div id="menu">  
	<%@ include file="menu.jsp" %>
</div>
<h3>Select witch playlist you want to add the video to:</h3>
    <form action="/VOB.bg/addtoplaylist" id="list" method="post">
    	<select name="list" form="list">
 			<c:forEach var="playlist" items= "${ allplaylists }">
 				<option value="${ playlist.name }">${ playlist.name }</option>
 			</c:forEach>
		</select>
    	<input type="submit" value="Add">
    </form>

</body>
</html>