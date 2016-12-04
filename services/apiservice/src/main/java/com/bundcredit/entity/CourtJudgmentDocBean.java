package com.bundcredit.entity;

import  org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
public class CourtJudgmentDocBean {
	
    private String courtName;//审理法院
    private String title;//标题 
    private String caseCode;//案号 
    private String judgeDate;//裁判日期
    private String judgmentDocUrl;//判决书url
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
