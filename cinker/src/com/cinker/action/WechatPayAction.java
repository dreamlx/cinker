package com.cinker.action;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cinker.constant.Constant;
import com.cinker.http.CinkerHttpService;
import com.cinker.model.FilmInfo;
import com.cinker.model.FilmOrder;
import com.cinker.model.Payment;
import com.cinker.model.UserVipMember;
import com.cinker.service.CinemaService;
import com.cinker.service.FilmService;
import com.cinker.service.PaymentService;
import com.cinker.service.UserMemberService;
import com.cinker.wechatpay.util.CinkerWechatPay;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
@Controller
@RequestMapping("/wechat1")
public class WechatPayAction{
	@Autowired
	protected HttpServletResponse response;
	private static Logger logger = Logger.getLogger(WechatPayAction.class);
	@Autowired
	protected PaymentService paymentService;
	@Autowired
	protected FilmService filmService;
	@Autowired
	protected CinemaService cinemaService;
	@Autowired
	protected UserMemberService userMemberService;
	@RequestMapping(value = "/getQRcode", method = {GET,POST })
	public String getQRcode(String outTradeNo,String filmName,String sessionID,String totalPrice,Model model){
		String content="";
        try {
        	CinkerWechatPay cinkerWechatPay=new CinkerWechatPay();
        	//测试一分钱
        	//totalPrice="0.01";
        	content=cinkerWechatPay.getImageUrl(filmName,outTradeNo,sessionID,(int)((Double.valueOf(totalPrice)*100)));
        	//如果生成二维码成功，则生成该订单一条对于的支付信息
        	List<Payment> paymentList=paymentService.getPaymentList(outTradeNo);
        	if(!StringUtils.isEmpty(content)&& (paymentList==null || paymentList.size()<1)){
        		Payment payment=new Payment();
            	payment.setType(Payment.PAYMENT_WECHAT);
            	payment.setStatus(Payment.PAYMENT_STATUS_INIT);
            	payment.setStartTime(new Date());
            	payment.setPaymentPrice(totalPrice);
            	logger.info("payment price:"+payment.getPaymentPrice());
            	payment.setOrderNumber(outTradeNo);
            	paymentService.savePayment(payment);
        	}
        	paymentList=paymentService.getPaymentList(outTradeNo);
        	//更新订单表里的PaymentID
        	if(paymentList!=null && paymentList.size()>0){
        		Payment payment=paymentList.get(0);
        		int    paymentID=payment.getPaymentID();
        		filmService.updateOrderPaymentID(paymentID,outTradeNo);
        	}
         } catch (Exception e) {
            e.printStackTrace();
            logger.error("Wechat get QRCode Server Error");
         }
         model.addAttribute("content",content);
         model.addAttribute("tradeNo",outTradeNo);
         return "wechat_pay_show";
	}
	@SuppressWarnings({ "rawtypes","unchecked"})
	@RequestMapping(value = "/showQRcode", method = { GET, POST })
	@ResponseBody
	public String showQRcode(String content,Model model){
        try {
        	if(!StringUtils.isEmpty(content)){
        		 MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                 Hashtable hints = new Hashtable();
                 hints.put(EncodeHintType.CHARACTER_SET,"UTF-8");
                 BitMatrix bitMatrix = multiFormatWriter.encode(content,BarcodeFormat.QR_CODE,300,300,hints);
                 MatrixToImageWriter.writeToStream(bitMatrix,"jpg",response.getOutputStream());
        	}
        	else{
        		logger.error("Wechat get QRCode is null");
        		return "获取二维码失败";
        	}
         } catch (Exception e) {
            e.printStackTrace();
            logger.error("Wechat get QRCode Server Error");
         }
         return null;
	}
	@RequestMapping(value = "/queryOrder", method = {GET})
	public @ResponseBody int queryOrder(String tradeno,Model model){
		 logger.info("Wechat pay query order tradeno is "+tradeno);
		 CinkerWechatPay cinkerWechatPay=new CinkerWechatPay();
		 String queryOrderResult="";
		 try {
			queryOrderResult=cinkerWechatPay.queryOrder(tradeno);
		} catch (Exception e) {
			logger.error("Wechat pay query order error");
			e.printStackTrace();
		}
		int orderStatus=0;
		if(StringUtils.isEmpty(queryOrderResult)){
			orderStatus=0;
		}
		else{
			String parseResult=parseQueryOrderXml(queryOrderResult);
			if(StringUtils.isEmpty(parseResult))
				orderStatus=0;
			else{
				if("SUCCESS".equals(parseResult))
					orderStatus=1;
				else
					orderStatus=0;
			}
		}
         return orderStatus;
	}
	@RequestMapping(value = "/orderPaySuccess", method ={GET})
	public String orderPaySuccess(String tradeno,Model model){
		 logger.info("Wechat pay success order tradeno is "+tradeno);
		 //订单支付成功 调用Vista的完成订单接口并且更新本地数据库的payment和order表数据
		 //更新支付信息
		 paymentService.updatePaymentStatus(Payment.PAYMENT_STATUS_SUCCESS,new Date(),tradeno);
		 FilmOrder filmOrder=filmService.getFilmOrder(tradeno);
		 //调用Vista的完成订单接口
		 String result = completeOrderFromVista(tradeno, filmOrder);
		 JSONObject resultJson=JSONObject.fromObject(result);		
		 if(resultJson.getInt("Result")==0){
			 //取票码
			 String bookingNumber=resultJson.getString("VistaBookingNumber");
			 //验证码
			 String bookingId=resultJson.getString("VistaBookingId");
			 //更改本地数据库的订单信息
			 filmService.updateOrderSuccess(FilmOrder.ORDER_SUCCESS,new Date(),bookingNumber,bookingId,tradeno);
		 }
		 filmOrder=filmService.getFilmOrder(tradeno);
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
         return "film_cashier_result";
	}
	private String parseQueryOrderXml(String queryResult){
		Document document=null;
		String tradeState="";
		try {
			document=DocumentHelper.parseText(queryResult);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(document!=null){
			Element root=document.getRootElement();
			if(root!=null){
				String returnCode=root.element("return_code").getText();
				if("SUCCESS".equals(returnCode)){
					String resultCode=root.element("result_code").getText();
					if("SUCCESS".equals(resultCode)){
						tradeState=root.element("trade_state").getText();
					}
				}
			}
		}
		return tradeState;
	}

	public String completeOrderFromVista(String tradeno,FilmOrder filmOrder){
		String cinemaType = filmService.getCinema(filmOrder.getCinemaId()).getType();
		 String connectapiurl;
		 String connectapitoken;
		 String mobilePhone = "";
		 String userNickName = "";
		 
		 List<UserVipMember> userVipMember = userMemberService.getUserVistaCardNumber(filmOrder.getUserNumber());
		 if (userVipMember != null && userVipMember.size() > 0) {
			 if (!StringUtils.isEmpty(userVipMember.get(0).getMobilePhone())) {
				 mobilePhone = userVipMember.get(0).getMobilePhone();
			 }
			 if (!StringUtils.isEmpty(userVipMember.get(0).getUserNickName())) {
				 try {
					 userNickName = URLDecoder.decode(userVipMember.get(0).getUserNickName(), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			 }
		 }

		 if ("Pictures".equals(cinemaType)){
		 	connectapiurl = Constant.CONNECT_API_URL;
		 	connectapitoken = Constant.CONNECT_API_TOKEN;
		 } else {
		 	connectapiurl = Constant.CONNECT_API_URL_C;
		 	connectapitoken = Constant.CONNECT_API_TOKEN_C;
		 }
		 
		 String vistaMemberCardNumber ="";
		 if (!StringUtils.isEmpty(filmOrder.getUserNumber())) {
			 vistaMemberCardNumber = userMemberService.getUserVistaCardNumber(filmOrder.getUserNumber()).get(0).getVistaMemberCardNumber();
		 }
		 
		 if (StringUtils.isEmpty(vistaMemberCardNumber)) {
			 vistaMemberCardNumber = "9999 9999 9999 9999";
		 }

		 JSONObject jsonParam=new JSONObject();
		 jsonParam.put("UserSessionId",tradeno);
		 JSONObject paymentJsonParam=new JSONObject();
		 paymentJsonParam.put("CardNumber",vistaMemberCardNumber);
		 
		 if ("9999 9999 9999 9999".equals(vistaMemberCardNumber)){
			 paymentJsonParam.put("CardType","GZH");
			 //订单支付金额
			 paymentJsonParam.put("PaymentValueCents",(int)(Double.valueOf(filmOrder.getTotalValueCents())*100));
			 //paymentJsonParam.put("PaymentTenderCategory","CreditCard");
			 paymentJsonParam.put("PaymentTenderCategory","GZH");
		 }else {
			 paymentJsonParam.put("CardType","LOYAL");
			 //订单支付金额
			 paymentJsonParam.put("PaymentValueCents",(int)(Double.valueOf(filmOrder.getTotalValueCents())*100));
			 //paymentJsonParam.put("PaymentTenderCategory","CreditCard");
			 paymentJsonParam.put("PaymentTenderCategory","LOYAL");
			 paymentJsonParam.put("CardExpiryMonth","07");
			 paymentJsonParam.put("CardExpiryYear","2018");
		 }

//		 paymentJsonParam.put("PerformPayment",false);
		 jsonParam.put("PaymentInfo",paymentJsonParam);
		 		 
		 jsonParam.put("CustomerPhone",mobilePhone);
		 jsonParam.put("CustomerEmail","");
		 jsonParam.put("CustomerName",userNickName);
		 jsonParam.put("OptionalClientClass",Constant.OPTIONAL_CLIENT_CLASS);
		 jsonParam.put("OptionalClientId",Constant.OPTIONAL_CLIENT_ID);
		 jsonParam.put("OptionalClientName",Constant.OPTIONAL_CLIENT_NAME);
		 
		 String result=CinkerHttpService.httpPostRequest(connectapiurl+"WSVistaWebClient/RestTicketing.svc/order/payment",jsonParam.toString(),connectapitoken);
		 logger.info("Vista complete order request:"+jsonParam.toString());
		 logger.info("Vista complete order response:"+ result);		 
		 return result;
		
	}
}
