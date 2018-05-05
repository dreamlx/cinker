package com.cinker.model;

import java.io.Serializable;
import java.util.Date;

public class ActivityFilm implements Serializable{

	private static final long serialVersionUID = -9197191785242246801L;
	
	private String id;
	private String cinemaId;
	private String name;
	private String sessionId;
	private String filmId;
	private String sessionName;
	private String activityFilmType;
	private String filmTitle;
	private String chineseName;
	private String englishName;
	private double totalValueCents;
	private String sessionTime;
	private String startSessionTime;
	private Integer total;
	private Integer quaty;
	private Date sessionTimes;
	private Date startSessionTimes;
	private String language;
	private String surfaceImage;
	private Integer activityTickets;
	private String url;
	private Integer isForUpperMember;
	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getActivityTickets() {
		return activityTickets;
	}
	public void setActivityTickets(Integer activityTickets) {
		this.activityTickets = activityTickets;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getFilmId() {
		return filmId;
	}
	public void setFilmId(String filmId) {
		this.filmId = filmId;
	}
	public String getSessionName() {
		return sessionName;
	}
	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}
	public String getFilmTitle() {
		return filmTitle;
	}
	public void setFilmTitle(String filmTitle) {
		this.filmTitle = filmTitle;
	}
	public double getTotalValueCents() {
		return totalValueCents;
	}
	public void setTotalValueCents(double totalValueCents) {
		this.totalValueCents = totalValueCents;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getQuaty() {
		return quaty;
	}
	public void setQuaty(int quaty) {
		this.quaty = quaty;
	}
	public String getActivityFilmType() {
		return activityFilmType;
	}
	public void setActivityFilmType(String activityFilmType) {
		this.activityFilmType = activityFilmType;
	}
	public String getSessionTime() {
		return sessionTime;
	}
	public void setSessionTime(String sessionTime) {
		this.sessionTime = sessionTime;
	}
	public String getStartSessionTime() {
		return startSessionTime;
	}
	public void setStartSessionTime(String startSessionTime) {
		this.startSessionTime = startSessionTime;
	}
	public Date getSessionTimes() {
		return sessionTimes;
	}
	public void setSessionTimes(Date sessionTimes) {
		this.sessionTimes = sessionTimes;
	}
	public Date getStartSessionTimes() {
		return startSessionTimes;
	}
	public void setStartSessionTimes(Date startSessionTimes) {
		this.startSessionTimes = startSessionTimes;
	}
	public String getChineseName() {
		return chineseName;
	}
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getSurfaceImage() {
		return surfaceImage;
	}
	public void setSurfaceImage(String surfaceImage) {
		this.surfaceImage = surfaceImage;
	}
	
	public Integer getIsForUpperMember() {
		return isForUpperMember;
	}
	public void setIsForUpperMember(Integer isForUpperMember) {
		this.isForUpperMember = isForUpperMember;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
