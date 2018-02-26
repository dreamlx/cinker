package com.cinker.model;

public class UserVipPayment {
	
	private String orderNumber;
	private String userMember;
	private String rechargePrice;
	private Integer status;
	private String startRechargeTime;
	private String endRechargeTime;
	private Integer type;
	private String transactionId;
	private Integer id;
	
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getUserMember() {
		return userMember;
	}
	public void setUserMember(String userMember) {
		this.userMember = userMember;
	}
	public String getRechargePrice() {
		return rechargePrice;
	}
	public void setRechargePrice(String rechargePrice) {
		this.rechargePrice = rechargePrice;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getStartRechargeTime() {
		return startRechargeTime;
	}
	public void setStartRechargeTime(String startRechargeTime) {
		this.startRechargeTime = startRechargeTime;
	}
	public String getEndRechargeTime() {
		return endRechargeTime;
	}
	public void setEndRechargeTime(String endRechargeTime) {
		this.endRechargeTime = endRechargeTime;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	

}
