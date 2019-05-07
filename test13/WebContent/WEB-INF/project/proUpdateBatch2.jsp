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
	$(document).ready(function() {
		$("#save").click(function() {
			var pros = "";
			$("form").each(function(index, element) {
				var id = $(this).find("[name=id]").val();
				var name = $(this).find("[name=name]").val();
				var sex = $(this).find("[name=sex]:checked").val();
				var age = $(this).find("[name=age]").val();
				pros += id + "," + name + "," + sex + "," + age + ";";
			})
			pros = pros.substring(0, pros.length - 1);
			location.href = "proView?type=updateBatch2&pros=" + pros;

		})

	})
</script>

</head>
<body>
	<%-- 	<%
		List<Project> list = new ArrayList<Project>();
		list = (List<Project>) request.getAttribute("list");
		String ids = (String) request.getAttribute("ids");
	%> --%>
	<div id="main">
		<%-- <%
			for (int i = 0; i < list.size(); i++) {
		%> --%>
		<c:forEach items="${list}" var="pro">
			<form action="proView" class="form-horizontal" method="post">
				<input type="hidden" name="type" value="updateBatch2"> <input
					type="hidden" name="id" value="${pro.id }">
				<div class="form-group">
					<label for="firstname" class="col-sm-4 control-label">项目名称</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" value="${pro.name }"
							name="name">
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