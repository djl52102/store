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
		$('#dg').datagrid({    
		    url:'${ctx}/admin?md=products',    
		    columns:[[    
		        {field:'pid',title:'id',width:100},    
		        {field:'pname',title:'商品名',width:100},    
		        {field:'shop_price',
			        title:'商城价格',
			        width:40,
			        formatter:function(value,row,index){
			        	return value;
			     }},
			     {field:'pdesc',title:'描述',width:100},
			     {field:'is_hot',title:'是否热门',width:40,
			    	 formatter:function(value,row,index){
			    		 if(value == 1){
			    			 return '是';
			    		 }else{
			    			 return '否';
			    		 } 
			    	 }},
			    {field:'pimage',
			     title:'图片展示',
			     width:50,
			     height:240,
			     formatter:function(value,row,index){
			        	 //return value
			        	 //return row.img
			        	 return "<img src='${ctx}/"+value+"' width='60px'>"
			    }}  
		    ]],
		    fit:true,
		    fitColumns:true,
		    pagination:true,
		    nowrap:false,
		    toolbar: [{
		    	text:'添加商品',
				iconCls: 'icon-add',
				handler: function(){
					$("#add").dialog("open");
				}
			}]
		}); 
		
		//为分类标签添加下拉框
		$('#caid').combobox({    
		    url:'${ctx}/category?md=findAll',    
		    valueField:'cid',    
		    textField:'cname'   
		});
	})
	
	//添加商品方法
	function save_product(){
		//将对话框关闭
		$("#add").dialog("close");
		$('#save_form').form('submit', {    
		    url:'${ctx}/productAdd',       
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
</script>
<body>
	<table id="dg"></table> 
	<div id="add" class="easyui-dialog" title="添加商品" style="width:500px;height:200px;"   
        data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true">   
		<form id="save_form" method="post" enctype="multipart/form-data">
				<table>
					<tr>
						<td>商品名称:</td>
						<td><input class="easyui-textbox" name="pname" style="width:100%"></td>
						<td>所属分类:</td>
						<td><input id="caid" name="cid" style="width:100%"></td>
					</tr>
					<tr>
						<td>市场价格:</td>
						<td><input class="easyui-textbox" name="market_price" style="width:100%"></td>
						<td>商城价格:</td>
						<td><input class="easyui-textbox" name="shop_price" style="width:100%"></td>
					</tr>
					<tr>
						<td>是否热门:</td>
						<td>
							<select class="easyui-combobox" name="is_hot" style="width:100%" data-options="panelHeight:'auto'">   
							    <option value="1">是</option>   
							    <option value="0">否</option>   
							</select>
						</td>
						<td>图片:</td>
						<td>
							<input name="pimage" class="easyui-filebox" data-options="buttonText:'选择文件'" style="width:100%">
						</td>
					</tr>
					<tr>
						<td>商品描述:</td>
						<td colspan="3">
							<input class="easyui-textbox" name="pdesc" style="width:100%" data-options="multiline:true">
						</td>
					</tr>
					<tr style="text-align:center;padding:5px 0">
						<td colspan="4">
							<input type="button" value="保存" class="easyui-linkbutton" onclick="save_product()" style="width:80px">
							<input type="reset" value="重置" class="easyui-linkbutton"  style="width:80px">
						</td>
					</tr>
				</table>
			</form>        
	</div>  
</body>
</html>