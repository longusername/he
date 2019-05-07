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

#emp img {
	width: 30px;
	height: 30px;
	overflow: hidden;
}

/* #emp img:hover {
	transform: scale(3);
	transition: all 0.8s;
} */
#bigPicture {
	position: absolute;
	display: none;
}
</style>
<link href="bootstrap/css/bootstrap.css" rel="stylesheet" />
<script src="js/jquery.js"></script>
<script>
	$(document).ready(function() {
		var selectId = -1;
		$("#add").click(function() {

			location.href = "empView?type=showAdd";
		})
		
		$("#add2").click(function() {

			location.href = "empView?type=showAdd2";
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
			 if($(this).children().eq(1).html()=='男'){
				selected="<select name='sex'><option selected value='男'>男</option><option value='女'>女</option></select>";
			}else if($(this).children().eq(1).html()=='女'){
				selected="<select name='sex'><option value='男'>男</option><option selected value='女'>女</option></select>";
			}

			var selectedDep="<select style='width:120px;' class='form-control' name='depId'><option value=''>选择部门</option><c:forEach items='${listOfDepartment}' var='lod'><option value='${lod.id}'>${lod.name}</option></c:forEach></select>"; 

			$(this).children().eq(0).html("<input type='text' name='name' value='"+$(this).children().eq(0).text() +"'/>");
			$(this).children().eq(1).html(selected);
			$(this).children().eq(2).html("<input type='text' name='age' value='"+$(this).children().eq(2).text() +"'/>"); 
			$(this).children().eq(3).html(selectedDep);
		})
		

		$("#showUpdate").click(function() {
			if (selectId > -1) {
				location.href = "empView?type=showUpdate&id=" + selectId;
			} else {
				alert("请选中一条数据");
			}

		})
		
		$("#showUpdateBatch1").click(function() {
			if (selectId > -1) {
				var selectIds = new Array();
				$("#emp .select").each(function() {
					selectIds.push($(this).data("id"));

				})
				location.href = "empView?type=showUpdateBatch1&ids=" + selectIds;
			} else {
				alert("请选中一条数据");
			}

		})
		
		$("#showUpdateBatch2").click(function() {
			if (selectId > -1) {
				var selectIds = new Array();
				$("#emp .select").each(function() {
					selectIds.push($(this).data("id"));

				})
				location.href = "empView?type=showUpdateBatch2&ids=" + selectIds;
			} else {
				alert("请选中一条数据");
			}

		})
		
		$("#updateBatch3").click(function() {
			var emps = "";
			var array=new Array();
			$(".db").each(function(index, element) {
				var id = $(this).data("id");				
				var name = $(this).find("[name=name]").val();
				var sex = $(this).find("[name=sex]").val();
				var age = $(this).find("[name=age]").val();
				var d_id = $(this).find("[name=depId]").val();
				if(d_id == ""){
					d_id = -1;
				}
				//emps += id + "," + name + "," + sex + "," + age + ";";
				var dep={
					id:d_id
				}
				var emp={
					id:id,
					name:name,
					sex:sex,
					age:age, 
					dep:dep
				}
				array.push(emp);
			})
			emps=JSON.stringify(array);
			emps=emps.replace(/{/g,"%7b");
			emps=emps.replace(/}/g,"%7d");
			//alert(emps);
			//emps = emps.substring(0, emps.length - 1);
			location.href = "empView?type=updateBatch3&emps=" + emps;

		})   

		$("#delete").click(function() {
			if (selectId > -1) {
				var r = confirm("是否删除");
				if (r) {
					location.href = "empView?type=delete&id=" + selectId;
				} else {
				}

			} else {
				alert("请选中一条数据");
			}

		})

		$("#deleteBatch").click(function() {
			if ($("#emp .select").length > 0) {
				var selectIds = new Array();
				$("#emp .select").each(function(index, element) {
					selectIds.push($(this).data("id"));
				})
				var r = confirm("是否删除");
				if (r) {
					location.href = "empView?type=deleteBatch&ids=" + selectIds;
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
		
		$("#emp img").hover(function(event){
			var src=$(this).attr("src");
			$("#bigPicture").show();
			$("#bigPicture img").attr("src",src); 
			$("#bigPicture img").css({"width":"100px","height":"100px"});
			$("#bigPicture").css({"left":event.pageX+10,"top":event.pageY+10});
		},function(){			
			$("#bigPicture").hide();			
		})
	})
</script>
</head>

<body>
	<div id="main">
		<h1>员工管理</h1>
		<form action="empView" class="form-horizontal" method="post">
			<div class="form-group">
				<div class="col-sm-2">
					<input type="text" class="form-control" placeholder="姓名"
						name="name" value=${c.name }>
				</div>
				<div class="col-sm-3">
					<select class="form-control" name="sex">
						<option value="">选择性别</option>
						<option value='男' <c:if test="${c.sex=='男' }">selected</c:if>>男</option>
						<option value='女' <c:if test="${c.sex=='女' }">selected</c:if>>女</option>
					</select>
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control" placeholder="年龄" name="age"
						value=${c.age==-1?"":c.age }>
				</div>
				<div class="col-sm-3">
					<select class="form-control" name="depId">
						<option value="">选择部门</option>
						<c:forEach items="${listOfDepartment}" var="lod">
							<option value="${lod.id }"
								<c:if test="${lod.id==c.dep.id }">selected</c:if>>${lod.name}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-sm-2">
					<button type="submit" class="btn btn-primary">搜索</button>
				</div>
			</div>
		</form>
		<table id="emp" class="table table-bordered table-striped table-hover">
			<thead>
				<tr style="font-weight: bold;">
					<td>姓名</td>
					<td>性别</td>
					<td>年龄</td>
					<td>部门名称</td>
					<td>照片</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${empList}" var="emp">
					<tr data-id="${emp.id}" data-depid="${emp.dep.id }">
						<td>${emp.name}</td>
						<td>${emp.sex}</td>
						<td>${emp.age}</td>
						<td>${emp.dep.name}</td>
						<td><c:if test="${not empty emp.picture}">
								<img src="empPic/${emp.picture}" />
							</c:if></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<ul class="pagination">
			<li><a
				href="empView?ye=1&name=${c.name}&sex=${c.sex}&age=${c.age}&depId=${c.dep.id}&picture=${c.picture}">首页</a></li>
			<li id="pre"><a
				href="empView?ye=${p.ye-1}&name=${c.name}&sex=${c.sex}&age=${c.age}&depId=${c.dep.id}&picture=${c.picture}">上一页</a></li>
			<c:forEach begin="${p.beginYe}" end="${p.endYe}" varStatus="status">
				<li <c:if test="${p.ye==status.index}">class="active"</c:if>><a
					href="empView?ye=${status.index}&name=${c.name}&sex=${c.sex}&age=${c.age}&depId=${c.dep.id}&picture=${c.picture}">${status.index}</a></li>
				<!-- class="disabled" -->
			</c:forEach>
			<li id="next"><a
				href="empView?ye=${p.ye+1}&name=${c.name}&sex=${c.sex}&age=${c.age}&depId=${c.dep.id}&picture=${c.picture}">下一页</a></li>
			<li><a
				href="empView?ye=${p.maxYe}&name=${c.name}&sex=${c.sex}&age=${c.age}&depId=${c.dep.id}&picture=${c.picture}">尾页</a></li>

		</ul>
		<div>
			<button type="button" class="btn btn-primary" id="add">添加</button>
			<button type="button" class="btn btn-primary" id="add2">添加2</button>
			<button type="button" class="btn btn-warning" id="showUpdate">修改</button>
			<button type="button" class="btn btn-warning" id="showUpdateBatch1">批量修改1</button>
			<button type="button" class="btn btn-warning" id="showUpdateBatch2">批量修改2</button>
			<button type="button" class="btn btn-warning" id="updateBatch3">批量修改3</button>
			<button type="button" class="btn btn-danger" id="delete">删除</button>
			<button type="button" class="btn btn-danger" id="deleteBatch">批量删除</button>
		</div>
		<div id="bigPicture">
			<img src="" />
		</div>
	</div>
</body>
</html>