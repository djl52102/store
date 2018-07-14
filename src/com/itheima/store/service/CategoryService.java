package com.itheima.store.service;

import java.util.List;

import com.itheima.store.domain.Category;
import com.itheima.store.exception.DelForeignKeyException;

public interface CategoryService {

	List<Category> findAll();

	Category findCategoryByCid(String cid);

	void save(Category category);

	void updateCategory(Category category);

	void delCategory(String cid) throws DelForeignKeyException;

	
}
