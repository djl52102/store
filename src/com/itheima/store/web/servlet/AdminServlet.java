package com.itheima.store.web.servlet;



import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.itheima.store.constant.Constant;
import com.itheima.store.domain.Category;
import com.itheima.store.domain.PageBean;
import com.itheima.store.domain.Product;
import com.itheima.store.exception.DelForeignKeyException;
import com.itheima.store.service.CategoryService;
import com.itheima.store.service.ProductService;
import com.itheima.store.utils.BeanFactory;
import com.itheima.store.utils.RedisUtil;
import com.itheima.store.utils.UUIDUtil;
import com.itheima.store.web.annotation.Auth;
import com.itheima.store.web.base.BaseServlet;
import com.itheima.store.web.views.Result;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import redis.clients.jedis.Jedis;

/**
 * Servlet implementation class AdminServlet
 */
public class AdminServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private CategoryService categoryService=BeanFactory.newInstance(CategoryService.class);
	private ProductService productService=BeanFactory.newInstance(ProductService.class);
      
	@Auth("admin")
	public void test1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("test1");
	}

	@Auth("user")
	public void test2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("test2");
	}
	
	@Auth("admin")
	public void home(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//转发到后台管理主页
		request.getRequestDispatcher("/WEB-INF/jsp/admin/home.jsp").forward(request, response);
	}
	@Auth("admin")
	public void categories(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
	
	
	 //添加分类
	 @Auth("admin")
	  public void addCategory(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
	      response.setContentType("text/html;charset=UTF-8");
		  try {
	    		 //获取cname
			    String cname = request.getParameter("cname");
			    
			    Category category = new Category();
			    
			    category.setCid(UUIDUtil.getId());
			    
			    category.setCname(cname);
			    
			    //调用categoryService添加分类
			    categoryService.save(category);
			    
			    //设置result返回信息
			    Result result = new Result(Result.STATE_SUCCESS,"分类添加成功");
			    
			    response.getWriter().println(JSONObject.fromObject(result));
			    
			} catch (Exception e) {
				e.printStackTrace();
				 //设置result返回信息
			    Result result = new Result(Result.STATE_FAIL,"分类添加失败");
			    
			    response.getWriter().println(JSONObject.fromObject(result));
			}
		  
	 }
	
	 @Auth("admin")
	public void findCategoryByCid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 //获取cid
		 String cid = request.getParameter("cid");
		 
		 //根据cid查找分类
		 Category category = categoryService.findCategoryByCid(cid);
		 
		 //返回给浏览器
		 response.setContentType("text/html;charset=UTF-8");
		 
		 response.getWriter().print(JSONObject.fromObject(category));
	}
	 
	 @Auth("admin")
	public void updateCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 response.setContentType("text/html;charset=UTF-8");
		 try {
			// 获取cid
				String cid = request.getParameter("cid");

				String cname = request.getParameter("cname");
				
				Category category = new Category();
				
				category.setCid(cid);
				
				category.setCname(cname);
				
				//调用categoryService更新分类
				categoryService.updateCategory(category);
				
				//设置result返回信息
			    Result result = new Result(Result.STATE_SUCCESS,"分类更新成功");
			    
			    response.getWriter().println(JSONObject.fromObject(result));
		} catch (Exception e) {
			e.printStackTrace();
			//设置result返回信息
		    Result result = new Result(Result.STATE_FAIL,"分类更新失败");
		    
		    response.getWriter().println(JSONObject.fromObject(result));
		}
		
		
		
	}
	
	
	 @Auth("admin")
	public void delCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 response.setContentType("text/html;charset=UTF-8");
		 try {
			 //获取cid
			 String cid = request.getParameter("cid");
			 
			 //调用service进行删除
			 categoryService.delCategory(cid);
			 
			//设置result返回信息
			Result result = new Result(Result.STATE_SUCCESS, "分类删除成功");

			response.getWriter().println(JSONObject.fromObject(result));
			 
		}catch(DelForeignKeyException e2){
			Result result = new Result(Result.STATE_FAIL, "该分类下有商品无法删除");

			response.getWriter().println(JSONObject.fromObject(result));
		}catch (Exception e) {
			e.printStackTrace();
			//设置result返回信息
			Result result = new Result(Result.STATE_FAIL, "分类删除失败");

			response.getWriter().println(JSONObject.fromObject(result));
		} 
	}
	
	
	@Auth("admin")
	public void categoryUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//转发到category.jsp页面
		request.getRequestDispatcher("/WEB-INF/jsp/admin/category.jsp").forward(request, response);
	}
	
	@Auth("admin")
	public void productUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.getRequestDispatcher("/WEB-INF/jsp/admin/product.jsp").forward(request, response);
	}
	
	//获取商品分页信息
	public void products(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    int  pageNumber = Integer.parseInt(request.getParameter("page")) ;
	    int  pageSize = Integer.parseInt(request.getParameter("rows"));
	    //调用service进行分页查询
	    PageBean pageBean=productService.findPage(pageNumber, pageSize);
	    
	   //创建map集合
	   HashMap<Object, Object> map = new HashMap<>();
	  		
		// 需要返回total,list
		map.put("total", pageBean.getTotal());
		map.put("rows", pageBean.getList());
		
		JsonConfig jsonConfig = new JsonConfig();
		
		jsonConfig.setExcludes(new String[]{"pdate"});

		// 转为json数据
		String str = JSONObject.fromObject(map,jsonConfig).toString();
		
		// 响应给浏览器
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(str);
	}
}
