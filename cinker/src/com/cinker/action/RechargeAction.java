package com.cinker.action;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
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
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.druid.util.StringUtils;
import com.cinker.constant.Constant;
import com.cinker.http.CinkerHttpService;
import com.cinker.model.ConcessionItems;
import com.cinker.model.ConcessionTabs;
import com.cinker.model.Payment;
import com.cinker.model.UserMember;
import com.cinker.model.UserVipMember;
import com.cinker.model.UserVipPayment;
import com.cinker.service.UserMemberService;
import com.cinker.util.CommonsUtil;
import com.cinker.util.OrderNumUtil;
import com.cinker.wechat.PayCommonUtil;
import com.cinker.wechat.WxPayUtil;


@Controller
@RequestMapping("/recharge")
public class RechargeAction {
	
	@Value("${APPID}")
	private  String APPID;
	@Value("${MCH_ID}")
	private String MCH_ID;
	@Value("${API_KEY}")
	private String API_KEY;
	@Value("${NOTIFY_URL}")
	private String NOTIFY_URL;
	@Autowired
	UserMemberService userMemberService;
	
	@Autowired
	private static Logger logger = Logger.getLogger(WechatPayAction.class);

	@Autowired
	protected HttpServletResponse response;
	@Autowired
	protected HttpServletRequest request;
	
	@RequestMapping(value = "/firstRecharge", method = { GET, POST })
	public String firstRecharge(Model model,String userSessionId) throws IOException{
		UserMember userMembers = ((UserMember) request.getSession()
				.getAttribute(Constant.SESSION_ATTRIBUTE_KEY));
		UserVipMember userMember = userMemberService.getUserVipMember(userMembers.getOpenID());
		if("Cinker".equals(userMember.getFirstName()) && "Member".equals(userMember.getLastName())){
			response.setContentType("text/html;charset=UTF-8");
			String message = "请先填写个人信息";
			PrintWriter out = response.getWriter();
			out.print("<script language=\"javascript\"> alert('"+ message +"！');window.location.href='/cinker/usermember/pageToProfile'</script>");	
			out.close();
		}

		// 20170712 Qin
		/*		CinkerHttpService cinkerHttpService=CinkerHttpService.getInstance();
		String result=cinkerHttpService.httpGetRequest(Constant.CONNECT_API_URL+"WSVistaWebClient/RESTData.svc/concession-items-grouped-by-tabs?"+
				"cinemaId=1001&clientId="+ Constant.OPTIONAL_CLIENT_ID +"");
		JSONObject resultJson=JSONObject.fromObject(result);
		logger.info("Vista complete order response:"+ result);
		Double PriceInCents = 0D;
		JSONArray concessionTabsArray = resultJson.getJSONArray("ConcessionTabs");
		List<ConcessionTabs> ConcessionTabsList = new ArrayList<ConcessionTabs>();
		List<ConcessionItems> ConcessionItemsList = new ArrayList<ConcessionItems>();
		for(int i=0; i<concessionTabsArray.size(); i++){
			try{
				ConcessionTabs concessionTabs = new ConcessionTabs();
				ConcessionItems concessionItems = new ConcessionItems();
				JSONObject concessionItemsJson = concessionTabsArray.getJSONObject(i);
				concessionTabs.setId(concessionItemsJson.getInt("Id"));
				concessionTabs.setName(concessionItemsJson.getString("Name"));
				ConcessionTabsList.add(concessionTabs);
				JSONArray concessionItemsArray = concessionItemsJson.getJSONArray("ConcessionItems");
				for(int k=0; k<concessionItemsArray.size(); k++){
					JSONObject concessionItemsJsonObject = concessionItemsArray.getJSONObject(k);
					
					concessionItems.setDescription(concessionItemsJsonObject.getString("Description"));
					concessionItems.setDescriptionAlt(concessionItemsJsonObject.getString("DescriptionAlt"));
					concessionItems.setExtendedDescription(concessionItemsJsonObject.getString("ExtendedDescription"));
					concessionItems.setExtendedDescriptionAlt(concessionItemsJsonObject.getString("ExtendedDescriptionAlt"));
					concessionItems.setHeadOfficeItemCode(concessionItemsJsonObject.getString("HeadOfficeItemCode"));
					concessionItems.setItemId(concessionItemsJsonObject.getString("Id"));
					concessionItems.setPriceInCents(CommonsUtil.roundByScale(concessionItemsJsonObject.getDouble("PriceInCents")/100, 2));
					ConcessionItemsList.add(concessionItems);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		for(int j=0; j<ConcessionTabsList.size(); j++){
			if(ConcessionTabsList.get(j).getId()==1){
				for(int l=0; l<ConcessionItemsList.size(); l++){
					String description = ConcessionItemsList.get(j).getDescription();
					String descriptionAlt = ConcessionItemsList.get(j).getDescriptionAlt();
					String extendedDescription = ConcessionItemsList.get(j).getExtendedDescription();
					String extendedDescriptionAlt = ConcessionItemsList.get(j).getExtendedDescriptionAlt();
					String headOfficeItemCode = ConcessionItemsList.get(j).getHeadOfficeItemCode();
					String itemId = ConcessionItemsList.get(j).getItemId();
					String priceInCents = ConcessionItemsList.get(j).getPriceInCents();
					ConcessionItems conItems = ConcessionItemsList.get(l);
					model.addAttribute("description", description);
					model.addAttribute("descriptionAlt", descriptionAlt);
					model.addAttribute("headOfficeItemCode", headOfficeItemCode);
					model.addAttribute("itemId", itemId);
					model.addAttribute("priceInCents", priceInCents);
				}
			}
		}
		String orderId = OrderNumUtil.getOrderId();
		model.addAttribute("userSessionId", userSessionId);
		model.addAttribute("orderId", orderId);
		model.addAttribute("ConcessionItemsList", ConcessionItemsList);
		model.addAttribute("PriceInCents",PriceInCents);*/
		String orderId = OrderNumUtil.getOrderId();
		model.addAttribute("userSessionId", userSessionId);
		model.addAttribute("orderId", orderId);
		return "user/charge_select";
		
	}
	
