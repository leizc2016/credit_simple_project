package com.pactera.pds.u2.commerce.thirdpartyapi;

import net.sf.json.JSONObject;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.pactera.pds.u2.commerce.utils.GEODesUtil;

public class GEOTest {

	@Test
	public void testGEOAPI() throws Exception {
		
		String data_auth = "{'head': {'userName': 'shbundcredit','password': 'BA78952FAE730594BDC36C6779B3C234'},'body': {}}";
		
		String dataStr = GEODesUtil.encrypt(data_auth);
		
		RestTemplate restTemplate = new RestTemplate();
		String resultD = restTemplate.postForObject(
				"http://118.26.142.81/civp/queryAuthentication",
				dataStr, String.class);
		
		JSONObject requestData = JSONObject.fromObject(GEODesUtil
				.decrypt(resultD));
		
		Assert.assertTrue("GEO AUTH API is broken.", validateToken(requestData));
		
		JSONObject head_token = (JSONObject) requestData.get("head");
		
		String data_query = "{'head': {'token': '" + head_token.getString("token") + "'},'body': {'queryID': 'xx','infoID': '1','CID': '18916921230', 'CIDName': '吴莹','CID2': '18917254309','authID': '00000','operatorID': '10001','userRegID': '1012'}}";
		dataStr = GEODesUtil.encrypt(data_query);
		
		resultD = restTemplate.postForObject(
				"http://118.26.142.81/civp/queryUserInfoByCID",
				dataStr, String.class);
		
		requestData = JSONObject.fromObject(GEODesUtil
				.decrypt(resultD));
		
		JSONObject head = requestData.getJSONObject("head");
		String code = head.getString("errorCode");
		
		Assert.assertEquals("GEO QUERY API is broken.", "10005", code);
	}
	
	private boolean validateToken(JSONObject requestData) {
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
	
	
	public static void main(String[] args) throws Exception {
//		String data = "{'head': {'userName': 'shbundcredit','password': 'BA78952FAE730594BDC36C6779B3C234'},'body': {}}";
		String data = "{'head': {'token': '0d992981be9a4d01baa5249789950adf'},'body': {'queryID': 'xx','infoID': '1','CID': '18916921230', 'CIDName': '吴莹','CID2': '18917254309','authID': '00000','operatorID': '10001','userRegID': '1012'}}";
//		String data = "{'head': {'token': '694d3fa1ed6a485da4041f81dc4715f6'},'body': {'queryID': 'xx','infoID': '2','CID': '18917254309','CID2': '18917254309','authID': '00000','operatorID': '10001','userRegID': '1012'}}";
		String dataStr = GEODesUtil.encrypt(data);
		RestTemplate restTemplate = new RestTemplate();
//		String resultD = restTemplate.postForObject(
//				"http://118.26.142.81/civp/queryAuthentication",
//				dataStr, String.class);
		
		String resultD = restTemplate.postForObject(
				"http://118.26.142.81/civp/queryUserInfoByCID",
				dataStr, String.class);
		System.out.println(GEODesUtil.decrypt(resultD));
		
//		String result = doGeoPhoneSearch();
//		
//		System.out.println(GEODesUtil.decrypt(result));

	}


}
