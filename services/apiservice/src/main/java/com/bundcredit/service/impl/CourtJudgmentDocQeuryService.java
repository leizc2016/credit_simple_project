package com.bundcredit.service.impl;

import java.text.MessageFormat;

import javax.jws.WebService;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import com.bundcredit.entity.CourtJudgmentDocBean;
import com.bundcredit.service.IEnterpriseInfoQueryService;
import com.bundcredit.utils.HttpRequest;
import com.bundcredit.utils.JsonBinder;

//法院裁判文书查询webservice
@WebService
public class CourtJudgmentDocQeuryService implements IEnterpriseInfoQueryService{
	
	private static JsonBinder binder = JsonBinder.buildNonDefaultBinder();  
	private static final String url="http://www.court.gov.cn/extension/simpleSearch.htm";
	private static final String invokeType = "GET";
	private static final String tableAttrValue ="tablestyle";
	private static final String trAttrValue ="tdbgs_odd";
	@Autowired
	private CourtJudgmentDocBean courtJudgmentDocBean;
	
	public String query(String param,String param1,String param2){
		String content = null;
		String urlparam = "keyword=&caseCode={0}&beginDate=2014-01-01&endDate=2015-09-18&adv=1&orderby=&order=";
		if(StringUtils.isNotBlank(invokeType)){
			if(invokeType.equalsIgnoreCase("post")){
				content = HttpRequest.sendPost(url, MessageFormat.format(urlparam, param));
			}else if(invokeType.equalsIgnoreCase("get")){
				content = HttpRequest.sendGet(url, MessageFormat.format(urlparam, param));
			}
		}
		if(StringUtils.isNotBlank(content)){
			Document doc = Jsoup.parse(content, "utf-8");
			Elements trs = doc.select("table." + tableAttrValue).select("tr." + trAttrValue);
	        for(int i = 0;i<trs.size();i++){
	            Elements tds = trs.get(i).select("td");
	            for(int j = 0;j<tds.size();j++){
	                String text = tds.get(j).text();
	                switch(j){
	        			case 1:courtJudgmentDocBean.setCourtName(text); break;
	        			case 2:courtJudgmentDocBean.setTitle(text); break;
	        			case 3:courtJudgmentDocBean.setCaseCode(text); break;
	        			case 4:courtJudgmentDocBean.setJudgeDate(text); break;
	                }
	                //取得判决书url
	                if(j == 2){
	                	  Elements elements = doc.select("a[title=" + text + "]");
	                	  courtJudgmentDocBean.setJudgmentDocUrl(elements.get(0).attr("href"));
	                }
	            }
	        }
		}
		if(null != courtJudgmentDocBean.getCaseCode()){
			return binder.toJson(courtJudgmentDocBean);
		}else{
			return null;
		}
	}

	public CourtJudgmentDocBean getCourtJudgmentDocBean() {
		return courtJudgmentDocBean;
	}

	public void setCourtJudgmentDocBean(CourtJudgmentDocBean courtJudgmentDocBean) {
		this.courtJudgmentDocBean = courtJudgmentDocBean;
	}
	
	public static void main(String[] args) {
		CourtJudgmentDocQeuryService s = new CourtJudgmentDocQeuryService();
		System.out.println(s.query("（2015）丰民初字第","",""));
	}
	
}
