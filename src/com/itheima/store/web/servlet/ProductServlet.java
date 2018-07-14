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
	 
	 //չʾ��ҳ������Ʒ��������Ʒ
	 public void index(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		 //������Ʒ
		 
		 List<Product> list=productService.hots(Constant.PRODUCT_IS_HOT);
		 //������Ʒ
		 List<Product> list2=productService.news();
		 
		 //�浽request����
		 request.setAttribute("hots", list);
		 
		 request.setAttribute("news", list2);
		 
		 //ת����index
		 request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
		 
	 }
	 
	 
	 //��ҳ��ѯ
	 public void findPageByCid(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
	    	//��ȡ�ύ�Ĳ���
		 	String cid = request.getParameter("cid");
		 	
		 	int pageNumber =Integer.parseInt(request.getParameter("pageNumber"));
		 	
		 	PageBean pageBean=productService.findPageByCid(cid,pageNumber);
		 	
		 	request.setAttribute("cid", cid);
		 	
		 	//��ȡcookie
		 	String cookie = CookUtils.getCookieByName("history", request.getCookies()).getValue();
		 	
		 	//���cookie
		 	String[] arr=cookie.split("-");
		 	
		    ArrayList<Product> list = new ArrayList<>();
		 	
		 	for (String pid : arr) {
				//����pid������Ʒ
		 		Product p = productService.findProductByPid(pid);
		 		list.add(p);
			}
		 	
		 	//������ת����product_listҳ��
		 	request.setAttribute("pageBean", pageBean);
		 	
		 	request.setAttribute("list", list);
		 	
		 	List list2 = pageBean.getList();
		 	
		 	request.getRequestDispatcher("/WEB-INF/jsp/product/product_list.jsp").forward(request, response);
		 	
	 }
	 
	 //����pid������Ʒ
	 public void info(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		 //��ȡpid
		 String pid = request.getParameter("pid");
		
		 //����pid��ѯ��Ʒ��Ϣ
		Product product=productService.findProductByPid(pid);
		
		//��ѯ������Ϣ
		Category category=categoryService.findCategoryByCid(product.getCid());
		
		//����cookie
		Cookie cookie = CookUtils.getCookieByName("history", request.getCookies());
		
		if(cookie == null){
			//��һ�������Ʒ
			Cookie c = new Cookie("history",pid);
			c.setMaxAge(60*60*24*7);
			response.addCookie(c);
		}else{
			//���ǵ�һ�������Ʒ
			String value=cookie.getValue();
			
			String[] ids=value.split("-");
			
			//������תΪlist����,������linkedlist��
			LinkedList<String> list = new LinkedList<String>(Arrays.asList(ids));
			
			if(list.contains(pid)){
				//�Ѿ������
				list.remove(pid);
				
				list.addFirst(pid);
			}else{
				//û�������
				if(list.size()>=6){
					list.removeLast();
					list.addFirst(pid);
				}else{
					list.addFirst(pid);
				}
			}
			
			//�ַ���ƴ��
			StringBuffer sb = new StringBuffer();
			
			for (String id : list) {
				sb.append(id+"-");
			}
			
			String history=sb.substring(0,sb.length()-1);
			
			Cookie c=new Cookie("history",history);
			
			c.setMaxAge(60*60*24*7);
			
			response.addCookie(c);
		}
			
	
		 //����Ʒ��Ϣ����request��
		request.setAttribute("category", category);
		
		request.setAttribute("p", product);
		 
		 //ת������Ʒ����ҳ��
		 request.getRequestDispatcher("/WEB-INF/jsp/product/product_info.jsp").forward(request, response);
	 }
	 

}
