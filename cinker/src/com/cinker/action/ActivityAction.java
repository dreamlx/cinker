package com.cinker.action;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alibaba.druid.util.StringUtils;
import com.cinker.constant.Constant;
import com.cinker.model.ActivityFilm;
import com.cinker.model.ActivityPersonal;
import com.cinker.model.Cinema;
import com.cinker.model.FilmContentImage;
import com.cinker.model.FilmInfo;
import com.cinker.model.FilmOrder;
import com.cinker.model.Payment;
import com.cinker.model.UserMember;
import com.cinker.model.UserVipMember;
import com.cinker.service.ActivityService;
import com.cinker.service.CinemaService;
import com.cinker.service.FilmService;
import com.cinker.service.PaymentService;
import com.cinker.service.UserMemberService;
import com.cinker.util.CommonsUtil;
import com.cinker.util.OrderNumUtil;
import com.cinker.wechat.PayCommonUtil;
import com.cinker.wechat.WxPayUtil;

@Component
@Controller
@RequestMapping("/activity")
public class ActivityAction {
	
	@Autowired
	FilmService filmService;
	
	@Autowired
	ActivityService activityService;
	
	@Autowired
	UserMemberService userMemberService;
	
	@Autowired
	CinemaService cinemaService;
	
	@Autowired
	protected PaymentService paymentService;
	
	@Autowired
	protected HttpServletRequest request;
	
	@Autowired
	protected HttpServletResponse response;
	
	@Value("${APPID}")
	private  String APPID;
	@Value("${MCH_ID}")
	private String MCH_ID;
	@Value("${API_KEY}")
	private String API_KEY;
	@Value("${NOTIFY_URL}")
	private String NOTIFY_URL;
	private static Logger logger = Logger.getLogger(ActivityAction.class);
	

    @Scheduled(cron = "0 0/10 * * * ?")
    public void myTask() {
    	activityService.updateActivityPersonalStatus();
    }

	
	
	@RequestMapping(value = "/loginActivity", method = { GET, POST })
	public String loginActivity(Model model){
		String eventId =request.getParameter("eventId");
		List<FilmInfo> films = cinemaService.getFilmInfo();
		List<FilmContentImage> image = null;
		String orderId = OrderNumUtil.getOrderId();
		model.addAttribute("orderId", orderId);
		ActivityFilm activityFilm = activityService.getActivityFilm(eventId);
		if(activityFilm != null){
			// check the count of VIP can buy tickets 2017/9/5
			int restTicketsCount = 100;
			String memberLevelId = "";
			String userNumber = "";
			HttpSession session = request.getSession();
			if(session.getAttribute(Constant.SESSION_ATTRIBUTE_KEY) != null) {
				userNumber = ((UserMember)session.getAttribute(Constant.SESSION_ATTRIBUTE_KEY)).getUserNumber();
				memberLevelId = userMemberService.getUserVistaCardNumber(userNumber).get(0).getMemberLevelId();
				String cinemaType = "Pictures";
				if( "6".equals(memberLevelId)) {
					restTicketsCount = filmService.getRestTickets(userNumber,memberLevelId,activityFilm.getCinemaId(),cinemaType,eventId,"",activityFilm.getSessionTime());
				}
			}
			
			for(FilmInfo film:films){
				if(film.getFilmId().equals(activityFilm.getFilmId())){
					activityFilm.setChineseName(film.getChineseName());
					activityFilm.setEnglishName(film.getEnglishName());
					activityFilm.setLanguage(film.getLanguage());
					image = cinemaService.getFilmContentImage(film.getFilmId());
				}
			}
			
			Boolean memberCanBook = true;
			// upper member activity only morden or upper morden member can book;
			int memberLv = 0;
			try {
				
				memberLv = Integer.parseInt(memberLevelId);
			} catch (NumberFormatException e) {
			    e.printStackTrace();
			}
			if(memberLv < activityFilm.getIsForUpperMember() ){
				memberCanBook = false;
			}
			
			model.addAttribute("image", image);
			String sessionTime = activityFilm.getSessionTime();
			String date = sessionTime.substring(0, 10);
			String time = sessionTime.substring(11, 16);
			model.addAttribute("date", date);
			model.addAttribute("time", time);
			model.addAttribute("eventId", eventId);
			model.addAttribute("activityFilm", activityFilm);
			model.addAttribute("memberCanBook", memberCanBook);
			model.addAttribute("userNumber", userNumber);
			model.addAttribute("memberLevelLock", activityFilm.getIsForUpperMember());
			model.addAttribute("memberLevelId", memberLevelId);
			model.addAttribute("restTicketsCount", restTicketsCount);
		}
		return "activity";
	}
	
