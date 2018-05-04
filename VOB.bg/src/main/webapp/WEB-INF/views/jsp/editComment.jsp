<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<div id="top">  
		<%@ include file="logo.jsp" %>
	</div>
	<div id="menu">  
		<%@ include file="menu.jsp" %>
	</div>
	
	<h3> Enter edited comment: </h3>
	<form action="/VOB.bg/editCommentForm/${comment.id}">
		<textarea name="content" rows="4" cols="40"></textarea>
		<br>
		<input type="submit" value="Save">
	</form>
	<form action="/VOB.bg/view/${comment.videoId}">
		<input type="submit" value="Cancel">
	</form>
</body>
</html>