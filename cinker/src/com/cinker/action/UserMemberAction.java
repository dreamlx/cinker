package com.cinker.action;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.cinker.constant.Constant;
import com.cinker.http.CinkerHttpService;
import com.cinker.model.BalanceList;
import com.cinker.model.Cinema;
import com.cinker.model.FilmInfo;
import com.cinker.model.FilmOrder;
import com.cinker.model.MemberTransactions;
import com.cinker.model.UserMember;
import com.cinker.model.UserVipMember;
import com.cinker.service.CinemaService;
import com.cinker.service.FilmService;
import com.cinker.service.UserMemberService;
import com.cinker.service.UserService;
import com.cinker.util.OrderNumUtil;

@Controller
@RequestMapping("/usermember")
public class UserMemberAction {

	@Autowired
	UserService userService;
	@Autowired
	UserMemberService userMemberService;
	@Autowired
	protected HttpServletRequest request;
	@Autowired
	FilmService filmService;
	@Autowired
	CinemaService cinemaService;
	@Autowired
	protected HttpServletResponse response;

	@RequestMapping(value = "/getUserInfo", method = { GET, POST })
	public String getUserInfo(Model model) throws UnsupportedEncodingException {
		UserMember userMembers = ((UserMember) request.getSession()
				.getAttribute(Constant.SESSION_ATTRIBUTE_KEY));
		UserVipMember userMember = userMemberService.getUserVipMember(userMembers.getOpenID());
		userMember.setUserNickName(URLDecoder.decode(
				userMember.getUserNickName(), "UTF-8"));
		model.addAttribute("userMember", userMember);		
		
		UserVipMember userVipMembers = userMemberService.getUserVipMember(userMember.getOpenID());
		String userSessionId = OrderNumUtil.getOrderId();
		String cinemaType = "Pictures";
		String result = validateVip(userVipMembers, userSessionId,cinemaType);
		JSONObject resultJson = null;
		if ( !StringUtils.isEmpty(result)) {
			resultJson = JSONObject.fromObject(result);
		}
		String cardNumber = null;
		String memberLevelName = "0";
		String memberLevelId = "0";
		String memberId = "";
		String clubId = "";
		if (resultJson != null && 0 == resultJson.getInt("Result")) {
			JSONObject loyaltyMemberJson = resultJson
					.getJSONObject("LoyaltyMember");
			cardNumber = loyaltyMemberJson.getString("CardNumber");
			memberLevelName = loyaltyMemberJson.getString("MemberLevelName");
			memberLevelId = loyaltyMemberJson.getString("MemberLevelId");
			memberId = loyaltyMemberJson.getString("MemberId");
			clubId = loyaltyMemberJson.getString("ClubID");
			if(!memberLevelId.equals("0") || !memberLevelName.equals("")){
				Object jsonObject = loyaltyMemberJson.getString("BalanceList");
				if(jsonObject != "null"){
					JSONArray balanceArray = loyaltyMemberJson.getJSONArray("BalanceList");
					List<BalanceList> balancelist = new ArrayList<BalanceList>();
					for(int i=0;i<balanceArray.size();i++){
						try{
							JSONObject balanceArrayList = balanceArray.getJSONObject(i);
							BalanceList balanceList = new BalanceList();
							balanceList.setBalanceTypeID(balanceArrayList.getString("BalanceTypeID"));
							balanceList.setIsDefault(balanceArrayList.getBoolean("IsDefault"));
							balanceList.setLifetimePointsBalanceDisplay(balanceArrayList.getDouble("LifetimePointsBalanceDisplay"));
							balanceList.setMessage(balanceArrayList.getString("Message"));
							balanceList.setName(balanceArrayList.getString("Name"));
							balanceList.setNameAlt(balanceArrayList.getString("NameAlt"));
							JSONArray nameTranslationsArray = balanceArrayList.getJSONArray("NameTranslations");
							String[] nameTranslations = new String[nameTranslationsArray.size()];
							for(int j=0;j<nameTranslationsArray.size();j++){
								JSONObject nameTranslationsJson = nameTranslationsArray.getJSONObject(j);
								String nameTranslation = nameTranslationsJson.getString("");						
								nameTranslations[j] = new String(nameTranslation);
							}
							balanceList.setNameTranslations(nameTranslations);
							balanceList.setPointsRemaining(balanceArrayList.getDouble("PointsRemaining"));
							balanceList.setRedemptionRate(balanceArrayList.getString("RedemptionRate"));
							balancelist.add(balanceList);
							
						}catch(Exception e){
							e.printStackTrace();
						}
					}
		//			userVipMember.getBalanceList().add(balanceList);
					if ("2".equals(balancelist.get(1).getBalanceTypeID())) {
						String name_2 = balancelist.get(1).getName();
						Double pointsRemaining_2 = balancelist.get(1).getPointsRemaining();
						model.addAttribute("name_2", name_2);
						model.addAttribute("pointsRemaining_2", pointsRemaining_2);
					}
					if ("1".equals(balancelist.get(0).getBalanceTypeID())) {
						String name_1 = balancelist.get(0).getName();
						Double pointsRemaining_1 = balancelist.get(0).getPointsRemaining();
						model.addAttribute("name_1", name_1);
						model.addAttribute("pointsRemaining_1", pointsRemaining_1);
					}
				}
			}else{
				Object jsonObject = loyaltyMemberJson.getString("BalanceList");
				if(jsonObject != "null"){
				JSONArray balanceArray = loyaltyMemberJson.getJSONArray("BalanceList");
				List<BalanceList> balancelist = new ArrayList<BalanceList>();
				for(int i=0;i<balanceArray.size();i++){
					try{
						JSONObject balanceArrayList = balanceArray.getJSONObject(i);
						BalanceList balanceList = new BalanceList();
						balanceList.setBalanceTypeID(balanceArrayList.getString("BalanceTypeID"));
						balanceList.setIsDefault(balanceArrayList.getBoolean("IsDefault"));
						balanceList.setLifetimePointsBalanceDisplay(balanceArrayList.getDouble("LifetimePointsBalanceDisplay"));
						balanceList.setMessage(balanceArrayList.getString("Message"));
						balanceList.setName(balanceArrayList.getString("Name"));
						balanceList.setNameAlt(balanceArrayList.getString("NameAlt"));
						JSONArray nameTranslationsArray = balanceArrayList.getJSONArray("NameTranslations");
						String[] nameTranslations = new String[nameTranslationsArray.size()];
						for(int j=0;j<nameTranslationsArray.size();j++){
							JSONObject nameTranslationsJson = nameTranslationsArray.getJSONObject(j);
							String nameTranslation = nameTranslationsJson.getString("");						
							nameTranslations[j] = new String(nameTranslation);
						}
						balanceList.setNameTranslations(nameTranslations);
						balanceList.setPointsRemaining(balanceArrayList.getDouble("PointsRemaining"));
						balanceList.setRedemptionRate(balanceArrayList.getString("RedemptionRate"));
						balancelist.add(balanceList);
						
					}catch(Exception e){
						e.printStackTrace();
					}
				}
	//			userVipMember.getBalanceList().add(balanceList);
				if ("2".equals(balancelist.get(1).getBalanceTypeID())) {
					String name_2 = balancelist.get(1).getName();
					Double pointsRemaining_2 = balancelist.get(1).getPointsRemaining();
					model.addAttribute("name_2", name_2);
					model.addAttribute("pointsRemaining_2", pointsRemaining_2);
				}
				if ("1".equals(balancelist.get(0).getBalanceTypeID())) {
					String name_1 = balancelist.get(0).getName();
					Double pointsRemaining_1 = balancelist.get(0).getPointsRemaining();
					model.addAttribute("name_1", name_1);
					model.addAttribute("pointsRemaining_1", pointsRemaining_1);
				}
			}
			}
		}
		model.addAttribute("userSessionId", userSessionId);
		model.addAttribute("cardNumber", cardNumber);
		model.addAttribute("memberLevelName", memberLevelName);
		model.addAttribute("memberLevelId", memberLevelId);
		model.addAttribute("memberId", memberId);
		model.addAttribute("clubId", clubId);
		return "user/user_member_center";
	}
	
	
	@RequestMapping(value = "/getOrderList", method = { GET, POST })
	public String getOrderList(String cinemaId, Model model) {
		UserMember userMembers = ((UserMember) request.getSession()
				.getAttribute(Constant.SESSION_ATTRIBUTE_KEY));
		UserVipMember userMember = userMemberService.getUserVipMember(userMembers.getOpenID());
		List<FilmOrder> filmOrderList = filmService
				.getFilmOrderListByUserNumber(userMember.getUserNumber(),cinemaId);
		model.addAttribute("filmOrderList", filmOrderList);

		List<Cinema> cinemas = filmService.getCinemas();
		model.addAttribute("cinemas", cinemas);
		model.addAttribute("inCinemaId", cinemaId);
		return "user_member_order_list";
	}