	@RequestMapping(value = "/eventCPSH", method = { GET, POST })
	public String eventCPSH(Model model){
		String orderId = OrderNumUtil.getOrderId();
		model.addAttribute("orderId", orderId);
		
		// check the count of VIP can buy tickets 2017/9/5
		String userNumber = "";
		HttpSession session = request.getSession();
		if(session.getAttribute(Constant.SESSION_ATTRIBUTE_KEY) != null) {
			userNumber = ((UserMember)session.getAttribute(Constant.SESSION_ATTRIBUTE_KEY)).getUserNumber();
		}

		model.addAttribute("userNumber", userNumber);
		
		return "cpsh_xmas";
	}
	
	@RequestMapping(value = "/getQRcode", method = { GET, POST })
	public String getCinema(Model model,String name, String phone, Integer quaty, String eventId,String orderId,String totalPrice, String userNumber) throws IOException{
		ActivityFilm activityFilm = activityService.getActivityFilm(eventId);
		int totle = activityFilm.getTotal();
		int totle_ = activityService.getActivityPersonal(eventId);
		
		if(quaty != null){
			if(totle_ >= totle){
				 model.addAttribute("message","名额已满");
				 return "activity";
			}
			if(activityFilm.getQuaty() < quaty){
				model.addAttribute("message","超出规定数量");
				model.addAttribute("eventId", eventId);
				return "activity";
			}else if(totle - totle_ < quaty){
				model.addAttribute("message", "超出规定数量");
				model.addAttribute("eventId", eventId);
				return "activity";
			}
		}
		String cinemaId = activityFilm.getCinemaId();
		String sessionId = activityFilm.getSessionId();
		String sessionName = activityFilm.getSessionName();
		String filmTitle = activityFilm.getFilmTitle();
		String sessionTime = activityFilm.getSessionTime();
		Double totalValueCents = activityFilm.getTotalValueCents();
		String totalValueCents_ = "";
		Double totalValueCents_D = 0.0;
		if(quaty != null){
			totalValueCents_ = totalValueCents * quaty + "";
			totalValueCents_D = totalValueCents * quaty;
		}
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = df.format(new Date());	
		HttpSession session = request.getSession();
		//------------------------------------------------------------------------------------------------
		String openid = "";
		String memberLevelId = "";
		int restTicketsCount = 100;
		if(session.getAttribute(Constant.SESSION_ATTRIBUTE_KEY) != null){
			UserMember userMembers = ((UserMember) request.getSession()
					.getAttribute(Constant.SESSION_ATTRIBUTE_KEY));
			UserVipMember userMember = userMemberService.getUserVipMember(userMembers.getOpenID());
			openid = userMember.getOpenID();
			
			// check the count of VIP can buy tickets 2017/9/5
			String userNumberOrder = userMembers.getUserNumber();
			memberLevelId = userMemberService.getUserVistaCardNumber(userNumberOrder).get(0).getMemberLevelId();
			String cinemaType = "Pictures";
			if( "6".equals(memberLevelId)) {
				restTicketsCount = filmService.getRestTickets(userNumberOrder,memberLevelId,activityFilm.getCinemaId(),cinemaType,eventId,"",activityFilm.getSessionTime());

				if (restTicketsCount < 1) {
					model.addAttribute("result",9);
					logger.error("ticketsCount is more than the maximum value");
					return "activityWxpay";
				}else{
					//make the super morden member free ticket;
					totalValueCents_ = "0.0";
				}
			}
		}
		if(!totalValueCents_.equals("0.0")){
			logger.info("三克活动订单支付开始...");
			String timeStamp = PayCommonUtil.create_timestamp();
		    String nonceStr = PayCommonUtil.create_nonce_str();
		    //totalPrice = "0.01";
		    String prepayId = WxPayUtil.unifiedorder(APPID,MCH_ID,NOTIFY_URL,API_KEY,filmTitle,orderId, openid, totalValueCents_+"", cinemaId);
		    logger.info(prepayId);
		    SortedMap<Object,Object> signParams = new TreeMap<Object,Object>();  
		    signParams.put("appId",APPID);  
		    signParams.put("nonceStr",nonceStr);  
		    signParams.put("package", "prepay_id=" + prepayId);  
		    signParams.put("timeStamp", timeStamp);  
		    signParams.put("signType", "MD5");
		    String sign = PayCommonUtil.createSign(signParams,API_KEY);
		    logger.info(sign);

		    model.addAttribute("timeStamp", timeStamp);
		    model.addAttribute("nonceStr", nonceStr);
		    model.addAttribute("openid",openid);
		    model.addAttribute("paySign", sign);
		    model.addAttribute("packageValue", "prepay_id=" + prepayId);
		}
	    //----------------------------------------------------------------------------------------------
	    ActivityPersonal activityPersonal_ = activityService.getActivityPersonalbyorderNumber(orderId);
	    if(activityPersonal_ == null){
			List<FilmOrder> orderList = cinemaService.getFilmOrderByOrderNumber(orderId);
			if(orderList == null || orderList.size()<1){
				FilmOrder saveOrder = new FilmOrder();
				saveOrder.setOrderNumber(orderId);
				saveOrder.setCinemaId(cinemaId);		
				if(!StringUtils.isEmpty(cinemaId)){
					Cinema cinema = filmService.getCinema(cinemaId);
					if(cinema != null)saveOrder.setCinemaName(cinema.getName());
				}
				saveOrder.setSessionId(sessionId);
				saveOrder.setSessionName(sessionName);
				saveOrder.setFilmTitle(filmTitle);
				saveOrder.setScheduledFilmId(name);// save the name and phone in the order
				saveOrder.setAreaCategoryCode(phone);// when order completed, the name and phone will write in the activity_personal 
				saveOrder.setShowTime(sessionTime);
				saveOrder.setStatus(0);
				saveOrder.setTotalOrderCount(quaty);
				saveOrder.setTotalValueCents(CommonsUtil.roundByScale(totalValueCents_D, 2));
				saveOrder.setStartTime(now);
				saveOrder.setEndTime(null);
				saveOrder.setOrderType(FilmOrder.ORDER_TYPE_ACTIVITY);
				if(session.getAttribute(Constant.SESSION_ATTRIBUTE_KEY) != null)
				saveOrder.setUserNumber(((UserMember)session.getAttribute(Constant.SESSION_ATTRIBUTE_KEY)).getUserNumber());
				saveOrder.setSeat("现场安排");
				saveOrder.setMemberLevelId(memberLevelId);
				filmService.saveOrder(saveOrder);
				}
				//------------------------------------------------------------------------------------------------
		    logger.info("保存支付信息...");
		    List<Payment> payList = paymentService.getPaymentList(orderId);
		    if(payList == null || payList.size()<1){
			    Payment payment = new Payment();
		     	payment.setType(Payment.PAYMENT_WECHAT);
		     	payment.setStatus(Payment.PAYMENT_STATUS_INIT);
		     	payment.setStartTime(new Date());
		     	payment.setPaymentPrice(totalValueCents+"");
		     	logger.info("payment price:"+payment.getPaymentPrice());
		     	payment.setOrderNumber(orderId);
		     	paymentService.savePayment(payment);
		     	List<Payment> paymentList = paymentService.getPaymentList(orderId);
		     	paymentList = paymentService.getPaymentList(orderId);
		    	//更新订单表里的PaymentID
		    	if(paymentList!=null && paymentList.size()>0){
		    		Payment payment_ = paymentList.get(0);
		    		int    paymentID = payment_.getPaymentID();
		    		filmService.updateOrderPaymentID(paymentID,orderId);
		    	}
		    }
		    //插入活动个人报名表开始-------------------------------------
			FilmOrder filmOrder = cinemaService.getFilmOrderByOrderNumber(orderId).get(0);
			Payment payments = cinemaService.getPaymentUserNickName(orderId).get(0);
			ActivityPersonal activityPersonalList = activityService.getActivityPersonalbyorderNumber(orderId);
			if(activityPersonalList == null){
				ActivityPersonal activityPersonal = new ActivityPersonal();
				activityPersonal.setCinemaId(filmOrder.getCinemaId());
				activityPersonal.setName(filmOrder.getScheduledFilmId());
				activityPersonal.setPhone(filmOrder.getAreaCategoryCode());
				activityPersonal.setSessionTime(filmOrder.getShowTime());
				SimpleDateFormat simdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				activityPersonal.setStartTime(simdf.format(new Date()));
				activityPersonal.setDateTime(simdf.format(new Date()));
				activityPersonal.setFilmTitle(filmOrder.getFilmTitle());
				activityPersonal.setNickeName(payments.getUserNickName());
				activityPersonal.setActivityUserNumber(filmOrder.getUserNumber());
				activityPersonal.setActivityId(filmOrder.getSessionId());
				activityPersonal.setQuaty(filmOrder.getTotalOrderCount());
				activityPersonal.setOrderEndTime(null);
				activityPersonal.setOrderNumber(filmOrder.getOrderNumber());
				activityPersonal.setStatus(0);
				activityService.insertActivity(activityPersonal);
			}
	    }
			//------------------------------------------------------------------------------------------------
	    if(totalValueCents_.equals("0.0")){
		    FilmOrder filmOrder = filmService.getFilmOrder(orderId); 
			ActivityPersonal acp = activityService.getActivityPersonalbyorderNumber(orderId);
			if(filmOrder.getOrderType()==2 && acp!=null){
				paymentService.updatePaymentStatus(Payment.PAYMENT_STATUS_SUCCESS,new Date(),orderId);
				//更改本地数据库的订单信息
				filmService.updateOrderSuccess(FilmOrder.ORDER_SUCCESS,new Date(),"","","",orderId);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				activityService.updateActivityPersonal(Payment.PAYMENT_STATUS_SUCCESS,sdf.format(new Date()), orderId);
			}
			model.addAttribute("filmOrder",filmOrder);
			model.addAttribute("result",1);
			response.setContentType("text/html;charset=UTF-8");
			String message = "报名成功";
			PrintWriter out = response.getWriter();
			out.print("<script language=\"javascript\"> alert('"+ message +"！');window.location.href='/cinker/activity/signUpSuccess?orderId="+orderId+"'</script>");	
			out.close();
			
			
	    }
	    
	    //----------------------------------------------------------------------------------------------
	    model.addAttribute("out_trade_no", orderId);
	    model.addAttribute("appid",APPID);
	    model.addAttribute("eventId", eventId);
	    logger.info("三克订单支付结束...");
	    
		
	    return "activityWxpay";
		
	}
	
