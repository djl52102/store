package com.itheima.store.utils;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

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
		//1.��ȡ�ӿڵ�����
		String simpleName = clazz.getSimpleName();
		//2.����api selectSingleNode(���ʽ)
		Element beanEle=(Element) doc.selectSingleNode("//bean[@name='"+simpleName+"']");
		//3.��ȡԪ�ص�class����
		String className = beanEle.attributeValue("class");
		//4.��ȡʵ�����ȫ�޶���
		try {
			Class<T> forName = (Class<T>) Class.forName(className);
			return forName.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} 
	}
}
