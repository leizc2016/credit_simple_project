package com.pactera.pds.u2.commerce.utils;

import java.util.HashMap;
import java.util.Map;

public class CodeMapTable {
	
	public static final Map<String, String> APPLY_CODE_MAP = new HashMap<String, String>() {
		{
			put("N", "无抵押无担保");
			put("A", "车抵押");
			put("H", "房抵押");
			put("O", "其他抵押");
			put("P", "自然人担保");
			put("E", "法人担保");
			put("X", "其他");
		}
	};
	
	public static final Map<Integer, String> QUERY_TYPE = new HashMap<Integer, String>() {
		{
			put(1, "申请查询");
			put(2, "贷后查询");
			put(3, "第三方查询");
		}
	};

}
