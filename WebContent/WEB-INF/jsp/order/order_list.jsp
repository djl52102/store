<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
		<div class="container">
			<div class="row">

				<div style="margin:0 auto; margin-top:10px;width:950px;">
					<strong>我的订单</strong>
					<table class="table table-bordered">
						<c:forEach items="${pb.list }" var="order">
						<tbody>
							<tr class="success">
								<th colspan="2"><a href="javascript:window.location.href='${ctx }/order?md=info&oid=${order.oid }'" >订单编号:${order.oid }</a></th>
								<th colspan="1">
								<c:if test="${order.state == 0 }">
									订单状态:未付款
								</c:if>
								<c:if test="${order.state == 1}">
									订单状态:已付款
								</c:if>
								<c:if test="${order.state == 2 }">
									订单状态:已发货
								</c:if>
								<c:if test="${order.state == 3 }">
									订单状态:已完成
								</c:if>
								 </th>
								<th colspan="2">订单时间:<fmt:formatDate value="${order.ordertime }" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></th>
							</tr>
							<tr class="warning">
								<th>图片</th>
								<th>商品</th>
								<th>价格</th>
								<th>数量</th>
								<th>小计</th>
							</tr>
							<c:forEach items="${order.itemViews}" var="oi">
							<tr class="active">
								<td width="60" width="40%">
									<input type="hidden" name="id" value="22">
									<img src="${pageContext.request.contextPath}/${oi.pimage}" width="70" height="60">
								</td>
								<td width="30%">
									<a target="_blank">${oi.pname }</a>
								</td>
								<td width="20%">
									&yen;${oi.shop_price }
								</td>
								<td width="10%">
									${oi.count }
								</td>
								<td width="15%">
									<span class="subtotal">&yen;${oi.subtotal }</span>
								</td>
							</tr>
							</c:forEach>
						</tbody>
						</c:forEach>
					</table>
				</div>
			</div>
			<!--分页 -->
		  <div style="width:380px;margin:0 auto;margin-top:50px;">
			<ul class="pagination" style="text-align:center; margin-top:10px;">
				<!-- 前一页 -->
				<c:if test="${1==pb.pageNumber }">
					<li class="disabled"><a href="javascript:;" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
				</c:if>
				<c:if test="${1!=pb.pageNumber }">
					<li><a href="${ctx }/order?md=myOrdersWithPage&pageNumber=${pb.pageNumber-1}" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
				</c:if>
				
				
				<c:forEach begin="1" end="${pb.totalPage }" step="1" var="i">
					<c:if test="${i==pb.pageNumber}">
						<li class="active"><a href="${ctx }/order?md=myOrdersWithPage&pageNumber=${i}">${i}</a></li>
					</c:if>
					<c:if test="${i!=pb.pageNumber }">
						<li ><a href="${ctx }/order?md=myOrdersWithPage&pageNumber=${i}">${i}</a></li>
					</c:if>
				</c:forEach>
				
				<c:if test="${pb.pageNumber==pb.totalPage }">
				<li class="disabled">
					<a href="javascript:;" aria-label="Next">
						<span aria-hidden="true">&raquo;</span>
					</a>
				</li>
				</c:if>
				
				<c:if test="${pb.pageNumber!=pb.totalPage }">
				<li>
					<a href="${ctx }/order?md=myOrdersWithPage&pageNumber=${pb.pageNumber+1}" aria-label="Next">
						<span aria-hidden="true">&raquo;</span>
					</a>
				</li>
				</c:if>
				
			</ul>
		</div>
		<!-- 分页结束=======================        -->
		</div>

<%@ include file="../foot.jsp" %>