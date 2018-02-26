package com.cinker.model;

import java.io.Serializable;
import java.util.Date;

public class UserMember implements Serializable{

	private static final long serialVersionUID = 6011664808212775523L;
	
	private String userNumber;
	private String vistaMemberCardNumber;
	private String userNickName;
	private String userHeadImageUrl;
	private int userSex;
	private String openID;
	private Date loginTime;
	
	public String getUserNumber() {
		return userNumber;
	}
	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}
	public String getVistaMemberCardNumber() {
		return vistaMemberCardNumber;
	}
	public void setVistaMemberCardNumber(String vistaMemberCardNumber) {
		this.vistaMemberCardNumber = vistaMemberCardNumber;
	}
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	public String getUserHeadImageUrl() {
		return userHeadImageUrl;
	}
	public void setUserHeadImageUrl(String userHeadImageUrl) {
		this.userHeadImageUrl = userHeadImageUrl;
	}	
	public int getUserSex() {
		return userSex;
	}
	public void setUserSex(int userSex) {
		this.userSex = userSex;
	}
	public String getOpenID() {
		return openID;
	}
	public void setOpenID(String openID) {
		this.openID = openID;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	
	

}