	@RequestMapping(value = "/getOrderDetail", method = { GET, POST })
	public String getOrderDetail(String orderNumber, Model model) {
		FilmOrder filmOrder = filmService.getFilmOrder(orderNumber);
		List<FilmOrder> filmOrderList = filmService.getOrderDetailByOrderNumber(orderNumber);
		if(filmOrderList.size()>0){
			List<FilmInfo> filmInfo = cinemaService.getFilmInfoByFilmId(filmOrder.getFilmTitle());
			if(filmInfo != null && filmInfo.size()>0){
				String englishName = filmInfo.get(0).getEnglishName();
				model.addAttribute("englishName", englishName);
			}
			String totalValueCents = filmOrderList.get(0).getTotalValueCents();
			String transactionId = filmOrderList.get(0).getTransactionId();
			Integer type = filmOrderList.get(0).getType();
			model.addAttribute("type", type);
			model.addAttribute("transactionId", transactionId);
			model.addAttribute("orderNumber", orderNumber);
			model.addAttribute("totalValueCents", totalValueCents);
		}
		model.addAttribute("filmOrder", filmOrder);
		return "user_member_order_detail";
	}

	@RequestMapping(value = "/vipRegister", method = { GET, POST })
	public String getVipRegister() {
		return "user/register";
	}
	
