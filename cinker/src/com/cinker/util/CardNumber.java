package com.cinker.util;

import com.cinker.service.UserMemberService;

public class CardNumber {
	
	public static String getCardNumber(UserMemberService userMemberService,String areaNumber){		 
		String i;
		String areaNumbers = "";
		String VistaMemberCardNumber=userMemberService.getVistaMemberCardNumber("10"+areaNumber);
		
		if(null ==VistaMemberCardNumber){
			i="0000000";
		}else{
			i=VistaMemberCardNumber.substring(6,VistaMemberCardNumber.length());
			areaNumbers=VistaMemberCardNumber.substring(2,6);
		}	
		
		 if(areaNumbers.equals(areaNumber)){
			 i=String.format("%07d", Integer.parseInt(i)+1);
		 }else{
			 areaNumbers=areaNumber;
			 i="0000001";
		 }
		 return "10"+areaNumber+i;
	 }
}
