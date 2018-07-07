package com.itheima.store.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.itheima.store.dao.UserDao;
import com.itheima.store.domain.User;
import com.itheima.store.utils.JDBCUtils;

public class UserDaoImpl implements UserDao {

	@Override
	public void register(User user) {
		//����QueryRunner����
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		
		//sql���
		String sql="insert into user values (?,?,?,?,?,?,?,?,?,?)";
		
		Object[] obj={user.getUid(),user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getRemark(),
				user.getBirthday(),user.getgender(),user.getState(),user.getCode()};
		
		//ִ��sql
		try {
			qr.update(sql, obj);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	@Override
	public User findByCode(String code) {
		//��ѯcode�Ƿ����
		//����QueryRunner����
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		
		//sql���
		String sql="select * from user where code=?";
		
		User user=null;
		try {
			user=qr.query(sql, new BeanHandler<>(User.class), code);
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(User user) {
		//����QueryRunner����
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		
		String sql="update user set username=?,password=?,email=?,state=? where uid=?";
		
		Object[] obj={user.getUsername(),user.getPassword(),user.getEmail(),
				user.getState(),user.getUid()};
		
		try {
			qr.update(sql, obj);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public User login(String username, String password) {
		
		//����QueryRunner����
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		
		String sql="select * from user where username=? and password=?";
		
		try {
			User user = qr.query(sql, new BeanHandler<>(User.class),username,password);
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
			
	}

	

	

}
