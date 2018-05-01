<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Your Profile</title>
</head>
<body>
	<div id="top">  
		<%@ include file="logo.jsp" %>
	</div>
	<div id="menu">  
		<%@ include file="menu.jsp" %>
	</div>
	
	<form action="profile" method="post" align="center">
		<table align="center" border="12px">
			<tr>
				<td>Username: </td>
				<td>${ sessionScope.user.username }</td>
			</tr>	
			<tr>
				<td>E-mail: </td>
				<td>${ sessionScope.user.email }</td>
			</tr>
			<tr>
				<td>PhoneNumber: </td>
				<td>${ sessionScope.user.phoneNumber }</td>
			</tr>
			<tr>
				<td>Age: </td>
				<td>${ sessionScope.user.age }</td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Update profile">  </td>
			</tr>
		</table>
	</form>

 <h2>Uploaded videos by the user</h2>
	<c:forEach var="video" items= "${ userVideos }">
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

			</div>
		</div>
	</c:forEach>


</body>
</html>