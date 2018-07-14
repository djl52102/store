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
		//��ȡ����ķ�ʽ
		String md = req.getParameter("md");
		
		try {
			//���ݷ����ҵ���Ӧ�ķ���
			Method method = this.getClass().getMethod(md,HttpServletRequest.class, HttpServletResponse.class);
			//����ִ��֮ǰ,�ж��Ƿ���Ҫ��¼
			if(method.isAnnotationPresent(Auth.class)){
				 //�ж��û��Ƿ��½��û�е�½��ת����½ҳ��
		    	 HttpSession session = req.getSession();
		    	 
		    	 if(session.getAttribute("user") == null){
		    		 //��¼֮ǰ����վ
		    		 String uri = req.getRequestURI();
		    		 
		    		 String queryString = req.getQueryString();
		    		 
		    		 //ƴ��uri
		    		 String oriPath=uri+"?"+queryString;
		    		 
		    		 //����session
		    		 session.setAttribute("oriPath", oriPath);
		    		
		    		 resp.sendRedirect(req.getContextPath()+"/login.jsp");
		    		 
		    		 return;
		    	 }
		    	 
		    	 //�ж��û��Ƿ���Ȩ�޷���ҳ��
		    	 Auth annotation = method.getAnnotation(Auth.class);
		    	 if("admin".equals(annotation.value())){
		    		 User user= (User)session.getAttribute("user");
		    		 
		    		 String remark = user.getRemark();
		    		 
		    		 if(!"admin".equals(remark)){
		    			 resp.setContentType("text/html;charset=UTF-8");
		    			 resp.getWriter().write("�Ƿ�����!!!");
		    			 return;
		    		 }
		    	 }
			}
			//ִ�з���
			method.invoke(this, req,resp);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} 
	}

}
