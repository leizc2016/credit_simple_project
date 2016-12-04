package com.pactera.pds.u2.commerce.utils;

import static com.pactera.pds.u2.commerce.utils.ConstantUtil.*;
import net.sf.json.JSONObject;

import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

public class GEOAPIUtil {
	
	private static final String QUERY_URL = "http://118.26.142.81/civp/queryUserInfoByCID";
	private static final String AUTH_URL = "http://118.26.142.81/civp/queryAuthentication";
	
	private static JSONObject result = new JSONObject();
	private static JSONObject resultBody = new JSONObject();
	private static JSONObject resultHead = new JSONObject();
	
	public static JSONObject query(JSONObject insJson) throws Exception {
		
		// Prepare parameters.
		JSONObject queryJosn = new JSONObject();
		JSONObject queryJosnHead = new JSONObject();
		JSONObject queryJosnBody = new JSONObject();
		queryJosnHead.put("token", getToken());
		queryJosnBody.put("queryID", "XX");
		
		queryJosnBody.put("CID", insJson.getString("CID"));
		queryJosnBody.put("CIDName", insJson.getString("CIDName"));
		queryJosnBody.put("CID2", insJson.getString("CID2"));
		queryJosnBody.put("authID", insJson.getString("authID"));
		queryJosnBody.put("operatorID", "10001");
		queryJosnBody.put("userRegID", "1012");
		
		queryJosn.put("head", queryJosnHead);
		queryJosn.put("body", queryJosnBody);
		
		// infoID = 1
		queryMatchable(queryJosnHead, queryJosnBody);
		
		// infoID = 2
		queryOnlineTime(queryJosnHead, queryJosnBody);
		
		// infoID = 3
		queryConsLevel(queryJosnHead, queryJosnBody);
		
		// infoID = 4
		queryLocation(queryJosnHead, queryJosnBody);
		
		// infoID = 5
		queryCallFreq(queryJosnHead, queryJosnBody);
		
		result.put("head", resultHead);
		result.put("body", resultBody);
		return result;
		
	}
	
	private static void queryMatchable(JSONObject queryJosnHead, JSONObject queryJosnBody) throws Exception {
		
		String queryResult = postQuery("1", queryJosnHead, queryJosnBody);
		
		JSONObject decryptJson = JSONObject.fromObject(queryResult);
		
		JSONObject head = decryptJson.getJSONObject("head");
		JSONObject body = decryptJson.getJSONObject("body");
		String errorCode = head.getString("errorCode");
//		String errorMsg = head.getString("errorMsg");
		String resultData = null;
		if (errorCode.equals("10005")) {
			resultData = body.getString("resultData");
		}
		
		resultHead.put("statusCode", GEO_CODE_MAP.get(errorCode));
		resultHead.put("msg", STATUS_CODE_MAP.get(GEO_CODE_MAP.get(errorCode)));
		if (errorCode.equals("10005") && GEO_MATCH_FLAG_MAP.containsKey(resultData)) {
			resultBody.put("real_name_match", GEO_MATCH_FLAG_MAP.get(resultData));
		} else {
			resultBody.put("real_name_match", "暂无数据");
		}
	}
	
	private static void queryOnlineTime(JSONObject queryJosnHead, JSONObject queryJosnBody) throws Exception {
			
			String queryResult = postQuery("2", queryJosnHead, queryJosnBody);
			
			JSONObject decryptJson = JSONObject.fromObject(queryResult);
			
			JSONObject head = decryptJson.getJSONObject("head");
			JSONObject body = decryptJson.getJSONObject("body");
			String errorCode = head.getString("errorCode");
	//		String errorMsg = head.getString("errorMsg");
			String resultData = null;
			if (errorCode.equals("10005")) {
				resultData = body.getString("resultData");
			}
			
			if (errorCode.equals("10005") && GEO_ONLINE_TIME_MAP.containsKey(resultData)) {
				resultBody.put("online_time", GEO_ONLINE_TIME_MAP.get(resultData));
			} else {
				resultBody.put("online_time", "暂无数据");
			}
		}
	
