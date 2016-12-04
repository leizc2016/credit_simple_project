
package com.pactera.pds.u2.commerce.service;

import com.pactera.pds.u2.commerce.entity.enterpriseinfo.BusinessInfoBean;

/**
 * 企业信息service
 * @author baifan
 *
 */
public interface BcEnterpriseInfoService {

	/**
	 * 根据工商注册号查询企业信息
	 * @param regId
	 * @return
	 */
	public BusinessInfoBean queryBcEnterpriseInfoById(String regId);
	
	/**
	 * 保存企业信息表
	 * @param businessInfoBean
	 */
	public void saveBcEnterpriseInfo(BusinessInfoBean  businessInfoBean);
}
