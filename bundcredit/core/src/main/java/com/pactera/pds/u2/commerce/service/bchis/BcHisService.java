
package com.pactera.pds.u2.commerce.service.bchis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.pactera.pds.u2.commerce.entity.BcHis;
import com.pactera.pds.u2.commerce.repository.mybatis.BcHisMybatisDao;

@Component
// 类中所有public函数都纳入事务管理的标识.
@Transactional
public class BcHisService {
	
	private BcHisMybatisDao bcHisDao;
	
	@Autowired
	public void setBcHisDao(BcHisMybatisDao bcHisDao) {
		this.bcHisDao = bcHisDao;
	}

	public long saveBcHis(BcHis entity) {
		return bcHisDao.saveBcHis(entity);
	}
	
	public void updateBcHis(BcHis entity){
		bcHisDao.updateBcHis(entity);
	}
	
	public void deleteBcHis(Long id) {
		bcHisDao.deleteBcHis(id);
	}

	public List<BcHis> getAllBcHis(Map params,PageBounds pageBounds) {
		return (List<BcHis>) bcHisDao.findAll(params, pageBounds);
	}
	
	public List<BcHis> getIncompleteCSRecords() {
		return (List<BcHis>) bcHisDao.findIncompleteCSRecords();
	}

	public BcHis getBcHisByConditionId(Map<String, Object> params){
		return bcHisDao.getBcHisByConditionId(params);
	}
}
