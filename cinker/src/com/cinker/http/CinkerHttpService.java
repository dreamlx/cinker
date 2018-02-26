package com.cinker.http;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.cinker.constant.Constant;

/*
 * 
 * 访问vista系统接口的Http工具类，包括Get和Post方法两种
 * 
 * */
public class CinkerHttpService {
	private CinkerHttpService() {
	}

	private static CinkerHttpService instance;

	public static CinkerHttpService getInstance() {
		if (instance == null)
			return new CinkerHttpService();
		else
			return instance;
	}

	public  String httpGetRequest(String requestUrl,String connectapitoken) {
		String result = "";
		try {
			HttpGet request = new HttpGet(requestUrl);// 这里发送get请求
			request.addHeader("connectapitoken", connectapitoken);
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpResponse response = httpclient.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = EntityUtils.toString(response.getEntity(), "utf-8");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String httpPostRequest(String requestUrl,String jsonParam,String connectapitoken) {
		String result = "";
		HttpPost httpost = new HttpPost(requestUrl);
		httpost.addHeader("connectapitoken",connectapitoken);
		try {
			StringEntity entity = new StringEntity(jsonParam.toString(),"utf-8");//解决中文乱码问题    
			entity.setContentEncoding("UTF-8"); 
			entity.setContentType("application/json");
			httpost.setEntity(entity);
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpResponse response = httpClient.execute(httpost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				result = EntityUtils.toString(response.getEntity(), "utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	public String httpGetRequestForWechatLogin(String requestUrl) {
		String result = "";
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(requestUrl);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			result = EntityUtils.toString(httpEntity, "utf-8");
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public static void main(String[] args) {
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("UserSessionId","FTest201508060001");
//		jsonObject.put("CinemaId","0200");
//		jsonObject.put("SessionId","39407");
//		//-----------------------------------------------------------
//		JSONArray ticketTypes = new JSONArray();
//		JSONObject ticketType = new JSONObject();
//		ticketType.put("TicketTypeCode", "0056");
//		ticketType.put("Qty", 2);
//		ticketType.put("PriceInCents", 5000);
//		ticketType.put("BookingFeeOverride", null);
//		ticketTypes.add(ticketType);
//		//-----------------------------------------------------------		
//		jsonObject.put("TicketTypes", ticketTypes);
//		jsonObject.put("ReturnOrder",false);
//		jsonObject.put("BookingFeeOverride",null);
//		//-----------------------------------------------------------
//		JSONArray selectedSeats = new JSONArray();
//		JSONObject selectedSeat = new JSONObject();
//		selectedSeat.put("AreaCategoryCode", "0000000001");
//		selectedSeat.put("AreaNumber", 2);
//		selectedSeat.put("RowIndex", 0);
//		selectedSeat.put("ColumnIndex", 1);
//		selectedSeats.add(selectedSeat);
//		JSONObject selectedSeat2 = new JSONObject();
//		selectedSeat2.put("AreaCategoryCode", "0000000001");
//		selectedSeat2.put("AreaNumber", 2);
//		selectedSeat2.put("RowIndex", 0);
//		selectedSeat2.put("ColumnIndex", 1);
//		selectedSeats.add(selectedSeat2);
//		jsonObject.put("SelectedSeats", selectedSeats);
//		//-----------------------------------------------------------
//		jsonObject.put("OptionalClientClass","RSP");
//		jsonObject.put("OptionalClientId","120.0.219.1");
//		jsonObject.put("OptionalClientName","CINK");
//		
//		String requestUrl = "http://180.167.19.17/WSVistaWebClient/RESTTicketing.svc/order/tickets";
//		CinkerHttpService httpService  = new CinkerHttpService();
//		System.out.println(httpService.httpPostRequest(requestUrl, jsonObject.toString()));;
		CinkerHttpService cinkerHttpService=CinkerHttpService.getInstance(); 
		String result=cinkerHttpService.httpGetRequest("http://180.167.19.17/WSVistaWebClient/RESTData.svc/cinemas/0200/sessions/38898/seat-plan",Constant.CONNECT_API_TOKEN);
		System.out.println(result);
		
				
	}
}