	public String validateVip(UserVipMember userVipMember,String userSessionId,String cinemaType){
//		UserVipMember userVipMember = userMemberService
//			.getUserVipMember(userMember.getOpenID());
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("OptionalClientClass", Constant.OPTIONAL_CLIENT_CLASS);
		jsonParam.put("OptionalClientId", Constant.OPTIONAL_CLIENT_ID);
		jsonParam.put("OptionalClientName", Constant.OPTIONAL_CLIENT_NAME);
		jsonParam.put("UserSessionId", userSessionId);
		jsonParam.put("MemberCardNumber",
				userVipMember.getVistaMemberCardNumber());
		jsonParam.put("MemberLogin", userVipMember.getUserName());
		jsonParam.put("MemberPassword", userVipMember.getPassword());
		jsonParam.put("ReturnMember", true);
		
		String connectapiurl;
		String connectapitoken;

		if ("Pictures".equals(cinemaType)){
			connectapiurl = Constant.CONNECT_API_URL;
			connectapitoken = Constant.CONNECT_API_TOKEN;
		} else {
			connectapiurl = Constant.CONNECT_API_URL_C;
			connectapitoken = Constant.CONNECT_API_TOKEN_C;
		}

		String result = CinkerHttpService.httpPostRequest(
				connectapiurl
						+ "WSVistaWebClient/RESTLoyalty.svc/member/validate",
				jsonParam.toString(),connectapitoken);
		System.out.println("Validate VistaVipMember request:"
				+ jsonParam.toString());
		System.out.println("Validate VistaVipMember response:" + result);
		return result;
	}
	

