package com.pactera.pds.u2.commerce.entity;

import java.io.Serializable;

public class BcHisYYSGEO implements Serializable {
	private static final long serialVersionUID = -4414582797502162678L;

    private Integer id;

    private String isMatch;

    private String netTime;

    private String spendingLevel;

    private String workingSite;

    private String homeAddr;

    private String contactRate;

    private String callNum;

    private String contactNum;

    private String userName;

    private String authCode;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIsMatch() {
		return isMatch;
	}

	public void setIsMatch(String isMatch) {
		this.isMatch = isMatch;
	}

	public String getNetTime() {
		return netTime;
	}

	public void setNetTime(String netTime) {
		this.netTime = netTime;
	}

	public String getSpendingLevel() {
		return spendingLevel;
	}

	public void setSpendingLevel(String spendingLevel) {
		this.spendingLevel = spendingLevel;
	}

	public String getWorkingSite() {
		return workingSite;
	}

	public void setWorkingSite(String workingSite) {
		this.workingSite = workingSite;
	}

	public String getHomeAddr() {
		return homeAddr;
	}

	public void setHomeAddr(String homeAddr) {
		this.homeAddr = homeAddr;
	}

	public String getContactRate() {
		return contactRate;
	}

	public void setContactRate(String contactRate) {
		this.contactRate = contactRate;
	}

	public String getCallNum() {
		return callNum;
	}

	public void setCallNum(String callNum) {
		this.callNum = callNum;
	}

	public String getContactNum() {
		return contactNum;
	}

	public void setContactNum(String contactNum) {
		this.contactNum = contactNum;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

}
