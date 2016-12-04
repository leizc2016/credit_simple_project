package com.pactera.pds.u2.commerce.repository.mybatis;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.pactera.pds.u2.commerce.entity.financialstable.CompanyInfoBean;

@MyBatisRepository
public interface CompanyInfoDao {
	List<CompanyInfoBean> findAll(Map<String, Object> params,PageBounds pb);
}
