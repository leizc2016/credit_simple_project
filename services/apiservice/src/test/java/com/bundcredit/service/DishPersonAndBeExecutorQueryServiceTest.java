package com.bundcredit.service;

import org.junit.Assert;
import org.junit.Test;

import com.bundcredit.service.impl.DishPersonAndBeExecutorQueryService;

public class DishPersonAndBeExecutorQueryServiceTest {
	@Test(timeout=60000)
	public void testBeExecutorQuery() throws Exception {
		DishPersonAndBeExecutorQueryService service = new DishPersonAndBeExecutorQueryService();
		String output = service.query("江苏元通能源发展有限公司","1","1");
		Assert.assertNotNull("BeExecutorQueryService is broken", output);
		Assert.assertTrue("BeExecutorQueryService is broken", output.contains("\"code\":\"76358365-4\""));
	}
	
	@Test(timeout=60000)
	public void testDishPersonQuery() throws Exception {
		DishPersonAndBeExecutorQueryService service = new DishPersonAndBeExecutorQueryService();
		String output = service.query("江苏元通能源发展有限公司","","1");
		Assert.assertNotNull("DishPersonQueryService is broken", output);
		Assert.assertTrue("DishPersonQueryService is broken", output.contains("\"code\":\"76358365-4\""));
	}
}
