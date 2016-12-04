package com.bundcredit.service;

import javax.jws.WebService;

@WebService
public interface IFinancialStableQueryService {
	//param 参数
	String query(String param,String param1,String param2);
	
	String getDishPersonsNum(String keys);
}
