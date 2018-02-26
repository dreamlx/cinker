package com.cinker.util;

import java.util.Comparator;

import com.cinker.model.FilmsSession;

@SuppressWarnings("rawtypes")
public class SortClass implements Comparator{

	@Override
	public int compare(Object arg0, Object arg1) {
		FilmsSession filmsSession1 = (FilmsSession) arg0;
		FilmsSession filmsSession2 = (FilmsSession) arg1;
		int flag = filmsSession1.getShowtime().compareTo(filmsSession2.getShowtime());
		return flag;
	}

}
