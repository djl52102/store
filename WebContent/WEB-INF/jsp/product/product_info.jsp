<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<script type="text/javascript">
$(function(){
	$("#addCart").click(function(){
		var count=$("#count").val();
		window.location.href="${ctx}/cart?md=addCart&pid=${p.pid}&count="+count;
	})
})

</script>
		<div class="container">
			<div class="row">
				<div style="border: 1px solid #e4e4e4;width:930px;margin-bottom:10px;margin:0 auto;padding:10px;margin-bottom:10px;">
					<a href="${ctx }">首页&nbsp;&nbsp;&gt;</a>
					<a href="${ctx }/product?md=findPageByCid&cid=${category.cid }&pageNumber=1">${category.cname }&nbsp;&nbsp;&gt;</a>
				</div>

				<div style="margin:0 auto;width:950px;">
					<div class="col-md-6">
						<img style="opacity: 1;width:400px;height:350px;" title="" class="medium" src="${ctx}/${p.pimage}">
					</div>

					<div class="col-md-6">
						<div><strong>${p.pname }</strong></div>
						<div style="border-bottom: 1px dotted #dddddd;width:350px;margin:10px 0 10px 0;">
							<div>编号：${p.pid }</div>
						</div>

						<div style="margin:10px 0 10px 0;">一口价: <strong style="color:#ef0101;">&yen;${p.shop_price }</strong> 参 考 价： <del>&yen;${p.market_price }</del>
						</div>

						<div style="margin:10px 0 10px 0;">促销: <a target="_blank" title="限时抢购 (2014-07-30 ~ 2015-01-01)" style="background-color: #f07373;">限时抢购</a> </div>

						<div style="padding:10px;border:1px solid #e7dbb1;width:330px;margin:15px 0 10px 0;;background-color: #fffee6;">
							
							<div style="border-bottom: 1px solid #faeac7;margin-top:20px;padding-left: 10px;">购买数量:
								<input id="count" name="count" value="1" maxlength="4" size="10" type="text"> </div>

							<div style="margin:20px 0 10px 0;;text-align: center;">
								<a href="javascript:;">
									<input    id="addCart" style="background: url('${pageContext.request.contextPath}/resources/images/product.gif') no-repeat scroll 0 -600px rgba(0, 0, 0, 0);height:36px;width:127px;" value="加入购物车" type="button">
								</a> &nbsp;收藏商品</div>
						</div>
					</div>
				</div>
				<div class="clear"></div>
				<div style="width:950px;margin:0 auto;">
					<div style="background-color:#d3d3d3;width:930px;padding:10px 10px;margin:10px 0 10px 0;">
						<strong>商品介绍</strong>
					</div>

					<div>
						<img src="${ctx}/${p.pimage}">
					</div>

					<div style="background-color:#d3d3d3;width:930px;padding:10px 10px;margin:10px 0 10px 0;">
						<strong>商品参数</strong>
					</div>
					
					<div style="margin-top:10px;width:900px;">
						${p.pdesc }
					</div>
					
					<div style="background-color:#d3d3d3;width:900px;">
						<table class="table table-bordered">
							<tbody>
								<tr class="active">
									<th><strong>商品评论</strong></th>
								</tr>
								<tr class="warning">
									<th>暂无商品评论信息 <a>[发表商品评论]</a></th>
								</tr>
							</tbody>
						</table>
					</div>

					<div style="background-color:#d3d3d3;width:900px;">
						<table class="table table-bordered">
							<tbody>
								<tr class="active">
									<th><strong>商品咨询</strong></th>
								</tr>
								<tr class="warning">
									<th>暂无商品咨询信息 <a>[发表商品咨询]</a></th>
								</tr>
							</tbody>
						</table>
					</div>
				</div>

			</div>
		</div>

<%@ include file="../foot.jsp" %>