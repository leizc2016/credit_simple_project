package com.pactera.pds.u2.commerce.service.enterpriseinfo;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Component;

import com.pactera.pds.u2.commerce.entity.enterpriseinfo.BusinessInfoBean;
import com.pactera.pds.u2.commerce.utils.JsonBinder;
import com.pactera.pds.u2.commerce.utils.PropertiesInfo;
import com.pactera.pds.u2.commerce.utils.WSClient;

@Component
public class FinancialStableInfoService {
	
	private static String URL=PropertiesInfo.getApiUrl();
	
	private static JsonBinder binder = JsonBinder.buildNonDefaultBinder(); 
	private static final String STABLEURL=URL+"apiservice/financialStableQueryService?wsdl";
	private static final String NAMESPACE="http://service.bundcredit.com/";
	private static final String METHOD="query";
	private static final String METHOD1="getDishPersonsNum";
	
	
	//金融维稳查询
	public List<BusinessInfoBean> getFinancialStableInfo(String key,String district){
		String result = WSClient.invokeWSClient(FinancialStableInfoService.STABLEURL, FinancialStableInfoService.NAMESPACE, FinancialStableInfoService.METHOD, new String[] {key,district,""});
		if(StringUtils.isNotBlank(result)){
			List<BusinessInfoBean> beanList = null;
			try {
				beanList = binder.getMapper().readValue(result, new TypeReference<List<BusinessInfoBean>>() {});
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return beanList;
		}else{
			return null;
		}
	}
	
	//金融维稳查询
		public String getDishPersonsNum(String keys){
			String result = WSClient.invokeWSClient(FinancialStableInfoService.STABLEURL, FinancialStableInfoService.NAMESPACE, 
					FinancialStableInfoService.METHOD1, keys);
			return result;
		}
		
	public static void main(String[] args) {
		String[] aa = {"1","2"};
		String str = binder.toJson(aa);
		System.out.println(binder.toJson(aa));
		System.out.println(binder.fromJson(str, String.class).toString());
		/*FinancialStableInfoService EnterpriseInfoService =  new FinancialStableInfoService();
		List<BusinessInfoBean> businessInfoBeans  = EnterpriseInfoService.getFinancialStableInfo("小额贷款","嘉定区");
		System.out.println(binder.toJson(businessInfoBeans));*/
	}
}
