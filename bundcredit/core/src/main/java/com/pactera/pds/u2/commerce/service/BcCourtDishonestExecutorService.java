
package com.pactera.pds.u2.commerce.service;

import java.util.List;

import com.pactera.pds.u2.commerce.entity.enterpriseinfo.DishPersonBean;

/**
 * 失信人service
 * @author baifan
 *
 */
public interface BcCourtDishonestExecutorService {

	
	/**
	 * 根据企业名称查询失信人
	 * @param businessName
	 * @return
	 */
	public List<DishPersonBean> queryBcCourtDishonestExecutorByBusinessName(DishPersonBean dishPersonBean);
	
	/**
	 * 保存失信人表
	 * @param dishPersonBeans
	 * @param businessName
	 */
	public void saveBcCourtDishonestExecutors(List<DishPersonBean> dishPersonBeans,String businessName);
}
