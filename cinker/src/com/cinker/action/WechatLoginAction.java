package com.cinker.action;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cinker.config.AppConfig;
import com.cinker.config.ConfigLoader;
import com.cinker.config.MESSAGEXsend;
import com.cinker.constant.Constant;
import com.cinker.http.CinkerHttpService;
import com.cinker.model.UserMember;
import com.cinker.model.UserVipMember;
import com.cinker.service.UserMemberService;
import com.cinker.util.CardNumber;
import com.cinker.util.CustomDateConverter;
import com.cinker.util.OrderNumUtil;

@Controller
@RequestMapping("/wechat/login")
public class WechatLoginAction {
	@Autowired
	protected HttpServletRequest request;
	@Autowired
	protected HttpServletResponse response;
	@Autowired
	protected UserMemberService userMemberService;

	private static Logger logger = Logger.getLogger(WechatLoginAction.class);
	@Value("${APPID}")
	private String APPID;
	@Value("${WX_LOGIN_AUTH_RETURN_SERVER_URL}")
	private String WX_LOGIN_AUTH_RETURN_SERVER_URL;
	@Value("${APP_SECRECT}")
	private String APP_SECRECT;

	@RequestMapping(value = "/auth", method = { GET, POST })
	public String auth(Model model) {
		String authUrl = "";
		try {
			authUrl = Constant.WX_LOGIN_AUTH_URL
					+ "?appid="
					+ APPID
					+ "&redirect_uri="
					+ URLEncoder.encode(WX_LOGIN_AUTH_RETURN_SERVER_URL,
							"UTF-8")
					+ "&response_type=code&scope=snsapi_userinfo&state="
					+ request.getRequestURI() + "#wechat_redirect";
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("authurl:" + authUrl);
		return "redirect:" + authUrl;
	}

	@RequestMapping(value = "/getCode", method = { GET, POST })
	public String getCode(String code, String state, Model model)
			throws UnsupportedEncodingException {
		logger.info("wechat login get code:" + code);
		String requestAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
				+ APPID
				+ "&secret="
				+ APP_SECRECT
				+ "&code="
				+ code
				+ "&grant_type=authorization_code";
		CinkerHttpService cinkerHttpService = CinkerHttpService.getInstance();
		String accessTokenResult = cinkerHttpService
				.httpGetRequestForWechatLogin(requestAccessTokenUrl);
		JSONObject accessTokenJson = JSONObject.fromObject(accessTokenResult);
		logger.info("access_token result:" + accessTokenJson);
		String accessToken = accessTokenJson.getString("access_token");
		String openID = accessTokenJson.getString("openid");
		List<UserMember> userMemberList = userMemberService
				.getUserMember(openID);
		UserMember userMember = null;
		if (userMemberList != null && userMemberList.size() > 0) {
			userMember = userMemberList.get(0);
			userMember.setUserNickName(URLDecoder.decode(userMember.getUserNickName(), "utf-8"));
		}
		if (userMember == null) {
			String requestUserInfourl = "https://api.weixin.qq.com/sns/userinfo?access_token="
					+ accessToken + "&openid=" + openID + "&lang=zh_CN";
			String userInfoResult = cinkerHttpService
					.httpGetRequestForWechatLogin(requestUserInfourl);
			JSONObject userInfoJson = JSONObject.fromObject(userInfoResult);
			logger.info("userinfo result:" + userInfoJson);
			userMember = new UserMember();
			userMember.setUserNumber("C" + OrderNumUtil.getOrderId());
			userMember.setOpenID(openID);
			userMember.setUserNickName(URLEncoder.encode(
					userInfoJson.getString("nickname"), "utf-8"));
			userMember.setUserSex(userInfoJson.getInt("sex"));
			userMember
					.setUserHeadImageUrl(userInfoJson.getString("headimgurl"));
			userMember.setLoginTime(new Date());
			userMemberService.addUserMember(userMember);
		}
		request.getSession().setAttribute(Constant.SESSION_ATTRIBUTE_KEY,
				userMember);
		// return "redirect:/film/getScheduledFilm";
		return "redirect:" + state;
	}

	@RequestMapping(value = "/register", method = { GET, POST })
	public String register(String username, String password, String firstName,
			String lastName, String email, String phone, Long date, String sex,String areaNumber,String randomNumber,
			Model model) throws IOException {
		String mess = (String) request.getSession().getAttribute(phone+Constant.SESSION_VALIDATE_KEY);
		if(mess == null){
			response.setContentType("text/html;charset=UTF-8");
			String message = "请获取验证码";
			PrintWriter out = response.getWriter();
			out.print("<script language=\"javascript\"> alert('"+ message +"！');window.location.href='/cinker/usermember/vipRegister'</script>");	
			out.close();
			return "user/register";
		}
		if(!mess.equals(randomNumber)){
			response.setContentType("text/html;charset=UTF-8");
			String message = "验证码验证失败";
			PrintWriter out = response.getWriter();
			out.print("<script language=\"javascript\"> alert('"+ message +"！');window.location.href='/cinker/usermember/vipRegister'</script>");	
			out.close();
			return "user/register";
		}
		UserMember userMembers = ((UserMember) request.getSession()
				.getAttribute(Constant.SESSION_ATTRIBUTE_KEY));
		UserVipMember userMember = userMemberService.getUserVipMember(userMembers.getOpenID());
		String cardNumber = userMemberService
				.checkVistaMemberCardNumber(userMember.getOpenID());
		if (null !=cardNumber && 13 == cardNumber.length()) {
			response.setContentType("text/html;charset=UTF-8");
			String message = "你已经是三克会员";
			PrintWriter out = response.getWriter();
			out.print("<script language=\"javascript\"> alert('"+ message +"！');window.location.href='/cinker/usermember/getUserInfo'</script>");	
			out.close();
		}
		JSONObject jsonParam = new JSONObject();
		JSONObject loyaltyMember = new JSONObject();
		loyaltyMember.put("FirstName", firstName);
		loyaltyMember.put("LastName", lastName);
		String vistaMemberCardNumber = CardNumber
				.getCardNumber(userMemberService,areaNumber);
		logger.info(vistaMemberCardNumber);
		loyaltyMember.put("CardNumber", vistaMemberCardNumber);
		loyaltyMember.put("MobilePhone", phone);
		loyaltyMember.put("Email", email);
		loyaltyMember.put("ClubID", "1");
		loyaltyMember.put("MemberLevelId", 1);
		loyaltyMember.put("UserName", username);
		loyaltyMember.put("Password", password);
		loyaltyMember.put("PreferredGenres", "");
		loyaltyMember.put("PIN", "");
		jsonParam.put("LoyaltyMember", loyaltyMember);
		jsonParam.put("OptionalClientClass", Constant.OPTIONAL_CLIENT_CLASS);
		jsonParam.put("OptionalClientId", Constant.OPTIONAL_CLIENT_ID);
		jsonParam.put("OptionalClientName", Constant.OPTIONAL_CLIENT_NAME);
		String userSessionId = OrderNumUtil.getOrderId();
		jsonParam.put("UserSessionId", userSessionId);
		String result = CinkerHttpService.httpPostRequest(
				Constant.CONNECT_API_URL
						+ "WSVistaWebClient/RESTLoyalty.svc/member/create",
				jsonParam.toString(),Constant.CONNECT_API_TOKEN);
		logger.info("Vista complete order request:" + jsonParam.toString());
		logger.info("Vista complete order response:" + result);
		JSONObject resultJson = JSONObject.fromObject(result);
		if(resultJson.getInt("Result") == 3){
			response.setContentType("text/html;charset=UTF-8");
			String message = resultJson.getString("ErrorDescription");
			PrintWriter out = response.getWriter();
			out.print("<script language=\"javascript\"> alert('"+ message +"！');window.location.href='/cinker/usermember/vipRegister'</script>");	
			out.close();
		}
		if(resultJson.getInt("Result") == 20||resultJson.getInt("Result") == 43){
			response.setContentType("text/html;charset=UTF-8");
			String message = "该手机已被注册";
			PrintWriter out = response.getWriter();
			out.print("<script language=\"javascript\"> alert('"+ message +"！');window.location.href='/cinker/usermember/vipRegister'</script>");	
			out.close();
		}
		logger.info("Vista complete order response:" + result);
		if (resultJson.getInt("Result") == 0) {

			UserVipMember userVipMember = new UserVipMember();
			userVipMember.setOpenID(userMember.getOpenID());
			userVipMember.setUserName(username);
			userVipMember.setPassword(password);
			//model.addAttribute("vistaMemberCardNumber", vistaMemberCardNumber);
			userVipMember.setVistaMemberCardNumber(vistaMemberCardNumber);
			userVipMember.setFirstName(firstName);
			userVipMember.setLastName(lastName);
			userVipMember.setEmail(email);
			userVipMember.setPhone(phone);
			if(null !=date){
			userVipMember.setBirthday(new SimpleDateFormat("yyyy-MM-dd")
					.format(new Date(date)));
			}
			if (("1").equals(sex)) {
				userVipMember.setSex("男");
			} else if (("2").equals(sex)) {
				userVipMember.setSex("女");
			}
			userMemberService.udateToVip(userVipMember);
			
			model.addAttribute("userSessionId", userSessionId);
			model.addAttribute("regsuc", "true"); 
            return "redirect:/usermember/getMemberCard";
			//return "register_ok";
		} else {
			response.setContentType("text/html;charset=UTF-8");
			String message = "网络连接错误";
			PrintWriter out = response.getWriter();
			out.print("<script language=\"javascript\"> alert('"+ message +"！');window.location.href='/cinker/usermember/getUserInfo'</script>");	
			out.close();
			model.addAttribute("message", "网络连接错误");
			return "user/register";
		}
	}

	@RequestMapping(value = "/getRandomNumber", method = { GET, POST })
	public @ResponseBody String getRandomNumber(@RequestBody String phone, Model model) throws IOException{
		String[] tel = phone.split("=");
		phone = tel[1];
		String result = "";
		List<UserVipMember> userVipMemberList = userMemberService.getUserName(phone);
		if(userVipMemberList.size()>0 && userVipMemberList != null){
			model.addAttribute("message", "该手机已注册");
			result = "该手机已注册";
			return result;
		}
		String randomNumber = OrderNumUtil.getRandomNumber();
		AppConfig config = ConfigLoader.load(ConfigLoader.ConfigType.Message);
		MESSAGEXsend submail = new MESSAGEXsend(config);
		submail.addTo(phone);
		submail.setProject("iQFXd4");
		submail.addVar("code", randomNumber);
		submail.xsend();
		HttpSession session = request.getSession();
		session.setAttribute(phone+Constant.SESSION_VALIDATE_KEY, randomNumber);
		session.setMaxInactiveInterval(90);
		result = "获取验证码成功";
		return result;
	}
}
