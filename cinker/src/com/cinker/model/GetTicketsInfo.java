package com.cinker.model;

import java.io.Serializable;
import java.util.Date;

public class GetTicketsInfo implements Serializable{
	private static final long serialVersionUID = -1654516919531777795L;
	
	private String cinemaId;
	private String sessionId;
	private String scheduledFilmId;//影片内部id
	private String endTime;//根据上映时间加上时长计算得出
	private String startTime;//上映时间
	private String title;//影片名
	private String nowTime;//今天日期
	private String description;//属性名称
	private String priceInCents;//单价
	private String screenName;//影厅号码
	private Date   sessionShowTime;//场次上映时间
	private String ticketTypeCode;//票类型码
	private String memberLevelId;//票类型码
	private String cinemaType;//票类型码
	private String priceNoDiscount;//全价票单价
	private String ticketTypeCodeNoDiscount;//票类型码
	private String filmLanguage;//电影语言
	
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
