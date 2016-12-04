package com.pactera.pds.u2.commerce.entity.enterpriseinfo;

import java.io.Serializable;
import java.util.Date;

import  org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
public class CourtJudgmentDocBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5720840017208191172L;
	private long id;
    private String courtName;//审理法院
    private String title;//标题 
    private String caseCode;//案号 
    private String judgeDate;//裁判日期
    private String judgmentDocUrl;//判决书url
    private Date createTime;
	private Date updateTime;
	
	private String judgmentType;
	private String judgmentId;
	
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getJudgmentId() {
		return judgmentId;
	}
	public void setJudgmentId(String judgmentId) {
		this.judgmentId = judgmentId;
	}
	public String getJudgmentType() {
		return judgmentType;
	}
	public void setJudgmentType(String judgmentType) {
		this.judgmentType = judgmentType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getCourtName() {
		return courtName;
	}
	public void setCourtName(String courtName) {
		this.courtName = courtName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCaseCode() {
		return caseCode;
	}
	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}
	public String getJudgeDate() {
		return judgeDate;
	}
	public void setJudgeDate(String judgeDate) {
		this.judgeDate = judgeDate;
	}
	public String getJudgmentDocUrl() {
		return judgmentDocUrl;
	}
	public void setJudgmentDocUrl(String judgmentDocUrl) {
		this.judgmentDocUrl = judgmentDocUrl;
	}
    
}
