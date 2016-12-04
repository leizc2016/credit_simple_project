package com.pactera.pds.u2.commerce.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pactera.pds.u2.commerce.entity.enterpriseinfo.DishPersonBean;
import com.pactera.pds.u2.commerce.repository.mybatis.BcCourtDishonestExecutorDao;
import com.pactera.pds.u2.commerce.service.BcCourtDishonestExecutorService;

@Transactional
@Service("bcCourtDishonestExecutorService")
public class BcCourtDishonestExecutorServiceImpl implements
		BcCourtDishonestExecutorService {

	@Autowired
	private BcCourtDishonestExecutorDao bcCourtDishonestExecutorDao;

	@Override
	public List<DishPersonBean> queryBcCourtDishonestExecutorByBusinessName(DishPersonBean dishPersonBean) {
		// TODO Auto-generated method stub
		return bcCourtDishonestExecutorDao.queryBcCourtDishonestExecutorByBusinessName(dishPersonBean);
	}

	@Override
	public void saveBcCourtDishonestExecutors(List<DishPersonBean> dishPersonBeans,String businessName) {
		// TODO Auto-generated method stub
		if(dishPersonBeans!=null&&dishPersonBeans.size()>0){
			for (DishPersonBean dishPersonBean : dishPersonBeans) {
				if(dishPersonBean!=null&&StringUtils.isNotBlank(dishPersonBean.getCode())){
					dishPersonBean.setBusinessName(businessName);
					bcCourtDishonestExecutorDao.saveBcCourtDishonestExecutor(dishPersonBean);
				}
				
			}
		}
	}
	

}
