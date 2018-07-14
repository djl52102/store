package com.itheima.store.utils.transaction;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


import com.itheima.store.utils.ConnectionManager;

public class TransactionUtils {

	public static <T>T proxy(T t) {
		//�ж������Ƿ���serviceע��,�н�����ǿ,û�з���
		if(checkClassAnnotationNecessery(t.getClass())){
			//������ǿ
			return doProxy(t);
		}else{
			return t;
		}
	}

	//��ǿ������
	private static <T>T doProxy(final T t) {
		
		Object proxyInstance = Proxy.newProxyInstance(t.getClass().getClassLoader(), 
				t.getClass().getInterfaces(),
				new InvocationHandler() {
					
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						//System.out.println(method.toString());
						//���÷����ж��Ƿ���Ҫ��ǿ
						Method implMethod=getImplMethod(method,t);
						if(checkMethodNecssery(implMethod)){
							//��������
							ConnectionManager.begin();
							try {
								Object invoke = method.invoke(t, args);
								ConnectionManager.commit();
								return invoke;
							} catch (Exception e) {
								e.printStackTrace();
								ConnectionManager.rollback();
							}
							
						}else{
							return method.invoke(t, args);
						}
						return null;
					}

					private Method getImplMethod(Method method, T t) {

						//��ȡ����
						try {
							Method method2 = t.getClass().getMethod(method.getName(), method.getParameterTypes());
							return method2;
						}catch(Exception e){
							e.printStackTrace();
							throw new RuntimeException(e);
						}
					}

					
					
				});
		return (T) proxyInstance;
	}
	
	//�жϷ������Ƿ���ע��
	public static boolean checkMethodNecssery(Method method) {
		
		if(method.isAnnotationPresent(Transaction.class)){
			return true;
		}
		return false;
	}
	
	
	//�ж���������serviceע�ⷽ��
	private static boolean checkClassAnnotationNecessery(Class<? extends Object> class1) {
		
		//�ж�������û��Serviceע��
		if(class1.isAnnotationPresent(Service.class)){
			return true;
		}
		return false;
	}

}
