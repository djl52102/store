package com.itheima.store.dao;

import com.itheima.store.domain.User;

public interface UserDao {

	void register(User user);

	User findByCode(String code);

	void update(User user);

	User login(String username, String password);

}
