package com.cinker.model;

import java.io.Serializable;

public class TicketType implements Serializable{
	

	private static final long serialVersionUID = -3064775347701202186L;
	
	private String orderId;
	private String ticketTypeCode;
	private int qty;
	private double priceInCents;
	private String bookingFeeOverride;
	
	public String getTicketTypeCode() {
		return ticketTypeCode;
	}
	public void setTicketTypeCode(String ticketTypeCode) {
		this.ticketTypeCode = ticketTypeCode;
	}
	
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public double getPriceInCents() {
		return priceInCents;
	}
	public void setPriceInCents(double priceInCents) {
		this.priceInCents = priceInCents;
	}
	public String getBookingFeeOverride() {
		return bookingFeeOverride;
	}
	public void setBookingFeeOverride(String bookingFeeOverride) {
		this.bookingFeeOverride = bookingFeeOverride;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	
	

}
