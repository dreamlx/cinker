package com.cinker.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinker.dao.ActivityDao;
import com.cinker.model.ActivityFilm;
import com.cinker.model.ActivityPersonal;
import com.cinker.service.ActivityService;

@Service
public class ActivityServiceImpl implements ActivityService{
	
	@Autowired
	ActivityDao activityDao;

	@Override
	public ActivityFilm getActivityFilm(String eventId) {		
		return activityDao.getActivityFilm(eventId);
	}

	@Override
	public int getActivityPersonal(String eventId) {		
		return activityDao.getActivityPersonal(eventId);
	}

	@Override
	public void insertActivity(ActivityPersonal activityPersonal) {
		activityDao.insertActivity(activityPersonal);
		
	}

	@Override
	public List<ActivityFilm> getActivityFilmByCinemaId(String cinemaId) {
		return activityDao.getActivityFilmByCinemaId(cinemaId);
	}

	@Override
	public void updateActivityPersonal(int status,String orderEndTime, String orderNumber) {
		activityDao.updateActivityPersonal(status, orderEndTime, orderNumber);		
	}

	@Override
	public ActivityPersonal getActivityPersonalbyorderNumber(String orderNumber) {
		
		return activityDao.getActivityPersonalbyorderNumber(orderNumber);
	}

	@Override
	public List<ActivityPersonal> getActivityPersonalByActivityId(
			String activityId,Integer page) {
		return activityDao.getActivityPersonalByActivityId(activityId,page);
	}

	@Override
	public void updateActivityPersonalStatus() {
		activityDao.updateActivityPersonalStatus();
		
	}

}
