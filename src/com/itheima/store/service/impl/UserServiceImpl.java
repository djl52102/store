package com.itheima.store.service.impl;


import com.itheima.store.constant.Constant;
import com.itheima.store.dao.UserDao;
import com.itheima.store.domain.User;
import com.itheima.store.exception.UserCodeException;
import com.itheima.store.service.UserService;
import com.itheima.store.utils.BeanFactory;
import com.itheima.store.utils.MailUtil;

public class UserServiceImpl implements UserService {
	//注入UserDao
	UserDao userDao=BeanFactory.newInstance(UserDao.class);
	@Override
	public void register(User user) {
		//调用save方法存入数据库
		userDao.register(user);
		
		//给用户发送激活邮件
		try {
			MailUtil.sendMail(user.getEmail(), "恭喜您注册成功,请点击链接进行激活<a href='http://localhost/store/user?md=active&code="+user.getCode()+"'>点此进行激活</a>");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} 
		
	}

	@Override
	public void active(String code) throws UserCodeException {
		//查询code是否存在
		User user=userDao.findByCode(code);
		
		//System.out.println(user);
		if(user != null){
			//调用dao激活用户
			user.setState(Constant.USER_IS_ACTIVE);
		
			userDao.update(user);
		}else{
			throw new UserCodeException();
		}
	
	}

	@Override
	public User login(String username, String password) {
		//判断state状态
		User user=userDao.login(username,password);
		
		return user;
	}

}
