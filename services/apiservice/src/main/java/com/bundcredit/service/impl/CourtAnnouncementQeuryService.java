package com.bundcredit.service.impl;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jws.WebService;

import org.apache.commons.lang.StringUtils;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;

import com.bundcredit.entity.CourtAnnouncementBean;
import com.bundcredit.service.IEnterpriseInfoQueryService;
import com.bundcredit.utils.HtmlParserHelper;
import com.bundcredit.utils.HttpRequest;
import com.bundcredit.utils.JsonBinder;

//法院公告查询webservice
@WebService
public class CourtAnnouncementQeuryService implements IEnterpriseInfoQueryService{
	
	private static JsonBinder binder = JsonBinder.buildNonDefaultBinder();  
	private static final String url="http://www.live.chinacourt.org/fygg/result.shtml";
	private static final String invokeType = "GET";
	private static final String contentTagName = "div";
	private static final String attrName = "class";
	private static final String contentAttrValue ="dsrnr";
	
	//返回 -1:参数错误 -2查询公告详情报错  -3:查询公告主信息出错
	public String query(String param,String param1,String param2){
		String content = null;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String endDate = format.format(new Date());
		String urlparam = "kind_id=&party={0}&puter=&reg_date_end=" + endDate + "&content=";
		if(StringUtils.isNotBlank(invokeType)){
			if(invokeType.equalsIgnoreCase("post")){
				content = HttpRequest.sendPost(url, MessageFormat.format(urlparam, param));
			}else if(invokeType.equalsIgnoreCase("get")){
				content = HttpRequest.sendGet(url, MessageFormat.format(urlparam, param));
			}else{
				return "-1";
			}
		}
		List<CourtAnnouncementBean> announcementBeans = new ArrayList<CourtAnnouncementBean>();
		NodeList nodeList = null;
		String hrefval = null;
		if(StringUtils.isNotBlank(content)){
			try {
				Parser parser = Parser.createParser(content, "utf-8");
				NodeFilter tableFilter = new NodeClassFilter(TableTag.class);
		        OrFilter lastFilter = new OrFilter();
		        lastFilter.setPredicates(new NodeFilter[] {tableFilter});
		            nodeList = parser.parse(lastFilter);
		            for (int i = 0; i <= nodeList.size(); i++) {
		                if (nodeList.elementAt(i) instanceof TableTag) {
		                    TableTag tag = (TableTag) nodeList.elementAt(i);
		                    TableRow[] rows = tag.getRows(); 
		                    for (int j = 0; j < rows.length; j++) {
		                        TableRow tr = (TableRow) rows[j]; 
		                        TableColumn[] td = tr.getColumns();
		                        CourtAnnouncementBean announcementBean =null;
		                        if(td.length != 0){
		                        	announcementBean = new CourtAnnouncementBean();
		                        }
		                        for (int k = 0; k < td.length; k++) {
		                        	if(announcementBean != null){
		                        		switch(k){
		                        			case 0:announcementBean.setAnnouncementType(td[k].toPlainTextString()); break;
		                        			case 1:announcementBean.setAnnouncement(td[k].toPlainTextString()); break;
		                        			case 2:announcementBean.setLitigant(td[k].toPlainTextString()); break;
		                        			case 3:announcementBean.setAnnouncementDate(td[k].toPlainTextString()); break;
		                        		}
		                        		//截取公告内容链接
		                        		if(k ==1){
		                        			 hrefval = td[k].toHtml().substring(td[k].toHtml().indexOf("f='")+3, td[k].toHtml().indexOf("l'") + 1);
		                        			 if(StringUtils.isNotBlank(hrefval)){
		                 						Pattern p = Pattern.compile("^http(s)?://(?<=//|)((\\w)+\\.)+\\w+");
		                 				        Matcher m = p.matcher(url);
		                 				        if(m.find()){
		                 				        	String contentUrl = m.group()+hrefval;
		                 				        	if(this.getContent(contentUrl, invokeType) != null){
		                 				        		announcementBean.setContent(this.getContent(contentUrl, invokeType).replaceAll("\t", ""));
		                 				        	}else{
		                 				        		return "-2";
		                 				        	}
		                 				        }
		                 					}
		                        		}
		                        	}
		                            
		                        } 
		                        if(announcementBean != null){
		                        	announcementBeans.add(announcementBean);
		                        }
		                    } 
		                }
		            } 
			} catch (Exception e) {
				e.printStackTrace();
				return "-3";
			}
		}
		return binder.toJson(announcementBeans);
	}

	/*//得到最终的查看公告内容的url
	private String getActualHrefVal(String hrefval){
		String[] hrefvals = hrefval.split("/");
		StringBuffer sbuffer = new StringBuffer();
		for(String val : hrefvals){
			if(hrefvals[hrefvals.length-1].equals(val)){
				val = "block" + val;
				sbuffer.append(val);
			}else{
				sbuffer.append(val);
				sbuffer.append("/");
			}
		}
		return sbuffer.toString();
	}*/
	
	private String getContent(String contentUrl,String invokeType) throws Exception{
		String content = null;	
		String announcementContent = null;
		if(StringUtils.isNotBlank(invokeType)){
			if(invokeType.equalsIgnoreCase("post")){
				content = HttpRequest.sendPost(contentUrl,"");
			}else if(invokeType.equalsIgnoreCase("get")){
				content = HttpRequest.sendGet(contentUrl, "");
			}else{
				return announcementContent;
			}
		}
		if(content != null){
			NodeList nodeList = HtmlParserHelper.getContentByTag(content, CourtAnnouncementQeuryService.contentTagName,  CourtAnnouncementQeuryService.attrName, CourtAnnouncementQeuryService.contentAttrValue);
			for (int i = 0; i < nodeList.size(); ++i) {  
				TagNode node  = (TagNode) nodeList.elementAt(i);  
				announcementContent = node.toPlainTextString();
			}  
		}
		return announcementContent;
	}
	
	public static void main(String[] args) {
		CourtAnnouncementQeuryService s = new CourtAnnouncementQeuryService();
		System.out.println(s.query("苏州市金诚担保有限公司","",""));
	}

}
