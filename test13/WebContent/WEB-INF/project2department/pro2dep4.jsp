<%@page import="com.oracle.webservices.internal.api.EnvelopeStyle.Style"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%-- <%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="entity.*,util.*"%> --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="f"%>


<title>Insert title here</title>
<style type="text/css">
#modalMain {
	width: 538px;
	margin: 20px auto;
}

#modalMain #pro, #noPro {
	width: 550px;
	height: 200px;
	border: 1px solid #337ab7;
	border-radius: 3px;
}

#modalMain #btn {
	width: 330px;
	margin: 20px auto;
}

#modalMain #add, #delete, #addBatch {
	margin-right: 50px;
}

#modalMain .pro {
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

#modalMain .select {
	background: #d9534f;
}
</style>
<script>
	$(document).ready(function() {
		$("#modalMain .pro").click(function() {
			$(this).toggleClass("select");
		})

		$("#modalMain #add").click(function() {
			
			if ($("#modalMain #noPro").find(".select").length > 0) {
				var proId=$("#modalMain #noPro").find(".select").data("id");
					$.ajax({
						url:"pro2DepView",
						type:"POST",
						data:{type:"add2",depId:${dep.id},proId:proId},
						dataType:"text",
						success:function(data){
							if(data=="true"){
								var pro=$("#modalMain #noPro").find(".select");
								pro.removeClass("select");
								$("#modalMain #pro").append(pro);
							}
						}
					})
			} else {
				alert("请选择数据");
			}
		})
		
		
		$("#modalMain #delete").click(function() {
			
			if ($("#modalMain #pro").find(".select").length > 0) {
				var proId=$("#modalMain #pro").find(".select").data("id");
					$.ajax({
						url:"pro2DepView",
						type:"POST",
						data:{type:"delete2",depId:${dep.id},proId:proId},
						dataType:"text",
						success:function(data){
							if(data=="true"){
								var pro=$("#modalMain #pro").find(".select");
								pro.removeClass("select");
								$("#modalMain #noPro").append(pro);
							}
						}
					})
			} else {
				alert("请选择数据");
			}
		})
		
		
		$("#modalMain #addBatch").click(function() {
			
			if ($("#modalMain #noPro").find(".select").length > 0) {
				var proIds="";
				$("#modalMain #noPro").find(".select").each(function(index,element){
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
								var pro=$("#modalMain #noPro").find(".select");
								pro.removeClass("select");
								$("#modalMain #pro").append(pro);
							}
						}
					})   
			} else {
				alert("请选择数据");
			}
			
			
		})
		
		
		$("#modalMain #deleteBatch").click(function() {
			
			if ($("#modalMain #pro").find(".select").length > 0) {
				var proIds="";
				$("#modalMain #pro").find(".select").each(function(index,element){
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
								var pro=$("#modalMain #pro").find(".select");
								pro.removeClass("select");
								$("#modalMain #noPro").append(pro);
							}
						}
					})   
			} else {
				alert("请选择数据");
			}			
		})
		
		
		var proLeft=$("#modalMain #pro").offset().left;
		var proTop=$("#modalMain #pro").offset().top;
		var noProLeft=$("#modalMain #noPro").offset().left;
		var noProTop=$("#modalMain #noPro").offset().top;
		var proWidth=parseFloat($("#modalMain #pro").css("width"));
		var proHeight=parseFloat($("#modalMain #pro").css("height"));
		var noProWidth=parseFloat($("#modalMain #noPro").css("width"));
		var noProHeight=parseFloat($("#modalMain #noPro").css("height"));
		var startLeft;
		var startTop; 
		$("#modalMain .pro").draggable({	
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
									$("#modalMain #pro").append(pro);
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
									$("#modalMain #noPro").append(pro);
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

<body>
	<div id="modalMain">
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
