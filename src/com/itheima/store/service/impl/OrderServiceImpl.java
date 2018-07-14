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
		//�洢����
		dao.saveOrder(order);
		//�洢������
		for (OrderItem item : order.getItems()) {
			dao.saveOrderItem(item);
		}
	}


	@Override
	public PageBean findMyOrdersByPage(String uid, int pageNumber, int pageSize) {
		//����pageBean����
		PageBean pageBean=new PageBean();
		
		pageBean.setPageNumber(pageNumber);
		
		pageBean.setPageSize(pageSize);
		
		//��¼����
		int total=dao.findTotalByUid(uid);
		
		pageBean.setTotal(total);
		
		//���ⶩ��
		List<Order> list=dao.findOrderByPage(uid,(pageNumber-1)*pageSize,pageSize);
		
		//���������б�,��Ҫ������Ӷ��������Ʒ
		for (Order order : list) {
			//���ݶ����Ų�ѯ���������Ʒ
			List<OrderItemView> itemViews= dao.findOrderItemProduct(order.getOid());
			//��ӵ�������
			order.setItemViews(itemViews);
		}
		
		//�������б���ӵ�pageBean��
		pageBean.setList(list);
		
		return pageBean;
	}


	@Override
	public Order findOrderInfo(String oid) {
		//����dao��ѯ��������
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
