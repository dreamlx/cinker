package com.cinker.util;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.beans.factory.annotation.Autowired;

import com.cinker.constant.Constant;
import com.cinker.service.UserMemberService;


public class Test {
//	private static String i = "00000";
//	private static String date = "201704";
//	private static String i = new String("00000");
//	private static String date = "201704";
	static ConcurrentLinkedQueue<String> sendMsgQueue = new ConcurrentLinkedQueue<String>();
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		//System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(new Date(1492444800000L)));;
		
		//UserMemberService userMemberService = new UserMemberService();
		Integer youNumber = 12;     
	    // 0 代表前面补充0     
	    // 4 代表长度为4     
	    // d 代表参数为正数型     
	    String str = String.format("%04d", youNumber);     
	    //System.out.println(new Date(1492444800000l).toString()); // 0001 
//		System.out.println(new CustomDateConverter().convert("1492444800000"));
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//		String dstr="1492444800000";  
		//Calendar c = Calendar.getInstance();
		//SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		//String b=simpleDateFormat.parse(c);
		//System.out.println(userMemberService);
		System.out.println(OrderNumUtil.getOrderId());
		System.out.println(OrderNumUtil.getOrderId());
		System.out.println(OrderNumUtil.getOrderId());
		System.out.println(OrderNumUtil.getOrderId());
		System.out.println(OrderNumUtil.getOrderId());
//		Date d = new Date();  
//        System.out.println(d);  
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");  
//        String dateNowStr = sdf.format(d);  
//        System.out.println("格式化后的日期：" + dateNowStr); 
//        System.out.println(date.equals(dateNowStr));
	}
	
	 public static String roundByScale(double v, int scale) {  
		
	        if(scale == 0){  
	            return new DecimalFormat("0").format(v);  
	        }  
	        String formatStr = "0.";  
	        for(int i=0;i<scale;i++){  
	            formatStr = formatStr + "0";  
	        }  
	        return new DecimalFormat(formatStr).format(v);  
	    }
	 
	 
}
