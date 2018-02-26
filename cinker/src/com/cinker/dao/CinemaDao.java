package com.cinker.dao;


import java.util.List;

import com.cinker.model.ActivityFilm;
import com.cinker.model.ActivityPersonal;
import com.cinker.model.Cinema;
import com.cinker.model.FilmContentImage;
import com.cinker.model.FilmInfo;
import com.cinker.model.FilmOrder;
import com.cinker.model.FilmOrders;
import com.cinker.model.OrderTicket;
import com.cinker.model.Payment;
import com.cinker.model.Screen;
import com.cinker.model.UserMember;
import com.cinker.model.UserVipMember;



public interface CinemaDao {

	public List<Cinema> getCinema();
	
	public List<Screen> getScreen();
	
	public List<FilmOrders> getFilmOrders(Integer page); 
	
	public int getFilmOrdersTotal();
	
	public List<FilmInfo> getFilmInfo();
	
	public List<FilmInfo> getFilmInfoEnglishName();
	
	public List<Payment> getPaymentInfo();
	
	public List<UserMember> getPaymentInformations(String userNumber);
	
	public Cinema editCinema(String id);
	
	public void saveCinema(Cinema cinema);
	
	public void deleteCinema(int id);
	
	public Screen editScreen(String id);
	
	public void saveScreen(Screen screen);
	
	public void deleteScreen(int id);
	
	public FilmInfo editFilmInfo(String id);
	
	public void saveFilmInfo(FilmInfo filmInfo);
	
	public void deleteFilmInfo(String id);
	
	public FilmOrders editFilmOrders(String id);
	
	public void saveFilmOrders(FilmOrders filmOrders);
	
	public void deleteFilmOrders(String id);
		
	public void insertCinema(Cinema cinema);
		
	public void insertScreen(Screen screen);
	
	public void insertFilmInfo(FilmInfo filmInfo);
	
	public void insertFilmOrders(FilmOrders filmOrders);

	public FilmInfo findFilmInfo(String id);
	
	public FilmOrders findFilmOrdersInfo(String id);
	
	public Payment findPayment(String paymentID);
	
	public Payment findPaymentInfo(String orderNumber);
	
	public OrderTicket findOrderTicket(String orderNumber);
	
	public UserMember findUserMember(String userNumber);
	
	public List<FilmContentImage> getFilmContentImage(String filmId);
	
	public void saveImage(String filmId, String newFileName);

	public List<FilmInfo> getSearch(String id, String chineseName, String englishName,String beginTime,String endTime,Integer page);
	
	public List<Payment> getSearchPayment(String userNickName,String orderNumber,String paymentID,String beginTime,String endTime);	
	
	public List<Payment> getSearchPayment(String userNickName,String orderNumber,String paymentID,String beginTime,String endTime,Integer page);
	
	public FilmContentImage findFilmContentImage(String id);
	
	public void deleteFilmContentImage(String filmId);
	
	public void deleteFilmInfoByFIlmId(String filmId);
	
	public int findFilmInfoByFilmId(String filmId);
	
	public FilmInfo findFilmInfoFilmId(String filmId);

	public Payment findPaymentInformation(String paymentID);

	public List<FilmInfo> getFilmInfoType(String filmId);
	
	public List<ActivityFilm> getActivityFilmInfo();
	
	public void insertActivityFilm(ActivityFilm activityFilm);
	
	public void updateActivityFilm(ActivityFilm activityFilm);
	
	public void deleteActivityFilm(int id);
	
	public ActivityFilm editActivityFilm(String id);
	
	public List<ActivityPersonal> getActivityPersonal(Integer page);
	
	public int getActivityPersonalCount();

	public List<FilmOrders> getSearch(String orderNumber, String filmTitle,
			String beginTime, String endTime, String scheduledFilmId,
			String beginShowTime, String endShowTime,Integer page,String submit1,String submit2,String cinemaId);
	
	public void updatePaymentTransactionId(String transactionId, String orderNumber);

	public List<FilmOrders> getSearch(String orderNumber, String filmTitle,
			String beginTime, String endTime, String scheduledFilmId,
			String beginShowTime, String endShowTime,String cinemaId);

	public List<Payment> getPaymentUserNickName(String orderNumber);
	
	public List<FilmOrder> getFilmOrderByOrderNumber(String orderNumber);
	
	public List<FilmInfo> getFilmInfoByFilmId(String filmTitle);
	
	public Integer findActicityFilmInfoByActivityId(String activityId);
	
	public List<UserVipMember> getSearchUserVipMember(String phone, String areaNumber,Integer page,String submit1,String submit2);
	
	public List<ActivityFilm> getSearchActivityFilm(String filmId, String filmTitle,Integer page);
}
