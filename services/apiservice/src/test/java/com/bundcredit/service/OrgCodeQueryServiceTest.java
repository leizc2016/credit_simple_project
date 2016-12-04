package com.bundcredit.service;

import org.junit.Assert;
import org.junit.Test;

import com.bundcredit.service.impl.OrgCodeQueryService;

public class OrgCodeQueryServiceTest {
	@Test(timeout=60000)
	public void testQuery() throws Exception {
		OrgCodeQueryService service = new OrgCodeQueryService();
		String output = service.query("外滩海纳互联网金融服务(上海)有限公司","","");
		Assert.assertNotNull("OrgCodeQueryService is broken", output);
		Assert.assertTrue("OrgCodeQueryService is broken", output.contains("\"orgCode\":\"342295005\""));
	}
}
