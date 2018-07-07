package com.itheima.store.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCUtils {
	private static ComboPooledDataSource ds=new ComboPooledDataSource();
	
	//获得连接池
	public static DataSource getDataSource(){
		return ds;
	}
	
	//获得连接
	public static Connection getConnection() throws SQLException{
		return ds.getConnection();
	}
}
