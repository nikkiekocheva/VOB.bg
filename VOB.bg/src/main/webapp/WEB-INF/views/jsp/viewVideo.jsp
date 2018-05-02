<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
table, th, td {
    border: 1px solid black;
    border-collapse: collapse;
    
    
}
td {
    padding: 5px;
    width: 10%;
} table{
 	width: 32%;
 	text-align: left; 
}
</style>
</head>
<body>
	<div id="top">  
		<%@ include file="logo.jsp" %>
	</div>
	<div id="menu">  
		<%@ include file="menu.jsp" %>
	</div>
	
	<h1 >${video.name}</h1>
	<div>
		<video width="420" height="300" controls>
			<source src= "/VOB.bg/videos/${video.path}" type="video/mp4">
		</video>
	</div>
	User: ${videouser.username} <br>
	Likes:${likes}
	Dislikes:${dislikes} <br>
	Views: ${views}<br>
	
	<form action="/VOB.bg/rateVideo/${video.id}">
			<button name="button" value = "button1"> Like</button>
			<button name="button" value = "button2"> Dislike</button>	
	 </form>
	<br>
	<h3> Comments: </h3>
	<c:forEach var="comment" items= "${allComments}">
		<table>
			<tr>
					<td>${comment.formattedDate}</td>
					<td>${comment.username}</td>
			</tr>
			<tr>
					<td colspan="2"> ${comment.content}</td>
			</tr>
		</table>
		<br>
	</c:forEach>	 
</body>
</html>