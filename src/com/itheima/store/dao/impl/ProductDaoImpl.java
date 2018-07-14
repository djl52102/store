package com.itheima.store.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itheima.store.constant.Constant;
import com.itheima.store.dao.ProductDao;
import com.itheima.store.domain.Product;
import com.itheima.store.utils.JDBCUtils;
import com.sun.org.apache.xml.internal.utils.ListingErrorHandler;

public class ProductDaoImpl implements ProductDao {

	@Override
	public List<Product> hots(int i) {
		//创建QueryRunner对象
		QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
		//sql语句
		String sql="select * from product where is_hot=? limit 9";
		
		try {
			return qr.query(sql, new BeanListHandler<>(Product.class),Constant.PRODUCT_IS_HOT);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Product> news() {
		//创建QueryRunner对象
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		// sql语句
		String sql = "select * from product order by pdate desc limit 9";

		try {
			return qr.query(sql, new BeanListHandler<>(Product.class));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public int findTotal(String cid) {
		//创建QueryRunner对象
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		// sql语句
		String sql = "select count(*) from product where cid=?";

		try {
			Long query = (Long)qr.query(sql,new ScalarHandler(),cid);
			return query.intValue();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Product> findPageByCid(String cid,int i, int pageSize) {
		//创建QueryRunner对象
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		// sql语句
		String sql = "select * from product where cid=? limit ?,?";
		
		try {
			//执行sql
			return qr.query(sql, new BeanListHandler<>(Product.class),cid,i,pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public Product findProductByPid(String pid) {
		//创建QueryRunner对象
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		// sql语句
		String sql = "select * from product where pid=?";

		try {
			// 执行sql
			return qr.query(sql, new BeanHandler<>(Product.class),pid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public int findAllTotal() {
		//创建QueryRunner对象
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		// sql语句
		String sql = "select count(*) from product";

		try {
			// 执行sql
			return ((Long)qr.query(sql, new ScalarHandler())).intValue();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Product> findPage(int i, int pageSize) {
		//创建QueryRunner对象
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		// sql语句
		String sql = "select * from product limit ?,?";

		try {
			// 执行sql
			return qr.query(sql, new BeanListHandler<>(Product.class),i,pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void saveProduct(Product p) {
		//创建QueryRunner对象
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		// sql语句
		String sql = "insert into product values(?,?,?,?,?,?,?,?,?,?)";

		try {
			// 执行sql
			qr.update(sql,p.getPid(),p.getPname(),p.getMarket_price(),p.getShop_price(),p.getPimage(),p.getPdate(),
					p.getIs_hot(),p.getPdesc(),p.getPflag(),p.getCid());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}	
	}


}
