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
	//ע��userService
	UserService userService=BeanFactory.newInstance(UserService.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    //ת��registerUIҳ��
    public void registerUI(HttpServletRequest request,HttpServletResponse response){
    	//System.out.println("ע�᷽��");
    	try {
			request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} 
    }
    
    //ע�᷽��
    public void register(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
    	
    	try {
    		//��ȡ������
        	Map<String, String[]> map = request.getParameterMap();
        	//����User����
        	User user = new User();
        	//��װ��user������
			BeanUtils.populate(user, map);
			//����uid
			String id = UUIDUtil.getId();
			user.setUid(id);
			
			//System.out.println(user);
			//���ɼ�����
			String code=UUIDUtil.getId();
			user.setCode(code);
			
			//�����û�״̬
			user.setState(Constant.USER_NOT_ACTIVE);
			
			//����userService,����ȡ��user���ݴ������ݿ�
			userService.register(user);
			
			//ע��ɹ�,������ʾ��Ϣ,��ת����info.jsp
			request.setAttribute("msg", "��ϲ��ע��ɹ�,��ȥ���伤��");
			
			request.getRequestDispatcher("/WEB-INF/jsp/info.jsp").forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
			//ע��ʧ��,����ʧ����ʾ,ת����info.jsp
			request.setAttribute("msg", "ע��ʧ��������ע��");
			
			request.getRequestDispatcher("/WEB-INF/jsp/info.jsp").forward(request, response);
		
		} 
    	//System.out.println(user);
    }

    //�����û�����
    public void active(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
    	//��ȡcode
    	try {
    		String code = request.getParameter("code");
        	
        	userService.active(code);
        	
        	//����ɹ�,������ʾ��Ϣ,��ת����info.jsp
        	request.setAttribute("msg", "����ɹ�,��ȥ��¼");
        	
        	request.getRequestDispatcher("/WEB-INF/jsp/info.jsp").forward(request, response);
        	
		}catch (UserCodeException e2) {
			e2.printStackTrace();
			request.setAttribute("msg", "��֤���쳣");
		
			request.getRequestDispatcher("/WEB-INF/jsp/info.jsp").forward(request, response);
			
		}catch (Exception e) {
			e.printStackTrace();
			
        	request.setAttribute("msg", "����ʧ��,������ע��");
        	
			request.getRequestDispatcher("/WEB-INF/jsp/info.jsp").forward(request, response);
		}
    	
    }
    
    //��¼����
    public void login(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
    	//��ȡ�û���������
    	String username = request.getParameter("username");
    	String password = request.getParameter("password");
    	//��ȡautologin��ֵ
    	String autoLogin = request.getParameter("autoLogin");
    	//System.out.println(autoLogin);
    	
    	//����userService��ѯ���ݿ�
    	User user=userService.login(username,password);
    	if(user != null){
            if(Constant.USER_IS_ACTIVE==user.getState()){
            	//�ж��Զ���¼�Ƿ�ѡ
            	if("on".equals(autoLogin)){
            		//��username��password����cookie
            		Cookie cookie = new Cookie("username", username);
            		Cookie cookie2 = new Cookie("password", password);
            		
            		//����cookie��������
            		cookie.setMaxAge(60*60*24*30);
            		cookie2.setMaxAge(60*60*24*30);
            		//����cookie���÷�Χ
            		cookie.setPath(request.getContextPath());
            		cookie2.setPath(request.getContextPath());
            		
            		//���͸������
            		response.addCookie(cookie);
            		response.addCookie(cookie2);
            	}
            	
            	request.getSession().setAttribute("user", user);
            	
    			response.sendRedirect(request.getContextPath());
    		}else{
    			request.setAttribute("msg", "�û�״̬�����");
    			
    			request.getRequestDispatcher("/login.jsp").forward(request, response);
    		}
    		
			
    	}else{
    		request.setAttribute("msg", "�û����������");
		
			request.getRequestDispatcher("/login.jsp").forward(request, response);
    	}
    		
    }
    
    //��¼�˳�
	public void logout(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
		//���session,����ת��login
		request.getSession().invalidate();
		
		response.sendRedirect(request.getContextPath()+"/login.jsp");
	}
}
