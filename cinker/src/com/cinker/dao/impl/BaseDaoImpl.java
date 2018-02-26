package com.cinker.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.cinker.dao.BaseDao;
@Repository
@SuppressWarnings({"unchecked", "rawtypes"})
public class BaseDaoImpl<T> implements BaseDao<T>{

	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	protected BeanPropertySqlParameterSource paramBeanMapper(Object object) {
		return new BeanPropertySqlParameterSource(object);
	}

	@Override
	public List<T> queryForList(String sql, Class<T> clazz) {
		List<T> list = null;
		if(clazz != null){
			list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(clazz));
		}else{
			list = (List<T>) jdbcTemplate.queryForList(sql);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> queryListForMoreObject(String sql) {
		List<Map<String,Object>> listMap = null;
		listMap = jdbcTemplate.queryForList(sql);
		return listMap;
	}


	@Override
	public Map<String, Object> queryForMap(String sql, Map<String, Object> mapObject) {
		Map<String, Object> map = null;
		if(mapObject != null){
			map = namedParameterJdbcTemplate.queryForMap(sql, mapObject);
		}else{
			map = jdbcTemplate.queryForMap(sql);
		}
		return map;
	}

	@Override
	public int updateForMap(String sql, Map<String, Object> map) {
		int result = 0;
		if(map != null){
			result = namedParameterJdbcTemplate.update(sql, map);
		}else{
			result = jdbcTemplate.update(sql);
		}
		return result;
	}

	@Override
	public Integer queryForInt(String sql, Object... args) {
		int result = 0;
		if(args != null){
			result = jdbcTemplate.queryForObject(sql, args, Integer.class);
		}else{
			result = jdbcTemplate.queryForObject(sql, Integer.class);
		}
		return result;
	}

	@Override
	public Integer update(String sql, Object... args) {
		int result = 0;
		if(args != null){
			result = jdbcTemplate.update(sql, args);
		}else{
			result = jdbcTemplate.update(sql);
		}
		return result;
	}

	@Override
	public int[] batchUpdate(String sql, List<Object[]> batchArgs) {
		return jdbcTemplate.batchUpdate(sql, batchArgs);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return namedParameterJdbcTemplate;
	}

	

	
	

}
