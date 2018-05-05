package com.cinker.dao;

import java.util.List;

import com.cinker.model.ActivityFilm;
import com.cinker.model.ActivityPersonal;

public interface ActivityDao {
	
	public ActivityFilm getActivityFilm(String eventId);
	
	public List<ActivityFilm> getActivityFilmByCinemaId(String cinemaId);
	
	public int getActivityPersonal(String eventId);
	
	public void insertActivity(ActivityPersonal activityPersonal);
	
	public void updateActivityPersonal(int status,String orderEndTime, String orderNumber);
	
	public ActivityPersonal getActivityPersonalbyorderNumber(String orderNumber);
	
	List<ActivityPersonal> getActivityPersonal(String activityId,String sessionTime,String name,String phone,String filmTitle,String cinemaId,Integer page);

	public void updateActivityPersonalStatus();

	int getActivityPersonalCount(String activityId, String sessionTime, String name, String phone, String filmTitle,
			String cinemaId);

	
	
}
