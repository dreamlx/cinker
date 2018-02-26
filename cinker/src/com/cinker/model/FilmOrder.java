package com.cinker.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilmOrder implements Serializable{

	private static final long serialVersionUID = 5337212652856884982L;
	public  static final int ORDER_SUCCESS = 1;
	public  static final int ORDER_TYPE_NORMAL = 1;
	public  static final int ORDER_TYPE_ACTIVITY = 2;
	private String orderNumber;
	private String scheduledFilmId;
	private String filmTitle;
	private String cinemaId;
	private String cinemaName;
	private String sessionId;
	private String sessionName;
	private String   showTime;
	private String areaCategoryCode;
	private int status;
	private int totalOrderCount;
	private String totalValueCents;
	private String startTime;
	private String endTime;	
	private String userNumber;
	private int paymentId;
	private String bookingNumber;//取票码
	private String bookingID;//验证码
	private String seat;
	private int    paymentStatus;
	private String englishName;
	private int orderType;
	private int type;
	private String transactionId;
	
	private Map<String,Object> results = new HashMap<String,Object>();
	private List<TicketType> ticketTypes = new ArrayList<TicketType>();
	private List<SelectedSeat> selectedSeats = new ArrayList<SelectedSeat>();
	
	private String  sessionShowTime;
	
	// 20170718 Qin 会员下单接口
	private int freeRightFlg;
	private String  cinemaType;
	private String  memberLevelId;
	
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public int getOrderType() {
		return orderType;
	}
	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getScheduledFilmId() {
		return scheduledFilmId;
	}
	public void setScheduledFilmId(String scheduledFilmId) {
		this.scheduledFilmId = scheduledFilmId;
	}
	public String getFilmTitle() {
		return filmTitle;
	}
	public void setFilmTitle(String filmTitle) {
		this.filmTitle = filmTitle;
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
	public String getAreaCategoryCode() {
		return areaCategoryCode;
	}
	public void setAreaCategoryCode(String areaCategoryCode) {
		this.areaCategoryCode = areaCategoryCode;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getUserNumber() {
		return userNumber;
	}
	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}
	public int getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}
	public String getBookingNumber() {
		return bookingNumber;
	}
	public void setBookingNumber(String bookingNumber) {
		this.bookingNumber = bookingNumber;
	}
	public String getBookingID() {
		return bookingID;
	}
	public void setBookingID(String bookingID) {
		this.bookingID = bookingID;
	}

	public List<TicketType> getTicketTypes() {
		return ticketTypes;
	}
	public void setTicketTypes(List<TicketType> ticketTypes) {
		this.ticketTypes = ticketTypes;
	}
	public List<SelectedSeat> getSelectedSeats() {
		return selectedSeats;
	}
	public void setSelectedSeats(List<SelectedSeat> selectedSeats) {
		this.selectedSeats = selectedSeats;
	}
	public String getSeat() {
		return seat;
	}
	public void setSeat(String seat) {
		this.seat = seat;
	}
	public int getTotalOrderCount() {
		return totalOrderCount;
	}
	public void setTotalOrderCount(int totalOrderCount) {
		this.totalOrderCount = totalOrderCount;
	}
	public String getTotalValueCents() {
		return totalValueCents;
	}
	public void setTotalValueCents(String totalValueCents) {
		this.totalValueCents = totalValueCents;
	}
		
	
	public String getCinemaName() {
		return cinemaName;
	}
	public void setCinemaName(String cinemaName) {
		this.cinemaName = cinemaName;
	}
	public String getSessionName() {
		return sessionName;
	}
	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}
	public int getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(int paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Map<String, Object> getResults() {
		return results;
	}
	public void setResults(Map<String, Object> results) {
		this.results = results;
	}

	public String getShowTime() {
		return showTime;
	}
	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}
	public String getSessionShowTime() {
		return sessionShowTime;
	}
	public void setSessionShowTime(String sessionShowTime) {
		this.sessionShowTime = sessionShowTime;
	}
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	public int getFreeRightFlg() {
		return freeRightFlg;
	}
	public void setFreeRightFlg(int freeRightFlg) {
		this.freeRightFlg = freeRightFlg;
	}
	public String getCinemaType() {
		return cinemaType;
	}
	public void setCinemaType(String cinemaType) {
		this.cinemaType = cinemaType;
	}
	public String getMemberLevelId() {
		return memberLevelId;
	}
	public void setMemberLevelId(String memberLevelId) {
		this.memberLevelId = memberLevelId;
	}
	

	
}
