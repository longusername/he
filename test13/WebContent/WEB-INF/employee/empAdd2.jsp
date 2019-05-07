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

#pictures img {
	width: 100px;
	height: 100px;
}
</style>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	$(document).ready(function() {

		$("#upload").click(function() {
			var formData = new FormData();
			for(var i=0;i<$("[name=picture]")[0].files.length;i++){
				formData.append("picture", $("[name=picture]")[0].files[i]);
			}		
			$.ajax({
				url : "empView?type=upload",
				type : "post",
				data : formData,
				cache : false,
				processData : false,
				contentType : false,
				dataType : "text",
				success : function(data) {
					var str = "<img src='empPic/"+data+"'/>"
					$("#pictures").append(str);
					var hidden="<input type='hidden' name='pictureName' value='"+data+"' />"
					$("#pictures").append(hidden);
				}
			})

		})
		
		$(document).on("click","#pictures img",function(){		
			var pictureName = $(this).next().val();
			$(this).next().remove();
			$(this).remove();
		    $.ajax({
				url:"empView",
				type:"post",
				data:{type:"deletePicture",pictureName:pictureName},
				dataType:"text",
				success:function(data){	
					if(data){
						alert("服务器图片删除成功");		
					}						
				}
			}) 
		})

	})
</script>
</head>
<body>
	<div id="main">
		<form action="empView?type=add2" class="form-horizontal" method="post">
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
				<div class="col-sm-7">
					<input type="file" name="picture"  /><!-- multiple 文件域多选择属性 -->
				</div>
				<div class="col-sm-3">
					<input type="button" class="btn btn-primary" value="上传" id="upload" />
				</div>

			</div>
			<div class="form-group" id="pictures"></div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-primary">确定</button>
				</div>
			</div>
		</form>
	</div>

</body>
</html>