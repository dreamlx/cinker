package com.cinker.dao;

import java.util.List;
import java.util.Map;

public interface BaseDao<T> {
	
	public List<T> queryForList(String sql, Class<T> clazz);
	
	public List<Map<String, Object>> queryListForMoreObject(String sql);
		
	public Map<String, Object> queryForMap(String sql, Map<String, Object> map);
	
	public int updateForMap(String sql, Map<String, Object> map);
	
	public Integer queryForInt(String sql, Object...args);
	
	public Integer update(String sql, Object...args);
	
	public int[] batchUpdate(String sql, List<Object[]> batchArgs);
	

}
