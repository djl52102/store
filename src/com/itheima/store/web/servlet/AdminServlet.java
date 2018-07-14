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
		//ת������̨������ҳ
		request.getRequestDispatcher("/WEB-INF/jsp/admin/home.jsp").forward(request, response);
	}
	@Auth("admin")
	public void categories(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    	
    	//����redis����
    	Jedis connection = RedisUtil.getConnection();
    	
    	try {
			//�鿴redis���Ƿ��з�����Ϣ
    		String list = connection.get("list");
    		
    		if(list != null){
    			response.getWriter().print(list);
    		}else{
    			//����service��ѯ���ݿ�,�������з�����Ϣ
    			List<Category> cateList=categoryService.findAll();
    			
    			//�����list����תΪjson�ַ���,���ظ�jspҳ��
    			String string = JSONArray.fromObject(cateList).toString();
    			
    			//����õ�list��Ϣ����redis
    			connection.set("list", string);
    			
    			response.getWriter().print(string);
    		}
    		
		} finally {
			connection.close();
		}
	}
	
	
	 //��ӷ���
	 @Auth("admin")
	  public void addCategory(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
	      response.setContentType("text/html;charset=UTF-8");
		  try {
	    		 //��ȡcname
			    String cname = request.getParameter("cname");
			    
			    Category category = new Category();
			    
			    category.setCid(UUIDUtil.getId());
			    
			    category.setCname(cname);
			    
			    //����categoryService��ӷ���
			    categoryService.save(category);
			    
			    //����result������Ϣ
			    Result result = new Result(Result.STATE_SUCCESS,"������ӳɹ�");
			    
			    response.getWriter().println(JSONObject.fromObject(result));
			    
			} catch (Exception e) {
				e.printStackTrace();
				 //����result������Ϣ
			    Result result = new Result(Result.STATE_FAIL,"�������ʧ��");
			    
			    response.getWriter().println(JSONObject.fromObject(result));
			}
		  
	 }
	
	 @Auth("admin")
	public void findCategoryByCid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 //��ȡcid
		 String cid = request.getParameter("cid");
		 
		 //����cid���ҷ���
		 Category category = categoryService.findCategoryByCid(cid);
		 
		 //���ظ������
		 response.setContentType("text/html;charset=UTF-8");
		 
		 response.getWriter().print(JSONObject.fromObject(category));
	}
	 
	 @Auth("admin")
	public void updateCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 response.setContentType("text/html;charset=UTF-8");
		 try {
			// ��ȡcid
				String cid = request.getParameter("cid");

				String cname = request.getParameter("cname");
				
				Category category = new Category();
				
				category.setCid(cid);
				
				category.setCname(cname);
				
				//����categoryService���·���
				categoryService.updateCategory(category);
				
				//����result������Ϣ
			    Result result = new Result(Result.STATE_SUCCESS,"������³ɹ�");
			    
			    response.getWriter().println(JSONObject.fromObject(result));
		} catch (Exception e) {
			e.printStackTrace();
			//����result������Ϣ
		    Result result = new Result(Result.STATE_FAIL,"�������ʧ��");
		    
		    response.getWriter().println(JSONObject.fromObject(result));
		}
		
		
		
	}
	
	
	 @Auth("admin")
	public void delCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 response.setContentType("text/html;charset=UTF-8");
		 try {
			 //��ȡcid
			 String cid = request.getParameter("cid");
			 
			 //����service����ɾ��
			 categoryService.delCategory(cid);
			 
			//����result������Ϣ
			Result result = new Result(Result.STATE_SUCCESS, "����ɾ���ɹ�");

			response.getWriter().println(JSONObject.fromObject(result));
			 
		}catch(DelForeignKeyException e2){
			Result result = new Result(Result.STATE_FAIL, "�÷���������Ʒ�޷�ɾ��");

			response.getWriter().println(JSONObject.fromObject(result));
		}catch (Exception e) {
			e.printStackTrace();
			//����result������Ϣ
			Result result = new Result(Result.STATE_FAIL, "����ɾ��ʧ��");

			response.getWriter().println(JSONObject.fromObject(result));
		} 
	}
	
	
	@Auth("admin")
	public void categoryUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ת����category.jspҳ��
		request.getRequestDispatcher("/WEB-INF/jsp/admin/category.jsp").forward(request, response);
	}
	
	@Auth("admin")
	public void productUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.getRequestDispatcher("/WEB-INF/jsp/admin/product.jsp").forward(request, response);
	}
	
	//��ȡ��Ʒ��ҳ��Ϣ
	public void products(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    int  pageNumber = Integer.parseInt(request.getParameter("page")) ;
	    int  pageSize = Integer.parseInt(request.getParameter("rows"));
	    //����service���з�ҳ��ѯ
	    PageBean pageBean=productService.findPage(pageNumber, pageSize);
	    
	   //����map����
	   HashMap<Object, Object> map = new HashMap<>();
	  		
		// ��Ҫ����total,list
		map.put("total", pageBean.getTotal());
		map.put("rows", pageBean.getList());
		
		JsonConfig jsonConfig = new JsonConfig();
		
		jsonConfig.setExcludes(new String[]{"pdate"});

		// תΪjson����
		String str = JSONObject.fromObject(map,jsonConfig).toString();
		
		// ��Ӧ�������
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(str);
	}
}
