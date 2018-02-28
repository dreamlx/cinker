package com.cinker.service.impl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.OrderUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cinker.action.UserMemberAction;
import com.cinker.constant.Constant;
import com.cinker.dao.CinemaDao;
import com.cinker.dao.FilmDao;
import com.cinker.dao.UserMemberDao;
import com.cinker.http.CinkerHttpService;
import com.cinker.model.Cinema;
import com.cinker.model.Film;
import com.cinker.model.FilmContentImage;
import com.cinker.model.FilmInfo;
import com.cinker.model.FilmOrder;
import com.cinker.model.FilmsSession;
import com.cinker.model.GetTicketsInfo;
import com.cinker.model.Screen;
import com.cinker.model.SelectedSeat;
import com.cinker.model.TicketType;
import com.cinker.model.UserMember;
import com.cinker.model.UserVipMember;
import com.cinker.service.FilmService;
import com.cinker.service.UserMemberService;
import com.cinker.util.OrderNumUtil;
import com.cinker.util.SortClass;
import com.danga.MemCached.MemCachedClient;

@Service
public class FilmServiceImpl implements FilmService{
	
	@Autowired
	FilmDao filmDao;
	
	@Autowired
	CinemaDao cinemaDao; 
	
	@Autowired
	UserMemberDao userMemberDao; 
	
	@Autowired
	UserMemberService userMemberService; 
	
	@Autowired
	protected MemCachedClient memCachedClient;
	
	@Autowired
	protected HttpServletRequest request;
	
