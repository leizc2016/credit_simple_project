package com.pactera.pds.u2.commerce.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

/**
 * 通过post方式发送请求，获取响应信息的工具类
 * @author gaoxiang
 *
 */
public class HttpsPostUtil
{	
	/**
	 * 登录地址
	 */
	private static final String LOGINURL ="https://uat.bundcredit.com/bundcredit-site/creditreportApi/bundCreditLogin";
	/**
	 * 上传地址
	 */
	private static final String UPLOADURL = "https://uat.bundcredit.com/bundcredit-site/api/upload";
	/**
	 * 统一编码为UTF-8
	 */
	private static final String CHARSET = "UTF-8";
	
	
	
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
		conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
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
	/**
	 * post mothed for https protocol(use when login-in)
	 * 
	 * @param url
	 *            api url
	 * @param content
	 *            username&password
	 * @param charset
	 *            encode type
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws IOException
	 */
	public static String postLoginIn(String url, String content, String charset)
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
		conn.setDoInput(true);    
		conn.setRequestMethod("POST");    
		conn.setUseCaches(false);    
		conn.setInstanceFollowRedirects(true);    
		conn.setRequestProperty("Content-Type "," application/x-www-form-urlencoded ");    

		conn.connect(); 
		
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());    

		out.writeBytes(content);    

		out.flush();    
		out.close(); 
	
		BufferedReader reader = new BufferedReader(new InputStreamReader(    
		conn.getInputStream()));
		
		String line;    
		StringBuilder result = new StringBuilder();
		while ((line = reader.readLine()) != null) {    
			result.append(line);
		}    
		reader.close();    
		conn.disconnect();
		return result.toString();  
	}
	/**
	 * 实现证书信任管理器类
	 * @author gaoxiang
	 *
	 */
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
	/**
	 * 实现对证书域名检查
	 * @author gaoxiang
	 *
	 */
	private static class TrustAnyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}
	
	public static void main(String[] args) throws Exception {

		//需上传信息
		String comInfo="{\"data\": [{\" apply_amount_max\": \"5000\", \"app_datetime\": \"20150817\", \"app_pro_city\": \"\\u4e0a\\u6d77\", \"app_type\": \"N\", \"apply_amount_min\": \"4000\", \"approval_amount_max\": \"3800\", \"approval_amount_min\": \"3600\", \"cellphone_num\": \"xxxxxxxxxxxxxxxx\", \"full_name\": \"\\u674e\\u78ca\", \"home_addr\": \"\\u4e0a\\u6d77\\u5e02\\u9ec4\\u6d66\\u533a\\u5317\\u4eac\\u4e1c\\u8def355\\u53f7\", \"id_card_num\": \"xxxxxxxxxxxxxxxx\", \"id_card_type\": \"0\", \"ip_add\": \"xxx.xxx.x.x\", \"loan_account\": \"102569\", \"loan_end_date\": \"20151201\", \"loan_start_date\": \"20150901\", \"relation_card_num\": \"xxxxxxxxxxxxxxxxxxx\", \"relation_card_type\": \"0\", \"relation_name\": \"\\u674e\\u78ca\", \"relation_type\": \"G\"}], \"data_type\": \"1\", \"token\": \"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\"}";
		//将信息字符串转换为JSON对象
		JSONObject obj = JSONObject.fromObject(comInfo);
		//获取登录情况的信息 格式为{"status":"10000","msg":"登陆成功","token":"xxxxxxxxxxxxxxxxxxxxxxxxxxx"}
		String token = HttpsPostUtil.postLoginIn(LOGINURL,"login_name=yourusername&login_psw=yourpassword", CHARSET);
		JSONObject tokenObj = JSONObject.fromObject(token);
		//将comInfo中的token替换为最新的token
		obj.put("token",tokenObj.getString("token"));
		//获取上传的响应信息 格式为{"head":{"statusCode":"YW000","msg":"业务查询成功"},"body":{}}
		String  result= new String(HttpsPostUtil.post(UPLOADURL, obj.toString(), CHARSET), CHARSET);
		
		System.out.println("Result is: " + result);
		
	}

}