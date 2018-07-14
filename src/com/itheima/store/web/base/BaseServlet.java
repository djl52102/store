package com.itheima.store.web.base;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itheima.store.domain.User;
import com.itheima.store.web.annotation.Auth;

/**
 * Servlet implementation class BaseServlet
 */
public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BaseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//获取请求的方式
		String md = req.getParameter("md");
		
		try {
			//根据反射找到对应的方法
			Method method = this.getClass().getMethod(md,HttpServletRequest.class, HttpServletResponse.class);
			//方法执行之前,判断是否需要登录
			if(method.isAnnotationPresent(Auth.class)){
				 //判断用户是否登陆，没有登陆跳转到登陆页面
		    	 HttpSession session = req.getSession();
		    	 
		    	 if(session.getAttribute("user") == null){
		    		 //记录之前的网站
		    		 String uri = req.getRequestURI();
		    		 
		    		 String queryString = req.getQueryString();
		    		 
		    		 //拼接uri
		    		 String oriPath=uri+"?"+queryString;
		    		 
		    		 //存入session
		    		 session.setAttribute("oriPath", oriPath);
		    		
		    		 resp.sendRedirect(req.getContextPath()+"/login.jsp");
		    		 
		    		 return;
		    	 }
		    	 
		    	 //判断用户是否有权限访问页面
		    	 Auth annotation = method.getAnnotation(Auth.class);
		    	 if("admin".equals(annotation.value())){
		    		 User user= (User)session.getAttribute("user");
		    		 
		    		 String remark = user.getRemark();
		    		 
		    		 if(!"admin".equals(remark)){
		    			 resp.setContentType("text/html;charset=UTF-8");
		    			 resp.getWriter().write("非法访问!!!");
		    			 return;
		    		 }
		    	 }
			}
			//执行方法
			method.invoke(this, req,resp);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} 
	}

}
