package com.cinker.model;

import java.io.Serializable;
import java.util.Date;

public class GetTicketsInfo implements Serializable{
	private static final long serialVersionUID = -1654516919531777795L;
	
	private String cinemaId;
	private String sessionId;
	private String scheduledFilmId;//ӰƬ�ڲ�id
	private String endTime;//������ӳʱ�����ʱ������ó�
	private String startTime;//��ӳʱ��
	private String title;//ӰƬ��
	private String nowTime;//��������
	private String description;//��������
	private String priceInCents;//����
	private String screenName;//Ӱ������
	private Date   sessionShowTime;//������ӳʱ��
	private String ticketTypeCode;//Ʊ������
	private String memberLevelId;//Ʊ������
	private String cinemaType;//Ʊ������
	private String priceNoDiscount;//ȫ��Ʊ����
	private String ticketTypeCodeNoDiscount;//Ʊ������
	private String filmLanguage;//��Ӱ����
	
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
	public String getNowTime() {
		return nowTime;
	}
	public void setNowTime(String nowTime) {
		this.nowTime = nowTime;
	}

	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public String getPriceInCents() {
		return priceInCents;
	}
	public void setPriceInCents(String priceInCents) {
		this.priceInCents = priceInCents;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getScheduledFilmId() {
		return scheduledFilmId;
	}
	public void setScheduledFilmId(String scheduledFilmId) {
		this.scheduledFilmId = scheduledFilmId;
	}
	public String getCinemaId() {
		return cinemaId;
	}
	public void setCinemaId(String cinemaId) {
		this.cinemaId = cinemaId;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public Date getSessionShowTime() {
		return sessionShowTime;
	}
	public void setSessionShowTime(Date sessionShowTime) {
		this.sessionShowTime = sessionShowTime;
	}
	public String getTicketTypeCode() {
		return ticketTypeCode;
	}
	public void setTicketTypeCode(String ticketTypeCode) {
		this.ticketTypeCode = ticketTypeCode;
	}
	public String getMemberLevelId() {
		return memberLevelId;
	}
	public void setMemberLevelId(String memberLevelId) {
		this.memberLevelId = memberLevelId;
	}
	public String getCinemaType() {
		return cinemaType;
	}
	public void setCinemaType(String cinemaType) {
		this.cinemaType = cinemaType;
	}
	public String getPriceNoDiscount() {
		return priceNoDiscount;
	}
	public void setPriceNoDiscount(String priceNoDiscount) {
		this.priceNoDiscount = priceNoDiscount;
	}
	public String getTicketTypeCodeNoDiscount() {
		return ticketTypeCodeNoDiscount;
	}
	public void setTicketTypeCodeNoDiscount(String ticketTypeCodeNoDiscount) {
		this.ticketTypeCodeNoDiscount = ticketTypeCodeNoDiscount;
	}
	public String getFilmLanguage() {
		return filmLanguage;
	}
	public void setFilmLanguage(String filmLanguage) {
		this.filmLanguage = filmLanguage;
	}
	
	

	
	

}
