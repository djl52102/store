<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>


		<div class="row" style="width:1210px;margin:0 auto;">
			<div class="col-md-12">
				<ol class="breadcrumb">
					<li><a href="${ctx }/product?md=findPageByCid&cid=${cid }&pageNumber=1">首页</a></li>
				</ol>
			</div>

			<c:forEach items="${pageBean.list}" var="p">
			<div class="col-md-2" style="height:250px;">
				<a href="${ctx }/product?md=info&pid=${p.pid}">
					<img src="${pageContext.request.contextPath}/${p.pimage}" width="170" height="170" style="display: inline-block;">
				</a>
				<p><a href="${ctx }/product?md=info&pid=${p.pid}" style='color:green'>${p.pname }</a></p>
				<p><font color="#FF0000">商城价：&yen;${p.shop_price }</font></p>
			</div>
			</c:forEach>
			


		</div>

		<!--分页 -->
		<div style="width:380px;margin:0 auto;margin-top:50px;">
			<ul class="pagination" style="text-align:center; margin-top:10px;">
				<!-- 前一页 -->
				<c:if test="${1==pageBean.pageNumber }">
					<li class="disabled"><a href="javascript:;" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
				</c:if>
				<c:if test="${1!=pageBean.pageNumber }">
					<li><a href="${ctx }/product?md=findPageByCid&cid=${cid }&pageNumber=${pageBean.pageNumber-1}" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
				</c:if>
				
				
				<c:forEach begin="1" end="${pageBean.totalPage }" step="1" var="i">
					<c:if test="${i==pageBean.pageNumber}">
						<li class="active"><a href="${ctx }/product?md=findPageByCid&cid=${cid }&pageNumber=${i}">${i}</a></li>
					</c:if>
					<c:if test="${i!=pageBean.pageNumber }">
						<li ><a href="${ctx }/product?md=findPageByCid&cid=${cid }&pageNumber=${i}">${i}</a></li>
					</c:if>
				</c:forEach>
				
				<c:if test="${pageBean.pageNumber==pageBean.totalPage }">
				<li class="disabled">
					<a href="javascript:;" aria-label="Next">
						<span aria-hidden="true">&raquo;</span>
					</a>
				</li>
				</c:if>
				
				<c:if test="${pageBean.pageNumber!=pageBean.totalPage }">
				<li>
					<a href="${ctx }/product?md=findPageByCid&cid=${cid }&pageNumber=${pageBean.pageNumber+1}" aria-label="Next">
						<span aria-hidden="true">&raquo;</span>
					</a>
				</li>
				</c:if>
				
			</ul>
		</div>
		<!-- 分页结束=======================        -->

		<!--
       		商品浏览记录:
        -->
		<div style="width:1210px;margin:0 auto; padding: 0 9px;border: 1px solid #ddd;border-top: 2px solid #999;height: 246px;">

			<h4 style="width: 50%;float: left;font: 14px/30px " 微软雅黑 ";">浏览记录</h4>
			<div style="width: 50%;float: right;text-align: right;"><a href="">more</a></div>
			<div style="clear: both;"></div>

			<div style="overflow: hidden;">

				<ul style="list-style: none;">
					<c:forEach items="${list }" var="p">
						<li style="width: 150px;height: 216;float: left;margin: 0 8px 0 0;padding: 0 18px 15px;text-align: center;"><img src="${pageContext.request.contextPath}/${p.pimage}" width="130px" height="130px" /></li>
					</c:forEach>
				</ul>

			</div>
		</div>
<%@ include file="../foot.jsp" %>