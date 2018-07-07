package com.itheima.store.service.impl;


import com.itheima.store.constant.Constant;
import com.itheima.store.dao.UserDao;
import com.itheima.store.domain.User;
import com.itheima.store.exception.UserCodeException;
import com.itheima.store.service.UserService;
import com.itheima.store.utils.BeanFactory;
import com.itheima.store.utils.MailUtil;

public class UserServiceImpl implements UserService {
	//ע��UserDao
	UserDao userDao=BeanFactory.newInstance(UserDao.class);
	@Override
	public void register(User user) {
		//����save�����������ݿ�
		userDao.register(user);
		
		//���û����ͼ����ʼ�
		try {
			MailUtil.sendMail(user.getEmail(), "��ϲ��ע��ɹ�,�������ӽ��м���<a href='http://localhost/store/user?md=active&code="+user.getCode()+"'>��˽��м���</a>");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} 
		
	}

	@Override
	public void active(String code) throws UserCodeException {
		//��ѯcode�Ƿ����
		User user=userDao.findByCode(code);
		
		//System.out.println(user);
		if(user != null){
			//����dao�����û�
			user.setState(Constant.USER_IS_ACTIVE);
		
			userDao.update(user);
		}else{
			throw new UserCodeException();
		}
	
	}

	@Override
	public User login(String username, String password) {
		//�ж�state״̬
		User user=userDao.login(username,password);
		
		return user;
	}

}
