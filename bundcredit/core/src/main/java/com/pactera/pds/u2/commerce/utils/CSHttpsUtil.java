package com.pactera.pds.u2.commerce.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Random;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import net.sf.json.JSONObject;

public class CSHttpsUtil
{	
	private static final String KEY = "54dfbcde23a1f59b";
	private static final String SYNV_URL = "https://222.186.52.29/zhengxin/sh/v1.0/reality_realtime_test.php";
	private static final String ASYNV_URL = "https://222.186.52.29/zhengxin/sh/v1.0/reality_async_test.php";
	private static final String CHARSET = "UTF-8";
	
	/**
	 * syncQuery
	 * 
	 * @param insJson input Json. 
	 * {"s_shouji": "12345678901", 
	 *  "s_shenfenID": "320123456789123456", 
	 *  "s_contact1": "13611111111", 
	 *  "s_add_baitian": "北京东路", 
	 *  "s_add_yewan": "北京西路", 
	 *  "s_caseid": "12345678", 
	 *  "SQ": "0000"}
	 * @return
	 * @throws Exception
	 */
	public static String syncQuery(JSONObject insJson) throws Exception {
		
		if (PropertiesInfo.isUatFlag()) {
			return dmpSyncQuery();
		}
		
		final String POST_BODY = "KEY=%s&s_shouji=%s&s_shenfenID=%s&s_contact1=%s&s_add_baitian=%s&s_add_yewan=%s&s_caseid=%s&SQ=%s";
		
		String s_shouji = insJson.getString("s_shouji");
		String s_shenfenID = insJson.getString("s_shenfenID");
		String s_contact1 = insJson.getString("s_contact1");
		String s_add_baitian = insJson.getString("s_add_baitian");
		String s_add_yewan = insJson.getString("s_add_yewan");
		String s_caseid = insJson.getString("s_caseid");
		String sq = insJson.getString("SQ");
		
		String content = String.format(POST_BODY, KEY, s_shouji, s_shenfenID, s_contact1, s_add_baitian, s_add_yewan, s_caseid, sq);
		byte[] sr = CSHttpsUtil.post(SYNV_URL, content, CHARSET);
		
		System.out.println(new String(sr, CHARSET));
	
		return new String(sr, CHARSET);
		
	}
	
	
	/**
	 * AsyncQuery
	 * 
	 * @param insJson input Json. {"s_shouji": "12345678901", "s_caseid": "12345678"}
	 * @return
	 * @throws Exception
	 */
	public static String asyncQuery(JSONObject insJson) throws Exception {
		
		if (PropertiesInfo.isUatFlag()) {
			return dmpAsyncQuery();
		}
		
		final String POST_BODY = "KEY=%s&s_shouji=%s&s_caseid=%s";
		
		
		String s_shouji = insJson.getString("s_shouji");
		String s_caseid = insJson.getString("s_caseid");
		
		String content = String.format(POST_BODY, KEY, s_shouji, s_caseid);
		byte[] sr = CSHttpsUtil.post(ASYNV_URL, content, CHARSET);
		
		System.out.println(new String(sr, CHARSET));
		
	
		return new String(sr, CHARSET);
		
	}
	
    /**
	 * post mothed for https protocol
	 * 
	 * @param url
	 *            api url
	 * @param content
	 *            post parameters
	 * @param charset
	 *            encode type
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws IOException
	 */
	public static byte[] post(String url, String content, String charset)
			throws NoSuchAlgorithmException, KeyManagementException,
			IOException {
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
				new java.security.SecureRandom());

		URL console = new URL(url);
		HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
		conn.setSSLSocketFactory(sc.getSocketFactory());
		conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
		conn.setDoOutput(true);
		conn.connect();
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		out.write(content.getBytes(charset));
		// refresh and close
		out.flush();
		out.close();
		InputStream is = conn.getInputStream();
		if (is != null) {
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = is.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
			is.close();
			return outStream.toByteArray();
		}
		return null;
	}
	
	// dump Sync Query
	private static String dmpSyncQuery() {
		final String RES = "{\"i_shimin\":%s,\"i_guhuashu\":%s,\"i_shenfenzhen\":%s,\"i_zaiwangshijian\":%s,\"i_shoujishu\":%s,\"area\":\"sh\",\"code\":0}";
		String result = String.format(RES, randomNum(2), randomNum(10), randomNum(2), "641", randomNum(10));
		return result;
	}
	
	// dump Async Query
	private static String dmpAsyncQuery() {
		final String RES = "{\"i_yewan\":%s,\"i_baitian\":%s,\"lianxi1\":[1,1,1],\"lianxi2\":[81,161,161],\"zhangdan_6\":[1001,2001,8001,1,8001,1001],\"liuliang_6\":[5000001,5000001,1,5000001,5000001,1],\"code\":0}";
		String result = String.format(RES, randomNum(2), randomNum(2));
		return result;
	}
	
	private static int randomNum(int max){
		int number = new Random().nextInt(max);
		return number;
	}
	
	private static class TrustAnyTrustManager implements X509TrustManager {

		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}
	}
	
	private static class TrustAnyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			
			return true;
		}
	}

}