	@RequestMapping(value = "/getMemberCard", method = { GET, POST })
	public String getMemberCard(Model model, String userSessionId, String regsuc) throws IOException{
		UserMember userMembers = ((UserMember) request.getSession()
				.getAttribute(Constant.SESSION_ATTRIBUTE_KEY));
		UserVipMember userMember = userMemberService.getUserVipMember(userMembers.getOpenID());
		String cinemaType = "Pictures";
		String result = validateVip(userMember, userSessionId,cinemaType);
		JSONObject resultJson = null;
		if ( !StringUtils.isEmpty(result)) {
			resultJson = JSONObject.fromObject(result);
		}

		String memberLevelId = "0";
		String memberExpiryDate = "0";
		if (resultJson != null && 0 == resultJson.getInt("Result")) {
			JSONObject loyaltyMemberJson = resultJson.getJSONObject("LoyaltyMember");
			memberLevelId = loyaltyMemberJson.getString("MemberLevelId");
			memberExpiryDate = loyaltyMemberJson.getString("ExpiryDate");
		}
		if(userMember.getVistaMemberCardNumber() != null && userMember.getUserName() != null){
			String memberNameCN = "";
			String memberNameEN = "";
			if(memberLevelId.equals("1")){
				memberNameCN = "三克会员";
				memberNameEN = "CINKER MEMBER";
			}		
			if(memberLevelId.equals("3")){
				memberNameCN = "三克摩登会员";
				memberNameEN = "CINKER MORDEN";
			}			
			if(memberLevelId.equals("6")){
				memberNameCN = "三克超级摩登会员";
				memberNameEN = "CINKER MORDEN SUPER";
			}		
			if(memberLevelId.equals("2")){
				memberNameCN = "三克经典会员";
				memberNameEN = "CINKER CLASSIC";
			}	
			if(memberLevelId.equals("4")){
				memberNameCN = "三克超级经典会员";
				memberNameEN = "CINKER CLASSIC SUPER";
			}
				
			if(regsuc != null){
				model.addAttribute("regsuc", regsuc);
			}
			model.addAttribute("memberNameCN", memberNameCN);
			model.addAttribute("memberNameEN", memberNameEN);
			model.addAttribute("memberLevelId", memberLevelId);
			model.addAttribute("userSessionId", userSessionId);
			model.addAttribute("userMember", userMember);
			model.addAttribute("expiryDate", memberExpiryDate);
		}else{
			response.setContentType("text/html;charset=UTF-8");
			String message = "请先注册会员";
			PrintWriter out = response.getWriter();
			out.print("<script language=\"javascript\"> alert('"+ message +"！');window.location.href='/cinker/usermember/vipRegister'</script>");	
			out.close();
		}
		return "user/membercard";
	}
	
	@RequestMapping(value="/vipPersonInformation" , method = {GET , POST})
	public String vipPersonInformation(Model model, String userSessionId, String clubId, String memberId){
		UserMember userMembers = ((UserMember) request.getSession()
				.getAttribute(Constant.SESSION_ATTRIBUTE_KEY));
		UserVipMember userMember = userMemberService.getUserVipMember(userMembers.getOpenID());
		model.addAttribute("userMember", userMember);
		model.addAttribute("userSessionId", userSessionId);
		model.addAttribute("clubId", clubId);
		model.addAttribute("memberId", memberId);
		return "user/vipPersonInformation";
	}
	
	@RequestMapping(value="/benifits" , method = {GET , POST})
	public String benifitsList(Model model, String userSessionId){
		String orderId = OrderNumUtil.getOrderId();
		model.addAttribute("userSessionId", userSessionId);
		model.addAttribute("orderId", orderId);
		return "user/benifits";
	}
	
	@RequestMapping(value="/qanda" , method = {GET , POST})
	public String qandapage(Model model, String userSessionId){
		
		return "user/qanda";
	}
	
