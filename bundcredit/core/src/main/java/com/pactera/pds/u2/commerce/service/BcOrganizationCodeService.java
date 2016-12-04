
package com.pactera.pds.u2.commerce.service;

import com.pactera.pds.u2.commerce.entity.enterpriseinfo.OrgCodeBean;

/**
 * 
 * 组织机构service
 */
public interface BcOrganizationCodeService {

	/**
	 * 根据code或businessName查询组织机构
	 * @param orgCodeBean
	 * @return
	 */
	public OrgCodeBean queryBcOrganizationCodeById(OrgCodeBean  orgCodeBean);
	
	/**
	 * 保存组织机构表
	 * @param orgCodeBean
	 * @param bussinessName
	 */
	public void saveBcOrganizationCode(OrgCodeBean  orgCodeBean,String bussinessName);
}
