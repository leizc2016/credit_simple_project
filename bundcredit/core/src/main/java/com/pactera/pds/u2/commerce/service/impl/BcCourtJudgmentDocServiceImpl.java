package com.pactera.pds.u2.commerce.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pactera.pds.u2.commerce.entity.enterpriseinfo.CourtJudgmentDocBean;
import com.pactera.pds.u2.commerce.repository.mybatis.BcCourtJudgmentDocDao;
import com.pactera.pds.u2.commerce.service.BcCourtJudgmentDocService;

@Transactional
@Service("bcCourtJudgmentDocService")
public class BcCourtJudgmentDocServiceImpl implements BcCourtJudgmentDocService {

	@Autowired
	private BcCourtJudgmentDocDao bcCourtJudgmentDocDao;
	
	@Override
	public List<CourtJudgmentDocBean> queryBcCourtJudgmentDocByBusinessName(String businessName) {
		// TODO Auto-generated method stub
		return bcCourtJudgmentDocDao.queryBcCourtJudgmentDocByBusinessName(businessName);
	}

	@Override
	public void saveBcCourtJudgmentDoc(CourtJudgmentDocBean courtJudgmentDocBean) {
		// TODO Auto-generated method stub
		if(courtJudgmentDocBean!=null&&StringUtils.isNotBlank(courtJudgmentDocBean.getCaseCode())){
			String caseCode=courtJudgmentDocBean.getCaseCode().replaceAll("(", "").replaceAll(")", "").replaceAll("（", "").replaceAll("）", "").replaceAll(" ", "").replaceAll("O", "0").replaceAll("o", "0");
			courtJudgmentDocBean.setCaseCode(caseCode);
			bcCourtJudgmentDocDao.saveBcCourtJudgmentDoc(courtJudgmentDocBean);
		}
	}

}
