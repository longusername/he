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

#pro, #noPro {
	width: 650px;
	height: 200px;
	border: 1px solid #337ab7;
	border-radius: 3px;
}

#btn {
	width: 330px;
	margin: 20px auto;
}

#add, #delete, #addBatch {
	margin-right: 50px;
}

.pro {
	background: #337ab7;
	height: 40px;
	line-height: 40px;
	float: left;
	margin-left: 5px;
	color: #fff;
	padding: 0 20px;
	margin-top: 10px;
	border-radius: 3px;
}

.select {
	background: #d9534f;
}
</style>
<link href="bootstrap/css/bootstrap.css" rel="stylesheet" />
<script src="js/jquery.js"></script>
<script src="//apps.bdimg.com/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>
<script>
	$(document).ready(function() {
		$(".pro").click(function() {
			$(this).toggleClass("select");
		})

		$("#add").click(function() {
			
			if ($("#noPro").find(".select").length > 0) {
				var proId=$("#noPro").find(".select").data("id");
					$.ajax({
						url:"pro2DepView",
						type:"POST", 
						data:{type:"add2",depId:${dep.id},proId:proId},
						dataType:"text",
						success:function(data){
							if(data=="true"){
								var pro=$("#noPro").find(".select");
								pro.removeClass("select");
								$("#pro").append(pro);
							}
						}
					})
			} else {
				alert("请选择数据");
			}
		})
		
		
		$("#delete").click(function() {
			
			if ($("#pro").find(".select").length > 0) {
				var proId=$("#pro").find(".select").data("id");
					$.ajax({
						url:"pro2DepView",
						type:"POST",
						data:{type:"delete2",depId:${dep.id},proId:proId},
						dataType:"text",
						success:function(data){
							if(data=="true"){
								var pro=$("#pro").find(".select");
								pro.removeClass("select");
								$("#noPro").append(pro);
							}
						}
					})
			} else {
				alert("请选择数据");
			}
		})
		
		
		$("#addBatch").click(function() {
			
			if ($("#noPro").find(".select").length > 0) {
				var proIds="";
				$("#noPro").find(".select").each(function(index,element){
					   proIds += $(this).data("id")+",";
					 
				})
				proIds=proIds.substring(0,proIds.length-1);
					   $.ajax({
						url:"pro2DepView", 
						type:"POST",
						data:{type:"addBatch",depId:${dep.id},proIds:proIds},    
						dataType:"text",
						success:function(data){
							if(data=="true"){
								var pro=$("#noPro").find(".select");
								pro.removeClass("select");
								$("#pro").append(pro);
							}
						}
					})   
			} else {
				alert("请选择数据");
			}
			
			
		})
		
		
		$("#deleteBatch").click(function() {
			
			if ($("#pro").find(".select").length > 0) {
				var proIds="";
				$("#pro").find(".select").each(function(index,element){
					   proIds += $(this).data("id")+","; 
					 
				})
				proIds=proIds.substring(0,proIds.length-1);
					   $.ajax({
						url:"pro2DepView", 
						type:"POST",
						data:{type:"deleteBatch",depId:${dep.id},proIds:proIds},     
						dataType:"text",
						success:function(data){
							if(data=="true"){
								var pro=$("#pro").find(".select");
								pro.removeClass("select");
								$("#noPro").append(pro);
							}
						}
					})   
			} else {
				alert("请选择数据");
			}			
		})
		
		
		var proLeft=$("#pro").offset().left;
		var proTop=$("#pro").offset().top;
		var noProLeft=$("#noPro").offset().left;
		var noProTop=$("#noPro").offset().top;
		var proWidth=parseFloat($("#pro").css("width"));
		var proHeight=parseFloat($("#pro").css("height"));
		var noProWidth=parseFloat($("#noPro").css("width"));
		var noProHeight=parseFloat($("#noPro").css("height"));
		var startLeft;
		var startTop; 
		
		$(".pro").draggable({	
			start:function(){ 
				startLeft=$(this).offset().left;
				startTop=$(this).offset().top;
			},
			stop:function(){
				var stopLeft=$(this).offset().left;
				var stopTop=$(this).offset().top;
				 if(stopLeft>=proLeft&&stopLeft<=proLeft+proWidth&&stopTop>=proTop&&stopTop<=proTop+proHeight){
			   		 var proId=$(this).data("id");
			   	     var pro=$(this); 
						$.ajax({
							url:"pro2DepView",
							type:"POST", 
							data:{type:"add2",depId:${dep.id},proId:proId},
							dataType:"text",
							success:function(data){
								if(data=="true"){
									pro.css("position","static");
									$("#pro").append(pro);
									pro.css("position","relative");
									pro.css({"left":"0","top":"0"});
								}
							}
						})
				}else{
					$(this).offset({left:startLeft,top:startTop});
				}
				 
				 if(stopLeft>=noProLeft&&stopLeft<=noProLeft+noProWidth&&stopTop>=noProTop&&stopTop<=noProTop+noProHeight){
			   		 var proId=$(this).data("id");
			   	     var pro=$(this); 
						$.ajax({
							url:"pro2DepView",
							type:"POST", 
							data:{type:"delete2",depId:${dep.id},proId:proId},
							dataType:"text",
							success:function(data){
								if(data=="true"){
									pro.css("position","static");
									$("#noPro").append(pro);
									pro.css("position","relative");
									pro.css({"left":"0","top":"0"});
								}
							}
						})
				}else{
					$(this).offset({left:startLeft,top:startTop});
				} 	 
				 
			}		
		})
	
		
	})
</script>
</head>

<body>
	<div id="main">
		<h1>${dep.name}</h1>
		<div id="pro">
			<c:forEach items="${proList}" var="pro">
				<div class="pro" data-id="${pro.id }">${pro.name }</div>
			</c:forEach>
		</div>
		<div id="btn">
			<button type="button" class="btn btn-primary" id="add">↑</button>
			<button type="button" class="btn btn-primary" id="delete">↓</button>
			<button type="button" class="btn btn-primary" id="addBatch">↑↑↑</button>
			<button type="button" class="btn btn-primary" id="deleteBatch">↓↓↓</button>
		</div>
		<div id="noPro">
			<c:forEach items="${listOfNoProject}" var="pro">
				<div class="pro" data-id="${pro.id }">${pro.name }</div>
			</c:forEach>
		</div>
	</div>
</body>
</html>