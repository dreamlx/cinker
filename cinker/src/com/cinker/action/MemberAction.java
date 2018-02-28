package com.cinker.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cinker.constant.Constant;
import com.cinker.http.CinkerHttpService;
import com.cinker.model.FilmInfo;
import com.cinker.model.FilmOrder;
import com.cinker.model.Payment;
import com.cinker.model.UserMember;
import com.cinker.model.UserVipMember;
import com.cinker.model.UserVipPayment;
import com.cinker.service.CinemaService;
import com.cinker.service.FilmService;
import com.cinker.service.PaymentService;
import com.cinker.service.UserMemberService;

@RequestMapping(value="/vipMember")
public class MemberAction {
	
	private static Logger logger = Logger.getLogger(MemberAction.class);
	
	protected HttpServletRequest request;
	@Autowired
	UserMemberService userMemberService;
	@Autowired
	FilmService filmService;
	@Autowired
	CinemaService cinemaService;
	@Autowired
	PaymentService paymentService;
	
	@RequestMapping(value="/completeOrder")
	public String completeOrder(String outTradeNo,String filmName,String sessionID, String totalPrice, Model model){
		UserMemberAction userMemberAction = new UserMemberAction();
		HttpSession session = request.getSession();
		String openid = "";
		UserMember userMember = null;
		if(session.getAttribute(Constant.SESSION_ATTRIBUTE_KEY) != null){
			userMember = (UserMember) session.getAttribute(Constant.SESSION_ATTRIBUTE_KEY);
			openid = userMember.getOpenID();
		}
	    List<Payment> payList = paymentService.getPaymentList(outTradeNo);
	    if(payList == null || payList.size()<1){
		    Payment payment = new Payment();
	     	payment.setType(Payment.PAYMENT_ECARD);
	     	payment.setStatus(Payment.PAYMENT_STATUS_INIT);
	     	payment.setStartTime(new Date());
	     	payment.setPaymentPrice(totalPrice+"");
	     	logger.info("payment price:"+payment.getPaymentPrice());
	     	payment.setOrderNumber(outTradeNo);
	     	paymentService.savePayment(payment);
	     	List<Payment> paymentList = paymentService.getPaymentList(outTradeNo);
	     	paymentList = paymentService.getPaymentList(outTradeNo);
	    	//更新订单表里的PaymentID
	    	if(paymentList!=null && paymentList.size()>0){
	    		Payment payment_ = paymentList.get(0);
	    		int    paymentID = payment_.getPaymentID();
	    		filmService.updateOrderPaymentID(paymentID,outTradeNo);
	    	}
	    }
		UserVipMember userVipMember = userMemberService.getUserVipMember(openid);
		String cinemaType = "Pictures";
		String result = userMemberAction.validateVip(userVipMember, outTradeNo,cinemaType);
		JSONObject resultJson = JSONObject.fromObject(result);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (0 == resultJson.getInt("Result")) {
			List<UserVipPayment> userVipPaymentList = userMemberService.getUserVipPaymentByOrderNumber(outTradeNo);
			if(userVipPaymentList == null || userVipPaymentList.size()<1){
				UserVipPayment userVipPayment = new UserVipPayment();
				userVipPayment.setEndRechargeTime("");
				userVipPayment.setOrderNumber(outTradeNo);
				userVipPayment.setRechargePrice(totalPrice);
				userVipPayment.setStartRechargeTime(sdf.format(new Date()));
				userVipPayment.setStatus(Payment.PAYMENT_STATUS_INIT);
				userVipPayment.setTransactionId("");
				userVipPayment.setType(1);
				userVipPayment.setUserMember(userMember.getUserNumber());
				userMemberService.addUserVipPayment(userVipPayment);
				logger.info("保存userVipPayment数据");
			}
		}
		String completeOrderResult = vipCompleteOrder(userVipMember, outTradeNo, totalPrice,"","");
		JSONObject completeJson = JSONObject.fromObject(completeOrderResult);
		if(0 == completeJson.getInt("Result")){
			//取票码
			String bookingNumber=resultJson.getString("VistaBookingNumber");
			//验证码
			String bookingId=resultJson.getString("VistaBookingId");
			//Vista交易单号
			String transNumber = resultJson.getString("VistaTransNumber");
			//更改本地数据库的订单信息
			filmService.updateOrderSuccess(FilmOrder.ORDER_SUCCESS,new Date(),bookingNumber,bookingId,transNumber,outTradeNo);
			userMemberService.updateUserVipPaymentByOrderNumber(Payment.PAYMENT_STATUS_SUCCESS,new Date(), outTradeNo);
			paymentService.updatePaymentStatus(Payment.PAYMENT_STATUS_FAIL,new Date(),outTradeNo);
			FilmOrder filmOrder = filmService.getFilmOrder(outTradeNo);
			String[] str = filmOrder.getShowTime().split(" ");
			filmOrder.setSessionShowTime(str[0]);
			filmOrder.setShowTime(str[1]);
			List<FilmInfo> filmInfo = cinemaService.getFilmInfo();
				for(FilmInfo info:filmInfo){
					if(info.getFilmId().equals(filmOrder.getScheduledFilmId()))	{
						filmOrder.setEnglishName(info.getEnglishName());
						break;
					}
				}
			 model.addAttribute("filmOrder",filmOrder);
		}
		return "film_cashier_result";
	}
	
	@RequestMapping(value="/refundBooking")
	public String refundBooking(String orderNumber){
		FilmOrder filmOrder = filmService.getFilmOrder(orderNumber);
		
		return null;
	}
	
	public String vipCompleteOrder(UserVipMember userVipMember ,String outTradeNo, String totalPrice,
			String CardExpiryMonth,String CardExpiryYear){
		String requestUrl = Constant.CONNECT_API_URL + "WSVistaWebClient/RESTTicketing.svc/order/payment";
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("UserSessionId", outTradeNo);
		JSONArray paymentInfoArray = new JSONArray();
		JSONObject paymentObject = new JSONObject();
		paymentObject.put("CardNumber", userVipMember.getVistaMemberCardNumber());
		paymentObject.put("CardType", "LOYAL");
		paymentObject.put("PaymentValueCents", totalPrice);
		paymentObject.put("PaymentTenderCategory", "LOYAL");
		paymentObject.put("CardExpiryMonth", "");
		paymentObject.put("CardExpiryYear", "");
		paymentInfoArray.add(paymentObject);
		jsonParam.put("PaymentInfo", paymentInfoArray);
		jsonParam.put("PerformPayment", true);
		jsonParam.put("OptionalClientClass",Constant.OPTIONAL_CLIENT_CLASS);
		jsonParam.put("OptionalClientId",Constant.OPTIONAL_CLIENT_ID);
		jsonParam.put("OptionalClientName",Constant.OPTIONAL_CLIENT_NAME);
		String completeOrderResult = CinkerHttpService.httpPostRequest(requestUrl, jsonParam.toString(),Constant.CONNECT_API_TOKEN);
		return completeOrderResult;
	}

	public String refundMoney(FilmOrder filmOrder){
		
		
		return null;
	}

}
