package com.bundcredit.service;

import org.junit.Assert;

import org.junit.Test;

import com.bundcredit.service.impl.BusinessInfoQueryService;

public class BusinessInfoQueryServiceTest {
	@Test(timeout=60000)
	public void testQuery() throws Exception {
		BusinessInfoQueryService service = new BusinessInfoQueryService();
		String output = service.query("320381000024653","","");
		Assert.assertNotNull("BusinessInfoQueryService is broken", output);
		Assert.assertTrue("BusinessInfoQueryService is broken", output.contains("\"regID\":\"320381000024653\""));
	}
}