	@RequestMapping(value = "/confirmRecharge", method = { GET, POST })
	public String confirmRecharge(Model model,String orderId,String userSessionId,String priceInCents,String headOfficeItemCode,String recognitionId) throws IOException{

		// 支付确认页面跳转
		model.addAttribute("userSessionId", userSessionId);
		model.addAttribute("orderId", orderId);
		model.addAttribute("priceInCents", priceInCents);
		model.addAttribute("headOfficeItemCode", headOfficeItemCode);
		model.addAttribute("recognitionId", recognitionId);
		int priceInYuan = (int)(Double.valueOf(priceInCents)/ 100);
		model.addAttribute("priceInYuan", priceInYuan);
		return "user/charge_confirm";
		
	}
	
	@RequestMapping(value="/addConcession" ,method= { POST, GET})
	public String addConcession(String userSessionId,String headOfficeItemCode,String recognitionId){
		String requestUrl = Constant.CONNECT_API_URL+"WSVistaWebClient/RESTTicketing.svc/order/concessions";
		String cinemaId = "1001";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("UserSessionId", userSessionId);
		jsonObject.put("CinemaId", cinemaId);
		JSONArray concessionArray = new JSONArray();
		JSONObject concessObject = new JSONObject();
		// 20170712 Qin
		//concessObject.put("ItemId", itemId);
		concessObject.put("HeadOfficeItemCode", headOfficeItemCode);
		concessObject.put("Quantity", 1);
		concessObject.put("RecognitionId", recognitionId);
		concessObject.put("RecognitionSequenceNumber", 1);
		concessionArray.add(concessObject);
		jsonObject.put("Concessions", concessionArray);
		jsonObject.put("ReturnOrder", "true");
		jsonObject.put("SessionId", "");
		jsonObject.put("GiftStoreOrder", "false");
		jsonObject.put("OptionalClientClass",Constant.OPTIONAL_CLIENT_CLASS);
		jsonObject.put("OptionalClientId",Constant.OPTIONAL_CLIENT_ID);
		jsonObject.put("OptionalClientName",Constant.OPTIONAL_CLIENT_NAME);
		logger.info("add ticket request:"+jsonObject.toString());
		String result = CinkerHttpService.httpPostRequest(requestUrl, jsonObject.toString(),Constant.CONNECT_API_TOKEN);
		logger.info("add ticket response:"+result);
		return result;
	}
	
