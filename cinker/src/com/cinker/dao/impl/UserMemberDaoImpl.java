package com.cinker.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import com.cinker.dao.UserMemberDao;
import com.cinker.model.Cinema;
import com.cinker.model.UserMember;
import com.cinker.model.UserVipMember;
import com.cinker.model.UserVipPayment;

@SuppressWarnings({ "rawtypes" })
@Repository
public class UserMemberDaoImpl extends BaseDaoImpl implements UserMemberDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<UserMember> getUserMember(String openID) {
		try {
			String sql = "select * from cinker_user_member where OpenID = ?";
			List<UserMember> userMemberList = new ArrayList<UserMember>();
			List<Map<String, Object>> resultSetList = jdbcTemplate
					.queryForList(sql, openID);
			for (int i = 0; i < resultSetList.size(); i++) {
				Map<String, Object> map = resultSetList.get(i);
				UserMember userMember = new UserMember();
				if (null != map.get("UserNumber"))
					userMember.setUserNumber((String) map.get("UserNumber"));
				if (null != map.get("OpenID"))
					userMember.setOpenID((String) map.get("OpenID"));
				if (null != map.get("UserNickName"))
					userMember
							.setUserNickName((String) map.get("UserNickName"));
				if (null != map.get("UserHeadImageUrl"))
					userMember.setUserHeadImageUrl((String) map
							.get("UserHeadImageUrl"));
				if (null != map.get("UserSex"))
					userMember.setUserSex((Integer) map.get("UserSex"));
				if (null != map.get("VistaMemberCardNumber"))
					userMember.setVistaMemberCardNumber((String) map.get("VistaMemberCardNumber"));
				userMemberList.add(userMember);
			}
			return userMemberList;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public void addUserMember(UserMember userMember) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO cinker_user_member(UserNumber,UserNickName,UserHeadImageUrl,UserSex,OpenID,LoginTime)VALUES(?,?,?,?,?,?)";
		update(sql,
				new Object[] { userMember.getUserNumber(),
						userMember.getUserNickName(),
						userMember.getUserHeadImageUrl(),
						userMember.getUserSex(), userMember.getOpenID(),
						userMember.getLoginTime() });
	}

	@Override
	public void updateToVip(UserVipMember userVipMember) {
		// TODO Auto-generated method stub
		String sql = "update cinker_user_member set UserName=?,Password=?,VistaMemberCardNumber=?,"
				+ "FirstName=?,LastName=?,MobilePhone=?,Email=?,Birthday=?,Sex=?,MemberLevelId=1,ClubID=1 where OpenID = ?";
		update(sql, userVipMember.getUserName(), userVipMember.getPassword(),
				userVipMember.getVistaMemberCardNumber(),
				userVipMember.getFirstName(), userVipMember.getLastName(),
				userVipMember.getPhone(), userVipMember.getEmail(),userVipMember.getBirthday(),
				userVipMember.getSex(),userVipMember.getOpenID());
	}
	
	@Override
	public String getVistaMemberCardNumber(String areaNumber) {
		String sql = "SELECT max(VistaMemberCardNumber) FROM cinker_user_member WHERE VistaMemberCardNumber REGEXP '^"+areaNumber +"'";
		List<Map<String, Object>> resultSetList = jdbcTemplate
		.queryForList(sql);
		Map<String, Object> map = resultSetList.get(0);	
		return (String)map.get("max(VistaMemberCardNumber)");		
	}

	@Override
	public String checkVistaMemberCardNumber(String openID) {
		String sql = "SELECT VistaMemberCardNumber FROM cinker_user_member where OpenID = ?";
		List<Map<String, Object>> resultSetList = jdbcTemplate
		.queryForList(sql,openID);
		if(resultSetList.size()>0){
			Map<String, Object> map = resultSetList.get(0);	
			return (String)map.get("VistaMemberCardNumber");
		}else
			return null;
	}

