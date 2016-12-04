package com.pactera.pds.u2.commerce.web.report;

import static com.pactera.pds.u2.commerce.utils.ConstantUtil.CS_CODE_MAP;
import static com.pactera.pds.u2.commerce.utils.ConstantUtil.CS_IDNUM_FLAG;
import static com.pactera.pds.u2.commerce.utils.ConstantUtil.STATUS_CODE_MAP;

import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;

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
import com.pactera.pds.u2.commerce.utils.MenuConstant;
import com.pactera.pds.u2.commerce.utils.Schedulee;

@RestController
@RequestMapping(value = "/api")
public class CSSyncSearchApiCotroller {

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

	@RequestMapping(value = "cssyncsearch")
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
		String mobile = paramsMap.get("mobile")[0].trim();
		String IDCardNum = paramsMap.get("IDCardNum")[0].trim();
		String mobile2 = paramsMap.get("mobile2")[0].trim();
		String workAdd = paramsMap.get("workAdd")[0].trim();
		String homeAdd = paramsMap.get("homeAdd")[0].trim();
		String caseID = String.valueOf(System.currentTimeMillis());
		String authID = paramsMap.get("authID")[0].trim();
		
		// Validate token
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
		
		//validate account balance.
		BCUser user = bcAccountService.findUserByUUID(token);
		if(!creditSearchService.enoughBalance(user.getInsCode(), MenuConstant.yunYinShangCSCode, false)){
			JSONObject rtnObj = new JSONObject();
        	JSONObject head = new JSONObject();
        	JSONObject body = new JSONObject();
        	head.put("statusCode", "YW003");
        	head.put("msg", STATUS_CODE_MAP.get("YW003"));
        	rtnObj.put("head", head);
        	rtnObj.put("body", body);
    		return rtnObj;
    	}

		
		JSONObject requestData = new JSONObject();

		requestData.put("s_shouji", mobile);
		requestData.put("s_shenfenID", IDCardNum);
		requestData.put("s_contact1", mobile2);
		requestData.put("s_add_baitian", workAdd);
		requestData.put("s_add_yewan", homeAdd);
		requestData.put("s_caseid", caseID);
		requestData.put("SQ", authID);
		
		JSONObject requestResultData = null;
		
		try {
			requestResultData = JSONObject.fromObject(CSHttpsUtil.syncQuery(requestData));
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
			resultHead.put("msg", STATUS_CODE_MAP.get(code));
			
			String realNameFlag = requestResultData.getString("i_shimin");
			String telAmount = requestResultData.getString("i_guhuashu");
			String IDNumFlag = requestResultData.getString("i_shenfenzhen");
			String onlineDay = requestResultData.getString("i_zaiwangshijian");
			String mobileAmount = requestResultData.getString("i_shoujishu");
			
			resultBody.put("real_name_match", CS_IDNUM_FLAG.get(IDNumFlag));
			
			resultBody.put("telephone_quantity", telAmount);
			
			resultBody.put("mobile_quantity", mobileAmount);
			
			resultBody.put("online_time", onlineDay);
			
			resultBody.put("asyn_query_id", caseID);
			
			BcHisYYSCS bcHisYYSCS = new BcHisYYSCS();
			
			bcHisYYSCS.setCellNum(mobile);
			bcHisYYSCS.setIdCardNum(IDCardNum);
			bcHisYYSCS.setContactNum(mobile2);
			bcHisYYSCS.setWorkingSite(workAdd);
			bcHisYYSCS.setHomeAddr(homeAdd);
			bcHisYYSCS.setAuthCode(authID);
			bcHisYYSCS.setAsynCode(caseID);
			bcHisYYSCS.setMatchLevel(realNameFlag);
			bcHisYYSCS.setMatchIdCard(IDNumFlag);
			bcHisYYSCS.setNetTime(onlineDay);
			bcHisYYSCS.setCellAccount(Integer.parseInt(mobileAmount));
			bcHisYYSCS.setFixedAccount(Integer.parseInt(telAmount));
			
			bcHisYYSCSService.saveBcHisYYSCS(bcHisYYSCS);
			
			creditSearchService.chargeCS(bcHisYYSCS, user.getInsCode());
			
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
	
private boolean validateParameters(Map<String, String[]> paramsMap, JSONObject result) {

		JSONObject resultBody = new JSONObject();
		JSONObject resultHead = new JSONObject();
		String errorCode = "YW002";
		StringBuilder errorMessage = new StringBuilder();
		int failedCount = 0;
		
		errorMessage.append(STATUS_CODE_MAP.get(errorCode) + ": ");
		String[] tokenArr = paramsMap.get("token");
		String[] mobileArr = paramsMap.get("mobile");
		String[] IDCardNumArr = paramsMap.get("IDCardNum");
		String[] mobile2Arr = paramsMap.get("mobile2");
		String[] workAddArr = paramsMap.get("workAdd");
		String[] homeAddArr = paramsMap.get("homeAdd");
		String[] authIDArr = paramsMap.get("authID");
		String token = null;
		String infoID = null;
		String mobile = null;
		String IDCardNum = null;
		String mobile2 = null;
		String authID = null;
		
		if (isNullOrEmptyArray(tokenArr)) {
			failedCount++;
			errorMessage.append("未发现token参数或为空, ");
		}
		else {
			token = String.valueOf(tokenArr[0]).trim();
		}
		
		
		if (isNullOrEmptyArray(mobileArr)) {
			failedCount++;
			errorMessage.append("未发现mobile参数或为空, ");
		}
		else {
			mobile = String.valueOf(mobileArr[0]).trim();
		}
		
		if (isNullOrEmptyArray(IDCardNumArr)) {
			failedCount++;
			errorMessage.append("未发现IDCardNum参数或为空, ");
		}
		else {
			IDCardNum = String.valueOf(IDCardNumArr[0]).trim();
		}
		
		
		if (isNullOrEmptyArray(authIDArr)) {
			failedCount++;
			errorMessage.append("未发现authID参数或为空, ");
		}
		else {
			authID = String.valueOf(authIDArr[0]).trim();
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
		
		if (!isNumeric(mobile) || mobile.length() != 11) {
			failedCount++;
			errorMessage.append("mobile格式错误, ");
		}
		
		
		if (isNullOrEmptyArray(mobile2Arr)) {

			mobile2 = String.valueOf(mobile2Arr[0]).trim();
			if (!isNumeric(mobile2) || mobile2.length() != 11) {
				failedCount++;
				errorMessage.append("mobile2格式错误, ");
			}

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

	private static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
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
