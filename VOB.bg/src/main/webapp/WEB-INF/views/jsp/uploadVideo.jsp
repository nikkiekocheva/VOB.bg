<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Upload video</title>
</head>
<body>
	<div id="menu">  
		<%@ include file="menu.jsp" %>
	</div>
	
	<h1> Upload video</h1>
	
	<form action="uploadVideo" method="post" enctype="multipart/form-data">
		File name: <input type="text" name="name" required/> <br>
		Description: <textarea name="description" rows="4" cols="50" required></textarea><br>
		<input type="file"  name="videoFile" accept="video/*" required/>  <!-- Only videos are accepted --><br>
		<input type="submit"/>
		
	</form>
</body>
</html>