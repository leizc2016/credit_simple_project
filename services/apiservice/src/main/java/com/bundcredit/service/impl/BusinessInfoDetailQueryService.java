package com.bundcredit.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import org.apache.commons.lang.StringUtils;
import com.bundcredit.entity.BusinessInfoBean;
import com.bundcredit.entity.ChangeRecordBean;
import com.bundcredit.entity.PartnerInformationBean;
import com.bundcredit.service.IEnterpriseInfoDetailQueryService;
import com.bundcredit.utils.HttpRequest;
import com.bundcredit.utils.JsonBinder;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * @author libolin
 * 根据云聚数据的接口开发查询企业信息接口
 */
@WebService
public class BusinessInfoDetailQueryService implements IEnterpriseInfoDetailQueryService{
	private static JsonBinder binder = JsonBinder.buildNonDefaultBinder(); 
	private static final String  HTTPURL = "http://eci.yjapi.com/ECIFast/SearchExisting";
	private static final String  DETAILHTTPURL = "http://eci.yjapi.com/ECIFast/GetDetails";
	private static final String HTTPPARAM = "key="+BusinessInfoDetailQueryService.APPKEY+"&dtype=json&keyword={0}&exactlyMatch=true";
	private static final String DETAILPARAM = "key="+BusinessInfoDetailQueryService.APPKEY+"&keyNo={0}";
	private static final String APPKEY="ddfa3553e43a4f6891f1d394f0325f36";
	/*
	 * 参数工商注册号或公司名称
	 * null：查询无记录  -1 查询失败
	 */
	public String query(String param) {
        String result = HttpRequest.sendGet(HTTPURL, MessageFormat.format(HTTPPARAM, param));
		JSONObject resultJson = JSONObject.fromObject(result);
		String status = resultJson.get("Status").toString();
		if(StringUtils.isNotBlank(status)){
			if("200".equals(status)){
				//取得keyNo
				JSONArray array = resultJson.getJSONArray("Result");
				String keyNo = null;
				for (int i = 0; i < array.size(); i++) {
					JSONObject jo = (JSONObject) array.get(i);
					keyNo = (String) jo.get("KeyNo");
				}
				if(StringUtils.isNotBlank(keyNo)){
					//根据keyNO取得企业详细信息
					String detailResult = HttpRequest.sendGet(DETAILHTTPURL, MessageFormat.format(DETAILPARAM, keyNo));
					JSONObject dtlresultJson = JSONObject.fromObject(detailResult);
					String detailStatus = dtlresultJson.get("Status").toString();
					if(StringUtils.isNotBlank(detailStatus)){
						if("200".equals(detailStatus)){
							BusinessInfoBean businessInfoBean = this.getBusinessInfoDetailFromJson(detailResult);
							return binder.toJson(businessInfoBean);
						}else if("201".equals(detailStatus)){
							return null;
						}else{
							String message = dtlresultJson.get("Message").toString();
							try {
								throw new Exception(detailStatus + ":" + message);
							} catch (Exception e) {
								e.printStackTrace();
								return "-1";
							}
						}
					}
				}
			}else if("201".equals(status)){
				return null;
			}else{
				String message = resultJson.get("Message").toString();
				try {
					throw new Exception(status + ":" + message);
				} catch (Exception e) {
					e.printStackTrace();
					return "-1";
				}
			}
		}
		return null;
	}
	/**
	 * 得到企业详细信息
	 * @param detailResult
	 */
	private BusinessInfoBean getBusinessInfoDetailFromJson(String detailResult){
		JSONObject dtlresultJson = JSONObject.fromObject(detailResult);
		JSONObject jo = (JSONObject) dtlresultJson.get("Result");
		BusinessInfoBean businessInfoBean = new BusinessInfoBean();
		businessInfoBean.setRegID(jo.get("No") == null || "null".equals(jo.get("No"))? "": jo.get("No").toString());
		businessInfoBean.setName(jo.get("Name") == null || "null".equals(jo.get("Name").toString()) ? "": jo.get("Name").toString());
		businessInfoBean.setType(jo.get("EconKind") == null || "null".equals(jo.get("EconKind").toString())? "": jo.get("EconKind").toString());
		businessInfoBean.setLegalPerson(jo.get("OperName") == null || "null".equals(jo.get("OperName").toString())? "": jo.get("OperName").toString());
		businessInfoBean.setRegisteredCapital(jo.get("RegistCapi") == null || "null".equals(jo.get("RegistCapi").toString())? "": jo.get("RegistCapi").toString());
		businessInfoBean.setSetupDate(jo.get("StartDate") == null || "null".equals(jo.get("StartDate").toString())? "": jo.get("StartDate").toString().split("T")[0]);
		businessInfoBean.setAddress(jo.get("Address") == null || "null".equals(jo.get("Address").toString())? "": jo.get("Address").toString());
		businessInfoBean.setState(jo.get("Status") == null || "null".equals(jo.get("Status").toString())? "": jo.get("Status").toString());
		StringBuffer sbuffer = new StringBuffer();
		sbuffer.append(jo.get("TermStart") == null || "null".equals(jo.get("TermStart").toString())? "": jo.get("TermStart").toString().split("T")[0]);
		sbuffer.append(" 至 ");
		sbuffer.append(jo.get("TeamEnd") == null || "null".equals(jo.get("TeamEnd").toString())? "": jo.get("TeamEnd").toString().split("T")[0]);
		businessInfoBean.setOperatingPeriod(sbuffer.toString());
		businessInfoBean.setBureau(jo.get("BelongOrg") == null || "null".equals(jo.get("BelongOrg").toString())? "": jo.get("BelongOrg").toString());
		businessInfoBean.setAwardDate(jo.get("CheckDate") == null || "null".equals(jo.get("CheckDate").toString())? "": jo.get("CheckDate").toString().split("T")[0]);
		businessInfoBean.setScope(jo.get("Scope") == null || "null".equals(jo.get("Scope").toString())? "": jo.get("Scope").toString());
		//得到股东信息
		businessInfoBean = this.getPartners(businessInfoBean, jo);
		//得到变更信息
		businessInfoBean = this.getChangeRecords(businessInfoBean, jo);
		return businessInfoBean;
	}
	/**
	 * 得到股东信息
	 * @param businessInfoBean
	 * @param obj
	 */
	public BusinessInfoBean getPartners(BusinessInfoBean businessInfoBean,JSONObject obj){
		JSONArray array = obj.getJSONArray("Partners");
		List<PartnerInformationBean> partnerBeanLists = null;
		if(array.size() > 0){
			partnerBeanLists = new ArrayList<PartnerInformationBean>();
			for (int i = 0; i < array.size(); i++) {
				PartnerInformationBean partnerInformationBean = new PartnerInformationBean();
				JSONObject jo = (JSONObject) array.get(i);
				partnerInformationBean.setPartnerName(jo.get("StockName") == null || "null".equals(jo.get("StockName").toString())? "": jo.get("StockName").toString());
				partnerInformationBean.setPartnerType(jo.get("StockType") == null || "null".equals(jo.get("StockType").toString())? "": jo.get("StockType").toString());
				partnerInformationBean.setIdentifyType(jo.get("IdentifyType") == null || "null".equals(jo.get("IdentifyType").toString())? "": jo.get("IdentifyType").toString());
				partnerInformationBean.setIdentifyNo(jo.get("IdentifyNo") == null || "null".equals(jo.get("IdentifyNo").toString())? "": jo.get("IdentifyNo").toString());
				partnerInformationBean.setRealCapi(jo.get("RealCapi") == null || "null".equals(jo.get("RealCapi").toString())? "": jo.get("RealCapi").toString());
				partnerInformationBean.setCapiDate(jo.get("CapiDate") == null || "null".equals(jo.get("CapiDate").toString())? "": jo.get("CapiDate").toString());
				partnerBeanLists.add(partnerInformationBean);
			}
			if(null != partnerBeanLists && partnerBeanLists.size() > 0){
				businessInfoBean.setPartnerInformationBeans(partnerBeanLists);
			}
		}
		return businessInfoBean;
	}
	
