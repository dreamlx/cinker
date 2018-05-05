package com.cinker.action;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import com.alibaba.druid.util.StringUtils;
import com.cinker.constant.Constant;
import com.cinker.http.CinkerHttpService;
import com.cinker.model.ActivityFilm;
import com.cinker.model.ActivityPersonal;
import com.cinker.model.Cinema;
import com.cinker.model.FilmContentImage;
import com.cinker.model.FilmInfo;
import com.cinker.model.FilmOrder;
import com.cinker.model.FilmOrders;
import com.cinker.model.OrderTicket;
import com.cinker.model.Page;
import com.cinker.model.Payment;
import com.cinker.model.Screen;
import com.cinker.model.UserMember;
import com.cinker.model.UserVipMember;
import com.cinker.service.ActivityService;
import com.cinker.service.CinemaService;
import com.cinker.service.FilmService;
import com.cinker.util.ExportExcelUtils;
import com.cinker.util.UploadFileUtil;
import com.cinker.wechat.WxPayUtil;




@Controller
@RequestMapping("/cinkerMaintain")
public class CinemaAction {
	private final String ORDER_PAY = "1";
	private final String ORDER_NO_PAY = "0";
	private int pageSize = 20;
	@Autowired
	CinemaService cinemaService;
	
	@Autowired
	ActivityService activityService;

	@Autowired
	FilmService filmService;
	
	@Value("${APPID}")
	private  String APPID;
	@Value("${MCH_ID}")
	private String MCH_ID;
	@Value("${API_KEY}")
	private String API_KEY;
	
	@RequestMapping(value = "/getCinemaList", method = { GET, POST })
	public String getCinema(Model model,String id){
		List<Cinema> cinemalist = cinemaService.getCinema();
		model.addAttribute("cinemalist", cinemalist);
		return "admin/cinemaList";
		
	}

	@RequestMapping(value = "/getScreenList", method = { GET, POST })
	public String getScreen(Model model,String id){
		List<Screen> screenlist = cinemaService.getScreen();
		model.addAttribute("screenlist", screenlist);
		return "admin/screenList";
		
	}
	
	@RequestMapping(value = "/getFilmOrder",method={GET , POST})
	public String getFilmOrders(Model model,Integer page,Integer pagee){
		List<FilmOrders> orderList = null;
		int total = cinemaService.getFilmOrdersTotal();
		int totalPage = total/pageSize;
		if(total%pageSize != 0){
			totalPage++;
		}
		Page pages = new Page();
		if(pagee != null){
			page = pagee;
			orderList = cinemaService.getFilmOrders(page);
		}else{
			orderList = cinemaService.getFilmOrders(page);
			pages.setPage(page);
		}
		pages.setTotal(total);
		pages.setTotalPage(totalPage);
		model.addAttribute("pages", pages);
		model.addAttribute("pagee", pagee);
		model.addAttribute("orderList", orderList);
		return "admin/orderList";
		
	}
	
	@RequestMapping(value = "/getFilmInfo",method={GET , POST})
	public String getFilmInfo(Model model){
		List<FilmInfo> filmInfo = cinemaService.getFilmInfo();
		model.addAttribute("filmInfo", filmInfo);
		return "filmInfo";
	}
	
	@RequestMapping(value = "/getPaymentInfo",method={GET , POST})
	public String getPaymentInfo(Model model) throws UnsupportedEncodingException{
		List<Payment> paymentList = cinemaService.getPaymentInfo();
		List<Payment> paymentList_ = new ArrayList<Payment>();
		for(Payment payment : paymentList){
			payment.setUserNickName(URLDecoder.decode(payment.getUserNickName(), "utf-8"));
			paymentList_.add(payment);
		}
		model.addAttribute("paymentList", paymentList_);
		return "paymentView";
	}
	
	
	@RequestMapping(value = "/getSearch", method={GET , POST})
	public String getSearch(Model model,HttpServletRequest request,String id,String chineseName,
			String englishName,String beginTime,String endTime,Integer page,Integer pagee,
			String submit1) throws UnsupportedEncodingException{
		List<FilmInfo> filmInfo = null;
		Page pages = new Page();
		int total = cinemaService.getSearch(id, chineseName, englishName, beginTime, endTime,0).size();
		if(StringUtils.isEmpty(submit1)){
			
			int totalPage = total/pageSize;
			if(total%pageSize != 0){
				totalPage++;
			}
			
			if(pagee != null){
				page = pagee;
				filmInfo = cinemaService.getSearch(id, chineseName, englishName, beginTime, endTime,page);	
				pages.setPage(page);
			}
			if(page != null){
				filmInfo = cinemaService.getSearch(id, chineseName, englishName, beginTime, endTime,page);
				pages.setPage(page);
			}
			pages.setTotal(total);
			pages.setTotalPage(totalPage);
			pages.setPageSize(pageSize);
		}
		if(!StringUtils.isEmpty(submit1)){
			submit1 = request.getParameter("submit1");
			submit1 = new String(submit1.getBytes("iso8859-1"),"UTF-8");
			int totalPage = total/pageSize;
			if(total%pageSize != 0){
				totalPage++;
			}
			if(pagee != null){
				page = pagee;
				filmInfo = cinemaService.getSearch(id, chineseName, englishName, beginTime, endTime,page);
				pages.setPage(page);
			}else{
				pagee = 1;
			}
			if(page != null){
				filmInfo = cinemaService.getSearch(id, chineseName, englishName, beginTime, endTime,page);
				pages.setPage(page);
			}else{
				page = 1;
			}
			pages.setPage(page);
			pages.setTotal(total);
			pages.setTotalPage(totalPage);
			pages.setPageSize(pageSize);
			model.addAttribute("submit1", submit1);
			model.addAttribute("id", id);
			model.addAttribute("chineseName",chineseName );
			model.addAttribute("englishName", englishName);
			model.addAttribute("beginTime", beginTime);
			model.addAttribute("endTime", endTime);
		}
		model.addAttribute("pages", pages);
		model.addAttribute("pagee", pagee);
		model.addAttribute("filmInfo", filmInfo);
		return "admin/filmInfoList";
	}
	
