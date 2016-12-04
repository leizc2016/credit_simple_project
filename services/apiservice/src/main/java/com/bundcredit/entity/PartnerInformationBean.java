package com.bundcredit.entity;

//股东信息bean
public class PartnerInformationBean {
	private String partnerName;
	private String partnerType;
	private String identifyType;
	private String identifyNo;
	private String realCapi; 
	private String capiDate;
	
	public String getIdentifyType() {
		return identifyType;
	}
	public void setIdentifyType(String identifyType) {
		this.identifyType = identifyType;
	}
	public String getIdentifyNo() {
		return identifyNo;
	}
	public void setIdentifyNo(String identifyNo) {
		this.identifyNo = identifyNo;
	}
	public String getRealCapi() {
		return realCapi;
	}
	public void setRealCapi(String realCapi) {
		this.realCapi = realCapi;
	}
	public String getCapiDate() {
		return capiDate;
	}
	public void setCapiDate(String capiDate) {
		this.capiDate = capiDate;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public String getPartnerType() {
		return partnerType;
	}
	public void setPartnerType(String partnerType) {
		this.partnerType = partnerType;
	}
}
