package com.cinker.action;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cinker.constant.Constant;
import com.cinker.http.CinkerHttpService;
import com.cinker.model.ActivityFilm;
import com.cinker.model.ActivityPersonal;
import com.cinker.model.FilmInfo;
import com.cinker.model.FilmOrder;
import com.cinker.model.Payment;
import com.cinker.model.UnsuccessRecord;
import com.cinker.model.UserMember;
import com.cinker.model.UserVipMember;
import com.cinker.model.UserVipPayment;
import com.cinker.service.ActivityService;
import com.cinker.service.CinemaService;
import com.cinker.service.FilmService;
import com.cinker.service.PaymentService;
import com.cinker.service.UserMemberService;
import com.cinker.wechat.PayCommonUtil;
import com.cinker.wechat.WxPayUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
@Controller
@RequestMapping("/wechat")
public class WechatAction {

	private static Logger logger = Logger.getLogger(WechatAction.class);
	
	@Autowired
	protected PaymentService paymentService;
	@Autowired
	protected FilmService filmService;	
	@Autowired
	ActivityService activityService;
	@Autowired
	protected CinemaService cinemaService;	
	@Autowired
	protected UserMemberService userMemberService;
	@Autowired
	protected HttpServletRequest request;
	@Autowired
	protected HttpServletResponse response;
	@Value("${APPID}")
	private  String APPID;
	@Value("${MCH_ID}")
	private String MCH_ID;
	@Value("${NOTIFY_URL}")
	private String NOTIFY_URL;
	@Value("${API_KEY}")
	private String API_KEY;
	@RequestMapping("/pay")
	 public String wechatPay(String outTradeNo,String filmName,String sessionID, String totalPrice, Model model) throws Exception {
		logger.info("三克订单支付开始...");
		HttpSession session = request.getSession();
		String openid = "";
		String vistaMemberCardNumber = "9999 9999 9999 9999";
		String restTicketsFlag = "0";
		FilmOrder filmOrder = filmService.getFilmOrder(outTradeNo);
		if(session.getAttribute(Constant.SESSION_ATTRIBUTE_KEY) != null){
			UserMember userMember = (UserMember) session.getAttribute(Constant.SESSION_ATTRIBUTE_KEY);
			openid = userMember.getOpenID();
			vistaMemberCardNumber = userMember.getVistaMemberCardNumber();
			
			// check the count of VIP can buy tickets 2017/9/5
			String orderUserNumber = userMember.getUserNumber();
			String cinemaType = filmService.getCinema(filmOrder.getCinemaId()).getType();
			int restTicketsCount = filmService.getRestTickets(orderUserNumber,filmOrder.getMemberLevelId(),filmOrder.getCinemaId(),cinemaType,filmOrder.getSessionId(),"",filmOrder.getShowTime());
			int orderTicketsCount =  filmOrder.getTotalOrderCount();
			
			if (orderTicketsCount > restTicketsCount) {
				logger.error("ticketsCount is more than the maximum value when pay for the order");
				restTicketsFlag = "1";
				model.addAttribute("out_trade_no", "");
			    model.addAttribute("appid","");
			    model.addAttribute("timeStamp", "");
			    model.addAttribute("nonceStr", "");
			    model.addAttribute("openid","");
			    model.addAttribute("paySign", "");
			    model.addAttribute("packageValue", "");
			    model.addAttribute("vistaMemberCardNumber", "");
			    model.addAttribute("restTicketsFlag", restTicketsFlag);
			    return "wxpay";
			}
		}
		String timeStamp=PayCommonUtil.create_timestamp();
	    String nonceStr=PayCommonUtil.create_nonce_str();
	    //totalPrice = "0.01";
	    String prepayId = WxPayUtil.unifiedorder(APPID,MCH_ID,NOTIFY_URL,API_KEY,filmName,outTradeNo, openid, totalPrice,filmOrder.getCinemaId());
	    logger.info(prepayId);
	    SortedMap<Object,Object> signParams = new TreeMap<Object,Object>();  
	    signParams.put("appId",APPID); 
	    signParams.put("nonceStr",nonceStr);  
	    signParams.put("package", "prepay_id=" + prepayId);  
	    signParams.put("timeStamp", timeStamp);  
	    signParams.put("signType", "MD5");
	    String sign = PayCommonUtil.createSign(signParams,API_KEY);
	    logger.info(sign);
	    //----------------------------------------------------------------------------------------------
	    logger.info("保存支付信息...");
	    List<Payment> payList = paymentService.getPaymentList(outTradeNo);
	    if(payList.size()<1 || payList == null){
		    Payment payment = new Payment();
	     	payment.setType(Payment.PAYMENT_WECHAT);
	     	payment.setStatus(Payment.PAYMENT_STATUS_INIT);
	     	payment.setStartTime(new Date());
	     	payment.setPaymentPrice(totalPrice);
	     	logger.info("payment price:"+payment.getPaymentPrice());
	     	payment.setOrderNumber(outTradeNo);
	     	paymentService.savePayment(payment);
	     	List<Payment> paymentList=paymentService.getPaymentList(outTradeNo);
	    	//更新订单表里的PaymentID
	    	if(paymentList!=null && paymentList.size()>0){
	    		Payment payment_ = paymentList.get(0);
	    		int    paymentID = payment_.getPaymentID();
	    		filmService.updateOrderPaymentID(paymentID,outTradeNo);
	    	}
	    }
     	//----------------------------------------------------------------------------------------------
	    model.addAttribute("out_trade_no", outTradeNo);
	    model.addAttribute("appid",APPID);
	    model.addAttribute("timeStamp", timeStamp);
	    model.addAttribute("nonceStr", nonceStr);
	    model.addAttribute("openid",openid);
	    model.addAttribute("paySign", sign);
	    model.addAttribute("packageValue", "prepay_id=" + prepayId);
	    model.addAttribute("vistaMemberCardNumber", vistaMemberCardNumber);
	    model.addAttribute("restTicketsFlag", restTicketsFlag);
	    logger.info("三克订单支付结束...");
	    return "wxpay";
		
	}
	
