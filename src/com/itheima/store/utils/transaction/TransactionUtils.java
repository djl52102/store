package com.itheima.store.utils.transaction;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


import com.itheima.store.utils.ConnectionManager;

public class TransactionUtils {

	public static <T>T proxy(T t) {
		//判断类上是否有service注解,有进行增强,没有返回
		if(checkClassAnnotationNecessery(t.getClass())){
			//进行增强
			return doProxy(t);
		}else{
			return t;
		}
	}

	//增强代理方法
	private static <T>T doProxy(final T t) {
		
		Object proxyInstance = Proxy.newProxyInstance(t.getClass().getClassLoader(), 
				t.getClass().getInterfaces(),
				new InvocationHandler() {
					
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						//System.out.println(method.toString());
						//调用方法判断是否需要增强
						Method implMethod=getImplMethod(method,t);
						if(checkMethodNecssery(implMethod)){
							//开启事务
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

						//获取方法
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
	
	//判断方法上是否有注解
	public static boolean checkMethodNecssery(Method method) {
		
		if(method.isAnnotationPresent(Transaction.class)){
			return true;
		}
		return false;
	}
	
	
	//判断类上有无service注解方法
	private static boolean checkClassAnnotationNecessery(Class<? extends Object> class1) {
		
		//判断类上有没有Service注解
		if(class1.isAnnotationPresent(Service.class)){
			return true;
		}
		return false;
	}

}
