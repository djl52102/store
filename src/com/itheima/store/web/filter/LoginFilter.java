package com.itheima.store.web.filter;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.itheima.store.domain.User;
import com.itheima.store.service.UserService;
import com.itheima.store.utils.BeanFactory;



/**
 * Servlet Filter implementation class LoginFilter
 */
public class LoginFilter implements Filter {
    /**
     * Default constructor. 
     */
    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
       HttpServletRequest req=(HttpServletRequest)request;
       
	    // ��ȡcookie
	    Cookie[] cookies = req.getCookies();
	    
	    //���sessin�Ѵ���user,�������session
	    if(req.getSession().getAttribute("user") == null){
	    	if(cookies !=null && cookies.length>0){
				// ����cookie
				String username = null;
				String password = null;
				for (Cookie cookie : cookies) {
					// �ж�username
					String  name = URLDecoder.decode(cookie.getName(),"utf-8");
					String  value = URLDecoder.decode(cookie.getValue(),"utf-8");
					if (name.equals("username")) {
						username = value;
					}
					// �ж�password
					if (name.equals("password")) {
						password = value;
					}
				}

				// ��ѯ���ݿ�
				if(username !=null && password != null){
					UserService userService = BeanFactory.newInstance(UserService.class);

					User user = userService.login(username, password);

					if (user != null) {
						// ��ӵ�session��
						req.getSession().setAttribute("user", user);
					}
				}
					
		    }
	    }
	    
	    chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
