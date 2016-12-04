package com.bundcredit.service.impl;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import com.bundcredit.entity.BeExecutorBean;
import com.bundcredit.entity.DishPersonBean;
import com.bundcredit.service.IEnterpriseInfoQueryService;
import com.bundcredit.utils.HttpClientUtil;
import com.bundcredit.utils.JsonBinder;

//失信人与被执行人查询service
@SuppressWarnings("restriction")
@WebService
public class DishPersonAndBeExecutorQueryService implements IEnterpriseInfoQueryService{
	
	private static JsonBinder binder = JsonBinder.buildNonDefaultBinder(); 
	private static final String  HTTPURL = "http://qichacha.com/search?type=1&province=&key={0}&sstype={1}&p={2}";
	private List<BeExecutorBean> beExecutorBeans = new ArrayList<BeExecutorBean>();
	private List<DishPersonBean> dishPersonBeans = new ArrayList<DishPersonBean>();
	
	/*
	 * 参数公司名字、查询类型、页数默认传1
	 *返回0：参数传入错误 返回-1 查询出错
	 */
	public String query(String param,String param1,String param2) {
		beExecutorBeans.clear();
		dishPersonBeans.clear();
		String httpUrl = DishPersonAndBeExecutorQueryService.HTTPURL;    
		Header[] headers = {new BasicHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.8.1.16) Gecko/20080702 Firefox/2.0.0.16"),
				new BasicHeader("Cookie", "pspt=%7B%22id%22%3A%2234668%22%2C%22pswd%22%3A%228835d2c1351d221b4ab016fbf9e8253f%22%2C%22_code%22%3A%2289d23281a56814c9d00adb80163e2333%22%7D")}; 
		if(StringUtils.isNotBlank(param2)){
			param2 = "1";
		}
		String[] params = {param,param1,param2};
		String httpresp = HttpClientUtil.sendGet(MessageFormat.format(httpUrl, params), headers);
		/*try {
			httpresp = new String(httpresp.getBytes("iso-8859-1"), "gbk");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}*/
		Document doc = Jsoup.parse(httpresp);
		return this.queryByPage(doc, param,param1,param2);
	}
	
	private String queryByPage(Document doc,String param,String param1,String param2){
		String countstr = doc.select("div[style=text-align:center;]").select("span.search-key").text();
		int result = 0;
		if(StringUtils.isNotBlank(countstr)){
			int count = Integer.parseInt(countstr);
			int page = count/10 + 1;
			if(null == param1 || "".equals(param1)){
				result = getDishPersonInfo(doc);
				if(page > 1){
					for(int j=2;j<=page;j++){
						param2 = String.valueOf(j);
						result = getDishPersonInfoByPage(param,param1,param2);
					}
				}
				if(result == 1){
					return binder.toJson(dishPersonBeans) ;
				}else{
					return "-1";
				}
			}else if("1".equals(param1)){
				result = getBeExecutorInfo(doc);
				if(page > 1){
					for(int j=2;j<=page;j++){
						param2 = String.valueOf(j);
						result = getBeExecutorInfoByPage(param,param1,param2);
					}
				}
				if(result == 1){
					return binder.toJson(beExecutorBeans) ;
				}else{
					return "-1";
				}
			}else{
				return "0";
			}
		}
		return "-1";
	}
	
	//失信人分页查询
	private int getDishPersonInfoByPage(String param,String param1,String param2){
		String httpUrl = DishPersonAndBeExecutorQueryService.HTTPURL;    
		Header[] headers = {new BasicHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.8.1.16) Gecko/20080702 Firefox/2.0.0.16"),
				new BasicHeader("Cookie", "pspt=%7B%22id%22%3A%2234205%22%2C%22pswd%22%3A%223ee21dcdf73505ef0474077db131bfad%22%2C%22_code%22%3A%2239ff8d782bebe929e0e422a962a28ef5%22%7D")}; 
		String[] params = new String[]{param,param1,param2};
		String httpresp = HttpClientUtil.sendGet(MessageFormat.format(httpUrl, params), headers);
		Document doc = Jsoup.parse(httpresp);
		return getDishPersonInfo(doc);
	}
	
	//失信人信息查询
	private int getDishPersonInfo(Document doc){
		try {
			Elements trs = doc.select("div.modal-body").select("table.table").select("table.table-bordered");
			for(int i = 0;i<trs.size();i++){
				DishPersonBean dishPersonBean = new DishPersonBean();
	            Elements tds = trs.get(i).select("td");
	            for(int j = 0;j<tds.size();j++){
	            	String text = tds.get(j).text();
	            	switch ( j) {
					case 1: dishPersonBean.setName(text); break;
					case 3: dishPersonBean.setLegalPerson(text); break;
					case 5: dishPersonBean.setCode(text); break;
					case 7: dishPersonBean.setBasisDocNo(text); break;
					case 9: dishPersonBean.setCaseCode(text); break;
					case 11: dishPersonBean.setBasisDept(text); break;
					case 13: dishPersonBean.setDocContent(text); break;
					case 15: dishPersonBean.setExecStatus(text); break;
					case 17: dishPersonBean.setExecCourt(text); break;
					case 19: dishPersonBean.setProvince(text); break;
					case 21: dishPersonBean.setFilingTime(text); break;
					case 23: dishPersonBean.setPublishDate(text); break;
					default:
						break;
					}
	            }
	        	dishPersonBeans.add(dishPersonBean);
	        }
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
			
		}
	}
	
	//被执行人查询
	private int getBeExecutorInfoByPage(String param,String param1,String param2){
		String httpUrl = DishPersonAndBeExecutorQueryService.HTTPURL;    
		Header[] headers = {new BasicHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.8.1.16) Gecko/20080702 Firefox/2.0.0.16"),
				new BasicHeader("Cookie", "pspt=%7B%22id%22%3A%2234205%22%2C%22pswd%22%3A%223ee21dcdf73505ef0474077db131bfad%22%2C%22_code%22%3A%2239ff8d782bebe929e0e422a962a28ef5%22%7D")}; 
		String[] params = new String[]{param,param1,param2};
		String httpresp = HttpClientUtil.sendGet(MessageFormat.format(httpUrl, params), headers);
		Document doc = Jsoup.parse(httpresp);
		return getBeExecutorInfo(doc);
	}
	
	//被执行人信息查询
	private int getBeExecutorInfo(Document doc){
		try {
			Elements trs = doc.select("div.modal-body").select("table.table").select("table.table-bordered");
			for(int i = 0;i<trs.size();i++){
				BeExecutorBean beExecutorBean = new BeExecutorBean();
	            Elements tds = trs.get(i).select("td");
	            for(int j = 0;j<tds.size();j++){
	            	String text = tds.get(j).text();
	            	switch ( j) {
					case 1: beExecutorBean.setName(text); break;
					case 3: beExecutorBean.setExecCourt(text); break;
					case 5: beExecutorBean.setCode(text); break;
					case 7: beExecutorBean.setFilingTime(text); break;
					case 9: beExecutorBean.setCaseCode(text); break;
					case 11: beExecutorBean.setExecutiveSubject(text); break;
					default:
						break;
					}
	            }
	            beExecutorBeans.add(beExecutorBean);
	        }
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	public static void main(String[] args) {
		DishPersonAndBeExecutorQueryService s = new DishPersonAndBeExecutorQueryService();
		System.out.println(s.query("江苏元通能源发展有限公司","1","1"));
	}
}
