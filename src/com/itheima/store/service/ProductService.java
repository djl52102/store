package com.itheima.store.service;

import java.util.List;

import com.itheima.store.domain.PageBean;
import com.itheima.store.domain.Product;

public interface ProductService {

	List<Product> news();

	List<Product> hots(int productIsHot);
	
	int findTotal(String cid);

	PageBean findPageByCid(String cid, int pageNumber);

	Product findProductByPid(String pid);

	PageBean findPage(int pageNumber, int pageSize);

	void saveProduct(Product product);

}
