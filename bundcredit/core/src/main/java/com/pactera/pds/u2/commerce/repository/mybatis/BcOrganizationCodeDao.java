
package com.pactera.pds.u2.commerce.repository.mybatis;

import com.pactera.pds.u2.commerce.entity.enterpriseinfo.OrgCodeBean;

/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * 
 */
@MyBatisRepository
public interface BcOrganizationCodeDao {

	public OrgCodeBean queryBcOrganizationCodeById(OrgCodeBean  OrgCodeBean);
	
	public long saveBcOrganizationCode(OrgCodeBean  OrgCodeBean);
}
