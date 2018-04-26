<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" href="WEB-INF/stype.css">
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Main Page</title>
</head>
<body>
	<div id="top">  
		<%@ include file="logo.jsp" %>
	</div>
	<div id="menu">  
		<%@ include file="menu.jsp" %>
	</div>
	
	<h1>
		Wellcome, <%= session.getAttribute("username") %>!
	</h1>
	
</body>
</html>