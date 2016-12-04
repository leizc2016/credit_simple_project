package com.bundcredit.service.impl;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jws.WebService;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.type.TypeReference;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.bundcredit.entity.BusinessInfoBean;
import com.bundcredit.service.IFinancialStableQueryService;
import com.bundcredit.utils.HttpClientUtil;
import com.bundcredit.utils.JsonBinder;

//金融维稳查询service
@WebService
public class FinancialStableQueryService implements IFinancialStableQueryService{
	
	private static JsonBinder binder = JsonBinder.buildNonDefaultBinder(); 
	private static final String  HTTPURL = "http://qichacha.com/search?key={0}&type=0&province=SH&p={1}";
	private static final String  DPHTTPURL = "http://qichacha.com/search?type=1&province=&key={0}&sstype=''";
	private static final String USERAGENT = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.8.1.16) Gecko/20080702 Firefox/2.0.0.16";
	private static final String COOKIE = "pspt=%7B%22id%22%3A%2234668%22%2C%22pswd%22%3A%228835d2c1351d221b4ab016fbf9e8253f%22%2C%22_code%22%3A%2289d23281a56814c9d00adb80163e2333%22%7D";
	List<BusinessInfoBean> BusinessInfoBeans = new ArrayList<BusinessInfoBean>();
	/*
	 * 参数工商注册号
	 * 返回null 查询出错
	 * param 关键字 param1 区域 param2 查询页
	 */
	public String query(String param,String param1,String param2) {
		BusinessInfoBeans.clear();
		if(StringUtils.isBlank(param2)){
			param2 = "1";
		}
		Document doc = this.init(param, param1, param2);
		//取第一页的数据
		//取得页数
		String countstr = doc.select("div[style=text-align:center;]").select("span.search-key").text();
		if(StringUtils.isNotBlank(countstr)){
			int page = Integer.parseInt(countstr)/10 +1;
			//if(page == 1){
				return binder.toJson(BusinessInfoBeans);
			/*}else if(page >1){
				for(int j=2;j<=2;j++){
					param2 = String.valueOf(j);
					doc = this.init(param, param1, param2);
				}
				return binder.toJson(BusinessInfoBeans);
			}*/
		}
		if(BusinessInfoBeans.size() > 0){
			return binder.toJson(BusinessInfoBeans);
		}
		return null;
	}

	private Document init(String param,String param1,String param2){
		String httpUrl = FinancialStableQueryService.HTTPURL;    
		Header[] headers = {new BasicHeader("User-Agent", FinancialStableQueryService.USERAGENT),
				new BasicHeader("Cookie", FinancialStableQueryService.COOKIE)}; 
		String[] params = {param,param2};
		String httpresp = HttpClientUtil.sendGet(MessageFormat.format(httpUrl, params), headers);
		Document doc = Jsoup.parse(httpresp);
		Elements elements  = doc.select("h3.site-list-title").select("a");
		if(elements.size() != 0){
			for(int i=0;i<elements.size();i++){
				String hrefval = elements.get(i).attr("href");
				if(StringUtils.isNotBlank(hrefval)){
					Pattern p = Pattern.compile("^http(s)?://(?<=//|)((\\w)+\\.)+\\w+");
			        Matcher m = p.matcher(FinancialStableQueryService.HTTPURL);
			        if(m.find()){
			        	String contentUrl = m.group()+hrefval;
			        	 this.getBasisInfo(contentUrl,param1);
			        }
				}
			}
		}else{
			String text = doc.select("script").html();
			String hrefval = null;
			if(StringUtils.isNotBlank(text)){
				hrefval = text.substring(15, text.length() -2);
			}
			if(StringUtils.isNotBlank(hrefval)){
					/*Pattern p = Pattern.compile("^http(s)?://(?<=//|)((\\w)+\\.)+\\w+");
			        Matcher m = p.matcher(BusinessInfoQueryService.HTTPURL);
			        if(m.find()){
			        	String contentUrl = m.group()+hrefval;*/
			        	this.getBasisInfo(hrefval,param1);
			    /*    }*/
				}
		}
		return doc;
	}
	
	private void getBasisInfo(String contentUrl,String district){
		Header[] headers = {new BasicHeader("User-Agent", FinancialStableQueryService.USERAGENT),
				new BasicHeader("Cookie", FinancialStableQueryService.COOKIE)}; 
		String httpresp = HttpClientUtil.sendGet(contentUrl, headers);
		Document doc = Jsoup.parse(httpresp);
		String companyName = doc.select("h3.detail-header-title").select("a").text();
		BusinessInfoBean businessInfoBean = new BusinessInfoBean();
		businessInfoBean.setName(companyName.trim().split(" ")[0]);
		Elements elements = doc.select("ul.company-info").select("li");
		if(elements.get(9).text().indexOf(district) != -1){
			for(int i=0;i<elements.size();i++){
				String text = "";
				if(i < 10){
					text = elements.get(i).text().split("：")[1].trim();
				}else if(i == 10){
					text = elements.get(i).text().substring(5).trim();
				}
				switch (i) {
					case 0:  businessInfoBean.setRegID(text); break;
					case 1:  businessInfoBean.setState(text); break;
					case 2:  businessInfoBean.setType(text); break;
					case 3:  businessInfoBean.setSetupDate(text);  break;
					case 4:  businessInfoBean.setLegalPerson(text); break;
					case 5:  businessInfoBean.setRegisteredCapital(text); break;
					case 6:  businessInfoBean.setOperatingPeriod(text); break;
					case 7:  businessInfoBean.setBureau(text); break;
					case 8:  businessInfoBean.setAwardDate(text); break;
					case 9:  businessInfoBean.setAddress(text.trim().split(" ")[0]);break;
					case 10: businessInfoBean.setScope(text);break;
				default:
					break;
				}
			}
			BusinessInfoBeans.add(businessInfoBean);
		}
	}

	public String getDishPersonsNum(String keys){
		Map<String, String> keysMap = null;
		try {
			keysMap = binder.getMapper().readValue(keys, new TypeReference<Map<String,String>>() {});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<String,String> numsMap = new LinkedHashMap<String, String>();
		String httpUrl = FinancialStableQueryService.DPHTTPURL;    
		Header[] headers = {new BasicHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.8.1.16) Gecko/20080702 Firefox/2.0.0.16"),
				new BasicHeader("Cookie", "pspt=%7B%22id%22%3A%2234205%22%2C%22pswd%22%3A%223ee21dcdf73505ef0474077db131bfad%22%2C%22_code%22%3A%2239ff8d782bebe929e0e422a962a28ef5%22%7D")}; 
		int index = 0;
		if(null != keysMap){
			for(Map.Entry<String, String> entry:keysMap.entrySet()){ 
				String httpresp = HttpClientUtil.sendGet(MessageFormat.format(httpUrl, entry.getValue()), headers);
				Document doc = Jsoup.parse(httpresp);
				String countstr = doc.select("div[style=text-align:center;]").select("span.search-key").text();
				if(StringUtils.isBlank(countstr)){
					countstr = "0";
				}
				numsMap.put(String.valueOf(index), countstr);
				index++;
			}   
			return binder.toJson(numsMap);
		}else{
			return null;
		}
	}
	
	public static void main(String[] args) {
		FinancialStableQueryService s = new FinancialStableQueryService();
		System.out.println(s.query("中鑫","嘉定",""));
	}
}
