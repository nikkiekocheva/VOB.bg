<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
				<td></td>
			</tr>
			<tr>
				<td>Password: </td>
				<td></td>
			</tr>		
			<tr>
				<td>E-mail: </td>
				<td>${ sessionScope.user.email }</td>
				<td></td>
			</tr>
			<tr>
				<td>PhoneNumber: </td>
				<td>${ sessionScope.user.phoneNumber }</td>
				<td></td>
			</tr>
			<tr>
				<td>Age: </td>
				<td>${ sessionScope.user.age }</td>
			</tr>
		</table>
	</form>

</body>
</html>