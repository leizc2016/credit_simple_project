package com.pactera.pds.u2.commerce.entity;

import java.io.Serializable;
import java.util.Date;

public class BcProviderLog implements Serializable {
	
	private static final long serialVersionUID = 4131708430071041348L;
	
	private long id;
	private Date queryDate;
	private String queryCondition;
	private String queryResult;
	private String provider;
	private String operator;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getQueryDate() {
		return queryDate;
	}
	public void setQueryDate(Date queryDate) {
		this.queryDate = queryDate;
	}
	public String getQueryCondition() {
		return queryCondition;
	}
	public void setQueryCondition(String queryCondition) {
		this.queryCondition = queryCondition;
	}
	public String getQueryResult() {
		return queryResult;
	}
	public void setQueryResult(String queryResult) {
		this.queryResult = queryResult;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
}