	@RequestMapping(value="/orderPayment")
	public String completeRecharge(Model model,String userSessionId,String orderId,String priceInCents,String headOfficeItemCode,String recognitionId){
		// 20170712 Qin
		String result = addConcession(userSessionId, headOfficeItemCode,recognitionId);
		HttpSession session = request.getSession();
		logger.info("add concession return message" + result);
		if(result!=null){
			JSONObject resultJson = JSONObject.fromObject(result);
			JSONObject jsonObject = resultJson.getJSONObject("Order");
			Double totalValueCents = (Double)jsonObject.getDouble("TotalValueCents");
			totalValueCents = (Double)jsonObject.getDouble("TotalValueCents");
			String addConcessionResult = resultJson.getString("Result");
			if(addConcessionResult.equals("0")){
				logger.info("充值会员卡支付开始...");
				String openid = "";
				if(session.getAttribute(Constant.SESSION_ATTRIBUTE_KEY) != null){
					UserMember userMember = (UserMember) session.getAttribute(Constant.SESSION_ATTRIBUTE_KEY);
					openid = userMember.getOpenID();
				}
				String timeStamp = PayCommonUtil.create_timestamp();
			    String nonceStr = PayCommonUtil.create_nonce_str();
				String cinemaId = "1001";
			    //priceInCents = "0.01";
			    String prepayId = WxPayUtil.unifiedorder(APPID,MCH_ID,NOTIFY_URL,API_KEY,"充值金额",orderId, openid, priceInCents+"", cinemaId);
			    logger.info(prepayId);
			    SortedMap<Object,Object> signParams = new TreeMap<Object,Object>();  
			    signParams.put("appId",APPID);  
			    signParams.put("nonceStr",nonceStr);  
			    signParams.put("package", "prepay_id=" + prepayId);  
			    signParams.put("timeStamp", timeStamp);  
			    signParams.put("signType", "MD5");
			    String sign = PayCommonUtil.createSign(signParams,API_KEY);
			    logger.info(sign);
			    //---------------------------------------------------------
			    logger.info("保存充值信息...");
			    List<UserVipPayment> userVipPaymentList = userMemberService.getUserVipPaymentByOrderNumber(orderId);
			    List<UserVipMember> userVipMember = userMemberService.getUserVipMemberByOpenId(openid);
				if(userVipMember.size()>0){
				    if( userVipPaymentList  == null || userVipPaymentList.size()<1){
					    UserVipPayment userVipPayment = new UserVipPayment();
					    userVipPayment.setOrderNumber(orderId);
					    userVipPayment.setUserMember(userVipMember.get(0).getUserNumber());
					    userVipPayment.setRechargePrice(priceInCents);
					    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					    userVipPayment.setStartRechargeTime(sdf.format(new Date()));
					    userVipPayment.setStatus(Payment.PAYMENT_STATUS_INIT);
					    userVipPayment.setType(1);
					    userMemberService.addUserVipPayment(userVipPayment);
				    }
				}
				String out_trade_no = orderId;
			    model.addAttribute("out_trade_no", out_trade_no);
			    model.addAttribute("userSessionId", userSessionId);
			    model.addAttribute("totalValueCents", totalValueCents);
			    model.addAttribute("appid",APPID);
			    model.addAttribute("timeStamp", timeStamp);
			    model.addAttribute("nonceStr", nonceStr);
			    model.addAttribute("openid",openid);
			    model.addAttribute("paySign", sign);
			    model.addAttribute("packageValue", "prepay_id=" + prepayId);
			    model.addAttribute("headOfficeItemCode", headOfficeItemCode);
			    logger.info("充值会员卡支付结束...");
			    return "rechargepay";
			}
		}	
		return "charge_select";
	}
	
