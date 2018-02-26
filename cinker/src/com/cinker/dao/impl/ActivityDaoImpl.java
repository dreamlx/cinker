package com.cinker.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cinker.dao.ActivityDao;
import com.cinker.model.ActivityFilm;
import com.cinker.model.ActivityPersonal;

@SuppressWarnings("rawtypes")
@Repository
public class ActivityDaoImpl extends BaseDaoImpl implements ActivityDao{

	private Integer pageSize = 20;
	
	@SuppressWarnings("unchecked")
	@Override
	public ActivityFilm getActivityFilm(String eventId) {
		String sql = "select * from cinker_activity_film where SessionId = '"+ eventId+"'" ;
		try {
			return jdbcTemplate.queryForObject(sql, new RowMapper(){
				 public Object mapRow(ResultSet rs,int rowNum)throws SQLException { 
					 ActivityFilm activityFilm = new ActivityFilm();
					 activityFilm.setCinemaId(rs.getString("CinemaId"));
					 activityFilm.setFilmId(rs.getString("FilmId"));
					 activityFilm.setFilmTitle(rs.getString("FilmTitle"));
					 activityFilm.setSessionId(rs.getString("SessionId"));
					 activityFilm.setSessionName(rs.getString("SessionName"));
					 activityFilm.setStartSessionTime(rs.getString("StartSessionTime"));
					 activityFilm.setActivityFilmType(rs.getString("ActivityFilmType"));
					 activityFilm.setTotalValueCents(rs.getDouble("TotalValueCents"));
					 activityFilm.setTotal(rs.getInt("Total"));
					 activityFilm.setQuaty(rs.getInt("Quaty"));
					 activityFilm.setSessionTime(rs.getString("SessionTime"));
					 activityFilm.setIsForUpperMember(rs.getInt("isForUpperMember"));
					 return activityFilm;
				 }
			});
			
		}catch (EmptyResultDataAccessException e) {
			 return null;
		}
		
	}

	@Override
	public int getActivityPersonal(String eventId) {
		try{
			String sql = "SELECT SUM(Quaty) FROM cinker_activity_personal where ActivityId = '"+ eventId +"' AND Status = 1 OR Status = 0"  ;
			return jdbcTemplate.queryForObject(sql, Integer.class);
		}catch(Exception e){
			if((e instanceof IncorrectResultSizeDataAccessException) &&((IncorrectResultSizeDataAccessException)e).getActualSize()==0)
			return 0;
		}
		return 0;
		
	}

	@Override
	public void insertActivity(ActivityPersonal activityPersonal) {
		try {
			String sql = "INSERT INTO cinker_activity_personal(Name,Phone,FilmTitle,SessionTime,StartTime,DateTime,CinemaId,NickeName,ActivityId,Quaty,ActivityUserNumber,OrderNumber,OrderEndTime,Status)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			update(sql,new Object[]{activityPersonal.getName(),activityPersonal.getPhone(),activityPersonal.getFilmTitle(),activityPersonal.getSessionTime(),activityPersonal.getStartTime(),activityPersonal.getDateTime(),activityPersonal.getCinemaId(),activityPersonal.getNickeName(),activityPersonal.getActivityId(),activityPersonal.getQuaty(),activityPersonal.getActivityUserNumber(),activityPersonal.getOrderNumber(),activityPersonal.getOrderEndTime(),activityPersonal.getStatus()});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ActivityFilm> getActivityFilmByCinemaId(String cinemaId) {
		try{
			String sql = "SELECT * FROM cinker_activity_film WHERE CinemaId = ?";
			return jdbcTemplate.query(sql,new Object[]{cinemaId}, new BeanPropertyRowMapper(ActivityFilm.class));
		}catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	@Override
	public void updateActivityPersonal(int status,String orderEndTime, String orderNumber) {
		String sql = "UPDATE cinker_activity_personal SET Status = ?,OrderEndTime = ? WHERE OrderNumber = ?";
		update(sql, new Object[]{status,orderEndTime,orderNumber});
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public ActivityPersonal getActivityPersonalbyorderNumber(String orderNumber) {
		String sql = "select * from cinker_activity_personal where OrderNumber = '"+ orderNumber +"'" ;
		try {
			return jdbcTemplate.queryForObject(sql, new RowMapper(){
				 public Object mapRow(ResultSet rs,int rowNum)throws SQLException { 
					 ActivityPersonal activityPersonal = new ActivityPersonal();
					 activityPersonal.setActivityId(rs.getString("ActivityId"));
					 activityPersonal.setActivityUserNumber(rs.getString("ActivityUserNumber"));
					 activityPersonal.setCinemaId(rs.getString("CinemaId"));
					 activityPersonal.setDateTime(rs.getString("DateTime"));
					 activityPersonal.setFilmTitle(rs.getString("FilmTitle"));
					 activityPersonal.setName(rs.getString("Name"));
					 activityPersonal.setNickeName(rs.getString("NickeName"));
					 activityPersonal.setOrderEndTime(rs.getString("OrderEndTime"));
					 activityPersonal.setPhone(rs.getString("Phone"));
					 activityPersonal.setActivityUserNumber(rs.getString("ActivityUserNumber"));
					 activityPersonal.setQuaty(rs.getInt("Quaty"));
					 activityPersonal.setSessionTime(rs.getString("SessionTime"));
					 activityPersonal.setStatus(rs.getInt("Status"));
					 activityPersonal.setOrderNumber(rs.getString("OrderNumber"));
					 return activityPersonal;
				 }
			});
			
		}catch (EmptyResultDataAccessException e) {
			 return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ActivityPersonal> getActivityPersonalByActivityId(
			String activityId,Integer page) {
		try{
			StringBuffer sb = new StringBuffer("SELECT * FROM cinker_activity_personal WHERE 1=1 AND Status = 1 ");
			if(activityId != null){
				sb.append(" AND ActivityId LIKE '%" + activityId +"%'");
			}
			sb.append(" ORDER BY ID desc ");
			if(page != 0 && page!= null){
				sb.append(" LIMIT "+(page-1)*pageSize +","+pageSize +"");
			}
			return jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper(ActivityPersonal.class));
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

	@Override
	public void updateActivityPersonalStatus() {
		String sql = "UPDATE cinker_activity_personal SET Status = 2 WHERE TIMESTAMPDIFF(SECOND,DATE_ADD(StartTime,interval 10 DAY_MINUTE),NOW())>0 AND Status = 0;";
		jdbcTemplate.update(sql);
	}

	

}
