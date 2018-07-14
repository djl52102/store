package com.itheima.store.web.servlet;

import com.itheima.store.constant.Constant;
import com.itheima.store.domain.Category;
import com.itheima.store.domain.PageBean;
import com.itheima.store.domain.Product;
import com.itheima.store.service.CategoryService;
import com.itheima.store.service.ProductService;
import com.itheima.store.utils.BeanFactory;
import com.itheima.store.utils.CookUtils;
import com.itheima.store.web.base.BaseServlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ProductServlet
 */
public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
    private ProductService productService=BeanFactory.newInstance(ProductService.class);
    private CategoryService categoryService=BeanFactory.newInstance(CategoryService.class);
	private List list;
	 public void test(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
	    	
	 }
	 
	 //展示首页热门商品和最新商品
	 public void index(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		 //热门商品
		 
		 List<Product> list=productService.hots(Constant.PRODUCT_IS_HOT);
		 //最新商品
		 List<Product> list2=productService.news();
		 
		 //存到request域中
		 request.setAttribute("hots", list);
		 
		 request.setAttribute("news", list2);
		 
		 //转发到index
		 request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
		 
	 }
	 
	 
	 //分页查询
	 public void findPageByCid(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
	    	//获取提交的参数
		 	String cid = request.getParameter("cid");
		 	
		 	int pageNumber =Integer.parseInt(request.getParameter("pageNumber"));
		 	
		 	PageBean pageBean=productService.findPageByCid(cid,pageNumber);
		 	
		 	request.setAttribute("cid", cid);
		 	
		 	//获取cookie
		 	String cookie = CookUtils.getCookieByName("history", request.getCookies()).getValue();
		 	
		 	//拆分cookie
		 	String[] arr=cookie.split("-");
		 	
		    ArrayList<Product> list = new ArrayList<>();
		 	
		 	for (String pid : arr) {
				//根据pid查找商品
		 		Product p = productService.findProductByPid(pid);
		 		list.add(p);
			}
		 	
		 	//将数据转发到product_list页面
		 	request.setAttribute("pageBean", pageBean);
		 	
		 	request.setAttribute("list", list);
		 	
		 	List list2 = pageBean.getList();
		 	
		 	request.getRequestDispatcher("/WEB-INF/jsp/product/product_list.jsp").forward(request, response);
		 	
	 }
	 
	 //根据pid查找商品
	 public void info(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		 //获取pid
		 String pid = request.getParameter("pid");
		
		 //根据pid查询商品信息
		Product product=productService.findProductByPid(pid);
		
		//查询分类信息
		Category category=categoryService.findCategoryByCid(product.getCid());
		
		//设置cookie
		Cookie cookie = CookUtils.getCookieByName("history", request.getCookies());
		
		if(cookie == null){
			//第一次浏览商品
			Cookie c = new Cookie("history",pid);
			c.setMaxAge(60*60*24*7);
			response.addCookie(c);
		}else{
			//不是第一次浏览商品
			String value=cookie.getValue();
			
			String[] ids=value.split("-");
			
			//将数组转为list集合,并放入linkedlist中
			LinkedList<String> list = new LinkedList<String>(Arrays.asList(ids));
			
			if(list.contains(pid)){
				//已经浏览过
				list.remove(pid);
				
				list.addFirst(pid);
			}else{
				//没有浏览过
				if(list.size()>=6){
					list.removeLast();
					list.addFirst(pid);
				}else{
					list.addFirst(pid);
				}
			}
			
			//字符串拼接
			StringBuffer sb = new StringBuffer();
			
			for (String id : list) {
				sb.append(id+"-");
			}
			
			String history=sb.substring(0,sb.length()-1);
			
			Cookie c=new Cookie("history",history);
			
			c.setMaxAge(60*60*24*7);
			
			response.addCookie(c);
		}
			
	
		 //将商品信息存入request域
		request.setAttribute("category", category);
		
		request.setAttribute("p", product);
		 
		 //转发到商品详情页面
		 request.getRequestDispatcher("/WEB-INF/jsp/product/product_info.jsp").forward(request, response);
	 }
	 

}