	// only for new year event
	@RequestMapping(value = "/activityPayBegin", method = { GET, POST })
	public String activityPayBegin(Model model,String name, String phone, Integer quaty, String eventId,String orderId,String totalPrice, String userNumber) throws IOException{
		ActivityFilm activityFilm = activityService.getActivityFilm(eventId);
		int totle = activityFilm.getTotal();
		int totle_ = activityService.getActivityPersonal(eventId);
		
		if(quaty != null){
			if(totle_ >= totle){
				 model.addAttribute("message","名额已满");
				 return "activity";
			}
			if(activityFilm.getQuaty() < quaty){
				model.addAttribute("message","超出规定数量");
				model.addAttribute("eventId", eventId);
				return "activity";
			}else if(totle - totle_ < quaty){
				model.addAttribute("message", "超出规定数量");
				model.addAttribute("eventId", eventId);
				return "activity";
			}
		}
		String cinemaId = activityFilm.getCinemaId();
		String sessionId = activityFilm.getSessionId();
		String sessionName = activityFilm.getSessionName();
		String filmTitle = activityFilm.getFilmTitle();
		String sessionTime = activityFilm.getSessionTime();
		Double totalValueCents = activityFilm.getTotalValueCents();
		String totalValueCents_ = "";
		Double totalValueCents_D = 0.0;
		if(quaty != null){
			if(eventId.equals("xmas500")){
				totalValueCents_ = totalValueCents + "";
				totalValueCents_D = totalValueCents;
			}else{
				totalValueCents_ = totalValueCents * quaty + "";
				totalValueCents_D = totalValueCents * quaty;
			}
			
		}
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = df.format(new Date());	
		HttpSession session = request.getSession();
		//------------------------------------------------------------------------------------------------
		String openid = "";
		String memberLevelId = "";
		if(session.getAttribute(Constant.SESSION_ATTRIBUTE_KEY) != null){
			UserMember userMembers = ((UserMember) request.getSession()
					.getAttribute(Constant.SESSION_ATTRIBUTE_KEY));
			UserVipMember userMember = userMemberService.getUserVipMember(userMembers.getOpenID());
			openid = userMember.getOpenID();
			
		}
		if(!totalValueCents_.equals("0.0")){
			logger.info("三克活动订单支付开始...");
			String timeStamp = PayCommonUtil.create_timestamp();
		    String nonceStr = PayCommonUtil.create_nonce_str();
		    //totalPrice = "0.01";
		    String prepayId = WxPayUtil.unifiedorder(APPID,MCH_ID,NOTIFY_URL,API_KEY,filmTitle,orderId, openid, totalValueCents_+"", cinemaId);
		    logger.info(prepayId);
		    SortedMap<Object,Object> signParams = new TreeMap<Object,Object>();  
		    signParams.put("appId",APPID);  
		    signParams.put("nonceStr",nonceStr);  
		    signParams.put("package", "prepay_id=" + prepayId);  
		    signParams.put("timeStamp", timeStamp);  
		    signParams.put("signType", "MD5");
		    String sign = PayCommonUtil.createSign(signParams,API_KEY);
		    logger.info(sign);

		    model.addAttribute("timeStamp", timeStamp);
		    model.addAttribute("nonceStr", nonceStr);
		    model.addAttribute("openid",openid);
		    model.addAttribute("paySign", sign);
		    model.addAttribute("packageValue", "prepay_id=" + prepayId);
		}
	    //----------------------------------------------------------------------------------------------
	    ActivityPersonal activityPersonal_ = activityService.getActivityPersonalbyorderNumber(orderId);
	    if(activityPersonal_ == null){
			List<FilmOrder> orderList = cinemaService.getFilmOrderByOrderNumber(orderId);
			if(orderList == null || orderList.size()<1){
				FilmOrder saveOrder = new FilmOrder();
				saveOrder.setOrderNumber(orderId);
				saveOrder.setCinemaId(cinemaId);		
				if(!StringUtils.isEmpty(cinemaId)){
					Cinema cinema = filmService.getCinema(cinemaId);
					if(cinema != null)saveOrder.setCinemaName(cinema.getName());
				}
				saveOrder.setSessionId(sessionId);
				saveOrder.setSessionName(sessionName);
				saveOrder.setFilmTitle(filmTitle);
				saveOrder.setScheduledFilmId(name);// save the name and phone in the order
				saveOrder.setAreaCategoryCode(phone);// when order completed, the name and phone will write in the activity_personal 
				saveOrder.setShowTime(sessionTime);
				saveOrder.setStatus(0);
				saveOrder.setTotalOrderCount(quaty);
				saveOrder.setTotalValueCents(CommonsUtil.roundByScale(totalValueCents_D, 2));
				saveOrder.setStartTime(now);
				saveOrder.setEndTime(null);
				saveOrder.setOrderType(FilmOrder.ORDER_TYPE_ACTIVITY);
				if(session.getAttribute(Constant.SESSION_ATTRIBUTE_KEY) != null)
				saveOrder.setUserNumber(((UserMember)session.getAttribute(Constant.SESSION_ATTRIBUTE_KEY)).getUserNumber());
				saveOrder.setSeat("现场安排");
				saveOrder.setMemberLevelId(memberLevelId);
				filmService.saveOrder(saveOrder);
				}
				//------------------------------------------------------------------------------------------------
		    logger.info("保存act支付信息...");
		    List<Payment> payList = paymentService.getPaymentList(orderId);
		    if(payList == null || payList.size()<1){
			    Payment payment = new Payment();
		     	payment.setType(Payment.PAYMENT_WECHAT);
		     	payment.setStatus(Payment.PAYMENT_STATUS_INIT);
		     	payment.setStartTime(new Date());
		     	payment.setPaymentPrice(totalValueCents+"");
		     	logger.info("payment price:"+payment.getPaymentPrice());
		     	payment.setOrderNumber(orderId);
		     	paymentService.savePayment(payment);
		     	List<Payment> paymentList = paymentService.getPaymentList(orderId);
		     	paymentList = paymentService.getPaymentList(orderId);
		    	//更新订单表里的PaymentID
		    	if(paymentList!=null && paymentList.size()>0){
		    		Payment payment_ = paymentList.get(0);
		    		int    paymentID = payment_.getPaymentID();
		    		filmService.updateOrderPaymentID(paymentID,orderId);
		    	}
		    }
		    //插入活动个人报名表开始-------------------------------------
			FilmOrder filmOrder = cinemaService.getFilmOrderByOrderNumber(orderId).get(0);
			Payment payments = cinemaService.getPaymentUserNickName(orderId).get(0);
			ActivityPersonal activityPersonalList = activityService.getActivityPersonalbyorderNumber(orderId);
			if(activityPersonalList == null){
				ActivityPersonal activityPersonal = new ActivityPersonal();
				activityPersonal.setCinemaId(filmOrder.getCinemaId());
				activityPersonal.setName(filmOrder.getScheduledFilmId());
				activityPersonal.setPhone(filmOrder.getAreaCategoryCode());
				activityPersonal.setSessionTime(filmOrder.getShowTime());
				SimpleDateFormat simdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				activityPersonal.setStartTime(simdf.format(new Date()));
				activityPersonal.setDateTime(simdf.format(new Date()));
				activityPersonal.setFilmTitle(filmOrder.getFilmTitle());
				activityPersonal.setNickeName(payments.getUserNickName());
				activityPersonal.setActivityUserNumber(filmOrder.getUserNumber());
				activityPersonal.setActivityId(filmOrder.getSessionId());
				activityPersonal.setQuaty(filmOrder.getTotalOrderCount());
				activityPersonal.setOrderEndTime(null);
				activityPersonal.setOrderNumber(filmOrder.getOrderNumber());
				activityPersonal.setStatus(0);
				activityService.insertActivity(activityPersonal);
			}
	    }
	    
	    //----------------------------------------------------------------------------------------------
	    model.addAttribute("out_trade_no", orderId);
	    model.addAttribute("appid",APPID);
	    model.addAttribute("eventId", eventId);
	    logger.info("三克act订单支付结束...");
		
	    return "activityWxpay";
		
	}
	
