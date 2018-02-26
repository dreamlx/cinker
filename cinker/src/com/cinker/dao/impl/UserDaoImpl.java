package com.cinker.dao.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.cinker.dao.UserDao;
import com.cinker.model.User;

@SuppressWarnings({"rawtypes"})
@Repository
public class UserDaoImpl extends BaseDaoImpl implements UserDao {

	@Override
	public User userLogin(String name, String password) throws EmptyResultDataAccessException{
		try{
			String sql = "SELECT * FROM MD_USER WHERE NAME = ? AND PASSWORD = ? ";
			
			return jdbcTemplate.queryForObject(sql, new Object[]{name,password},  new BeanPropertyRowMapper<User>(User.class));
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	

}
