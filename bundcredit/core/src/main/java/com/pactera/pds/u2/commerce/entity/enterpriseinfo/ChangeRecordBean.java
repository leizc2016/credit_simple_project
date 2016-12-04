package com.pactera.pds.u2.commerce.entity.enterpriseinfo;

/**
 * 企业变更信息bean
 * @author libolin
 */
public class ChangeRecordBean {
	private long id;//主键
	private String projectName;
	private String beforeContent;
	private String afterContent;
	private String changeDate;
	private String regId;//外键
	
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getBeforeContent() {
		return beforeContent;
	}
	public void setBeforeContent(String beforeContent) {
		this.beforeContent = beforeContent;
	}
	public String getAfterContent() {
		return afterContent;
	}
	public void setAfterContent(String afterContent) {
		this.afterContent = afterContent;
	}
	public String getChangeDate() {
		return changeDate;
	}
	public void setChangeDate(String changeDate) {
		this.changeDate = changeDate;
	}
	
}
