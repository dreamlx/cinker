package com.cinker.model;

import java.io.Serializable;
import java.util.Date;

public class UnsuccessRecord implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;
	private String orderNumber;//֧���Ķ������
	private String inData;//΢���û�
	private String result;//֧���ܽ��
	private Date  endTime;//֧����ʼʱ��
	
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getInData() {
		return inData;
	}
	public void setInData(String inData) {
		this.inData = inData;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
