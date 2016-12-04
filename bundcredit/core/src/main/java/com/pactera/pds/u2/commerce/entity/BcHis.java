package com.pactera.pds.u2.commerce.entity;

import java.util.Date;

import com.pactera.pds.u2.commerce.utils.CodeMapTable;
import com.pactera.pds.u2.commerce.utils.IdcardUtils;
import com.pactera.pds.u2.commerce.utils.MenuConstant;
import com.pactera.pds.u2.commerce.utils.MobileEmailValidateUtil;

public class BcHis {

	private Long id;
	private Date queryDate;
	private String queryCondition;
	private String productCode;
	private String productCode2nd;
	private int flag;
	private long conditionId;
	private Long userId;
	private String userName;
	private Integer queryType;
	private String insCode;//机构名称
	
	
	public String getInsCode() {
		return insCode;
	}
	public void setInsCode(String insCode) {
		this.insCode = insCode;
	}
	public Integer getQueryType() {
		return queryType;
	}
	public void setQueryType(Integer queryType) {
		this.queryType = queryType;
	}
	public String getQueryTypeName() {
		return CodeMapTable.QUERY_TYPE.get(queryType);
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getProductName() {
		return MenuConstant.productName.get(productCode);
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getQueryDate() {
		return queryDate;
	}
	public void setQueryDate(Date queryDate) {
		this.queryDate = queryDate;
	}
	
	public String getEncryptQueryCondition() {
		String result = null;
		if (queryCondition.length() == 11) {
			result = MobileEmailValidateUtil.hideMobileMidNumber(queryCondition);
		} else {
			result = IdcardUtils.hideIdCardBirthInfo(queryCondition);
		}
		
		return result;
	}
	public String getQueryCondition() {
		return queryCondition;
	}
	public void setQueryCondition(String queryCondition) {
		this.queryCondition = queryCondition;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductCode2nd() {
		return productCode2nd;
	}
	public void setProductCode2nd(String productCode2nd) {
		this.productCode2nd = productCode2nd;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public long getConditionId() {
		return conditionId;
	}
	public void setConditionId(long conditionId) {
		this.conditionId = conditionId;
	}
	
}
