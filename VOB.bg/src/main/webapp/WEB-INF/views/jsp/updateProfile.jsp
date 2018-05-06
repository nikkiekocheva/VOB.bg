<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<style>
.form-control{
width: 400px;
}
.text{
	color:#2e6da4;
    position: relative;
    left: 400px;
    bottom: 20px;}
</style>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Update profile info</title>
</head>
<body>
	<div id="menu">  
		<%@ include file="menu.jsp" %>
	</div>
	
	<div class="container">
    <h1>Edit Profile</h1>
  	<hr>
	<div class="row">

      
      <!-- INPUT FORM FOR REGISTRATION -->
     
        <h3 class="text" >Account info</h3>
        
        <form action="updateprofile" method="post" align="center" class="form-horizontal" role="form">
          <div class="form-group">
            <label class="col-lg-3 control-label">Email:</label>
            <div class="col-lg-8">
              <input class="form-control" type="text" name="email"> 
            </div>
          </div>
          <div class="form-group">
            <label class="col-md-3 control-label">Phone number:</label>
            <div class="col-md-8">
              <input class="form-control" type="text" maxlength="10" name="phone">
            </div>
          </div>
          <div class="form-group">
            <label class="col-md-3 control-label">Old password:</label>
            <div class="col-md-8">
              <input class="form-control" type="password" name="currentpassword">
            </div>
          </div>
          <div class="form-group">
            <label class="col-md-3 control-label">Password:</label>
            <div class="col-md-8">
              <input class="form-control" type="password" name="newpassword">
            </div>
          </div>
          <div class="form-group">
            <label class="col-md-3 control-label">Confirm password:</label>
            <div class="col-md-8">
              <input class="form-control" type="password" name="newpassword1">
            </div>
          </div>
          <div class="form-group">
            <label class="col-md-3 control-label"></label>
            <div class="col-md-8">
              <input type="submit" class="btn btn-primary" value="Save Changes">
              <span></span>
              <input type="reset" class="btn btn-default" value="Cancel">
            </div>
          </div>
        </form>
      </div>
  </div>
<hr>
	
	
	
</body>
</html>