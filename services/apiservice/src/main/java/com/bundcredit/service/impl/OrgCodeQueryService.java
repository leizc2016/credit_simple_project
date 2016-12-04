package com.bundcredit.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jws.WebService;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.bundcredit.entity.OrgCodeBean;
import com.bundcredit.service.IEnterpriseInfoQueryService;
import com.bundcredit.utils.HttpClientUtil;
import com.bundcredit.utils.JsonBinder;

@WebService
public class OrgCodeQueryService implements IEnterpriseInfoQueryService {
	
	private static JsonBinder binder = JsonBinder.buildNonDefaultBinder();
	private static final String  HTTPURL = "https://s.nacao.org.cn/dwr/exec/ServiceForNum.getData.dwr";

	public String query(String param, String param1, String param2) {
		   //HttpPost连接对象         
		   HttpPost httpRequest = new HttpPost(HTTPURL);  
		   List<NameValuePair> params = new ArrayList<NameValuePair>();
		   params.add(new BasicNameValuePair("callCount", "1"));  
		   params.add(new BasicNameValuePair("c0-scriptName", "ServiceForNum"));
		   params.add(new BasicNameValuePair("c0-methodName", "getData"));
		   params.add(new BasicNameValuePair("c0-e2", "string:jgmc='" + param + "'"));
		   params.add(new BasicNameValuePair("c0-e4", "string:2"));
		   params.add(new BasicNameValuePair("c0-param0", "Object:{firststrfind:reference:c0-e1, strfind:reference:c0-e2, key:reference:c0-e3, kind:reference:c0-e4, tit1:reference:c0-e5, selecttags:reference:c0-e6, xzqhName:reference:c0-e7, button:reference:c0-e8, jgdm:reference:c0-e9, jgmc:reference:c0-e10, jgdz:reference:c0-e11, zch:reference:c0-e12, strJgmc:reference:c0-e13, :reference:c0-e14, secondSelectFlag:reference:c0-e15}"));
		   
		   Header[] headers = {new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0"),  
		             new BasicHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3"), 
		             new BasicHeader("Content-Type", "text/plain; charset=UTF-8"),  
		             new BasicHeader("Accept-Encoding", "gzip, deflate"),
		             new BasicHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"),
		             new BasicHeader("Connection", "keep-alive"),
		             new BasicHeader("Host", "s.nacao.org.cn"),
		             new BasicHeader("Referer", "https://s.nacao.org.cn/specialResult.html?x=4xikDs5ykmmAiqSqP8W350q28MM=&k=i2GjGJLm0wu0YmhZ9VEa8RA=&s"+
		            		 			"=fsmQ08JPMnb0vum1vbZPOPogEMmFsHi3PkcG49sw0fvFZwc3RyeDVcpAQbRxDXYTWtgQLQ==&y=FXchEQxhgKBS9t0Bzgvaxk74JbONlxvJ2C2APw=="),
		   }; 

		   OrgCodeBean orgCode= new OrgCodeBean();
		   //设置字符集     
		   try {
		    HttpEntity httpentity = new UrlEncodedFormEntity(params, "utf-8");             
		    httpRequest.setHeaders(headers);
		   //请求httpRequest            
		    httpRequest.setEntity(httpentity);             
		    
		    CloseableHttpClient httpc = HttpClientUtil.createSSLClientDefault();
		    //取得HttpResponse            
		     HttpResponse httpResponse = httpc.execute(httpRequest);              
		       
		     //取得返回的字符串                 
		     String strResult = EntityUtils.toString(httpResponse.getEntity());          
		     System.out.println(strResult);
		     
		     orgCode.setApplyDate(getOrgCodeInfo("s18=\".*\";", strResult));
		     orgCode.setIssuer(getOrgCodeInfo("s9=\".*\";", strResult));
		     orgCode.setStartDate(getOrgCodeInfo("s10=\".*\";", strResult));
		     orgCode.setEndDate(getOrgCodeInfo("s19=\".*\";", strResult));
		     orgCode.setOrgCode(getOrgCodeInfo("s11=\".*\";", strResult));
		     orgCode.setOrgAddress(getOrgCodeInfo("s12=\".*\";", strResult));
		     orgCode.setOrgType(getOrgCodeInfo("s13=\".*\";", strResult).trim());
		     orgCode.setOrgName(getOrgCodeInfo("s14=\".*\";", strResult));
		     orgCode.setOrgRegID(getOrgCodeInfo("s17=\".*\";", strResult));
		   } catch(Exception e){
			   e.printStackTrace();
		   }
		return binder.toJson(orgCode);
	}
	
	private static String getOrgCodeInfo(String pattern, String wholeResult) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(wholeResult);
		String msg = null;
		while (m.find()) {
			msg = m.group();
		}

		msg = msg.split("\"")[1];
		
		return decodeUnicode(msg);
	}
	           
	public static String decodeUnicode(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					// Read the xxxx
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException(
									"Malformed   \\uxxxx   encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't') {
						aChar = '\t';
					} else if (aChar == 'r') {
						aChar = '\r';
					} else if (aChar == 'n') {
						aChar = '\n';
					}

					else if (aChar == 'f') {
						aChar = '\f';
					}
					outBuffer.append(aChar);
				}
			} else {
				outBuffer.append(aChar);
			}
		}
		return outBuffer.toString();
	}

	public static void main(String[] args) {
		OrgCodeQueryService s = new OrgCodeQueryService();
		System.out.println(s.query("外滩海纳互联网金融服务(上海)有限公司","",""));
	}

}
