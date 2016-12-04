package com.pactera.pds.u2.commerce.service.financialstable;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.pactera.pds.u2.commerce.entity.financialstable.CompanyInfoBean;
import com.pactera.pds.u2.commerce.repository.mybatis.CompanyInfoDao;

@Component
public class CompanyInfoService {
	@Autowired
	private CompanyInfoDao companyInfoDao;
	public List<CompanyInfoBean> getAllCompanyInfos(Map params,PageBounds pb) {
		return (List<CompanyInfoBean>) companyInfoDao.findAll(params,pb);
	}
}
