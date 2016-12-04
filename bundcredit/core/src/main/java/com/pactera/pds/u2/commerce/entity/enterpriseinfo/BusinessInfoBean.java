package com.pactera.pds.u2.commerce.entity.enterpriseinfo;

import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;

//工商信息bean
public class BusinessInfoBean {
	private String regID; //企业注册号
	private String name; //公司名称
	private String type; //企业类型
	private String legalPerson; //法人
	private String registeredCapital; //注册资金
	private String address; //注册地址
	private String city;//城市
	private String bureau; //登记机关
	private String state; //登记状态
	private String scope; //经营范围
	private String setupDate; //成立日期
	private String operatingPeriod; //营业期限
	private String awardDate;//发证日期
	private Date createTime;
	private Date updateTime;
	private List<PartnerInformationBean> partnerInformationBeans;
	private List<ChangeRecordBean> changeRecordsBeans;
	
	public List<ChangeRecordBean> getChangeRecordsBeans() {
		return changeRecordsBeans;
	}
	public void setChangeRecordsBeans(List<ChangeRecordBean> changeRecordsBeans) {
		this.changeRecordsBeans = changeRecordsBeans;
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
	public String getAwardDate() {
		return awardDate;
	}
	public void setAwardDate(String awardDate) {
		this.awardDate = awardDate;
	}
	public String getRegID() {
		return regID;
	}
	public void setRegID(String regID) {
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
	public String getRegisteredCapital() {
		return registeredCapital;
	}
	public void setRegisteredCapital(String registeredCapital) {
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
	public String getSetupDate() {
		return setupDate;
	}
	public void setSetupDate(String setupDate) {
		this.setupDate = setupDate;
	}
	public String getOperatingPeriod() {
		return operatingPeriod;
	}
	public void setOperatingPeriod(String operatingPeriod) {
		this.operatingPeriod = operatingPeriod;
	}
	public List<PartnerInformationBean> getPartnerInformationBeans() {
		return partnerInformationBeans;
	}
	public void setPartnerInformationBeans(List<PartnerInformationBean> partnerInformationBeans) {
		this.partnerInformationBeans = partnerInformationBeans;
	}
	public String getCity() {
		if(StringUtils.isNotBlank(address)){
			city = address.substring(0, 2);
			return city;
		}else{
			return "";
		}
	}
	public void setCity(String city) {
		this.city = city;
	}
	
}