	@RequestMapping(value="/profile" , method = {GET , POST})
	public  String profile(Model model, String firstName,String lastName,String email,String birthday,Integer sex,String userSessionId, String clubId, String memberId) throws IOException{
		UserMember userMembers = ((UserMember) request.getSession()
				.getAttribute(Constant.SESSION_ATTRIBUTE_KEY));
		UserVipMember userMember = userMemberService.getUserVipMember(userMembers.getOpenID());
		UserVipMember userVipMember = new UserVipMember();
		userVipMember.setFirstName(firstName);
		userVipMember.setLastName(lastName);
		userVipMember.setUserName(userMember.getUserName());
		userVipMember.setEmail(email);
		userVipMember.setBirthday(birthday);
		userVipMember.setSex(sex+"");
		userVipMember.setOpenID(userMember.getOpenID());
		userMemberService.profile(userVipMember);
		//更新VISTA
		String result = profileVista(firstName,lastName,email,userSessionId,clubId,memberId);
		String message = "修改信息失败";
		if(result != null){
			JSONObject resultJson = JSONObject.fromObject(result);
				if (0 == resultJson.getInt("Result")) {
					response.setContentType("text/html;charset=UTF-8");
					message = "修改信息成功";
					PrintWriter out = response.getWriter();
					out.print("<script language=\"javascript\"> alert('"+ message +"！');window.location.href='/cinker/usermember/getUserInfo'</script>");	
					out.close();
				}
		}
		model.addAttribute("message", message);
		model.addAttribute("userMember", userMember);
		return "user/user_member_center";
	}

	public String profileVista(String firstName,String lastName,String email,String userSessionId, String clubId, String memberId){
		String requestUrl = Constant.CONNECT_API_URL+"WSVistaWebClient/RESTLoyalty.svc/member/update";
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("OptionalClientClass",Constant.OPTIONAL_CLIENT_CLASS);
		jsonObject.put("OptionalClientId",Constant.OPTIONAL_CLIENT_ID);
		jsonObject.put("OptionalClientName",Constant.OPTIONAL_CLIENT_NAME);
		jsonObject.put("UserSessionId", userSessionId);
		
		JSONObject memberJsonParam = new JSONObject();
		
		memberJsonParam.put("MemberId",memberId);
		memberJsonParam.put("ClubID",clubId);
		memberJsonParam.put("FirstName",firstName);
		memberJsonParam.put("LastName",lastName);
		memberJsonParam.put("Email",email);
		
		jsonObject.put("LoyaltyMember", memberJsonParam);
		
		String result = CinkerHttpService.httpPostRequest(requestUrl, jsonObject.toString(),Constant.CONNECT_API_TOKEN);
		return result;
	}
	
	@RequestMapping(value="/pageToProfile" , method = {GET , POST})
	public String pageToProfile(Model model){
		UserMember userMembers = ((UserMember) request.getSession()
				.getAttribute(Constant.SESSION_ATTRIBUTE_KEY));
		UserVipMember userMember = userMemberService.getUserVipMember(userMembers.getOpenID());
		userMember.setFirstName("");
		userMember.setLastName("");
		userMember.setEmail("");
		model.addAttribute("userMember", userMember);
		return "user/vipPersonInformation";
	}

