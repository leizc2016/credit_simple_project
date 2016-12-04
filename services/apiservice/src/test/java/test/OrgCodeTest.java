package test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.bundcredit.utils.HttpClientUtil;


/**
 * 利用HttpClient，模拟https连接
 */
public class OrgCodeTest{
    
    /**
     * 运行主方法
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
      Map<String, String> parameters = new HashMap<String, String>();
      parameters.put("callCount", "1");
      parameters.put("c0-scriptName", "ServiceForNum");
      parameters.put("c0-methodName", "getData");
      parameters.put("c0-id", "");
      parameters.put("c0-e1", URLEncoder.encode("string:jgmc='外滩海纳互联网金融服务(上海)有限公司'  not ZYBZ=('2')", "UTF8"));
      parameters.put("c0-e2", URLEncoder.encode("string:jgmc='外滩海纳互联网金融服务(上海)有限公司'  not ZYBZ=('2')", "UTF8"));
      parameters.put("c0-e3", URLEncoder.encode("string:外滩海纳互联网金融服务(上海)有限公司", "UTF8"));
      parameters.put("c0-e4", URLEncoder.encode("string:2", "UTF8"));
      parameters.put("c0-e5", URLEncoder.encode("string:外滩海纳互联网金融服务(上海)有限公司", "UTF8"));
      parameters.put("c0-e6", URLEncoder.encode("string:全国", "UTF8"));
      parameters.put("c0-e7", URLEncoder.encode("string:alll", "UTF8"));
      parameters.put("c0-e8", URLEncoder.encode("string:", "UTF8"));
      parameters.put("c0-e9", URLEncoder.encode("boolean:false", "UTF8"));
      parameters.put("c0-e10", URLEncoder.encode("boolean:true", "UTF8"));
      parameters.put("c0-e11", URLEncoder.encode("boolean:false", "UTF8"));
      parameters.put("c0-e12", URLEncoder.encode("boolean:false", "UTF8"));
      parameters.put("c0-e13", URLEncoder.encode("string:", "UTF8"));
      parameters.put("c0-e14", URLEncoder.encode("string:", "UTF8"));
      parameters.put("c0-e15", URLEncoder.encode("string:", "UTF8"));
      parameters.put("c0-param0", URLEncoder.encode("Object:{firststrfind:reference:c0-e1, strfind:reference:c0-e2, key:reference:c0-e3, kind:reference:c0-e4, tit1:reference:c0-e5, selecttags:reference:c0-e6, xzqhName:reference:c0-e7, button:reference:c0-e8, jgdm:reference:c0-e9, jgmc:reference:c0-e10, jgdz:reference:c0-e11, zch:reference:c0-e12, strJgmc:reference:c0-e13, :reference:c0-e14, secondSelectFlag:reference:c0-e15}", "UTF8"));
      parameters.put("xml", URLEncoder.encode("true", "UTF8"));
      
      CloseableHttpClient httpClient = HttpClientUtil.createSSLClientDefault();
      //HttpPost httpost = new HttpPost("https://s.nacao.org.cn/dwr/exec/ServiceForNum.getData.dwr");  
      HttpPost httpost = new HttpPost("https://s.nacao.org.cn/dwr/exec/ServiceForNum.getData.dwr"); 
      List<NameValuePair> nvps = new ArrayList <NameValuePair>();  
      Set<String> keySet = parameters.keySet();  
      for(String key : keySet) {  
          nvps.add(new BasicNameValuePair(key, parameters.get(key)));  
      }  
      httpost.addHeader("callCount", "1");
      httpost.addHeader("c0-scriptName", "ServiceForNum");
      httpost.addHeader("c0-methodName", "getData");
      httpost.addHeader("c0-id", "");
      httpost.addHeader("c0-e1", URLEncoder.encode("string:jgmc='外滩海纳互联网金融服务(上海)有限公司'  not ZYBZ=('2')", "UTF-8"));
      httpost.addHeader("c0-e2", URLEncoder.encode("string:jgmc='外滩海纳互联网金融服务(上海)有限公司'  not ZYBZ=('2')", "UTF-8"));
      httpost.addHeader("c0-e3", URLEncoder.encode("string:外滩海纳互联网金融服务(上海)有限公司", "UTF-8"));
      httpost.addHeader("c0-e4", URLEncoder.encode("string:2", "UTF-8"));
      httpost.addHeader("c0-e5", URLEncoder.encode("string:外滩海纳互联网金融服务(上海)有限公司", "UTF-8"));
      httpost.addHeader("c0-e6", URLEncoder.encode("string:全国", "UTF-8"));
      httpost.addHeader("c0-e7", URLEncoder.encode("string:alll", "UTF-8"));
      httpost.addHeader("c0-e8", URLEncoder.encode("string:", "UTF-8"));
      httpost.addHeader("c0-e9", URLEncoder.encode("boolean:false", "UTF-8"));
      httpost.addHeader("c0-e10", URLEncoder.encode("boolean:true", "UTF-8"));
      httpost.addHeader("c0-e11", URLEncoder.encode("boolean:false", "UTF-8"));
      httpost.addHeader("c0-e12", URLEncoder.encode("boolean:false", "UTF-8"));
      httpost.addHeader("c0-e13", URLEncoder.encode("string:", "UTF-8"));
      httpost.addHeader("c0-e14", URLEncoder.encode("string:", "UTF-8"));
      httpost.addHeader("c0-e15", URLEncoder.encode("string:", "UTF-8"));
      httpost.addHeader("c0-param0", URLEncoder.encode("Object:{firststrfind:reference:c0-e1, strfind:reference:c0-e2, key:reference:c0-e3, kind:reference:c0-e4, tit1:reference:c0-e5, selecttags:reference:c0-e6, xzqhName:reference:c0-e7, button:reference:c0-e8, jgdm:reference:c0-e9, jgmc:reference:c0-e10, jgdz:reference:c0-e11, zch:reference:c0-e12, strJgmc:reference:c0-e13, :reference:c0-e14, secondSelectFlag:reference:c0-e15}", "UTF-8"));
      httpost.addHeader("xml", URLEncoder.encode("true", "UTF-8"));
      httpost.addHeader("Content-Type", URLEncoder.encode("text/plain","UTF-8"));
     /* try {  
    	  
         // httpost.setEntity(new UrlEncodedFormEntity(nvps, "UTF8"));  
      } catch (UnsupportedEncodingException e) {  
          e.printStackTrace();  
      }*/
      httpost.addHeader("User-Agent",  "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)");
      //httpost.addHeader("Cookie","	");
      //发送请求
      HttpResponse response = httpClient.execute(httpost);
      //输出返回值
      InputStream is = response.getEntity().getContent();
      System.out.println(convertStreamToString(is));
      /*BufferedReader br = new BufferedReader(new InputStreamReader(is));
      String line = "";
      while((line = br.readLine())!=null){
          System.out.println(line);
      }*/
      httpost.releaseConnection();
    }
    
    public static String convertStreamToString(InputStream is) {      
        StringBuilder sb1 = new StringBuilder();      
        byte[] bytes = new byte[4096];    
        int size = 0;    
          
        try {      
            while ((size = is.read(bytes)) > 0) {    
                String str = new String(bytes, 0, size, "UTF-8");    
                sb1.append(str);    
            }    
        } catch (IOException e) {      
            e.printStackTrace();      
        } finally {      
            try {      
                is.close();      
            } catch (IOException e) {      
               e.printStackTrace();      
            }      
        }      
        return sb1.toString();      
    }  
} 
