package com.cinker.action;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.cinker.constant.Constant;
import com.cinker.http.CinkerHttpService;
import com.cinker.model.ActivityFilm;
import com.cinker.model.BalanceList;
import com.cinker.model.Cinema;
import com.cinker.model.Film;
import com.cinker.model.FilmContentImage;
import com.cinker.model.FilmInfo;
import com.cinker.model.FilmOrder;
import com.cinker.model.FilmsSession;
import com.cinker.model.GetTicketsInfo;
import com.cinker.model.MemberItems;
import com.cinker.model.MemberTransactions;
import com.cinker.model.Screen;
import com.cinker.model.SelectedSeat;
import com.cinker.model.TicketType;
import com.cinker.service.ActivityService;
import com.cinker.service.CinemaService;
import com.cinker.service.UserMemberService;
import com.cinker.model.UserMember;
import com.cinker.service.FilmService;
import com.cinker.util.CommonsUtil;
import com.cinker.util.GetTimeUtil;
import com.cinker.util.OrderNumUtil;
import com.cinker.util.SortClass;
import com.cinker.util.SortClasses;
@Controller
@RequestMapping("/film")
public class FilmAction {
	
	protected Logger logger = Logger.getLogger(FilmAction.class);
	
	@Autowired
	FilmService filmService;
	
	@Autowired
	ActivityService activityService;
	
