package com.cinker.service;

import java.util.Date;
import java.util.List;

import com.cinker.model.Payment;
import com.cinker.model.UnsuccessRecord;

public interface PaymentService {
	public void savePayment(Payment payment);
	public void updatePaymentStatus(int status,Date endTime,String orderNumber);
	public List<Payment> getPaymentList(String orderNumber);
	public void saveUnsuccessRecord(UnsuccessRecord unsuccessRecord);
}