	protected Logger logger=Logger.getLogger(FilmServiceImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Film> getFilms(String cinemaId) {
		List<Film> films = null;
//		memCachedClient.delete(cinemaId);
		if(memCachedClient.get(cinemaId) != null){
			 films =  (List<Film>) memCachedClient.get(cinemaId);
			 matchFilms(films);
			 logger.info("get getScheduledFilm from memcached");
			 return films;
		}
		
		String connectapiurl;
		String connectapitoken;
		String cinemaType = getCinema(cinemaId).getType();
		
		if ("Pictures".equals(cinemaType)){
			connectapiurl = Constant.CONNECT_API_URL;
			connectapitoken = Constant.CONNECT_API_TOKEN;
		} else {
			connectapiurl = Constant.CONNECT_API_URL_C;
			connectapitoken = Constant.CONNECT_API_TOKEN_C;
		}
		
		String url = connectapiurl + "WSVistaWebClient/OData.svc/GetNowShowingScheduledFilms?$format=json&cinemaId='"+ cinemaId +"'&$expand=Sessions/Attributes&$select=ScheduledFilmId,FilmHOCode,Title,RunTime,ShortCode,RatingDescription,Synopsis,Sessions/SessionId,Sessions/ScreenNumber,Sessions/ScreenName,Sessions/Showtime,Sessions/Attributes/Description";
		String result = CinkerHttpService.getInstance().httpGetRequest(url,connectapitoken);
		films = new ArrayList<Film>();
		JSONObject resultJson = JSONObject.fromObject(result);
		JSONArray  jsonArray = resultJson.getJSONArray("value");
		for(int i = 0; i < jsonArray.size(); i++){
			Film film = new Film();
			JSONObject valueJson = jsonArray.getJSONObject(i);
			film.setCinemaId(cinemaId);
			film.setScheduledFilmId(valueJson.getString("FilmHOCode"));
			film.setShortCode(valueJson.getString("ShortCode"));
			film.setTitle(valueJson.getString("Title"));
			film.setSynopsis(valueJson.getString("Synopsis"));
			film.setRatingDescription(valueJson.getString("RatingDescription"));
			film.setRunTime(valueJson.getString("RunTime"));
			JSONArray  sessionArray = valueJson.getJSONArray("Sessions");
			for(int j = 0; j < sessionArray.size(); j++){
				try{
					
					FilmsSession filmSession = new FilmsSession();
					JSONObject sessionJson = sessionArray.getJSONObject(j);
					filmSession.setSessionId(sessionJson.getString("SessionId"));
					String showTime = sessionJson.getString("Showtime");
					if(!showTime.isEmpty()){
						showTime = showTime.replace("T", " ");
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						filmSession.setShowtime(sdf.parse(showTime));
					}
					filmSession.setScreenNumber(sessionJson.getString("ScreenNumber"));
					filmSession.setScreenName(sessionJson.getString("ScreenName"));
					JSONArray attributeArray = sessionJson.getJSONArray("Attributes");
					String[] descriptions = new String[attributeArray.size()];
					for(int k = 0; k < attributeArray.size(); k++){
						JSONObject attributeJson = attributeArray.getJSONObject(k);
						String description = attributeJson.getString("Description");						
						descriptions[k] = new String(description);						
					}
					filmSession.setDescription(descriptions);
					film.getSessions().add(filmSession);
					
				}catch (ParseException e) {
					e.printStackTrace();
				}
								
			}
			List<FilmsSession> sessions = film.getSessions();
			SortClass sort = new SortClass();
			Collections.sort(sessions,sort);
			if(sessions.size() > 0){
				SimpleDateFormat df = new SimpleDateFormat("MM/dd");
				String filmDateTime = df.format(sessions.get(0).getShowtime());
				film.setDateTime(filmDateTime);
				//add By Ike 2017/03/23
				//直接输出一个带年份的日期做排序用
				SimpleDateFormat sortDate = new SimpleDateFormat("YYYY/MM/dd hh:mm:ss");
				String filmSortDate = sortDate.format(sessions.get(0).getShowtime());
				film.setFilmSortDate(filmSortDate);
			}
			films.add(film);			
		}
		matchFilms(films);
		memCachedClient.set(cinemaId, films, new Date(30*60*1000));
		return films;
	}
	//将本地库的Films和缓存或者接口的影讯信息进行比较
	protected void matchFilms(List<Film> films){
		//从数据库中读出管理影讯
		List<FilmInfo> filmInfoDBList=cinemaDao.getFilmInfo();
		if(filmInfoDBList!=null && filmInfoDBList.size()>0){
			Map<String,FilmInfo> filmInfoMap=new  HashMap<String, FilmInfo>();
			for(FilmInfo fiDB:filmInfoDBList){
				String filmId=fiDB.getFilmId();
				if(!filmInfoMap.containsKey(filmId)){
					filmInfoMap.put(filmId,fiDB);
				}
			}
			for(Film fiVista:films){
				if(filmInfoMap.get(fiVista.getScheduledFilmId())!=null){
					FilmInfo filmDB=filmInfoMap.get(fiVista.getScheduledFilmId());
					fiVista.setSurfaceImageUrl(filmDB.getSurfaceImage());
					fiVista.setChineseName(filmDB.getChineseName());
					fiVista.setEnglishName(filmDB.getEnglishName());
					fiVista.setRunTime(filmDB.getRunTime());
					fiVista.setSynopsis("导演:"+filmDB.getDirector()+"<br/>"+"主演:"+filmDB.getStarringActor()+"<br/>"+"编剧:"+filmDB.getScriptWriter()+"<br/>"
							+"上映时间:"+(StringUtils.isEmpty(filmDB.getShowTime())? "":filmDB.getShowTime().substring(0,10)));
					fiVista.setFilmDType(filmDB.getFilmType());
					fiVista.setFilmActors(filmDB.getStarringActor());
					fiVista.setRatingDescription(filmDB.getLanguage());
					fiVista.setCinecism(filmDB.getCinecism());
				}
			}
		}
	}
	@Override
	public String addOrder(String orderId, FilmOrder filmOrder) {
		String result = "";
		if(orderId != null && filmOrder != null){
			
			String connectapiurl;
			String connectapitoken;
			if ("Pictures".equals(filmOrder.getCinemaType())){
				connectapiurl = Constant.CONNECT_API_URL;
				connectapitoken = Constant.CONNECT_API_TOKEN;
			} else {
				connectapiurl = Constant.CONNECT_API_URL_C;
				connectapitoken = Constant.CONNECT_API_TOKEN_C;
			}
			
			String requestUrl = connectapiurl + "WSVistaWebClient/RestTicketing.svc/order/tickets";		
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("UserSessionId",orderId);
			jsonObject.put("CinemaId",filmOrder.getCinemaId());
			jsonObject.put("SessionId",filmOrder.getSessionId());
			//-----------------------------------------------------------
			//  20170718 Qin 会员下单编辑
			int loyaltyRecognitionId = 0;
			String memberLevelId = filmOrder.getMemberLevelId();
			if (filmOrder.getFreeRightFlg() == 1) {
				loyaltyRecognitionId = 12;
			}else if ("1".compareTo(memberLevelId) <= 0){
				//Cinema类型影院票价折扣RegcognitionID表
				if ("Cinema".equals(filmOrder.getCinemaType())){
/*					if ("1".equals(memberLevelId)) {
						//微信会员
						loyaltyRecognitionId = 7;
					} else if ("2".equals(memberLevelId)) {
						//经典会员
						loyaltyRecognitionId = 6;
					} else if ("4".equals(memberLevelId)) {
						//超级经典会员
						loyaltyRecognitionId = 2;
					} else if ("3".equals(memberLevelId)) {
						//摩登会员
						loyaltyRecognitionId = 4;
					} else if ("6".equals(memberLevelId)) {
						//超级摩登会员
						loyaltyRecognitionId = 13;
					}*/
					loyaltyRecognitionId = 0;
					//Pictures类型影院票价折扣RegcognitionID表：
				}else {
					if ("1".equals(memberLevelId)) {
						//微信会员
						loyaltyRecognitionId = 8;
					} else if ("2".equals(memberLevelId)) {
						//经典会员
						loyaltyRecognitionId = 14;
					} else if ("6".equals(memberLevelId)) {
						//超级经典会员
						loyaltyRecognitionId = 17;
					}
				}
			}

			List<TicketType> ticketTypes = filmOrder.getTicketTypes();
			JSONArray ticketArray = new JSONArray();
			for(TicketType ticketType : ticketTypes){			
				JSONObject ticketJson = new JSONObject();
				ticketJson.put("TicketTypeCode", ticketType.getTicketTypeCode());
				ticketJson.put("Qty", ticketType.getQty());
				ticketJson.put("PriceInCents", ticketType.getPriceInCents());
				ticketJson.put("BookingFeeOverride", ticketType.getBookingFeeOverride());
				
				//  20170718 Qin 会员下单编辑
				//	20170902 Ike need not this params
				//if (loyaltyRecognitionId > 0){
					//ticketJson.put("LoyaltyRecognitionId", loyaltyRecognitionId);
					//ticketJson.put("LoyaltyRecognitionSequence", 1);
				//}
				
				ticketArray.add(ticketJson);
			}
			jsonObject.put("TicketTypes", ticketArray);
			//-----------------------------------------------------------				
			jsonObject.put("ReturnOrder",true);
			jsonObject.put("BookingFeeOverride",null);
			//-----------------------------------------------------------
			List<SelectedSeat> selectedSeats = filmOrder.getSelectedSeats();
			JSONArray selectedArray = new JSONArray();
			for(SelectedSeat selectedSeat : selectedSeats){
				JSONObject selectedSeatJson = new JSONObject();
				selectedSeatJson.put("AreaCategoryCode", selectedSeat.getAreaCategoryCode());
				selectedSeatJson.put("AreaNumber", selectedSeat.getAreaNumber());
				selectedSeatJson.put("RowIndex", selectedSeat.getRowIndex());
				selectedSeatJson.put("ColumnIndex", selectedSeat.getColumnIndex());
				selectedArray.add(selectedSeatJson);
			}		
			jsonObject.put("SelectedSeats", selectedArray);
			//-----------------------------------------------------------
			jsonObject.put("OptionalClientClass",Constant.OPTIONAL_CLIENT_CLASS);
			jsonObject.put("OptionalClientId",Constant.OPTIONAL_CLIENT_ID);
			jsonObject.put("OptionalClientName",Constant.OPTIONAL_CLIENT_NAME);
			logger.info("add ticket request:"+jsonObject.toString());		
			result = CinkerHttpService.httpPostRequest(requestUrl, jsonObject.toString(),connectapitoken);
			logger.info("add ticket response:"+result);
		}		
		return result;
	}

	@Override
	public void saveOrder(FilmOrder filmOrder) {
		filmDao.saveOrder(filmOrder);		
	}


	@Override
	public void saveTicketType(List<TicketType> ticketTypes) {
		filmDao.saveTicketType(ticketTypes);		
	}

	@Override
	public FilmOrder getFilmOrder(String orderNumber) {		
		return filmDao.getFilmOrder(orderNumber);
	}

	@Override
	public TicketType getTicketType(String orderNumber) {
		return filmDao.getTicketType(orderNumber);
		
	}
	
	
	// TODO 需要增加vista交易号
	@Override
	public void updateOrderSuccess(int status, Date endTime,
			String bookingNumber, String bookingId, String vistaTransNumber, String orderNumber) {
		// TODO Auto-generated method stub
		filmDao.updateOrderSuccess(status, endTime, bookingNumber, bookingId, vistaTransNumber, orderNumber);
	}	

	@Override
	public void cancleOrder(int status,String id) {
		// TODO Auto-generated method stub
		filmDao.cancleOrder(status, id);
	}
	
	@Override
	public void getFilmTicket(String cinemaId, String sessionId,GetTicketsInfo getTicketsInfo,String orderId,String scheduledFilmId) {
		UserMemberAction uma = new UserMemberAction();
		UserMember userMember = ((UserMember) request.getSession()
				.getAttribute(Constant.SESSION_ATTRIBUTE_KEY));
		UserVipMember userVipMember = userMemberService.getUserVipMember(userMember.getOpenID());
//		UserVipMember userVipMember = userMemberService.getUserVipMember("o4voAwU7hli4D3B1ZVTrNxfB7gWE");

		String cinemaType = getCinema(cinemaId).getType();
		String connectapiurl;
		String connectapitoken;
		if ("Pictures".equals(cinemaType)){
			connectapiurl = Constant.CONNECT_API_URL;
			connectapitoken = Constant.CONNECT_API_TOKEN;
		} else {
			connectapiurl = Constant.CONNECT_API_URL_C;
			connectapitoken = Constant.CONNECT_API_TOKEN_C;
		}

		String ValidateResult = uma.validateVip(userVipMember, orderId,cinemaType);
		JSONObject validateJson =  null;
		if ( !StringUtils.isEmpty(ValidateResult)) {
			validateJson = JSONObject.fromObject(ValidateResult);
		}
		
		if(validateJson != null && 0 == validateJson.getInt("Result")){

			// 20170713 Qin
			JSONObject loyaltyMemberJson = validateJson.getJSONObject("LoyaltyMember");
			String memberLevelId = "0";
			String headOfficeGroupCode = "0";
			memberLevelId = loyaltyMemberJson.getString("MemberLevelId");
			
			if ("2".equals(memberLevelId)) {
				//经典会员
				headOfficeGroupCode = "HOTK000006";
			} else if ("4".equals(memberLevelId)) {
				//超级经典会员
				headOfficeGroupCode = "HOTK000007";
			} else if ("3".equals(memberLevelId)) {
				//摩登会员
				headOfficeGroupCode = "HOTK000008";
			} else if ("6".equals(memberLevelId)) {
				//超级摩登会员
				headOfficeGroupCode = "HOTK000009";
			} else {
				//微信会员
				headOfficeGroupCode = "HOTK000002";
			}

			getTicketsInfo.setMemberLevelId(memberLevelId);
			getTicketsInfo.setCinemaType(cinemaType);
			
			
			
			if("Pictures".equals(cinemaType) && "3".equals(memberLevelId)){
				headOfficeGroupCode = "HOTK000001";
			}
			
			JSONObject VipTicketsJson = null;
			// Use memCachedClient for save FilmTicket 2017/11/10
			if(memCachedClient.get("VipFilmTicket" + cinemaId + sessionId + memberLevelId) != null){
				VipTicketsJson = (JSONObject) memCachedClient.get("VipFilmTicket" + cinemaId + sessionId + memberLevelId);
				//logger.info("**********USE memCachedClient find VIPTickets information:"+ VipTicketsJson.toString());
			}else{
				String requestUrl = connectapiurl + "WSVistaWebClient/RESTData.svc/cinemas/"+cinemaId+"/sessions/"+sessionId+"/tickets?salesChannelFilter="+Constant.SALES_CHANNEL_FILTER+"&userSessionId="+orderId+"";
				String result = CinkerHttpService.getInstance().httpGetRequest(requestUrl,connectapitoken);	
				//logger.info("GetVipMemberTickets request: "+result);
				VipTicketsJson = JSONObject.fromObject(result);
				memCachedClient.set("VipFilmTicket" + cinemaId + sessionId + memberLevelId, VipTicketsJson, new Date(30*60*1000));
			}
			String vipTicketsResponseCode = VipTicketsJson.getString("ResponseCode");
			//logger.info("vipTicketsResponseCode: "+vipTicketsResponseCode);
			if(vipTicketsResponseCode.equals("0")){
				JSONArray jsonArray = VipTicketsJson.getJSONArray("Tickets");
				for(int i = 0; i < jsonArray.size(); i++){
					JSONObject ticketJson = jsonArray.getJSONObject(i);
					if(StringUtils.isEmpty(scheduledFilmId) || scheduledFilmId.substring(0, 3).equals("bjc") || scheduledFilmId.substring(0, 4).equals("bjpt")|| scheduledFilmId.substring(0, 2).equals("cp") || scheduledFilmId.substring(0, 4).equals("bjcp") || scheduledFilmId.substring(0, 4).equals("shcp")){
						if(ticketJson.getString("HeadOfficeGroupingCode").equals("HOTK000012")){
							//原价
							//logger.info("GetVipMemberTickets by HOTK000012 normal movie og price");
							int priceInCent = ticketJson.getInt("PriceInCents");
							DecimalFormat doubledf = new DecimalFormat("#0.00");
							getTicketsInfo.setPriceNoDiscount(doubledf.format(priceInCent/100));
							getTicketsInfo.setTicketTypeCodeNoDiscount(ticketJson.getString("TicketTypeCode"));
						}
						if(ticketJson.getString("HeadOfficeGroupingCode").equals(headOfficeGroupCode)){
							//logger.info("GetVipMemberTickets by "+headOfficeGroupCode+" normal movie price");
							int priceInCent = ticketJson.getInt("PriceInCents");
							DecimalFormat doubledf = new DecimalFormat("#0.00");
							getTicketsInfo.setPriceInCents(doubledf.format(priceInCent/100));
							getTicketsInfo.setTicketTypeCode(ticketJson.getString("TicketTypeCode"));
							
						}
					}else if(scheduledFilmId.substring(0, 4).equals("bjnt") || scheduledFilmId.substring(0, 2).equals("nt") || scheduledFilmId.substring(0, 4).equals("shnt")){
						//活动票价
						if(ticketJson.getString("HeadOfficeGroupingCode").equals("HOTK000005")){
							//logger.info("GetVipMemberTickets by HOTK000005 nt live movie price");
							int priceInCent = ticketJson.getInt("PriceInCents");
							DecimalFormat doubledf = new DecimalFormat("#.00");
							getTicketsInfo.setPriceInCents(doubledf.format(priceInCent/100));
							getTicketsInfo.setTicketTypeCode(ticketJson.getString("TicketTypeCode"));
							getTicketsInfo.setPriceNoDiscount(doubledf.format(priceInCent/100));
							getTicketsInfo.setTicketTypeCodeNoDiscount(ticketJson.getString("TicketTypeCode"));
						}
					}
				}
			}
		}else{
			if(cinemaId != null && sessionId != null){
				JSONObject resultJson = null;
				String headOfficeGroupCode = "0";
				if(memCachedClient.get("FilmTicket" + cinemaId + sessionId) != null){
					resultJson = (JSONObject) memCachedClient.get("FilmTicket" + cinemaId + sessionId);
//					logger.info("**********USE memCachedClient find Ticket information");
				}else{
					String requestUrl = connectapiurl + "WSVistaWebClient/RESTData.svc/cinemas/"+cinemaId+"/sessions/"+sessionId+"/tickets";
					String result = CinkerHttpService.getInstance().httpGetRequest(requestUrl,connectapitoken);	
					resultJson = JSONObject.fromObject(result);
					memCachedClient.set("FilmTicket" + cinemaId + sessionId, resultJson, new Date(30*60*1000));
				}			
				String responseCode = resultJson.getString("ResponseCode");
				if(responseCode.equals("0")){

					getTicketsInfo.setMemberLevelId("");
					getTicketsInfo.setCinemaType(cinemaType);
					
					JSONArray jsonArray = resultJson.getJSONArray("Tickets");
					for(int i = 0; i < jsonArray.size(); i++){
						JSONObject ticketJson = jsonArray.getJSONObject(i);
						if(StringUtils.isEmpty(scheduledFilmId) || scheduledFilmId.substring(0, 3).equals("bjc") || scheduledFilmId.substring(0, 4).equals("bjpt")|| scheduledFilmId.substring(0, 2).equals("cp") || scheduledFilmId.substring(0, 4).equals("bjcp") || scheduledFilmId.substring(0, 4).equals("shcp")){
							if(ticketJson.getString("HeadOfficeGroupingCode").equals("HOTK000012")){
								//原价
								int priceInCent = ticketJson.getInt("PriceInCents");
								DecimalFormat doubledf = new DecimalFormat("#0.00");
								getTicketsInfo.setPriceNoDiscount(doubledf.format(priceInCent/100));
								getTicketsInfo.setTicketTypeCodeNoDiscount(ticketJson.getString("TicketTypeCode"));
							}
							
							if(ticketJson.getString("HeadOfficeGroupingCode").equals("HOTK000001")){
								int priceInCent = ticketJson.getInt("PriceInCents");
								DecimalFormat doubledf = new DecimalFormat("#0.00");
								getTicketsInfo.setPriceInCents(doubledf.format(priceInCent/100));
								getTicketsInfo.setTicketTypeCode(ticketJson.getString("TicketTypeCode"));
								
							}
						}else if(scheduledFilmId.substring(0, 4).equals("bjnt") || scheduledFilmId.substring(0, 2).equals("nt") || scheduledFilmId.substring(0, 4).equals("shnt")){
							if(ticketJson.getString("HeadOfficeGroupingCode").equals("HOTK000005")){
								//活动
								int priceInCent = ticketJson.getInt("PriceInCents");
								DecimalFormat doubledf = new DecimalFormat("#.00");
								getTicketsInfo.setPriceInCents(doubledf.format(priceInCent/100));
								getTicketsInfo.setTicketTypeCode(ticketJson.getString("TicketTypeCode"));
								getTicketsInfo.setPriceNoDiscount(doubledf.format(priceInCent/100));
								getTicketsInfo.setTicketTypeCodeNoDiscount(ticketJson.getString("TicketTypeCode"));
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void updateOrderPaymentID(int paymentID, String orderNumber) {		
		filmDao.updateOrderPaymentID(paymentID,orderNumber);
	}

	@Override
	public List<FilmContentImage> getFilmContentImage(String scheduleFilmId) {		
		return filmDao.getFilmContentImage(scheduleFilmId);
	}

	@Override
	public List<Cinema> getCinemas(String cityId,String cinemaType) {		
		return filmDao.getCinemas(cityId,cinemaType);
	}
	
	@Override
	public List<Cinema> getCinemasByType(String cinemaType) {		
		return filmDao.getCinemasByType(cinemaType);
	}

	@Override
	public List<Cinema> getCinemas() {		
		return filmDao.getCinemas();
	}
	
	@Override
	public List<FilmOrder> getFilmOrderListByUserNumber(String userNumber,String cinemaId) {
		return filmDao.getFilmOrderListByUserNumber(userNumber,cinemaId);
	}

	@Override
	public Cinema getCinema(String id) {
		return filmDao.getCinema(id);
	}
	@Override
	public List<FilmOrder> getOrderDetailByOrderNumber(String orderNumber) {
		return filmDao.getOrderDetailByOrderNumber(orderNumber);
	}

	@Override
	public List<Screen> getScreens(String cinemaId) {		
		return filmDao.getScreens(cinemaId);
	}

	@Override
	public int getTicketsCount(String userNumber, String cinemaId, String sessionId, String showTime) {		
		return filmDao.getTicketsCount(userNumber,cinemaId,sessionId,showTime);
	}

	@Override
	public int getRestTickets(String userNumber, String memberLevelId, String cinemaId, String cinemaType, String sessionId, String sessionShowTime, String showTime) {		
		String filmShowTime = "";
		if (StringUtils.isEmpty(sessionShowTime)){
			filmShowTime = showTime;
		}else{
			filmShowTime = sessionShowTime + " " + showTime;
		}
		int maxTicketsCount = 0;
		int restTicketsCount = 0;
		if( "2".equals(memberLevelId)){
			maxTicketsCount = Constant.MAX_TICKETS_COUNT_LEVEL2;
		}else if( "3".equals(memberLevelId)){
			maxTicketsCount = Constant.MAX_TICKETS_COUNT_LEVEL3;
		}else if( "4".equals(memberLevelId)){
			maxTicketsCount = Constant.MAX_TICKETS_COUNT_LEVEL4;
		}else if( "6".equals(memberLevelId) && "Pictures".equals(cinemaType)){
			maxTicketsCount = Constant.MAX_TICKETS_COUNT_LEVEL6P;
		}else if( "6".equals(memberLevelId) && "Cinema".equals(cinemaType)){
			maxTicketsCount = Constant.MAX_TICKETS_COUNT_LEVEL6C;
		}else {
			maxTicketsCount = 100;
		}
		
		int ticketsCount = getTicketsCount(userNumber,cinemaId,sessionId,filmShowTime);
		if(ticketsCount < maxTicketsCount) {
			restTicketsCount = maxTicketsCount - ticketsCount;
		}

		return restTicketsCount;
	}
	
}
