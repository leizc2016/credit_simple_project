package com.pactera.pds.u2.commerce.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pactera.pds.u2.commerce.entity.enterpriseinfo.BusinessInfoBean;
import com.pactera.pds.u2.commerce.entity.enterpriseinfo.ChangeRecordBean;
import com.pactera.pds.u2.commerce.entity.enterpriseinfo.PartnerInformationBean;
import com.pactera.pds.u2.commerce.repository.mybatis.BcChangeRecordsInfoDao;
import com.pactera.pds.u2.commerce.repository.mybatis.BcEnterpriseInfoDao;
import com.pactera.pds.u2.commerce.repository.mybatis.BcPartnerInfoDao;
import com.pactera.pds.u2.commerce.service.BcEnterpriseInfoService;


@Transactional
@Service("bcEnterpriseInfoService")
public class BcEnterpriseInfoServiceImpl implements BcEnterpriseInfoService {

	@Autowired
	private BcEnterpriseInfoDao bcEnterpriseInfoDao;
	@Autowired
	private BcPartnerInfoDao bcPartnerInfoDao;
	@Autowired
	private BcChangeRecordsInfoDao bcChangeRecordsInfoDao;
	@Override
	public BusinessInfoBean queryBcEnterpriseInfoById(String regId) {
		// TODO Auto-generated method stub
		BusinessInfoBean bean=new BusinessInfoBean();
		bean=bcEnterpriseInfoDao.queryBcEnterpriseInfoById(regId);
		if(bean!=null){
			bean.setPartnerInformationBeans(bcPartnerInfoDao.queryBcPartnerInfoByRegId(regId));
		}
		return bean;
	}

	@Override
	public void saveBcEnterpriseInfo(BusinessInfoBean businessInfoBean) {
		// TODO Auto-generated method stub
		if(businessInfoBean!=null&&StringUtils.isNotBlank(businessInfoBean.getRegID())){
			bcEnterpriseInfoDao.saveBcEnterpriseInfo(businessInfoBean);
			
			List<PartnerInformationBean> list = businessInfoBean.getPartnerInformationBeans();
			if(list!=null&&list.size()>0){
				for (PartnerInformationBean partnerInformationBean : list) {
					partnerInformationBean.setRegId(businessInfoBean.getRegID());
					bcPartnerInfoDao.saveBcPartnerInfo(partnerInformationBean);
				}
				
			}
			//保存变更信息
			List<ChangeRecordBean> changeRecords = businessInfoBean.getChangeRecordsBeans();
			if(changeRecords!=null&&changeRecords.size()>0){
				for (ChangeRecordBean changeRecordBean : changeRecords) {
					changeRecordBean.setRegId(businessInfoBean.getRegID());
					bcChangeRecordsInfoDao.saveBcChangeRecord(changeRecordBean);
				}
				
			}
		}
		 
	}

}
