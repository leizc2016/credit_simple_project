package com.pactera.pds.u2.commerce.utils;

import java.util.Map;

import com.google.common.collect.Maps;

public class ControllerConstant {

	public static Integer productConciselId =  1;
	public static Integer productFullCreditId =  2;
	
    public static Map<String, String> searchTypes = Maps.newLinkedHashMap();
	static {
		searchTypes.put("text_credit", "申请查询");
		searchTypes.put("text_after_loan", "贷后查询");
		searchTypes.put("key_credit", "1");
		searchTypes.put("key_after_loan", "2");
	}
}
