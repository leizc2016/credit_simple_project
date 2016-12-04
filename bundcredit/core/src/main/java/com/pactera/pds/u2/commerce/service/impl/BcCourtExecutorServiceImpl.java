package com.pactera.pds.u2.commerce.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pactera.pds.u2.commerce.entity.enterpriseinfo.BeExecutorBean;
import com.pactera.pds.u2.commerce.repository.mybatis.BcCourtExecutorDao;
import com.pactera.pds.u2.commerce.service.BcCourtExecutorService;

@Transactional
@Service("bcCourtExecutorService")
public class BcCourtExecutorServiceImpl implements BcCourtExecutorService {

	@Autowired
	private BcCourtExecutorDao bcCourtExecutorDao;
	
	@Override
	public  List<BeExecutorBean> queryBcCourtExecutorByBusinessName(String businessName){
		// TODO Auto-generated method stub
		return bcCourtExecutorDao.queryBcCourtExecutorByBusinessName(businessName);
	}

	@Override
	public void saveBcCourtExecutors(List<BeExecutorBean> beExecutorBeans,String businessName) {
		// TODO Auto-generated method stub
		if(beExecutorBeans!=null&&beExecutorBeans.size()>0){
			for (BeExecutorBean beExecutorBean : beExecutorBeans) {
				if(beExecutorBean!=null&&StringUtils.isNotBlank(beExecutorBean.getCode())){
					beExecutorBean.setBusinessName(businessName);
					bcCourtExecutorDao.saveBcCourtExecutor(beExecutorBean);
				}
				
			}
		}
	}

	@Override
	public List<BeExecutorBean> queryBcCourtExecutorByCode(String code) {
		// TODO Auto-generated method stub
		return bcCourtExecutorDao.queryBcCourtExecutorByCode(code);
	}

}
