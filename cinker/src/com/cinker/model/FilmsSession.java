package com.cinker.model;

import java.io.Serializable;
import java.util.Date;
/*
 * 正在出售的影讯信息
 * */
public class FilmsSession implements Serializable{

	private static final long serialVersionUID = -7875328441827846739L;
	
	private String[] description = new String[]{};//属性名称
	private String sessionId;//场次编码
	private Date showtime;//放映时间
	private String screenNumber;//影厅号码
	private String screenName;//影厅名称
	
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public Date getShowtime() {
		return showtime;
	}
	public void setShowtime(Date showtime) {
		this.showtime = showtime;
	}
	
	public String getScreenNumber() {
		return screenNumber;
	}
	public void setScreenNumber(String screenNumber) {
		this.screenNumber = screenNumber;
	}
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public String[] getDescription() {
		return description;
	}
	public void setDescription(String[] description) {
		this.description = description;
	}
	
	
	

	
	

}
