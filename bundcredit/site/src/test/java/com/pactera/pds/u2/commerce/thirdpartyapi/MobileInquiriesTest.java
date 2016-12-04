package com.pactera.pds.u2.commerce.thirdpartyapi;

import net.sf.json.JSONObject;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

public class MobileInquiriesTest {

	@Test
	public void testMobileInquiriesAPI() throws Exception {
		
		String url = "http://v.showji.com/Locating/"
	               + "showji.com20150416273007.aspx"
				   + "?m=13601832802&output=json";
		RestTemplate restTemplate = new RestTemplate();
		String output = restTemplate.getForObject(url, String.class);
		JSONObject requestData = JSONObject.fromObject(output);
		String provider = (String)requestData.get("Corp");
		
		Assert.assertEquals("Mobile Inquiries API is broken.", "中国移动",  provider);
		
	}
}
