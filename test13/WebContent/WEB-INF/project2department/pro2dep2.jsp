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
		var flag=1; 
		var selectId = -1;
		$("#add").bind("click",add);
		
		
		
		if($("#selectPro").children().length==0){ 
			flag=0;
			$("#add").unbind("click"); 
			$("#add").addClass("disabled");
		} 
		
		
		
		function add() {      
			var proId=$("#selectPro").val(); 
			var i=0;
			//location.href = "pro2DepView?type=add2&depId=${dep.id}&proId="+proId;
			$.ajax({
				url:"pro2DepView", 
				type:"post",
				data:{type:"add2",depId:${dep.id},proId:proId}, 
				dataType:"text",
				success:function(data){
					if(data=="true"){
						var proName="";
						var tr=""; 
						$("#selectPro").children().each(function(index,element){
							 if($(this).val()==proId){ 
								 proName=$(this).text();
								 i=index;
							 } 
						})				 	
						tr="<tr data-id='"+proId+"'><td>"+proId+"</td><td>"+proName+"</td></tr>";
						$("#pro").append(tr);  
						$("#selectPro").children().eq(i).remove(); 
						
						if($("#selectPro").children().length==0){ 
							flag =0;
							$("#add").unbind("click"); 
							$("#add").addClass("disabled");
						} 
					}     
				}
			})
		}
		
		
		
		
		
		<c:if test="${f:length(listOfNoProject) == 0}">  
		</c:if>
		
		
		$(document).on("click","tbody tr",function() {
			$(this).toggleClass("select");
			selectId = $(this).data("id");
		})
	
		$("#delete").click(function() {
			if (selectId > -1) {
				var r = confirm("是否删除");
				if (r) {
					var proId=selectId; 
					var i=0;
					//location.href = "pro2DepView?type=add2&depId=${dep.id}&proId="+proId;
					$.ajax({
						url:"pro2DepView", 
						type:"post",
						data:{type:"delete2",depId:${dep.id},proId:proId},      
						dataType:"text",
						success:function(data){
							if(data=="true"){ 
								var proName="";
								var option=""; 
								$("tr").each(function(index,element){
									 if($(this).data("id")==proId){ 
										 proName=$(this).children().eq(1).text();
										 i=index;
									 }
								})
								option ="<option value='"+proId+"'>"+proName+"</option>";
								$("#selectPro").append(option);
								$("tr").eq(i).remove();
								
								if(flag==0){
									flag=1;
									$("#add").bind("click",add);
									$("#add").removeClass("disabled");
									selectId = -1;
								}
							}     
						}
					})
				} else {
				}

			} else {
				alert("请选中一条数据");
			}

		})
	

		
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