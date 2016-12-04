package com.pactera.pds.u2.commerce.entity;

import java.io.Serializable;
import java.util.Date;

import com.pactera.pds.u2.commerce.utils.CodeMapTable;
import com.pactera.pds.u2.commerce.utils.IdcardUtils;
import com.pactera.pds.u2.commerce.utils.MenuConstant;
import com.pactera.pds.u2.commerce.utils.MobileEmailValidateUtil;

public class BcHisEnterpriseQCC implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7323673552995587360L;
	private Long id;
	private String registerNum;
	private String idCardNum;
	private String fullName;
	private Date createTime;
	private Date updateTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRegisterNum() {
		return registerNum;
	}
	public void setRegisterNum(String registerNum) {
		this.registerNum = registerNum;
	}
	public String getIdCardNum() {
		return idCardNum;
	}
	public void setIdCardNum(String idCardNum) {
		this.idCardNum = idCardNum;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	
}
