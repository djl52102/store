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
		//�������
		clearCache();
	}

	//���redis����
	public void clearCache(){
		//ʹ��redis
		Jedis connection = null;
		try {
			connection = RedisUtil.getConnection();
			connection.del("list");
		} finally {
			// һ���ǵû�
			connection.close();
		}
	}
	@Override
	public void updateCategory(Category category) {
		dao.update(category);
		//�������
		clearCache();
	}
	@Override
	//��ʵɾ��
	public void delCategory(String cid) throws DelForeignKeyException {
		//��ȥ����Ʒ������û�и÷���
			int total = productDao.findTotal(cid);
			//��,����ɾ��
			if(total>0){
				throw new DelForeignKeyException();
			}else{
				//��ʵɾ��
				//dao.delCategory(cid);
				//���ɾ��
				dao.delCate(cid);
				//�������
				clearCache();
			}
			
	}
}
