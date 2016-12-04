
package com.pactera.pds.u2.commerce.service;

import java.util.List;

import com.pactera.pds.u2.commerce.entity.enterpriseinfo.CourtJudgmentDocBean;

/**
 * 法院文书service
 * @author baifan
 *
 */
public interface BcCourtJudgmentDocService {

	/**
	 * 根据caseCode查询法院文书
	 * @param caseCode
	 * @return
	 */
	public List<CourtJudgmentDocBean> queryBcCourtJudgmentDocByBusinessName(String businessName);
	
	/**
	 * 保存法院文书表
	 * @param courtJudgmentDocBean
	 */
	public void saveBcCourtJudgmentDoc(CourtJudgmentDocBean courtJudgmentDocBean);
}
