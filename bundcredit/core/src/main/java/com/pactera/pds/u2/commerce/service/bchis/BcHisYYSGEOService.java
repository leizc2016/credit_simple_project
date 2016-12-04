
package com.pactera.pds.u2.commerce.service.bchis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pactera.pds.u2.commerce.entity.BcHisYYSGEO;
import com.pactera.pds.u2.commerce.repository.mybatis.BcHisYYSGEODao;

@Component
// 类中所有public函数都纳入事务管理的标识.
@Transactional
public class BcHisYYSGEOService {
	
	@Autowired
	private BcHisYYSGEODao bcHisYYSGEODao;
	
	public BcHisYYSGEO queryBcHisYYSGEOById(long id){
		return bcHisYYSGEODao.queryBcHisYYSGEOById(id);
	}
	
}
