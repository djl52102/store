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
    
    //添加购物车
    public void addCart(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
    	//获取pid
    	String pid = request.getParameter("pid");
    	
    	int count = Integer.parseInt(request.getParameter("count"));
    	
    	//设置购物项
    	//获取商品信息
    	CartItem cartItem = new CartItem();
    	
    	Product product = productService.findProductByPid(pid);
    	
    	cartItem.setP(product);
    	
    	//设置数量
    	cartItem.setCount(count);
    	
    	//System.out.println(cartItem);
    	
    	//将购物项添加到购物车中
    	Cart cart = getCart(request);
    	
    	cart.addItem(cartItem);
    	
    	//跳转
    	response.sendRedirect(request.getContextPath()+"/cart?md=show");
    }
    
    //购物车展示方法
    public void show(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
    	//转发到jsp页面
    	request.getRequestDispatcher("/WEB-INF/jsp/cart/cart.jsp").forward(request, response);
    }
    
    //删除购物项
    public void del(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
    	//获取商品参数
    	String pid = request.getParameter("pid");
    	//调用购物车进行删除
    	Cart cart = getCart(request);
    	cart.removeItem(pid);
    	
    	//跳转到show方法
    	response.sendRedirect(request.getContextPath()+"/cart?md=show");
    }
    
    
    //清空购物车
    public void clear(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
    	//调用购物车清空方法
    	Cart cart = getCart(request);
    	
    	cart.clear();
    	
    	//跳转到show方法
    	response.sendRedirect(request.getContextPath()+"/cart?md=show");
    }
    
    
    
    
    
    
    //获取购物车方法
    private Cart getCart(HttpServletRequest request){
    	//判断是否是第一次购物
    	HttpSession session = request.getSession();
    	
    	Cart cart=(Cart) session.getAttribute("cart");
    	
    	if(session.getAttribute("cart") == null){
    		//如果没有，新建购物车
    		cart=new Cart();
    		//存入session
    		session.setAttribute("cart", cart);
    		
    		return cart;
    		
    	}else{
    		//如果有，直接取出
    		return cart;
    	}
    }

}
