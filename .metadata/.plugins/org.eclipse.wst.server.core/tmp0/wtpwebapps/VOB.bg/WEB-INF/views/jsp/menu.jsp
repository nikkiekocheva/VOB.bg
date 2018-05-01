<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<table>
	<tr>
		<form action="main" method="get">
			<button>Home</button>
		</form>
		<form action="videos" method="get">
			<button>Videos</button>
		</form>
		<form action="uploadVideo" method="get">
			<button>Upload video</button>
		</form>
		<form action="profile" method="get">
			<button>Profile</button>
		</form>
		<form action="playlist" method="get">
			<button>Playlists</button>
		</form>
		<form action="logout" method="post">
			<button type="submit">Logout</button>
		</form>
	</tr>
</table>