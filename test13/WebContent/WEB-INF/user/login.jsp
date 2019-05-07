<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
* {
	padding: 0;
	margin: 0
}

#main {
	width: 300px;
	height: 600px;
	margin: auto;
}

#main div {
	margin-top: 30px;
}

#main #login, #main #register {
	margin-top: 10px;
	margin-left: 110px;
}

#main h1 {
	text-align: center;
}

#image {
	position: relative;
	left: 300px;
	top: -40px;
}
</style>
<link rel="stylesheet"
	href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	if (self != top) {
		top.location = "user";
	}
	$(document).ready(function() {

		$("#main form").submit(function() {
			var username = $("#username").val();
			var password = $("#password").val();
			var random = $("#random").val();
			if (username == "") {
				$("#mes").html("帐号不能为空");
				setTimeout(function() {
					$("#mes").html("");
				}, 1000)
				return false;
			} else if (password == "") {
				$("#mes").html("密码不能为空");
				setTimeout(function() {
					$("#mes").html("");
				}, 1000);
				return false;
			} else if (random == "") {
				$("#mes").html("验证码不能为空");
				setTimeout(function() {
					$("#mes").html("");
				}, 1000);
				return false;
			}

		})

		setTimeout(function() {
			$("#mes").html("");
		}, 3000);

		$("#image").click(function() {
			$(this).attr("src", "user?type=randomImage&" + Math.random());
		})

		$("#register").click(function() {
			location.href = "user?type=showRegister";
		})

	})
</script>
<title>Insert title here</title>

</head>
<body>
	<div id="main">
		<h1>员工管理系统</h1>
		<form action="user?type=doLogin" method="post">
			<div class="input-group input-group-lg">
				<span class="input-group-addon glyphicon glyphicon-user"></span> <input
					type="text" class="form-control" placeholder="请输入帐号"
					name="username" id="username" value="${username}" />
			</div>
			<div class="input-group input-group-lg">
				<span class="input-group-addon glyphicon glyphicon-lock"></span> <input
					type="password" class="form-control" placeholder="请输入密码"
					name="password" id="password" />
			</div>
			<div class="input-group input-group-lg">
				<span class="input-group-addon glyphicon glyphicon-list-alt"></span>
				<input type="password" class="form-control" placeholder="请输入验证码 "
					name="random" id="random" />
			</div>
			<img id="image" src="user?type=randomImage" />
			<div id="mes" style="height: 40px; color: red">${mes}</div>
			<div class="input-group input-group-lg" id="login">
				<input type="submit" class="btn btn-primary" value="登录" />
			</div>
		</form>
		<div class="input-group input-group-lg" id="register">
			<input type="button" class="btn btn-primary" value="注册" />
		</div>
	</div>

</body>
</html>
