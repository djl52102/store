package com.itheima.store.dao;

import java.util.List;

import com.itheima.store.domain.Order;
import com.itheima.store.domain.OrderItem;
import com.itheima.store.domain.view.OrderItemView;

public interface OrderDao {

	void saveOrder(Order order);

	void saveOrderItem(OrderItem item);

	int findTotalByUid(String uid);

	List<Order> findOrderByPage(String uid, int i, int pageSize);

	List<OrderItemView> findOrderItemProduct(String oid);

	Order findOrder(String oid);

	void updateOrder(Order order);

}
