<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="top">  
		<%@ include file="logo.jsp" %>
	</div>
	<div id="menu">  
		<%@ include file="menu.jsp" %>
	</div>
	
	<form action="updateprofile" method="post" align="center">
		<table align="center" border="12px">
			<tr>
				<td>E-mail: </td>
				<td><input type="text" name="email" placeholder="Enter your new e-mail!"></td>
			</tr>
			<tr>
				<td>PhoneNumber: </td>
				<td><input type="text" name="phone" placeholder="Enter your new phone number!" maxlength="10"></td>
			</tr>
			<tr></tr>
			<tr>
				<td>New Password: </td>
				<td><input type="password" name="newpassword" placeholder="Enter your new password!"></td>
			</tr>
			<tr>
				<td>New Password Again: </td>
				<td><input type="password" name="newpassword1" placeholder="Enter your new password again!"></td>
			</tr>
			<tr>
				<td>Current Password: </td>
				<td><input type="password" name="currentpassword" placeholder="Enter your password to submit" required></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Submit">  </td>
			</tr>
		</table>
	</form>
</body>
</html>