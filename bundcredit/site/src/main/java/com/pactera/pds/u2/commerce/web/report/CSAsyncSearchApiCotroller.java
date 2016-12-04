package com.pactera.pds.u2.commerce.web.report;

import static com.pactera.pds.u2.commerce.utils.ConstantUtil.BILL_AMOUNT_MAP;
import static com.pactera.pds.u2.commerce.utils.ConstantUtil.BILL_DATA_MAP;
import static com.pactera.pds.u2.commerce.utils.ConstantUtil.CS_CODE_MAP;
import static com.pactera.pds.u2.commerce.utils.ConstantUtil.MATCH_FLAG_MAP;
import static com.pactera.pds.u2.commerce.utils.ConstantUtil.RECENT_CALL_FREQUENCY_MAP;
import static com.pactera.pds.u2.commerce.utils.ConstantUtil.STATUS_CODE_MAP;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pactera.pds.u2.commerce.entity.BcHisYYSCS;
import com.pactera.pds.u2.commerce.entity.BcProviderLog;
import com.pactera.pds.u2.commerce.entity.mybatis.BCUser;
import com.pactera.pds.u2.commerce.service.account.BCAccountService;
import com.pactera.pds.u2.commerce.service.bchis.BcHisYYSCSService;
import com.pactera.pds.u2.commerce.service.bcproviderlog.BcProviderLogService;
import com.pactera.pds.u2.commerce.service.car.CreditSearchService;
import com.pactera.pds.u2.commerce.utils.CSHttpsUtil;
import com.pactera.pds.u2.commerce.utils.MobileEmailValidateUtil;
import com.pactera.pds.u2.commerce.utils.Schedulee;

@RestController
@RequestMapping(value = "/api")
public class CSAsyncSearchApiCotroller {

	@Autowired
    private Schedulee schedulee;
	@Autowired
    private BCAccountService bcAccountService;
	@Autowired
	private CreditSearchService creditSearchService;
    @Autowired
    private BcProviderLogService bcProviderLogService;
    @Autowired
    private BcHisYYSCSService bcHisYYSCSService;

	@RequestMapping(value = "csasyncsearch")
	public String searchPhone(ServletRequest request) {
		
		Map<String, String[]> ParamsMap = request.getParameterMap();
		
		JSONObject result = doQuery(ParamsMap);
		return result.toString();
	}
	
