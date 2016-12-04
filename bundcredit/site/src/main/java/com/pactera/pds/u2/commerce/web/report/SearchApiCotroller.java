package com.pactera.pds.u2.commerce.web.report;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pactera.pds.u2.commerce.entity.BcHisYYSGEO;
import com.pactera.pds.u2.commerce.entity.mybatis.BCUser;
import com.pactera.pds.u2.commerce.service.account.BCAccountService;
import com.pactera.pds.u2.commerce.service.bcproviderlog.BcProviderLogService;
import com.pactera.pds.u2.commerce.service.car.CreditSearchService;
import com.pactera.pds.u2.commerce.utils.GEOAPIUtil;
import com.pactera.pds.u2.commerce.utils.IdcardUtils;
import com.pactera.pds.u2.commerce.utils.MenuConstant;
import com.pactera.pds.u2.commerce.utils.Schedulee;

@RestController
@RequestMapping(value = "/creditreportApiBak")
public class SearchApiCotroller {

	@Autowired
    private Schedulee schedulee;
	/*@Autowired
	private DmpSearChService dmpSearchService;*/
	@Autowired
    private BCAccountService bcAccountService;
	@Autowired
	private BcProviderLogService bcProviderLogService;
	
	@Autowired
	private CreditSearchService creditSearchService;

	@RequestMapping(value = "IPS_SearchBak", method=RequestMethod.POST)
	public String searchPhone(ServletRequest request)  {
		
		Map<String, String[]> ParamsMap = request.getParameterMap();
		
		JSONObject result = doQuery(ParamsMap);
		return result.toString();
	}
	
