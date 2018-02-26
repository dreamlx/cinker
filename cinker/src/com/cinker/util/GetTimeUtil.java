package com.cinker.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class GetTimeUtil {
	
	public static String getRunTime(int a){
		int b;
		int c;
		b = a%60;
		c = a/60;
		int s = c >= 24?0:c;
		if(b<10){
			StringBuffer g = new StringBuffer().append("0").append(b);
			return s+":"+g;
		}else{
		return s+":"+b;
		}
		
		
	}
	public static int getEndTime(String str) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Date date = null;
		Date date1 = null;
		date = sdf.parse(str);
		date1 = sdf.parse("00:00");
		return (int) (date.getTime()-date1.getTime())/60000;
		
	}
	
	@SuppressWarnings("static-access")
	public static String tomorrow(String arg1){
		Date date = new Date();
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(cal.DATE, 1);
		date = cal.getTime();
		SimpleDateFormat sf = new SimpleDateFormat("MM-dd");
		String date1 = sf.format(date);
		return date1;
		
	}
	
	@SuppressWarnings("static-access")
	public static String theDayAfterTomorrow(String arg1){
		Date date = new Date();
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(cal.DATE, 2);
		date = cal.getTime();
		SimpleDateFormat sf = new SimpleDateFormat("MM-dd");
		String date1 = sf.format(date);
		return date1;
		
	}
		

}
