package com.cinker.wechat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import org.apache.log4j.Logger;
import org.jdom.JDOMException;
public class WxPayUtil {
	
	private static Logger logger = Logger.getLogger(WxPayUtil.class);
	@SuppressWarnings("unchecked")
	public static String unifiedorder(String appid,String mchid,String notifyUrl,String apiKey,String body,String out_trade_no,String openid, String totalPrice, String cinemaId) {
	    SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
	    int totalPrice_ = (int)(Double.valueOf(totalPrice)* 100);
	   
	    parameters.put("appid",appid);
	    parameters.put("mch_id",mchid);
	    parameters.put("nonce_str", PayCommonUtil.CreateNoncestr());
	    parameters.put("body", body);
	    parameters.put("attach", cinemaId);
	    parameters.put("out_trade_no", out_trade_no);
	    parameters.put("total_fee", totalPrice_+"");
	    parameters.put("spbill_create_ip",CommonUtil.getLocalIp());
	    parameters.put("notify_url",notifyUrl);
	    parameters.put("trade_type", "JSAPI");
	    parameters.put("openid", openid);
	    String sign = PayCommonUtil.createSign(parameters,apiKey);
	    parameters.put("sign", sign);
	    String requestXML = PayCommonUtil.getRequestXml(parameters);
	    logger.info("微信支付订单信息： " + requestXML);
	    String result = CommonUtil.httpsRequest(ConfigUtil.UNIFIED_ORDER_URL, "POST", requestXML);
	    logger.info("微信支付订单返回信息： " + result.toString());
	    Map<String, String> map = new HashMap<String, String>();
	    try {
	        map = XMLUtil.doXMLParse(result);
	    } catch (JDOMException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }//解析微信返回的信息，以Map形式存储便于取值
	    return map.get("prepay_id").toString();
	}
	
	public static String queryOrder(String appid,String mchid,String apiKey,String out_trade_no) throws Exception{
		SortedMap<Object,Object> paramMap = new TreeMap<Object,Object>();
        paramMap.put("appid",appid);
        paramMap.put("mch_id",mchid);
        paramMap.put("nonce_str",PayCommonUtil.CreateNoncestr());
        paramMap.put("out_trade_no",out_trade_no);       
        paramMap.put("sign", PayCommonUtil.createSign(paramMap,apiKey));
        String xmlData = PayCommonUtil.getRequestXml(paramMap);        
        String result = CommonUtil.httpsRequest(ConfigUtil.CHECK_ORDER_URL, "POST", xmlData);
        return result;
    }
	
	public static String downloadbill(String appid,String mchid,String apiKey,String billdate,String billtype) throws Exception{
		SortedMap<Object,Object> paramMap = new TreeMap<Object,Object>();
        paramMap.put("appid",appid);
        paramMap.put("bill_date",billdate);
        paramMap.put("bill_type",billtype);
        paramMap.put("mch_id",mchid);
        paramMap.put("nonce_str",PayCommonUtil.CreateNoncestr());
        paramMap.put("sign", PayCommonUtil.createSign(paramMap,apiKey));
        String xmlData = PayCommonUtil.getRequestXml(paramMap);
        String result = CommonUtil.httpsRequest(ConfigUtil.DOWNLOAD_BILL_URL, "POST", xmlData);
        return result;
    }
	
	public static void main(String[] args) throws Exception {
		
	}

}
