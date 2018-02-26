package com.cinker.service.impl;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cinker.dao.PaymentDao;
import com.cinker.model.Payment;
import com.cinker.model.UnsuccessRecord;
import com.cinker.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService{
	
	@Autowired
	PaymentDao paymentDao;

	@Override
	public void savePayment(Payment payment) {
		// TODO Auto-generated method stub
		paymentDao.savePayment(payment);
	}

	@Override
	public void updatePaymentStatus(int status, Date endTime, String orderNumber) {
		// TODO Auto-generated method stub
		paymentDao.updatePaymentStatus(status, endTime, orderNumber);
	}

	@Override
	public List<Payment> getPaymentList(String orderNumber) {
		// TODO Auto-generated method stub
		return paymentDao.getPaymentList(orderNumber);
	}

	@Override
	public void saveUnsuccessRecord(UnsuccessRecord unsuccessRecord) {
		// TODO Auto-generated method stub
		paymentDao.saveUnsuccessRecord(unsuccessRecord);
	}

}
