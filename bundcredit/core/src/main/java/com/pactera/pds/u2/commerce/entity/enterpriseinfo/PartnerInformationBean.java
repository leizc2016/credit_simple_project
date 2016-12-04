package com.pactera.pds.u2.commerce.entity.enterpriseinfo;

import java.io.Serializable;

//股东信息bean
public class PartnerInformationBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6413091504796375905L;
	private String id;
	private String partnerName;
	private String partnerType;
	private String regId;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
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
