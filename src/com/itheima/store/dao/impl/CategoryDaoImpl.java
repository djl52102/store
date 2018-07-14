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
		// ����QueryRunner
		System.out.println("�����ݿ��ȡ����");
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		// sql���
		String sql = "select * from category where mark=0";
		// ִ��sql
		try {
			return qr.query(sql, new BeanListHandler<>(Category.class));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public Category findCategoryByCid(String cid) {
		// ����QueryRunner
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		// sql���
		String sql = "select * from category where cid=? and mark=0";
		// ִ��sql
		try {
			return qr.query(sql, new BeanHandler<>(Category.class), cid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void save(Category category) {
		// ����QueryRunner
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		// sql���
		String sql = "insert into category values (?,?)";
		// ִ��sql
		try {
			qr.update(sql, category.getCid(), category.getCname());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(Category category) {
		// ����QueryRunner
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		// sql���
		String sql = "update category set cname=? where cid=?";
		// ִ��sql
		try {
			qr.update(sql, category.getCname(), category.getCid());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delCategory(String cid) {
		// ����QueryRunner
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		// sql���
		String sql = "delete from category where cid=?";
		// ִ��sql
		try {
			qr.update(sql, cid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	//���ɾ��
	public void delCate(String cid) {
		// ����QueryRunner
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		// sql���
		String sql = "update category set mark=1 where cid=?";
		// ִ��sql
		try {
			qr.update(sql, cid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
