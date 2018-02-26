package com.cinker.model;

import java.io.Serializable;

public class Ticket implements Serializable{
	
	private static final long serialVersionUID = -6795124584552400664L;
	
	private String areaCategoryCode;//����������
	private String cinemaId;//Ӱ���ڲ����� 
	private String description;//Ʊ������
	private int priceInCents;//����
	private String ticketTypeCode;//Ʊ�����
	
	
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