	private JSONObject doQuery(Map<String, String[]> paramsMap) {
		
		JSONObject result = new JSONObject();
		// Validate Parameters
		if (!validateParameters(paramsMap, result)) {
			return result;
		}
		JSONObject resultBody = new JSONObject();
		JSONObject resultHead = new JSONObject();
		
		String token = paramsMap.get("token")[0].trim();
		String caseID = paramsMap.get("asyn_query_id")[0].trim();
		
		
		if (isTokenExpired(token)) {
			JSONObject rtnObj = new JSONObject();
			JSONObject head = new JSONObject();
			JSONObject body = new JSONObject();
			head.put("statusCode", "YW001");
			head.put("msg", STATUS_CODE_MAP.get("YW001"));
			rtnObj.put("head", head);
			rtnObj.put("body", body);
			return rtnObj;
		}
		
		BCUser user = bcAccountService.findUserByUUID(token);
		if(!validateRole(user)){
			JSONObject rtnObj = new JSONObject();
			rtnObj.put("status", "10003");
			rtnObj.put("msg", "查询失败，该用户无权限");
			return rtnObj;
		}
		
//		String caseID = insCode + paramsMap.get("authID")[0].trim();
		
		JSONObject requestData = new JSONObject();
//		requestData.put("s_shouji", mobile);
		requestData.put("s_caseid", caseID);
		
		JSONObject requestResultData = null;
		
		try {
			requestResultData = JSONObject.fromObject(CSHttpsUtil.asyncQuery(requestData));
		} catch (Exception e) {
			JSONObject rtnObj = new JSONObject();
        	JSONObject head = new JSONObject();
        	JSONObject body = new JSONObject();
        	head.put("statusCode", "YW006");
        	head.put("msg", STATUS_CODE_MAP.get("YW006"));
        	rtnObj.put("head", head);
        	rtnObj.put("body", body);
    		return rtnObj;
		}
		
		if (StringUtils.isEmpty(requestResultData)) {
			JSONObject rtnObj = new JSONObject();
        	JSONObject head = new JSONObject();
        	JSONObject body = new JSONObject();
        	head.put("statusCode", "YW006");
        	head.put("msg", STATUS_CODE_MAP.get("YW006"));
        	rtnObj.put("head", head);
        	rtnObj.put("body", body);
    		return rtnObj;
		}
		
		
		//Save provider log
		saveProviderlog(requestData, requestResultData, user);
		
		String code = requestResultData.getString("code");
		
		if (("0").equals(code)){
			resultHead.put("statusCode", "YW000");
			resultHead.put("msg", STATUS_CODE_MAP.get("YW000"));
			
			String homeAddFlag = requestResultData.getString("i_yewan");
			String workAddFlag = requestResultData.getString("i_baitian");
			JSONArray callNums = requestResultData.getJSONArray("lianxi1");
			JSONArray billAmount = requestResultData.getJSONArray("zhangdan_6");
			JSONArray internetData = requestResultData.getJSONArray("liuliang_6");
			
			resultBody.put("home_add_match", MATCH_FLAG_MAP.get(homeAddFlag));
			
			resultBody.put("work_add_match", MATCH_FLAG_MAP.get(workAddFlag));
			
			resultBody.put("recent_call_frequency", handleJsonArray(callNums, RECENT_CALL_FREQUENCY_MAP));
			
			resultBody.put("bill_amount", handleJsonArray(billAmount, BILL_AMOUNT_MAP));
			
			resultBody.put("bill_data", handleJsonArray(internetData, BILL_DATA_MAP));
			
			BcHisYYSCS bcHisYYSCS = bcHisYYSCSService.getBcHisYYSCSByAsynCode(caseID);
			
			bcHisYYSCS.setAsynData(requestResultData.toString());
			
			bcHisYYSCSService.updateBcHisYYSCS(bcHisYYSCS);
			
			String description = "运营商API异步查询：" + MobileEmailValidateUtil.hideMobileMidNumber(bcHisYYSCS.getCellNum());
			creditSearchService.commitFee((float) 0, user.getInsCode(), true, description);
			
		} else {
			JSONObject rtnObj = new JSONObject();
        	JSONObject head = new JSONObject();
        	JSONObject body = new JSONObject();
        	
        	String finalCode = null;
        	
			if (CS_CODE_MAP.containsKey(code)) {
				finalCode = CS_CODE_MAP.get(code);
			} else {
				finalCode = "YW006";
			}
			
        	head.put("statusCode", finalCode);
        	head.put("msg", STATUS_CODE_MAP.get(finalCode));
        	
        	rtnObj.put("head", head);
        	rtnObj.put("body", body);
    		return rtnObj;
		}
	
		result.put("head", resultHead);
		result.put("body", resultBody);
		return result;
	}
	/**
     * 判断用户权限：
     * 只有管理员和报告查询角色可以执行查询操作
     * @param user
     * @return
     */
    private boolean validateRole(BCUser user) {
		// TODO Auto-generated method stub
    	if("creditreport".equals(user.getRoles())
    			|| "admin".equals(user.getRoles())){
    		return true;
    	}
    	
		return false;
	}
private boolean validateParameters(Map<String, String[]> paramsMap, JSONObject result) {
		JSONObject resultBody = new JSONObject();
		JSONObject resultHead = new JSONObject();
		String errorCode = "YW002";
		StringBuilder errorMessage = new StringBuilder();
		int failedCount = 0;
		
		errorMessage.append(STATUS_CODE_MAP.get(errorCode) + ": ");
		String[] tokenArr = paramsMap.get("token");
		String[] caseIDArr = paramsMap.get("asyn_query_id");
		
		if (isNullOrEmptyArray(tokenArr)) {
			failedCount++;
			errorMessage.append("未发现token参数或为空, ");
		}
		
		if (isNullOrEmptyArray(caseIDArr)) {
			failedCount++;
			errorMessage.append("未发现asyn_query_id参数或为空, ");
		}
		
		
		if (failedCount > 0) {
			resultHead.put("statusCode", errorCode);
			String errorMsg = errorMessage.toString().trim();
			errorMsg = errorMsg.substring(0, errorMsg.length()-1);
			resultHead.put("msg", errorMsg);
			result.put("head", resultHead);
			result.put("body", resultBody);
			return false;
		}
		
		
		return true;
		
	}

	private static String handleJsonArray(JSONArray jsonArray, Map<String, String> codeMap) {
		String[] arr = new String[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			if (codeMap.containsKey(jsonArray.getString(i))) {
			arr[i] = codeMap.get(jsonArray.getString(i));
			} else {
				arr[i] = jsonArray.getString(i);
			}
		}

		return Arrays.toString(arr);

	}
	
	private boolean isTokenExpired(String token){
		BCUser user = bcAccountService.findUserByUUID(token);
        long now = System.currentTimeMillis();
        if(user == null || now - user.getLastLoginTime().getTime() > schedulee.getDeadtime()){

    		return true;
        }
        user.setLastLoginTime(new Date());
		bcAccountService.updateuser(user);
		return false;
	}
	
	private boolean isNullOrEmptyArray(String[] myStringArray) {
		
		if (myStringArray == null || myStringArray.length < 1) {
			return true;
		}
		
		if (myStringArray[0] == null || myStringArray[0].trim().equals("")) {
			return true;
		}
		
		return false;
	}
	
	private void saveProviderlog(JSONObject request, JSONObject response, BCUser user){
		BcProviderLog bcProviderLog = new BcProviderLog();
		bcProviderLog.setQueryDate(new Date());
		
		bcProviderLog.setQueryCondition(request.toString());
		
		
		bcProviderLog.setQueryResult(response.toString());
		
		bcProviderLog.setProvider("CS");
		bcProviderLog.setOperator(user.getLoginName());
		bcProviderLogService.saveBcProviderLog(bcProviderLog);
	}
}
