package com.itheima.store.service.impl;

import java.util.List;

import com.itheima.store.dao.CategoryDao;
import com.itheima.store.dao.ProductDao;
import com.itheima.store.domain.Category;
import com.itheima.store.domain.Product;
import com.itheima.store.exception.DelForeignKeyException;
import com.itheima.store.service.CategoryService;
import com.itheima.store.utils.BeanFactory;
import com.itheima.store.utils.RedisUtil;

import redis.clients.jedis.Jedis;

public class CategoryServiceImpl implements CategoryService {
	private CategoryDao dao=BeanFactory.newInstance(CategoryDao.class);
	private ProductDao productDao=BeanFactory.newInstance(ProductDao.class);
	@Override
	public List<Category> findAll() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}
	@Override
	public Category findCategoryByCid(String cid) {
		// TODO Auto-generated method stub
		return dao.findCategoryByCid(cid);
	}
	@Override
	public void save(Category category) {
		dao.save(category);
		//清除缓存
		clearCache();
	}

	//清楚redis缓存
	public void clearCache(){
		//使用redis
		Jedis connection = null;
		try {
			connection = RedisUtil.getConnection();
			connection.del("list");
		} finally {
			// 一定记得还
			connection.close();
		}
	}
	@Override
	public void updateCategory(Category category) {
		dao.update(category);
		//清除缓存
		clearCache();
	}
	@Override
	//真实删除
	public void delCategory(String cid) throws DelForeignKeyException {
		//先去查商品表下有没有该分类
			int total = productDao.findTotal(cid);
			//有,不能删除
			if(total>0){
				throw new DelForeignKeyException();
			}else{
				//真实删除
				//dao.delCategory(cid);
				//虚假删除
				dao.delCate(cid);
				//清除缓存
				clearCache();
			}
			
	}
}