	@RequestMapping(value = "/getSearchPayment", method={GET , POST})
	public String getSearchPayment(Model model,HttpServletResponse response,HttpServletRequest request,
			String userNickName,String orderNumber, String paymentID, String beginTime,String endTime,String submit1,
			String submit2,Integer page,Integer pagee) throws UnsupportedEncodingException{
		List<Payment> paymentList = null;
		Page pages = new Page();
		if(StringUtils.isEmpty(submit1) && StringUtils.isEmpty(submit2)){
			int total = cinemaService.findPaymentCount(userNickName, orderNumber, paymentID, beginTime, endTime, null);
			int totalPage = total/pageSize;
			if(total%pageSize != 0){
				totalPage++;
			}
			
			if(pagee != null){
				page = pagee;
				paymentList = cinemaService.getSearchPayment(userNickName, orderNumber, paymentID, beginTime, endTime, page);	
				for(Payment payment:paymentList){
					payment.setUserNickName(URLDecoder.decode(payment.getUserNickName(),"utf-8"));
				}
				pages.setPage(page);
			}
			if(page != null){
				paymentList = cinemaService.getSearchPayment(userNickName, orderNumber, paymentID, beginTime, endTime, page);
				for(Payment payment:paymentList){
					payment.setUserNickName(URLDecoder.decode(payment.getUserNickName(),"utf-8"));
				}
				pages.setPage(page);
			}
			pages.setTotal(total);
			pages.setTotalPage(totalPage);
			pages.setPageSize(pageSize);
		}
		if(!StringUtils.isEmpty(submit1)){
			int total = cinemaService.findPaymentCount(userNickName, orderNumber, paymentID, beginTime, endTime, null);
			int totalPage = total/pageSize;
			if(total%pageSize != 0){
				totalPage++;
			}
			if(pagee != null){
				page = pagee;
				paymentList = cinemaService.getSearchPayment(userNickName, orderNumber, paymentID, beginTime, endTime, page);	
				for(Payment payment:paymentList){
					payment.setUserNickName(URLDecoder.decode(payment.getUserNickName(),"utf-8"));
				}
				pages.setPage(page);
			}else{
				pagee = 1;
			}
			if(page != null){
				paymentList = cinemaService.getSearchPayment(userNickName, orderNumber, paymentID, beginTime, endTime, page);
				for(Payment payment:paymentList){
					payment.setUserNickName(URLDecoder.decode(payment.getUserNickName(),"utf-8"));
				}
				pages.setPage(page);
			}else{
				page = 1;
			}
			pages.setPage(page);
			pages.setTotal(total);
			pages.setTotalPage(totalPage);
			pages.setPageSize(pageSize);
			model.addAttribute("userNickName", URLDecoder.decode(userNickName,"utf-8"));
			model.addAttribute("orderNumber", orderNumber);
			model.addAttribute("paymentID", paymentID);
			model.addAttribute("beginTime", beginTime);
			model.addAttribute("endTime", endTime);
			model.addAttribute("submit1", submit1);
			
		}
		if(!StringUtils.isEmpty(submit2)){
			try{
				paymentList = cinemaService.getSearchPayment(userNickName, orderNumber, paymentID, beginTime, endTime);
				String title = "支付一览表";
				String[] rowsName = new String[]{"ID","支付ID","支付类型","微信名称","支付状态","支付金额",
						"生成支付时间","完成支付时间","订单编号","流水单号"};
				List<Object[]> dateList = new ArrayList<Object[]>();
				Object[] objs = null;
				for(int i = 0 ; i<paymentList.size() ; i++){
					Payment payment = paymentList.get(i);
					objs = new Object[rowsName.length];  
					objs[0] = i;
					objs[1] = payment.getPaymentID();
					objs[2] = payment.getType();
					objs[3] = URLDecoder.decode(payment.getUserNickName(), "utf-8");
					objs[4] = payment.getStatus();
					objs[5] = payment.getPaymentPrice();
					objs[6] = payment.getStartTime();
					objs[7] = payment.getEndTime();
					objs[8] = payment.getOrderNumber();
					objs[9] = payment.getTransactionId();
					dateList.add(objs);
				}
				ExportExcelUtils ex =new ExportExcelUtils(title, rowsName, dateList,response);  
				ex.exportData();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		model.addAttribute("pages", pages);
		model.addAttribute("pagee", pagee);
		model.addAttribute("paymentList", paymentList);
		return "admin/paymentList";
	}
	
	@RequestMapping(value = "/getSearchFilmOrders", method={GET , POST})
	public String getSearchFilmOrders(HttpServletResponse response,HttpServletRequest request,Model model,String orderNumber,String filmTitle, String beginTime,String endTime,String vistaTransNumber,
			String beginShowTime,String endShowTime,String status,String submit1,String submit2,Integer page,Integer pagee,String cinemaId) throws UnsupportedEncodingException{
		List<FilmOrders> orderList = null;
		/* 修改提交方式为POST，防止tomcat版本不同，造成默认编码格式不同出现的乱码问题
		if(!StringUtils.isEmpty(submit2)){
			submit2 = request.getParameter("submit2");
			submit2 = new String(submit2.getBytes("iso8859-1"),"UTF-8");
		}
		if(!StringUtils.isEmpty(filmTitle)){
			filmTitle = request.getParameter("filmTitle");
			filmTitle = new String(filmTitle.getBytes("iso8859-1"),"UTF-8");
		}
		*/
		List<Cinema> cinemas = filmService.getCinemas();
		model.addAttribute("cinemas", cinemas);
		model.addAttribute("inCinemaId", cinemaId);
		String orderStatus;
		if(status!=null)
			orderStatus = status;
		else
			orderStatus = ORDER_PAY;
		int total = cinemaService.findOrderCount(orderNumber, filmTitle, beginTime, endTime, vistaTransNumber, beginShowTime, endShowTime, orderStatus,0,submit1,submit2,cinemaId);
		int totalPage = total/pageSize;
		if(total%pageSize != 0){
			totalPage++;
		}
		Page pages = new Page();
		if(pagee != null){
			page = pagee;
			orderList = cinemaService.getSearch(orderNumber, filmTitle, beginTime, endTime, vistaTransNumber, beginShowTime, endShowTime, orderStatus,page,submit1,submit2,cinemaId);			
			orderList = modifyOrderInfo(orderList);
			pages.setPage(page);
		}
		if(page != null){
			orderList = cinemaService.getSearch(orderNumber, filmTitle, beginTime, endTime, vistaTransNumber, beginShowTime, endShowTime, orderStatus,page,submit1,submit2,cinemaId);
			orderList = modifyOrderInfo(orderList);
			pages.setPage(page);
		}
		pages.setTotal(total);
		pages.setTotalPage(totalPage);
		pages.setPageSize(pageSize);
		if(!StringUtils.isEmpty(submit2)){
			if(StringUtils.isEmpty(submit1)){
				total = cinemaService.findOrderCount(orderNumber, filmTitle, beginTime, endTime, vistaTransNumber, beginShowTime, endShowTime,orderStatus,cinemaId);
				orderList = modifyOrderInfo(orderList);
				totalPage = total/pageSize;
				if(total%pageSize != 0){
					totalPage++;
				}
				if(pagee != null){
					page = pagee;
					orderList = cinemaService.getSearch(orderNumber, filmTitle, beginTime, endTime, vistaTransNumber, beginShowTime, endShowTime, orderStatus,page,submit1,submit2,cinemaId);			
					orderList = modifyOrderInfo(orderList);
					pages.setPage(page);
				}else{
					pagee = 1;
				}
				if(page != null){
					orderList = cinemaService.getSearch(orderNumber, filmTitle, beginTime, endTime, vistaTransNumber, beginShowTime, endShowTime, orderStatus,page,submit1,submit2,cinemaId);
					orderList = modifyOrderInfo(orderList);
					pages.setPage(page);
				}else{
					page = 1;
				}
				pages.setTotal(total);
				pages.setTotalPage(totalPage);
				model.addAttribute("orderNumber", orderNumber);
				model.addAttribute("filmTitle", filmTitle);
				model.addAttribute("beginTime", beginTime);
				model.addAttribute("endTime", endTime);
				model.addAttribute("submit2", submit2);
				model.addAttribute("vistaTransNumber", vistaTransNumber);
				model.addAttribute("beginShowTime", beginShowTime);
				model.addAttribute("endShowTime", endShowTime);
				model.addAttribute("orderStatus", orderStatus);
			}
		}
		if(!StringUtils.isEmpty(submit1)){
			try{
				orderList = cinemaService.getSearch(orderNumber, filmTitle, beginTime, endTime, vistaTransNumber, beginShowTime, endShowTime,orderStatus,cinemaId);
				String title = "订单表";
				String[] rowsName = new String[]{"ID","订单编号","影院名称","影片名称","场次名称","微信名称","姓名","电话","开始时间",
						"订单数量","订单购买总价","订单生成时间","订单完成时间","座位","会员等级"};
				List<Object[]>  dataList = new ArrayList<Object[]>();  
				Object[] objs = null;  
				for (int i = 0; i < orderList.size(); i++) {
					FilmOrders ord = orderList.get(i);
					objs = new Object[rowsName.length];  
					objs[0] = i; 
					objs[1] = ord.getOrderNumber();
					objs[2] = ord.getCinemaName();
					objs[3] = ord.getFilmTitle();
					objs[4] = ord.getSessionName();
					objs[5] = java.net.URLDecoder.decode(ord.getUserNickName(), "UTF-8");
					objs[6] = ord.getUserName();
					objs[7] = ord.getMobilePhone();
					objs[8] = ord.getShowTime();
					objs[9] = ord.getTotalOrderCount();
					objs[10] = ord.getTotalValueCents();
					objs[11] = ord.getStartTime();
					objs[12] = ord.getEndTime();
					objs[13] = ord.getSeats();
					objs[14] = ord.getMemberLevelId();
					dataList.add(objs);				
				}						
				ExportExcelUtils ex =new ExportExcelUtils(title, rowsName, dataList,response);  
				ex.exportData();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		model.addAttribute("orderList", orderList);
		model.addAttribute("pages", pages);
		model.addAttribute("pagee", pagee);
		return "admin/orderList";
	}
	/**
	 * Descrption paySuccess but not get ticket and compute unit price
	 * @param FilmOrders
	 * @throws UnsupportedEncodingException 
	 */
	private List<FilmOrders> modifyOrderInfo(List<FilmOrders> FilmOrders) throws UnsupportedEncodingException {
		List<FilmOrders> orderList = FilmOrders;
		for(FilmOrders filmOrder:FilmOrders){
			filmOrder.setUserNickName(URLDecoder.decode(filmOrder.getUserNickName(),"utf-8"));
			//20180110 find paySuccess but not get ticket
			if (filmOrder.getPaymentID() != 0) {
				Payment payment = cinemaService.findPayment(String.valueOf(filmOrder.getPaymentID()));
				if (payment != null) {
					int paymentStatus = payment.getStatus();
					if(paymentStatus == 1 && filmOrder.getStatus() == 0){
						filmOrder.setStatus(8);
					}
				}
			}
			//compute unit price
			Double totalValueCents = Double.valueOf(filmOrder.getTotalValueCents());
			Double totalOrderCount = (double) filmOrder.getTotalOrderCount();
			Double unitPrice = 0d;
			if(totalOrderCount != 0 && totalOrderCount != null)
				unitPrice = totalValueCents/totalOrderCount;
			filmOrder.setUnitPrice(String.format("%.2f", unitPrice));
			//change date format
			if(filmOrder.getEndTime() != null) {
				if(filmOrder.getEndTime().trim().length()>19)
					filmOrder.setEndTime(filmOrder.getEndTime().substring(0, 19));	
			}
		}
		return orderList;
	}

	@RequestMapping(value = "/editCinema/{id}",method={GET , POST})
	public String editCinema(Model model,@PathVariable("id")String id){		
		if(id.equals("0"))return "admin/editCinema";
		Cinema cinema = cinemaService.editCinema(id);
		model.addAttribute("cinema", cinema);
		return "admin/editCinema";
		
	}
	
	@RequestMapping(value = "/saveCinema",method={GET , POST})	  
	public @ResponseBody String saveCinema(@RequestBody Cinema cinema){
		String result = "";
		if(cinema != null){
			cinemaService.saveCinema(cinema);
			result = "success";
		}		
		return result;		
	}
	
	@RequestMapping(value = "/deleteCinema/{id}",method={GET , POST})
	public String deleteCinema(Model model,@PathVariable("id")int id){
		cinemaService.deleteCinema(id);
		return "redirect:/cinkerMaintain/getCinemaList";
		
	}

	@RequestMapping(value = "/editScreen/{id}",method={GET , POST})
	public String editScreen(Model model,@PathVariable("id")String id){
		if(id.equals("0"))return "admin/editScreen";
		Screen screen = cinemaService.editScreen(id);
		model.addAttribute("screen", screen);
		return "admin/editScreen";
		
	}
	
	@RequestMapping(value = "/saveScreen",method={GET , POST})	  
	public @ResponseBody String saveScreen(@RequestBody Screen screen){
		String result = "";
		if(screen != null){
			cinemaService.saveScreen(screen);
			result = "success";
		}		
		return result;		
	}
	
	@RequestMapping(value = "/deleteScreen/{id}",method={GET , POST})
	public String deleteScreen(Model model,@PathVariable("id")int id){
		cinemaService.deleteScreen(id);
		return "redirect:/cinkerMaintain/getScreenList";
		
	}
	
	@RequestMapping(value = "/editFilmInfo/{id}",method={GET , POST})
	public String editFilmInfo(Model model,@PathVariable("id")String id){
		if(id.equals("0"))return "admin/editFilmInfo";
		FilmInfo filmInfo = cinemaService.editFilmInfo(id);		
		List<FilmContentImage> filmContentImages = null;
		FilmContentImage filmContentImage = new FilmContentImage();
		if(filmInfo != null){
			if(!StringUtils.isEmpty(filmInfo.getSurfaceImage()))
			filmInfo.setImageUrl(filmInfo.getSurfaceImage().substring(filmInfo.getSurfaceImage().indexOf("&")+1, filmInfo.getSurfaceImage().length()));
			String filmId = filmInfo.getFilmId();
			filmContentImages = cinemaService.getFilmContentImage(filmId);			
			StringBuffer sb = new StringBuffer();
			if(filmContentImages.size() > 0){
				for(int i = 0; i < filmContentImages.size(); i++){
					filmContentImage = filmContentImages.get(i);
					String fileName = filmContentImage.getImageUrl();
					sb.append(fileName.substring(fileName.indexOf("&")+1, fileName.length()));
					if(i < filmContentImages.size() - 1){
						sb.append(";");
					}
				}
			}
			filmContentImage.setImageUrl(sb.toString());
		}
		model.addAttribute("filmContentImage", filmContentImage);
		model.addAttribute("filmInfo", filmInfo);
		return "admin/editFilmInfo";
		
	}
		
	@RequestMapping(value = "/deleteFilmInfo/{id}",method={GET , POST})
	public String deleteFilmInfo(Model model,@PathVariable("id")String id){
		FilmInfo filmInfo = cinemaService.editFilmInfo(id);
		if(filmInfo != null){
			String filmId = filmInfo.getFilmId();
			cinemaService.deleteFilmContentImage(filmId);
		}
		cinemaService.deleteFilmInfo(id);
		return "redirect:/cinkerMaintain/getSearch?page=1";
		
	}
	
	@RequestMapping(value = "/editFilmOrders/{id}",method={GET , POST})
	public String editFilmOrders(Model model,@PathVariable("id")String id){
		if(id.equals("0"))return "editOrderInfo";
		FilmOrders filmOrders = cinemaService.editFilmOrders(id);
		model.addAttribute("filmOrders", filmOrders);
		return "admin/editOrderInfo";
		
	}
	
	@RequestMapping(value = "/saveFilmOrders",method={GET , POST})	  
	public @ResponseBody String saveFilmOrders(@RequestBody FilmOrders filmOrders){
		String result = "";
		if(filmOrders != null){
			cinemaService.saveFilmOrders(filmOrders);
			result = "success";
		}		
		return result;		
	}
	
	@RequestMapping(value = "/deleteFilmOrders/{id}",method={GET , POST})
	public String deleteFilmOrders(Model model,@PathVariable("id")String id){
		cinemaService.deleteFilmOrders(id);
		return "redirect:/cinkerMaintain/getFilmOrder";
		
	}

	
	@RequestMapping(value = "/insertCinema",method={GET , POST})
	public @ResponseBody String insertCinema(Model model,@RequestBody Cinema cinema){
		cinemaService.insertCinema(cinema);
		return "success";
		
	}
	
	
	@RequestMapping(value = "/insertScreen",method={GET , POST})
	public @ResponseBody String insertScreen(Model model,@RequestBody Screen screen){
		cinemaService.insertScreen(screen);
		return "success";
		
	}
	
	@RequestMapping(value = "/insertFilmOrders",method={GET , POST})
	public @ResponseBody String insertFilmOrders(Model model,@RequestBody FilmOrders filmOrders){
		cinemaService.insertFilmOrders(filmOrders);
		return "success";
		
	}
	
	
	@RequestMapping(value = "/findFilmOrders/{id}",method={GET , POST})
	public String findFilmOrders(Model model,@PathVariable("id")String id){
		FilmOrders filmOrders = cinemaService.findFilmOrdersInfo(id);
		model.addAttribute("filmOrders", filmOrders);
		return "admin/filmOrdersInfo";
	}
	
	@RequestMapping(value = "/findUserMember/{userNumber}",method={GET , POST})
	public String findUserMember(Model model,@PathVariable("userNumber")String userNumber){
		UserMember userMember = cinemaService.findUserMember(userNumber);
		model.addAttribute("userMember", userMember);
		return "findUserMember";
	}
	
	@RequestMapping(value = "/findPaymentInformation/{paymentID}",method={GET , POST})
	public String findPaymentInformation(Model model,@PathVariable("paymentID")String paymentID) throws UnsupportedEncodingException{
		Payment payment = cinemaService.findPaymentInformation(paymentID);
		payment.setUserNickName(URLDecoder.decode(payment.getUserNickName(), "utf-8"));
		model.addAttribute("payment", payment);
		return "admin/orderPaymentInfo";
	}
	
		
	@RequestMapping(value = "/insertFilmInfo",method={GET , POST})
	  public String insertFilmInfo(HttpServletRequest request,HttpServletResponse response, Model model,@RequestParam(value = "graphTheories", required = false) MultipartFile[] graphTheories, @RequestParam(value = "surfaceImage", required = false)MultipartFile[] surfaceImage,
			  String filmId,String chineseName,String englishName,String director,String scriptWriter,String showTime,String runTime,String starringActor,String filmType,String language,String submit1,String cinecism) throws Exception {		
		
		if(!StringUtils.isEmpty(submit1)){			
			// INSERT
			FilmInfo filmInfo = new FilmInfo();
			boolean vaild = false;
			response.setContentType("text/html;charset=utf-8");
			String masess = "";
			int total = cinemaService.findFilmInfoByFilmId(filmId);
			if(total > 0){
				vaild = true;
				model.addAttribute("message", "影片编码已存在！");
			}else{
				masess = "保存成功";
			}
			FilmContentImage filmContentImage = new FilmContentImage();
			List<String> newFile = UploadFileUtil.uploadFileList(request,surfaceImage);
			List<String> newFileNames = UploadFileUtil.uploadFileList(request,graphTheories);
			StringBuffer sb = new StringBuffer();
			if(newFileNames.size() > 0){
				for(int i = 0; i < newFileNames.size(); i++){
					String newFileName = newFileNames.get(i);
					if(!vaild)cinemaService.saveImage(filmId, newFileName);
					sb.append(newFileName.substring(newFileName.indexOf("&")+1, newFileName.length()));
					if(i < newFileNames.size() - 1){
						sb.append(";");
					}
				}
			}
			if(newFile.size() > 0){
				String newFileName = newFile.get(0);
				filmInfo.setSurfaceImage(newFileName);
				filmInfo.setImageUrl(newFileName.substring(newFileName.indexOf("&")+1, newFileName.length()));
			}
			
			filmInfo.setFilmId(filmId);
			filmInfo.setChineseName(chineseName);
			filmInfo.setEnglishName(englishName);
			filmInfo.setDirector(director);
			filmInfo.setScriptWriter(scriptWriter);
			filmInfo.setShowTime(showTime);
			filmInfo.setRunTime(runTime);
			filmInfo.setStarringActor(starringActor);
			filmInfo.setFilmType(filmType);
			filmInfo.setLanguage(language);
			filmInfo.setCinecism(cinecism);
			filmContentImage.setFilmId(filmId);
			filmContentImage.setImageUrl(sb.toString());
			if(!vaild)cinemaService.insertFilmInfo(filmInfo);
			if (model.containsAttribute("message")) filmInfo.setFilmId(null);
			model.addAttribute("filmInfo", filmInfo);
			model.addAttribute("filmContentImage", filmContentImage);
			response.setContentType("text/html;charset=UTF-8");
			if(!StringUtils.isEmpty(masess)){
				PrintWriter out = response.getWriter();
				out.print("<script language=\"javascript\"> alert('"+ masess +"！');window.location.href='/cinker/cinkerMaintain/getSearch?page=1'</script>");	
				out.close();
			}
			return "admin/editFilmInfo";
		}else {
			// UPDATE
			FilmInfo filmInfo = new FilmInfo();
			List<FilmContentImage> filmContentImages = null;
			String surfaceImage_ = null;
			response.setContentType("text/html;charset=utf-8");
			String masess = "";
			int total = cinemaService.findFilmInfoByFilmId(filmId);
			if(total > 0){
				masess = "更新成功";
				filmInfo = cinemaService.findFilmInfoFilmId(filmId);
				filmContentImages = cinemaService.getFilmContentImage(filmId);
				surfaceImage_ = filmInfo.getSurfaceImage();
			}			
			FilmContentImage filmContentImage = new FilmContentImage();
			List<String> newFile = new  ArrayList<String>();
			List<String> newFileNames = new  ArrayList<String>();
			if(surfaceImage != null) {
				newFile = UploadFileUtil.uploadFileList(request,surfaceImage);
			}
			if (graphTheories != null){
				newFileNames = UploadFileUtil.uploadFileList(request,graphTheories);
			}
			StringBuffer sb = new StringBuffer();
			if(newFileNames.size() > 0){
				cinemaService.deleteFilmContentImage(filmId);
				for(int i = 0; i < newFileNames.size(); i++){
					String newFileName = newFileNames.get(i);
					cinemaService.saveImage(filmId, newFileName);
					sb.append(newFileName.substring(newFileName.indexOf("&")+1, newFileName.length()));
					if(i < newFileNames.size() - 1){
						sb.append(";");
					}
				}
			}else{
				if(filmContentImages != null && filmContentImages.size() > 0){
					for(int i = 0; i < filmContentImages.size(); i++){
						FilmContentImage filmContentImage_ = filmContentImages.get(i);
						sb.append(filmContentImage_.getImageUrl().substring(filmContentImage_.getImageUrl().indexOf("&")+1, filmContentImage_.getImageUrl().length()));
						if(i < newFileNames.size() - 1){
							sb.append(";");
						}
					}
						
				}
			}
			if(newFile.size() > 0){
				String newFileName = newFile.get(0);
				filmInfo.setSurfaceImage(newFileName);
				filmInfo.setImageUrl(newFileName.substring(newFileName.indexOf("&")+1, newFileName.length()));
			}else{
				if (surfaceImage_ != null ) {
					filmInfo.setImageUrl(surfaceImage_.substring(surfaceImage_.indexOf("&")+1, surfaceImage_.length()));
				}
			}
			filmInfo.setFilmId(filmId);
			filmInfo.setChineseName(chineseName);
			filmInfo.setEnglishName(englishName);
			filmInfo.setDirector(director);
			filmInfo.setScriptWriter(scriptWriter);
			filmInfo.setShowTime(showTime);
			filmInfo.setRunTime(runTime);
			filmInfo.setStarringActor(starringActor);
			filmInfo.setFilmType(filmType);
			filmInfo.setLanguage(language);
			filmInfo.setCinecism(cinecism);
			filmContentImage.setFilmId(filmId);
			filmContentImage.setImageUrl(sb.toString());
			cinemaService.saveFilmInfo(filmInfo);
			model.addAttribute("filmInfo", filmInfo);
			model.addAttribute("filmContentImage", filmContentImage);
			response.setContentType("text/html;charset=UTF-8");
			if(!StringUtils.isEmpty(masess)){
				PrintWriter out = response.getWriter();
				out.print("<script language=\"javascript\"> alert('"+ masess +"！');window.location.href='/cinker/cinkerMaintain/getSearch?page=1'</script>");	
				out.close();
			}
			return "admin/editFilmInfo";	
		}
		
	}

	
	
	@RequestMapping(value = "/admin", method = { GET, POST })
	public String getCinema(HttpServletRequest request, Model model){
		HttpSession session = request.getSession();
		session.setAttribute("username", null);
		return "admin/login";
		
	}
	
	@RequestMapping(value="/getActivityFilmInfo" , method={GET , POST})
	public String getActivityFilm(Model model,Integer page,Integer pagee,String filmId,String filmTitle,
			String sessionTime, String cinemaId, String submit1,HttpServletRequest request) throws UnsupportedEncodingException{
		/*	提交中文乱码问题  由get提交造成	
 		* if(!StringUtils.isEmpty(filmTitle)){
			filmTitle = request.getParameter("filmTitle");
			filmTitle = new String(filmTitle.getBytes("iso8859-1"),"UTF-8");
		}*/
		List<Cinema> cinemas = filmService.getCinemas();
		model.addAttribute("cinemas", cinemas);
		model.addAttribute("inCinemaId", cinemaId);
		model.addAttribute("sessionTime", sessionTime);
		List<ActivityFilm> activityFilm = null;
		Page pages = new Page();
		int total = cinemaService.findActivityFilmCount(filmId, filmTitle, sessionTime, cinemaId);
		if(StringUtils.isEmpty(submit1)){	
			int totalPage = total/pageSize;
			if(total%pageSize != 0){
				totalPage++;
			}
			
			if(pagee != null){
				page = pagee;
				activityFilm = cinemaService.getSearchActivityFilm(filmId, filmTitle, sessionTime, cinemaId, page)	;
				if(activityFilm.size()>0){
					for(int i =0; i<activityFilm.size();i++){
						Integer activityFilmLists = cinemaService.findActicityFilmInfoByActivityId(activityFilm.get(i).getSessionId());
						if(activityFilmLists == null){
							activityFilmLists = 0;
						}
						activityFilm.get(i).setActivityTickets(activityFilm.get(i).getTotal()-activityFilmLists);
					}
				}
				pages.setPage(page);
			}
			if(page != null){
				activityFilm = cinemaService.getSearchActivityFilm(filmId, filmTitle, sessionTime, cinemaId, page);
				if(activityFilm.size()>0){
					for(int i =0; i<activityFilm.size();i++){
						Integer activityFilmLists = cinemaService.findActicityFilmInfoByActivityId(activityFilm.get(i).getSessionId());
						if(activityFilmLists == null){
							activityFilmLists = 0;
						}
						activityFilm.get(i).setActivityTickets(activityFilm.get(i).getTotal()-activityFilmLists);
					}
				}
				pages.setPage(page);
			}
			pages.setTotal(total);
			pages.setTotalPage(totalPage);
			pages.setPageSize(pageSize);
		}
		else {
			int totalPage = total/pageSize;
			if(total%pageSize != 0){
				totalPage++;
			}
			if(pagee != null){
				page = pagee;
				activityFilm = cinemaService.getSearchActivityFilm(filmId, filmTitle, sessionTime, cinemaId, page);
				if(activityFilm.size()>0){
					for(int i =0; i<activityFilm.size();i++){
						Integer activityFilmLists = cinemaService.findActicityFilmInfoByActivityId(activityFilm.get(i).getSessionId());
						if(activityFilmLists == null){
							activityFilmLists = 0;
						}
						activityFilm.get(i).setActivityTickets(activityFilm.get(i).getTotal()-activityFilmLists);
					}
				}
				pages.setPage(page);
			}else{
				pagee = 1;
			}
			if(page != null){
				activityFilm = cinemaService.getSearchActivityFilm(filmId, filmTitle, sessionTime, cinemaId, pagee);
				if(activityFilm.size()>0){
					for(int i =0; i<activityFilm.size();i++){
						Integer activityFilmLists = cinemaService.findActicityFilmInfoByActivityId(activityFilm.get(i).getSessionId());
						if(activityFilmLists == null){
							activityFilmLists = 0;
						}
						activityFilm.get(i).setActivityTickets(activityFilm.get(i).getTotal()-activityFilmLists);
					}
				}
				pages.setPage(page);
			}else{
				page = 1;
			}
			pages.setPage(page);
			pages.setTotal(total);
			pages.setTotalPage(totalPage);
			pages.setPageSize(pageSize);
			model.addAttribute("submit1", submit1);
			model.addAttribute("filmId", filmId);
			model.addAttribute("filmTitle",filmTitle );
		}
		model.addAttribute("pages", pages);
		model.addAttribute("pagee", pagee);
		
		model.addAttribute("activityFilm", activityFilm);
		return "admin/activity_list";
	}
	
	@RequestMapping(value="/insertActivityFilm" , method={GET , POST})
	public @ResponseBody String insertActivityFilm(Model model,@RequestBody ActivityFilm activityFilm){
		cinemaService.insertActivityFilm(activityFilm);
		return "success";
	}
	
	@RequestMapping(value="/updateActivityFilm" , method={GET , POST})
	public @ResponseBody String updateActivityFilm(Model model,@RequestBody ActivityFilm activityFilm){
		String result = "";
		if(activityFilm != null){
			cinemaService.updateActivityFilm(activityFilm);
			result = "success";
		}
		return result;
	}
	
	@RequestMapping(value="/deleteActivityFilm/{id}" , method={GET , POST})
	public String deleteActivityFilm(Model model,@PathVariable int id){
		cinemaService.deleteActivityFilm(id);
		return "redirect:/cinkerMaintain/getActivityFilmInfo";
	}
	
	@RequestMapping(value = "/editActivityFilm/{id}",method={GET , POST})
	public String editActivityFilm(Model model,@PathVariable("id")String id){		
		if(id.equals("0"))return "admin/editActivityFilm";
		ActivityFilm activityFilm = cinemaService.editActivityFilm(id);
		model.addAttribute("activityFilm", activityFilm);
		
		return "admin/editActivityFilm";
		
	}
	
	@RequestMapping(value="/getActivityPersonal" , method={GET , POST})
	public String getActivityPersonal(HttpServletRequest request,Model model,Integer page,String activityId,Integer pagee,
			String sessionTime,String name,String phone,String filmTitle,String cinemaId,
			String submit1,String submit2,HttpServletResponse response) throws UnsupportedEncodingException{
		List<ActivityPersonal> apFilmList = null;
		List<Cinema> cinemas = filmService.getCinemas();
		model.addAttribute("cinemas", cinemas);
		model.addAttribute("inCinemaId", cinemaId);
		model.addAttribute("sessionTime", sessionTime);
		model.addAttribute("name", name);
		model.addAttribute("phone", phone);
		model.addAttribute("filmTitle", filmTitle);
		int total = activityService.getActivityPersonalCount(activityId, sessionTime, name, phone, filmTitle, cinemaId);
		int totalPage = total/pageSize;
		if(total%pageSize != 0){
			totalPage++;
		}
		Page pages = new Page();
		if(StringUtils.isEmpty(submit1) && StringUtils.isEmpty(submit2)){
			if(pagee != null){
				page = pagee;
			}
			apFilmList = activityService.getActivityPersonal(activityId, sessionTime, name, phone, filmTitle, cinemaId, page);
			for(ActivityPersonal ap:apFilmList){
				ap.setNickeName(URLDecoder.decode(ap.getNickeName(), "utf-8"));
			}
			pages.setPage(page);
			pages.setTotal(total);
			pages.setTotalPage(totalPage);
		}
		if(!StringUtils.isEmpty(submit2)){
			if(page == null){
				page = 1;
			}
			if(pagee != null){
				page = pagee;
				apFilmList = activityService.getActivityPersonal(activityId, sessionTime, name, phone, filmTitle, cinemaId, page);
				for(ActivityPersonal ap:apFilmList){
					ap.setNickeName(URLDecoder.decode(ap.getNickeName(), "utf-8"));
				}
				pages.setPage(page);
			}else{
				pagee = 1;
			}
			if(page != null){
				apFilmList = activityService.getActivityPersonal(activityId, sessionTime, name, phone, filmTitle, cinemaId, page);
				for(ActivityPersonal ap:apFilmList){
					ap.setNickeName(URLDecoder.decode(ap.getNickeName(), "utf-8"));
				}
				pages.setPage(page);
			}
			pages.setTotal(total);
			pages.setTotalPage(totalPage);
			model.addAttribute("submit2", submit2);
			model.addAttribute("activityId", activityId);
		}
		if(!StringUtils.isEmpty(submit1)){
			try{
				activityService.getActivityPersonal(activityId, sessionTime, name, phone, filmTitle, cinemaId, page);
				String title = "活动个人详情一览表";
				String[] rowsName = new String[]{"ID","姓名","电话","影片名称","影片场次时间","订单完成时间","购票数"};
				List<Object[]>  dataList = new ArrayList<Object[]>();  
				Object[] objs = null;  
				for (int i = 0; i < apFilmList.size(); i++) {
					ActivityPersonal ord = apFilmList.get(i);
					objs = new Object[rowsName.length];  
					objs[0] = i; 
					objs[1] = ord.getName();
					objs[2] = ord.getPhone();
					objs[3] = ord.getFilmTitle();
					objs[4] = ord.getSessionTime();
					objs[5] = ord.getDateTime();
					objs[6] = ord.getQuaty();
					dataList.add(objs);				
				}						
				ExportExcelUtils ex =new ExportExcelUtils(title, rowsName, dataList,response);  
				ex.exportData();
				return null;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		model.addAttribute("apFilmList", apFilmList);
		model.addAttribute("pages", pages);
		model.addAttribute("pagee", pagee);
		return "admin/activityPersonalList";
	}

	@RequestMapping(value="/getSearchUserVipMember" , method={GET , POST})
	public String getSearchUserVipMember(HttpServletRequest request,HttpServletResponse response,
			String phone,String areaNumber,String submit1,String submit2,Model model,Integer page,Integer pagee) throws UnsupportedEncodingException{
		List<UserVipMember> userVipMemberList = null;
		int total = cinemaService.getSearchUserVipMember("", "", 0, submit1, submit2).size();
		int totalPage = total/pageSize;
		if(total%pageSize != 0){
			totalPage++;
		}
		Page pages = new Page();
		if(StringUtils.isEmpty(submit1) && StringUtils.isEmpty(submit2)){
			if(pagee != null){
				page = pagee;
				userVipMemberList = cinemaService.getSearchUserVipMember("", "", page, submit1, submit2);
				for(UserVipMember userVipMember:userVipMemberList){
					userVipMember.setUserNickName(URLDecoder.decode(userVipMember.getUserNickName(), "utf-8"));
				}
				pages.setPage(page);
			}
			if(page != null){
				userVipMemberList = cinemaService.getSearchUserVipMember("", "", page, submit1, submit2);
				for(UserVipMember userVipMember:userVipMemberList){
					userVipMember.setUserNickName(URLDecoder.decode(userVipMember.getUserNickName(), "utf-8"));
				}
				pages.setPage(page);
			}
			pages.setTotal(total);
			pages.setTotalPage(totalPage);
		}
		if(!StringUtils.isEmpty(submit2)){
			submit2 = request.getParameter("submit2");
			submit2 = new String(submit2.getBytes("iso8859-1"),"UTF-8");
		}
		if(!StringUtils.isEmpty(submit2)){
			total = cinemaService.getSearchUserVipMember(phone, areaNumber, 0, submit1, submit2).size();
			totalPage = total/pageSize;
			if(total%pageSize != 0){
				totalPage++;
			}
			if(page == null){
				page = 1;
			}
			if(pagee != null){
				page = pagee;
				userVipMemberList = cinemaService.getSearchUserVipMember(phone, areaNumber, page, submit1, submit2);
				for(UserVipMember userVipMember:userVipMemberList){
					userVipMember.setUserNickName(URLDecoder.decode(userVipMember.getUserNickName(), "utf-8"));
				}
				pages.setPage(page);
			}else{
				pagee = 1;
			}
			if(page != null){
				userVipMemberList = cinemaService.getSearchUserVipMember(phone, areaNumber, page, submit1, submit2);
				for(UserVipMember userVipMember:userVipMemberList){
					userVipMember.setUserNickName(URLDecoder.decode(userVipMember.getUserNickName(), "utf-8"));
				}
				pages.setPage(page);
			}
			pages.setTotal(total);
			pages.setTotalPage(totalPage);
			model.addAttribute("submit2", submit2);
			model.addAttribute("phone", phone);
			model.addAttribute("areaNumber", areaNumber);
		}
		if(!StringUtils.isEmpty(submit1)){
			try{
				userVipMemberList = cinemaService.getSearchUserVipMember(phone, areaNumber, page, submit1, submit2);
				String title = "会员管理表";
				String[] rowsName = new String[]{"ID","会员编号","微信名称","手机号","会员名","邮箱","卡号","性别"};
				List<Object[]>  dataList = new ArrayList<Object[]>();  
				Object[] objs = null;  
				for (int i = 0; i < userVipMemberList.size(); i++) {
					UserVipMember userMember = userVipMemberList.get(i);
					objs = new Object[rowsName.length];  
					objs[0] = i; 
					objs[1] = userMember.getUserNumber();
					objs[2] = URLDecoder.decode(userMember.getUserNickName(), "utf-8");
					objs[3] = userMember.getMobilePhone();
					objs[4] = userMember.getUserName();
					objs[5] = userMember.getEmail();
					objs[6] = userMember.getVistaMemberCardNumber();
					objs[7] = userMember.getUserSex();
					dataList.add(objs);				
				}						
				ExportExcelUtils ex =new ExportExcelUtils(title, rowsName, dataList,response);  
				ex.exportData();
				return null;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		model.addAttribute("userVipMemberList", userVipMemberList);
		model.addAttribute("pages", pages);
		model.addAttribute("pagee", pagee);
		return "admin/userVipMemberList";
	}
	
	// TODO 退票功能
	@RequestMapping(value="/cancleFilmOrder/{id}" , method={GET , POST})
	public String cancleFilmOrder(Model model,@PathVariable("id")String id){
		FilmOrders filmOrders = cinemaService.findFilmOrdersInfo(id);
		/*String vistaMemberCardNumber = "9999 9999 9999 9999";
		
		if (!StringUtils.isEmpty(filmOrders.getUserNumber())) {
			UserMember userMember = cinemaService.findUserMember(filmOrders.getUserNumber());
			if (userMember != null) {
				vistaMemberCardNumber = userMember.getVistaMemberCardNumber();
			}
		}
		
		 String cinemaType = filmService.getCinema(filmOrders.getCinemaId()).getType();
		 String connectapiurl;
		 String connectapitoken;

		 if ("Pictures".equals(cinemaType)){
		 	connectapiurl = Constant.CONNECT_API_URL;
		 	connectapitoken = Constant.CONNECT_API_TOKEN;
		 } else {
		 	connectapiurl = Constant.CONNECT_API_URL_C;
		 	connectapitoken = Constant.CONNECT_API_TOKEN_C;
		 }
		 //调用Vista的完成订单接口
		 JSONObject jsonParam = new JSONObject();
		 jsonParam.put("CinemaId",filmOrders.getCinemaId());
		 JSONObject paymentJsonParam = new JSONObject();
		 //PaymentInfo
		 paymentJsonParam.put("CardNumber",vistaMemberCardNumber);
		 
		 if ("9999 9999 9999 9999".equals(vistaMemberCardNumber)){
			 paymentJsonParam.put("CardType","GZH");
			 //订单支付金额
			 paymentJsonParam.put("PaymentValueCents",(int)(Double.valueOf(filmOrders.getTotalValueCents())*100));
			 paymentJsonParam.put("PaymentTenderCategory","GZH");
		 }else {
			 paymentJsonParam.put("CardType","LOYAL");
			 //订单支付金额
			 paymentJsonParam.put("PaymentValueCents",(int)(Double.valueOf(filmOrders.getTotalValueCents())*100));
			 paymentJsonParam.put("PaymentTenderCategory","LOYAL");
		 }
		 
		 jsonParam.put("PaymentInfo",paymentJsonParam);
		 jsonParam.put("BookingNumber",filmOrders.getBookingID());
		 jsonParam.put("RefundReason","backend");
		 jsonParam.put("RefundAmount",0);
		 jsonParam.put("RefundBookingFee",true);
		 jsonParam.put("MarkAsRefundedOnly",true);
		 jsonParam.put("OptionalClientId",Constant.OPTIONAL_CLIENT_ID);
		 
		 String result = CinkerHttpService.httpPostRequest(connectapiurl+"/WSVistaWebClient/RestBooking.svc/booking/refund",jsonParam.toString(),connectapitoken);

		 if (!StringUtils.isEmpty(result)) {
			 JSONObject resultJson = JSONObject.fromObject(result);
			 if(resultJson.getInt("ResultCode") == 0){
				 //更改本地数据库的订单信息
				 filmService.cancleOrder(9,id);
			 }
		 }*/
		return "redirect:/cinkerMaintain/getSearchFilmOrders?page=1";
	}

	//下载对帐单
	@RequestMapping(value = "/getStatements", method = { GET, POST })
	public String getStatements(Model model,String fromDate,HttpServletResponse response){
		String status = "0";
		if (!StringUtils.isEmpty(fromDate)){
			String billtype = "ALL";
			String queryStatementResult = "";
			try {
				queryStatementResult = WxPayUtil.downloadbill(APPID,MCH_ID,API_KEY,fromDate,billtype);			
				//查询错误
				if(queryStatementResult.startsWith("<xml>")){
		            status = "1";
		        } else {
		        	String listStr = "";
		            String objStr = "";
		            if(queryStatementResult.indexOf("总交易单数")>=0){
		            	listStr =  queryStatementResult.substring(0, queryStatementResult.indexOf("总交易单数"));
		            	objStr =  queryStatementResult.substring(queryStatementResult.indexOf("总交易单数"));
		            } else {
		            	listStr = queryStatementResult;
		            }

					String title = fromDate + "对帐单";
					List<Object[]>  dataList = new ArrayList<Object[]>();  
					Object[] objs = null;
					
					String[] tempStr = listStr.split("`"); // 数据分组
		            //将ID加入标题
		            String[] rowsName = ("ID," + tempStr[0]).split(",");// 分组标题
		            //一条数据的项目数
		            int columnCount = rowsName.length - 1;
		            int k = 1; // 纪录数组下标
		            int j = tempStr.length / (columnCount); // 计算件数
		            for (int i = 0; i < j; i++) {
						objs = new Object[rowsName.length];
						objs[0] = i;
						for (int l = 0; l < columnCount; l++) {
		                	objs[l + 1] = tempStr[l + k].replaceAll(",", "");
		                }
		                
						dataList.add(objs);
		                k = k + columnCount;
		            }
					
					ExportExcelUtils ex =new ExportExcelUtils(title, rowsName, dataList,response);  
					ex.exportData();
		        }
				model.addAttribute("status", status);
				return "admin/statements";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		model.addAttribute("status", status);
		return "admin/statements";

	}

}
