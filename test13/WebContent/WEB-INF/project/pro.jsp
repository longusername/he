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

#main h1 {
	text-align: center;
}
</style>
<link href="bootstrap/css/bootstrap.css" rel="stylesheet" />
<script src="js/jquery.js"></script>
<script>
	$(document).ready(function() {
		var selectId = -1;
		$("#add").click(function() {

			location.href = "proView?type=showAdd";
		})

		$("tbody tr").click(function() {
			$(this).toggleClass("select");
			selectId = $(this).data("id");
		})
		
	 	$("tbody tr").dblclick(function(){
			$(this).unbind("dblclick");
			$(this).unbind("click");
			$(this).addClass("db");
			var selected='';
			selected="<input type='text' name='name' value='"+$(this).children().eq(1).text() +"'/>"
			$(this).children().eq(1).html(selected);
		})  
		

		$("#showUpdate").click(function() {
			if (selectId > -1) {
				location.href = "proView?type=showUpdate&id=" + selectId;
			} else {
				alert("请选中一条数据");
			}

		})
		
		$("#showUpdateBatch1").click(function() {
			if (selectId > -1) {
				var selectIds = new Array();
				$("#pro .select").each(function() {
					selectIds.push($(this).data("id"));

				})
				location.href = "proView?type=showUpdateBatch1&ids=" + selectIds;
			} else {
				alert("请选中一条数据");
			}

		})
		
		$("#showUpdateBatch2").click(function() {
			if (selectId > -1) {
				var selectIds = new Array();
				$("#pro .select").each(function() {
					selectIds.push($(this).data("id"));

				})
				location.href = "proView?type=showUpdateBatch2&ids=" + selectIds;
			} else {
				alert("请选中一条数据");
			}

		})
		
		$("#updateBatch3").click(function() {
			var pros = "";
			var array=new Array();
			$(".db").each(function(index, element) {
				var id = $(this).data("id");				
				var name = $(this).find("[name=name]").val();
				//emps += id + "," + name + "," + sex + "," + age + ";";
				var pro={
					id:id,
					name:name,			
				}
				array.push(pro);
			})
			pros=JSON.stringify(array);
			pros=pros.replace(/{/g,"%7b");
			pros=pros.replace(/}/g,"%7d");
			//alert(emps);
			//emps = emps.substring(0, emps.length - 1);
			location.href = "proView?type=updateBatch3&pros=" + pros;

		})   

		$("#delete").click(function() {
			if (selectId > -1) {
				var r = confirm("是否删除");
				if (r) {
					location.href = "proView?type=delete&id=" + selectId;
				} else {
				}

			} else {
				alert("请选中一条数据");
			}

		})

		$("#deleteBatch").click(function() {
			if ($("#pro .select").length > 0) {
				var selectIds = new Array();
				$("#pro .select").each(function(index, element) {
					selectIds.push($(this).data("id"));
				})
				var r = confirm("是否删除");
				if (r) {
					location.href = "proView?type=deleteBatch&ids=" + selectIds;
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
		<h1>项目管理</h1>
		<form action="proView" class="form-horizontal" method="post">
			<div class="form-group">
				<div class="col-sm-4">
					<select class="form-control" name="name">
						<option value="">请选择项目</option>
						<c:forEach items="${listOfProject}" var="lop">
							<option value="${lop.name }"
								<c:if test="${lop.name==c.name }">selected</c:if>>${lop.name}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-sm-2">
					<button type="submit" class="btn btn-primary">搜索</button>
				</div>
			</div>
		</form>
		<table id="pro" class="table table-bordered table-striped table-hover">
			<thead>
				<tr style="font-weight: bold;">
					<td>id</td>
					<td>部门名称</td>
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
			<li id="pre"><a href="proView?ye=${p.ye-1}&name=${c.name}">上一页</a></li>
			<c:forEach begin="${p.beginYe}" end="${p.endYe}" varStatus="status">
				<li <c:if test="${p.ye==status.index}">class="active"</c:if>><a
					href="proView?ye=${status.index}&name=${c.name}">${status.index}</a></li>
				<!-- class="disabled" -->
			</c:forEach>
			<li id="next"><a href="proView?ye=${p.ye+1}&name=${c.name}">下一页</a></li>
		</ul>
		<div>
			<button type="button" class="btn btn-primary" id="add">添加</button>
			<button type="button" class="btn btn-warning" id="showUpdate">修改</button>
			<button type="button" class="btn btn-warning" id="showUpdateBatch1">批量修改1</button>
			<button type="button" class="btn btn-warning" id="showUpdateBatch2">批量修改2</button>
			<button type="button" class="btn btn-warning" id="updateBatch3">批量修改3</button>
			<button type="button" class="btn btn-danger" id="delete">删除</button>
			<button type="button" class="btn btn-danger" id="deleteBatch">批量删除</button>
		</div>

	</div>
</body>
</html>