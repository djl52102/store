<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>WEB01</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/resources/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js" type="text/javascript"></script>
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
						<li><a href="">登录</a></li>
						<li><a href="">注册</a></li>
						<li><a href="">购物车</a></li>
					</ol>
				</div>
			</div>
			<!--
            	时间：2015-12-30
            	描述：导航条
            -->
			<div class="container-fluid">
					信息页面展示${msg }
			</div>

		</div>
		<div class="container-fluid">
				<div style="margin-top:50px;">
					<img src="${pageContext.request.contextPath}/resources/img/footer.jpg" width="100%" height="78" alt="我们的优势" title="我们的优势" />
				</div>
		
				<div style="text-align: center;margin-top: 5px;">
					<ul class="list-inline">
						<li><a href="info.html">关于我们</a></li>
						<li><a>联系我们</a></li>
						<li><a>招贤纳士</a></li>
						<li><a>法律声明</a></li>
						<li><a>友情链接</a></li>
						<li><a>支付方式</a></li>
						<li><a>配送方式</a></li>
						<li><a>服务声明</a></li>
						<li><a>广告声明</a></li>
					</ul>
				</div>
				<div style="text-align: center;margin-top: 5px;margin-bottom:20px;">
					Copyright &copy; 2005-2016 传智商城 版权所有
				</div>
			</div>
		</div>

	</body>

</html>