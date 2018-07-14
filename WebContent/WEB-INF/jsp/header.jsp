<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>WEB01</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" type="text/css" />
		<style type="text/css">
			.clear{
				clear:both;
			}
		</style>
		<script src="${pageContext.request.contextPath}/resources/js/jquery.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js" type="text/javascript"></script>
		<script type="text/javascript">
			$(function(){
				//页面加载事件,ajax请求分类信息
				var url="${ctx}/category";
				var param="md=findAll";
				$.post(url,param,function(data){
					$.each(data,function(index,element){
						//alert(element.cname);
						var li="<li><a href='${ctx}/product?md=findPageByCid&cid="+element.cid+"&pageNumber=1'>"+element.cname+"</a></li>";
						$("#cate_list").append(li);
					})
				},"json")
				
			})
		
		</script>
	
	
	
	</head>

	<body>
		<div class="container-fluid">
			<!--
            	时间：2015-12-30
            	描述：菜单栏
            -->
			<div class="container-fluid">
				<div class="col-md-4">
					<img src="${pageContext.request.contextPath}/resources/img/logo2.png" />
				</div>
				<div class="col-md-5">
					<img src="${pageContext.request.contextPath}/resources/img/header.png" />
				</div>
				<div class="col-md-3" style="padding-top:20px">
					<ol class="list-inline">
						<c:if test="${empty user }">
							<li><a href="${ctx}/login.jsp">登录</a></li>
						    <li><a href="${ctx}/user?md=registerUI">注册</a></li>
						</c:if>
						<c:if test="${not empty user }">
							<li><a href="#"><font style="color:green;">${user.name }欢迎你</font></a></li>
						    <li><a href="${ctx}/user?md=logout">退出</a></li>
						    <li><a href="${ctx}/order?md=myOrdersWithPage">我的订单</a></li>
						    <c:if test="${'admin' eq user.remark }">
						    	<li><a href="${ctx}/admin?md=home">后台管理</a></li>
						    </c:if>
						</c:if>
						
						<li><a href="${ctx }/cart?md=show">购物车</a></li>
					</ol>
				</div>
			</div>
			<!--
            	时间：2015-12-30
            	描述：导航条
            -->
			<div class="container-fluid">
				<nav class="navbar navbar-inverse">
					<div class="container-fluid">
						<!-- Brand and toggle get grouped for better mobile display -->
						<div class="navbar-header">
							<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
								<span class="sr-only">Toggle navigation</span>
								<span class="icon-bar"></span>
								<span class="icon-bar"></span>
								<span class="icon-bar"></span>
							</button>
							<a class="navbar-brand" href="${ctx }">首页</a>
						</div>

						<!-- Collect the nav links, forms, and other content for toggling -->
						<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
							<ul class="nav navbar-nav" id="cate_list">
								
							</ul>
							<form class="navbar-form navbar-right" role="search">
								<div class="form-group">
									<input type="text" class="form-control" placeholder="Search">
								</div>
								<button type="submit" class="btn btn-default">Submit</button>
							</form>

						</div>
						<!-- /.navbar-collapse -->
					</div>
					<!-- /.container-fluid -->
				</nav>
			</div>