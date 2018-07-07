package com.itheima.store.web.servlet;


import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import com.itheima.store.constant.Constant;
import com.itheima.store.domain.User;
import com.itheima.store.exception.UserCodeException;
import com.itheima.store.service.UserService;
import com.itheima.store.utils.BeanFactory;
import com.itheima.store.utils.UUIDUtil;
import com.itheima.store.web.base.BaseServlet;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	//注入userService
	UserService userService=BeanFactory.newInstance(UserService.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    //转发registerUI页面
    public void registerUI(HttpServletRequest request,HttpServletResponse response){
    	//System.out.println("注册方法");
    	try {
			request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} 
    }
    
    //注册方法
    public void register(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
    	
    	try {
    		//获取表单数据
        	Map<String, String[]> map = request.getParameterMap();
        	//创建User对象
        	User user = new User();
        	//封装到user对象中
			BeanUtils.populate(user, map);
			//设置uid
			String id = UUIDUtil.getId();
			user.setUid(id);
			
			//System.out.println(user);
			//生成激活码
			String code=UUIDUtil.getId();
			user.setCode(code);
			
			//设置用户状态
			user.setState(Constant.USER_NOT_ACTIVE);
			
			//调用userService,将获取的user数据存入数据库
			userService.register(user);
			
			//注册成功,设置提示信息,并转发到info.jsp
			request.setAttribute("msg", "恭喜您注册成功,请去邮箱激活");
			
			request.getRequestDispatcher("/WEB-INF/jsp/info.jsp").forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
			//注册失败,设置失败提示,转发到info.jsp
			request.setAttribute("msg", "注册失败请重新注册");
			
			request.getRequestDispatcher("/WEB-INF/jsp/info.jsp").forward(request, response);
		
		} 
    	//System.out.println(user);
    }

    //激活用户方法
    public void active(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
    	//获取code
    	try {
    		String code = request.getParameter("code");
        	
        	userService.active(code);
        	
        	//激活成功,设置提示信息,并转发到info.jsp
        	request.setAttribute("msg", "激活成功,请去登录");
        	
        	request.getRequestDispatcher("/WEB-INF/jsp/info.jsp").forward(request, response);
        	
		}catch (UserCodeException e2) {
			e2.printStackTrace();
			request.setAttribute("msg", "验证码异常");
		
			request.getRequestDispatcher("/WEB-INF/jsp/info.jsp").forward(request, response);
			
		}catch (Exception e) {
			e.printStackTrace();
			
        	request.setAttribute("msg", "激活失败,请重新注册");
        	
			request.getRequestDispatcher("/WEB-INF/jsp/info.jsp").forward(request, response);
		}
    	
    }
    
    //登录方法
    public void login(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
    	//获取用户名和密码
    	String username = request.getParameter("username");
    	String password = request.getParameter("password");
    	//获取autologin的值
    	String autoLogin = request.getParameter("autoLogin");
    	//System.out.println(autoLogin);
    	
    	//调用userService查询数据库
    	User user=userService.login(username,password);
    	if(user != null){
            if(Constant.USER_IS_ACTIVE==user.getState()){
            	//判断自动登录是否勾选
            	if("on".equals(autoLogin)){
            		//将username和password存入cookie
            		Cookie cookie = new Cookie("username", username);
            		Cookie cookie2 = new Cookie("password", password);
            		
            		//设置cookie生命周期
            		cookie.setMaxAge(60*60*24*30);
            		cookie2.setMaxAge(60*60*24*30);
            		//设置cookie作用范围
            		cookie.setPath(request.getContextPath());
            		cookie2.setPath(request.getContextPath());
            		
            		//发送给浏览器
            		response.addCookie(cookie);
            		response.addCookie(cookie2);
            	}
            	
            	request.getSession().setAttribute("user", user);
            	
    			response.sendRedirect(request.getContextPath());
    		}else{
    			request.setAttribute("msg", "用户状态码错误");
    			
    			request.getRequestDispatcher("/login.jsp").forward(request, response);
    		}
    		
			
    	}else{
    		request.setAttribute("msg", "用户或密码错误");
		
			request.getRequestDispatcher("/login.jsp").forward(request, response);
    	}
    		
    }
    
    //登录退出
	public void logout(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
		//清除session,并跳转到login
		request.getSession().invalidate();
		
		response.sendRedirect(request.getContextPath()+"/login.jsp");
	}
}
