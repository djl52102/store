package com.itheima.store.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.itheima.store.dao.CategoryDao;
import com.itheima.store.domain.Category;
import com.itheima.store.utils.JDBCUtils;

public class CategoryDaoImpl implements CategoryDao {

	@Override
	public List<Category> findAll() {
		// 创建QueryRunner
		System.out.println("从数据库获取数据");
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		// sql语句
		String sql = "select * from category where mark=0";
		// 执行sql
		try {
			return qr.query(sql, new BeanListHandler<>(Category.class));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public Category findCategoryByCid(String cid) {
		// 创建QueryRunner
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		// sql语句
		String sql = "select * from category where cid=? and mark=0";
		// 执行sql
		try {
			return qr.query(sql, new BeanHandler<>(Category.class), cid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void save(Category category) {
		// 创建QueryRunner
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		// sql语句
		String sql = "insert into category values (?,?)";
		// 执行sql
		try {
			qr.update(sql, category.getCid(), category.getCname());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(Category category) {
		// 创建QueryRunner
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		// sql语句
		String sql = "update category set cname=? where cid=?";
		// 执行sql
		try {
			qr.update(sql, category.getCname(), category.getCid());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delCategory(String cid) {
		// 创建QueryRunner
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		// sql语句
		String sql = "delete from category where cid=?";
		// 执行sql
		try {
			qr.update(sql, cid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	//虚假删除
	public void delCate(String cid) {
		// 创建QueryRunner
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		// sql语句
		String sql = "update category set mark=1 where cid=?";
		// 执行sql
		try {
			qr.update(sql, cid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
