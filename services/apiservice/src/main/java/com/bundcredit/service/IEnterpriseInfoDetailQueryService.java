package com.bundcredit.service;

import javax.jws.WebService;

@WebService
public interface IEnterpriseInfoDetailQueryService {
	//param 参数 -- 公司名称或者工商注册号
	String query(String param);
}
