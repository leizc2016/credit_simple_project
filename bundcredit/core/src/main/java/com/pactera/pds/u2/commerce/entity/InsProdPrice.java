package com.pactera.pds.u2.commerce.entity;


public class InsProdPrice {
    private String insCode;
    private String prodCode;
    private String prodName;
    private Float queryDiscount;
    private String insProdPriceId;
    private int defaultFlag;
    
	public int getDefaultFlag() {
		return defaultFlag;
	}
	public void setDefaultFlag(int defaultFlag) {
		this.defaultFlag = defaultFlag;
	}
	public String getInsCode() {
		return insCode;
	}
	public void setInsCode(String insCode) {
		this.insCode = insCode;
	}
	public String getProdCode() {
		return prodCode;
	}
	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}
	public Float getQueryDiscount() {
		return queryDiscount;
	}
	public void setQueryDiscount(Float queryDiscount) {
		this.queryDiscount = queryDiscount;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public String getInsProdPriceId() {
		return insProdPriceId;
	}
	public void setInsProdPriceId(String insProdPriceId) {
		this.insProdPriceId = insProdPriceId;
	}
	
    
}