	// 20170713 Qin 会员历史记录查询
	@RequestMapping(value="/payforInformation" ,method= {GET , POST})
	public String payforInformation(Model model,String userSessionId){
		String result = getPayforInformation(userSessionId);
		List<MemberTransactions> memberTranslist = new ArrayList<MemberTransactions>();

		if(result!=null){
		JSONObject resultJson = JSONObject.fromObject(result);

			if (0 == resultJson.getInt("Result")) {
				Object jsonObject = resultJson.getString("MemberTransactions");
				if(jsonObject != "null"){
				JSONArray memberTransArray = resultJson.getJSONArray("MemberTransactions");

					for(int i=0;i<memberTransArray.size();i++){
						try{
							JSONObject memberTransArrayList = memberTransArray.getJSONObject(i);
							MemberTransactions memberTrans = new MemberTransactions();
						
							memberTrans.setBalanceTypeId(memberTransArrayList.getInt("BalanceTypeId"));
							memberTrans.setBalanceTypeName(memberTransArrayList.getString("BalanceTypeName"));
							memberTrans.setBoxOfficeValue(memberTransArrayList.getDouble("BoxOfficeValue"));
							memberTrans.setCinemaId(memberTransArrayList.getInt("CinemaId"));
							memberTrans.setCinemaName(memberTransArrayList.getString("CinemaName"));
							memberTrans.setCinemaNameAlt(memberTransArrayList.getString("CinemaNameAlt"));
							memberTrans.setConcessionsValue(memberTransArrayList.getDouble("ConcessionsValue"));
							memberTrans.setItemName(memberTransArrayList.getString("ItemName"));
							memberTrans.setMovieCode(memberTransArrayList.getString("MovieCode"));
							memberTrans.setMovieName(memberTransArrayList.getString("MovieName"));
							memberTrans.setMovieNameAlt(memberTransArrayList.getString("MovieNameAlt"));
							memberTrans.setPointsEarned(memberTransArrayList.getInt("PointsEarned"));
							memberTrans.setPointsRedeemed(memberTransArrayList.getInt("PointsRedeemed"));

							//memberTrans.setSessionTime(memberTransArrayList.getString("SessionTime"));
							//memberTrans.setTransactionDate(memberTransArrayList.getString("TransactionDate"));
							String sTime = memberTransArrayList.getString("SessionTime");
							String tDate = memberTransArrayList.getString("TransactionDate");
							
							sTime = changeDate(sTime);
							tDate = changeDate(tDate);
														
							SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY/MM/dd hh:mm:ss");
							
							if (sTime != null && !sTime.equals("") && !sTime.equals("null")){
								Date sTimeAfter = new Date(Long.parseLong(sTime));
								memberTrans.setSessionTime(simpleDateFormat.format(sTimeAfter));
							}else{
								memberTrans.setSessionTime("");
							}
							if (tDate != null && !tDate.equals("") && !tDate.equals("null")){
								Date dateAfter = new Date(Long.parseLong(tDate));
								memberTrans.setTransactionDate(simpleDateFormat.format(dateAfter));
							}else{
								memberTrans.setTransactionDate("");
							}

							memberTrans.setTransactionId(memberTransArrayList.getInt("TransactionId"));
							memberTrans.setTransactionLineItem(memberTransArrayList.getInt("TransactionLineItem"));
							memberTrans.setVistaTransactionId(memberTransArrayList.getInt("VistaTransactionId"));

							memberTranslist.add(memberTrans);
						
						}catch(Exception e){
							e.printStackTrace();
						}
					}
			    }
			}
		}
		model.addAttribute("userSessionId", userSessionId);
		model.addAttribute("memberTranslist", memberTranslist);
		
		return "user_member_test";
	}

	public String getPayforInformation(String userSessionId){
		String requestUrl = Constant.CONNECT_API_URL+"WSVistaWebClient/RESTLoyalty.svc/member/transactions";

		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("OptionalClientClass",Constant.OPTIONAL_CLIENT_CLASS);
		jsonObject.put("OptionalClientId",Constant.OPTIONAL_CLIENT_ID);
		jsonObject.put("OptionalClientName",Constant.OPTIONAL_CLIENT_NAME);
		jsonObject.put("UserSessionId", userSessionId);
		jsonObject.put("ReturnMemberTransactionDetails", "false");
		jsonObject.put("MaxResults", "100");
		
		String result = CinkerHttpService.httpPostRequest(requestUrl, jsonObject.toString(),Constant.CONNECT_API_TOKEN);
		return result;
	}	
	
	private String changeDate(String beforDate){
		int startLen = 0;
		int endLen = 0;

		if (beforDate != null && !beforDate.equals("") && !beforDate.equals("null")){
			for (int i=0; i<beforDate.length();i++) {
				if (beforDate.charAt(i) == '('){
					startLen = i + 1;
				}
				if (beforDate.charAt(i) == ')'){
					endLen = i;
				}
			}
			if (beforDate.contains("+")){
				return beforDate.substring(startLen, endLen-5);
			} else {
				return beforDate.substring(startLen, endLen);
			}
		}
		return beforDate;
		
	}
	
}
