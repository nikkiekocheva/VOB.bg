<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="http://localhost:8080/VOB.bg/playlistvideos">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Playlist ${playlist.name}</title>
<style>
.text{
color:#2e6da4;
    }</style>
</head>
<body>
	<div id="menu">  
		<%@ include file="menu.jsp" %>
	</div>
	<!-- SHOW ALL VIDEOS IN PLAYLIST -->
	
	<div style="position: relative;left: 500px;">
		<h2 class="text">Videos in ${ playlist.name } playlist:</h2>
	</div>
	<div style="position: relative;left: 250px;">
		<c:forEach var="video" items= "${ videos }">
			<c:if test="${ videos =! null }">
			<div style="display: inline-block; border: 1px solid #ccc;margin: 5px;width: 20%;">
				<div style="position: relative;left: 50px;">
					<div style="padding: 15px;display: inline-block;">
						${video.name}
					</div>	
				</div>
				<div style="position: relative;left: 50px;">
					<a href = "/VOB.bg/view/${video.id}"><img alt="videoImg" src="/VOB.bg/img/${ video.imagePath } " width="200" height="150" border="2"></a>
				</div>
				<li class="list-group-item text-muted" style="font-size: 14px; color: #2e6da4; text-align: center;">
		         	<form action="/VOB.bg/removevideoplaylist" method="post">
						<input type="hidden" name="videoid" value="${ video.id }"/>
						<input type="hidden" name="playlist" value="${ playlist.name }"/>
						<input type="submit" class="btn btn-primary" value="Remove from playlist" name="addvideo" style="position: relative;height: 25px;padding-top: 1px;text-align: center;">
					</form>
          		</li>
			</div>
			</c:if>
		</c:forEach>
	</div>
	
</body>
</html>