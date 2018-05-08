<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add video to playlist</title>


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
    left: 400px;
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
<h3 class="text">Select which playlist you want to add the video in:</h3>

<div>
   <form action="/VOB.bg/addtoplaylist" id="list" method="post">
      <div style="position:relative; left:450px">
 		<select name="list" form="list" id="soflow">
 		<c:forEach var="playlist" items= "${ allplaylists }">
 				<option value="${ playlist.name }">${ playlist.name }</option>
 			</c:forEach>
		</select>
		</div>
    	<input type="submit" class="btn btn-primary" value="Add" style="position: relative;height: 25px;bottom: 46px;padding-top: 1px;left: 785px;text-align: center;">
    </form>

</div>

</body>
</html>