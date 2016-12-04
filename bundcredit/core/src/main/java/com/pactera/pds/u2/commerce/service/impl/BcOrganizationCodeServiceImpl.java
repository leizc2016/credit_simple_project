package com.pactera.pds.u2.commerce.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pactera.pds.u2.commerce.entity.enterpriseinfo.OrgCodeBean;
import com.pactera.pds.u2.commerce.repository.mybatis.BcOrganizationCodeDao;
import com.pactera.pds.u2.commerce.service.BcOrganizationCodeService;

@Transactional
@Service("bcOrganizationCodeService")
public class BcOrganizationCodeServiceImpl implements BcOrganizationCodeService {

	@Autowired
	private BcOrganizationCodeDao bcOrganizationCodeDao;
	
	@Override
	public OrgCodeBean queryBcOrganizationCodeById(OrgCodeBean  orgCodeBean) {
		// TODO Auto-generated method stub
		return bcOrganizationCodeDao.queryBcOrganizationCodeById(orgCodeBean);
	}

	@Override
	public void saveBcOrganizationCode(OrgCodeBean orgCodeBean,String bussinessName) {
		// TODO Auto-generated method stub
		if(orgCodeBean!=null&&StringUtils.isNotBlank(orgCodeBean.getOrgCode())){
			orgCodeBean.setBusinessName(bussinessName);
			 bcOrganizationCodeDao.saveBcOrganizationCode(orgCodeBean);
		}
		
	}

}
