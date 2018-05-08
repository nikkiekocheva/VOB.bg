<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
body{
  background-color: #99bbff;
}
.login-box{
  position:relative;
    right: 250px;
    top: 50px;
  margin: 10px auto;
  width: 500px;
  height: 480px;
  background-color: #fff;
  padding: 10px;
  border-radius: 3px;
  -webkit-box-shadow: 0px 2px 3px 0px rgba(0,0,0,0.33);
-moz-box-shadow: 0px 2px 3px 0px rgba(0,0,0,0.33);
box-shadow: 0px 2px 3px 0px rgba(0,0,0,0.33);
}
.lb-header{
  position:relative;
  color: #00415d;
  margin: 5px 5px 10px 5px;
  padding-bottom:10px;
  border-bottom: 1px solid #eee;
  text-align:center;
  height:28px;
}
.lb-header a{
  margin: 0 25px;
  padding: 0 20px;
  text-decoration: none;
  color: #666;
  font-weight: bold;
  font-size: 15px;
  -webkit-transition: all 0.1s linear;
  -moz-transition: all 0.1s linear;
  transition: all 0.1s linear;
}
.lb-header .active{
  color: #029f5b;
  font-size: 18px;
}
.social-login{
  position:relative;
  float: left;
  width: 100%;
  height:auto;
  padding: 10px 0 15px 0;
  border-bottom: 1px solid #eee;
}
.social-login a{
  position:relative;
  float: left;
  width:calc(40% - 8px);
  text-decoration: none;
  color: #fff;
  border: 1px solid rgba(0,0,0,0.05);
  padding: 12px;
  border-radius: 2px;
  font-size: 12px;
  text-transform: uppercase;
  margin: 0 3%;
  text-align:center;
}
.social-login a i{
  position: relative;
  float: left;
  width: 20px;
  top: 2px;
}
.social-login a:first-child{
  background-color: #49639F;
}
.social-login a:last-child{
  background-color: #CEF6F5;
}
.email-login,.email-signup{
  position:relative;
  float: left;
  width: 100%;
  height:auto;
  margin-top: 20px;
  text-align:center;
}
.u-form-group{
  width:100%;
  margin-bottom: 10px;
}
.u-form-group input[type="text"],
.u-form-group input[type="password"]{
  width: calc(50% - 22px);
  height:45px;
  outline: none;
  border: 1px solid #ddd;
  padding: 0 10px;
  border-radius: 2px;
  color: #333;
  font-size:0.8rem;
  -webkit-transition:all 0.1s linear;
  -moz-transition:all 0.1s linear;
  transition:all 0.1s linear;
}
.u-form-group input:focus{
  border-color: #358efb;
}
.u-form-group button{
  width:50%;
  background-color: #2e6da4;
  border: none;
  outline: none;
  color: #fff;
  font-size: 14px;
  font-weight: normal;
  padding: 14px 0;
  border-radius: 2px;
  text-transform: uppercase;
}
.forgot-password{
  width:50%;
  text-align: left;
  text-decoration: underline;
  color: #888;
  font-size: 0.75rem;
}
.logo{
    position: relative;
    bottom: 610px;
    left: 800px;
}
.text{
color:#2e6da4;
    }
</style>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
</head>
<body>
	
	<div class="login-box">
		<div class="lb-header">
			<img src="images/logintext.png"/>
		</div>
	    <div class="lb-header">
	    	<a class="active text" id="login-box-link" style=" width:370px"><img src="images/logotextblack.png"/></a>
	    </div>
	   
	    <form class="email-login" action="login" method="post">
	      <div class="u-form-group">
	      	<input type="text" name="username" placeholder="Username" required />
	      
	        <input type="password" name="password" placeholder="Password" required />
	      </div>
	      <div class="u-form-group">
	        <button type="submit">Log in</button>
	      </div>
	    </form>
	    
	    <form class="email-login" action="register" method="get">
	    	<div class="u-form-group">
	       		<button type="submit">Regiser</button>
	     	</div>
	    </form>
	</div>	
	<div>
	<img class="logo" src="images/biglogo.png"/>
	</div>
	

</body>
</html>