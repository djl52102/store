package com.itheima.store.dao;

import java.util.List;

import com.itheima.store.domain.Product;

public interface ProductDao {

	List<Product> news();

	List<Product> hots(int i);

	int findTotal(String cid);

	List<Product> findPageByCid(String cid,int i, int pageSize);

	Product findProductByPid(String pid);

	int findAllTotal();

	List<Product> findPage(int i, int pageSize);

	void saveProduct(Product product);

}
