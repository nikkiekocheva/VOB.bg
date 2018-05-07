<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
@import url(https://fonts.googleapis.com/css?family=Raleway:700);

$color1: #EE4B5E;
$color2: #342643;
$color3: #1FA9D6;

*, *:before, *:after {
  box-sizing: border-box;
}
html {
    height: 100%;
}
body {
    background: url(https://i.imgur.com/76NZB7A.gif) no-repeat center center fixed; 
    background-size: cover;
    font-family: 'Raleway', sans-serif;
    background-color: $color2; 
    height: 100%;
}

.text-wrapper {
    height: 100%;
   display: flex;
   flex-direction: column;
   align-items: center;
   justify-content: center;
}

.title {
    font-size: 6em;
    font-weight: 700;
    color: $color1;
}

.subtitle {
    font-size: 40px;
    font-weight: 700;
    color: $color3;
}

.buttons {
    margin: 30px;
    
    a.button {
        font-weight: 700;
        border: 2px solid $color1;
        text-decoration: none;
        padding: 15px;
        text-transform: uppercase;
        color: $color1;
        border-radius: 26px;
        transition: all 0.2s ease-in-out;
        
        &:hover {
            background-color: $color1;
            color: white;
            transition: all 0.2s ease-in-out;
        }
    }

    
}


</style>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Error</title>
</head>
<body>
	
<div class="text-wrapper">
    <div class="title" data-content="404">
        Error!
    </div>

    <div class="subtitle">
        ${requestScope.exception.message }
    </div>

    <div class="buttons">
    <c:choose>
			<c:when test="${ requestScope.newSession }">
				<a class="button" href="/VOB.bg/">Return to login</a>
			</c:when>
			<c:otherwise>
				<a class="button" href="/VOB.bg/main">Return to main</a>
			</c:otherwise>
		</c:choose>
    </div>
</div>    
</body>
</html>