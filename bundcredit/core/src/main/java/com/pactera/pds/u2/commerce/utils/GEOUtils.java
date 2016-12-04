package com.pactera.pds.u2.commerce.utils;

import java.util.Date;

import net.sf.json.JSONObject;

import org.springframework.web.client.RestTemplate;

import com.pactera.pds.u2.commerce.entity.BcHisYYSGEO;

public class GEOUtils {

	public static String getToken() {
		String data = "{'head': {'userName': 'shbundcredit','password': 'BA78952FAE730594BDC36C6779B3C234'},'body': {}}";
		String dataStr = null;
		try {
			dataStr = GEODesUtil.encrypt(data);
			RestTemplate restTemplate = new RestTemplate();
			String output = restTemplate.postForObject(
					"http://118.26.142.81/civp/queryAuthentication", dataStr,
					String.class);
			JSONObject requestData = JSONObject.fromObject(GEODesUtil
					.decrypt(output));
			if (!validateToken(requestData)) {
				return null;
			}
			JSONObject head = (JSONObject) requestData.get("head");
			return head.getString("token");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
	
	public static JSONObject requestObj(String cellNum, String authCode,
			String cellOwner, String contactCell) {
		JSONObject requestData = new JSONObject();
		requestData.put("CID", cellNum);
		requestData.put("authID", authCode);
		requestData.put("CIDName", cellOwner);
		requestData.put("CID2", contactCell);
		
		JSONObject queryJosn = new JSONObject();
		JSONObject queryJosnHead = new JSONObject();
		JSONObject queryJosnBody = new JSONObject();
		queryJosnHead.put("token", GEOUtils.getToken());
		queryJosnBody.put("queryID", "XX");
		
		queryJosnBody.put("infoID", "1");
		queryJosnBody.put("CID", cellNum);
	    queryJosnBody.put("CIDName", cellOwner);
		queryJosnBody.put("CID2", contactCell);
		queryJosnBody.put("authID", authCode);
//		queryJosnBody.put("infoID", "1");
//		queryJosnBody.put("CID", "18121070687");
//		queryJosnBody.put("CIDName", "张岩");
//		queryJosnBody.put("CID2", "18916921230");
//		queryJosnBody.put("authID", "10086");
		queryJosnBody.put("operatorID", "10001");
		queryJosnBody.put("userRegID", "1012");
		
		queryJosn.put("head", queryJosnHead);
		queryJosn.put("body", queryJosnBody);
		
		return queryJosn;
	}

	public static void setInfoID(JSONObject queryJosn, Integer infoID) {
		// TODO Auto-generated method stub
		JSONObject head = (JSONObject)queryJosn.get("body");
		head.put("infoID", infoID.toString());
	}

	public static String invokeGEO(JSONObject queryJosn) throws Exception {
		String queryStr = GEODesUtil.encrypt(queryJosn.toString());
		RestTemplate restTemplate = new RestTemplate();
		String resultJson = restTemplate.postForObject(
				"http://118.26.142.81/civp/queryUserInfoByCID",
				queryStr, String.class);
		resultJson = GEODesUtil.decrypt(resultJson);
		return resultJson;
	}
	
	/*public static DmpSearch dmpQuery() {
		DmpSearch geo = new DmpSearch();
		
		String resultData = "1";
		
		geo.setMatch(Constant.nameMap.get(resultData));
		
		geo.setTime(Constant.timeInNet.get(resultData));
		
		geo.setConsumption(Constant.consumeClass.get(resultData));
				
		
		geo.setOffice("南京路步行街");

		geo.setRest("东方绿洲");


		geo.setRate(Constant.howOftenComunication.get(resultData));

		geo.setCid("15923456789");
		geo.setCid2("18923456789");
		geo.setCidname("张飞");
		geo.setAuthid("0000");
		geo.setSearchDate(new Date());
		geo.setInfoid("0");
		
		return geo;
	}*/
	
	
	/**
	 * 赋默认值
	 * @param bcHisYYSGEO
	 * @return
	 */
	public static BcHisYYSGEO getDefaultBcHisYYSGEO(BcHisYYSGEO bcHisYYSGEO) {
        
        String resultData = "1";
        String authCode=bcHisYYSGEO.getAuthCode();
        
        bcHisYYSGEO.setIsMatch(Constant.nameMap.get(resultData));
        
        //bcHisYYSGEO.setTime(Constant.timeInNet.get(resultData));
        //bcHisYYSGEO.setConsumption(Constant.consumeClass.get(resultData));
        //bcHisYYSGEO.setOffice("南京路步行街");
        //bcHisYYSGEO.setRest("东方绿洲");
        //bcHisYYSGEO.setRate(Constant.howOftenComunication.get(resultData));
        //bcHisYYSGEO.setCallNum(callNum);
        //bcHisYYSGEO.setSearchDate(new Date());
        // bcHisYYSGEO.setInfoid("0");
        
        bcHisYYSGEO.setContactNum("18923456789");
        bcHisYYSGEO.setUserName("张飞");
        if (authCode == null || "".equals(authCode)) {
            bcHisYYSGEO.setAuthCode("0000");
        }
       
        
        return bcHisYYSGEO;
    }
	
//	public static JSONObject errorObj(){
//		JSONObject rtnObj = new JSONObject();
//    	JSONObject head = new JSONObject();
//    	JSONObject body = new JSONObject();
//    	head.put("statusCode", "20001");
//    	head.put("msg", "第三方查询出错");
//    	rtnObj.put("head", head);
//    	rtnObj.put("body", body);
//		return rtnObj;
//	}
}