	@Autowired
	CinemaService cinemaService;
	@Autowired
	UserMemberService userMemberService;
	@Autowired
	protected HttpServletRequest request;
	@Autowired
	protected HttpServletResponse response;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getScheduledFilm", method = { GET, POST })
	public String getScheduledFilm(Model model,String cinemaId,ModelMap modelMap){
		try{
			if(cinemaId == null){
				cinemaId = "1002";
			}
			Cinema cinema = filmService.getCinema(cinemaId);
			String cinemaCity = cinema.getCity();
			String cinemaType = cinema.getType();

			List<Cinema> cinemas = filmService.getCinemas(cinemaCity,cinemaType);
			model.addAttribute("cinemas", cinemas);
			logger.info("getFilmList start...");
			
			List<Film> films = filmService.getFilms(cinemaId);
			
			List<ActivityFilm> activityFilm = activityService.getActivityFilmByCinemaId(cinemaId);
			List<ActivityFilm> activityFilmList = new ArrayList<ActivityFilm>();
			List<FilmInfo> filminfoList = cinemaService.getFilmInfo();
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			for(ActivityFilm acFilm:activityFilm){
				for(FilmInfo filminfos:filminfoList){
						if(acFilm.getFilmId().equals(filminfos.getFilmId())){
							Date startSessionTime = simple.parse(acFilm.getStartSessionTime());
							Date endSessionTime = simple.parse(acFilm.getSessionTime());
							Date nowdate = new Date();
							acFilm.setSurfaceImage(filminfos.getSurfaceImage());
							acFilm.setEnglishName(filminfos.getEnglishName());
							SortClasses sorts = new SortClasses();
							Collections.sort(activityFilm,sorts);
							if(!nowdate.before(startSessionTime) && !nowdate.after(endSessionTime)){
								acFilm.setSessionTimes(endSessionTime);
								acFilm.setStartSessionTimes(startSessionTime);
								activityFilmList.add(acFilm);
							}
						
					}
				}
			}
			//把同一天放映的影片放在一个map集合中
//			Map<String,List<Film>> map = new TreeMap<String, List<Film>>();
//			List<Film> fis = null;
//			String filmDateTime = "";
//			for(Film film:films){
//				List<FilmsSession> sessions = film.getSessions();
//				for(FilmsSession s:sessions){
//					SimpleDateFormat df = new SimpleDateFormat("MM/dd");
//					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//					filmDateTime = df.format(s.getShowtime());
//					GetTicketsInfo getticket = new GetTicketsInfo();
//					if(!map.containsKey(filmDateTime)){
//						fis = new ArrayList<Film>();
//						Film filmm = new Film();
//						filmm.setChineseName(film.getChineseName());
//						filmm.setEnglishName(film.getEnglishName());
//						filmm.setSurfaceImageUrl(film.getSurfaceImageUrl());
//						filmm.setPriceInCents(getticket.getPriceInCents());
//						filmm.setCinemaId(film.getCinemaId());
//						filmm.setScreenName(s.getScreenName());
//						filmm.setStartTime(sdf.format(s.getShowtime()));
//						filmm.setDateTime(df.format(s.getShowtime()));
//						filmm.setScheduledFilmId(film.getScheduledFilmId());
//						filmm.setTitle(film.getTitle());
//						filmm.setSessionId(s.getSessionId());
//						filmm.setSessionShowTime(s.getShowtime());
//						fis.add(filmm);
//						map.put(filmDateTime, fis);
//					}else{
//						fis = map.get(filmDateTime);
//						Film filmm = new Film();
//						filmm.setChineseName(film.getChineseName());
//						filmm.setEnglishName(film.getEnglishName());
//						filmm.setSurfaceImageUrl(film.getSurfaceImageUrl());
//						filmm.setPriceInCents(getticket.getPriceInCents());
//						filmm.setCinemaId(film.getCinemaId());
//						filmm.setScreenName(s.getScreenName());
//						filmm.setStartTime(sdf.format(s.getShowtime()));
//						filmm.setDateTime(df.format(s.getShowtime()));
//						filmm.setScheduledFilmId(film.getScheduledFilmId());
//						filmm.setTitle(film.getTitle());
//						filmm.setSessionId(s.getSessionId());
//						filmm.setSessionShowTime(s.getShowtime());
//						fis.add(filmm);
//						Collections.sort(fis, new Comparator<Film>(){
//							public int compare(Film o1, Film o2) {
//								int flag = o1.getSessionShowTime().compareTo(o2.getSessionShowTime());
//								return flag;
//							}
//						});
//					}
//				}
//			}
			
			
//			Map<String,List<Film>> mapmap = new TreeMap<String, List<Film>>();
//			List<Film> listfis = null;
//			String title = "";
//			for(Film film:films){
//				List<FilmsSession> sessions = film.getSessions();
//				for(FilmsSession s:sessions){
//					SimpleDateFormat df = new SimpleDateFormat("MM/dd");
//					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//					title = film.getTitle();
//					GetTicketsInfo getticket = new GetTicketsInfo();
//				//	filmService.getFilmTicket(cinemaId,s.getSessionId(),getticket);
//					if(!mapmap.containsKey(title)){
//						listfis = new ArrayList<Film>();
//						Film filmmf = new Film();
//						filmmf.setSurfaceImageUrl(film.getSurfaceImageUrl());
//						filmmf.setPriceInCents(getticket.getPriceInCents());
//						filmmf.setCinemaId(film.getCinemaId());
//						filmmf.setScreenName(s.getScreenName());
//						filmmf.setStartTime(sdf.format(s.getShowtime()));
//						filmmf.setChineseName(film.getChineseName());
//						filmmf.setDateTime(df.format(s.getShowtime()));
//						filmmf.setEnglishName(film.getEnglishName());
//						filmmf.setScheduledFilmId(film.getScheduledFilmId());
//						filmmf.setTitle(film.getTitle());
//						filmmf.setSessionId(s.getSessionId());
//						filmmf.setSessionShowTime(s.getShowtime());
//						listfis.add(filmmf);
//						mapmap.put(title, listfis);
//					}else{
//						listfis = mapmap.get(title);
//						Film filmmf = new Film();
//						filmmf.setPriceInCents(getticket.getPriceInCents());
//						filmmf.setCinemaId(film.getCinemaId());
//						filmmf.setScreenName(s.getScreenName());
//						filmmf.setStartTime(sdf.format(s.getShowtime()));
//						filmmf.setChineseName(film.getChineseName());
//						filmmf.setDateTime(df.format(s.getShowtime()));
//						filmmf.setEnglishName(film.getEnglishName());
//						filmmf.setScheduledFilmId(film.getScheduledFilmId());
//						filmmf.setTitle(film.getTitle());
//						filmmf.setSessionId(s.getSessionId());
//						filmmf.setSessionShowTime(s.getShowtime());
//						listfis.add(filmmf);
//						Collections.sort(fis, new Comparator<Film>(){
//							public int compare(Film o1, Film o2) {
//								int flag = o1.getSessionShowTime().compareTo(o2.getSessionShowTime());
//								return flag;
//							}
//						});
//					}
//				}
//			}
//			modelMap.addAttribute("mapmap", mapmap);
//			modelMap.addAttribute("map", map);
//			for(String key:map.keySet()){
//				System.out.println("key,"+key);
//				for(Film filmli:map.get(key)){
//					System.out.println("value,"+filmli.getTitle()+","+filmli.getSessionId()+","+filmli.getDateTime()+","+filmli.getScheduledFilmId()+","+filmli.getCinemaId()+","+filmli.getScreenName());				
//				}
//			}
			
			
			films = filmService.getFilms(cinemaId);
			for(Film film:films){
				List<FilmsSession> sessions = film.getSessions();
				SortClass sort = new SortClass();
				Collections.sort(sessions,sort);
				SimpleDateFormat sortDate = new SimpleDateFormat("YYYY/MM/dd hh:mm:ss");
				String filmSortDate = sortDate.format(sessions.get(0).getShowtime());
				film.setFilmSortDate(filmSortDate);
			}
			logger.info("getFilmList end...");
			model.addAttribute("films", films);
			model.addAttribute("activityFilmList", activityFilmList);
			model.addAttribute("inCinemaId", cinemaId);
		}catch (Exception e) {
			logger.error("getFilmList error!");
			e.printStackTrace();
			return "error";
		}		
		return "scheduled_films_list";
		
		
	}

/* 根据影厅显示排片Pictures影院
 * 2017/7/26
 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getScheduledFilmByScreen", method = { GET, POST })
	public String getScheduledFilmByScreen(Model model,String cinemaId,String screenNumber,ModelMap modelMap){
		try{
			if(cinemaId == null){
				cinemaId = "1001";
			}

			if(screenNumber == null){
				screenNumber = "0";
			}
			List<Screen> screens = filmService.getScreens(cinemaId);
			model.addAttribute("screens", screens);
			
			List<Cinema> cinemas = filmService.getCinemasByType("Pictures");
			model.addAttribute("cinemas", cinemas);
			
			logger.info("getFilmListByScreen start...");
			List<Film> films = filmService.getFilms(cinemaId);
			List<ActivityFilm> activityFilmList = new ArrayList<ActivityFilm>();
			Map<String,List<ActivityFilm>> activityFilmMap = new TreeMap<String, List<ActivityFilm>>();
			Map<String,List<ActivityFilm>> memberFilmMap = new TreeMap<String, List<ActivityFilm>>();
			
			if ("0".equals(screenNumber)) {
				List<ActivityFilm> activityFilm = activityService.getActivityFilmByCinemaId(cinemaId);
				List<FilmInfo> filminfoList = cinemaService.getFilmInfo();
				SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				for(ActivityFilm acFilm:activityFilm){
					logger.info("activity film id is "+acFilm.getFilmId()+"; sessionID is "+acFilm.getSessionId());
					for(FilmInfo filminfos:filminfoList){
						if(acFilm.getFilmId().equals(filminfos.getFilmId())){
							Date startSessionTime = simple.parse(acFilm.getStartSessionTime());
							Date endSessionTime = simple.parse(acFilm.getSessionTime());
							Date nowdate = new Date();
							acFilm.setSurfaceImage(filminfos.getSurfaceImage());
							acFilm.setEnglishName(filminfos.getEnglishName());
							if(!nowdate.before(startSessionTime) && !nowdate.after(endSessionTime)){
								acFilm.setSessionTimes(endSessionTime);
								acFilm.setStartSessionTimes(startSessionTime);
								activityFilmList.add(acFilm);
								
								
							}
						}
					}
				}

				// 把filmId和sessionName相同的放在一个map集合中 2017-08-09
				List<ActivityFilm> fisList = null;
				String filmIdSessionName = "";
				for(ActivityFilm aFilm:activityFilmList){
					//KEY的分割符暂定为 "."
					Map<String,List<ActivityFilm>> filmMap = null;
					if(aFilm.getIsForUpperMember()>0){
						filmMap = memberFilmMap;
					}else{
						filmMap = activityFilmMap;
					}
					filmIdSessionName = aFilm.getFilmId() + "." + aFilm.getSessionName();
					logger.info("get active by "+filmIdSessionName);
					if(!filmMap.containsKey(filmIdSessionName)){
						fisList = new ArrayList<ActivityFilm>();
						ActivityFilm afilmm = new ActivityFilm();

						afilmm.setId(aFilm.getId());
						afilmm.setCinemaId(aFilm.getCinemaId());
						afilmm.setSessionId(aFilm.getSessionId());
						afilmm.setFilmId(aFilm.getFilmId());
						afilmm.setSessionName(aFilm.getSessionName());
						afilmm.setActivityFilmType(aFilm.getActivityFilmType());
						afilmm.setFilmTitle(aFilm.getFilmTitle());
						afilmm.setChineseName(aFilm.getChineseName());
						afilmm.setEnglishName(aFilm.getEnglishName());
						afilmm.setTotalValueCents(aFilm.getTotalValueCents());
						afilmm.setSessionTime(aFilm.getSessionTime());
						afilmm.setStartSessionTime(aFilm.getStartSessionTime());
						afilmm.setTotal(aFilm.getTotal());
						afilmm.setQuaty(aFilm.getQuaty());
						afilmm.setSessionTimes(aFilm.getSessionTimes());
						afilmm.setStartSessionTimes(aFilm.getStartSessionTimes());
						afilmm.setLanguage(aFilm.getLanguage());
						afilmm.setSurfaceImage(aFilm.getSurfaceImage());
						afilmm.setActivityTickets(aFilm.getActivityTickets());
						afilmm.setUrl(aFilm.getUrl());

						fisList.add(afilmm);
						filmMap.put(filmIdSessionName, fisList);
					}else{
						fisList = filmMap.get(filmIdSessionName);
						ActivityFilm afilmm = new ActivityFilm();

						afilmm.setId(aFilm.getId());
						afilmm.setCinemaId(aFilm.getCinemaId());
						afilmm.setSessionId(aFilm.getSessionId());
						afilmm.setFilmId(aFilm.getFilmId());
						afilmm.setSessionName(aFilm.getSessionName());
						afilmm.setActivityFilmType(aFilm.getActivityFilmType());
						afilmm.setFilmTitle(aFilm.getFilmTitle());
						afilmm.setChineseName(aFilm.getChineseName());
						afilmm.setEnglishName(aFilm.getEnglishName());
						afilmm.setTotalValueCents(aFilm.getTotalValueCents());
						afilmm.setSessionTime(aFilm.getSessionTime());
						afilmm.setStartSessionTime(aFilm.getStartSessionTime());
						afilmm.setTotal(aFilm.getTotal());
						afilmm.setQuaty(aFilm.getQuaty());
						afilmm.setSessionTimes(aFilm.getSessionTimes());
						afilmm.setStartSessionTimes(aFilm.getStartSessionTimes());
						afilmm.setLanguage(aFilm.getLanguage());
						afilmm.setSurfaceImage(aFilm.getSurfaceImage());
						afilmm.setActivityTickets(aFilm.getActivityTickets());
						afilmm.setUrl(aFilm.getUrl());
						
						fisList.add(afilmm);
						Collections.sort(fisList, new Comparator<ActivityFilm>(){
							public int compare(ActivityFilm o1, ActivityFilm o2) {
								int flag = o1.getSessionTime().compareTo(o2.getSessionTime());
								return flag;
							}
						});
					}
				}
			}

			//把同一天放映的影片放在一个map集合中
			Map<String,List<Film>> map = new TreeMap<String, List<Film>>();
			List<Film> fis = null;
			String filmDateTime = "";
			for(Film film:films){
				List<FilmsSession> sessions = film.getSessions();
				for(FilmsSession s:sessions){
					SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
					filmDateTime = df.format(s.getShowtime());
					GetTicketsInfo getticket = new GetTicketsInfo();
					if(!map.containsKey(filmDateTime)){
						fis = new ArrayList<Film>();
						Film filmm = new Film();
						filmm.setChineseName(film.getChineseName());
						filmm.setEnglishName(film.getEnglishName());
						filmm.setSurfaceImageUrl(film.getSurfaceImageUrl());
						filmm.setPriceInCents(getticket.getPriceInCents());
						filmm.setCinemaId(film.getCinemaId());
						filmm.setScreenName(s.getScreenName());
						filmm.setStartTime(sdf.format(s.getShowtime()));
						filmm.setDateTime(df.format(s.getShowtime()));
						filmm.setScheduledFilmId(film.getScheduledFilmId());
						filmm.setTitle(film.getTitle());
						filmm.setSessionId(s.getSessionId());
						filmm.setSessionShowTime(s.getShowtime());
						//没有选择影厅(screenNumber =0)时,将所有电影添加到list,否则将对应影厅的电影添加到list
						if ("0".equals(screenNumber) || screenNumber.equals(s.getScreenNumber())){
							fis.add(filmm);
							map.put(filmDateTime, fis);
						}
					}else{
						fis = map.get(filmDateTime);
						Film filmm = new Film();
						filmm.setChineseName(film.getChineseName());
						filmm.setEnglishName(film.getEnglishName());
						filmm.setSurfaceImageUrl(film.getSurfaceImageUrl());
						filmm.setPriceInCents(getticket.getPriceInCents());
						filmm.setCinemaId(film.getCinemaId());
						filmm.setScreenName(s.getScreenName());
						filmm.setStartTime(sdf.format(s.getShowtime()));
						filmm.setDateTime(df.format(s.getShowtime()));
						filmm.setScheduledFilmId(film.getScheduledFilmId());
						filmm.setTitle(film.getTitle());
						filmm.setSessionId(s.getSessionId());
						filmm.setSessionShowTime(s.getShowtime());
						//没有选择影厅(screenNumber =0)时,将所有电影添加到list,否则将对应影厅的电影添加到list
						if ("0".equals(screenNumber) || screenNumber.equals(s.getScreenNumber())){
							fis.add(filmm);
							Collections.sort(fis, new Comparator<Film>(){
								public int compare(Film o1, Film o2) {
									int flag = o1.getSessionShowTime().compareTo(o2.getSessionShowTime());
									return flag;
								}
							});
						}
					}
				}
			}

			modelMap.addAttribute("map", map);
			modelMap.addAttribute("activityFilmMap", activityFilmMap);
			modelMap.addAttribute("memberFilmMap", memberFilmMap);
			model.addAttribute("inScreenNumber", screenNumber);
			model.addAttribute("cinemaId", cinemaId);
			logger.info("getFilmListByScreen end...");
		}catch (Exception e) {
			logger.error("getFilmListByScreen error!");
			e.printStackTrace();
			return "error";
		}		
		return "scheduled_screen_films_list";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getFilmDetail", method = { GET, POST })
	public String getFilmTicket(Model model,String scheduledFilmId,String cinemaId,String dateTime) throws ParseException{
		try{
			String userSessionId = OrderNumUtil.getOrderId();
			logger.info("getFilmTicket start...");
			List<Film> films = filmService.getFilms(cinemaId);
			String filmTitle = "";
			List<GetTicketsInfo> ticketsInfoList = new ArrayList<GetTicketsInfo>();			
			for(Film film : films){
				if(scheduledFilmId.equals(film.getScheduledFilmId())){					
					List<FilmsSession> sessions = film.getSessions();
					SortClass sort = new SortClass();
					Collections.sort(sessions,sort);
					filmTitle = film.getTitle();
					if(dateTime.isEmpty()||dateTime == null){
						if(sessions.size() > 0){
							SimpleDateFormat df = new SimpleDateFormat("MM/dd");
							String now = df.format(sessions.get(0).getShowtime());	
							dateTime = now;
						}else{
							SimpleDateFormat df = new SimpleDateFormat("MM/dd");
							String now = df.format(new Date());	
							dateTime = now;
						}						
												
					}
					model.addAttribute("filmDateTime",film.getDateTime());
					for(FilmsSession filmsSession : sessions){
						SimpleDateFormat df = new SimpleDateFormat("MM/dd");					
						String nowTime = df.format(filmsSession.getShowtime());														
						Date showdate = filmsSession.getShowtime();  				          
						SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
						String showtime = sdf.format(showdate);
						int showTime = GetTimeUtil.getEndTime(showtime);
						String runtime = (String)film.getRunTime();
						int runTime = Integer.parseInt(runtime);
						String endTime = GetTimeUtil.getRunTime(runTime+showTime);
						String startTime = sdf.format(filmsSession.getShowtime());	
						if(dateTime.equals(nowTime)){
							GetTicketsInfo getTicketsInfo = new GetTicketsInfo();
							filmService.getFilmTicket(cinemaId,filmsSession.getSessionId(),getTicketsInfo,userSessionId,scheduledFilmId);
							getTicketsInfo.setScheduledFilmId(scheduledFilmId);
							getTicketsInfo.setSessionId(filmsSession.getSessionId());
							getTicketsInfo.setCinemaId(cinemaId);
							getTicketsInfo.setStartTime(startTime);
							getTicketsInfo.setEndTime(endTime);	
							getTicketsInfo.setNowTime(nowTime);
							getTicketsInfo.setTitle(film.getTitle());
							List<FilmInfo> filmInfo = cinemaService.getFilmInfo();
							for(FilmInfo info:filmInfo){
								if(info.getFilmId().equals(film.getScheduledFilmId()))	{
									String type = info.getFilmType();
									getTicketsInfo.setDescription(type);
									break;
								}
							}
							getTicketsInfo.setScreenName(filmsSession.getScreenName());
							getTicketsInfo.setSessionShowTime(filmsSession.getShowtime());
							ticketsInfoList.add(getTicketsInfo);	
						}
					}
					model.addAttribute("film",film);
				}
				List<FilmContentImage> filmContentImage=filmService.getFilmContentImage(scheduledFilmId);
				model.addAttribute("filmContentImage",filmContentImage);
					
				}
			logger.info("getFilmTicket end...");	
			model.addAttribute("ticketsInfoList", ticketsInfoList);
			model.addAttribute("filmTitle", filmTitle);
			model.addAttribute("orderId", userSessionId);
		}catch (Exception e) {
			logger.error("getFilmTicket error!");
			e.printStackTrace();
			return "error";
		}
		
		return "film_detail";
		
	}
	
	@RequestMapping(value = "/getCinemaFilmDetail", method = { GET, POST })
	public String getCinemaFilmDetail(Model model,String scheduledFilmId,String cinemaId) throws ParseException{
		try{
			
			logger.info("getFilmTicket start...");
			List<Film> films = filmService.getFilms(cinemaId);
			
			for(Film film : films){
				if(scheduledFilmId.equals(film.getScheduledFilmId())){					
					model.addAttribute("film",film);
				}
				List<FilmContentImage> filmContentImage=filmService.getFilmContentImage(scheduledFilmId);
				model.addAttribute("filmContentImage",filmContentImage);		
			}
			model.addAttribute("cinemaId",cinemaId);		
			logger.info("getFilmTicket end...");	
		}catch (Exception e) {
			logger.error("getFilmTicket error!");
			e.printStackTrace();
			return "error";
		}	
		return "cinemas/film_details";
	}
	
	//Cinema影院，显示单个电影所有场次
	
	@RequestMapping(value = "/getCinemaFilmSessions", method = { GET, POST })
	public String getCinemaFilmSessions(Model model,String scheduledFilmId,String cinemaId,String dateTime) throws ParseException{
		try{
			String userSessionId = OrderNumUtil.getOrderId();
			logger.info("getFilmTicket start...");
			List<Film> films = filmService.getFilms(cinemaId);
			String filmTitle = "";
			List<GetTicketsInfo> ticketsInfoList = new ArrayList<GetTicketsInfo>();			
			for(Film film : films){
				if(scheduledFilmId.equals(film.getScheduledFilmId())){					
					List<FilmsSession> sessions = film.getSessions();
					SortClass sort = new SortClass();
					Collections.sort(sessions,sort);
					filmTitle = film.getTitle();
					if(dateTime.isEmpty()||dateTime == null){
						if(sessions.size() > 0){
							SimpleDateFormat df = new SimpleDateFormat("MM/dd");
							String now = df.format(sessions.get(0).getShowtime());	
							dateTime = now;
						}else{
							SimpleDateFormat df = new SimpleDateFormat("MM/dd");
							String now = df.format(new Date());	
							dateTime = now;
						}						
												
					}
					model.addAttribute("filmDateTime",film.getDateTime());
					for(FilmsSession filmsSession : sessions){
						SimpleDateFormat df = new SimpleDateFormat("MM/dd");					
						String nowTime = df.format(filmsSession.getShowtime());														
						Date showdate = filmsSession.getShowtime();  				          
						SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
						String showtime = sdf.format(showdate);
						int showTime = GetTimeUtil.getEndTime(showtime);
						String runtime = (String)film.getRunTime();
						int runTime = Integer.parseInt(runtime);
						String endTime = GetTimeUtil.getRunTime(runTime+showTime);
						String startTime = sdf.format(filmsSession.getShowtime());	
						if(dateTime.equals(nowTime)){
							GetTicketsInfo getTicketsInfo = new GetTicketsInfo();
							filmService.getFilmTicket(cinemaId,filmsSession.getSessionId(),getTicketsInfo,userSessionId,scheduledFilmId);
							getTicketsInfo.setScheduledFilmId(scheduledFilmId);
							getTicketsInfo.setSessionId(filmsSession.getSessionId());
							getTicketsInfo.setCinemaId(cinemaId);
							getTicketsInfo.setStartTime(startTime);
							getTicketsInfo.setEndTime(endTime);	
							getTicketsInfo.setNowTime(nowTime);
							getTicketsInfo.setTitle(film.getTitle());
							getTicketsInfo.setFilmLanguage(filmsSession.getDescription()[0]);
							List<FilmInfo> filmInfo = cinemaService.getFilmInfo();
							for(FilmInfo info:filmInfo){
								if(info.getFilmId().equals(film.getScheduledFilmId()))	{
									String type = info.getFilmType();
									getTicketsInfo.setDescription(type);
									break;
								}
							}
							getTicketsInfo.setScreenName(filmsSession.getScreenName());
							getTicketsInfo.setSessionShowTime(filmsSession.getShowtime());
							ticketsInfoList.add(getTicketsInfo);	
						}
					}
					model.addAttribute("film",film);
				}
				List<FilmContentImage> filmContentImage=filmService.getFilmContentImage(scheduledFilmId);
				model.addAttribute("filmContentImage",filmContentImage);
					
				}
			logger.info("getFilmTicket end...");	
			model.addAttribute("ticketsInfoList", ticketsInfoList);
			model.addAttribute("filmTitle", filmTitle);
			model.addAttribute("films", films);
			model.addAttribute("orderId", userSessionId);
		}catch (Exception e) {
			logger.error("getFilmTicket error!");
			e.printStackTrace();
			return "error";
		}
		
		return "cinemas/film_sessions";
		
	}
	
	@RequestMapping(value = "/getSessionForSeat", method = { GET, POST })
	public String getSessionForSeat(Model model,String userSessionId,String scheduledFilmId,String filmTitle,
			String screenName,String cinemaId,String sessionId,String showTime,String sessionShowTime,String ticketTypeCode,String priceInCents,String memberLevelId,String cinemaType,String ticketTypeCodeNoDiscount,String priceNoDiscount) throws IOException{
//		if(!StringUtils.isEmpty(filmTitle)){
//			filmTitle = request.getParameter("filmTitle");
//			filmTitle = new String(filmTitle.getBytes("iso8859-1"),"UTF-8");
//		}
		// check the count of VIP can buy tickets 2017/9/5
		int restTicketsCount = 0;
		HttpSession session = request.getSession();
		if(session.getAttribute(Constant.SESSION_ATTRIBUTE_KEY) != null) {
			String userNumber = ((UserMember)session.getAttribute(Constant.SESSION_ATTRIBUTE_KEY)).getUserNumber();

			restTicketsCount = filmService.getRestTickets(userNumber,memberLevelId,cinemaId,cinemaType,sessionId,sessionShowTime,showTime);
		}
		
		int priceInCents1 =0;
		int priceNoDiscount1 =0;
		if (!StringUtils.isEmpty(priceInCents)){
			priceInCents1 = Integer.parseInt(priceInCents.replace(".", ""));
		}
		if (!StringUtils.isEmpty(priceNoDiscount)){
			priceNoDiscount1 = Integer.parseInt(priceNoDiscount.replace(".", ""));
		}
		
		String filmEnglishName = "";
		String cinemaName = "";
		
		List<FilmInfo> filmInfo = cinemaService.getFilmInfoEnglishName();
		for(FilmInfo info:filmInfo){
			if(info.getFilmId().equals(scheduledFilmId))	{
				filmEnglishName = info.getEnglishName();
				break;
			}
		}
		
		Cinema cinema = filmService.getCinema(cinemaId);
		if(cinema != null) {
			cinemaName = cinema.getName();
		}
		
		model.addAttribute("userSessionId", userSessionId);
		model.addAttribute("scheduledFilmId", scheduledFilmId);
		model.addAttribute("filmTitle", filmTitle);
		model.addAttribute("screenName", screenName);
		model.addAttribute("cinemaId",cinemaId );
		model.addAttribute("sessionId", sessionId);
		model.addAttribute("showTime", showTime);
		model.addAttribute("sessionShowTime", sessionShowTime);
		model.addAttribute("ticketTypeCode", ticketTypeCode);
		model.addAttribute("priceInCents", priceInCents1);
		model.addAttribute("memberLevelId", memberLevelId);
		model.addAttribute("cinemaType", cinemaType);
		model.addAttribute("filmEnglishName", filmEnglishName);
		model.addAttribute("cinemaName", cinemaName);
		model.addAttribute("ticketTypeCodeNoDiscount", ticketTypeCodeNoDiscount);
		model.addAttribute("priceNoDiscount", priceNoDiscount1);
		model.addAttribute("restTicketsCount", restTicketsCount);

		return "seat_plan_for_session";
	}
	
	@RequestMapping(value = "/addOrder", method = { GET, POST })
	public @ResponseBody FilmOrder addOrder(Model model,@RequestBody FilmOrder filmOrder) throws UnsupportedEncodingException{
		//String orderId = OrderNumUtil.getOrderId();
		String orderId = filmOrder.getOrderNumber();
		try{
			//Check the MemberLevel,only VIP can buy tickets 2017/11/10
			if ("1".compareTo(filmOrder.getMemberLevelId()) > 0 ){
				filmOrder.getResults().put("Result", 9);
				filmOrder.getResults().put("ErrorDescription", "Only VIP can buy tickets");
				logger.error("Only VIP can buy tickets");
				throw new Exception("Only VIP can buy tickets");
			}
			// check the count of VIP can buy tickets 2017/9/5
			HttpSession session = request.getSession();
			if(session.getAttribute(Constant.SESSION_ATTRIBUTE_KEY) != null) {
				String userNumber = ((UserMember)session.getAttribute(Constant.SESSION_ATTRIBUTE_KEY)).getUserNumber();

				int restTicketsCount = filmService.getRestTickets(userNumber,filmOrder.getMemberLevelId(),filmOrder.getCinemaId(),filmOrder.getCinemaType(),filmOrder.getSessionId(),filmOrder.getSessionShowTime(),filmOrder.getShowTime());
				int orderTicketsCount =  filmOrder.getSelectedSeats().size();
				
				if (orderTicketsCount > restTicketsCount) {
					filmOrder.getResults().put("Result", 9);
					filmOrder.getResults().put("ErrorDescription", "ticketsCount is more than the maximum value");
					logger.error("ticketsCount is more than the maximum value");
					throw new Exception("ticketsCount is more than the maximum value");
				}
			}

			logger.info("addOrder start...");
			String result = "";			
			result = filmService.addOrder(orderId, filmOrder);
			//filmOrder.setOrderNumber(orderId);
			if(!result.isEmpty()){
				JSONObject resultJson = JSONObject.fromObject(result);
				String resultVista = resultJson.getString("Result");
				String errorDescription = resultJson.getString("ErrorDescription");
				if(resultVista.equals("0")){
					FilmOrder saveOrder = new FilmOrder();
					saveOrder.setOrderNumber(orderId);
					saveOrder.setOrderType(FilmOrder.ORDER_TYPE_NORMAL);
					saveOrder.setCinemaId(filmOrder.getCinemaId());
					if(!StringUtils.isEmpty(filmOrder.getCinemaId())){
						Cinema cinema = filmService.getCinema(filmOrder.getCinemaId());
						if(cinema != null)saveOrder.setCinemaName(cinema.getName());
					}
					saveOrder.setSessionId(filmOrder.getSessionId());
					saveOrder.setSessionName(filmOrder.getSessionName());
					saveOrder.setFilmTitle(filmOrder.getFilmTitle());
					saveOrder.setScheduledFilmId(filmOrder.getScheduledFilmId());
					saveOrder.setAreaCategoryCode(filmOrder.getAreaCategoryCode());
					saveOrder.setShowTime(filmOrder.getSessionShowTime()+" "+filmOrder.getShowTime());
					//----------------------------------------------------------------------
					List<TicketType> ticketTypes = filmOrder.getTicketTypes();
					if(ticketTypes.size() > 0){
						for(TicketType TicketType : ticketTypes){
							TicketType.setOrderId(orderId);
						}
					}
					saveOrder.setStatus(0);
					int totalOrderCount = resultJson.getJSONObject("Order").getInt("TotalOrderCount");
					saveOrder.setTotalOrderCount(totalOrderCount);
					filmOrder.setTotalOrderCount(totalOrderCount);
					int totalValueCents = resultJson.getJSONObject("Order").getInt("TotalValueCents");
					saveOrder.setTotalValueCents(CommonsUtil.roundByScale(totalValueCents/100, 2));
					filmOrder.setTotalValueCents(CommonsUtil.roundByScale(totalValueCents/100, 2));
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					saveOrder.setStartTime(df.format(new Date()));
					saveOrder.setEndTime(null);
//					HttpSession session = request.getSession();
					if(session.getAttribute(Constant.SESSION_ATTRIBUTE_KEY) != null)
					saveOrder.setUserNumber(((UserMember)session.getAttribute(Constant.SESSION_ATTRIBUTE_KEY)).getUserNumber());
					StringBuffer seat = new StringBuffer();
					List<SelectedSeat> selectedSeats = filmOrder.getSelectedSeats();
					if(selectedSeats.size() > 0){
						for(SelectedSeat selectedSeat : selectedSeats){
							String columnIndex = selectedSeat.getPhysicalName();
							String rowIndex = selectedSeat.getId();
							seat.append(columnIndex);
							seat.append("排");
							seat.append(rowIndex);
							seat.append("座;");
						}
					}
					saveOrder.setSeat(seat.toString());
					saveOrder.setMemberLevelId(filmOrder.getMemberLevelId());
					
					filmService.saveOrder(saveOrder);
					filmService.saveTicketType(ticketTypes);
					
				}
				filmOrder.getResults().put("Result", resultVista);
				filmOrder.getResults().put("ErrorDescription", errorDescription);
				logger.info("addOrder end...");
			}
		}catch (Exception e) {
			logger.error("addOrder error!");
			e.printStackTrace();
			return filmOrder;
		}
		
		return filmOrder;
		
	}
	
/*	@RequestMapping(value = "/getFilmPyment", method = { GET, POST })
	public String getFilmPyment(Model model,String orderNumber,String userSessionId){
		try{
			logger.info("FilmPyment start...");
			FilmOrder filmOrder = filmService.getFilmOrder(orderNumber);
			String[] str = filmOrder.getShowTime().split(" ");
			filmOrder.setSessionShowTime(str[0]);
			filmOrder.setShowTime(str[1]);
			TicketType ticketType = filmService.getTicketType(orderNumber);
			List<FilmInfo> filmInfo = cinemaService.getFilmInfoEnglishName();
			for(FilmInfo info:filmInfo){
				if(info.getFilmId().equals(filmOrder.getScheduledFilmId()))	{
					String englishName = info.getEnglishName();
					filmOrder.setEnglishName(englishName);
					break;
				}
			}
			model.addAttribute("filmOrder", filmOrder);
			model.addAttribute("ticketType", ticketType);
		}catch (Exception e) {
			logger.error("FilmPyment error!");
			e.printStackTrace();
			return "error";
		}
		
		return "film_cashier";
		
	}*/

