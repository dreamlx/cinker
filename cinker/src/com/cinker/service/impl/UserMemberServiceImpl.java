package com.cinker.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinker.constant.Constant;
import com.cinker.dao.UserDao;
import com.cinker.dao.UserMemberDao;
import com.cinker.model.User;
import com.cinker.model.UserMember;
import com.cinker.model.UserVipMember;
import com.cinker.model.UserVipPayment;
import com.cinker.service.UserMemberService;
import com.cinker.service.UserService;

@Service
public class UserMemberServiceImpl implements UserMemberService{

	@Autowired
	UserMemberDao userMemberDao;

	@Override
	public List<UserMember> getUserMember(String openID) {
		// TODO Auto-generated method stub
		return userMemberDao.getUserMember(openID);
	}

	@Override
	public void addUserMember(UserMember userMember) {
		// TODO Auto-generated method stub
		userMemberDao.addUserMember(userMember);
	}

	@Override
	public void udateToVip(UserVipMember userVipMember) {
		// TODO Auto-generated method stub
		userMemberDao.updateToVip(userVipMember);
	}

	@Override
	public String getVistaMemberCardNumber(String areaNumber) {
		// TODO Auto-generated method stub
		return userMemberDao.getVistaMemberCardNumber(areaNumber);
	}

	@Override
	public String checkVistaMemberCardNumber(String openID) {
		// TODO Auto-generated method stub
		return userMemberDao.checkVistaMemberCardNumber(openID);
	}

	@Override
	public UserVipMember getUserVipMember(String openID) {
		// TODO Auto-generated method stub
		return userMemberDao.getUserVipMember(openID);
	}

	@Override
	public void addUserVipPayment(UserVipPayment userVipPayment) {
		userMemberDao.addUserVipPayment(userVipPayment);
	}

	@Override
	public List<UserVipPayment> getUserVipPaymentByOrderNumber(String orderNumber) {
		// TODO Auto-generated method stub
		return userMemberDao.getUserVipPaymentByOrderNumber(orderNumber);
	}

	@Override
	public void updateUserVipPaymentByOrderNumber(int status,
			Date endChargeTime, String orderNumber) {
		userMemberDao.updateUserVipPaymentByOrderNumber(status, endChargeTime, orderNumber);
	}

	@Override
	public void updateUserVipPaymentTransactionIdByOrderNumber(
			String transactionId, String orderNumber) {
		userMemberDao.updateUserVipPaymentTransactionIdByOrderNumber(transactionId, orderNumber);
		
	}

	@Override
	public List<UserVipMember> getUserVipMemberByOpenId(String openId) {
		return userMemberDao.getUserVipMemberByOpenId(openId);
	}

	@Override
	public List<UserVipMember> getUserVipVistaCardNumber(String orderId) {
		return userMemberDao.getUserVipVistaCardNumber(orderId);
	}

	@Override
	public List<UserVipMember> getUserName(String phone) {
		return userMemberDao.getUserName(phone);
	}

	@Override
	public void profile(UserVipMember userVipMember) {
		userMemberDao.profile(userVipMember);
	}

	@Override
	public List<UserVipMember> getUserVistaCardNumber(String userNumber) {
		return userMemberDao.getUserVistaCardNumber(userNumber);
	}

	@Override
	public void updateUserVipLevelByOpenId(String headOfficeItemCode,String openid) {
		String memberLevelId = "";
		//摩登会员
		if (Constant.HEAD_OFFICE_ITEM_CODE_1.equals(headOfficeItemCode)){
			memberLevelId = "3";
		//经典会员
		}else if (Constant.HEAD_OFFICE_ITEM_CODE_2.equals(headOfficeItemCode)){
			memberLevelId = "2";
		//超级经典
		}else if (Constant.HEAD_OFFICE_ITEM_CODE_3.equals(headOfficeItemCode)){
			memberLevelId = "4";
		//超级摩登
		}else if (Constant.HEAD_OFFICE_ITEM_CODE_4.equals(headOfficeItemCode)){
			memberLevelId = "6";
		}
		
		userMemberDao.updateUserVipLevelByOpenId(memberLevelId,openid);
		
	}
}
