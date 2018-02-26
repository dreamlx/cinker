package com.cinker.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Film implements Serializable{

	private static final long serialVersionUID = 7575049144388007437L;
	
	private String cinemaId;
	private String sessionId;
	private String scheduledFilmId;//影片内部编码
	private String title;//影片名称 
	private String shortCode;//影片广电编码
	private String ratingDescription;//影片简介
	private String synopsis;//影片其他信息
	private String runTime;//影片时长
	private String surfaceImageUrl;//海报图片地址
	private List<FilmsSession> sessions = new ArrayList<FilmsSession>();
	private String chineseName;//中文名称
	private String englishName;//英文名称
	private String cinecism;
	private String dateTime;
	private String filmSortDate; //用于首页排片的日期 add by Ike温泉
	private String filmDType; //2D or 3D add by Ike温泉
	private String filmActors; //2D or 3D add by Ike温泉
	private String priceInCents;
	private String endTime;//根据上映时间加上时长计算得出
	private String startTime;//上映时间
	private String screenName;//影厅号码
	private Date sessionShowTime;
	
	public String getFilmDType() {
		return filmDType;
	}
	public void setFilmDType(String Dtype) {
		this.filmDType = Dtype;
	}
	public String getFilmActors() {
		return filmActors;
	}
	public void setFilmActors(String actors) {
		this.filmActors = actors;
	}
	public String getScheduledFilmId() {
		return scheduledFilmId;
	}
	public void setScheduledFilmId(String scheduledFilmId) {
		this.scheduledFilmId = scheduledFilmId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getShortCode() {
		return shortCode;
	}
	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}
	public String getRatingDescription() {
		return ratingDescription;
	}
	public void setRatingDescription(String ratingDescription) {
		this.ratingDescription = ratingDescription;
	}
	public String getSynopsis() {
		return synopsis;
	}
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	public String getRunTime() {
		return runTime;
	}
	public void setRunTime(String runTime) {
		this.runTime = runTime;
	}
	public List<FilmsSession> getSessions() {
		return sessions;
	}
	public void setSessions(List<FilmsSession> sessions) {
		this.sessions = sessions;
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
	public String getSurfaceImageUrl() {
		return surfaceImageUrl;
	}
	public void setSurfaceImageUrl(String surfaceImageUrl) {
		this.surfaceImageUrl = surfaceImageUrl;
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
	public String getCinecism() {
		return cinecism;
	}
	public void setCinecism(String cinecism) {
		this.cinecism = cinecism;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public void setFilmSortDate(String filmSortDate) {
		this.filmSortDate = filmSortDate;
	}
	public String getFilmSortDate() {
		return this.filmSortDate;
	}
	public String getPriceInCents() {
		return priceInCents;
	}
	public void setPriceInCents(String priceInCents) {
		this.priceInCents = priceInCents;
	}
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
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public Date getSessionShowTime() {
		return sessionShowTime;
	}
	public void setSessionShowTime(Date sessionShowTime) {
		this.sessionShowTime = sessionShowTime;
	}
	
	
}
