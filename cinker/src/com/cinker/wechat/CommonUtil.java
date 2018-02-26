package com.cinker.wechat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CommonUtil {
	
	private static Logger log = LoggerFactory.getLogger(CommonUtil.class); 
	/** 
	* ����https���� 
	* @param requestUrl �����ַ 
	* @param requestMethod ����ʽ��GET��POST�� 
	* @param outputStr �ύ������ 
	* @return ����΢�ŷ�������Ӧ����Ϣ 
	*/ 
	public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) { 
	try { 
	// ����SSLContext���󣬲�ʹ������ָ�������ι�������ʼ�� 
		TrustManager[] tm = { new MyX509TrustManager() }; 
		SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE"); 
		sslContext.init(null, tm, new SecureRandom()); 
		// ������SSLContext�����еõ�SSLSocketFactory���� 
		SSLSocketFactory ssf = sslContext.getSocketFactory(); 
		URL url = new URL(requestUrl); 
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection(); 
		conn.setSSLSocketFactory(ssf); 
		conn.setDoOutput(true); 
		conn.setDoInput(true); 
		conn.setUseCaches(false); 
		// ��������ʽ��GET/POST�� 
		conn.setRequestMethod(requestMethod); 
		conn.setRequestProperty("content-type", "application/x-www-form-urlencoded"); 
		// ��outputStr��Ϊnullʱ�������д���� 
		if (null != outputStr) { 
		OutputStream outputStream = conn.getOutputStream(); 
		// ע������ʽ 
		outputStream.write(outputStr.getBytes("UTF-8")); 
		outputStream.close(); 
		} 
	// ����������ȡ�������� 
	InputStream inputStream = conn.getInputStream(); 
	InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8"); 
	BufferedReader bufferedReader = new BufferedReader(inputStreamReader); 
	String str = null; 
	StringBuffer buffer = new StringBuffer(); 
	while ((str = bufferedReader.readLine()) != null) { 
	buffer.append(str); 
	} 
	// �ͷ���Դ 
	bufferedReader.close(); 
	inputStreamReader.close(); 
	inputStream.close(); 
	inputStream = null; 
	conn.disconnect(); 
	return buffer.toString(); 
	} catch (ConnectException ce) { 
	log.error("���ӳ�ʱ��{}", ce); 
	} catch (Exception e) { 
	log.error("https�����쳣��{}", e); 
	} 
	return null; 
	}
	
	public static String urlEncodeUTF8(String source){
	    String result = source;
	    try {
	        result = java.net.URLEncoder.encode(source,"utf-8");
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    }
	    return result;
	}
	
	public static String getLocalIp() {
		String ip = null;
		@SuppressWarnings("rawtypes")
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

}
