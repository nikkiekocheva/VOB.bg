<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<table>
	<tr>
		<form action="/VOB.bg/main" method="get">
			<button>Home</button>
		</form>
		<form action="/VOB.bg/videos" method="get">
			<button>Videos</button>
		</form>
		<form action="/VOB.bg/uploadVideo" method="get">
			<button>Upload video</button>
		</form>
		<form action="/VOB.bg/profile/${ sessionScope.user.username }" method="get">
			<button>Profile</button>
		</form>
		<form action="/VOB.bg/playlist" method="get">
			<button>Playlists</button>
		</form>
		<form action="/VOB.bg/logout" method="post">
			<button type="submit">Logout</button>
		</form>
	</tr>
</table>