	@RequestMapping(value = "/getMemberItems", method = { GET, POST })
	public @ResponseBody MemberItems getMemberItems(@RequestBody MemberItems memberItems){

		memberItems.setQtyAvailable(0);
		memberItems.setRecognitionID("");
/*		String memberLevelId = memberItems.getMemberLevelId();
		String userSessionId = memberItems.getUserSessionId();

		String connectapiurl;
		String connectapitoken;
		if ("Pictures".equals(memberItems.getCinemaType())){
			connectapiurl = Constant.CONNECT_API_URL;
			connectapitoken = Constant.CONNECT_API_TOKEN;
		} else {
			connectapiurl = Constant.CONNECT_API_URL_C;
			connectapitoken = Constant.CONNECT_API_TOKEN_C;
		}

		if ("1".compareTo(memberLevelId) < 0) {
			String requestUrl = connectapiurl+"WSVistaWebClient/RESTLoyalty.svc/member/items";
			JSONObject jsonObject = new JSONObject();

			jsonObject.put("GetTicketTypes", "true");
			jsonObject.put("SupressSelectedSessionDateTimeFilter", "true");
			jsonObject.put("UserSessionId", userSessionId);
			jsonObject.put("OptionalClientClass",Constant.OPTIONAL_CLIENT_CLASS);
			jsonObject.put("OptionalClientId",Constant.OPTIONAL_CLIENT_ID);
			jsonObject.put("OptionalClientName",Constant.OPTIONAL_CLIENT_NAME);
		
			String result = CinkerHttpService.httpPostRequest(requestUrl, jsonObject.toString(),connectapitoken);
		
			if(result != null){
				JSONObject resultJson = JSONObject.fromObject(result);
			
				Object ticketTypeObject = resultJson.getString("TicketTypeList");
				if(ticketTypeObject != "null"){
					JSONArray ticketTypeArray = resultJson.getJSONArray("TicketTypeList");
					String recognitionID;

					if ("2".equals(memberLevelId)) {
						//经典会员
						recognitionID = "19";
					} else if ("4".equals(memberLevelId)) {
						//超级经典会员
						recognitionID = "12";
					} else if ("3".equals(memberLevelId)) {
						//摩登会员
						recognitionID = "20";
					} else if ("6".equals(memberLevelId)) {
						//超级摩登会员
						recognitionID = "21";
					} else {
						recognitionID = "";
					}
					
					for(int i=0;i<ticketTypeArray.size();i++){
						try{
							JSONObject ticketType = ticketTypeArray.getJSONObject(i);
							//有免费权益
							if (ticketType.getString("RecognitionID").equals(recognitionID) && ticketType.getInt("QtyAvailable") > 0){
								memberItems.setQtyAvailable(ticketType.getInt("QtyAvailable"));
								memberItems.setRecognitionID(ticketType.getString("RecognitionID"));
							}
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				}
			}
		}
		//免费功能暂时取消
		memberItems.setQtyAvailable(0);*/
		return memberItems;
	}
	
