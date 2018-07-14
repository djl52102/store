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
	

     public void test(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
    	
    }
}
