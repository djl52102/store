package com.itheima.store.service;



import com.itheima.store.domain.Order;
import com.itheima.store.domain.PageBean;


public interface OrderService {

	void saveOrder(Order order);

	PageBean findMyOrdersByPage(String uid, int pageNumber, int pageSize);

	Order findOrderInfo(String oid);

	void updateOrder(Order order);
}
