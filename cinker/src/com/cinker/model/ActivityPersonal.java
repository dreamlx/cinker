package com.cinker.model;

import java.io.Serializable;

public class ActivityPersonal implements Serializable{

	private static final long serialVersionUID = -6184749384726555175L;
	
	private String name;
	private String phone;
	private String filmTitle;
	private String sessionTime;
	private String startTime;
	private String dateTime;
	private String cinemaId;
	private String cinemaName;
	private String nickeName;
	private String activityId;
	private Integer quaty;
	private String orderNumber;
	private Integer status;
	private String activityUserNumber;
	private String orderEndTime;
	
	
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFilmTitle() {
		return filmTitle;
	}
	public void setFilmTitle(String filmTitle) {
		this.filmTitle = filmTitle;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getCinemaId() {
		return cinemaId;
	}
	public void setCinemaId(String cinemaId) {
		this.cinemaId = cinemaId;
	}
	public String getNickeName() {
		return nickeName;
	}
	public void setNickeName(String nickeName) {
		this.nickeName = nickeName;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public Integer getQuaty() {
		return quaty;
	}
	public void setQuaty(Integer quaty) {
		this.quaty = quaty;
	}
	public String getSessionTime() {
		return sessionTime;
	}
	public void setSessionTime(String sessionTime) {
		this.sessionTime = sessionTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getActivityUserNumber() {
		return activityUserNumber;
	}
	public void setActivityUserNumber(String activityUserNumber) {
		this.activityUserNumber = activityUserNumber;
	}
	public String getOrderEndTime() {
		return orderEndTime;
	}
	public void setOrderEndTime(String orderEndTime) {
		this.orderEndTime = orderEndTime;
	}
	public String getCinemaName() {
		return cinemaName;
	}
	public void setCinemaName(String cinemaName) {
		this.cinemaName = cinemaName;
	}
	
	
	

}
