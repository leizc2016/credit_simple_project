package com.bundcredit.utils;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {
	
	public static CloseableHttpClient createSSLClientDefault(){
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				//信任所有
				public boolean isTrusted(X509Certificate[] chain,String authType) throws CertificateException {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return  HttpClients.createDefault();
	}
	
	public static String sendPost(String httpUrl,List<NameValuePair> params,Header[] headers){
			HttpPost httpRequest = null;
		    HttpEntity httpentity = null;
		    String httpResp = null;
			try {
				httpRequest = new HttpPost(httpUrl); 
				httpentity = new UrlEncodedFormEntity(params, "utf-8");
				if(null != httpRequest){
					 httpRequest.setHeaders(headers);
					 httpRequest.setEntity(httpentity);  
					 CloseableHttpClient httpc = HttpClientUtil.createSSLClientDefault();
					 HttpResponse httpResponse = httpc.execute(httpRequest);  
					 if(null != httpResponse){
		    			 httpResp =  EntityUtils.toString(httpResponse.getEntity());  
		    		 }
				}
			} catch (Exception e) {
				e.printStackTrace();
			}   
			return httpResp;
	}
	
	public static String sendGet(String httpUrl,Header[] headers){
		HttpGet httpRequest = null;
	    String httpResp = null;
		try {
			httpRequest = new HttpGet(httpUrl); 
			if(null != httpRequest){
				 httpRequest.setHeaders(headers);
				 CloseableHttpClient httpc = HttpClientUtil.createSSLClientDefault();
				 HttpResponse httpResponse = httpc.execute(httpRequest);  
	    		 if(null != httpResponse){
	    			 httpResp =  EntityUtils.toString(httpResponse.getEntity());  
	    		 }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}   
		return httpResp;
	}
}