	@RequestMapping(value = "/getSetPlan/{cinemaId}/{sessionId}", method = {GET})
	public @ResponseBody String getSetPlan(@PathVariable("cinemaId")String cinemaId,@PathVariable("sessionId")String sessionId,Model model){
		 CinkerHttpService cinkerHttpService=CinkerHttpService.getInstance();

		 String cinemaType = filmService.getCinema(cinemaId).getType();
			String connectapiurl;
			String connectapitoken;
			if ("Pictures".equals(cinemaType)){
				connectapiurl = Constant.CONNECT_API_URL;
				connectapitoken = Constant.CONNECT_API_TOKEN;
			} else {
				connectapiurl = Constant.CONNECT_API_URL_C;
				connectapitoken = Constant.CONNECT_API_TOKEN_C;
			}

//		 System.out.println(connectapiurl+"WSVistaWebClient/RESTData.svc/cinemas/"+cinemaId+"/sessions/"+sessionId+"/seat-plan");
		 String result=cinkerHttpService.httpGetRequest(connectapiurl+"WSVistaWebClient/RESTData.svc/cinemas/"+cinemaId+"/sessions/"+sessionId+"/seat-plan",connectapitoken);
		 //System.out.println();
		 //String result=cinkerHttpService.httpGetRequest("http://180.167.19.17/WSVistaWebClient/RESTData.svc/cinemas/0200/sessions/38898/seat-plan");
         logger.info("Vista getSetPlan  response:"+result);
		 return result;
	}
	
