package com.itheima.store.utils;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.itheima.store.utils.transaction.TransactionUtils;

public class BeanFactory {
	private static Document doc=null;
	static{
		try {
			doc=new SAXReader().read(BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	@SuppressWarnings("unchecked")
	public static <T>T newInstance(Class<T> clazz){
		//1.获取接口的名字
		String simpleName = clazz.getSimpleName();
		//2.调用api selectSingleNode(表达式)
		Element beanEle=(Element) doc.selectSingleNode("//bean[@name='"+simpleName+"']");
		//3.获取元素的class属性
		String className = beanEle.attributeValue("class");
		try {
			//4.获取实现类的全限定名
			Class<T> forName = (Class<T>) Class.forName(className);
			Object newInstance = forName.newInstance();
			//调用TransactionUtils方法判断是否需要增强,返回对象
			Object proxy=TransactionUtils.proxy(newInstance);
			//在创建好对象的时候进行增强
			return (T) proxy;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} 
	}
}
