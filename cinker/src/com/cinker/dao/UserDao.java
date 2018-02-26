package com.cinker.dao;

import com.cinker.model.User;

public interface UserDao {
	
	public User userLogin(String name, String password);

}
