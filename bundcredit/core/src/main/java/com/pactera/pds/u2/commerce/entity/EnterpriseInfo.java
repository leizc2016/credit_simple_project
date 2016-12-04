
package com.pactera.pds.u2.commerce.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//JPA标识
@Entity
@Table(name = "bc_enterprise_info")
public class EnterpriseInfo implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long regID; //企业注册号
	private String name; //公司名称
	private String type; //企业类型
	private String legalPerson; //法人
	private Double registeredCapital; //注册资金
	private String address; //注册地址
	private String bureau; //登记机关
	private String state; //登记状态
	private String scope; //经营范围
	private Date setupDate; //成立日期
	private Date fromDate; //成立日期
	private Date approvalDate; //审批时间
	private String city; //所在省市
	private Date createTime; //审批时间
	private String url; //网站
	private Date reportDatetime;//报告时间

	public Date getReportDatetime() {
		return new Date();
	}
	public void setReportDatetime(Date reportDatetime) {
		this.reportDatetime = reportDatetime;
	}
	public long getRegID() {
		return regID;
	}
	public void setRegID(long regID) {
		this.regID = regID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLegalPerson() {
		return legalPerson;
	}
	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}
	public Double getRegisteredCapital() {
		return registeredCapital;
	}
	public void setRegisteredCapital(Double registeredCapital) {
		this.registeredCapital = registeredCapital;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBureau() {
		return bureau;
	}
	public void setBureau(String bureau) {
		this.bureau = bureau;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public Date getSetupDate() {
		return setupDate;
	}
	public void setSetupDate(Date setupDate) {
		this.setupDate = setupDate;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getApprovalDate() {
		return approvalDate;
	}
	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