	@RequestMapping(value = "/orderPaySuccess", method ={GET})
	public String orderPaySuccess(String userSessionId,String out_trade_no,String totalValueCents,String headOfficeItemCode,String openid,Model model) throws UnsupportedEncodingException{
		StringBuffer sql = request.getRequestURL();
		 logger.info("微信订单查询单号： " + out_trade_no);
		 String queryOrderResult = "";
		 try {			 
			queryOrderResult = WxPayUtil.queryOrder(APPID,MCH_ID,API_KEY,out_trade_no);			
		 } catch (Exception e) {
			logger.error("微信订单支付失败订单号：" + out_trade_no);
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
			 logger.info("微信支付成功订单号： "+out_trade_no);
			 //订单支付成功 调用Vista的完成订单接口并且更新本地数据库的payment和order表数据
			 //更新支付信息		
			 String result = orderPayment(out_trade_no, userSessionId,totalValueCents);
			 logger.info("Vista complete order response:"+ result);	
			 JSONObject resultJson = JSONObject.fromObject(result);
			 if(resultJson.getInt("Result") == 0){
				 userMemberService.updateUserVipPaymentByOrderNumber(Payment.PAYMENT_STATUS_SUCCESS, new Date(), out_trade_no);
				 userMemberService.updateUserVipLevelByOpenId(headOfficeItemCode,openid);
			 }
			 
			 model.addAttribute("regsuc","true");
			 model.addAttribute("userSessionId", userSessionId);
			 return "redirect:/usermember/getMemberCard";
		 }else{
			 model.addAttribute("result",0);
		 }
		 
         return "user_member_center";
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
						String orderId = root.elementText("out_trade_no");
						userMemberService.updateUserVipPaymentTransactionIdByOrderNumber(transactionId, orderId);
					}
				}
			}
		}
		return tradeState;
	}
	
	public String orderPayment(String orderId,String userSessionId,String totalValueCents){
		 List<UserVipMember> userVipMemberList = userMemberService.getUserVipVistaCardNumber(orderId);
		 UserVipMember userVipMemberCardNumber = userVipMemberList.get(0);
		 JSONObject jsonParam=new JSONObject();
		 jsonParam.put("UserSessionId",userSessionId);
		 JSONObject paymentJsonParam=new JSONObject();
		 paymentJsonParam.put("CardNumber",userVipMemberCardNumber.getVistaMemberCardNumber());
		 paymentJsonParam.put("CardType","GZH");
		 //订单支付金额
		 paymentJsonParam.put("PaymentValueCents",totalValueCents);
		 //paymentJsonParam.put("PaymentTenderCategory","CreditCard");
		 paymentJsonParam.put("PaymentTenderCategory","DebitCard");
		 paymentJsonParam.put("PerformPayment",false);
		 jsonParam.put("PaymentInfo",paymentJsonParam);
		 jsonParam.put("OptionalClientClass",Constant.OPTIONAL_CLIENT_CLASS);
		 jsonParam.put("OptionalClientId",Constant.OPTIONAL_CLIENT_ID);
		 jsonParam.put("OptionalClientName",Constant.OPTIONAL_CLIENT_NAME);
		 String result=CinkerHttpService.httpPostRequest(Constant.CONNECT_API_URL+"WSVistaWebClient/RestTicketing.svc/order/payment",jsonParam.toString(),Constant.CONNECT_API_TOKEN);
		 logger.info("Vista complete order request:"+jsonParam.toString());
		 return result;
	}
}