	@RequestMapping(value = "/orderPaySuccess", method ={GET})
	public String orderPaySuccess(String tradeno,String vistaMemberCardNumber,Model model){
		 logger.info("微信订单查询单号：" + tradeno);
		 String queryOrderResult = "";
		 try {			 
			queryOrderResult = WxPayUtil.queryOrder(APPID,MCH_ID,API_KEY,tradeno);			
		 } catch (Exception e) {
			logger.error("微信订单支付失败订单号：" + tradeno);
			e.printStackTrace();
		 }
		 int orderStatus=0;
		 if(StringUtils.isEmpty(queryOrderResult)){
			orderStatus=0;
		 }else{
			 String parseResult= parseQueryOrderXml(queryOrderResult);
			 if(StringUtils.isEmpty(parseResult)){
				 orderStatus = 0;
			 }else{
				 if("SUCCESS".equals(parseResult)){
					 orderStatus = 1;
				 }
			 }
		 }
		 
		 FilmOrder filmOrderTest = filmService.getFilmOrder(tradeno);
		 
		 if(orderStatus > 0 && filmOrderTest.getStatus() == 0){
			 logger.info("微信支付成功订单号： "+tradeno);
			 //订单支付成功 调用Vista的完成订单接口并且更新本地数据库的payment和order表数据
			 //更新支付信息		
			 paymentService.updatePaymentStatus(Payment.PAYMENT_STATUS_SUCCESS,new Date(),tradeno);

			 FilmOrder filmOrder = filmService.getFilmOrder(tradeno);
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
				 if (StringUtils.isEmpty(vistaMemberCardNumber)) {
					 vistaMemberCardNumber = userVipMember.get(0).getVistaMemberCardNumber();
				 }
			 }
			 
			 if ("Pictures".equals(cinemaType)){
			 	connectapiurl = Constant.CONNECT_API_URL;
			 	connectapitoken = Constant.CONNECT_API_TOKEN;
			 } else {
			 	connectapiurl = Constant.CONNECT_API_URL_C;
			 	connectapitoken = Constant.CONNECT_API_TOKEN_C;
			 }

			 //调用Vista的完成订单接口
			 JSONObject jsonParam = new JSONObject();
			 jsonParam.put("UserSessionId",tradeno);
			 JSONObject paymentJsonParam = new JSONObject();
			 //PaymentInfo
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
			 jsonParam.put("PaymentInfo",paymentJsonParam);

/*				if ("9999 9999 9999 9999".equals(vistaMemberCardNumber)){
					jsonParam.put("PerformPayment",false);
				}else{
					jsonParam.put("PerformPayment",true);
				}*/

			 jsonParam.put("CustomerPhone",mobilePhone);
			 jsonParam.put("CustomerEmail","");
			 jsonParam.put("CustomerName",userNickName);
			 jsonParam.put("OptionalClientClass",Constant.OPTIONAL_CLIENT_CLASS);
			 jsonParam.put("OptionalClientId",Constant.OPTIONAL_CLIENT_ID);
			 jsonParam.put("OptionalClientName",Constant.OPTIONAL_CLIENT_NAME);
			 String result = CinkerHttpService.httpPostRequest(connectapiurl+"WSVistaWebClient/RestTicketing.svc/order/payment",jsonParam.toString(),connectapitoken);
			 logger.info("PaySuccess方法中,Vista complete order request:"+jsonParam.toString());
			 logger.info("PaySuccess方法中,Vista complete order response:"+ result);
			 JSONObject resultJson = JSONObject.fromObject(result);
			 if(resultJson.getInt("Result") == 0){
				 //取票码
				 String bookingNumber = resultJson.getString("VistaBookingNumber");
				 //验证码
				 String bookingId = resultJson.getString("VistaBookingId");
				 //Vista交易单号
				 String vistaTransNumber = resultJson.getString("VistaTransNumber");
				 //更改本地数据库的订单信息
				 filmService.updateOrderSuccess(FilmOrder.ORDER_SUCCESS,new Date(),bookingNumber,bookingId,vistaTransNumber,tradeno);
			 } else {
				 logger.info("******PaySuccess方法中,完成订单失败,request =["+ jsonParam.toString() + "]");
				 logger.info("******PaySuccess方法中,完成订单失败,Result =["+ result + "]");
			 }
			 filmOrder = filmService.getFilmOrder(tradeno);
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
			 model.addAttribute("result",1);
		 }else if (orderStatus > 0 && filmOrderTest.getStatus() != 0){
			 model.addAttribute("filmOrder",filmOrderTest);
			 model.addAttribute("result",1);
		 }
		 else{
			 model.addAttribute("result",0);
		 }
		 
