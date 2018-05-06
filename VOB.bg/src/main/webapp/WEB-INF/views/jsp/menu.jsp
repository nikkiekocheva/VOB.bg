<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>VOB.bg</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<!-- bootstrap -->
<link href="css/bootstrap.min.css" rel='stylesheet' type='text/css' media="all" />
<!-- //bootstrap -->
<link href="css/dashboard.css" rel="stylesheet">
<!-- Custom Theme files -->
<link href="css/style.css" rel='stylesheet' type='text/css' media="all" />
<script src="js/jquery-1.11.1.min.js"></script>
<!--start-smoth-scrolling-->
<!-- fonts -->
<link href='//fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800' rel='stylesheet' type='text/css'>
<link href='//fonts.googleapis.com/css?family=Poiret+One' rel='stylesheet' type='text/css'>
<!-- //fonts -->
</head>
  <body>

    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container-fluid">
        <div class="navbar-header">
          <a style= "border-color: white;border-style: solid;position:relative;" class="navbar-brand" href="index.html"><h1>
          <img src="images/logo.png" alt="" /></h1></a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
			<div class="top-search">
				<form class="navbar-form navbar-right">
				<div style="margin-left: -7em; position: relative; left: 220px;">
				<img src="images/logotext2.png"></div>
				<div>
					 <input style="width: 350px;" type="text" class="form-control" placeholder="Search..."> 
					<input type="submit" value=" ">
					</div>
				</form>
			</div>

			<div class="header-top-right">
				<ul class="no-bullets">
				<div class="file">
					<li><a href="/VOB.bg/main">Home</a></li>
				</div>	
				<div class="file">
					<form action="/VOB.bg/profile/${sessionScope.user.username}" method="get">
			<li><button class="btn-link">Profile</button></li>
			</form>
				</div>
				<div class="file">
					<li><a href="/VOB.bg/playlist">Playlists</a></li>
				</div>
				<div class="file">
					<li><a href="/VOB.bg/uploadVideo">Upload video</a></li>
				</div>
				<div class="file">
				<form action="/VOB.bg/logout" method="post">
				<li><button type="submit" class="btn-link">Logout</button></li>
				<!--<li><a href="/VOB.bg/logout" type="submit">Log out</a></li>  -->	
				</form>
				</div>
			</ul>
				
				<div class="clearfix"> </div>
			</div>
        </div>
		<div class="clearfix"> </div>
      </div>
    </nav>
    
    <br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="js/bootstrap.min.js"></script>
    <!-- Just to make our placeholder images work. Don't actually copy the next line! -->
  </body>
</html>