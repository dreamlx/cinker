package com.cinker.model;

import java.io.Serializable;

public class OrderTicket implements Serializable{

	private static final long serialVersionUID = -4355028842022586239L;
	
	private int id;
	private String ticketTypeCode;
	private String priceInCents;
	private int ticketsNumber;
	private String orderNumber;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTicketTypeCode() {
		return ticketTypeCode;
	}
	public void setTicketTypeCode(String ticketTypeCode) {
		this.ticketTypeCode = ticketTypeCode;
	}
	public String getPriceInCents() {
		return priceInCents;
	}
	public void setPriceInCents(String priceInCents) {
		this.priceInCents = priceInCents;
	}
	public int getTicketsNumber() {
		return ticketsNumber;
	}
	public void setTicketsNumber(int ticketsNumber) {
		this.ticketsNumber = ticketsNumber;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	

}
