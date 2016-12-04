package com.pactera.pds.u2.commerce.entity;

//畅圣查询bean
public class CSCarrierSearch {
	private String authCode; //授权码
	private String homeDetailAddr;//详细家庭住址
	private String homelocationa; //家庭住址所在省
	private String homelocationc; //家庭住址所在市
	private String homelocationp; //家庭住址所在县
	private String idCard; //身份证
	private String refTelephoneNum;//关联手机号码
	private String cellNum; //手机号
	private String userName; //姓名
	private String workDetailAddr;//详细工作住址
	private String worklocationa;//工作住址所在省
	private String worklocationc;//工作住址所在市
	private String worklocationp;//工作住址所在县
	private String homeAddr; //完整家庭地址
	private String workAddr; //完整工作地址
	private String namelevel; //实名等级
	private String idCardMatch; //身份证匹配
	private String inNetTimes; //在网时间
	private String mobileCallCount;//手机卡数量
	private String TelCallCount;//固话数量
	private String asynCode; //同步唯一值
	private String asynData; //同步返回数据json
	private int showWarn=0; //用于页面显示提示信息
	
	public String getAsynData() {
		return asynData;
	}
	public void setAsynData(String asynData) {
		this.asynData = asynData;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public String getHomeDetailAddr() {
		return homeDetailAddr;
	}
	public void setHomeDetailAddr(String homeDetailAddr) {
		this.homeDetailAddr = homeDetailAddr;
	}
	public String getHomelocationa() {
		return homelocationa;
	}
	public void setHomelocationa(String homelocationa) {
		this.homelocationa = homelocationa;
	}
	public String getHomelocationc() {
		return homelocationc;
	}
	public void setHomelocationc(String homelocationc) {
		this.homelocationc = homelocationc;
	}
	public String getHomelocationp() {
		return homelocationp;
	}
	public void setHomelocationp(String homelocationp) {
		this.homelocationp = homelocationp;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getRefTelephoneNum() {
		return refTelephoneNum;
	}
	public void setRefTelephoneNum(String refTelephoneNum) {
		this.refTelephoneNum = refTelephoneNum;
	}
	public String getCellNum() {
		return cellNum;
	}
	public void setCellNum(String cellNum) {
		this.cellNum = cellNum;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getWorkDetailAddr() {
		return workDetailAddr;
	}
	public void setWorkDetailAddr(String workDetailAddr) {
		this.workDetailAddr = workDetailAddr;
	}
	public String getWorklocationa() {
		return worklocationa;
	}
	public void setWorklocationa(String worklocationa) {
		this.worklocationa = worklocationa;
	}
	public String getWorklocationc() {
		return worklocationc;
	}
	public void setWorklocationc(String worklocationc) {
		this.worklocationc = worklocationc;
	}
	public String getWorklocationp() {
		return worklocationp;
	}
	public void setWorklocationp(String worklocationp) {
		this.worklocationp = worklocationp;
	}
	public String getHomeAddr() {
		return homeAddr;
	}
	public void setHomeAddr(String homeAddr) {
		this.homeAddr = homeAddr;
	}
	public String getWorkAddr() {
		return workAddr;
	}
	public void setWorkAddr(String workAddr) {
		this.workAddr = workAddr;
	}
	public String getNamelevel() {
		return namelevel;
	}
	public void setNamelevel(String namelevel) {
		this.namelevel = namelevel;
	}
	public String getIdCardMatch() {
		return idCardMatch;
	}
	public void setIdCardMatch(String idCardMatch) {
		this.idCardMatch = idCardMatch;
	}
	public String getInNetTimes() {
		return inNetTimes;
	}
	public void setInNetTimes(String inNetTimes) {
		this.inNetTimes = inNetTimes;
	}
	public String getMobileCallCount() {
		return mobileCallCount;
	}
	public void setMobileCallCount(String mobileCallCount) {
		this.mobileCallCount = mobileCallCount;
	}
	public int getShowWarn() {
		return showWarn;
	}
	public void setShowWarn(int showWarn) {
		this.showWarn = showWarn;
	}
	public String getTelCallCount() {
		return TelCallCount;
	}
	public void setTelCallCount(String telCallCount) {
		TelCallCount = telCallCount;
	}
	public String getAsynCode() {
		return asynCode;
	}
	public void setAsynCode(String asynCode) {
		this.asynCode = asynCode;
	}
	
}
