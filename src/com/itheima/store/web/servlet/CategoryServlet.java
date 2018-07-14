package com.itheima.store.web.servlet;

import com.itheima.store.domain.Category;
import com.itheima.store.service.CategoryService;
import com.itheima.store.utils.BeanFactory;
import com.itheima.store.utils.RedisUtil;
import com.itheima.store.utils.UUIDUtil;
import com.itheima.store.web.base.BaseServlet;

import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CategoryServlet
 */
public class CategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
    private CategoryService categoryService=BeanFactory.newInstance(CategoryService.class); 
	
    public void findAll(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
    	response.setContentType("text/html;charset=UTF-8");
    	
    	//创建redis连接
    	Jedis connection = RedisUtil.getConnection();
    	
    	try {
			//查看redis中是否有分类信息
    		String list = connection.get("list");
    		
    		if(list != null){
    			response.getWriter().print(list);
    		}else{
    			//调用service查询数据库,返回所有分类信息
    			List<Category> cateList=categoryService.findAll();
    			
    			//将获得list集合转为json字符串,返回给jsp页面
    			String string = JSONArray.fromObject(cateList).toString();
    			
    			//将获得的list信息存入redis
    			connection.set("list", string);
    			
    			response.getWriter().print(string);
    		}
    		

		} finally {
			connection.close();
		}
    	
    }
	

     public void test(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
    	
    }
}
