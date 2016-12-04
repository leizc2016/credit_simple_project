package com.pactera.pds.u2.commerce.entity.enterpriseinfo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
public class DishPersonBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1486625205132456186L;
	private int id;
	private String name;//名称
	private String legalPerson; //法人名称
	private String code; //身份证号码/组织机构代码
	private String basisDocNo;//执行依据文号
	private String caseCode;//案号
	private String basisDept;//做出执行依据单位
	private String docContent;//法律生效文书确定的义务
	private String execStatus;//被执行人的履行情况
	private String execCourt;//执行法院
	private String province;//省份
	private String filingTime;//立案时间
	private String publishDate;//发布时间
	private String businessName;
	private Date createTime;
	private Date updateTime;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLegalPerson() {
		return legalPerson;
	}
	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getBasisDocNo() {
		return basisDocNo;
	}
	public void setBasisDocNo(String basisDocNo) {
		this.basisDocNo = basisDocNo;
	}
	public String getCaseCode() {
		return caseCode;
	}
	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}
	public String getBasisDept() {
		return basisDept;
	}
	public void setBasisDept(String basisDept) {
		this.basisDept = basisDept;
	}
	public String getDocContent() {
		return docContent;
	}
	public void setDocContent(String docContent) {
		this.docContent = docContent;
	}
	public String getExecStatus() {
		return execStatus;
	}
	public void setExecStatus(String execStatus) {
		this.execStatus = execStatus;
	}
	public String getExecCourt() {
		return execCourt;
	}
	public void setExecCourt(String execCourt) {
		this.execCourt = execCourt;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getFilingTime() {
		return filingTime;
	}
	public void setFilingTime(String filingTime) {
		this.filingTime = filingTime;
	}
	public String getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	
}
