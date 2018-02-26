package com.cinker.common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.cinker.constant.Constant;
import com.cinker.model.UserMember;
import com.cinker.service.UserMemberService;

public class WechatLoginInterceptor implements HandlerInterceptor {
	@Autowired
	UserMemberService userMemberService;
	private static Logger logger = Logger.getLogger(WechatLoginInterceptor.class);
	@Value("${APPID}")
	private  String APPID;
	@Value("${WX_LOGIN_AUTH_RETURN_SERVER_URL}")
	private String WX_LOGIN_AUTH_RETURN_SERVER_URL;//微信授权登录回调
	@Override
	public void afterCompletion(HttpServletRequest arg0,HttpServletResponse arg1, Object arg2, Exception arg3)throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String url = ""; 
		url = request.getScheme() +"://" + request.getServerName()+ ":" +request.getServerPort()+"/cinker"+ request.getServletPath(); 
		if(request.getQueryString() != null){ 
		url +="?" + request.getQueryString();
		} 
		UserMember userMember=(UserMember)request.getSession().getAttribute(Constant.SESSION_ATTRIBUTE_KEY);
		logger.info("userMember is null:"+(userMember==null));
		if(userMember==null){
			String authUrl="";
			try {
				authUrl = Constant.WX_LOGIN_AUTH_URL+"?appid="+APPID+"&redirect_uri="+URLEncoder.encode(WX_LOGIN_AUTH_RETURN_SERVER_URL,"UTF-8")+"&response_type=code&scope=snsapi_userinfo&state="+ url +"#wechat_redirect";
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.sendRedirect(authUrl);
			return false;
		}
		else{
			return true;
		}
	}
	
	
}
