package com.cinker.service;

import java.util.Date;
import java.util.List;

import com.cinker.model.Cinema;
import com.cinker.model.Film;
import com.cinker.model.FilmContentImage;
import com.cinker.model.FilmOrder;
import com.cinker.model.Screen;

import com.cinker.model.GetTicketsInfo;

import com.cinker.model.TicketType;


public interface FilmService {
	
	public List<Film> getFilms(String cinemaId);
	
	public List<Cinema> getCinemas(String cityId,String cinemaType);
	
	public List<Cinema> getCinemasByType(String cinemaType);
	
	public List<Cinema> getCinemas();
	
	public Cinema getCinema(String id);
	
	public String addOrder(String orderId, FilmOrder filmOrder);
	
	public void saveOrder(FilmOrder filmOrder);
	
	public void saveTicketType(List<TicketType> ticketTypes);
	
	public FilmOrder getFilmOrder(String orderNumber);
	
	public void updateOrderSuccess(int status,Date endTime,String bookingNumber,String bookingId,String orderNumber);
	
	public void cancleOrder(int status,String id);

	public void getFilmTicket(String cinemaId, String sessionId,GetTicketsInfo getTicketsInfo,String orderId,String scheduledFilmId);
	
	public TicketType getTicketType(String orderNumber);
	
	public void updateOrderPaymentID(int paymentID,String orderNumber);
	
	public List<FilmContentImage> getFilmContentImage(String scheduleFilmId);
	
	public List<FilmOrder> getFilmOrderListByUserNumber(String userNumber,String cinemaId);
	
	public List<FilmOrder> getOrderDetailByOrderNumber(String orderNumber);
	
	public List<Screen> getScreens(String cinemaId);
	
	public int getTicketsCount(String userNumber, String cinemaId, String sessionId, String showTime);
	
	public int getRestTickets(String userNumber, String memberLevelId, String cinemaId, String cinemaType, String sessionId, String sessionShowTime, String showTime);
}
