package com.cinker.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class FilmOrders implements Serializable{
	private static final long serialVersionUID = 5337212652856884982L;
	public  static final int ORDER_SUCCESS = 1;
	private int id;
	private String orderNumber;
	private String scheduledFilmId;
	private String filmTitle;
	private String cinemaId;
	private String cinemaName;
	private String sessionId;
	private String sessionName;
	private String showTime;
	private String areaCategoryCode;
	private int status;
	private int totalOrderCount;
	private String totalValueCents;
	private String unitPrice;
	private String startTime;
	private String endTime;	
	private String userNumber;
	private int paymentID;
	private String bookingNumber;
	private String bookingID;
	// vista½»Ò×ºÅ
	private String vistaTransNumber;
	private String seats;
	private int orderType;
	private String userNickName;
	private String userName;
	private String mobilePhone;
	private String memberLevelId;
	
	private List<TicketType> ticketTypes = new ArrayList<TicketType>();
	private List<SelectedSeat> selectedSeats = new ArrayList<SelectedSeat>();
	
	
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public int getOrderType() {
		return orderType;
	}
	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getSeats() {
		return seats;
	}
	public void setSeats(String seats) {
		this.seats = seats;
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
		
	public String getShowTime() {
		return showTime;
	}
	public void setShowTime(String showTime) {
		this.showTime = showTime;
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
	public int getPaymentID() {
		return paymentID;
	}
	public void setPaymentID(int paymentID) {
		this.paymentID = paymentID;
	}
	public String getMemberLevelId() {
		return memberLevelId;
	}
	public void setMemberLevelId(String memberLevelId) {
		this.memberLevelId = memberLevelId;
	}
	public String getVistaTransNumber() {
		return vistaTransNumber;
	}
	public void setVistaTransNumber(String vistaTransNumber) {
		this.vistaTransNumber = vistaTransNumber;
	}
	public String getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	
	
}