	@RequestMapping(value="/signUpSuccess", method = { GET , POST})
	public String signUpSuccess(String orderId,Model model){		
		FilmOrder filmOrder = filmService.getFilmOrder(orderId);
		model.addAttribute("filmOrder",filmOrder);
		
		Cinema cinema = filmService.getCinema(filmOrder.getCinemaId());
		model.addAttribute("cinema", cinema);
		
		return "activity_film_cashier_result";
	}
	
	@RequestMapping(value = "/orderPaySuccess", method ={GET})
	public String orderPaySuccess(String tradeno,Model model) throws UnsupportedEncodingException{
		
		 logger.info("微信订单查询单号： " + tradeno);
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
		 if(orderStatus > 0){
			 logger.info("微信支付成功订单号： "+tradeno);
			 //订单支付成功 调用Vista的完成订单接口并且更新本地数据库的payment和order表数据
			 //更新支付信息		
			 FilmOrder filmOrder = filmService.getFilmOrder(tradeno); 
			 ActivityPersonal acp = activityService.getActivityPersonalbyorderNumber(tradeno);
			 if(filmOrder.getOrderType()==2 && acp!=null){
				 paymentService.updatePaymentStatus(Payment.PAYMENT_STATUS_SUCCESS,new Date(),tradeno);
				//更改本地数据库的订单信息
				 filmService.updateOrderSuccess(FilmOrder.ORDER_SUCCESS,new Date(),"","","",tradeno);
				 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				 activityService.updateActivityPersonal(Payment.PAYMENT_STATUS_SUCCESS,sdf.format(new Date()), tradeno);
			 }
			 model.addAttribute("filmOrder",filmOrder);
			 model.addAttribute("result",1);
			 
			 String eventId = filmOrder.getSessionId();
			 
			 // for new year event, special success page
			 if(eventId.equals("xmas3001") || eventId.equals("xmas3002") || eventId.equals("xmas500")){
				 return "cpsh_xmas_ok";
			 }
		 }else{
			 model.addAttribute("result",0);
		 }
		 
		 
		 
         return "activity_film_cashier_result";
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


}