         return "film_cashier_result";
	}

	@RequestMapping("/freePay")
	 public String wechatFreePay(String outTradeNo,String filmName,String sessionID, String totalPrice, Model model) throws Exception {
		logger.info("三克免费和储值卡订单支付开始...");
		HttpSession session = request.getSession();
		String vistaMemberCardNumber = "9999 9999 9999 9999";
		if(session.getAttribute(Constant.SESSION_ATTRIBUTE_KEY) != null){
			UserMember userMember = (UserMember) session.getAttribute(Constant.SESSION_ATTRIBUTE_KEY);
			vistaMemberCardNumber = userMember.getVistaMemberCardNumber();
			
			// check the count of VIP can buy tickets 2017/9/5
			String orderUserNumber = userMember.getUserNumber();
			FilmOrder filmOrderCheck = filmService.getFilmOrder(outTradeNo);
			String cinemaTypeCheck = filmService.getCinema(filmOrderCheck.getCinemaId()).getType();
			int restTicketsCount = filmService.getRestTickets(orderUserNumber,filmOrderCheck.getMemberLevelId(),filmOrderCheck.getCinemaId(),cinemaTypeCheck,filmOrderCheck.getSessionId(),"",filmOrderCheck.getShowTime());
			int orderTicketsCount =  filmOrderCheck.getTotalOrderCount();
			
			if (orderTicketsCount > restTicketsCount) {
				logger.error("ticketsCount is more than the maximum value when pay for the order");
				 model.addAttribute("filmOrder",filmOrderCheck);
				 model.addAttribute("result",9);
			    return "film_cashier_result";
			}
			
			
		}

	    //----------------------------------------------------------------------------------------------
	    logger.info("保存支付信息...");
	    List<Payment> payList = paymentService.getPaymentList(outTradeNo);
	    if(payList.size()<1 || payList == null){
		    Payment payment = new Payment();
	     	payment.setType(Payment.PAYMENT_ECARD);
	     	payment.setStatus(Payment.PAYMENT_STATUS_SUCCESS);
	     	payment.setStartTime(new Date());
	     	payment.setEndTime(new Date());
	     	payment.setPaymentPrice("0");
	     	logger.info("payment price:"+payment.getPaymentPrice());
	     	payment.setOrderNumber(outTradeNo);
	     	paymentService.savePayment(payment);
	     	List<Payment> paymentList=paymentService.getPaymentList(outTradeNo);
	    	//更新订单表里的PaymentID
	    	if(paymentList!=null && paymentList.size()>0){
	    		Payment payment_ = paymentList.get(0);
	    		int    paymentID = payment_.getPaymentID();
	    		filmService.updateOrderPaymentID(paymentID,outTradeNo);
	    	}
	    }
     	//----------------------------------------------------------------------------------------------
		
			 FilmOrder filmOrder = filmService.getFilmOrder(outTradeNo);
			 
			 String cinemaType = filmService.getCinema(filmOrder.getCinemaId()).getType();
			 String connectapiurl;
			 String connectapitoken;
			 String mobilePhone = "";
			 String userNickName = "";
			 String memberLevelId = filmOrder.getMemberLevelId();
			 
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
			 
			 //调用Vista的完成订单接口
			 JSONObject jsonParam = new JSONObject();
			 jsonParam.put("UserSessionId",outTradeNo);
			 JSONObject paymentJsonParam = new JSONObject();
			 //PaymentInfo
			 paymentJsonParam.put("CardNumber",vistaMemberCardNumber);

			 paymentJsonParam.put("CardType","LOYAL");
			 //订单支付金额
			 paymentJsonParam.put("PaymentValueCents",(int)(Double.valueOf(filmOrder.getTotalValueCents())*100));
			 paymentJsonParam.put("PaymentTenderCategory","LOYAL");
			 paymentJsonParam.put("CardExpiryMonth","07");
			 paymentJsonParam.put("CardExpiryYear","2018");
			 paymentJsonParam.put("PaymentSystemId","-");
			 
			 jsonParam.put("PaymentInfo",paymentJsonParam);
			 if("2".equals(memberLevelId) || "4".equals(memberLevelId) ){
				jsonParam.put("PerformPayment",true); 
			 }
			 jsonParam.put("CustomerPhone",mobilePhone);
			 jsonParam.put("CustomerEmail","");
			 jsonParam.put("CustomerName",userNickName);
			 jsonParam.put("OptionalClientClass",Constant.OPTIONAL_CLIENT_CLASS);
			 jsonParam.put("OptionalClientId",Constant.OPTIONAL_CLIENT_ID);
			 jsonParam.put("OptionalClientName",Constant.OPTIONAL_CLIENT_NAME);
			 String result = CinkerHttpService.httpPostRequest(connectapiurl+"WSVistaWebClient/RestTicketing.svc/order/payment",jsonParam.toString(),connectapitoken);
			 logger.info("Vista complete order request:"+jsonParam.toString());
			 logger.info("Vista complete order response:"+ result);
			 JSONObject resultJson = JSONObject.fromObject(result);
			 if(resultJson.getInt("Result") == 0){
				 //取票码
				 String bookingNumber = resultJson.getString("VistaBookingNumber");
				 //验证码
				 String bookingId = resultJson.getString("VistaBookingId");
				 //Vista交易单号
				 String vistaTransNumber = resultJson.getString("VistaTransNumber");
				 //更改本地数据库的订单信息
				 filmService.updateOrderSuccess(FilmOrder.ORDER_SUCCESS,new Date(),bookingNumber,bookingId,vistaTransNumber,outTradeNo);

				 filmOrder = filmService.getFilmOrder(outTradeNo);
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
				 model.addAttribute("result",1);
			 } else {
				 logger.info("******免费支付完成订单失败,request =["+ jsonParam.toString() + "]");
				 logger.info("******免费支付完成订单失败,Result =["+ result + "]");
				 model.addAttribute("filmOrder",filmOrder);
				 model.addAttribute("result",resultJson.getInt("Result"));
			 }
			 
         return "film_cashier_result";
	}

	@RequestMapping("/notify")
	public @ResponseBody String notify(Model model) throws IOException{
		String inputLine; 
		String notityXml = "";
		try {
			while ((inputLine = request.getReader().readLine()) != null) { 
				notityXml += inputLine;
			}
			 request.getReader().close();
		}catch(Exception e){
			
		}
		logger.info("回调函数返回信息：" + notityXml);
		Map<String,Object> map = new HashMap<String, Object>();
		map = parseNotifyXml(notityXml);
		String orderNumber = (String) map.get("out_trade_no");
		logger.info("回调函数开始订单号：" + orderNumber + "回调函数返回状态：" + map.get("return_code") + "....");
		
		List<FilmOrder> filmorderTestList = cinemaService.getFilmOrderByOrderNumber(orderNumber);
		FilmOrder filmorderTest = null;
		if(filmorderTestList.size() > 0){
			filmorderTest = filmorderTestList.get(0);
		}
		if (!StringUtils.isEmpty(filmorderTest) && filmorderTest.getStatus() != 0) {
			logger.info("此次回调SKIP");
			return "<xml>"
			+"<return_code><![CDATA[SUCCESS]]></return_code>"
			+"<return_msg><![CDATA[OK]]></return_msg>"
			+"</xml>";
		} else {
			List<UserVipPayment> userVipPayment = userMemberService.getUserVipPaymentByOrderNumber(orderNumber);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(!map.get("return_code").equals("SUCCESS")){
				List<Payment> payments = paymentService.getPaymentList(orderNumber);
				ActivityPersonal activityPersonal = activityService.getActivityPersonalbyorderNumber(orderNumber);
				List<FilmOrder> filmorderList = cinemaService.getFilmOrderByOrderNumber(orderNumber);
				FilmOrder filmorder = null;
				if(filmorderList.size() > 0){
					filmorder = filmorderList.get(0);
					
					logger.info("回调函数更新支付信息：" + orderNumber);
					if(filmorder.getOrderType()==1){
						if(payments.size() > 0){
							Payment payment = payments.get(0);
							if(payment.getStatus() != Payment.PAYMENT_STATUS_SUCCESS){
								paymentService.updatePaymentStatus(Payment.PAYMENT_STATUS_FAIL,new Date(),orderNumber);
								filmService.updateOrderSuccess(Payment.PAYMENT_STATUS_FAIL,new Date(),"","","",orderNumber);
							}
						}
					}
					if(filmorder.getOrderType()==2){
						if(payments.size() > 0){
							Payment payment = payments.get(0);
							if(payment.getStatus() != Payment.PAYMENT_STATUS_SUCCESS){
								paymentService.updatePaymentStatus(Payment.PAYMENT_STATUS_FAIL,new Date(),orderNumber);
								filmService.updateOrderSuccess(Payment.PAYMENT_STATUS_FAIL,new Date(),"","","",orderNumber);
							}
						}
						if(activityPersonal!=null){
							ActivityFilm activityFilm = activityService.getActivityFilm(activityPersonal.getActivityId());
							int total = activityService.getActivityPersonal(activityPersonal.getActivityId());
							if(total <= activityFilm.getTotal()){
								if(activityPersonal.getStatus() != Payment.PAYMENT_STATUS_SUCCESS){
									activityService.updateActivityPersonal(Payment.PAYMENT_STATUS_INIT,sdf.format(new Date()), orderNumber);
								}
							}else{
								PrintWriter out = response.getWriter();
								String masess = "报名人数超额，请选择其他活动";
								out.print("<script language=\"javascript\"> alert('"+ masess +"！');window.location.href='/cinker/film/getScheduledFilm'</script>");	
								out.close();
							}
						}
					}
				}
				if(userVipPayment.size()>0){
					userMemberService.updateUserVipPaymentByOrderNumber(Payment.PAYMENT_STATUS_FAIL,new Date() , orderNumber);
				}
				logger.info("微信支付失败，回调函数结束订单号：" + orderNumber + "....");
				return "<xml>"
				+"<return_code><![CDATA[SUCCESS]]></return_code>"
				+"<return_msg><![CDATA[OK]]></return_msg>"
				+"</xml>";	
			}else{
				List<Payment> payments = paymentService.getPaymentList(orderNumber);
				ActivityPersonal activityPersonal = activityService.getActivityPersonalbyorderNumber(orderNumber);
				List<FilmOrder> filmorderList = cinemaService.getFilmOrderByOrderNumber(orderNumber);
				FilmOrder filmorder = null;
				logger.info("微信支付成功，回调函数结束订单号：" + orderNumber + "....");
				if(filmorderList.size() > 0){
					filmorder = filmorderList.get(0);
					if(filmorder.getOrderType()==1){
						if(payments.size() > 0){
							Payment payment = payments.get(0);
							if(payment.getStatus() != Payment.PAYMENT_STATUS_SUCCESS){
								Date endTime = new Date();
								paymentService.updatePaymentStatus(Payment.PAYMENT_STATUS_SUCCESS,endTime,orderNumber);
								FilmOrder filmOrder = filmService.getFilmOrder(orderNumber);
								String result = completeOrderFromVista(orderNumber, filmOrder,endTime);
								JSONObject resultJson=JSONObject.fromObject(result);		
								 if(resultJson.getInt("Result")==0){
									 //取票码
									 String bookingNumber=resultJson.getString("VistaBookingNumber");
									 //验证码
									 String bookingId=resultJson.getString("VistaBookingId");
									 //Vista交易单号
									 String vistaTransNumber = resultJson.getString("VistaTransNumber");
									 //更改本地数据库的订单信息
									 filmService.updateOrderSuccess(FilmOrder.ORDER_SUCCESS,new Date(),bookingNumber,bookingId,vistaTransNumber,orderNumber);
									 FilmOrder filmOrders = filmService.getFilmOrder(orderNumber);
									 if(filmOrders.getBookingID() == null || "".equals(filmOrders.getBookingID())){
										filmService.updateOrderSuccess(Payment.PAYMENT_STATUS_SUCCESS,new Date(),bookingNumber,bookingId,vistaTransNumber,orderNumber);
									 }
								 } else {
									 logger.info("******回调时完成订单失败,orderNumber =["+ orderNumber + "]");
									 logger.info("******回调时完成订单失败,Result =["+ result + "]");
								 }
							}
						}
					}
				if(filmorder.getOrderType()==2){
					if(payments.size() > 0){
						Payment payment = payments.get(0);
						if(payment.getStatus() != Payment.PAYMENT_STATUS_SUCCESS){
							paymentService.updatePaymentStatus(Payment.PAYMENT_STATUS_SUCCESS,new Date(),orderNumber);
							filmService.updateOrderSuccess(Payment.PAYMENT_STATUS_SUCCESS,new Date(),"","","",orderNumber);
						}
					}
					
					if(activityPersonal!=null){
						ActivityFilm activityFilm = activityService.getActivityFilm(activityPersonal.getActivityId());
						int total = activityService.getActivityPersonal(activityPersonal.getActivityId());
						if(total <= activityFilm.getTotal()){
							if(activityPersonal.getStatus() != Payment.PAYMENT_STATUS_SUCCESS){
								activityService.updateActivityPersonal(Payment.PAYMENT_STATUS_SUCCESS,sdf.format(new Date()), orderNumber);
							}
						}else{
							activityService.updateActivityPersonal(Payment.PAYMENT_STATUS_OVERTIME,sdf.format(new Date()), orderNumber);
						}
					}
				}
			}
			if(userVipPayment.size()>0){
					userMemberService.updateUserVipPaymentByOrderNumber(Payment.PAYMENT_STATUS_SUCCESS,new Date() , orderNumber);
				}
			}
			return "<xml>"
			+"<return_code><![CDATA[SUCCESS]]></return_code>"
			+"<return_msg><![CDATA[OK]]></return_msg>"
			+"</xml>";
		}
	}
	
	@SuppressWarnings({ "rawtypes","unchecked"})
	@RequestMapping(value = "/showQRcode", method = { GET, POST })
	@ResponseBody
	public String showQRcode(String content,Integer onColor,Model model){
        try {
        	if(!StringUtils.isEmpty(content)){
        		if(onColor != null){
        			MatrixToImageConfig config = new MatrixToImageConfig(onColor,0xFFFFFF);
        			MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                    Hashtable hints = new Hashtable();
                    hints.put(EncodeHintType.CHARACTER_SET,"UTF-8");
                    hints.put(EncodeHintType.MARGIN, 1);
                    BitMatrix bitMatrix = multiFormatWriter.encode(content,BarcodeFormat.QR_CODE,300,300,hints);
                    MatrixToImageWriter.writeToStream(bitMatrix,"jpg",response.getOutputStream(),config);
        		}
        		 MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                 Hashtable hints = new Hashtable();
                 hints.put(EncodeHintType.CHARACTER_SET,"UTF-8");
                 hints.put(EncodeHintType.MARGIN, 1);
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
	
	@SuppressWarnings({ "rawtypes","unchecked"})
	@RequestMapping(value = "/qRcode", method = { GET, POST })
	@ResponseBody
	public String qRcode(String content,Integer onColor,Model model){
        try {
        	if(!StringUtils.isEmpty(content)){
        		if(onColor != null){
        			MatrixToImageConfig config = new MatrixToImageConfig(onColor,0xFFFFFF);
        			MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                    Hashtable hints = new Hashtable();
                    hints.put(EncodeHintType.CHARACTER_SET,"UTF-8");
                    BitMatrix bitMatrix = multiFormatWriter.encode(content,BarcodeFormat.CODE_128,600,100,hints);
                    MatrixToImageWriter.writeToStream(bitMatrix,"jpg",response.getOutputStream(),config);
        		}
        		 MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                 Hashtable hints = new Hashtable();
                 hints.put(EncodeHintType.CHARACTER_SET,"UTF-8");
                 BitMatrix bitMatrix = multiFormatWriter.encode(content,BarcodeFormat.CODE_39,150,150,hints);
                 MatrixToImageWriter.writeToStream(bitMatrix,"jpg",response.getOutputStream());
        	}
        	else{
        		logger.error("Wechat get QRCode is null");
        		return "获取条形码失败";
        	}
         } catch (Exception e) {
            e.printStackTrace();
            logger.error("Wechat get QRCode Server Error");
         }
         return null;
	}
	
	private String parseQueryOrderXml(String queryResult){
		Document document = null;
		String tradeState = "";
		try {
			document = DocumentHelper.parseText(queryResult);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(document != null){
			Element root = document.getRootElement();
			if(root != null){
				String returnCode = root.element("return_code").getText();
				if("SUCCESS".equals(returnCode)){
					String resultCode = root.element("result_code").getText();
					if("SUCCESS".equals(resultCode)){
						tradeState = root.element("trade_state").getText();
						String transactionId = root.elementText("transaction_id");
						String orderNumber = root.elementText("out_trade_no");
						cinemaService.updatePaymentTransactionId(transactionId, orderNumber);
					}
				}
			}
		}
		return tradeState;
	}
	
	private Map<String,Object> parseNotifyXml(String queryResult){
		String result = "";
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			Document document = DocumentHelper.parseText(queryResult);
			if(document != null){
				Element root = document.getRootElement();
				String returnCode = root.element("return_code").getText();
				result = root.element("out_trade_no").getText();
				map.put("return_code", returnCode);
				map.put("out_trade_no", result);
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	public static void main(String[] args) {
		String result = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg><appid><![CDATA[wx90954065982d2e76]]></appid><mch_id><![CDATA[1276180601]]></mch_id><nonce_str><![CDATA[jKlyS1vOKRNPPSSA]]></nonce_str><sign><![CDATA[52EA1E0D4090084C3BBA146AA1605654]]></sign><result_code><![CDATA[SUCCESS]]></result_code><openid><![CDATA[o4voAwVWQrj5eJSPAZusJna30eEw]]></openid><is_subscribe><![CDATA[Y]]></is_subscribe><trade_type><![CDATA[NATIVE]]></trade_type><bank_type><![CDATA[CFT]]></bank_type><total_fee>10</total_fee><fee_type><![CDATA[CNY]]></fee_type><transaction_id><![CDATA[4007282001201702159995116662]]></transaction_id><out_trade_no><![CDATA[1415659995]]></out_trade_no><attach><![CDATA[]]></attach><time_end><![CDATA[20170215115759]]></time_end><trade_state><![CDATA[SUCCESS]]></trade_state><cash_fee>10</cash_fee></xml>";
		WechatAction wechatAction = new WechatAction();
		wechatAction.parseQueryOrderXml(result);
	}
	
	private String completeOrderFromVista(String tradeno,FilmOrder filmOrder,Date endTime){
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
		 logger.info("回调函数中,Vista complete order request:"+jsonParam.toString());
		 logger.info("回调函数中,Vista complete order response:"+ result);
		 JSONObject resultJson=JSONObject.fromObject(result);
		 if(resultJson.getInt("Result") !=0 ) {
			 saveUnsuccessRecord(tradeno,jsonParam.toString(),result,endTime);
		 }
		 return result;
		
	}
	//write unsuccess order record to DB
	private void saveUnsuccessRecord(String orderNumber, String inData,
			String result,Date endTime) {
		UnsuccessRecord unsuccessRecord = new UnsuccessRecord();
		unsuccessRecord.setOrderNumber(orderNumber);
		unsuccessRecord.setInData(inData);
		unsuccessRecord.setResult(result);
		unsuccessRecord.setEndTime(endTime);
		
		paymentService.saveUnsuccessRecord(unsuccessRecord);
	}
	
	@RequestMapping(value = "/test", method = { GET, POST })
	public String test() {
		String result = WxPayUtil.payRefund(APPID, MCH_ID, API_KEY, "1000001835913587", "2000001835913587", 15000, 15000, NOTIFY_URL);
		return result;
	}
		
}
