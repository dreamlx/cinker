package com.cinker.model;

import java.io.Serializable;
import java.util.Date;
/*
 * ���ڳ��۵�ӰѶ��Ϣ
 * */
public class FilmsSession implements Serializable{

	private static final long serialVersionUID = -7875328441827846739L;
	
	private String[] description = new String[]{};//��������
	private String sessionId;//���α���
	private Date showtime;//��ӳʱ��
	private String screenNumber;//Ӱ������
	private String screenName;//Ӱ������
	
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
