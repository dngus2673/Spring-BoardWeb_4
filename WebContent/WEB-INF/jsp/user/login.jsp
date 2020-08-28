<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
	 <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
	<div id="container">
		<h1 id="tit">LOGIN</h1>
		<div id="content">
			<form action="/login" method="post">
				<div><input type="text" name="user_id" placeholder="아이디" value="${user_id}"></div>
				<div><input type="password" name="user_pw" placeholder="비밀번호"></div>
				<div><input type="submit" value="login"></div>
			</form>
			<div class="msg_err">${msg }</div>
			<a href="/join">Join</a>
		</div>
	</div>
</body>
</html>