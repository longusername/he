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

#dep .select {
	background-color: #337ab7
}

#dep td {
	width: 180px;
}

#dep input {
	width: 80px;
}

#dep select {
	width: 40px;
}

#main h1 {
	text-align: center;
}
</style>
<link href="bootstrap/css/bootstrap.css" rel="stylesheet" />
<script src="js/jquery.js"></script>
<script
	src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="//apps.bdimg.com/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>
<script>
	$(document).ready(function() {
		var selectId = -1;
		$("#add").click(function() {

			location.href = "depView?type=showAdd";
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
			selected="<input type='text' name='name' value='"+$(this).children().eq(0).text() +"'/>"
			$(this).children().eq(0).html(selected);
		})  
		

		$("#showUpdate").click(function() {
			if (selectId > -1) {
				location.href = "depView?type=showUpdate&id=" + selectId;
			} else {
				alert("请选中一条数据");
			}

		})
		
		$("#showUpdateBatch1").click(function() {
			if (selectId > -1) {
				var selectIds = new Array();
				$("#dep .select").each(function() {
					selectIds.push($(this).data("id"));

				})
				location.href = "depView?type=showUpdateBatch1&ids=" + selectIds;
			} else {
				alert("请选中一条数据");
			}

		})
		
		$("#showUpdateBatch2").click(function() {
			if (selectId > -1) {
				var selectIds = new Array();
				$("#dep .select").each(function() {
					selectIds.push($(this).data("id"));

				})
				location.href = "depView?type=showUpdateBatch2&ids=" + selectIds;
			} else {
				alert("请选中一条数据");
			}

		})
		
		$("#updateBatch3").click(function() {
			var deps = "";
			var array=new Array();
			$(".db").each(function(index, element) {
				var id = $(this).data("id");				
				var name = $(this).find("[name=name]").val();
				//emps += id + "," + name + "," + sex + "," + age + ";";
				var dep={
					id:id,
					name:name,			
				}
				array.push(dep);
			})
			deps=JSON.stringify(array);
			deps=deps.replace(/{/g,"%7b");
			deps=deps.replace(/}/g,"%7d");
			//alert(emps);
			//emps = emps.substring(0, emps.length - 1);
			location.href = "depView?type=updateBatch3&deps=" + deps;

		})   

		$("#delete").click(function() {
			if (selectId > -1) {
				var r = confirm("是否删除");
				if (r) {
					location.href = "depView?type=delete&id=" + selectId;
				} else {
				}

			} else {
				alert("请选中一条数据");
			}

		})

		$("#deleteBatch").click(function() {
			if ($("#dep .select").length > 0) {
				var selectIds = new Array();
				$("#dep .select").each(function(index, element) {
					selectIds.push($(this).data("id"));
				})
				var r = confirm("是否删除");
				if (r) {
					location.href = "depView?type=deleteBatch&ids=" + selectIds;
				} else {
				}
			} else {
				alert("请选中一条数据");
			}
		})
		
		$("#showProject2Department").click(function() {
			if (selectId > -1) {
				location.href = "pro2DepView?depId="+selectId;
			} else {
				alert("请选中一条数据");
			}

		})
		
		$("#showProject2Department2").click(function() {
			if (selectId > -1) {
				location.href = "pro2DepView?type=m2&depId="+selectId;
			} else {
				alert("请选中一条数据");
			}

		})
		
		$("#showProject2Department3").click(function() {
			if (selectId > -1) {
				location.href = "pro2DepView?type=m3&depId="+selectId;
			} else {
				alert("请选中一条数据");
			}

		})
		
	    $("#showProject2Department4").click(function() {
			if (selectId > -1) {
				//location.href = "pro2DepView?type=m3&depId="+selectId;
				$("#modal").load("pro2DepView?type=m4&depId="+selectId);
				$("#myModal").modal("show");
				
				
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
		<h1>部门管理</h1>
		<form action="depView" class="form-horizontal" method="post">
			<div class="form-group">
				<div class="col-sm-4">
					<select class="form-control" name="name">
						<option value="">请选择部门</option>
						<c:forEach items="${listOfDepartment}" var="lod">
							<option value="${lod.name }"
								<c:if test="${lod.name==c.name }">selected</c:if>>${lod.name}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-sm-4">
					<input type="text" class="form-control" placeholder="请输入部门人数"
						name="empCount" value=${c.empCount==-1?"":c.empCount }>
				</div>
				<div class="col-sm-2">
					<button type="submit" class="btn btn-primary">搜索</button>
				</div>
			</div>
		</form>
		<table id="dep" class="table table-bordered table-striped table-hover">
			<thead>
				<tr style="font-weight: bold;">
					<td>部门名称</td>
					<td>部门人数</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${depList}" var="dep">
					<tr data-id="${dep.id}">
						<td>${dep.name}</td>
						<td><a href="empView?depId=${dep.id}">${dep.empCount}</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<ul class="pagination" id="page">
			<li id="pre"><a
				href="depView?ye=${p.ye-1}&name=${c.name}&empCount=${c.empCount}">上一页</a></li>
			<c:forEach begin="${p.beginYe}" end="${p.endYe}" varStatus="status">
				<li <c:if test="${p.ye==status.index}">class="active"</c:if>><a
					href="depView?ye=${status.index}&name=${c.name}&empCount=${c.empCount}">${status.index}</a></li>
				<!-- class="disabled" -->
			</c:forEach>
			<li id="next"><a
				href="depView?ye=${p.ye+1}&name=${c.name}&empCount=${c.empCount}">下一页</a></li>
		</ul>
		<div>
			<button type="button" class="btn btn-primary" id="add">添加</button>
			<button type="button" class="btn btn-warning" id="showUpdate">修改</button>
			<button type="button" class="btn btn-warning" id="showUpdateBatch1">批量修改1</button>
			<button type="button" class="btn btn-warning" id="showUpdateBatch2">批量修改2</button>
			<button type="button" class="btn btn-warning" id="updateBatch3">批量修改3</button>
			<button type="button" class="btn btn-danger" id="delete">删除</button>
			<button type="button" class="btn btn-danger" id="deleteBatch">批量删除</button>
			<button type="button" class="btn btn-primary"
				id="showProject2Department">项目管理</button>
			<button type="button" class="btn btn-primary"
				id="showProject2Department2">项目管理2</button>
			<button type="button" class="btn btn-primary"
				id="showProject2Department3">项目管理3</button>
			<button type="button" class="btn btn-primary"
				id="showProject2Department4">项目管理4</button>
		</div>
	</div>
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content" id="modal"></div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
</body>
</html>