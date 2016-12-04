
package com.pactera.pds.u2.commerce.service;

import java.util.List;

import com.pactera.pds.u2.commerce.entity.enterpriseinfo.BeExecutorBean;
/**
 * 被执行人查询
 * @author baifan
 *
 */
public interface BcCourtExecutorService {

	/**
	 * 根据公司名称查询被执行人
	 * @param businessName
	 * @return
	 */
	public List<BeExecutorBean> queryBcCourtExecutorByBusinessName(String businessName);
	/**
	 * 保存被执行人
	 * @param beExecutorBeans
	 */
	public void saveBcCourtExecutors(List<BeExecutorBean>  beExecutorBeans,String businessName);
	/**
	 * 根据身份证号码或组织机构代码查询被执行人
	 * @param code
	 * @return
	 */
	public List<BeExecutorBean> queryBcCourtExecutorByCode(String code);
}
