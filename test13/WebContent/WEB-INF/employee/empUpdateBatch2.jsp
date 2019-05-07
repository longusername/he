<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%-- <%@ page import="entity.*"%>
<%@ page import="java.util.*"%> --%>
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

#main form {
	margin-bottom: 50px;
	border-bottom: solid 1px gainsboro;
}
</style>
<script src="js/jquery.js"></script>
<script type="text/javascript">
	$(document).ready(
			function() {
				$("#save").click(
						function() {
							var emps = "";
							$("form").each(
									function(index, element) {
										var id = $(this).find("[name=id]")
												.val();
										var name = $(this).find("[name=name]")
												.val();
										var sex = $(this).find(
												"[name=sex]:checked").val();
										var age = $(this).find("[name=age]")
												.val();
										var d_id = $(this).find("[name=depId]")
												.val();
										if (d_id == "") {
											d_id = -1;
										}
										emps += id + "," + name + "," + sex
												+ "," + age + "," + d_id + ";";
									})
							emps = emps.substring(0, emps.length - 1);
							location.href = "empView?type=updateBatch2&emps="
									+ emps;

						})

			})
</script>

</head>
<body>
	<%-- 	<%
		List<Employee> list = new ArrayList<Employee>();
		list = (List<Employee>) request.getAttribute("list");
		String ids = (String) request.getAttribute("ids");
	%> --%>
	<div id="main">
		<%-- <%
			for (int i = 0; i < list.size(); i++) {
		%> --%>
		<c:forEach items="${list}" var="emp">
			<form action="empView" class="form-horizontal" method="post">
				<input type="hidden" name="type" value="updateBatch2"> <input
					type="hidden" name="id" value="${emp.id }">
				<div class="form-group">
					<label for="firstname" class="col-sm-2 control-label">姓名</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" value="${emp.name }"
							name="name">
					</div>
				</div>

				<div class="form-group">
					<label for="lastname" class="col-sm-2 control-label">性别</label>
					<div class="col-sm-10">
						男<input type="radio" name="sex"
							<c:if test="${emp.sex=='男' }"> checked </c:if> value="男">
						女 <input type="radio" name="sex"
							<c:if test="${emp.sex=='女' }"> checked </c:if> value="女">
					</div>
				</div>

				<div class="form-group">
					<label for="lastname" class="col-sm-2 control-label">年龄</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" value="${emp.age }"
							name="age">
					</div>
				</div>

				<div class="form-group">
					<label for="lastname" class="col-sm-2 control-label">部门名称</label>
					<div class="col-sm-10">
						<select class="form-control" name="depId">
							<option value="">请选择部门</option>
							<c:forEach items="${listOfDepartment}" var="lod">
								<option value="${lod.id}"
									<c:if test="${emp.dep.id==lod.id}">selected</c:if>>${lod.name}</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</form>
		</c:forEach>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button type="botton" class="btn btn-primary" id="save">确定</button>
			</div>
		</div>

	</div>

</body>
</html>