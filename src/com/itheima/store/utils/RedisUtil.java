package com.itheima.store.utils;

import java.util.Properties;
import java.util.ResourceBundle;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
	private static JedisPool jedisPool=null;
	//���ò�Ӧ��д��,�������ļ��ж�ȡ
	static{
		JedisPoolConfig config = new JedisPoolConfig();
		//��ȡproperties
		Properties properties = new Properties();
		//���ļ�������properties����
		try {
			//����properties�ļ��Ĺ�����
			ResourceBundle bundle = ResourceBundle.getBundle("redis");
			
			String host = bundle.getString("host");
			
			int port = Integer.parseInt(bundle.getString("port"));
			
			int maxTotal = Integer.parseInt(bundle.getString("maxTotal"));
			
			int maxIdle=Integer.parseInt(bundle.getString("maxIdle"));
			
			int minIdle=Integer.parseInt(bundle.getString("minIdle"));
			
			
			// ���������
			config.setMaxTotal(maxTotal);
			// ������������
			config.setMaxIdle(maxIdle);
			// ��С����������
			config.setMinIdle(minIdle);
			
			//��ȡ���ӳ�
			jedisPool = new JedisPool(host, port);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	//�����ȡ���ӷ���
	public static Jedis getConnection(){
		return jedisPool.getResource();
	}
}
