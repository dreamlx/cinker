package com.cinker.dao;
import java.util.Date;
import java.util.List;

import com.cinker.model.UserMember;
import com.cinker.model.UserVipMember;
import com.cinker.model.UserVipPayment;
public interface UserMemberDao {

	public List<UserMember> getUserMember(String openID);
	
	public void addUserMember(UserMember userMember);

	public void updateToVip(UserVipMember userVipMember);

	public String getVistaMemberCardNumber(String areaNumber);

	public String checkVistaMemberCardNumber(String openID);

	public UserVipMember getUserVipMember(String openID); 
	
	public void addUserVipPayment(UserVipPayment userVipPayment);
	
	public List<UserVipPayment> getUserVipPaymentByOrderNumber(String orderNumber);
	
	public List<UserVipMember> getUserVipMemberByOpenId(String openId);
	
	public void updateUserVipPaymentByOrderNumber(int status,Date endChargeTime,String orderNumber);
	
	public void updateUserVipPaymentTransactionIdByOrderNumber(String transactionId,String orderNumber);
	
	public List<UserVipMember> getUserVipVistaCardNumber(String orderId);
	
	public List<UserVipMember> getUserName(String phone);
	
	public void profile(UserVipMember userVipMember);
	
	public List<UserVipMember> getUserVistaCardNumber(String userNumber);
	
	public void updateUserVipLevelByOpenId(String memberLevelId,String openid);
}
