<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Your Playlists</title>
	
	<style>
.text{
color:#2e6da4;
    }</style>
</head>
<body>
	<div id="menu">  
		<%@ include file="menu.jsp" %>
	</div>

	<form action="/VOB.bg/profile" method="post">
	<ul class="list-group" style="width:350px; position:relative; left:750px;">
            <li class="list-group-item text-muted" style="font-size: 18px; color: #2e6da4; text-align: center;">Your playlists:</li>
            <c:forEach var="playlist" items= "${playlists}">
            <li class="list-group-item text-muted" style="font-size: 12px; text-align: center;">
			<a href="/VOB.bg/playlist/${playlist.name}" style="color: #333;">${playlist.name}</a></li>
         </c:forEach>
          </ul> 
          </form>
      
	<br>
	<!-- MAKE NEW PLAYLIST -->
	<div style="position: relative;bottom: 235px;left: 250px;width: 600px;">
	<c:if test="${ username == sessionScope.user.username }">
	<br>
		<h2 class="text">Make new playlist:</h2>
		
		<form action="playlist" method="post">
		<div class="form-group" style="    position: relative;top: 20px; right: 80px;">
            <label class="col-md-3 control-label" style="top:10px">Playlist name:</label>
            <div class="col-md-8" style=" position:  relative; right: 50px;">
              <input class="form-control" type="text" name="name" required="">
              <div class="col-md-8" style="bottom: 48px; left: 200px;">
              <br>
              <input type="submit" class="btn btn-primary" value="Save Changes">
            </div>
            </div>
           </div>
		</form>
		
	</c:if>
	</div>
</body>
</html>