
package com.pactera.pds.u2.commerce.service.bchis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.pactera.pds.u2.commerce.entity.BcHisYYSCS;
import com.pactera.pds.u2.commerce.repository.mybatis.BcHisYYSCSMybatisDao;

@Component
// 类中所有public函数都纳入事务管理的标识.
@Transactional
public class BcHisYYSCSService {
	
	private BcHisYYSCSMybatisDao bcHisYYSCSDao;
	
	@Autowired
	public void setBcHisYYSCSDao(BcHisYYSCSMybatisDao bcHisYYSCSDao) {
		this.bcHisYYSCSDao = bcHisYYSCSDao;
	}

	public long saveBcHisYYSCS(BcHisYYSCS entity) {
		return bcHisYYSCSDao.saveBcHisYYSCS(entity);
	}

	public void updateBcHisYYSCS(BcHisYYSCS entity){
		bcHisYYSCSDao.updateBcHisYYSCS(entity);
	}
	
	public void deleteBcHisYYSCS(Long id) {
		bcHisYYSCSDao.deleteBcHisYYSCS(id);
	}

	public List<BcHisYYSCS> getAllBcHisYYSCS(Map params,PageBounds pageBounds) {
		return (List<BcHisYYSCS>) bcHisYYSCSDao.findAll(params, pageBounds);
	}
	
	public BcHisYYSCS findByID(long id) {
		return bcHisYYSCSDao.getBcHisYYSCSById(id);
	}

	public BcHisYYSCS getBcHisYYSCSById(long id){
		return bcHisYYSCSDao.getBcHisYYSCSById(id);
	}
	
	public BcHisYYSCS getBcHisYYSCSByAsynCode(String asynCode){
		return bcHisYYSCSDao.getBcHisYYSCSByAsynCode(asynCode);
	}
	
	public String checkAuthCode(Map<String, Object> condition){
		return bcHisYYSCSDao.checkAuthCode(condition);
	}
}
