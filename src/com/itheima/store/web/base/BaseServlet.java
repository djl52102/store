package com.itheima.store.web.base;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
			//ִ�з���
			method.invoke(this, req,resp);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} 
	}

}
