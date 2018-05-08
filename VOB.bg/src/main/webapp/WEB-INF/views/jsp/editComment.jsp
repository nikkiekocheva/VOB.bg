<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="http://localhost:8080/VOB.bg/editComment">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
.text{
color:#2e6da4;
    }</style>
</head>
<body>
	</div><div id="menu">  
		<%@ include file="menu.jsp" %>
	</div>
	<div style="position: relative;left: 500px;">
	<h2 class="text">Enter edited comment:</h2>
	<br>
	<form action="/VOB.bg/editCommentForm/${comment.id}">
	<label class="col-md-3 control-label">Description:</label>
            <div class="col-md-8" style=" position:  relative; right: 250px;">
              <textarea class="form-control" type="text" name="content" required="">
              </textarea>
		<input type="submit" class="btn btn-primary" value="Save Changes" style="position: relative;height: 25px;bottom: 52px;padding-top: 1px;left: 225px;text-align: center;">
	</form>
	<form action="/VOB.bg/view/${comment.videoId}" style=" position:  relative; right: 50px;">
		<input type="submit" class="btn btn-primary" value="Cancel" style="position: relative;height: 25px;bottom: 46px;padding-top: 1px;left: 275px;text-align: center;">
	</form>
	</div>
</body>
</html>