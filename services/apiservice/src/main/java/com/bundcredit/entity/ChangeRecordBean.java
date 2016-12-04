package com.bundcredit.entity;

/**
 * 企业变更信息bean
 * @author libolin
 */
public class ChangeRecordBean {
	private String projectName;
	private String beforeContent;
	private String afterContent;
	private String changeDate;
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
