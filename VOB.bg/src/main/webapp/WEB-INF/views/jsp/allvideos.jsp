<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>All Videos</title>
	
	<style>

	select#soflow, select#soflow-color {
   -webkit-appearance: button;
   -webkit-border-radius: 2px;
   -webkit-box-shadow: 0px 1px 3px rgba(0, 0, 0, 0.1);
   -webkit-padding-end: 20px;
   -webkit-padding-start: 2px;
   -webkit-user-select: none;
   background-image: url(http://i62.tinypic.com/15xvbd5.png), -webkit-linear-gradient(#fff, #fff 40%, #fff);
   background-position: 97% center;
   background-repeat: no-repeat;
   border: 1px solid #AAA;
   color: #555;
   font-size: inherit;
   margin: 20px;
   overflow: hidden;
   padding: 5px 10px;
   text-overflow: ellipsis;
   white-space: nowrap;
   width: 300px;
}
	
	.text{
color:#2e6da4;
position: relative;
    left: 40px;
    }
	div.gallery {
    margin: 5px;
    border: 1px solid #ccc;
    float: left;
    width: 20%;
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
</head>
<body>
<div id="menu">  
		<%@ include file="menu.jsp" %>
	</div>
<div style="position:relative; left:500px">
	<!-- ALL VIDEOS -->
	<!-- ORDER BY -->
	<form action="videos" method="post" id="type">
      <div >
 			<select id="soflow" name="type" >
 			<option value="date">Order by date</option>
 			<option value="views">Order by views</option>
 			<option value="like">Order by likes</option>
		</select>
		</div>
    	<input type="submit" class="btn btn-primary" value="Order" style="position: relative;height: 25px;bottom: 46px;padding-top: 1px;left: 325px;text-align: center;">
    </form>
    
    <!-- SHOW VIDEOS -->
	<h2 class="text">Most recent</h2>
	<br><br><br>
	<div style="width: 900px; position: relative;right: 250px;">
	<c:forEach var="video" items= "${ allVideos }">
			<div class="gallery" style="position: relative;bottom:35px;left: 20px;">
			<a target="_blank" href="/VOB.bg/view/${video.id}">
			<img src="/VOB.bg/img/${ video.imagePath }" alt="videoImg" width="200" height="150"></a>
			<div class="desc"> ${video.name}</div>
			</div>
	</c:forEach>
	</div>
	<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
	<div style="width:600px">
	<!-- ALL VIDEOS OF FOLLOWING USERS -->
	<h2 class="text" >Videos of users you follow:</h2>
	<c:forEach var="type" items="${usersVideos}">
	<br>
		<h4 style="position:relative; left:180px;"> ${type.key}:</h4>
		<c:if test="${fn:length(type.value) < 1}">
		 <h5 style="position:relative; left:130px; color:#2e6da4;"> That user doesn't have videos</h5>
	</c:if>
		<div style="width:900px;">
		<c:forEach var="video" items= "${ type.value }">
			<div class="gallery" style="position: relative;top: 20px;left: 20px;">
			<a target="_blank" href="/VOB.bg/view/${video.id}">
			<img src="/VOB.bg/img/${ video.imagePath }" alt="videoImg" width="200" height="150"></a>
			<div class="desc"> ${video.name}</div>
				</div>
		</c:forEach>
		</div>
	</c:forEach>
	</div>
	</div>		 
</body>
</html>