package com.itheima.store.service.impl;

import java.util.List;

import com.itheima.store.constant.Constant;
import com.itheima.store.dao.ProductDao;
import com.itheima.store.domain.PageBean;
import com.itheima.store.domain.Product;
import com.itheima.store.service.ProductService;
import com.itheima.store.utils.BeanFactory;

public class ProductServiceImpl implements ProductService {
	private ProductDao dao=BeanFactory.newInstance(ProductDao.class);

	@Override
	public List<Product> news() {
		// TODO Auto-generated method stub
		return dao.news();
	}

	@Override
	public List<Product> hots(int productIsHot) {
		// TODO Auto-generated method stub
		return dao.hots(Constant.PRODUCT_IS_HOT);
	}

	@Override
	public PageBean findPageByCid(String cid, int pageNumber) {
		
		//创建PageBean对象
		PageBean<Product> pageBean = new PageBean<>();
		
		pageBean.setPageNumber(pageNumber);
		
		int pageSize=12;
		
		pageBean.setPageSize(pageSize);
		
		int total=dao.findTotal(cid);
		
		pageBean.setTotal(total);
		
		List<Product> list=dao.findPageByCid(cid,(pageNumber-1)*pageSize,pageSize);
		
		pageBean.setList(list);
		
		return pageBean;
	}

	@Override
	public int findTotal(String cid) {
		
		return dao.findTotal(cid);
	}

	@Override
	public Product findProductByPid(String pid) {
		// TODO Auto-generated method stub
		return dao.findProductByPid(pid);
	}

	@Override
	public PageBean findPage(int pageNumber, int pageSize) {
		PageBean pb=new PageBean();
		
		pb.setPageNumber(pageNumber);
		
		pb.setPageSize(pageSize);
		
		//设置总条数
		int total=dao.findAllTotal();
		
		pb.setTotal(total);
		
		List<Product> list=dao.findPage((pageNumber-1)*pageSize,pageSize);
		
		pb.setList(list);
		
		return pb;
	}

	@Override
	public void saveProduct(Product product) {
		dao.saveProduct(product);
	}
	
}
