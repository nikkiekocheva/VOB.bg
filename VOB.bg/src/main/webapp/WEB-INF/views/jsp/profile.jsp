<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<style>
.text{
color:#2e6da4;
position: relative;
    bottom: 250px;
    left: 40px;
    }
 div.gallery {
    margin: 5px;
    border: 1px solid #ccc;
    float: left;
    width: 30%;
}

div.gallery:hover {
    border: 1px solid #777;
}

div.gallery img {
    width: 100%;
    height: auto;
}

div.desc {
    padding: 15px;
    text-align: center;
}

</style>


<base href="http://localhost:8080/VOB.bg/profile">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Your Profile</title>
</head>
<body>

	<div id="menu">  
		<%@ include file="menu.jsp" %>
	</div>
	
	<form action="/VOB.bg/profile" method="post">
	<ul class="list-group" style="width:350px; position:relative; left:750px;">
            <li class="list-group-item text-muted" style="font-size: 18px; color: #2e6da4; text-align: center;">Profile</li>
            <li class="list-group-item text-right"><span class="pull-left"><strong>Username</strong></span> ${ user.username }</li>
            <li class="list-group-item text-right"><span class="pull-left"><strong>E-mail</strong></span> ${ user.email }</li>
            <li class="list-group-item text-right"><span class="pull-left"><strong>Phone number</strong></span> ${ user.phoneNumber }</li>
            <li class="list-group-item text-right"><span class="pull-left"><strong>Age</strong></span>${ user.age }</li>
            <!-- IF IT'S SESSION USER SHOW UPDATE PROFILE, OTWERWISE SHOW FOLLOW -->
				<c:choose>
			   		<c:when test="${ user.username eq sessionScope.user.username }">
			   		<br>
			   		<input type="submit" class="btn btn-primary" value="Update profile" style="position:relative;left: 120px;;">
			   	 	</c:when>    
			   	 	<c:otherwise>
			   	 	
			    	   <c:choose>
				    	   <c:when test="${	isUserFolloed }">
				    	    		<br>
				    	   		<a href="/VOB.bg/unfollow/${user.username }"><input type="button" class="btn btn-primary" value="Unfollow user" style="position:relative;left: 120px;"></a>
				    	   </c:when>
				    	   <c:otherwise>
				    	    		<br>
				    	   		<a href="/VOB.bg/follow/${user.username }"><input type="button" class="btn btn-primary" value="Follow user" style="position:relative;left: 120px;"></a>
				    	   </c:otherwise>
			    	   </c:choose>
			   		</c:otherwise>
				</c:choose>
          </ul> 
          </form>
          
	<!-- ALL UPLOADED VIDEOS OF THE USER -->
	<br>
	<h2 class="text">Uploaded videos by the user</h2>
	<c:forEach var="video" items= "${ userVideos }">
	<div style="width: 650px;">
			<div class="gallery" style="position: relative;bottom: 250px;left: 20px;">
			<a target="_blank" href="/VOB.bg/view/${video.id}">
			<img src="/VOB.bg/img/${ video.imagePath }" alt="videoImg" width="200" height="150"></a>
			<div class="desc"> ${video.name}</div>
			
			</div>
			</div>
	</c:forEach>
	
</body>
</html>