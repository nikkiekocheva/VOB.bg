<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>
	<div align="center">
		<h1 align="center">VOB --Video out of the Box--</h1>
	</div>
	
	<form action="login" method="post" align="center">
		<table align="center">
			<tr>
				<td>Username </td>
				<td><input type="text" name="username" placeholder="Enter Username"> </td>
			</tr>
			<tr>
				<td>Password </td>
				<td><input type="password" name="password" placeholder="Enter Password"></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Login"></td>
			</tr>
		</table>
		If u don't have an account click <a href="register"> here</a>!
			
	</form>
	
	
	

</body>
</html>