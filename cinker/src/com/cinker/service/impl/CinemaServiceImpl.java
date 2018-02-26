package com.cinker.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinker.dao.CinemaDao;
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
import com.cinker.service.CinemaService;


@Service
public class CinemaServiceImpl implements CinemaService{

	@Autowired
	CinemaDao cinemaDao;
	
	@Override
	public List<Cinema> getCinema() {
	
		return cinemaDao.getCinema();
	}

	@Override
	public List<Screen> getScreen() {
	
		return cinemaDao.getScreen();
	}
	
	@Override
	public List<FilmOrders> getFilmOrders(Integer page) {
		return cinemaDao.getFilmOrders(page);
	}

	@Override
	public List<FilmInfo> getFilmInfo() {
		
		return cinemaDao.getFilmInfo();
	}
	
	@Override
	public List<FilmInfo> getFilmInfoEnglishName() {
		
		return cinemaDao.getFilmInfoEnglishName();
	}

	@Override
	public Cinema editCinema(String id) {
		
		return cinemaDao.editCinema(id);
	}

	@Override
	public void saveCinema(Cinema cinema) {
		cinemaDao.saveCinema(cinema);
		
	}

	@Override
	public void deleteCinema(int id) {
		cinemaDao.deleteCinema(id);
		
	}

	@Override
	public Screen editScreen(String id) {
		
		return cinemaDao.editScreen(id);
	}

	@Override
	public void saveScreen(Screen screen) {
		cinemaDao.saveScreen(screen);
		
	}

	@Override
	public void deleteScreen(int id) {
		cinemaDao.deleteScreen(id);
		
	}

	@Override
	public FilmInfo editFilmInfo(String id) {
		
		return cinemaDao.editFilmInfo(id);
	}

	@Override
	public void insertCinema(Cinema cinema) {		
		cinemaDao.insertCinema(cinema);
	}

	@Override
	public void insertScreen(Screen screen) {		
		cinemaDao.insertScreen(screen);
	}

	@Override
	public void saveFilmInfo(FilmInfo filmInfo) {
		cinemaDao.saveFilmInfo(filmInfo);
		
	}

	@Override
	public void deleteFilmInfo(String id) {
		cinemaDao.deleteFilmInfo(id);
		
	}

	@Override
	public FilmOrders editFilmOrders(String id) {
		
		return cinemaDao.editFilmOrders(id);
	}

	@Override
	public void saveFilmOrders(FilmOrders filmOrders) {
		cinemaDao.saveFilmOrders(filmOrders);
	}

	@Override
	public void deleteFilmOrders(String id) {
		cinemaDao.deleteFilmOrders(id);
		
	}

	@Override
	public void insertFilmInfo(FilmInfo filmInfo) {
		cinemaDao.insertFilmInfo(filmInfo);
		
	}

	@Override
	public void insertFilmOrders(FilmOrders filmOrders) {
		cinemaDao.insertFilmOrders(filmOrders);
		
	}

	@Override
	public FilmInfo findFilmInfo(String id) {
		
		return cinemaDao.findFilmInfo(id);
	}

	@Override
	public FilmOrders findFilmOrdersInfo(String id) {
		
		return cinemaDao.findFilmOrdersInfo(id);
	}

	@Override
	public Payment findPayment(String paymentID) {
		
		return cinemaDao.findPayment(paymentID);
	}

	@Override
	public OrderTicket findOrderTicket(String orderNumber) {
		
		return cinemaDao.findOrderTicket(orderNumber);
	}

	@Override
	public UserMember findUserMember(String userNumber) {
		
		return cinemaDao.findUserMember(userNumber);
	}

	@Override
	public void saveImage(String filmId, String newFileName) {
		cinemaDao.saveImage(filmId, newFileName);
		
	}

	@Override
	public List<FilmContentImage> getFilmContentImage(String filmId) {
		return cinemaDao.getFilmContentImage(filmId);
		
	}

	@Override
	public FilmContentImage findFilmContentImage(String id) {		
		return cinemaDao.findFilmContentImage(id);
	}

	@Override
	public void deleteFilmContentImage(String filmId) {
		cinemaDao.deleteFilmContentImage(filmId);		
	}

	@Override
	public void deleteFilmInfoByFIlmId(String filmId) {
		cinemaDao.deleteFilmInfoByFIlmId(filmId);
		
	}

	
	@Override
	public List<FilmInfo> getSearch(String id, String chineseName,
			String englishName,String beginTime,String endTime,Integer page) {
		return cinemaDao.getSearch(id, chineseName, englishName, beginTime, endTime, page);
	}

	@Override
	public List<Payment> getPaymentInfo() {
		
		return cinemaDao.getPaymentInfo();
	}

