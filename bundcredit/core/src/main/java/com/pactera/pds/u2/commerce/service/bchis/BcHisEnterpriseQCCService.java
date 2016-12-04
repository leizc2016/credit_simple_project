
package com.pactera.pds.u2.commerce.service.bchis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pactera.pds.u2.commerce.entity.BcHisEnterpriseQCC;
import com.pactera.pds.u2.commerce.repository.mybatis.BcHisEnterpriseQCCDao;

/**
 * 企业信息查询service
 * @author baifan
 *
 */
@Component
@Transactional
public class BcHisEnterpriseQCCService {
	
	@Autowired
	private BcHisEnterpriseQCCDao bcHisEnterpriseQCCDao;
	
	/**
	 * 根据ID查询BcHisEnterPriseQCC表
	 * @param id
	 * @return
	 */
	public BcHisEnterpriseQCC queryBcHisEnterPriseQCCById(long id){
		return bcHisEnterpriseQCCDao.queryBcHisEnterPriseQCCById(id);
		
	}
	
	/**
	 * 插入BcHisEnterPriseQCC表
	 * @param bcHisEnterpriseQCC
	 * @return
	 */
	public long saveBcHisEnterPriseQCC(BcHisEnterpriseQCC bcHisEnterpriseQCC){
		return bcHisEnterpriseQCCDao.saveBcHisEnterPriseQCC(bcHisEnterpriseQCC);
	}
}
