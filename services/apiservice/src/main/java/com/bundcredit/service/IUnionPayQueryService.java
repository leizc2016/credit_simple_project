package com.bundcredit.service;

import javax.jws.WebService;

@WebService
public interface IUnionPayQueryService {
	
	public String query(String param);
}
