package com.cinker.dao.impl;




import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

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





@SuppressWarnings("rawtypes")
@Repository
public class CinemaDaoImpl extends BaseDaoImpl implements CinemaDao{
 	
	private Integer pageSize = 20;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Cinema> getCinema() {
		String sql = "SELECT * FROM cinker_cinema";		
		try {
			List<Cinema> cinemaList = jdbcTemplate.query(sql,new BeanPropertyRowMapper(Cinema.class));
			return cinemaList;
		}catch (EmptyResultDataAccessException e) {
			 return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Screen> getScreen() {
		String sql = "SELECT * FROM cinker_screen";		
		try {
			List<Screen> screenList = jdbcTemplate.query(sql,new BeanPropertyRowMapper(Screen.class));
			return screenList;
		}catch (EmptyResultDataAccessException e) {
			 return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FilmOrders> getFilmOrders(Integer page) {
		try {
			String sql = "";
			if(page == null || page == 0){
				sql = "select * from cinker_order order by StartTime DESC";
			}else{
				sql = "select * from cinker_order order by StartTime DESC LIMIT "+(page-1)*pageSize +","+pageSize +"";
			}
			List<FilmOrders> filmOrdersList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(FilmOrders.class));
			return filmOrdersList;
		}catch (EmptyResultDataAccessException e) {
			 return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FilmInfo> getFilmInfo() {
		String sql = "SELECT * FROM  cinker_film order by ShowTime DESC";
		try {
			List<FilmInfo> filmInfoList = jdbcTemplate.query(sql,new BeanPropertyRowMapper(FilmInfo.class));
			return filmInfoList;
		}catch (EmptyResultDataAccessException e) {
			 return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FilmInfo> getFilmInfoEnglishName() {
		String sql = "SELECT co.StartTime,cf.FilmId,cf.ChineseName,cf.FilmType,cf.EnglishName,co.StartTime from cinker_film cf, cinker_order co where cf.ChineseName = co.FilmTitle and cf.FilmId = co.ScheduledFilmId";
		try {
			List<FilmInfo> filmInfoList = jdbcTemplate.query(sql,new BeanPropertyRowMapper(FilmInfo.class));
			return filmInfoList;
		}catch (EmptyResultDataAccessException e) {
			 return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FilmInfo> getFilmInfoType(String filmId) {
		String sql = "SELECT * FROM  cinker_film WHERE filmId = ?";
		try {
			List<FilmInfo> filmInfoList = jdbcTemplate.query(sql,new BeanPropertyRowMapper(FilmInfo.class));
			return filmInfoList;
		}catch (EmptyResultDataAccessException e) {
			 return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Cinema editCinema(String id) {
		try {
			String sql = "SELECT * FROM cinker_cinema where id = ?";
			return jdbcTemplate.queryForObject(sql, new Object[]{id},  new RowMapper(){
				 public Object mapRow(ResultSet rs,int rowNum)throws SQLException { 
					 Cinema cinema = new Cinema();
					 cinema.setId(rs.getInt("ID"));
					 cinema.setCinemaId(rs.getString("CinemaId"));
					 cinema.setCity(rs.getString("City"));
					 cinema.setAddress(rs.getString("Address"));
					 cinema.setName(rs.getString("Name"));
					 cinema.setType(rs.getString("Type"));
					 cinema.setTel(rs.getString("Tel"));
					 return cinema;
				 }
			});
		}catch (EmptyResultDataAccessException e) {
			 return null;
		}
		
	}

	@Override
	public void saveCinema(Cinema cinema) {
		String sql = "UPDATE cinker_cinema SET CinemaId = ?,Name = ?,Address = ?,City = ?,Type = ?,Tel = ? WHERE ID = ?";
		jdbcTemplate.update(sql,new Object[]{cinema.getCinemaId(),cinema.getName(),cinema.getAddress(),cinema.getCity(),cinema.getType(),cinema.getId()});
		
	}

	@Override
	public void deleteCinema(int id) {
		String sql = "DELETE FROM cinker_cinema WHERE ID = ? ";
		update(sql,new Object[]{id});
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Screen editScreen(String id) {
		try {
			String sql = "SELECT * FROM cinker_screen where id = ?";
			return jdbcTemplate.queryForObject(sql, new Object[]{id},  new RowMapper(){
				 public Object mapRow(ResultSet rs,int rowNum)throws SQLException { 
					 Screen screen = new Screen();
					 screen.setId(rs.getInt("ID"));
					 screen.setScreenName(rs.getString("ScreenName"));
					 screen.setScreenNumber(rs.getString("ScreenNumber"));
					 screen.setCinemaId(rs.getString("CinemaId"));
					 return screen;
				 }
			});
		}catch (EmptyResultDataAccessException e) {
			 return null;
		}
		
	}

	@Override
	public void saveScreen(Screen screen) {
		String sql = "UPDATE cinker_screen SET ScreenName = ?,ScreenNumber = ?,CinemaId = ? WHERE ID = ?";
		jdbcTemplate.update(sql,new Object[]{screen.getScreenName(),screen.getScreenNumber(),screen.getCinemaId(),screen.getId()});
		
	}

	@Override
	public void deleteScreen(int id) {
		String sql = "DELETE FROM cinker_screen WHERE ID = ? ";
		update(sql,new Object[]{id});
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public FilmInfo editFilmInfo(String id) {
		String sql = "SELECT * FROM cinker_film where ID = ?";
		try {
			return jdbcTemplate.queryForObject(sql, new Object[]{id},  new RowMapper(){
				public Object mapRow(ResultSet rs,int rowNum)throws SQLException {
					FilmInfo filmInfo = new FilmInfo();
					filmInfo.setFilmId(rs.getString("FilmId"));
					filmInfo.setChineseName(rs.getString("ChineseName"));
					filmInfo.setDirector(rs.getString("Director"));
					filmInfo.setEnglishName(rs.getString("EnglishName"));
					filmInfo.setFilmType(rs.getString("FilmType"));
					filmInfo.setId(rs.getInt("ID"));
					filmInfo.setLanguage(rs.getString("Language"));
					filmInfo.setRunTime(rs.getString("RunTime"));
					filmInfo.setScriptWriter(rs.getString("ScriptWriter"));
					filmInfo.setShowTime(rs.getString("ShowTime"));
					filmInfo.setStarringActor(rs.getString("StarringActor"));
					filmInfo.setSurfaceImage(rs.getString("SurfaceImage"));
					filmInfo.setCinecism(rs.getString("Cinecism"));
					return filmInfo;			
				}
			});
		}catch (EmptyResultDataAccessException e) {
			 return null;
		}
	}

	@Override
	public void insertCinema(Cinema cinema) {
		String sql = "INSERT INTO cinker_cinema(CinemaId,Name,Address,City,Type) VALUES(?,?,?,?,?)";
		update(sql,new Object[]{cinema.getCinemaId(),cinema.getName(),cinema.getAddress(),cinema.getCity(),cinema.getType()});
	}

	@Override
	public void insertScreen(Screen screen) {
		String sql = "INSERT INTO cinker_screen(ScreenName,ScreenNumber,CinemaId) VALUES(?,?,?)";
		update(sql,new Object[]{screen.getScreenName(),screen.getScreenNumber(),screen.getCinemaId()});
	}
	
	@Override
	public void saveFilmInfo(FilmInfo filmInfo) {
		String sql = "UPDATE cinker_film SET ChineseName = ?,Director = ?,EnglishName = ?,FilmType = ?,Language = ?,RunTime = ?,ScriptWriter = ?,ShowTime = ?,StarringActor = ?,SurfaceImage = ?,FilmId = ?,Cinecism = ? WHERE ID = ?";
		jdbcTemplate.update(sql,new Object[]{filmInfo.getChineseName(),filmInfo.getDirector(),filmInfo.getEnglishName(),filmInfo.getFilmType(),filmInfo.getLanguage(),filmInfo.getRunTime(),filmInfo.getScriptWriter(),filmInfo.getShowTime(),filmInfo.getStarringActor(),filmInfo.getSurfaceImage(),filmInfo.getFilmId(),filmInfo.getCinecism(),filmInfo.getId()});
		
	}

	@Override
	public void deleteFilmInfo(String id) {
		String sql = "DELETE FROM cinker_film WHERE ID = ? ";
		update(sql,new Object[]{id});
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public FilmOrders editFilmOrders(String id) {
		String sql = "SELECT * FROM cinker_order where id = ?";
		try{
			return jdbcTemplate.queryForObject(sql, new Object[]{id},  new RowMapper(){
				public Object mapRow(ResultSet rs,int rowNum)throws SQLException {
					FilmOrders filmOrder = new FilmOrders();
					filmOrder.setAreaCategoryCode(rs.getString("AreaCategoryCode"));
					filmOrder.setBookingID(rs.getString("BookingID"));
					filmOrder.setBookingNumber(rs.getString("BookingNumber"));
					filmOrder.setCinemaId(rs.getString("CinemaId"));
					filmOrder.setCinemaName(rs.getString("CinemaName"));
					filmOrder.setEndTime(rs.getString("EndTime"));
					filmOrder.setFilmTitle(rs.getString("FilmTitle"));
					filmOrder.setId(rs.getInt("ID"));
					filmOrder.setOrderNumber(rs.getString("OrderNumber"));
					filmOrder.setPaymentID(rs.getInt("PaymentID"));
					filmOrder.setScheduledFilmId(rs.getString("ScheduledFilmId"));
					filmOrder.setSeats(rs.getString("Seats"));
					filmOrder.setSessionId(rs.getString("SessionId"));
					filmOrder.setSessionName(rs.getString("SessionName"));
					filmOrder.setShowTime(rs.getString("ShowTime"));
					filmOrder.setStartTime(rs.getString("StartTime"));
					filmOrder.setStatus(rs.getInt("Status"));
					filmOrder.setOrderType(rs.getInt("OrderType"));
					filmOrder.setTotalOrderCount(rs.getInt("TotalOrderCount"));
					filmOrder.setTotalValueCents(rs.getString("TotalValueCents"));
					filmOrder.setUserNumber(rs.getString("UserNumber"));
					return filmOrder;
				}
			});
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

	@Override
	public void saveFilmOrders(FilmOrders filmOrders) {
		String sql = "UPDATE cinker_order SET AreaCategoryCode = ?,BookingID = ?,BookingNumber = ?,CinemaId = ?,CinemaName = ?,EndTime = ?,FilmTitle = ? ,ID = ?,OrderNumber = ? ,PaymentID = ? ,ScheduledFilmId = ? ,Seats = ? ,SessionId = ? ,SessionName = ? ,ShowTime = ? ,StartTime = ? ,Status = ? ,TotalOrderCount = ? ,TotalValueCents = ? ,UserNumber = ? WHERE id = ?";
		jdbcTemplate.update(sql,new Object[]{filmOrders.getAreaCategoryCode(),filmOrders.getBookingID(),filmOrders.getBookingNumber(),filmOrders.getCinemaId(),filmOrders.getCinemaName(),filmOrders.getEndTime(),filmOrders.getFilmTitle(),filmOrders.getId(),filmOrders.getOrderNumber(),filmOrders.getPaymentID(),filmOrders.getScheduledFilmId(),filmOrders.getSeats(),filmOrders.getSessionId(),filmOrders.getSessionName(),filmOrders.getShowTime(),filmOrders.getStartTime(),filmOrders.getStatus(),filmOrders.getTotalOrderCount(),filmOrders.getTotalValueCents(),filmOrders.getUserNumber(),filmOrders.getId()});
		
	}

	@Override
	public void deleteFilmOrders(String id) {
		String sql = "DELETE FROM cinker_order WHERE id = ? ";
		update(sql,new Object[]{id});
		
	}

	@Override
	public void insertFilmInfo(FilmInfo filmInfo) {
		String sql = "INSERT INTO cinker_film(FilmId,ChineseName,EnglishName,SurfaceImage,Director,ScriptWriter,ShowTime,RunTime,StarringActor,FilmType,Language,Cinecism) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
		update(sql,new Object[]{filmInfo.getFilmId(),filmInfo.getChineseName(),filmInfo.getEnglishName(),filmInfo.getSurfaceImage(),filmInfo.getDirector(),filmInfo.getScriptWriter(),filmInfo.getShowTime(),filmInfo.getRunTime(),filmInfo.getStarringActor(),filmInfo.getFilmType(),filmInfo.getLanguage(),filmInfo.getCinecism()});
	}
	
	@Override
	public void insertFilmOrders(FilmOrders filmOrders) {
		String sql = "INSERT INTO cinker_order(ID,OrderNumber,FilmTitle,CinemaId,CinemaName,SessionId,SessionName,ShowTime,AreaCategoryCode,TotalOrderCount,TotalValueCents,Status,StartTime,EndTime,UserNumber,PaymentID,BookingNumber,BookingID,Seats,OrderType) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		update(sql,new Object[]{filmOrders.getId(),filmOrders.getOrderNumber(),filmOrders.getFilmTitle(),filmOrders.getCinemaId(),filmOrders.getCinemaName(),filmOrders.getSessionId(),filmOrders.getSessionName(),filmOrders.getShowTime(),filmOrders.getAreaCategoryCode(),filmOrders.getTotalOrderCount(),filmOrders.getTotalValueCents(),filmOrders.getStatus(),filmOrders.getStartTime(),filmOrders.getEndTime(),filmOrders.getUserNumber(),filmOrders.getPaymentID(),filmOrders.getBookingNumber(),filmOrders.getBookingID(),filmOrders.getSeats(),filmOrders.getOrderType()});
	}

	@SuppressWarnings("unchecked")
	@Override
	public FilmInfo findFilmInfo(String id) {
		try{
			String sql = "SELECT * FROM cinker_film WHERE ID = ? ";
			return jdbcTemplate.queryForObject(sql, new Object[]{id},  new RowMapper(){
				public Object mapRow(ResultSet rs,int rowNum)throws SQLException {
					FilmInfo filmInfo = new FilmInfo();
					filmInfo.setFilmId(rs.getString("FilmId"));
					filmInfo.setChineseName(rs.getString("ChineseName"));
					filmInfo.setDirector(rs.getString("Director"));
					filmInfo.setEnglishName(rs.getString("EnglishName"));
					filmInfo.setFilmType(rs.getString("FilmType"));
					filmInfo.setId(rs.getInt("ID"));
					filmInfo.setLanguage(rs.getString("Language"));
					filmInfo.setRunTime(rs.getString("RunTime"));
					filmInfo.setScriptWriter(rs.getString("ScriptWriter"));
					filmInfo.setShowTime(rs.getString("ShowTime"));
					filmInfo.setStarringActor(rs.getString("StarringActor"));
					filmInfo.setSurfaceImage(rs.getString("SurfaceImage"));
					filmInfo.setCinecism(rs.getString("Cinecism"));
					return filmInfo;			
				}
			});
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public FilmOrders findFilmOrdersInfo(String id) {
		String sql = "SELECT * FROM cinker_order  co inner join cinker_user_member um on co.UserNumber = um.UserNumber WHERE ID = ?";
		try {
			return jdbcTemplate.queryForObject(sql, new Object[]{id},  new RowMapper(){
				public Object mapRow(ResultSet rs,int rowNum)throws SQLException {
					FilmOrders filmOrder = new FilmOrders();
					filmOrder.setAreaCategoryCode(rs.getString("AreaCategoryCode"));
					filmOrder.setBookingID(rs.getString("BookingID"));
					filmOrder.setBookingNumber(rs.getString("BookingNumber"));
					filmOrder.setCinemaId(rs.getString("CinemaId"));
					filmOrder.setCinemaName(rs.getString("CinemaName"));
					filmOrder.setEndTime(rs.getString("EndTime"));
					filmOrder.setFilmTitle(rs.getString("FilmTitle"));
					filmOrder.setId(rs.getInt("ID"));
					filmOrder.setOrderType(rs.getInt("OrderType"));
					filmOrder.setOrderNumber(rs.getString("OrderNumber"));
					filmOrder.setPaymentID(rs.getInt("PaymentID"));
					filmOrder.setScheduledFilmId(rs.getString("ScheduledFilmId"));
					filmOrder.setSeats(rs.getString("Seats"));
					filmOrder.setSessionId(rs.getString("SessionId"));
					filmOrder.setSessionName(rs.getString("SessionName"));
					filmOrder.setShowTime(rs.getString("ShowTime"));
					filmOrder.setStartTime(rs.getString("StartTime"));
					filmOrder.setStatus(rs.getInt("Status"));
					filmOrder.setTotalOrderCount(rs.getInt("TotalOrderCount"));
					filmOrder.setTotalValueCents(rs.getString("TotalValueCents"));
					filmOrder.setUserNumber(rs.getString("UserNumber"));
					try {
						filmOrder.setUserNickName(URLDecoder.decode(rs.getString("UserNickName"),"utf-8"));
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return filmOrder;
				}
			});
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserMember findUserMember(String userNumber) {
		String sql = "select * from cinker_user_member where UserNumber = (select MAX(UserNumber) from cinker_order where UserNumber = ?) ";
		try{	
			return jdbcTemplate.queryForObject(sql, new Object[]{userNumber},  new RowMapper(){
				public Object mapRow(ResultSet rs,int rowNum)throws SQLException {
					UserMember userMember = new UserMember();
					userMember.setLoginTime(rs.getDate("LoginTime"));
					userMember.setOpenID(rs.getString("OpenID"));
					userMember.setUserHeadImageUrl(rs.getString("UserHeadImageUrl"));
					userMember.setUserNickName(rs.getString("UserNickName"));
					userMember.setUserNumber(rs.getString("UserNumber"));
					userMember.setUserSex(rs.getInt("UserSex"));
					userMember.setVistaMemberCardNumber(rs.getString("VistaMemberCardNumber"));
					return userMember;
				}
			});
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public OrderTicket findOrderTicket(String orderNumber) {
		String sql = "select * from cinker_order_ticket where OrderNumber = (select OrderNumber from cinker_order where OrderNumber = ?)";
		try{
			return jdbcTemplate.queryForObject(sql, new Object[]{orderNumber},  new RowMapper(){
				public Object mapRow(ResultSet rs,int rowNum)throws SQLException {
					OrderTicket orderTicket = new OrderTicket();
					orderTicket.setId(rs.getInt("ID"));
					orderTicket.setOrderNumber(rs.getString("OrderNumber"));
					orderTicket.setPriceInCents(rs.getString("PriceInCents"));
					orderTicket.setTicketsNumber(rs.getInt("TicketsNumber"));
					orderTicket.setTicketTypeCode(rs.getString("TicketTypeCode"));
					return orderTicket;
				}
			});
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Payment findPayment(String paymentID) {
		String sql = "select * from cinker_order_payment where PaymentID = (select PaymentID from cinker_order where PaymentID = ?)  ";
		try{
			return jdbcTemplate.queryForObject(sql, new Object[]{paymentID},  new RowMapper(){
				public Object mapRow(ResultSet rs,int rowNum)throws SQLException {
					Payment payment = new Payment();
					payment.setEndTime(rs.getDate("EndTime"));
					payment.setOrderNumber(rs.getString("OrderNumber"));
					payment.setPaymentID(rs.getInt("PaymentID"));
					payment.setPaymentPrice(rs.getString("PaymentPrice"));
					payment.setStartTime(rs.getDate("StartTime"));
					payment.setStatus(rs.getInt("Status"));
					payment.setType(rs.getInt("Type"));
					return payment;
				}
			});
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

	@Override
	public void saveImage(String filmID, String newFileName) {
		String sql = "INSERT INTO cinker_film_content_image(FilmID,ImageUrl)VALUES(?,?)";
		update(sql, new Object[]{filmID,newFileName});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FilmContentImage> getFilmContentImage(String filmId) {
		String sql = "select * from cinker_film_content_image where FilmId = ?"; 
		try{
			List<FilmContentImage> filmContentImage = jdbcTemplate.query(sql,new Object[]{filmId}, new BeanPropertyRowMapper(FilmContentImage.class));
			return filmContentImage;
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<FilmInfo> getSearch(String id, String chineseName,String englishName,String beginTime,String endTime,Integer page) {
		try{
			StringBuffer sb = new StringBuffer("SELECT * FROM cinker_film WHERE 1 = 1 ");
			if(!StringUtils.isEmpty(id)){
				sb.append("AND FilmId LIKE '%"+ id +"%'");
			}
			if(!StringUtils.isEmpty(chineseName)){
				sb.append("AND ChineseName LIKE '%"+ chineseName +"%'");
			}
			if(!StringUtils.isEmpty(englishName)){
				sb.append("AND EnglishName LIKE '%"+ englishName +"%'");
			}
			if(!StringUtils.isEmpty(beginTime) && !StringUtils.isEmpty(endTime)){
				sb.append("AND ShowTime BETWEEN '"+ beginTime +"' AND '" + endTime +"'");
			}
			sb.append(" ORDER BY ID DESC");
			if(page != 0 && page!= null){
				sb.append(" LIMIT "+(page-1)*pageSize +","+pageSize +"");
			}
			List<FilmInfo> filmInfoList = jdbcTemplate.query(sb.toString(),new BeanPropertyRowMapper(FilmInfo.class));
			return filmInfoList;
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public FilmContentImage findFilmContentImage(String id) {
		String sql = "select * from cinker_film_content_image where ID = ?"; 
		try{
			return jdbcTemplate.queryForObject(sql, new Object[]{id},  new RowMapper(){
				public Object mapRow(ResultSet rs,int rowNum)throws SQLException {
					FilmContentImage filmContentImage = new FilmContentImage();
					filmContentImage.setFilmId(rs.getString("FilmId"));
					filmContentImage.setImageUrl(rs.getString("ImageUrl"));
					return filmContentImage;
				}
			});
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

	@Override
	public void deleteFilmContentImage(String filmId) {
		String sql = "DELETE FROM cinker_film_content_image WHERE FilmId = ? ";
		update(sql,new Object[]{filmId});
		
	}

	@Override
	public void deleteFilmInfoByFIlmId(String filmId) {
		String sql = "DELETE FROM cinker_film WHERE FilmId = ? ";
		update(sql,new Object[]{filmId});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Payment> getPaymentInfo() {
		try{
			StringBuffer sb = new StringBuffer("SELECT um.UserNickName,op.Type, o.OrderNumber, o.Status, o.EndTime, o.PaymentID,op.PaymentPrice,op.TransactionId ");
			sb.append("from  cinker_user_member um,  cinker_order o, cinker_order_payment op");
			sb.append(" where um.UserNumber = o.UserNumber");
			sb.append(" and op.OrderNumber = o.OrderNumber order by o.EndTime DESC");
			return jdbcTemplate.query(sb.toString(),new BeanPropertyRowMapper(Payment.class));
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Payment> getSearchPayment(String userNickName,String orderNumber, String paymentID, String beginTime,String endTime) {
		try{
			StringBuffer sb = new StringBuffer("SELECT um.UserNickName,op.Type, o.OrderNumber, o.Status, o.EndTime, o.PaymentID,op.StartTime,op.PaymentPrice,op.TransactionId ");
			sb.append("from  cinker_user_member um,  cinker_order o, cinker_order_payment op WHERE 1=1 and um.UserNumber = o.UserNumber " +
					"and op.OrderNumber = o.OrderNumber ");
			if(userNickName != null){
				sb.append("AND um.UserNickName like '%" + userNickName +"%'");
			}
			if(orderNumber != null){
				sb.append("AND o.OrderNumber like '%" + orderNumber +"%'");
			}
			if(paymentID != null){
				sb.append("AND o.PaymentID like '%" + paymentID +"%'");
			}
			if(!StringUtils.isEmpty(beginTime) && !StringUtils.isEmpty(endTime)){
				sb.append("AND o.EndTime BETWEEN '"+ beginTime +"' AND '" + endTime +"'");
			}
			sb.append(" ORDER BY o.EndTime DESC");
			return jdbcTemplate.query(sb.toString(),new BeanPropertyRowMapper(Payment.class));
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Payment> getSearchPayment(String userNickName,String orderNumber, String paymentID, String beginTime,String endTime,Integer page) {
		try{
			StringBuffer sb = new StringBuffer("SELECT um.UserNickName,op.Type, o.OrderNumber, o.Status, o.EndTime, o.PaymentID,op.StartTime,op.PaymentPrice,op.TransactionId ");
			sb.append("from  cinker_user_member um,  cinker_order o, cinker_order_payment op WHERE 1=1 and um.UserNumber = o.UserNumber " +
					"and op.OrderNumber = o.OrderNumber ");
			if(userNickName != null){
				sb.append(" AND um.UserNickName like '%" + userNickName +"%'");
			}
			if(orderNumber != null){
				sb.append(" AND o.OrderNumber like '%" + orderNumber +"%'");
			}
			if(paymentID != null){
				sb.append(" AND o.PaymentID like '%" + paymentID +"%'");
			}
			if(!StringUtils.isEmpty(beginTime) && !StringUtils.isEmpty(endTime)){
				sb.append(" AND o.EndTime BETWEEN '"+ beginTime +"' AND '" + endTime +"'");
			}
			sb.append(" ORDER BY o.EndTime DESC ");
			if(page != 0 && page!= null){
				sb.append(" LIMIT "+(page-1)*pageSize +","+pageSize +"");
			}
			return jdbcTemplate.query(sb.toString(),new BeanPropertyRowMapper(Payment.class));
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FilmOrders> getSearch(String orderNumber, String filmTitle,
			String beginTime, String endTime, String scheduledFilmId ,String beginShowTime,String endShowTime,Integer page,String submit1,String submit2,String cinemaId) {
		try{
			StringBuffer sb = new StringBuffer("SELECT um.UserName,um.UserNickName,um.MobilePhone,co.* FROM cinker_order co,cinker_user_member um ");
			sb.append(" WHERE 1=1 AND um.UserNumber = co.UserNumber ");
			if(orderNumber != null){
				sb.append("AND OrderNumber like '%" + orderNumber +"%'");
			}
			if(filmTitle != null){
				sb.append("AND FilmTitle like '%" + filmTitle +"%'");
			}
			if(scheduledFilmId != null){
				sb.append("AND ScheduledFilmId like '%" + scheduledFilmId +"%'");
			}
			if(!StringUtils.isEmpty(beginTime) && !StringUtils.isEmpty(endTime)){
				sb.append("AND EndTime BETWEEN '"+ beginTime +"' AND '" + endTime +"'");
			}
			if(!StringUtils.isEmpty(beginShowTime) && !StringUtils.isEmpty(endShowTime)){
				sb.append("AND ShowTime BETWEEN '"+ beginShowTime +"' AND '" + endShowTime +"'");
			}
			if(!StringUtils.isEmpty(cinemaId)){
				sb.append("AND CinemaId like '%" + cinemaId +"%'");
			}
			
			sb.append(" ORDER BY StartTime DESC");
			if(page != 0 && page!= null){
				sb.append(" LIMIT "+(page-1)*pageSize +","+pageSize +"");
			}
			return jdbcTemplate.query(sb.toString(),new BeanPropertyRowMapper(FilmOrders.class));
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FilmOrders> getSearch(String orderNumber, String filmTitle,
			String beginTime, String endTime, String scheduledFilmId ,String beginShowTime,String endShowTime,String cinemaId) {
		try{
			StringBuffer sb = new StringBuffer("SELECT um.UserName,um.UserNickName,um.MobilePhone,co.* FROM cinker_order co,cinker_user_member um ");
			sb.append(" WHERE 1=1 AND um.UserNumber = co.UserNumber ");
			if(orderNumber != null){
				sb.append("AND OrderNumber like '%" + orderNumber +"%'");
			}
			if(filmTitle != null){
				sb.append("AND FilmTitle like '%" + filmTitle +"%'");
			}
			if(scheduledFilmId != null){
				sb.append("AND ScheduledFilmId like '%" + scheduledFilmId +"%'");
			}
			if(!StringUtils.isEmpty(beginTime) && !StringUtils.isEmpty(endTime)){
				sb.append("AND EndTime BETWEEN '"+ beginTime +"' AND '" + endTime +"'");
			}
			if(!StringUtils.isEmpty(beginShowTime) && !StringUtils.isEmpty(endShowTime)){
				sb.append("AND ShowTime BETWEEN '"+ beginShowTime +"' AND '" + endShowTime +"'");
			}
			if(!StringUtils.isEmpty(cinemaId)){
				sb.append("AND CinemaId like '%" + cinemaId +"%'");
			}
			sb.append(" ORDER BY StartTime DESC");
			return jdbcTemplate.query(sb.toString(),new BeanPropertyRowMapper(FilmOrders.class));
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

	@Override
	public int findFilmInfoByFilmId(String filmId) {
		try{
			String sql = "SELECT COUNT(*) FROM cinker_film WHERE FilmId = ?";
			return jdbcTemplate.queryForObject(sql, new Object[]{filmId}, Integer.class);
		}catch(EmptyResultDataAccessException e){
			return (Integer) null;
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Payment findPaymentInformation(String paymentID){
		try{
			StringBuffer sb = new StringBuffer("select p.*,um.UserNickName AS UserNickName from ");
			sb.append("cinker_order o, cinker_order_payment p, cinker_user_member um ");
			sb.append("where o.OrderNumber = p.OrderNumber ");
			sb.append("and o.UserNumber = um.UserNumber ");
			sb.append("and o.PaymentID = ?");
			return jdbcTemplate.queryForObject(sb.toString(), new Object[]{paymentID},  new RowMapper(){
				public Object mapRow(ResultSet rs,int rowNum)throws SQLException {
					Payment payment = new Payment();
					payment.setEndTime(rs.getTimestamp("EndTime"));
					payment.setOrderNumber(rs.getString("OrderNumber"));
					payment.setPaymentID(rs.getInt("PaymentID"));
					payment.setPaymentPrice(rs.getString("PaymentPrice"));
					payment.setStartTime(rs.getTimestamp("StartTime"));
					payment.setStatus(rs.getInt("Status"));
					payment.setType(rs.getInt("Type"));
					payment.setUserNickName(rs.getString("UserNickName"));
					payment.setTransactionId(rs.getString("TransactionId"));
					return payment;
				}
			});
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Payment findPaymentInfo(String orderNumber){
		try{
			StringBuffer sb = new StringBuffer("select p.*,um.UserNickName AS UserNickName from ");
			sb.append("cinker_order o, cinker_order_payment p, cinker_user_member um ");
			sb.append("where o.OrderNumber = p.OrderNumber ");
			sb.append("and o.UserNumber = um.UserNumber ");
			sb.append("and o.OrderNumber = ?");
			return jdbcTemplate.queryForObject(sb.toString(), new Object[]{orderNumber},  new RowMapper(){
				public Object mapRow(ResultSet rs,int rowNum)throws SQLException {
					Payment payment = new Payment();
					payment.setEndTime(rs.getTimestamp("EndTime"));
					payment.setOrderNumber(rs.getString("OrderNumber"));
					payment.setPaymentID(rs.getInt("PaymentID"));
					payment.setPaymentPrice(rs.getString("PaymentPrice"));
					payment.setStartTime(rs.getTimestamp("StartTime"));
					payment.setStatus(rs.getInt("Status"));
					payment.setType(rs.getInt("Type"));
					payment.setUserNickName(rs.getString("UserNickName"));
					payment.setTransactionId(rs.getString("TransactionId"));
					return payment;
				}
			});
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public FilmInfo findFilmInfoFilmId(String filmId) {
		try{
			String sql = "SELECT * FROM cinker_film WHERE FilmId = ?";
			return jdbcTemplate.queryForObject(sql, new Object[]{filmId},  new RowMapper(){
				public Object mapRow(ResultSet rs,int rowNum)throws SQLException {
					FilmInfo filmInfo = new FilmInfo();
					filmInfo.setFilmId(rs.getString("FilmId"));
					filmInfo.setChineseName(rs.getString("ChineseName"));
					filmInfo.setDirector(rs.getString("Director"));
					filmInfo.setEnglishName(rs.getString("EnglishName"));
					filmInfo.setFilmType(rs.getString("FilmType"));
					filmInfo.setId(rs.getInt("ID"));
					filmInfo.setLanguage(rs.getString("Language"));
					filmInfo.setRunTime(rs.getString("RunTime"));
					filmInfo.setScriptWriter(rs.getString("ScriptWriter"));
					filmInfo.setShowTime(rs.getString("ShowTime"));
					filmInfo.setStarringActor(rs.getString("StarringActor"));
					filmInfo.setSurfaceImage(rs.getString("SurfaceImage"));
					filmInfo.setCinecism(rs.getString("Cinecism"));
					return filmInfo;			
				}
			});
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	@Override
	public void insertActivityFilm(ActivityFilm activityFilm) {
		String sql = "INSERT INTO cinker_activity_film(FilmId,CinemaId,SessionId,SessionName,FilmTitle,ActivityFilmType,TotalValueCents,Total,Quaty,SessionTime,StartSessionTime,Url) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
		update(sql, new Object[]{activityFilm.getFilmId(),activityFilm.getCinemaId(),activityFilm.getSessionId(),activityFilm.getSessionName(),activityFilm.getFilmTitle(),activityFilm.getActivityFilmType(),activityFilm.getTotalValueCents(),activityFilm.getTotal(),activityFilm.getQuaty(),activityFilm.getSessionTime(),activityFilm.getStartSessionTime(),activityFilm.getUrl()});
	}

	@Override
	public void updateActivityFilm(ActivityFilm activityFilm) {
		String sql = "UPDATE cinker_activity_film SET FilmId = ?, CinemaId = ?, SessionId = ?, SessionName = ?, FilmTitle = ?,ActivityFilmType = ? , TotalValueCents = ?, Total = ?, Quaty = ?,SessionTime = ?, StartSessionTime = ? ,Url = ?, isForUpperMember = ? WHERE ID = ?";
		update(sql, new Object[]{activityFilm.getFilmId(),activityFilm.getCinemaId(),activityFilm.getSessionId(),activityFilm.getSessionName(),activityFilm.getFilmTitle(),activityFilm.getActivityFilmType(),activityFilm.getTotalValueCents(),activityFilm.getTotal(),activityFilm.getQuaty(),activityFilm.getSessionTime(),activityFilm.getStartSessionTime(),activityFilm.getUrl(),activityFilm.getIsForUpperMember(),activityFilm.getId()});
	}

	@Override
	public void deleteActivityFilm(int id) {
		String sql = "DELETE FROM cinker_activity_film WHERE ID = ?";
		update(sql, new Object[]{id});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ActivityFilm> getActivityFilmInfo() {
		try{
			String sql = "SELECT * FROM cinker_activity_film";
			List<ActivityFilm> activityFilmList = jdbcTemplate.query(sql,new BeanPropertyRowMapper(ActivityFilm.class));
			return  activityFilmList;
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ActivityFilm editActivityFilm(String id) {
		try{
			String sql = "SELECT * FROM cinker_activity_film where ID = ?";
			return jdbcTemplate.queryForObject(sql, new Object[]{id},  new RowMapper(){
				public Object mapRow(ResultSet rs,int rowNum)throws SQLException {
					ActivityFilm activityFilm = new ActivityFilm();
					activityFilm.setFilmId(rs.getString("FilmId"));
					activityFilm.setCinemaId(rs.getString("CinemaId"));
					activityFilm.setFilmTitle(rs.getString("FilmTitle"));
					activityFilm.setId(rs.getString("ID"));
					activityFilm.setSessionId(rs.getString("SessionId"));
					activityFilm.setSessionName(rs.getString("SessionName"));
					activityFilm.setActivityFilmType(rs.getString("ActivityFilmType"));
					activityFilm.setStartSessionTime(rs.getString("StartSessionTime"));
					activityFilm.setTotal(rs.getInt("Total"));
					activityFilm.setTotalValueCents(rs.getDouble("TotalValueCents"));	
					activityFilm.setQuaty(rs.getInt("Quaty"));
					activityFilm.setSessionTime(rs.getString("SessionTime"));
					activityFilm.setUrl(rs.getString("Url"));
					activityFilm.setIsForUpperMember(rs.getInt("isForUpperMember"));
					return activityFilm;			
				}
			});	
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ActivityPersonal> getActivityPersonal(Integer page) {
		try{
			String sql = "";
			if(page == null || page == 0){
				sql = "SELECT * FROM cinker_activity_personal WHERE Status = 1 ORDER BY DateTime DESC";
			}else {
				sql = "SELECT * FROM cinker_activity_personal WHERE Status = 1 ORDER BY DateTime DESC LIMIT " + (page-1)*pageSize +","+pageSize +"";
			}
			return jdbcTemplate.query(sql, new BeanPropertyRowMapper(ActivityPersonal.class));
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

	@Override
	public void updatePaymentTransactionId(String transactionId, String orderNumber) {
		String sql = "UPDATE cinker_order_payment SET TransactionId = ? WHERE OrderNumber = ?";
		update(sql,transactionId,orderNumber );
	}

	@Override
	public int getActivityPersonalCount() {
		String sql = "SELECT COUNT(*) FROM cinker_activity_personal WHERE Status = 1";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

	@Override
	public int getFilmOrdersTotal() {
		String sql = "SELECT COUNT(*) FROM cinker_order";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserMember> getPaymentInformations(String userNumber) {
		String sql = "SELECT * FROM cinker_order_payment where userNumber = " + userNumber + "";		
		try {
			List<UserMember> userMemberList = jdbcTemplate.query(sql,new BeanPropertyRowMapper(UserMember.class));
			return userMemberList;
		}catch (EmptyResultDataAccessException e) {
			 return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Payment> getPaymentUserNickName(String orderNumber) {
		try{
			StringBuffer sb = new StringBuffer("SELECT op.*,um.UserNickName ");
            sb.append(" from  cinker_user_member um,  cinker_order o, cinker_order_payment op ");
            sb.append(" where um.UserNumber = o.UserNumber ");
			sb.append(" and op.OrderNumber = o.OrderNumber ");
			sb.append(" and op.OrderNumber = '"+orderNumber + "'");
			return jdbcTemplate.query(sb.toString(),new BeanPropertyRowMapper(Payment.class));
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FilmOrder> getFilmOrderByOrderNumber(String orderNumber) {
		String sql = "SELECT * FROM cinker_order where orderNumber = '" + orderNumber + "'";		
		try {
			List<FilmOrder> filmOrderList = jdbcTemplate.query(sql,new BeanPropertyRowMapper(FilmOrder.class));
			return filmOrderList;
		}catch (EmptyResultDataAccessException e) {
			 return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FilmInfo> getFilmInfoByFilmId(String filmTitle) {
		String sql = "SELECT * FROM cinker_film where ChineseName = '" + filmTitle + "'";		
		try {
			List<FilmInfo> filmInfoList = jdbcTemplate.query(sql,new BeanPropertyRowMapper(FilmInfo.class));
			return filmInfoList;
		}catch (EmptyResultDataAccessException e) {
			 return null;
		}
	}

	@Override
	public Integer findActicityFilmInfoByActivityId(String activityId) {
		try{
			String sql = "SELECT SUM(Quaty) FROM cinker_activity_personal where ActivityId = '"+ activityId +"' AND Status = 1"  ;
			return jdbcTemplate.queryForObject(sql, Integer.class);
		}catch(Exception e){
			if((e instanceof IncorrectResultSizeDataAccessException) &&((IncorrectResultSizeDataAccessException)e).getActualSize()==0)
			return 0;
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserVipMember> getSearchUserVipMember(String phone,
			String areaNumber, Integer page, String submit1, String submit2) {
		try{
			StringBuffer sb = new StringBuffer("SELECT * FROM `cinker_user_member` WHERE VistaMemberCardNumber is not null ");
			if(phone != null){
				sb.append(" AND MobilePhone like '%" + phone +"%' ");
			}
			if(areaNumber != null){
				sb.append(" AND VistaMemberCardNumber like '%" + areaNumber +"%' ");
			}
			sb.append(" ORDER BY VistaMemberCardNumber DESC");
			if(page != 0 && page!= null){
				sb.append(" LIMIT "+(page-1)*pageSize +","+pageSize +"");
			}
			return jdbcTemplate.query(sb.toString(),new BeanPropertyRowMapper(UserVipMember.class));
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ActivityFilm> getSearchActivityFilm(String filmId,
			String filmTitle, Integer page) {
		try{
			StringBuffer sb = new StringBuffer("SELECT * FROM cinker_activity_film WHERE 1=1 ");
			if(filmId != null){
				sb.append(" AND FilmId like '%" + filmId +"%' ");
			}
			if(filmTitle != null){
				sb.append(" AND FilmTitle like '%" + filmTitle +"%' ");
			}
			sb.append(" ORDER BY ID DESC");
			if(page != 0 && page!= null){
				sb.append(" LIMIT "+(page-1)*pageSize +","+pageSize +"");
			}
			return jdbcTemplate.query(sb.toString(),new BeanPropertyRowMapper(ActivityFilm.class));
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

}

