package com.pactera.pds.u2.commerce.entity.financialstable;

public class CompanyInfoBean {
	private long id;
	private String name;
	private String address;
	private String regID;
	private String opAddress;
	private int riskNums;
	private String monitorRcd;
	private int isExistDishPersons;
	
	public int getIsExistDishPersons() {
		return isExistDishPersons;
	}
	public void setIsExistDishPersons(int isExistDishPersons) {
		this.isExistDishPersons = isExistDishPersons;
	}
	public String getMonitorRcd() {
		return monitorRcd;
	}
	public void setMonitorRcd(String monitorRcd) {
		this.monitorRcd = monitorRcd;
	}
	public int getRiskNums() {
		return riskNums;
	}
	public void setRiskNums(int riskNums) {
		this.riskNums = riskNums;
	}
	public String getOpAddress() {
		return opAddress;
	}
	public void setOpAddress(String opAddress) {
		this.opAddress = opAddress;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRegID() {
		return regID;
	}
	public void setRegID(String regID) {
		this.regID = regID;
	}
	
}
