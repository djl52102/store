package com.itheima.store.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itheima.store.dao.OrderDao;
import com.itheima.store.domain.Order;
import com.itheima.store.domain.OrderItem;
import com.itheima.store.domain.view.OrderItemView;
import com.itheima.store.utils.ConnectionManager;
import com.itheima.store.utils.JDBCUtils;

public class OrderDaoImpl implements OrderDao {

	@Override
	public void saveOrder(Order order) {
		//����QueryRunner����
		QueryRunner qr = new QueryRunner();
		//sql���
		String sql="insert into orders values (?,?,?,?,?,?,?,?)";
		
		try {
			qr.update(ConnectionManager.getConnection(), sql, order.getOid(),order.getOrdertime(),order.getTotal(),order.getState()
					,order.getAddress(),order.getName(),order.getTelephone(),order.getUid());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
	}

	@Override
	public void saveOrderItem(OrderItem item) {
		//����QueryRunner����
		QueryRunner qr = new QueryRunner();
		// sql���
		String sql = "insert into orderitem values (?,?,?,?,?)";

		try {
			qr.update(ConnectionManager.getConnection(), sql, item.getItemid(),item.getCount(),item.getSubtotal()
					,item.getPid(),item.getOid());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public int findTotalByUid(String uid) {
		// ����QueryRunner����
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		// sql���
		String sql = "select count(*) from orders where uid=?";

		try {
			return ((Long)qr.query(sql, new ScalarHandler(),uid)).intValue();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public List<Order> findOrderByPage(String uid, int i, int pageSize) {
		// ����QueryRunner����
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		// sql���
		String sql = "select * from orders where uid=? limit ?,?";

		try {
			return qr.query(sql, new BeanListHandler<>(Order.class),uid,i,pageSize);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

		
	@Override
	//��ѯ�����Ķ��������Ʒ��Ϣ
	public List<OrderItemView> findOrderItemProduct(String oid) {
		// ����QueryRunner����
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		// sql���
		String sql = "SELECT * FROM orderitem,product WHERE oid=? AND orderitem.pid=product.pid";

		try {
			return qr.query(sql, new BeanListHandler<>(OrderItemView.class),oid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	//����oid��ѯ���ⶩ��
	public Order findOrder(String oid) {
		// ����QueryRunner����
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		// sql���
		String sql = "SELECT * FROM orders where oid=?";

		try {
			return qr.query(sql, new BeanHandler<>(Order.class),oid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	//���¶�����Ϣ
	public void updateOrder(Order order) {
		// ����QueryRunner����
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		// sql���
		String sql = "update orders set state=?,address=?,name=?,telephone=? where oid=?";

		try {
			qr.update(sql,order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getOid());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}	
	}

}
