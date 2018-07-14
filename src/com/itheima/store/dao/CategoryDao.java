package com.itheima.store.dao;

import java.util.List;

import com.itheima.store.domain.Category;

public interface CategoryDao {

	List<Category> findAll();

	Category findCategoryByCid(String cid);

	void save(Category category);

	void update(Category category);

	void delCategory(String cid);

	void delCate(String cid);

}
