
package com.pactera.pds.u2.commerce.repository.mybatis;

import java.util.List;

import com.pactera.pds.u2.commerce.entity.enterpriseinfo.DishPersonBean;

/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * 
 */
@MyBatisRepository
public interface BcCourtDishonestExecutorDao {

	public List<DishPersonBean> queryBcCourtDishonestExecutorByBusinessName(DishPersonBean dishPersonBean);
	
	public long saveBcCourtDishonestExecutor(DishPersonBean dishPersonBean);
}
