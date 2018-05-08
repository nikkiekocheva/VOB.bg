<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="http://localhost:8080/VOB.bg/viewVideo">
<style>
.thumbnail {
    padding:0px;
}
.panel {
	position:relative;
}
.panel>.panel-heading:after,.panel>.panel-heading:before{
	position:absolute;
	top:11px;left:-16px;
	right:100%;
	width:0;
	height:0;
	display:block;
	content:" ";
	border-color:transparent;
	border-style:solid solid outset;
	pointer-events:none;
}
.panel>.panel-heading:after{
	border-width:7px;
	border-right-color:#f7f7f7;
	margin-top:1px;
	margin-left:2px;
}
.panel>.panel-heading:before{
	border-right-color:#ddd;
	border-width:8px;
}
</style>
</head>
<body>
	<div id="menu">  
		<%@ include file="menu.jsp" %>
	</div>

	<div style="height:800px; position:relative; left:200px;">
		<ul class="list-group" style=" position:relative; width:500px;">
			<li class="list-group-item text-muted" style="font-size: 18px; color: #2e6da4; text-align: center;">${video.name}</li>
           	<li class="list-group-item text-muted" style="font-size: 18px; color: #2e6da4; text-align: center;">
            	<video width="420" height="300" controls> <source
				src="/VOB.bg/videos/${video.path}" type="video/mp4"></video></li>
        </ul> 
    	<div style="width: 500px;height: 321px;position: relative;bottom: 341px;left: 500px;;">
        	<ul class="list-group" style=" position:relative; width:150px;">
            	<li class="list-group-item text-muted" style="font-size: 14px; color: #2e6da4; text-align: center;">
            		User: <a href = "/VOB.bg/profile/${ videouser.username }">${videouser.username}</a></li>
            	<li class="list-group-item text-muted" style="font-size: 14px; color: #2e6da4; text-align: center;">
           			Likes:${likes}</li>
           		<li class="list-group-item text-muted" style="font-size: 14px; color: #2e6da4; text-align: center;">
	          		Dislikes:${dislikes}</li>
	           	<li class="list-group-item text-muted" style="font-size: 14px; color: #2e6da4; text-align: center;">
          			Views: ${video.views}</li>
           		<li class="list-group-item text-muted" style="font-size: 14px; color: #2e6da4; text-align: center;">
		         	<form action="/VOB.bg/videotoplaylist" method="post">
						<input type="hidden" name="videoid" value="${ video.id }"/>
						<input type="submit" class="btn btn-primary" value="Add to playlist" name="addvideo" style="position: relative;height: 25px;padding-top: 1px;text-align: center;">
					</form>
          		</li>
           		<li class="list-group-item text-muted" style="font-size: 14px; color: #2e6da4; text-align: center; height:50px; width: 75px;">
					<form action="/VOB.bg/rateVideo/${video.id}">
						<c:choose>
							<c:when test="${ userLikeDislike == 0 || userLikeDislike == -1 }">
								<!-- <c:if test="${ userLikeDislike == 0 || userLikeDislike == -1 }"> -->
								<button name="button" value="buttonlike" style="background:transparent; border:none; color:transparent;">
									<img src="images/like.png" style="width:60px; position: relative;bottom: 20px; right:10px;"> 
								</button>
							<!-- </c:if> -->
							</c:when>
							<c:otherwise>
								<button name="button" value="buttonlike" style="background:transparent; border:none; color:transparent;">
									<img src="images/liked.png" style="width:40px; position: relative;bottom: 10px; right:10px;"> 
								</button>
							</c:otherwise>
						</c:choose>	
         			</form>
           		</li>
           		<li class="list-group-item text-muted" style="font-size: 14px; color: #2e6da4; text-align: center;width: 75px;height: 50px;bottom: 49px;left: 75px">
           			<form action="/VOB.bg/rateVideo/${video.id}">
          				<c:choose>
							<c:when test="${ userLikeDislike == 0 || userLikeDislike == 1 }">
           						<button name="button" value="buttondislike" style="background:transparent; border:none; color:transparent;"">
           							<img src="images/dis.png" style="width:60px; position: relative; bottom: 20px; right:10px;">
         						 </button>
         					 </c:when>
          					<c:otherwise>
								<button name="button" value="buttondislike" style="background:transparent; border:none; color:transparent;"">
									<img src="images/disliked.png" style="width:40px; position: relative;bottom: 10px; right:10px;"> 
								</button>
							</c:otherwise>
						</c:choose>	
          			</form>
          		</li>
			</ul>
		</div>
	
		<div class="form-group" style="position: relative; bottom:300px">
			<div style="position:relative; height:100px;">
            	<label class="col-md-3 control-label">Add comment:</label>
            
            	<div class="col-md-8" style=" position:  relative; right: 250px;">
            		<form action="/VOB.bg/addComment/${video.id}">
             			<textarea class="form-control" type="text" name="comment" required=""></textarea>
             			<input style="height: 25px;width: 80px;padding-top: 1px;" type="submit" class="btn btn-primary" value="Upload">
            		</form>
           		</div>
         	</div> 
		<a href="//netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css"></a>
		<script src="//netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
		<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
		<!------ Include the above in your HEAD tag ---------->
	
		<c:forEach var="comment" items="${allComments}">
	
			<div class="container">
				<div class="row">
					<div class="col-sm-12">
					</div><!-- /col-sm-12 -->
				</div><!-- /row -->
				<div class="row" style="    position: relative;right: 200px;">
					<div class="col-sm-1">
					</div><!-- /col-sm-1 -->
	
					<div class="col-sm-5">
						<div class="panel panel-default">
							<div class="panel-heading">
								<strong>${comment.username}</strong>
								<span class="text-muted" style="position:relative; left:50px">Likes:${comment.likes}</span>
								<span class="text-muted" style="position:relative; left:100px">Dislikes:${comment.dislikes}</span>
								<span class="text-muted" style="position:relative; left:150px">${comment.formattedDate}</span>
							</div>
							<div class="panel-body">
							${comment.content}
							</div><!-- /panel-body -->
						</div><!-- /panel panel-default -->
					</div><!-- /col-sm-5 -->
				</div><!-- /row -->
			</div><!-- /container -->
	
			<c:set var = "userId" scope = "session" value = "${videouser.id}"/>
				<c:if test ="${comment.userId == user.id}">
					<form action="/VOB.bg/editComment/${comment.id}">
						<button style="background: white; border-radius: 4px;position: relative;left: 360px;bottom: 15px;">Edit</button>
					</form>
					<form action="/VOB.bg/deleteComment/${comment.id}">
						<button style="background: white; border-radius: 4px; position: relative;left: 400px;bottom: 35px;">Delete</button>
					</form>
					</c:if>
						<form action="/VOB.bg/rateComment/${comment.id}">
							<form action="/VOB.bg/rateComment/${comment.id}">
								<button name="commentButton" value="commentButtonLike" style="background:transparent; border:none; color:transparent;"><img src="images/like.png" style= "width: 30px;position: relative; bottom: 60px;left: 270px;"> </button>
								<button name="commentButton" value="commentButtonDislike" style="background:transparent; border:none; color:transparent;"><img src="images/dis.png" style="width: 30px;position: relative; bottom: 60px;left: 270px;"> </button>
							</form>
		</c:forEach>
		<br>
			
		</div>
	</div>
</body>
</html>