	/**
	 *得到更新信息 
	 * @param businessInfoBean
	 * @param obj
	 */
	public BusinessInfoBean getChangeRecords(BusinessInfoBean businessInfoBean,JSONObject obj){
		JSONArray array = obj.getJSONArray("ChangeRecords");
		List<ChangeRecordBean> crecordsLists = null;
		if(array.size() > 0){
			crecordsLists = new ArrayList<ChangeRecordBean>();
			for (int i = 0; i < array.size(); i++) {
				ChangeRecordBean changeRecordsBean = new ChangeRecordBean();
				JSONObject jo = (JSONObject) array.get(i);
				changeRecordsBean.setProjectName(jo.get("ProjectName") == null || "null".equals(jo.get("ProjectName").toString())? "": jo.get("ProjectName").toString());
				changeRecordsBean.setBeforeContent(jo.get("BeforeContent") == null || "null".equals(jo.get("BeforeContent").toString())? "": jo.get("BeforeContent").toString());
				changeRecordsBean.setAfterContent(jo.get("AfterContent") == null || "null".equals(jo.get("AfterContent").toString())? "": jo.get("AfterContent").toString());
				changeRecordsBean.setChangeDate(jo.get("ChangeDate") == null || "null".equals(jo.get("ChangeDate").toString())? "": jo.get("ChangeDate").toString().split("T")[0]);
				crecordsLists.add(changeRecordsBean);
			}
			if(null != crecordsLists && crecordsLists.size() > 0){
				businessInfoBean.setChangeRecordsBeans(crecordsLists);
			}
		}
		return businessInfoBean;
	}
	
	public static void main(String[] args) {
		BusinessInfoDetailQueryService s = new BusinessInfoDetailQueryService();
		System.out.println(s.query("310115002482697"));
	}
}
