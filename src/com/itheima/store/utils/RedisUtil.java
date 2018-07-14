package com.itheima.store.utils;

import java.util.Properties;
import java.util.ResourceBundle;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
	private static JedisPool jedisPool=null;
	//配置不应该写死,从配置文件中读取
	static{
		JedisPoolConfig config = new JedisPoolConfig();
		//读取properties
		Properties properties = new Properties();
		//将文件加载入properties集合
		try {
			//处理properties文件的工具类
			ResourceBundle bundle = ResourceBundle.getBundle("redis");
			
			String host = bundle.getString("host");
			
			int port = Integer.parseInt(bundle.getString("port"));
			
			int maxTotal = Integer.parseInt(bundle.getString("maxTotal"));
			
			int maxIdle=Integer.parseInt(bundle.getString("maxIdle"));
			
			int minIdle=Integer.parseInt(bundle.getString("minIdle"));
			
			
			// 最大连接数
			config.setMaxTotal(maxTotal);
			// 最大空闲连接数
			config.setMaxIdle(maxIdle);
			// 最小空闲连接数
			config.setMinIdle(minIdle);
			
			//获取连接池
			jedisPool = new JedisPool(host, port);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	//定义获取连接方法
	public static Jedis getConnection(){
		return jedisPool.getResource();
	}
}
