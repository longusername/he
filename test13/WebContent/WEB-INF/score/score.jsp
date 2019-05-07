<%@page import="com.oracle.webservices.internal.api.EnvelopeStyle.Style"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%-- <%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="entity.*,util.*"%> --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<style type="text/css">
#main {
	width: 538px;
	margin: 20px auto;
}

#emp .select {
	background-color: #337ab7
}

#emp td {
	width: 180px;
}

#emp input {
	width: 80px;
}

#emp select {
	width: 40px;
}

#main h1 {
	text-align: center;
}
</style>
<link href="bootstrap/css/bootstrap.css" rel="stylesheet" />
<script src="js/jquery.js"></script>
<script>
	$(document).ready(function() {
		var selectId = -1;
		
		$("tbody tr").click(function() {
			$(this).toggleClass("select");
			selectId = $(this).data("id");
		})
		
		
		$("tbody tr").dblclick(function(){
			$(this).unbind("dblclick");
			$(this).unbind("click");
			$(this).addClass("db");	
			var value="<input type='text' name='value' value='"+$(this).children().eq(4).text()+"'/>"; 
			$(this).children().eq(4).html(value);
		})
		
		
		$(document).on("blur","input",function() {
			var empId = -1;
			var depId = -1;
			var proId = -1;
			var value = -1;
			$(".db").each(function(index, element) {
				 empId = $(this).children().eq(1).data("emp_id");
				 depId = $(this).children().eq(2).data("dep_id");//后台没用上,备用
				 proId = $(this).children().eq(3).data("pro_id");
				 value = $(this).find("[name=value]").val();
			})
			  $.ajax({
				url:"scoreView",
				type:"post",
				data:{type:"save",empId:empId,depId:depId,proId:proId,value:value},
				dataType:"text",
				success:function(data){	
					$(".db").each(function(index, element) {
							$(this).children().eq(4).html($(this).children().eq(4).find("[name=value]").val());
							$(this).children().eq(5).html(data);
							$(this).removeClass("db");							
					})
					$("tbody tr").dblclick(function(){
							$(this).unbind("dblclick");
							$(this).unbind("click");
							$(this).addClass("db");	
							var value="<input type='text' name='value' value='"+$(this).children().eq(4).text()+"'/>"; 
							$(this).children().eq(4).html(value);
					})
				}			
			})  
		})
	 

		if(${p.ye}<=1){  
			$("#pre").find("a").attr("onclick","return false");
			$("#pre").addClass("disabled"); 
		}
		
		if(${p.ye}>=${p.maxYe}){            
			$("#next").find("a").attr("onclick","return false");
			$("#next").addClass("disabled");
		}
		
	})
</script>
</head>

<body>
	<div id="main">
		<h1>绩效管理</h1>
		<form action="scoreView" class="form-horizontal" method="post">
			<div class="form-group">
				<div class="col-sm-2">
					<input type="text" class="form-control" placeholder="姓名"
						name="empName" value=${c.emp.name }>
				</div>
				<div class="col-sm-3">
					<select class="form-control" name="depId">
						<option value="">选择部门</option>
						<c:forEach items="${listOfDepartment}" var="lod">
							<option value="${lod.id }"
								<c:if test="${lod.id==c.emp.dep.id }">selected</c:if>>${lod.name}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-sm-3">
					<select class="form-control" name="proId">
						<option value="">选择项目</option>
						<c:forEach items="${listOfProject}" var="lop">
							<option value="${lop.id }"
								<c:if test="${lop.id==c.pro.id }">selected</c:if>>${lop.name}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control" placeholder="成绩"
						name="value" value=${c.value }>
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control" placeholder="等级"
						name="grade" value=${c.grade }>
				</div>
				<div class="col-sm-2">
					<button type="submit" class="btn btn-primary">搜索</button>
				</div>
			</div>
		</form>
		<table id="emp" class="table table-bordered table-striped table-hover">
			<thead>
				<tr style="font-weight: bold;">
					<td>id</td>
					<td>姓名</td>
					<td>部门</td>
					<td>项目</td>
					<td>成绩</td>
					<td>等级</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${scoreList}" var="scol">
					<tr data-id="${scol.id}">
						<td>${scol.id}</td>
						<td data-emp_id="${scol.emp.id}">${scol.emp.name}</td>
						<td data-dep_id="${scol.emp.dep.id}">${scol.emp.dep.name}</td>
						<td data-pro_id="${scol.pro.id}">${scol.pro.name}</td>
						<td>${scol.value}</td>
						<td>${scol.grade}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<ul class="pagination">
			<li><a
				href="scoreView?ye=1&empName=${c.emp.name}&depId=${c.emp.dep.id}&proId=${c.pro.id}&value=${c.value}&grade=${c.grade}">首页</a></li>
			<li id="pre"><a
				href="scoreView?ye=${p.ye-1}&empName=${c.emp.name}&depId=${c.emp.dep.id}&proId=${c.pro.id}&value=${c.value}&grade=${c.grade}">上一页</a></li>
			<c:forEach begin="${p.beginYe}" end="${p.endYe}" varStatus="status">
				<li <c:if test="${p.ye==status.index}">class="active"</c:if>><a
					href="scoreView?ye=${status.index}&empName=${c.emp.name}&depId=${c.emp.dep.id}&proId=${c.pro.id}&value=${c.value}&grade=${c.grade}">${status.index}</a></li>
				<!-- class="disabled" -->
			</c:forEach>
			<li id="next"><a
				href="scoreView?ye=${p.ye+1}&empName=${c.emp.name}&depId=${c.emp.dep.id}&proId=${c.pro.id}&value=${c.value}&grade=${c.grade}">下一页</a></li>
			<li><a
				href="scoreView?ye=${p.maxYe}&empName=${c.emp.name}&depId=${c.emp.dep.id}&proId=${c.pro.id}&value=${c.value}&grade=${c.grade}">尾页</a></li>

		</ul>

	</div>
</body>
</html>