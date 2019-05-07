<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<style type="text/css">
* {
	margin: 0;
	padding: 0;
}

#main {
	overflow: hidden;
}

#left {
	width: 400px;
	float: left;
	height: 820px;
	padding-left: 20px;
}

#right {
	width: 800px;
	float: left;
	height: 600px;
}

#top, #bottom {
	clear: both;
	height: 80px;
	background: #337ab7;
	height: 80px;
}

.yi {
	width: 160px;
	height: 40px;
	background: #337ab7;
	color: #fff;
	margin-top: 10px;
	border-radius: 3px;
	text-align: center;
	line-height: 40px;
}

.yi:hover {
	cursor: pointer;
}

a {
	color: #fff;
	text-decoration: none;
}

.er {
	list-style: none;
	width: 160px;
}

.er li {
	margin-top: 5px;
	text-align: center;
	background: #337a97;
	color: #fff;
	height: 30px;
	line-height: 30px;
	font-size: 14px;
}

.er a {
	color: #000;
}
</style>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	$(document).ready(
			function() {

				$(".yi").click(function() {
					$(this).next().slideToggle(500);
				})

				var websocket = null;

				//判断当前浏览器是否支持WebSocket
				if ('WebSocket' in window) {
					websocket = new WebSocket(
							"ws://192.168.0.147:8080/test13/websocket");
				} else {
					alert('没有建立websocket连接')
				}

				//连接发生错误的回调方法
				websocket.onerror = function() {
					//setMessage("错误");
				};

				//连接成功建立的回调方法
				websocket.onopen = function(event) {
					//setMessage("建立连接");
				}

				//接收到消息的回调方法
				websocket.onmessage = function(event) {
					setMessage(event.data);
				}

				//连接关闭的回调方法
				websocket.onclose = function() {
					//setMessage("close");
				}

				//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口。
				window.onbeforeunload = function() {
					websocket.close();
				}

				//将消息显示在网页上
				function setMessage(text) {
					$("#count").html("当前在线" + text + "人");
				}

				//关闭连接
				function closeWebSocket() {
					websocket.close();
				}

			})
</script>
</head>
<body>
	<div id="container">
		<div id="top">
			<div style="color: white; margin-left: 1150px" id="count">当前在线${applicationScope.num}人</div>
			<%-- ${applicationScope.num} --%>
		</div>
		<div id="main">
			<div id="left">
				<div class="yi">员工管理</div>
				<ul class="er">
					<li><a href="empView" target="right">员工管理</a></li>
					<li><a href="empView?type=showAdd" target="right">员工添加</a></li>
				</ul>
				<div class="yi">部门管理</div>
				<ul class="er">
					<li><a href="depView" target="right">部门管理</a></li>
					<li><a href="depView?type=showAdd" target="right">部门添加</a></li>
				</ul>
				<div class="yi">项目管理</div>
				<ul class="er">
					<li><a href="proView" target="right">项目管理</a></li>
					<li><a href="proView?type=showAdd" target="right">项目添加</a></li>
				</ul>
				<div class="yi">绩效管理</div>
				<ul class="er">
					<li><a href="scoreView" target="right">绩效管理</a></li>
					<li><a href="" target="right">绩效添加</a></li>
				</ul>
			</div>
			<iframe id="right" name="right" scrolling="no" frameborder="0"
				src="empView"></iframe>
		</div>
		<div id="bottom"></div>
	</div>
</body>
</html>