	@RequestMapping(value = "/getTicket/{cinemaId}/{sessionId}", method = {GET})
	public @ResponseBody String getTicket(@PathVariable("cinemaId")String cinemaId,@PathVariable("sessionId")String sessionId,Model model){
		 CinkerHttpService cinkerHttpService = CinkerHttpService.getInstance(); 

		 String cinemaType = filmService.getCinema(cinemaId).getType();
			String connectapiurl;
			String connectapitoken;
			if ("Pictures".equals(cinemaType)){
				connectapiurl = Constant.CONNECT_API_URL;
				connectapitoken = Constant.CONNECT_API_TOKEN;
			} else {
				connectapiurl = Constant.CONNECT_API_URL_C;
				connectapitoken = Constant.CONNECT_API_TOKEN_C;
			}

		 String result=cinkerHttpService.httpGetRequest(connectapiurl+"WSVistaWebClient/RESTData.svc/cinemas/"+cinemaId+"/sessions/"+sessionId+"/tickets",connectapitoken);
         logger.info("Vista getTicket  response:"+result);
		 return result;
	}
	
	@RequestMapping(value = "/getCinemaByCity", method = { GET, POST })
	public Map<String, Object> getCinemaByCity(String city,String type) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Cinema> cinemas = filmService.getCinemas(city,type);
		result.put("data", cinemas);
		result.put("success", true);
		return result;
		
	}

}
