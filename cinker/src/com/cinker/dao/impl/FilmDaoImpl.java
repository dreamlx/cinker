package com.cinker.dao.impl;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import com.alibaba.druid.util.StringUtils;
import com.cinker.dao.FilmDao;
import com.cinker.model.Cinema;
import com.cinker.model.FilmContentImage;
import com.cinker.model.FilmInfo;
import com.cinker.model.FilmOrder;
import com.cinker.model.Payment;
import com.cinker.model.Screen;
import com.cinker.model.TicketType;
import com.cinker.util.CommonsUtil;

@SuppressWarnings("rawtypes")
@Repository
public class FilmDaoImpl extends BaseDaoImpl implements FilmDao{

	@Override
	public void saveOrder(FilmOrder filmOrder) {
		String sql = "INSERT INTO CINKER_ORDER(OrderNumber,ScheduledFilmId,FilmTitle,CinemaId,SessionId,AreaCategoryCode,Status,StartTime,EndTime,UserNumber,PaymentID,BookingNumber,BookingID,Seats,TotalOrderCount,TotalValueCents,CinemaName,SessionName,ShowTime,OrderType,MemberLevelId)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		update(sql,new Object[]{filmOrder.getOrderNumber(),filmOrder.getScheduledFilmId(),filmOrder.getFilmTitle(),filmOrder.getCinemaId(),filmOrder.getSessionId(),filmOrder.getAreaCategoryCode(),filmOrder.getStatus(),filmOrder.getStartTime(),filmOrder.getEndTime(),filmOrder.getUserNumber(),filmOrder.getPaymentId(),filmOrder.getBookingNumber(),filmOrder.getBookingID(),filmOrder.getSeat(),filmOrder.getTotalOrderCount(),filmOrder.getTotalValueCents(),filmOrder.getCinemaName(),filmOrder.getSessionName(),filmOrder.getShowTime(),filmOrder.getOrderType(),filmOrder.getMemberLevelId()});
	}

