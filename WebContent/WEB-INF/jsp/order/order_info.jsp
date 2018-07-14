<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
		<div class="container">
			<div class="row">

				<div style="margin:0 auto;margin-top:10px;width:950px;">
					<strong>订单详情</strong>
					<table class="table table-bordered">
						<tbody>
							<tr class="warning">
								<th colspan="2">订单编号:${order.oid } </th>
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
									<span class="subtotal">&yen;${oi.subtotal }元</span>
								</td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

				<div style="text-align:right;margin-right:120px;">
					商品金额: <strong style="color:#ff6600;">&yen;${order.total }元</strong>
				</div>

			</div>

			<div>
				<hr/>
				<form  id="orderForm"  class="form-horizontal" style="margin-top:5px;margin-left:150px;" action="${ctx }/order?md=toPay" method="post">
					<input type="hidden" name="oid" value="${order.oid }">
					<div class="form-group">
						<label for="username" class="col-sm-1 control-label">地址</label>
						<div class="col-sm-5">
							<input type="text" class="form-control" id="username" name="address" placeholder="请输入收货地址">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword3" class="col-sm-1 control-label">收货人</label>
						<div class="col-sm-5">
							<input type="text" class="form-control" id="inputPassword3" name="name" placeholder="请输收货人">
						</div>
					</div>
					<div class="form-group">
						<label for="confirmpwd" class="col-sm-1 control-label">电话</label>
						<div class="col-sm-5">
							<input type="text" class="form-control" id="confirmpwd"  name="telephone" placeholder="请输入联系方式">
						</div>
					</div>
					<hr/>

				<div style="margin-top:5px;margin-left:150px;">
					<strong>选择银行：</strong>
					<p>
						<br/>
						<input type="radio" name="pd_FrpId" value="ICBC-NET-B2C" checked="checked" />工商银行
						<img src="${pageContext.request.contextPath}/resources/bank_img/icbc.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="pd_FrpId" value="BOC-NET-B2C" />中国银行
						<img src="${pageContext.request.contextPath}/resources/bank_img/bc.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="pd_FrpId" value="ABC-NET-B2C" />农业银行
						<img src="${pageContext.request.contextPath}/resources/bank_img/abc.bmp" align="middle" />
						<br/>
						<br/>
						<input type="radio" name="pd_FrpId" value="BOCO-NET-B2C" />交通银行
						<img src="${pageContext.request.contextPath}/resources/bank_img/bcc.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="pd_FrpId" value="PINGANBANK-NET" />平安银行
						<img src="${pageContext.request.contextPath}/resources/bank_img/pingan.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="pd_FrpId" value="CCB-NET-B2C" />建设银行
						<img src="${pageContext.request.contextPath}/resources/bank_img/ccb.bmp" align="middle" />
						<br/>
						<br/>
						<input type="radio" name="pd_FrpId" value="CEB-NET-B2C" />光大银行
						<img src="${pageContext.request.contextPath}/resources/bank_img/guangda.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="pd_FrpId" value="CMBCHINA-NET-B2C" />招商银行
						<img src="${pageContext.request.contextPath}/resources/bank_img/cmb.bmp" align="middle" />

					</p>
					<hr/>
					<p style="text-align:right;margin-right:100px;">
						<a href="javascript:document.getElementById('orderForm').submit();">
							<img src="${pageContext.request.contextPath}/resources/images/finalbutton.gif" width="204" height="51" border="0" />
						</a>
					</p>
					<hr/>

				</div>
				</form>

				
			</div>

		</div>

<%@ include file="../foot.jsp" %>