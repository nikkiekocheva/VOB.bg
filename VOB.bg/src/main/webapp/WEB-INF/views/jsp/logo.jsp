<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
    <h1>VOB.BG LOGO</h1>
    <form action="search" id="type">
    	<input id="text" onkeyup="search()" type="text" placeholder="Search.." name="text">
    	<select name="type" form="type">
 			<option value="user">User</option>
 			<option value="video">Video</option>
  			<option value="playlist">Playlist</option>
		</select>
    	<input type="submit" value="Search">
    </form>
    <hr>

    
    