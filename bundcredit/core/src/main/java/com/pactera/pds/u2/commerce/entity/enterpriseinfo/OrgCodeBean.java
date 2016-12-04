package com.pactera.pds.u2.commerce.entity.enterpriseinfo;

import java.io.Serializable;
import java.util.Date;

public class OrgCodeBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7290093261369901381L;
	private String orgCode;//组织机构代码
	private String orgName;//机构名称
	private String orgRegID; //机构登记证号
	private String orgType;//机构类型
	private String issuer;//颁发单位
	private String orgAddress;//机构地址
	private String applyDate;//机构申请日期
	private String startDate;//经营开始时间
	private String endDate;//经营结束时间
	private String businessName;
	private Date createTime;
	private Date updateTime;
	
	
	
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
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgRegID() {
		return orgRegID;
	}
	public void setOrgRegID(String orgRegID) {
		this.orgRegID = orgRegID;
	}
	public String getOrgType() {
		return orgType;
	}
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	public String getIssuer() {
		return issuer;
	}
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	public String getOrgAddress() {
		return orgAddress;
	}
	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}
	public String getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
}