	private static void queryConsLevel(JSONObject queryJosnHead, JSONObject queryJosnBody) throws Exception {
		
		String queryResult = postQuery("3", queryJosnHead, queryJosnBody);
		
		JSONObject decryptJson = JSONObject.fromObject(queryResult);
		
		JSONObject head = decryptJson.getJSONObject("head");
		JSONObject body = decryptJson.getJSONObject("body");
		String errorCode = head.getString("errorCode");
	//	String errorMsg = head.getString("errorMsg");
		String resultData = null;
		if (errorCode.equals("10005")) {
			resultData = body.getString("resultData");
		}
		
		if (errorCode.equals("10005") && GEO_CONS_LEVEL_MAP.containsKey(resultData)) {
			resultBody.put("consumption_level", GEO_CONS_LEVEL_MAP.get(resultData));
		} else {
			resultBody.put("consumption_level", "暂无数据");
		}
	}
	
	private static void queryLocation(JSONObject queryJosnHead, JSONObject queryJosnBody) throws Exception {
		
		String queryResult = null;
		queryResult = postQuery("4", queryJosnHead, queryJosnBody);

		
		JSONObject decryptJson = JSONObject.fromObject(queryResult);
		
		JSONObject head = decryptJson.getJSONObject("head");
		JSONObject body = decryptJson.getJSONObject("body");
		String errorCode = head.getString("errorCode");
	//	String errorMsg = head.getString("errorMsg");
		String resultData = null;
		if (errorCode.equals("10005")) {
			resultData = body.getString("resultData");
		}
		
		String[] resultArray = StringUtils.split(resultData, " ");
		if (resultArray!=null && errorCode.equals("10005")) {
			for (int i = 0; i < resultArray.length; i++) {
				String status = resultArray[i].substring(resultArray[i].length() - 1);
				if ("1".equals(status)) {
					resultBody.put("loc_worktime", resultArray[i].substring(0, resultArray[i].length() - 2));
				}
				if ("2".equals(status)) {
					resultBody.put("loc_resttime", resultArray[i].substring(0, resultArray[i].length() - 2));
				}
			}
		} else {
			resultBody.put("loc_worktime", "暂无数据");
			resultBody.put("loc_resttime", "暂无数据");
		}
	}
	
	private static void queryCallFreq(JSONObject queryJosnHead, JSONObject queryJosnBody) throws Exception {
		
		String queryResult = postQuery("5", queryJosnHead, queryJosnBody);
		
		JSONObject decryptJson = JSONObject.fromObject(queryResult);
		
		JSONObject head = decryptJson.getJSONObject("head");
		JSONObject body = decryptJson.getJSONObject("body");
		String errorCode = head.getString("errorCode");
	//	String errorMsg = head.getString("errorMsg");
		String resultData = null;
		if (errorCode.equals("10005")) {
			resultData = body.getString("resultData");
		}
		
		if (errorCode.equals("10005") && GEO_CALL_FREQUENCY_MAP.containsKey(resultData)) {
			resultBody.put("call_frequency_level", GEO_CALL_FREQUENCY_MAP.get(resultData));
		} else {
			resultBody.put("call_frequency_level", "暂无数据");
		}
	}
	
	private static String postQuery(String infoID, JSONObject queryJosnHead, JSONObject queryJosnBody) throws Exception {
		
		JSONObject queryJson = new JSONObject();
		queryJosnBody.put("infoID", infoID);
		queryJson.put("head", queryJosnHead);
		queryJson.put("body", queryJosnBody);
		
		String dataStr = GEODesUtil.encrypt(queryJson.toString());
		RestTemplate restTemplate = new RestTemplate();
		String resultJson = restTemplate.postForObject(
				QUERY_URL,
				dataStr, String.class);
		System.out.println(GEODesUtil.decrypt(resultJson));
		return GEODesUtil.decrypt(resultJson);
		
	}
	
	private static String getToken() throws Exception{
		String data = "{'head': {'userName': 'shbundcredit','password': 'BA78952FAE730594BDC36C6779B3C234'},'body': {}}";
		String dataStr = null;
		try {
			dataStr = GEODesUtil.encrypt(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		RestTemplate restTemplate = new RestTemplate();
		String output = restTemplate.postForObject(
				AUTH_URL,
				dataStr, String.class);
		JSONObject requestData = JSONObject.fromObject(GEODesUtil.decrypt(output));
		if(!validateToken(requestData)){
			return null;
		}
		JSONObject head = (JSONObject)requestData.get("head");
		return head.getString("token");
	}
	
	private static boolean validateToken(JSONObject requestData) {
		if(!requestData.containsKey("head")){
			return false;
		}
		JSONObject head = (JSONObject)requestData.get("head");
		if(!head.containsKey("errorCode")){
			return false;
		}
		String code = (String)head.get("errorCode");
		if(!"10004".equals(code)){
			return false;
		}
		return true;
	}
}
