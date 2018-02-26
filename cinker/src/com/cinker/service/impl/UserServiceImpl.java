package com.cinker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinker.dao.UserDao;
import com.cinker.model.User;
import com.cinker.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserDao userDao;
	
	
	@Override
	public User userLogin(String name, String password) {		
		return userDao.userLogin(name, password);
	}
	
	

}
