<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		<form action="empView?type=add" class="form-horizontal" method="post"
			enctype="multipart/form-data">
			<div class="form-group">
				<label for="firstname" class="col-sm-2 control-label">姓名</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" placeholder="请输入姓名"
						name="name">
				</div>
			</div>

			<div class="form-group">
				<label for="lastname" class="col-sm-2 control-label">性别</label>
				<div class="col-sm-10">
					男<input type="radio" name="sex" checked value="男"> 女 <input
						type="radio" name="sex" value="女">
				</div>
			</div>

			<div class="form-group">
				<label for="lastname" class="col-sm-2 control-label">年龄</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" placeholder="请输入年龄"
						name="age">
				</div>
			</div>

			<div class="form-group">
				<label for="lastname" class="col-sm-2 control-label">部门名称</label>
				<div class="col-sm-10">
					<select class="form-control" name="depId">
						<option value="">请选择部门</option>
						<c:forEach items="${listOfDepartment}" var="lod">
							<option value="${lod.id}">${lod.name}</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<div class="form-group">
				<label for="lastname" class="col-sm-2 control-label">员工图片</label>
				<div class="col-sm-10">
					<input type="file" name="picture" />
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