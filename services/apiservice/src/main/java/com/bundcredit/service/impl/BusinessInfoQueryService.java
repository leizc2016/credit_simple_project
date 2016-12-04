package com.bundcredit.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.jws.WebService;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import com.bundcredit.entity.BusinessInfoBean;
import com.bundcredit.entity.PartnerInformationBean;
import com.bundcredit.service.IEnterpriseInfoQueryService;
import com.bundcredit.utils.HttpClientUtil;
import com.bundcredit.utils.JsonBinder;

//工商信息查询service
@WebService
public class BusinessInfoQueryService implements IEnterpriseInfoQueryService{
	
	private static JsonBinder binder = JsonBinder.buildNonDefaultBinder(); 
	private static final String  HTTPURL = "http://qichacha.com/search?key={0}&type=0";
	private static final String USERAGENT = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.8.1.16) Gecko/20080702 Firefox/2.0.0.16";
	private static final String COOKIE = "pspt=%7B%22id%22%3A%2234668%22%2C%22pswd%22%3A%228835d2c1351d221b4ab016fbf9e8253f%22%2C%22_code%22%3A%2289d23281a56814c9d00adb80163e2333%22%7D";
	/*
	 * 参数工商注册号
	 * 返回null 查询出错
	 */
	public String query(String param,String param1,String param2) {
		String content  = null;
		String httpUrl = BusinessInfoQueryService.HTTPURL;    
		Header[] headers = {new BasicHeader("User-Agent", BusinessInfoQueryService.USERAGENT),
				new BasicHeader("Cookie", BusinessInfoQueryService.COOKIE)}; 
		String httpresp = HttpClientUtil.sendGet(MessageFormat.format(httpUrl, param), headers);
		Document doc = Jsoup.parse(httpresp);
		String hrefval = doc.select("h3.site-list-title").select("a").attr("href");
		if(StringUtils.isBlank(hrefval)){
			String text = doc.select("script").html();
			if(StringUtils.isNotBlank(text) && text.indexOf("qichacha.com") != -1){
				hrefval = text.substring(15, text.length() -2);
			}
			if(StringUtils.isNotBlank(hrefval)){
			        content  = this.getBasisInfo(hrefval);
				}
		}else{
				Pattern p = Pattern.compile("^http(s)?://(?<=//|)((\\w)+\\.)+\\w+");
		        Matcher m = p.matcher(BusinessInfoQueryService.HTTPURL);
		        if(m.find()){
		        	String contentUrl = m.group()+hrefval;
		        	content  = this.getBasisInfo(contentUrl);
		        }
		}
		return content;
	}

	private String getBasisInfo(String contentUrl){
		Header[] headers = {new BasicHeader("User-Agent", BusinessInfoQueryService.USERAGENT),
				new BasicHeader("Cookie", BusinessInfoQueryService.COOKIE)}; 
		String httpresp = HttpClientUtil.sendGet(contentUrl, headers);
		Document doc = Jsoup.parse(httpresp);
		String companyName = doc.select("h3.detail-header-title").select("a").text();
		BusinessInfoBean businessInfoBean = new BusinessInfoBean();
		businessInfoBean.setName(companyName.trim().split(" ")[0]);
		Elements elements = doc.select("ul.company-info").select("li");
		for(int i=0;i<elements.size();i++){
			String title = null;
			String text = null;
			title = elements.get(i).text().split("：")[0].trim();
			text = elements.get(i).text().split("：")[1].trim();
			if(title.equals("经营范围")){
				text = elements.get(i).text().substring(5).trim();
			}
			if(StringUtils.isNotBlank(title) && StringUtils.isNotBlank(text)){
				if(title.equals("注册号")){
					businessInfoBean.setRegID(text);
				}else if(title.equals("经营状态")){
					businessInfoBean.setState(text);
				}else if(title.equals("公司类型")){
					businessInfoBean.setType(text);
				}else if(title.equals("成立日期")){
					businessInfoBean.setSetupDate(text);
				}else if(title.equals("法定代表")){
					businessInfoBean.setLegalPerson(text);
				}else if(title.equals("注册资本")){
					businessInfoBean.setRegisteredCapital(text);
				}else if(title.equals("营业期限")){
					businessInfoBean.setOperatingPeriod(text);
				}else if(title.equals("登记机关")){
					businessInfoBean.setBureau(text);
				}else if(title.equals("发照日期")){
					businessInfoBean.setAwardDate(text);
				}else if(title.equals("住所")){
					businessInfoBean.setAddress(text.substring(0, text.length()-4));
				}else if(title.equals("经营范围")){
					businessInfoBean.setScope(text);
				}
			}
		}
		//股东信息
		this.getPartnerInfo(doc,businessInfoBean);
		return binder.toJson(businessInfoBean);
	}
	
	private void getPartnerInfo(Document doc,BusinessInfoBean businessInfoBean){
		Elements elements = doc.select("div.detail-partner-list").select("div.height-auto").select("p:has(a)");
		Elements elements1 = doc.select("div.detail-partner-list").select("div.height-auto").select("p");
		List<PartnerInformationBean> partnerInformationBeans = new ArrayList<PartnerInformationBean>();
		for(int i=0;i<elements.size();i++){
			PartnerInformationBean partnerInformationBean = new PartnerInformationBean();
			partnerInformationBean.setPartnerName(elements.get(i).text());
			//partnerInformationBean.setPartnerType(elements1.get(1 +2*i).text());
			partnerInformationBeans.add(partnerInformationBean);
		}
		businessInfoBean.setPartnerInformationBeans(partnerInformationBeans);
	}
	
	public static void main(String[] args) {
		BusinessInfoQueryService s = new BusinessInfoQueryService();
		System.out.println(s.query("320381000024653","",""));
	}
}
