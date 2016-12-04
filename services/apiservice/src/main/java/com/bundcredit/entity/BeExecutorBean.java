package com.bundcredit.entity;

public class BeExecutorBean {
	private String name;//被执行人名称
	private String execCourt;//执行法院
	private String code; //身份证号码/组织机构代码
	private String filingTime;//立案时间
	private String caseCode;//案号
	private String executiveSubject;//执行标的
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExecCourt() {
		return execCourt;
	}
	public void setExecCourt(String execCourt) {
		this.execCourt = execCourt;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getFilingTime() {
		return filingTime;
	}
	public void setFilingTime(String filingTime) {
		this.filingTime = filingTime;
	}
	public String getCaseCode() {
		return caseCode;
	}
	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}
	public String getExecutiveSubject() {
		return executiveSubject;
	}
	public void setExecutiveSubject(String executiveSubject) {
		this.executiveSubject = executiveSubject;
	}
}
