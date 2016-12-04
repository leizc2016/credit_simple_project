package com.bundcredit.service;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.bundcredit.service.impl.CourtJudgmentDocQeuryService;

public class CourtJudgmentDocQeuryServiceTest {
	@Test(timeout=60000)
	@Ignore
	public void testQuery() throws Exception {
		CourtJudgmentDocQeuryService service = new CourtJudgmentDocQeuryService();
		String output = service.query("（2015）丰民初字第","","");
		Assert.assertNotNull("CourtJudgmentDocQeuryService is broken", output);
		Assert.assertTrue("CourtJudgmentDocQeuryService is broken", output.contains("courtName"));
	}
}
