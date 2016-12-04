package test;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

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

import com.bundcredit.utils.HttpClientUtil;


/**
 * 利用HttpClient，模拟https连接
 */
public class OrgCodeTest1{
    
    /**
     * 运行主方法
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
    		  try {
    		   String httpUrl = "https://s.nacao.org.cn/dwr/exec/ServiceForNum.getData.dwr";         
    		   //HttpPost连接对象         
    		   HttpPost httpRequest = new HttpPost(httpUrl);  
    		   List<NameValuePair> params = new ArrayList<NameValuePair>();
    		   params.add(new BasicNameValuePair("callCount", "1"));  
    		   params.add(new BasicNameValuePair("c0-scriptName", "ServiceForNum"));
    		   params.add(new BasicNameValuePair("c0-methodName", "getData"));
//    		   params.add(new BasicNameValuePair("c0-id", ""));
//    		   params.add(new BasicNameValuePair("c0-e1", ""));
    		   params.add(new BasicNameValuePair("c0-e2", "string:jgmc='外滩海纳互联网金融服务(上海)有限公司'"));
//    		   params.add(new BasicNameValuePair("c0-e3", ""));
    		   params.add(new BasicNameValuePair("c0-e4", "string:2"));
//		      params.add(new BasicNameValuePair("c0-e5", "string:外滩海纳互联网金融服务(上海)有限公司"));
//		      params.add(new BasicNameValuePair("c0-e6", "string:全国"));
//		      params.add(new BasicNameValuePair("c0-e7", "string:alll"));
//		      params.add(new BasicNameValuePair("c0-e8", "string:"));
//		      params.add(new BasicNameValuePair("c0-e9", "boolean:false"));
//		      params.add(new BasicNameValuePair("c0-e10", "boolean:true"));
//		      params.add(new BasicNameValuePair("c0-e11", "boolean:false"));
//		      params.add(new BasicNameValuePair("c0-e12", "boolean:false"));
//		      params.add(new BasicNameValuePair("c0-e13", "string:"));
//		      params.add(new BasicNameValuePair("c0-e14", "string:"));
//		      params.add(new BasicNameValuePair("c0-e15", "string:"));
		      params.add(new BasicNameValuePair("c0-param0", "Object:{firststrfind:reference:c0-e1, strfind:reference:c0-e2, key:reference:c0-e3, kind:reference:c0-e4, tit1:reference:c0-e5, selecttags:reference:c0-e6, xzqhName:reference:c0-e7, button:reference:c0-e8, jgdm:reference:c0-e9, jgmc:reference:c0-e10, jgdz:reference:c0-e11, zch:reference:c0-e12, strJgmc:reference:c0-e13, :reference:c0-e14, secondSelectFlag:reference:c0-e15}"));
//		      params.add(new BasicNameValuePair("xml", URLEncoder.encode("true", "UTF8")));
    		   
    		   Header[] headers = {new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0"),  
    		             new BasicHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3"), 
    		             new BasicHeader("Content-Type", "text/plain; charset=UTF-8"),  
    		             new BasicHeader("Accept-Encoding", "gzip, deflate"),
    		             new BasicHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"),
    		             new BasicHeader("Connection", "keep-alive"),
    		             //new BasicHeader("Content-Length", "996"),
    		             new BasicHeader("Host", "s.nacao.org.cn"),
    		             new BasicHeader("Referer", "https://s.nacao.org.cn/specialResult.html?x=4xikDs5ykmmAiqSqP8W350q28MM=&k=i2GjGJLm0wu0YmhZ9VEa8RA=&s"+
    		            		 			"=fsmQ08JPMnb0vum1vbZPOPogEMmFsHi3PkcG49sw0fvFZwc3RyeDVcpAQbRxDXYTWtgQLQ==&y=FXchEQxhgKBS9t0Bzgvaxk74JbONlxvJ2C2APw=="),
    		   }; 

    		   
    		   //设置字符集            
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
    		     } catch (Exception e) {
    		    	 e.printStackTrace();
    		   }
    }
} 
