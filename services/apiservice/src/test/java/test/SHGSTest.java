/**
 * 
 */
package test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
 * @author CJ
 *上海工商企业信息
 */
public class SHGSTest {
    public static void main(String[] args) throws Exception {
        try {
         String httpUrl = "https://www.sgs.gov.cn/notice/search/popup_captcha";         
         //HttpPost连接对象         
         HttpPost httpRequest = new HttpPost(httpUrl);  
         CloseableHttpClient httpc = HttpClientUtil.createSSLClientDefault();
         //取得HttpResponse            
         HttpResponse httpResponse = httpc.execute(httpRequest); 
         //取得返回的字符串                 
         String strResult = EntityUtils.toString(httpResponse.getEntity());   
         Pattern p = Pattern.compile("code: \"([0-9a-zA-Z-]+)\",");
         Matcher m=p.matcher(strResult);
         m.find();
         String token = m.group().split("\"")[1];
         
         
         String conditionkeyword="麦肯锡";
         httpUrl="https://www.sgs.gov.cn/notice/search/ent_info_list?searchType=1&captcha=&session.token=" + token + "&condition.keyword=" + conditionkeyword;;
         httpRequest = new HttpPost(httpUrl); 
         httpResponse = httpc.execute(httpRequest); 
         strResult = EntityUtils.toString(httpResponse.getEntity());   
         
         p = Pattern.compile("uuid=(.*)&tab=01");
         m=p.matcher(strResult);
         m.find();
         httpUrl="https://www.sgs.gov.cn/notice/notice/view?"+m.group();
         httpRequest = new HttpPost(httpUrl); 
         httpResponse = httpc.execute(httpRequest); 
         strResult = EntityUtils.toString(httpResponse.getEntity()); 
         
         System.out.println(strResult);
        
        } catch (Exception e) {
             e.printStackTrace();
         }
         
           
}
}
