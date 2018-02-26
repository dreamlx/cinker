package com.cinker.model;

import java.io.Serializable;

public class Ticket implements Serializable{
	
	private static final long serialVersionUID = -6795124584552400664L;
	
	private String areaCategoryCode;//区域类别编码
	private String cinemaId;//影城内部编码 
	private String description;//票类名称
	private int priceInCents;//单价
	private String ticketTypeCode;//票类编码
	
	
	public String getAreaCategoryCode() {
		return areaCategoryCode;
	}
	public void setAreaCategoryCode(String areaCategoryCode) {
		this.areaCategoryCode = areaCategoryCode;
	}
	public String getCinemaId() {
		return cinemaId;
	}
	public void setCinemaId(String cinemaId) {
		this.cinemaId = cinemaId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getPriceInCents() {
		return priceInCents;
	}
	public void setPriceInCents(int priceInCents) {
		this.priceInCents = priceInCents;
	}
	public String getTicketTypeCode() {
		return ticketTypeCode;
	}
	public void setTicketTypeCode(String ticketTypeCode) {
		this.ticketTypeCode = ticketTypeCode;
	}

	
	

}