	@Override
	public void saveTicketType(List<TicketType> ticketTypes) {
		for(TicketType ticketType : ticketTypes){
			String sql = "INSERT INTO cinker_order_ticket(TicketTypeCode,PriceInCents,TicketsNumber,OrderNumber)VALUES(?,?,?,?)";
			update(sql,new Object[]{ticketType.getTicketTypeCode(),CommonsUtil.roundByScale(ticketType.getPriceInCents()/100,2),ticketType.getQty(),ticketType.getOrderId()});
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public FilmOrder getFilmOrder(String orderNumber) {
		try{
			String sql = "select * from cinker_order where OrderNumber = ?";
			return jdbcTemplate.queryForObject(sql, new Object[]{orderNumber},  new RowMapper(){
				 public Object mapRow(ResultSet rs,int rowNum)throws SQLException { 
					 FilmOrder filmOrder = new FilmOrder();
					 filmOrder.setOrderNumber(rs.getString("OrderNumber"));
					 filmOrder.setScheduledFilmId(rs.getString("ScheduledFilmId"));
					 filmOrder.setFilmTitle(rs.getString("FilmTitle"));
					 filmOrder.setCinemaId(rs.getString("CinemaId"));
					 filmOrder.setCinemaName(rs.getString("CinemaName"));
					 filmOrder.setSessionId(rs.getString("SessionId"));
					 filmOrder.setSessionName(rs.getString("SessionName"));
					 filmOrder.setEndTime(rs.getString("EndTime"));
					 filmOrder.setShowTime(rs.getString("ShowTime"));
					 filmOrder.setSeat(rs.getString("Seats"));
					 filmOrder.setOrderType(rs.getInt("OrderType"));
					 filmOrder.setUserNumber(rs.getString("UserNumber"));
					 filmOrder.setTotalOrderCount(rs.getInt("TotalOrderCount"));
					 filmOrder.setTotalValueCents(rs.getString("TotalValueCents"));		
					 filmOrder.setBookingID(rs.getString("BookingID"));
					 filmOrder.setBookingNumber(rs.getString("BookingNumber"));
					 filmOrder.setAreaCategoryCode(rs.getString("AreaCategoryCode"));
					 filmOrder.setMemberLevelId(rs.getString("MemberLevelId"));
					 filmOrder.setStatus(rs.getInt("Status"));
					 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					 filmOrder.setStartTime(df.format(rs.getDate("StartTime")));
					 return filmOrder;
				 }
			});
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public TicketType getTicketType(String orderNumber) {
		try{
			String sql = "select * from cinker_order_ticket where OrderNumber = ?";
			return jdbcTemplate.queryForObject(sql, new Object[]{orderNumber},  new RowMapper(){
				 public Object mapRow(ResultSet rs,int rowNum)throws SQLException { 
					 TicketType ticketType = new TicketType();
					 ticketType.setPriceInCents(rs.getDouble("PriceInCents"));
					 ticketType.setTicketTypeCode(rs.getString("TicketTypeCode"));
					 return ticketType;
				 }
			});
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

	@Override
	public void updateOrderSuccess(int status,Date endTime,String bookingNumber,String bookingId,String orderNumber) {
		// TODO Auto-generated method stub
		String sql="update cinker_order set Status=?,endTime=?,BookingNumber=?,BookingID=? where OrderNumber=?";
		update(sql,status,endTime,bookingNumber,bookingId,orderNumber);
	}

	@Override
	public void cancleOrder(int status,String id) {
		// TODO Auto-generated method stub
		String sql="update cinker_order set Status=? where id=?";
		update(sql,status,id);
	}
	
	@Override
	public void updateOrderPaymentID(int paymentID,String orderNumber) {
		// TODO Auto-generated method stub
		String sql="update cinker_order set PaymentID=? where OrderNumber=?";
		update(sql,paymentID,orderNumber);
	}

	@Override
	public List<FilmContentImage> getFilmContentImage(String scheduleFilmId) {
		try{
			String sql = "select * from cinker_film_content_image where FilmId = ?";
			List<FilmContentImage> filmContentImageList=new ArrayList<FilmContentImage>();
			List<Map<String,Object>> resultSetList=jdbcTemplate.queryForList(sql,scheduleFilmId);
			for(int i=0;i<resultSetList.size();i++){
				Map<String,Object> map=resultSetList.get(i);
				FilmContentImage filmContentImage=new FilmContentImage();
				if(null!=map.get("ImageUrl"))
					filmContentImage.setImageUrl((String)map.get("ImageUrl"));
				filmContentImageList.add(filmContentImage);
			}
			return filmContentImageList;
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Cinema> getCinemas(String cityId,String cinemaType) {
		try{
			String sql = "SELECT * FROM cinker_cinema WHERE City = ? AND Type = ?";		
			List<Cinema> cinemaList = jdbcTemplate.query(sql,new Object[]{cityId,cinemaType},new BeanPropertyRowMapper(Cinema.class));
			return cinemaList;
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	@Override
	public List<Cinema> getCinemasByType(String cinemaType) {
		try{
			String sql = "SELECT * FROM cinker_cinema WHERE Type = ?";		
			List<Cinema> cinemaList = jdbcTemplate.query(sql,new Object[]{cinemaType},new BeanPropertyRowMapper(Cinema.class));
			return cinemaList;
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Cinema> getCinemas() {
		try{
			String sql = "SELECT * FROM cinker_cinema ";		
			List<Cinema> cinemaList = jdbcTemplate.query(sql,new Object[]{},new BeanPropertyRowMapper(Cinema.class));
			return cinemaList;
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	@Override
	public List<FilmOrder> getFilmOrderListByUserNumber(String userNumber,String cinemaId) {
		try{
			String sql = "";
			List<Map<String,Object>> resultSetList = null;
				
			if (StringUtils.isEmpty(cinemaId)){
			 sql = "SELECT co.OrderNumber,co.FilmTitle,co.CinemaName,co.SessionName,co.ShowTime,co.StartTime as coStartTime,co.TotalValueCents,cop.Status as paymentStatus " +
					"FROM cinker_order co LEFT JOIN cinker_order_payment cop ON co.PaymentID=cop.PaymentID " +
					"WHERE co.UserNumber = ? " +
					"AND DATE_ADD(co.StartTime,INTERVAL 10 MINUTE) >= NOW() " +
					"AND co.Status = 0 " +
					"UNION " +
					"SELECT co.OrderNumber,co.FilmTitle,co.CinemaName,co.SessionName,co.ShowTime,co.StartTime as coStartTime,co.TotalValueCents,cop.Status as paymentStatus  " +
					"FROM cinker_order co LEFT JOIN cinker_order_payment cop ON co.PaymentID=cop.PaymentID  " +
					"WHERE co.UserNumber = ? " +
					"AND co.Status = 1 ORDER BY coStartTime DESC";

				resultSetList = jdbcTemplate.queryForList(sql,userNumber, userNumber);
			} else {
				sql = "SELECT co.OrderNumber,co.FilmTitle,co.CinemaName,co.SessionName,co.ShowTime,co.StartTime as coStartTime,co.TotalValueCents,cop.Status as paymentStatus " +
				"FROM cinker_order co LEFT JOIN cinker_order_payment cop ON co.PaymentID=cop.PaymentID " +
				"WHERE co.UserNumber = ? " +
				"AND co.CinemaId = ? " +
				"AND DATE_ADD(co.StartTime,INTERVAL 10 MINUTE) >= NOW() " +
				"AND co.Status = 0 " +
				"UNION " +
				"SELECT co.OrderNumber,co.FilmTitle,co.CinemaName,co.SessionName,co.ShowTime,co.StartTime as coStartTime,co.TotalValueCents,cop.Status as paymentStatus  " +
				"FROM cinker_order co LEFT JOIN cinker_order_payment cop ON co.PaymentID=cop.PaymentID  " +
				"WHERE co.UserNumber = ? " +
				"AND co.CinemaId = ? " +
				"AND co.Status = 1 ORDER BY coStartTime DESC";

				resultSetList = jdbcTemplate.queryForList(sql,userNumber,cinemaId, userNumber,cinemaId);
			}
			List<FilmOrder> filmOrderList=new ArrayList<FilmOrder>();
			for(int i=0;i<resultSetList.size();i++){
				Map<String,Object> map=resultSetList.get(i);
				FilmOrder filmOrder=new FilmOrder();
				if(null!=map.get("OrderNumber"))
					filmOrder.setOrderNumber((String)map.get("OrderNumber"));
				if(null!=map.get("FilmTitle"))
					filmOrder.setFilmTitle((String)map.get("FilmTitle"));
				if(null!=map.get("CinemaName"))
					filmOrder.setCinemaName((String)map.get("CinemaName"));
				if(null!=map.get("SessionName"))
					filmOrder.setSessionName((String)map.get("SessionName"));
				if(null!=map.get("ShowTime"))
					filmOrder.setShowTime((String)map.get("ShowTime"));
				if(null!=map.get("TotalValueCents"))
					filmOrder.setTotalValueCents((String)map.get("TotalValueCents"));
				if(null!=map.get("paymentStatus"))
					filmOrder.setPaymentStatus((Integer)map.get("paymentStatus"));
				else
					filmOrder.setPaymentStatus(Payment.PAYMENT_STATUS_INIT);
				filmOrderList.add(filmOrder);
			}
			return filmOrderList;
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Cinema getCinema(String id) {
		String sql = "select * from cinker_cinema where CinemaId = ?";
		try{
			return jdbcTemplate.queryForObject(sql, new Object[]{id},  new RowMapper(){
				 public Object mapRow(ResultSet rs,int rowNum)throws SQLException { 
					 Cinema cinema = new Cinema();
					 cinema.setCinemaId(rs.getString("CinemaId"));
					 cinema.setName(rs.getString("Name"));
					 cinema.setAddress(rs.getString("Address"));
					 cinema.setCity(rs.getString("City"));
					 cinema.setType(rs.getString("Type"));
					 cinema.setTel(rs.getString("Tel"));
					 return cinema;
				 }
			});
		}catch (EmptyResultDataAccessException e) {
			 return null;
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FilmOrder> getOrderDetailByOrderNumber(String orderNumber) {
		try{
			String sql = "SELECT op.Type,co.TotalValueCents,co.OrderNumber,op.TransactionId " 
				+ " from cinker_order co,cinker_order_payment op where co.OrderNumber = op.OrderNumber" 
				+ " AND co.OrderNumber = '" + orderNumber + "'";
			return jdbcTemplate.query(sql,new BeanPropertyRowMapper(FilmOrder.class));
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Screen> getScreens(String cinemaId) {
		try{
			String sql = "SELECT * FROM cinker_screen WHERE CinemaId = ? ORDER BY ScreenNumber";		
			List<Screen> screenList = jdbcTemplate.query(sql,new Object[]{cinemaId},new BeanPropertyRowMapper(Screen.class));
			return screenList;
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getTicketsCount(String userNumber, String cinemaId, String sessionId, String showTime) {
		try{
			String sql = "SELECT SUM(TotalOrderCount) FROM cinker_order WHERE UserNumber = ? " +
				"AND CinemaId = ? " +
				"AND sessionId = ? " +
				"AND ShowTime = ? " +
				"AND Status = 1";

			return jdbcTemplate.queryForObject(sql,new Object[]{userNumber,cinemaId,sessionId,showTime},Integer.class);

		}catch(Exception e){
			if((e instanceof IncorrectResultSizeDataAccessException) &&((IncorrectResultSizeDataAccessException)e).getActualSize()==0)
			return 0;
		}
		return 0;
	}

}
