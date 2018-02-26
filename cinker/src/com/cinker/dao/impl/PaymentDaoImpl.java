package com.cinker.dao.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import com.cinker.dao.PaymentDao;
import com.cinker.model.Payment;
import com.cinker.model.UnsuccessRecord;

@SuppressWarnings("rawtypes")
@Repository
public class PaymentDaoImpl extends BaseDaoImpl implements PaymentDao{
	@Override
	public void savePayment(Payment payment) {
		// TODO Auto-generated method stub
		String sql="insert into cinker_order_payment(Type,Status,PaymentPrice,StartTime,OrderNumber) VALUES(?,?,?,?,?)";
		update(sql,payment.getType(),payment.getStatus(),payment.getPaymentPrice(),payment.getStartTime(),payment.getOrderNumber());
	}

	@Override
	public void updatePaymentStatus(int status,Date endTime,String orderNumber) {
		// TODO Auto-generated method stub
		String sql="update cinker_order_payment set Status=?,endTime=? where OrderNumber=?";
		update(sql,status,endTime,orderNumber);
	}

	@Override
	public List<Payment> getPaymentList(String orderNumber) {
		try{
			String sql = "select * from cinker_order_payment where OrderNumber = ?";
			List<Payment> paymentList=new ArrayList<Payment>();
			List<Map<String,Object>> resultSetList=jdbcTemplate.queryForList(sql,orderNumber);
			for(int i=0;i<resultSetList.size();i++){
				Map<String,Object> map=resultSetList.get(i);
				Payment payment=new Payment();
				if(null!=map.get("PaymentID"))
					payment.setPaymentID((Integer)map.get("PaymentID"));
				paymentList.add(payment);
			}
			return paymentList;
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

	@Override
	public void saveUnsuccessRecord(UnsuccessRecord unsuccessRecord) {
		// TODO Auto-generated method stub
		String sql="insert into cinker_order_unsuccess(orderNumber,inData,result,endTime) VALUES(?,?,?,?)";
		update(sql,unsuccessRecord.getOrderNumber(),unsuccessRecord.getInData(),unsuccessRecord.getResult(),unsuccessRecord.getEndTime());
	}
}
