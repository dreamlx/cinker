package com.cinker.util;

import java.util.Comparator;

import com.cinker.model.ActivityFilm;

@SuppressWarnings("rawtypes")
public class SortClasses implements Comparator{

	@Override
	public int compare(Object arg0, Object arg1) {
		ActivityFilm activityFilm1 = (ActivityFilm) arg0;
		ActivityFilm activityFilm2 = (ActivityFilm) arg1;
		int flag = activityFilm1.getSessionTime().compareTo(activityFilm2.getSessionTime());
		return flag;
	}

}
