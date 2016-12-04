package com.pactera.pds.u2.commerce.thirdpartyapi;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import net.sf.json.JSONObject;

import org.junit.Assert;
import org.junit.Test;

public class CSTest {

	@Test
	public void testCSAPI() throws Exception {
		
		// Send Post Request
		final String POST_BODY_SYNC = "KEY=%s&s_shouji=%s&s_shenfenID=%s&s_contact1=%s&s_contact2=%s&s_add_baitian=%s&s_caseid=%s&SQ=%s";
		
		String url_sync = "https://222.186.52.29/zhengxin/sh/v1.0/reality_realtime_test.php";
		String url_async = "https://222.186.52.29/zhengxin/sh/v1.0/reality_async_test.php";
		String charset = "UTF-8";
		//String content = "KEY=54dfbcde23a1f59b&s_shouji=18917999161&s_shenfenID=231023197009270067&s_contact1=13069788077&s_contact2=18936616267&s_add_baitian=连云区中华路2号楼中单元704室&s_caseid=test44&SQ=oCzyAC7RAs-";
		String key = "54dfbcde23a1f59b";
		String s_shouji = "13601832802";
		String s_shenfenID = "231023197009270067";
		String s_contact1 = "13069788077";
		String s_contact2 = "18936616267";
		String s_add_baitian = "连云区中华路2号楼中单元704室";
		String s_add_yewan = "连云区中华路2号楼中单元704室";
		String s_caseid = "12345678";
		String sq = "oCzyAC7RAs";
		
		JSONObject requestResultData = null;
		
		String content_sync = String.format(POST_BODY_SYNC, key, s_shouji, s_shenfenID, s_contact1, s_contact2, s_add_baitian, s_caseid, sq);
		byte[] sr_sync = post(url_sync, content_sync, charset);
		
		requestResultData = JSONObject.fromObject(new String(sr_sync, "UTF-8"));
		
		Assert.assertEquals("CS SYNC API is broken.", "0",  requestResultData.getString("code"));
		
		System.out.println(new String(sr_sync, "UTF-8"));
		
		final String POST_BODY_ASYNC = "KEY=%s&s_shouji=%s&s_caseid=%s";
		
		String content_async = String.format(POST_BODY_ASYNC, key, s_shouji, s_caseid);
		byte[] sr_async = post(url_async, content_async, charset);
		
		requestResultData = JSONObject.fromObject(new String(sr_async, "UTF-8"));
		
		Assert.assertEquals("CS ASYNC API is broken.", "0",  requestResultData.getString("code"));
		
//		System.out.println(new String(sr_async, "UTF-8"));
		
	}
	
	public static void main(String[] args) throws Exception {
		
//		RestTemplate restTemplate = new RestTemplate();
//		
//		Map<String ,Object> urlVariables = new HashMap<String ,Object>();
//		urlVariables.put("KEY", "54dfbcde23a1f59b");
//		urlVariables.put("shouji", "18917999161");
//		urlVariables.put("shenfenzheng", "231023197009270067");
//		urlVariables.put("s_contact1", "13069788077");
//		urlVariables.put("s_contact2", "18936616267");
//		urlVariables.put("s_add_baitian", "连云区中华路2号楼中单元704室");
//		urlVariables.put("s_add_yewan", null);
//		urlVariables.put("s_caseid", "test44");
//		urlVariables.put("SQ", "oCzyAC7RAs-");
//		
//		String message3 = restTemplate.postForObject("https://222.186.52.29/zhengxin/sh/v1.0/reality_realtime_test.php",null, String.class, urlVariables);
//		System.out.println(message3);
		
		// Send Post Request
		final String POST_BODY_SYNC = "KEY=%s&s_shouji=%s&s_shenfenID=%s&s_contact1=%s&s_contact2=%s&s_add_baitian=%s&s_caseid=%s&SQ=%s";
		
		String url_sync = "https://222.186.52.29/zhengxin/sh/v1.0/reality_realtime_test.php";
		String url_async = "https://222.186.52.29/zhengxin/sh/v1.0/reality_async_test.php";
		String charset = "UTF-8";
//		String content = "KEY=54dfbcde23a1f59b&s_shouji=18917999161&s_shenfenID=231023197009270067&s_contact1=13069788077&s_contact2=18936616267&s_add_baitian=连云区中华路2号楼中单元704室&s_caseid=test44&SQ=oCzyAC7RAs-";
		String key = "54dfbcde23a1f59b";
		String s_shouji = "13601832802";
		String s_shenfenID = "231023197009270067";
		String s_contact1 = "13069788077";
		String s_contact2 = "18936616267";
		String s_add_baitian = "连云区中华路2号楼中单元704室";
		String s_add_yewan = "连云区中华路2号楼中单元704室";
		String s_caseid = "12345678";
		String sq = "oCzyAC7RAs";
		
		String content_sync = String.format(POST_BODY_SYNC, key, s_shouji, s_shenfenID, s_contact1, s_contact2, s_add_baitian, s_caseid, sq);
		byte[] sr_sync = post(url_sync, content_sync, charset);
		
		System.out.println(new String(sr_sync, "UTF-8"));
		
		final String POST_BODY_ASYNC = "KEY=%s&s_shouji=%s&s_caseid=%s";
		
		String content_async = String.format(POST_BODY_ASYNC, key, s_shouji, s_caseid);
		byte[] sr_async = post(url_async, content_async, charset);
		
		System.out.println(new String(sr_async, "UTF-8"));
		
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

	/**
	 * post方式请求服务器(https协议)
	 * 
	 * @param url
	 *            请求地址
	 * @param content
	 *            参数
	 * @param charset
	 *            编码
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
		// 刷新、关闭
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


}
