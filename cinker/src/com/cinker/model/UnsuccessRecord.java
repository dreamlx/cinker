package com.cinker.model;

import java.io.Serializable;
import java.util.Date;

public class UnsuccessRecord implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;
	private String orderNumber;//支付的订单编号
	private String inData;//微信用户
	private String result;//支付总金额
	private Date  endTime;//支付开始时间
	
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
