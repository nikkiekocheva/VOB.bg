<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Upload video</title>
<style>
.text{
color:#2e6da4;
    }
    </style>
</head>
<body>
	<div id="menu">  
		<%@ include file="menu.jsp" %>
	</div>
	
	<div style="position: relative;left: 600px;">
	<h1 class="text"> Upload video</h1>
	<form action="uploadVideo" method="post" enctype="multipart/form-data">
	<div class="form-group" style="position: relative;">
            <label class="col-md-3 control-label">File name:</label>
            <div class="col-md-8" style=" position:  relative; right: 250px;">
              <input class="form-control" type="text" name="name" required="">
             
            </div>
            <label class="col-md-3 control-label">Description:</label>
            <div class="col-md-8" style=" position:  relative; right: 250px;">
              <textarea class="form-control" type="text" name="description" required="">
              </textarea>
              <label class="col-md-3 control-label" style="right: 105px;">File:</label>
            </div>
            
        <div class="col-lg-6 col-sm-6 col-12">
        
            <label class="btn btn-primary" style="height: 25px;width: 80px;position: relative;padding-top: 1px;bottom: 20px;left: 93px;">
                Browse&hellip; <input type="file" style="display: none;" name="videoFile" >
            </label>
            <br>
             <input style="height: 25px;width: 80px;padding-top: 1px;" type="submit" class="btn btn-primary" value="Upload">
          
        </div>
            </div>
           </form>
	</div>

  <script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<a href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css" rel="stylesheet"></a>

<script>
$(function() {

	  // We can attach the `fileselect` event to all file inputs on the page
	  $(document).on('change', ':file', function() {
	    var input = $(this),
	        numFiles = input.get(0).files ? input.get(0).files.length : 1,
	        label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
	    input.trigger('fileselect', [numFiles, label]);
	  });

	  // We can watch for our custom `fileselect` event like this
	  $(document).ready( function() {
	      $(':file').on('fileselect', function(event, numFiles, label) {

	          var input = $(this).parents('.input-group').find(':text'),
	              log = numFiles > 1 ? numFiles + ' files selected' : label;

	          if( input.length ) {
	              input.val(log);
	          } else {
	              if( log ) alert(log);
	          }

	      });
	  });
	  
	});
</script>

</body>
</html>