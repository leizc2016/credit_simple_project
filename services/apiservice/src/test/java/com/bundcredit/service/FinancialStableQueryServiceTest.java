package com.bundcredit.service;

import org.junit.Assert;
import org.junit.Test;

import com.bundcredit.service.impl.FinancialStableQueryService;

public class FinancialStableQueryServiceTest {
	@Test(timeout=60000)
	public void testQuery() throws Exception {
		FinancialStableQueryService service = new FinancialStableQueryService();
		String output = service.query("中鑫","嘉定","");
		Assert.assertNotNull("FinancialStableQueryService is broken", output);
		Assert.assertTrue("FinancialStableQueryService is broken", output.contains("\"regID\":\"310114002547323\""));
	}
}
