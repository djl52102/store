<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="${pageContext.request.contextPath }" var="ctx"></c:set>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${ctx }/resources/css/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx }/resources/css/icon.css">
<script type="text/javascript" src="${ctx }/resources/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx }/resources/js/jquery.easyui.min.js"></script>
<script type="text/javascript">
$(function(){
	$(".menu_a").click(function(){
		//点击添加新的标签页
		var title=$(this).text();
		var xurl=$(this).attr("xurl");

		if($("#tt").tabs("exists",title)){
			$("#tt").tabs("select",title)
		}else{
			$('#tt').tabs('add',{    
			    title:title,    
			    content:'<iframe src="'+xurl+'" style="width:100%;height:100%;border:0px"></iframe>',    
			    closable:true,       
			});
		} 
	})
})


</script>
</head>
<body>
<div id="cc" class="easyui-layout" data-options="fit:true">   
	    <div data-options="region:'north'" style="height:100px;">
	    	<div style="text-align:center;color:black;margin:0px;padding: 0px;line-height: 80px;font-weight: bold;font-size: 30px" >
	    		欢迎访问黑马商城后台管理系统
	    	</div>
	    </div>   
	    <div data-options="region:'south'" style="height:100px;">
	    	<div style="text-align:center;color:#15428B;margin:0px;padding: 0px;line-height: 80px;font-weight: bold;font-size: 20px;font-family: 微软雅黑;" >
	    		黑马商城版权所有&copy;
	    	</div>
	    </div>   
	    <div data-options="region:'west',title:'菜单管理'" style="width:180px;">
	    	<div id="aa" class="easyui-accordion"  data-options="fit:true">   
			    <div title="分类管理" data-options="iconCls:'icon-large-chart'" style="overflow:auto;padding:10px;">   
			  		<a href="javascript:;" class="menu_a"  xurl="${ctx}/admin?md=categoryUI"  >分类列表</a>
			  	
			    </div>   
			    <div title="商品管理" data-options="iconCls:'icon-large-chart'" style="padding:10px;">   
			    	<a href="javascript:;" class="menu_a" xurl="${ctx}/admin?md=productUI" >商品列表</a>
			    </div>   
			    <div title="订单管理">   
			    </div>   
			</div>  
	    </div>   
	    <div data-options="region:'center'" style="background:#eee;">
	    	    <div id="tt" class="easyui-tabs" data-options="fit:true" >   
			    <div title="欢迎页" style="padding:20px;">   
			        欢迎欢迎 热烈欢迎  
			    </div>   
			</div>  
	    </div>   
	</div>  
</body>
</html>