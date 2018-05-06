<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="http://localhost:8080/VOB.bg/viewVideo">
<style>
table, th, td {
	border: 1px solid black;
	border-collapse: collapse;
}

td {
	padding: 5px;
	width: 10%;
}

table {
	width: 32%;
	text-align: left;
}
</style>
</head>
<body>
	<div id="menu">  
		<%@ include file="menu.jsp" %>
	</div>

	<!-- VIDEO -->
	<h1>${video.name}</h1>
	<div>
		<video width="420" height="300" controls> <source
			src="/VOB.bg/videos/${video.path}" type="video/mp4"></video>
	</div>
	User: <a href = "/VOB.bg/profile/${ videouser.username }">${videouser.username}</a>
	<br> Likes:${likes} Dislikes:${dislikes}
	<br> Views: ${video.views}
	<br>

	<form action="/VOB.bg/videotoplaylist" method="post">
		<input type="hidden" name="videoid" value="${ video.id }"/>
		<input type="submit" name="addvideo" value="Add to playlist" />
	</form>

	<!-- LIKE/DISLIKE BUTTON -->
	<form action="/VOB.bg/rateVideo/${video.id}">
		<button name="button" value="buttonlike">Like</button>
		<button name="button" value="buttondislike">Dislike</button>
	</form>
	<br>
	
	<!-- ADD COMMENT -->
	<h4>Add a comment:</h4>
	<form action="/VOB.bg/addComment/${video.id}">
	<textarea name="comment" rows="3" cols="50" required></textarea>
	<br>
	<input type="submit">
	</form>

	<!-- COMMENTS -->
	<h3>Comments:</h3>
	<c:forEach var="comment" items="${allComments}">
		<table>
			<tr>
				<td>${comment.formattedDate}</td>
				<td>${comment.username}</td>
				<c:set var = "userId" scope = "session" value = "${videouser.id}"/>
			<c:if test ="${comment.userId == user.id}">
				<form action="/VOB.bg/editComment/${comment.id}">
					<td style="text-align:center">
						<button>Edit</button>
					</td>
					</form>
					<form action="/VOB.bg/deleteComment/${comment.id}">
					<td style="text-align:center">
						<button>Delete</button>
					</td>
				</form>
			</c:if>
			</tr>
			<tr>
				<td colspan="4">${comment.content}</td>
			</tr>
			
		</table>
		Likes:${comment.likes}
		Dislikes:${comment.dislikes}
			<form action="/VOB.bg/rateComment/${comment.id}">
			<button name="commentButton" value="commentButtonLike">Like</button>
			<button name="commentButton" value="commentButtonDislike">Dislike</button>
			
		</form><br>
		
	</c:forEach>
	

</body>
</html>