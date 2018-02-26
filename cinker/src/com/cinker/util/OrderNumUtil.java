package com.cinker.util;


import java.util.UUID;

public class OrderNumUtil {
	
	public static String getOrderId(){
		int machineId = 1;
		 int hashCodeV = UUID.randomUUID().toString().hashCode();
		 if(hashCodeV < 0) {
			 hashCodeV = - hashCodeV;
		 }
		 return machineId+String.format("%015d", hashCodeV);
	}
	
	public static void main(String[] args) {
		
	}
	
	public static String getRandomNumber(){
		int i = (int)((Math.random()*9+1)*100000);
		return i + ""; 
	}
}
