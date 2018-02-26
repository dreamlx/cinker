package com.cinker.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UserVipMember implements Serializable{
private static final long serialVersionUID = 6011664808212775523L;
	
	private String userNumber;
	private String userName;
	private String password;
	private String vistaMemberCardNumber;
	private String userNickName;
	private String userHeadImageUrl;
	private int userSex;
	private String openID;
	private Date loginTime;
	private String firstName;
	private String lastName;
	private String phone;
	private String email;
	private String birthday;
	private String sex;
	private String clubId;
	private String memberLevelId;
	private String preferredGenres;
	private String pin;
	private List<BalanceList> balanceList;
	private String mobilePhone;
	
	private String memberLevelName;
	private Double pointsRemaining_2;
	private Double pointsRemaining_1;
	
	
	
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getMemberLevelName() {
		return memberLevelName;
	}
	public void setMemberLevelName(String memberLevelName) {
		this.memberLevelName = memberLevelName;
	}
	public Double getPointsRemaining_2() {
		return pointsRemaining_2;
	}
	public void setPointsRemaining_2(Double pointsRemaining_2) {
		this.pointsRemaining_2 = pointsRemaining_2;
	}
	public Double getPointsRemaining_1() {
		return pointsRemaining_1;
	}
	public void setPointsRemaining_1(Double pointsRemaining_1) {
		this.pointsRemaining_1 = pointsRemaining_1;
	}
	public String getUserNumber() {
		return userNumber;
	}
	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getClubId() {
		return clubId;
	}
	public void setClubId(String clubId) {
		this.clubId = clubId;
	}
	public String getMemberLevelId() {
		return memberLevelId;
	}
	public void setMemberLevelId(String memberLevelId) {
		this.memberLevelId = memberLevelId;
	}
	public String getPreferredGenres() {
		return preferredGenres;
	}
	public void setPreferredGenres(String preferredGenres) {
		this.preferredGenres = preferredGenres;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<BalanceList> getBalanceList() {
		return balanceList;
	}
	public void setBalanceList(List<BalanceList> balanceList) {
		this.balanceList = balanceList;
	}
	
	
	
	
	

}