	@Override
	public List<Payment> getSearchPayment(String userNickName,
			String orderNumber, String paymentID, String beginTime,
			String endTime) {
		
		return cinemaDao.getSearchPayment(userNickName, orderNumber, paymentID, beginTime, endTime);
	}

	@Override
	public List<Payment> getSearchPayment(String userNickName,
			String orderNumber, String paymentID, String beginTime,
			String endTime,Integer page) {
		
		return cinemaDao.getSearchPayment(userNickName, orderNumber, paymentID, beginTime, endTime,page);
	}

	@Override
	public int findFilmInfoByFilmId(String filmId) {
		return cinemaDao.findFilmInfoByFilmId(filmId);
	}

	@Override
	public Payment findPaymentInformation(String paymentID) {
		
		return cinemaDao.findPaymentInformation(paymentID);
	}
	
	@Override
	public Payment findPaymentInfo(String orderNumber) {
		
		return cinemaDao.findPaymentInfo(orderNumber);
	}

	@Override
	public FilmInfo findFilmInfoFilmId(String filmId) {
		return cinemaDao.findFilmInfoFilmId(filmId);
	}

	@Override
	public List<FilmInfo> getFilmInfoType(String filmId) {
		// TODO Auto-generated method stub
		return cinemaDao.getFilmInfoType(filmId);
	}

	@Override
	public List<ActivityFilm> getActivityFilmInfo() {
		
		return cinemaDao.getActivityFilmInfo();
	}

	@Override
	public void insertActivityFilm(ActivityFilm activityFilm) {
		cinemaDao.insertActivityFilm(activityFilm);
		
	}

	@Override
	public void updateActivityFilm(ActivityFilm activityFilm) {
		cinemaDao.updateActivityFilm(activityFilm);
		
	}

	@Override
	public void deleteActivityFilm(int id) {
		cinemaDao.deleteActivityFilm(id);
		
	}

	@Override
	public ActivityFilm editActivityFilm(String id) {
		
		return cinemaDao.editActivityFilm(id);
	}

	@Override
	public List<ActivityPersonal> getActivityPersonal(Integer page) {
		
		return cinemaDao.getActivityPersonal(page);
	}

	@Override
	public List<FilmOrders> getSearch(String orderNumber, String filmTitle,
			String beginTime, String endTime, String scheduledFilmId,
			String beginShowTime, String endShowTime,Integer page,String submit1,String submit2,String cinemaId) {
		
		return cinemaDao.getSearch(orderNumber, filmTitle, beginTime, endTime, scheduledFilmId, beginShowTime, endShowTime,page,submit1,submit2,cinemaId);
	}

	@Override
	public List<FilmOrders> getSearch(String orderNumber, String filmTitle,
			String beginTime, String endTime, String scheduledFilmId,
			String beginShowTime, String endShowTime,String cinemaId) {
		
		return cinemaDao.getSearch(orderNumber, filmTitle, beginTime, endTime, scheduledFilmId, beginShowTime, endShowTime, cinemaId);
	}

	
	@Override
	public void updatePaymentTransactionId(String transactionId,
			String orderNumber) {
		cinemaDao.updatePaymentTransactionId(transactionId, orderNumber);
		
	}

	@Override
	public int getActivityPersonalCount() {
		// TODO Auto-generated method stub
		return cinemaDao.getActivityPersonalCount();
	}

	@Override
	public int getFilmOrdersTotal() {
		// TODO Auto-generated method stub
		return cinemaDao.getFilmOrdersTotal();
	}

	@Override
	public List<UserMember> getPaymentInformations(String userNumber) {
		// TODO Auto-generated method stub
		return cinemaDao.getPaymentInformations(userNumber);
	}

	@Override
	public List<Payment> getPaymentUserNickName(String orderNumber) {
		// TODO Auto-generated method stub
		return cinemaDao.getPaymentUserNickName(orderNumber);
	}

	@Override
	public List<FilmOrder> getFilmOrderByOrderNumber(String orderNumber) {
		return cinemaDao.getFilmOrderByOrderNumber(orderNumber);
	}

	@Override
	public List<FilmInfo> getFilmInfoByFilmId(String filmTitle) {
		return cinemaDao.getFilmInfoByFilmId(filmTitle);
	}

	@Override
	public Integer findActicityFilmInfoByActivityId(String activityId) {
		return cinemaDao.findActicityFilmInfoByActivityId(activityId);
	}

	@Override
	public List<UserVipMember> getSearchUserVipMember(String phone,
			String areaNumber, Integer page, String submit1, String submit2) {
		return cinemaDao.getSearchUserVipMember(phone, areaNumber, page, submit1, submit2);
	}

	@Override
	public List<ActivityFilm> getSearchActivityFilm(String filmId,
			String filmTitle, Integer page) {
		return cinemaDao.getSearchActivityFilm(filmId, filmTitle, page);
	}

}
