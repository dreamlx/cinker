package com.cinker.model;

import java.io.Serializable;
import java.util.Date;

public class Payment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6634380618143287979L;
	public  static final int  PAYMENT_WECHAT=1;
	public  static final int  PAYMENT_ECARD=2;
	public  static final int  PAYMENT_STATUS_INIT=0;
	public  static final int  PAYMENT_STATUS_SUCCESS=1;
	public  static final int  PAYMENT_STATUS_FAIL=2;
	public  static final int  PAYMENT_STATUS_OVERTIME=3;
	private int paymentID;//���
	private int type;//֧������  1:΢��֧��  2����Ա��֧��
	private int status;//0:δ֧��  1��֧���ɹ� 2��֧��ʧ��
	private String paymentPrice;//֧���۸�
	private Date  startTime;//֧����ʼʱ��
	private Date  endTime;//֧������ʱ��
	private String orderNumber;//֧���Ķ������
	private String userNickName;//΢���û�
	private String totalValueCents;//֧���ܽ��
	private String transactionId;//��ˮ��
	
	
	public int getPaymentID() {
		return paymentID;
	}
	public void setPaymentID(int paymentID) {
		this.paymentID = paymentID;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getPaymentPrice() {
		return paymentPrice;
	}
	public void setPaymentPrice(String paymentPrice) {
		this.paymentPrice = paymentPrice;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	public String getTotalValueCents() {
		return totalValueCents;
	}
	public void setTotalValueCents(String totalValueCents) {
		this.totalValueCents = totalValueCents;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
}
