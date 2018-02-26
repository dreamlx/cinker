package com.cinker.wechatpay.util;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.util.StringUtils;

import com.cinker.constant.Constant;
/*
 * 微信支付的一些公用类
 * */
public class CommonUtil {
	// 获取本机的IP地址
	public String getLocalIp() {
		String ip = null;
		Enumeration allNetInterfaces;
		try {
			allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
				List<InterfaceAddress> InterfaceAddress = netInterface.getInterfaceAddresses();
				for (InterfaceAddress add : InterfaceAddress) {
					InetAddress Ip = add.getAddress();
					if (Ip != null && Ip instanceof Inet4Address) {
						ip = Ip.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return ip;
	}

	public String getSign(Map map, String key) throws Exception {
		String signTemp = "appid=" + map.get("appid") + "&body="
				+ map.get("body") + "&mch_id=" + map.get("mch_id")
				+ "&nonce_str=" + map.get("nonce_str") + "&notify_url="
				+ map.get("notify_url") + "&out_trade_no="
				+ map.get("out_trade_no") + "&product_id="
				+ map.get("product_id") + "&spbill_create_ip="
				+ map.get("spbill_create_ip") + "&total_fee="
				+ map.get("total_fee") + "&trade_type=" + map.get("trade_type")
				+ "&key=" + key;
		String sign = MD5.MD5Encode(signTemp).toUpperCase();// MD5微信接口中提供，直接调用即可
		return sign;
	}
	public String getQuerySign(Map map, String key) throws Exception {
		String signTemp = "appid=" + map.get("appid")+"&mch_id=" + map.get("mch_id")
				+ "&nonce_str=" + map.get("nonce_str") +"&out_trade_no="
				+ map.get("out_trade_no")+"&key=" + key;
		String sign = MD5.MD5Encode(signTemp).toUpperCase();// MD5微信接口中提供，直接调用即可
		return sign;
	}

	/*
	 * 获取一定长度的随机字符串
	 */
	public String getRandomStringByLength(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/*
	 * 将Map转换为XML
	 */
	public String map2xmlBody(Map<String, Object> vo, String rootElement) {
		Document doc = DocumentHelper.createDocument();
		Element body = DocumentHelper.createElement(rootElement);
		doc.add(body);
		__buildMap2xmlBody(body, vo);
		return doc.asXML();
	}

	private void __buildMap2xmlBody(Element body, Map<String, Object> vo) {
		if (vo != null) {
			Iterator<String> it = vo.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				if (!StringUtils.isEmpty(key)) {
					Object obj = vo.get(key);
					Element element = DocumentHelper.createElement(key);
					if (obj != null) {
						if (obj instanceof java.lang.String) {
							element.setText((String) obj);
						} else {
							if (obj instanceof java.lang.Character
									|| obj instanceof java.lang.Boolean
									|| obj instanceof java.lang.Number
									|| obj instanceof java.math.BigInteger
									|| obj instanceof java.math.BigDecimal) {
								Attribute attr = DocumentHelper.createAttribute(element, "type", obj.getClass().getCanonicalName());
								element.add(attr);
								element.setText(String.valueOf(obj));
							} else if (obj instanceof java.util.Map) {
								Attribute attr = DocumentHelper.createAttribute(element,"type",Map.class.getCanonicalName());
								element.add(attr);
								__buildMap2xmlBody(element,(Map<String, Object>) obj);
							} else {
							}
						}
					}
					body.add(element);
				}
			}
		}
	}

	/*
	 * 获取二维码URL地址
	 */
	public  String getCodeUrl(String xmlData) {
		String resXml = null;
		try {
			resXml =sendPost(Constant.WX_CREATE_ORDER_URL, xmlData);
			System.out.println(resXml);
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String code_url = "";
		Map<String, String> map;
		try {
			map = parseXml(resXml);
			Object returnCode = map.get("return_code");
			if ("SUCCESS".equals(returnCode)) {
				String resultCode = map.get("result_code");
				if ("SUCCESS".equals(resultCode)) {
					code_url = map.get("code_url");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return code_url;
	}
	private Map<String ,String> parseXml(String resXml){
		Map<String,String> resultMap=new HashMap<String, String>();
		Document document=null;
		try {
			document=DocumentHelper.parseText(resXml);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(document!=null){
			Element root=document.getRootElement();
			if(root!=null){
				String returnCode=root.element("return_code").getText();
				resultMap.put("return_code",returnCode);
				if("SUCCESS".equals(returnCode)){
					String resultCode=root.element("result_code").getText();
					resultMap.put("result_code",resultCode);
					if("SUCCESS".equals(resultCode)){
					String codeUrl=root.element("code_url").getText();
					resultMap.put("code_url",codeUrl);
					}
				}
			}
		}
		return resultMap;
	}
	//向接口发送请求
	@SuppressWarnings("deprecation")
	public String sendPost(String url, String postDataXML) throws IOException, KeyStoreException, UnrecoverableKeyException,NoSuchAlgorithmException, KeyManagementException {
		String result = null;
		HttpPost httpPost = new HttpPost(url);
		HttpClient client = new DefaultHttpClient();
		StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
		httpPost.addHeader("Content-Type", "text/xml");
		httpPost.setEntity(postEntity);
		try {
			HttpResponse response = client.execute(httpPost);
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity,"UTF-8");
		} catch (ConnectionPoolTimeoutException e) {
			e.printStackTrace();
		} catch (ConnectTimeoutException e) {
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpPost.abort();
		}
		System.out.println(result);
		return result;

	}
}
