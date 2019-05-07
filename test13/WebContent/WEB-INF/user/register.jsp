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

#main #register {
	margin-top: 10px;
	margin-left: 110px;
}

#main h1 {
	text-align: center;
}
</style>
<link rel="stylesheet"
	href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	$(document).ready(function() {

		$("#image").click(function() {
			$(this).attr("src", "user?type=randomImage&" + Math.random());

		})

		setTimeout(function() {
			$("#mes").html("");
		}, 1000)

		$("form").submit(function() {
			var username = $("#username").val();
			var password = $("#password").val();
			var rePassword = $("#rePassword").val();
			if (username == "") {
				alert("帐号不能为空");
				return false;
			} else if (password == "") {
				alert("密码不能为空");
				return false;
			} else if (password != rePassword) {
				alert("两次输入密码不一致");
				return false;
			}

		})
	})
</script>
<title>Insert title here</title>

</head>
<body>
	<div id="main">
		<h1>员工管理系统注册</h1>
		<form action="user?type=doRegister" method="post">
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
				<span class="input-group-addon glyphicon glyphicon-lock"></span> <input
					type="password" class="form-control" placeholder="请再次输入密码"
					id="rePassword" />
			</div>
			<div id="mes">${mes}</div>
			<div class="input-group input-group-lg" id="register">
				<input type="submit" class="btn btn-primary" value="注册" />
			</div>
		</form>
	</div>

</body>
</html>
