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
		//数据表格
		 $('#dg').datagrid({    
			    url:'${ctx}/admin?md=categories',    
			    columns:[[    
			        {field:'cid',title:'id',width:100},    
			        {field:'cname',title:'分类名称',width:100},    
			        {field:'xxx',title:'操作',width:100,
			        	formatter: function(value,row,index){
			        		var cid=row.cid;
							return '<a href="javascript:;" onclick="update(\''+cid+'\')">编辑</a> | <a a href="javascript:;" onclick="del(\''+cid+'\')">删除</a>'
						}
                    }    
			    ]],
			    fit:true,
			    fitColumns:true,
			    toolbar: [{
			    	text:'添加分类',
					iconCls: 'icon-add',
					handler: function(){
						$("#add").dialog("open");
					}
				}]
		  });
		 
		  //添加分类
		 $('#add').dialog({    
			    title: '添加分类',    
			    width: 400,    
			    height: 200,    
			    closed: true,          
			    modal: true   
		 });
		  
	})
 
	//添加分类
	function saveCategory(){
		//将对话框关闭
		$("#add").dialog("close");
		$('#saveForm').form('submit', {    
		    url:'${ctx}/admin?md=addCategory',       
		    success:function(data){    
		        //将字符串转为json对象
		    	var jsonobj = eval('(' + data + ')');
		        if(jsonobj.state == 1){
		        	$("#dg").datagrid("reload");
		        }
		    	parent.$.messager.show({
		    		title:'我的消息',
		    		msg:jsonobj.msg,
		    		timeout:5000,
		    		showType:'slide'
		    	});
		    }    
		});
	}
	
	//编辑分类
	function update(cid){
		var url="${ctx}/admin?md=findCategoryByCid&cid="+cid;
		$("#edit").dialog("open");
		$('#edit').form('load',url);
	}
	
	//更新分类表单提交
	function updateCategory(){
		//将对话框关闭
		$("#edit").dialog("close");
		$('#updateForm').form('submit', {    
		    url:'${ctx}/admin?md=updateCategory',       
		    success:function(data){    
		        //将字符串转为json对象
		    	var jsonobj = eval('(' + data + ')');
		        if(jsonobj.state == 1){
		        	$("#dg").datagrid("reload");
		        }
		    	parent.$.messager.show({
		    		title:'我的消息',
		    		msg:jsonobj.msg,
		    		timeout:5000,
		    		showType:'slide'
		    	});
		    }    
		});
	}
	
	//删除分类
	function del(cid){
		$.messager.confirm('确认对话框', '您确定要删除该分类吗', function(r){
			if (r){
			    // 退出操作;
			    var url="${ctx}/admin?md=delCategory";
			    var params={
			    	"cid":cid	
			    }
			    $.post(url,params,function(data){
			    	 //将字符串转为json对象
			    	//var jsonobj = eval('(' + data + ')');
			    	//alert(data.msg);
			        if(data.state == 1){
			        	$("#dg").datagrid("reload");
			        }
			    	parent.$.messager.show({
			    		title:'我的消息',
			    		msg:data.msg,
			    		timeout:5000,
			    		showType:'slide'
			    	});
			    },"json");
			}
		});
	}
	
</script>
</head>
<body>
	<!-- 数据显示表格 -->
	<table id="dg"></table> 
	<!-- 添加对话框 -->
	<div id="add" class="easyui-dialog" title="添加分类" style="width:400px;height:200px;"   
        data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true">
		<form method="post" id="saveForm" action="" >
		   		<table width="100%">
		   			<tr align="center">
		   				<td>
		   					分类名称:
		   				</td>
		   				<td>
		   					<input type="text" class="easyui-textbox" name="cname"><br>
		   				</td>
		   			</tr>
		   			<tr  align="center">
		   				<td colspan="2">
		   					<input type="button" class="easyui-linkbutton" value="保存" onclick="saveCategory()" style="width:80px"/>
		   				</td>
		   			</tr>
		   		</table>
		 </form>
	</div>  
	<!-- 编辑对话框 -->
	<!-- 添加对话框 -->
	<div id="edit" class="easyui-dialog" title="编辑分类" style="width:400px;height:200px;"   
        data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true">
		<form method="post" id="updateForm" action="" >
				<input type="hidden" name="cid">
		   		<table width="100%">
		   			<tr align="center">
		   				<td>
		   					分类名称:
		   				</td>
		   				<td>
		   					<input type="text" class="easyui-textbox" name="cname"><br>
		   				</td>
		   			</tr>
		   			<tr  align="center">
		   				<td colspan="2">
		   					<input type="button" class="easyui-linkbutton" value="保存" onclick="updateCategory()" style="width:80px"/>
		   				</td>
		   			</tr>
		   		</table>
		 </form>
	</div>
</body>
</html>