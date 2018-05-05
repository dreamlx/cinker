package com.cinker.wechat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class CommonUtil {

	private static Logger log = LoggerFactory.getLogger(CommonUtil.class);
	@Value("${MCH_ID}")
	private static String MCH_ID;

	/**
	 * ����https����
	 * 
	 * @param requestUrl
	 *            �����ַ
	 * @param requestMethod
	 *            ����ʽ��GET��POST��
	 * @param outputStr
	 *            �ύ������
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

	public static String urlEncodeUTF8(String source) {
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, "utf-8");
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

	public static String certificateHttps(String url, String data, String path) {
		// ���ӳ�ʱʱ�䣬Ĭ��10��
		int socketTimeout = 10000;
		// ���䳬ʱʱ�䣬Ĭ��30��
		int connectTimeout = 30000;
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();;
		CloseableHttpClient httpClient = initCert(path);
		String result = null;
        HttpPost httpPost = new HttpPost(url);
        //��ָ��ʹ��UTF-8���룬����API������XML�����Ĳ��ܱ��ɹ�ʶ��
        StringEntity postEntity = new StringEntity(data, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);
        //����������������
        httpPost.setConfig(requestConfig);
        try {
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
 
        } catch (ConnectionPoolTimeoutException e) {
            log.error("http get throw ConnectionPoolTimeoutException(wait time out)");
 
        } catch (ConnectTimeoutException e) {
        	log.error("http get throw ConnectTimeoutException");
 
        } catch (SocketTimeoutException e) {
        	log.error("http get throw SocketTimeoutException");
 
        } catch (Exception e) {
        	log.error("http get throw Exception");
 
        } finally {
            httpPost.abort();
        }
        return result;
    }

	@SuppressWarnings("deprecation")
	private static CloseableHttpClient initCert(String path) {

		KeyStore keyStore = null;
		FileInputStream instream = null;
		try {
			instream = new FileInputStream(new File(path));
			keyStore = KeyStore.getInstance("PKCS12");
			// ����֤�����룬Ĭ��Ϊ�̻�ID
			keyStore.load(instream, MCH_ID.toCharArray());
		} catch (FileNotFoundException e) {
			log.error("�ļ�·���쳣��{}", e);
		} catch (KeyStoreException | NoSuchAlgorithmException e) {
			log.error("��Կ�����쳣��{}", e);
		} catch (CertificateException e) {
			log.error("֤�鴴��ʧ���쳣��{}", e);
		} catch (IOException e) {

		} finally {
			try {
				instream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			instream = null;
		}

		// Trust own CA and all self-signed certs
		SSLContext sslcontext = null;
		try {
			sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, MCH_ID.toCharArray()) // ����֤�����룬Ĭ��Ϊ�̻�ID
					.build();
		} catch (KeyManagementException | UnrecoverableKeyException | NoSuchAlgorithmException | KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Allow TLSv1 protocol only
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		return HttpClients.custom().setSSLSocketFactory(sslsf).build();
	}
}
