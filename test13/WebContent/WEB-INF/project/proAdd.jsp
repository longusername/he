<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link href="bootstrap/css/bootstrap.css" rel="stylesheet" />
<style type="text/css">
#main {
	width: 400px;
	margin: 20px auto;
}
</style>

</head>
<body>
	<div id="main">
		<form action="proView" class="form-horizontal" method="post">
			<input type="hidden" name="type" value="add">
			<div class="form-group">
				<label for="firstname" class="col-sm-4 control-label">项目名称</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" placeholder="请输入项目名称"
						name="name">
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-primary">确定</button>
				</div>
			</div>
		</form>
	</div>

</body>
</html>