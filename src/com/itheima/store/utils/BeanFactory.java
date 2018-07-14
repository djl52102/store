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
		//1.��ȡ�ӿڵ�����
		String simpleName = clazz.getSimpleName();
		//2.����api selectSingleNode(���ʽ)
		Element beanEle=(Element) doc.selectSingleNode("//bean[@name='"+simpleName+"']");
		//3.��ȡԪ�ص�class����
		String className = beanEle.attributeValue("class");
		try {
			//4.��ȡʵ�����ȫ�޶���
			Class<T> forName = (Class<T>) Class.forName(className);
			Object newInstance = forName.newInstance();
			//����TransactionUtils�����ж��Ƿ���Ҫ��ǿ,���ض���
			Object proxy=TransactionUtils.proxy(newInstance);
			//�ڴ����ö����ʱ�������ǿ
			return (T) proxy;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} 
	}
}
