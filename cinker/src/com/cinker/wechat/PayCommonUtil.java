package com.cinker.wechat;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
public class PayCommonUtil {
	/**
	 * ��ȡ֧�������
	 * @return
	 */
	 public static String create_nonce_str() {
	        return UUID.randomUUID().toString();
	    }
	   /**
	    * ��ȡ΢��֧��ʱ���
	    * @return
	    */
	    public static String create_timestamp() {
	        return Long.toString(System.currentTimeMillis() / 1000);
	    }

	    /**
	     * ��ȡԤ֧��IDʱ  ��ȡ�����
	     * @param length
	     * @return
	     */
	    public static String CreateNoncestr(int length) {
	        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	        String res = "";
	        for (int i = 0; i < length; i++) {
	            Random rd = new Random();
	            res += chars.indexOf(rd.nextInt(chars.length() - 1));
	        }
	        return res;
	    }
	    /**
	     * ��ȡԤ֧��IDʱ  ��ȡ�����
	     * @param length
	     * @return
	     */
	    public static String CreateNoncestr() {
	        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	        String res = "";
	        for (int i = 0; i < 16; i++) {
	            Random rd = new Random();
	            res += chars.charAt(rd.nextInt(chars.length() - 1));
	        }
	        return res;
	    }

	/**
	 * @author Mark
	 * @Description��signǩ��
	 * @param characterEncoding �����ʽ
	 * @param parameters �������
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String createSign(SortedMap<Object,Object> parameters,String apiKey){
	    StringBuffer sb = new StringBuffer();
	    Set es = parameters.entrySet();
	    Iterator it = es.iterator();
	    while(it.hasNext()) {
	        Map.Entry entry = (Map.Entry)it.next();
	        String k = (String)entry.getKey();
	        Object v = entry.getValue();
	        if(null != v && !"".equals(v) 
	                && !"sign".equals(k) && !"key".equals(k)) {
	            sb.append(k + "=" + v + "&");
	        }
	    }
	    sb.append("key=" +apiKey);
	    String sign = MD5Util.MD5Encode(sb.toString(),"UTF-8").toUpperCase();
	    return sign;
	}


	/**
	 * @author Mark
	 * @Description�����������ת��Ϊxml��ʽ��string
	 * @param parameters  �������
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getRequestXml(SortedMap<Object,Object> parameters){
	    StringBuffer sb = new StringBuffer();
	    sb.append("<xml>");
	    Set es = parameters.entrySet();
	    Iterator it = es.iterator();
	    while(it.hasNext()) {
	        Map.Entry entry = (Map.Entry)it.next();
	        String k = (String)entry.getKey();
	        String v = (String)entry.getValue();
	        if ("attach".equalsIgnoreCase(k)||"body".equalsIgnoreCase(k)||"sign".equalsIgnoreCase(k)) {
	            sb.append("<"+k+">"+"<![CDATA["+v+"]]></"+k+">");
	        }else {
	            sb.append("<"+k+">"+v+"</"+k+">");
	        }
	    }
	    sb.append("</xml>");
	    return sb.toString();
	}
	/**
	 * @author Mark
	 * @Description�����ظ�΢�ŵĲ���
	 * @param return_code ���ر���
	 * @param return_msg  ������Ϣ
	 * @return
	 */
	public static String setXML(String return_code, String return_msg) {
	    return "<xml><return_code><![CDATA[" + return_code
	            + "]]></return_code><return_msg><![CDATA[" + return_msg
	            + "]]></return_msg></xml>";
	}

}