	private JSONObject doQuery(Map<String, String[]> paramsMap)   {
		
		
		
		JSONObject result = new JSONObject();
		// Validate Parameters
		if (!validateParameters(paramsMap, result)) {
			return result;
		}
		
		String token = paramsMap.get("token")[0].trim();
		String fullname = paramsMap.get("fullname")[0].trim();
//		String fullname = new String(fullnameUnicode.getBytes("Unicode"),"UTF-16"); 
		String IDNum = paramsMap.get("idcardnum")[0].trim();
		String mobile = paramsMap.get("mobile")[0].trim();
		String mobile2 = paramsMap.get("mobile2")[0].trim();
		String workAdd = paramsMap.get("work_add")[0].trim();
		String homeAddArr = paramsMap.get("home_add")[0].trim();
		String authID = paramsMap.get("authID")[0].trim();
		
		// validate token
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
		
		if(!creditSearchService.enoughBalance(
				user.getInsCode(),
    			MenuConstant.yunYinShangGEOCode, 
    			false)){
//    		model.addAttribute("message_info", "查询失败，余额不足");
//    		return "/report/yunYinShangGEO";
			JSONObject rtnObj = new JSONObject();
        	JSONObject head = new JSONObject();
        	JSONObject body = new JSONObject();
        	head.put("statusCode", "YW003");
        	head.put("msg", STATUS_CODE_MAP.get("YW003"));
        	rtnObj.put("head", head);
        	rtnObj.put("body", body);
    		return rtnObj;
    	}
		
		// Prepare third-party parameters
		JSONObject requestData = new JSONObject();
		JSONObject requestResultData = null;
		requestData.put("CID", mobile);
		requestData.put("CIDName", fullname);
		requestData.put("CID2", mobile2);
		requestData.put("authID", authID);

		
		// Execute third-party query
		try {
			requestResultData = GEOAPIUtil.query(requestData);
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
		
		
		
		// Save third-party data to DB
		BcHisYYSGEO resu = new BcHisYYSGEO();
		//resu.setInfoid("ALL");
		resu.setCallNum(mobile);
		resu.setContactNum(mobile2);
		resu.setUserName(fullname);;
		resu.setAuthCode(authID);
		//resu.setSearchDate(new Date());
		
    	JSONObject requestResultBody = (JSONObject) requestResultData.get("body");
		
		resu.setIsMatch(requestResultBody.getString("real_name_match"));
		resu.setNetTime(requestResultBody.getString("online_time"));
		resu.setSpendingLevel(requestResultBody.getString("consumption_level"));
		//resu.setOffice(requestResultBody.getString("loc_worktime"));
		//resu.setRest(requestResultBody.getString("loc_resttime"));
		resu.setContactRate(requestResultBody.getString("call_frequency_level"));
		creditSearchService.saveGEOHis(resu);
		creditSearchService.chargeGEO(resu, user.getInsCode());
		
		bcProviderLogService.GEOlog(resu, user);
		
		return requestResultData;
	}
	/**
     * 判断数据上传用户权限：
     * 只有管理员和报告查询权限可以执行查询操作
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
		String[] fullnameArr = paramsMap.get("fullname");
		String[] IDNumArr = paramsMap.get("idcardnum");
		String[] mobileArr = paramsMap.get("mobile");
		String[] mobile2Arr = paramsMap.get("mobile2");
		String[] workAddArr = paramsMap.get("work_add");
		String[] homeAddArr = paramsMap.get("home_add");
		String[] authIDArr = paramsMap.get("authID");
		String token = null;
		String fullname = null;
		String IDNum = null;
		String mobile = null;
		String mobile2 = null;
		String workAdd = null;
		String homeAdd = null;
		String authID = null;
		
		if (isNullOrEmptyArray(tokenArr)) {
			failedCount++;
			errorMessage.append("未发现token参数或为空, ");
		}
		else {
			token = String.valueOf(tokenArr[0]).trim();
		}
		
		if (isNullOrEmptyArray(fullnameArr)) {
			failedCount++;
			errorMessage.append("未发现fullname参数或为空, ");
		} else {
			fullname = String.valueOf(fullnameArr[0]).trim();
		}
		
		if (isNullOrEmptyArray(IDNumArr)) {
			failedCount++;
			errorMessage.append("未发现idcardnum参数或为空, ");
		} else {
			IDNum = String.valueOf(IDNumArr[0]).trim();
		}		
		
		if (isNullOrEmptyArray(mobileArr)) {
			failedCount++;
			errorMessage.append("未发现mobile参数或为空, ");
		}
		else {
			mobile = String.valueOf(mobileArr[0]).trim();
		}
		
		
		if (isNullOrEmptyArray(mobile2Arr)) {
			failedCount++;
			errorMessage.append("未发现mobile2参数或为空, ");
		}
		else {
			mobile2 = String.valueOf(mobile2Arr[0]).trim();
		}
		
		if (isNullOrEmptyArray(workAddArr)) {
			failedCount++;
			errorMessage.append("未发现work_add参数或为空, ");
		}
		else {
			workAdd = String.valueOf(workAddArr[0]).trim();
		}
		
		if (isNullOrEmptyArray(homeAddArr)) {
			failedCount++;
			errorMessage.append("未发现home_add参数或为空, ");
		}
		else {
			homeAdd = String.valueOf(homeAddArr[0]).trim();
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
		
		if (!isNumeric(mobile2) || mobile2.length() != 11) {
			failedCount++;
			errorMessage.append("mobile2格式错误, ");
		}
		
		if (!IdcardUtils.validateCard(IDNum)) {
			failedCount++;
			errorMessage.append("idcardnum格式错误, ");
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
	
//	private  Boolean isNumFromRightPlace(String mobile){
//		String url = "http://v.showji.com/Locating/"
//	               + "showji.com20150416273007.aspx"
//				   + "?m="+mobile+"&output=json";
//		RestTemplate restTemplate = new RestTemplate();
//		String output = restTemplate.getForObject(url, String.class);
////		System.out.print(output);
//		JSONObject requestData = JSONObject.fromObject(output);
//		Boolean flag = false;
//		String provider = (String)requestData.get("Corp");
//		if("中国移动".equals(provider)){
//			flag = avalaibleNum2provinces4mobile.contains(requestData.get("Province"));
//		}else if("中国电信".equals(provider)){
//			flag = avalaibleNum2provinces4telcom.contains(requestData.get("Province"));
//		}else if("中国联通".equals(provider)){
//			flag = avalaibleNum2provinces4union.contains(requestData.get("Province"));
//		}
////		{"Mobile":"18018608288","QueryResult":"True","TO":"中国电信","Corp":"中国电信","Province":"上海","City":"上海","AreaCode":"021","PostCode":"200000","VNO":"","Card":""}
//		System.out.print(flag);
//		return flag;
//	}
	
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
}
