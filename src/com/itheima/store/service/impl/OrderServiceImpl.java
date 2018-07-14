package com.itheima.store.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.itheima.store.dao.OrderDao;
import com.itheima.store.domain.Order;
import com.itheima.store.domain.OrderItem;
import com.itheima.store.domain.PageBean;
import com.itheima.store.domain.view.OrderItemView;
import com.itheima.store.service.OrderService;
import com.itheima.store.utils.BeanFactory;
import com.itheima.store.utils.transaction.Service;
import com.itheima.store.utils.transaction.Transaction;

@Service
public class OrderServiceImpl implements OrderService {
	private OrderDao dao=BeanFactory.newInstance(OrderDao.class);
	
	
	@Override
	@Transaction
	public void saveOrder(Order order) {
		//存储订单
		dao.saveOrder(order);
		//存储订单项
		for (OrderItem item : order.getItems()) {
			dao.saveOrderItem(item);
		}
	}


	@Override
	public PageBean findMyOrdersByPage(String uid, int pageNumber, int pageSize) {
		//创建pageBean对象
		PageBean pageBean=new PageBean();
		
		pageBean.setPageNumber(pageNumber);
		
		pageBean.setPageSize(pageSize);
		
		//记录条数
		int total=dao.findTotalByUid(uid);
		
		pageBean.setTotal(total);
		
		//纯粹订单
		List<Order> list=dao.findOrderByPage(uid,(pageNumber-1)*pageSize,pageSize);
		
		//遍历订单列表,需要继续添加订单项和商品
		for (Order order : list) {
			//根据订单号查询订单项和商品
			List<OrderItemView> itemViews= dao.findOrderItemProduct(order.getOid());
			//添加到订单中
			order.setItemViews(itemViews);
		}
		
		//将订单列表添加到pageBean中
		pageBean.setList(list);
		
		return pageBean;
	}


	@Override
	public Order findOrderInfo(String oid) {
		//调用dao查询订单详情
		Order order = dao.findOrder(oid);
		
		List<OrderItemView> itemViews = dao.findOrderItemProduct(oid);
		
		order.setItemViews(itemViews);
		
		return order;
	}


	@Override
	public void updateOrder(Order order) {
		dao.updateOrder(order);
	}

}
