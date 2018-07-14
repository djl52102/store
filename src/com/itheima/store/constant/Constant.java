package com.itheima.store.constant;

public interface Constant {
	//用户已激活
	int USER_IS_ACTIVE=1;
	//用户未激活
	int USER_NOT_ACTIVE=0;
	//热门商品
	int PRODUCT_IS_HOT=1;
	//非热门商品
	int PRODUCT_NOT_HOT=0;
	//未付款
	int ORDER_IS_WEIFUKUAN=0;
	//已付款
	int ORDER_IS_YIFUKUAN=1;
	//已发货
	int ORDER_IS_YIFAHUO=2;
	//已完成
    int ORDER_IS_YIWANCHENG=3;
	//上架
    int PRODUCT_ON=0;
    //未上架
    int PRODUCT_OFF=1;
}	
