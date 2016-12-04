package com.bundcredit.service;

import org.junit.Assert;
import org.junit.Test;

import com.bundcredit.service.impl.CourtAnnouncementQeuryService;

public class CourtAnnouncementQeuryServiceTest {
	@Test(timeout=60000)
	public void testQuery() throws Exception {
		CourtAnnouncementQeuryService service = new CourtAnnouncementQeuryService();
		String output = service.query("苏州市金诚担保有限公司","","");
		Assert.assertNotNull("CourtAnnouncementQeuryService is broken", output);
		Assert.assertTrue("CourtAnnouncementQeuryService is broken", output.contains("\"announcementType\":\"送达诉状副本及开庭传票\""));
	}
}
