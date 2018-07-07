package com.itheima.store.service;

import com.itheima.store.domain.User;
import com.itheima.store.exception.UserCodeException;

public interface UserService {

	void register(User user);

	void active(String code) throws UserCodeException;

	User login(String username, String password);

}
