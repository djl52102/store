<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<script type="text/javascript">
	$(function(){
		//删除购物项方法
		$(".delete").click(function(){
			 var pid=$(this).attr("data-id");
			 if(confirm("你确定要删除该购物项吗?")){
				 window.location.href="${ctx}/cart?md=del&pid="+pid;
			 }
		})
		
		//清空购物车
		$("#clear").click(function(){
			if(confirm("你确定要清空购物车吗")){
				window.location.href="${ctx}/cart?md=clear"
			}
		})
	})
	

</script>

		<div class="container">
			<div class="row">
				<div style="margin:0 auto; margin-top:10px;width:950px;">
					<strong style="font-size:16px;margin:5px 0;">订单详情</strong>
					<table class="table table-bordered">
						<tbody>
							<tr class="warning">
								<th>图片</th>
								<th>商品</th>
								<th>价格</th>
								<th>数量</th>
								<th>小计</th>
								<th>操作</th>
							</tr>
							<c:if test="${empty cart.map }">
								<tr><td colspan="6">购物车空空如也</td></tr>
							</c:if>
							<c:if test="${not empty cart.map }">
							<c:forEach items="${cart.map }"  var="item">
							<tr class="active">
								<td width="60" width="40%">
									<input type="hidden" name="id" value="22">
									<img src="${pageContext.request.contextPath}/${item.p.pimage}" width="70" height="60">
								</td>
								<td width="30%">
									<a target="_blank">${item.p.pname }</a>
								</td>
								<td width="20%">
									&yen;${item.p.shop_price }
								</td>
								<td width="10%">
									${item.count }
								</td>
								<td width="15%">
									<span class="subtotal">&yen;${item.subTotal }</span>
								</td>
								<td>
									<a href="javascript:;" class="delete"  data-id="${item.p.pid }">删除</a>
								</td>
							</tr>
							</c:forEach>
							</c:if>
						</tbody>
					</table>
				</div>
			</div>

			<div style="margin-right:130px;">
				<c:if test="${empty cart }">
					<div style="text-align:right;">
			                         商品金额: <strong style="color:#ff6600;">&yen;0.00</strong>
				    </div>
				</c:if>
				<c:if test="${not empty cart }">
					<div style="text-align:right;">
			                         商品金额: <strong style="color:#ff6600;">&yen;${cart.total }</strong>
				    </div>
				</c:if>
				
				<div style="text-align:right;margin-top:10px;margin-bottom:10px;">
					<a href="javascript:;" id="clear" class="clear">清空购物车</a>
					<a href="javascript:location.href='${ctx }/order?md=createOrder'">
						<input type="submit" width="100" value="提交订单" name="submit" border="0" style="background: url('${pageContext.request.contextPath}/resources/images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
						height:35px;width:100px;color:white;">
					</a>
				</div>
			</div>

		</div>

<%@ include file="../foot.jsp" %>		