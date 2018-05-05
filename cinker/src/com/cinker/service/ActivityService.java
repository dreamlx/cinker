package com.cinker.service;

import java.util.List;

import com.cinker.model.ActivityFilm;
import com.cinker.model.ActivityPersonal;

public interface ActivityService {
	
	public ActivityFilm getActivityFilm(String eventId);
	
	public int getActivityPersonal(String eventId);
	
	public void insertActivity(ActivityPersonal activityPersonal);
	
	public List<ActivityFilm> getActivityFilmByCinemaId(String cinemaId);

	public void updateActivityPersonal(int status,String orderEndTime, String orderNumber);
	
	public ActivityPersonal getActivityPersonalbyorderNumber(String orderNumber);
	
	public List<ActivityPersonal> getActivityPersonal(String activityId,String sessionTime,
			String name,String phone,String filmTitle,String cinemaId,Integer page); 

	public void updateActivityPersonalStatus();

	int getActivityPersonalCount(String activityId, String sessionTime, String name, String phone, String filmTitle,
			String cinemaId);
	
}
