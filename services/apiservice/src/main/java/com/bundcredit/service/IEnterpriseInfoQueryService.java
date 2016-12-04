package com.bundcredit.service;

import javax.jws.WebService;

@WebService
public interface IEnterpriseInfoQueryService {
	//param 参数
	String query(String param,String param1,String param2);
}
