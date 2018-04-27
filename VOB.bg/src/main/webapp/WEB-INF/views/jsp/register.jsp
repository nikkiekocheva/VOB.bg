<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Register</title>
</head>
<body>
	<form action="register" method="post" align="center">
		<table align="center">
			<tr>
				<td>Username: </td>
				<td><input type="text" name="username" placeholder="Enter your username" required></td>
			</tr>
			<tr>
				<td>Password: </td>
				<td><input type="password" name="password" placeholder="Enter your password" required></td>
			</tr>		
			<tr>
				<td>E-mail: </td>
				<td><input type="text" name="email" placeholder="Enter your e-mail" required></td>
			</tr>
			<tr>
				<td>PhoneNumber: </td>
				<td><input type="text" name="phone" placeholder="Enter your phone number" required maxlength="10"></td>
			</tr>
			<tr>
				<td>Age: </td>
				<td><input type="text" name="age" placeholder="Enter your age" required maxlength="3"></td>
			</tr>
		</table> <br>
		<a href="http://localhost:8080/VOB.bg/main"><button>Registur</button> </a>
	</form>
</body>
</html>