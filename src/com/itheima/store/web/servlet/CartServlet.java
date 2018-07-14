package com.itheima.store.web.servlet;

import com.itheima.store.domain.Cart;
import com.itheima.store.domain.CartItem;
import com.itheima.store.domain.Product;
import com.itheima.store.service.ProductService;
import com.itheima.store.utils.BeanFactory;
import com.itheima.store.web.base.BaseServlet;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CartServlet
 */
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	private ProductService productService=BeanFactory.newInstance(ProductService.class);
       
    public void test(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
    	
    }
    
    //��ӹ��ﳵ
    public void addCart(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
    	//��ȡpid
    	String pid = request.getParameter("pid");
    	
    	int count = Integer.parseInt(request.getParameter("count"));
    	
    	//���ù�����
    	//��ȡ��Ʒ��Ϣ
    	CartItem cartItem = new CartItem();
    	
    	Product product = productService.findProductByPid(pid);
    	
    	cartItem.setP(product);
    	
    	//��������
    	cartItem.setCount(count);
    	
    	//System.out.println(cartItem);
    	
    	//����������ӵ����ﳵ��
    	Cart cart = getCart(request);
    	
    	cart.addItem(cartItem);
    	
    	//��ת
    	response.sendRedirect(request.getContextPath()+"/cart?md=show");
    }
    
    //���ﳵչʾ����
    public void show(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
    	//ת����jspҳ��
    	request.getRequestDispatcher("/WEB-INF/jsp/cart/cart.jsp").forward(request, response);
    }
    
    //ɾ��������
    public void del(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
    	//��ȡ��Ʒ����
    	String pid = request.getParameter("pid");
    	//���ù��ﳵ����ɾ��
    	Cart cart = getCart(request);
    	cart.removeItem(pid);
    	
    	//��ת��show����
    	response.sendRedirect(request.getContextPath()+"/cart?md=show");
    }
    
    
    //��չ��ﳵ
    public void clear(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
    	//���ù��ﳵ��շ���
    	Cart cart = getCart(request);
    	
    	cart.clear();
    	
    	//��ת��show����
    	response.sendRedirect(request.getContextPath()+"/cart?md=show");
    }
    
    
    
    
    
    
    //��ȡ���ﳵ����
    private Cart getCart(HttpServletRequest request){
    	//�ж��Ƿ��ǵ�һ�ι���
    	HttpSession session = request.getSession();
    	
    	Cart cart=(Cart) session.getAttribute("cart");
    	
    	if(session.getAttribute("cart") == null){
    		//���û�У��½����ﳵ
    		cart=new Cart();
    		//����session
    		session.setAttribute("cart", cart);
    		
    		return cart;
    		
    	}else{
    		//����У�ֱ��ȡ��
    		return cart;
    	}
    }

}
