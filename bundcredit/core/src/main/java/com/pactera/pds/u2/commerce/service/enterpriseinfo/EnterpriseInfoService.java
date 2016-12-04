package com.pactera.pds.u2.commerce.service.enterpriseinfo;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Component;

import com.pactera.pds.u2.commerce.entity.enterpriseinfo.BeExecutorBean;
import com.pactera.pds.u2.commerce.entity.enterpriseinfo.BusinessInfoBean;
import com.pactera.pds.u2.commerce.entity.enterpriseinfo.CourtAnnouncementBean;
import com.pactera.pds.u2.commerce.entity.enterpriseinfo.CourtJudgmentDocBean;
import com.pactera.pds.u2.commerce.entity.enterpriseinfo.DishPersonBean;
import com.pactera.pds.u2.commerce.entity.enterpriseinfo.OrgCodeBean;
import com.pactera.pds.u2.commerce.utils.JsonBinder;
import com.pactera.pds.u2.commerce.utils.PropertiesInfo;
import com.pactera.pds.u2.commerce.utils.WSClient;

@Component
public class EnterpriseInfoService {
	
	private static JsonBinder binder = JsonBinder.buildNonDefaultBinder(); 
	private static String URL=PropertiesInfo.getApiUrl();
	
	//private String URL=urlService.getUrl();
	
	private static final String BUSINESSINFOURL=URL+"apiservice/businessInfoQueryService?wsdl";
	private static final String APIBUSINESSINFOURL=URL+"apiservice/businessInfoDetailQueryService?wsdl";
	private static final String COURTANNOUNCEMENTURL=URL+"apiservice/courtAnnouncementQeuryService?wsdl";
	private static final String DISHPERSONANDBEEXECUTORURL=URL+"apiservice/dishPersonAndBeExecutorQueryService?wsdl";
	private static final String COURTJUDGMENTDOCURL=URL+"apiservice/courtJudgmentDocQeuryService?wsdl";
	private static final String ORGCODEURL=URL+"apiservice/orgCodeQueryService?wsdl";
	private static final String NAMESPACE="http://service.bundcredit.com/";
	private static final String METHOD="query";
	
	//工商信息
	public BusinessInfoBean getBusinessInfo(String regid){
		String result = WSClient.invokeWSClient(EnterpriseInfoService.BUSINESSINFOURL, EnterpriseInfoService.NAMESPACE, EnterpriseInfoService.METHOD, new String[] {regid,"",""});
		BusinessInfoBean businessInfoBean = null;
		if(null != result){
			businessInfoBean = binder.fromJson(result, BusinessInfoBean.class);
		}
		return businessInfoBean;
	}
	
	//根据企查查api查询工商信息
	public String getBusinessInfoFromApi(String regid){
		String result = WSClient.invokeWSClient(EnterpriseInfoService.APIBUSINESSINFOURL, EnterpriseInfoService.NAMESPACE, EnterpriseInfoService.METHOD, new String[] {regid});
		return result;
	}
		
	//组织机构代码
	public OrgCodeBean getOrgCodeInfo(String companyName){
		String result = WSClient.invokeWSClient(EnterpriseInfoService.ORGCODEURL, EnterpriseInfoService.NAMESPACE, EnterpriseInfoService.METHOD, new String[] {companyName,"",""});
		OrgCodeBean orgCodeBean = null;
		if(null != result){
			orgCodeBean = binder.fromJson(result, OrgCodeBean.class);
		}
		return orgCodeBean;
	}
	
	//法院公告
	public List<CourtAnnouncementBean> getCourtAnnouncementInfo(String companyName){
		List<CourtAnnouncementBean> beanList = null;
		String result = WSClient.invokeWSClient(EnterpriseInfoService.COURTANNOUNCEMENTURL, EnterpriseInfoService.NAMESPACE, EnterpriseInfoService.METHOD, new String[] {companyName,"",""});
		try {
			if(null != result){
				beanList = binder.getMapper().readValue(result, new TypeReference<List<CourtAnnouncementBean>>() {});
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return beanList;
	}
	//法院裁判文书
	public CourtJudgmentDocBean getCourtJudgmentDocInfo(String caseCode){
		String result = WSClient.invokeWSClient(EnterpriseInfoService.COURTJUDGMENTDOCURL, EnterpriseInfoService.NAMESPACE, EnterpriseInfoService.METHOD, new String[] {caseCode,"",""});
		if(null != result){
			CourtJudgmentDocBean courtJudgmentDocBean = binder.fromJson(result, CourtJudgmentDocBean.class);
			return courtJudgmentDocBean;
		}
		return null;
	}
	//被执行人
	public List<BeExecutorBean> getBeExecutorBeanInfo(String name){
		List<BeExecutorBean> beExecutorBeans = null;
		String result = WSClient.invokeWSClient(EnterpriseInfoService.DISHPERSONANDBEEXECUTORURL, EnterpriseInfoService.NAMESPACE, EnterpriseInfoService.METHOD, new String[] {name,"1","1"});
		try {
			if(null != result  && !result.equals("-1")  ){
				beExecutorBeans = binder.getMapper().readValue(result, new TypeReference<List<BeExecutorBean>>() {});
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return beExecutorBeans;
	}
	//失信人
	public List<DishPersonBean> getDishPersonBeanInfo(String name){
		List<DishPersonBean> dishPersonBean = null;
		String result = WSClient.invokeWSClient(EnterpriseInfoService.DISHPERSONANDBEEXECUTORURL, EnterpriseInfoService.NAMESPACE, EnterpriseInfoService.METHOD, new String[] {name,"","1"});
		try {
			if(null != result && !("-1".equals(result))){
				dishPersonBean = binder.getMapper().readValue(result, new TypeReference<List<DishPersonBean>>() {});
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return dishPersonBean;
	}
	
	/**
	 * 根据身份证号查询失信人
	 * @param idCardNum
	 * @return
	 */
	public List<DishPersonBean> getDishPersonBeanInfoByIdCardNum(String idCardNum){
			List<DishPersonBean> dishPersonBean = null;
			String result = WSClient.invokeWSClient(EnterpriseInfoService.DISHPERSONANDBEEXECUTORURL, EnterpriseInfoService.NAMESPACE, EnterpriseInfoService.METHOD, new String[] {idCardNum,"","1"});
			try {
				if(null != result && !("-1".equals(result))){
					dishPersonBean = binder.getMapper().readValue(result, new TypeReference<List<DishPersonBean>>() {});
				}
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
			return dishPersonBean;
		}
	public static void main(String[] args) {
		EnterpriseInfoService EnterpriseInfoService =  new EnterpriseInfoService();
		List<DishPersonBean> dishPersonBean  = EnterpriseInfoService.getDishPersonBeanInfo("江苏宏安集团");
	//	System.out.println(courtJudgmentDocBean.getCaseCode());
		
	}
}
