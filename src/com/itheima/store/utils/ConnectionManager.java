package com.itheima.store.utils;

import java.sql.Connection;
import java.sql.SQLException;


import org.apache.commons.dbutils.DbUtils;

public class ConnectionManager {
	// ����һ������,��������Ǵ�Connection����
	// private static HashMap<Thread, Connection> map= new HashMap<>();
	private static ThreadLocal<Connection> tl = new ThreadLocal<>();

	public static Connection getConnection() throws SQLException {
		// ��һ�����ø� ����,�����������ǿյ�
		// Connection conn = map.get(Thread.currentThread());
		Connection conn = tl.get();

		if (conn == null) {
			// ���������û������,���ȡһ�����Ӵ���Map
			conn = JDBCUtils.getConnection();
			// map.put(Thread.currentThread(), conn);
			tl.set(conn);
		}
		return conn;
	}

	// ��������
	public static void begin() throws SQLException {
		ConnectionManager.getConnection().setAutoCommit(false);
	}

	// �ύ����
	public static void commit() throws SQLException {
		DbUtils.commitAndClose(ConnectionManager.getConnection());
	}

	// �ع�����
	public static void rollback() throws SQLException {
		DbUtils.rollbackAndClose(ConnectionManager.getConnection());
	}
}
