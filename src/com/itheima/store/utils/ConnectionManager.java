package com.itheima.store.utils;

import java.sql.Connection;
import java.sql.SQLException;


import org.apache.commons.dbutils.DbUtils;

public class ConnectionManager {
	// 定义一个集合,这个集合是存Connection对象
	// private static HashMap<Thread, Connection> map= new HashMap<>();
	private static ThreadLocal<Connection> tl = new ThreadLocal<>();

	public static Connection getConnection() throws SQLException {
		// 第一个调用该 方法,集合中内容是空的
		// Connection conn = map.get(Thread.currentThread());
		Connection conn = tl.get();

		if (conn == null) {
			// 如果集合中没有连接,则获取一个连接存入Map
			conn = JDBCUtils.getConnection();
			// map.put(Thread.currentThread(), conn);
			tl.set(conn);
		}
		return conn;
	}

	// 开启事务
	public static void begin() throws SQLException {
		ConnectionManager.getConnection().setAutoCommit(false);
	}

	// 提交事务
	public static void commit() throws SQLException {
		DbUtils.commitAndClose(ConnectionManager.getConnection());
	}

	// 回滚事务
	public static void rollback() throws SQLException {
		DbUtils.rollbackAndClose(ConnectionManager.getConnection());
	}
}