	@Override
	public UserVipMember getUserVipMember(String openID) {
		try {
			String sql = "select * from cinker_user_member where OpenID = ?";
			List<Map<String, Object>> resultSetList = jdbcTemplate
					.queryForList(sql, openID);
				Map<String, Object> map = resultSetList.get(0);
				UserVipMember userVipMember = new UserVipMember();
				if (null != map.get("UserNumber"))
					userVipMember.setUserNumber((String) map.get("UserNumber"));
				if (null != map.get("UserName"))
					userVipMember.setUserName((String) map.get("UserName"));
				if (null != map.get("Password"))
					userVipMember.setPassword((String) map.get("Password"));
				if (null != map.get("OpenID"))
					userVipMember.setOpenID((String) map.get("OpenID"));
				if (null != map.get("VistaMemberCardNumber"))
					userVipMember.setVistaMemberCardNumber((String) map.get("VistaMemberCardNumber"));
				if (null != map.get("UserNickName"))
					userVipMember
							.setUserNickName((String) map.get("UserNickName"));
				if (null != map.get("UserHeadImageUrl"))
					userVipMember.setUserHeadImageUrl((String) map
							.get("UserHeadImageUrl"));
				if (null != map.get("UserSex"))
					userVipMember.setUserSex((Integer) map.get("UserSex"));
				if (null != map.get("FirstName"))
					userVipMember.setFirstName((String) map.get("FirstName"));
				if (null != map.get("LastName"))
					userVipMember.setLastName((String) map.get("LastName"));
				if (null != map.get("MobilePhone"))
					userVipMember.setPhone((String) map.get("MobilePhone"));
				if (null != map.get("Email"))
					userVipMember.setEmail((String) map.get("Email"));
				if (null != map.get("Birthday"))
					userVipMember.setBirthday((String) map.get("Birthday"));
				if (null != map.get("Sex"))
					userVipMember.setSex((String) map.get("Sex"));
				if (null != map.get("ClubID"))
					userVipMember.setClubId((String) map.get("ClubID"));
				if (null != map.get("MemberLevelId"))
					userVipMember.setMemberLevelId((String) map.get("MemberLevelId"));
				
			System.out.println("Validate VistaVipMember datebase");
			return userVipMember;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public void addUserVipPayment(UserVipPayment userVipPayment) {
		String sql = "INSERT INTO cinker_vip_payment(OrderNumber,UserMember,RechargePrice,Status,StartRechargeTime,Type) values(?,?,?,?,?,?)";
		update(sql, new Object[]{userVipPayment.getOrderNumber(),userVipPayment.getUserMember(),userVipPayment.getRechargePrice(),userVipPayment.getStatus(),userVipPayment.getStartRechargeTime(),userVipPayment.getType()});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserVipPayment> getUserVipPaymentByOrderNumber(String orderNumber) {
		String sql = "SELECT * FROM cinker_vip_payment where OrderNumber = '" + orderNumber +"'";
		try {
			List<UserVipPayment> userVipPaymentList = jdbcTemplate.query(sql,new BeanPropertyRowMapper(UserVipPayment.class));
			return userVipPaymentList;
		}catch (EmptyResultDataAccessException e) {
			 return null;
		}
	}

	@Override
	public void updateUserVipPaymentByOrderNumber(int status,
			Date endChargeTime, String orderNumber) {
		String sql = "UPDATE cinker_vip_payment SET Status = ?,EndRechargeTime = ? WHERE OrderNumber = ?";
		update(sql, status,endChargeTime,orderNumber);
	}

	@Override
	public void updateUserVipPaymentTransactionIdByOrderNumber(
			String transactionId, String orderNumber) {
		String sql = "UPDATE cinker_vip_payment SET TransactionId = ?  WHERE OrderNumber = ?";
		update(sql, transactionId,orderNumber);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserVipMember> getUserVipMemberByOpenId(String openId) {
		String sql = "SELECT * FROM cinker_user_member where OpenID = '" + openId +"'";
		try {
			List<UserVipMember> userVipMemberList = jdbcTemplate.query(sql,new BeanPropertyRowMapper(UserVipMember.class));
			return userVipMemberList;
		}catch (EmptyResultDataAccessException e) {
			 return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserVipMember> getUserVipVistaCardNumber(String orderId) {
		String sql = "SELECT cum.VistaMemberCardNumber FROM cinker_user_member cum,cinker_vip_payment cvp WHERE cum.UserNumber = cvp.UserMember AND cvp.OrderNumber = '"+orderId+"'";
		try {
			return jdbcTemplate.query(sql, new BeanPropertyRowMapper(UserVipMember.class));
		}catch (EmptyResultDataAccessException e) {
			 return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserVipMember> getUserName(String phone) {
		String sql = "SELECT * FROM cinker_user_member WHERE UserName = '" + phone + "'";
		try {
			return jdbcTemplate.query(sql, new BeanPropertyRowMapper(UserVipMember.class));
		}catch (EmptyResultDataAccessException e) {
			 return null;
		}
	}

	@Override
	public void profile(UserVipMember userVipMember) {
		String sql = "UPDATE cinker_user_member SET UserName = ?,FirstName = ?,LastName = ?,Email = ?,BirthDay = ?,Sex = ? WHERE OpenID = ?";
		jdbcTemplate.update(sql,new Object[]{userVipMember.getUserName(),userVipMember.getFirstName(),userVipMember.getLastName(),userVipMember.getEmail(),userVipMember.getBirthday(),userVipMember.getSex(),userVipMember.getOpenID()});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserVipMember> getUserVistaCardNumber(String userNumber) {
		String sql = "SELECT * FROM cinker_user_member WHERE UserNumber = '"+userNumber+"'";
		try {
			return jdbcTemplate.query(sql, new BeanPropertyRowMapper(UserVipMember.class));
		}catch (EmptyResultDataAccessException e) {
			 return null;
		}
	}
	
	@Override
	public void updateUserVipLevelByOpenId(String memberLevelId,String openid) {
		String sql = "UPDATE cinker_user_member SET MemberLevelId = ?  WHERE OpenID = ?";
		update(sql, memberLevelId,openid);
		
	}
}
