<%@page import="com.oracle.webservices.internal.api.EnvelopeStyle.Style"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%-- <%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="entity.*,util.*"%> --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="f"%>

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

#pro .select {
	background-color: #337ab7
}

#pro td {
	width: 180px;
}

#pro input {
	width: 80px;
}

#pro select {
	width: 40px;
}
</style>
<link href="bootstrap/css/bootstrap.css" rel="stylesheet" />
<script src="js/jquery.js"></script>
<script>
	$(document).ready(function() {
		var selectId = -1;
		$("#add").click(function() {   
			var proId=$("#selectPro").val();
			location.href = "pro2DepView?type=add&depId=${dep.id}&proId="+proId;
		})
		
		<c:if test="${f:length(listOfNoProject) == 0}">
		$("#add").unbind("click");
		$("#add").addClass("disabled");
		</c:if>

		$("tbody tr").click(function() {
			$(this).toggleClass("select");
			selectId = $(this).data("id");
		})
	
		$("#delete").click(function() {
			if (selectId > -1) {
				var r = confirm("是否删除");
				if (r) {
					location.href = "pro2DepView?type=delete&depId=${dep.id}&proId=" + selectId;
				} else {
				}

			} else {
				alert("请选中一条数据");
			}

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
		<div class="page-header">
			<h1>${dep.name}</h1>
		</div>
		<table id="pro" class="table table-bordered table-striped table-hover">
			<thead>
				<tr style="font-weight: bold;">
					<td>项目id</td>
					<td>项目名称</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${proList}" var="pro">
					<tr data-id="${pro.id}">
						<td>${pro.id}</td>
						<td>${pro.name}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<ul class="pagination" id="page">
			<li id="pre"><a href="pro2DepView?ye=${p.ye-1}&depId=${dep.id}">上一页</a></li>
			<c:forEach begin="${p.beginYe}" end="${p.endYe}" varStatus="status">
				<li <c:if test="${p.ye==status.index}">class="active"</c:if>><a
					href="pro2DepView?&depId=${dep.id}&ye=${status.index}">${status.index}</a></li>
				<!-- class="disabled" -->
			</c:forEach>
			<li id="next"><a href="pro2DepView?ye=${p.ye+1}&depId=${dep.id}">下一页</a></li>
		</ul>
		<div>
			<div class="col-sm-4 form-group">
				<select class="form-control" id="selectPro">
					<c:forEach items="${listOfNoProject}" var="lonp">
						<option value="${lonp.id}">${lonp.name}</option>
					</c:forEach>
				</select>
			</div>
			<button type="button" class="btn btn-primary" id="add">添加</button>
			<button type="button" class="btn btn-danger" id="delete">删除</button>
		</div>

	</div>
</body>
</html>