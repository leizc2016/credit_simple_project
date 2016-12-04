package com.bundcredit.service;

import org.junit.Assert;
import org.junit.Test;

import com.bundcredit.service.impl.UnionPayZCQueryService;

public class UnionPayZCQueryServiceTest {
	@Test(timeout=60000)
	public void testQuery() throws Exception {
		UnionPayZCQueryService service = new UnionPayZCQueryService();
		String output = service.query("");
		Assert.assertNotNull("OrgCodeQueryService is broken", output);
		Assert.assertTrue("OrgCodeQueryService is broken", output.contains("\"error_code\":\"10104\""));
	}
}
