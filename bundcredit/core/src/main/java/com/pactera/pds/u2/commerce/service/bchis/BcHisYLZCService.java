
package com.pactera.pds.u2.commerce.service.bchis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.pactera.pds.u2.commerce.entity.BcHisYLZC;
import com.pactera.pds.u2.commerce.repository.mybatis.BcHisYLZCMybatisDao;
import com.pactera.pds.u2.commerce.service.car.CreditSearchService;
import com.pactera.pds.u2.commerce.utils.MenuConstant;
import com.pactera.pds.u2.commerce.utils.PropertiesInfo;
import com.pactera.pds.u2.commerce.utils.Sessions;
import com.pactera.pds.u2.commerce.utils.UnionPayZCUtil;
import com.pactera.pds.u2.commerce.utils.WSClient;

@Component
// 类中所有public函数都纳入事务管理的标识.
@Transactional
public class BcHisYLZCService {
	
	private static String URL=PropertiesInfo.getYlzcApiUrl();
	
	private static final String WSURL = URL+"apiservice/unionPayZCQueryService?wsdl";
	
	@Autowired
	CreditSearchService creditSearchService;
	
	private BcHisYLZCMybatisDao BcHisYLZCMybatisDao;
	
	public BcHisYLZCMybatisDao getBcHisYLZCMybatisDao() {
		return BcHisYLZCMybatisDao;
	}
	@Autowired
	public void setBcHisYLZCMybatisDao(BcHisYLZCMybatisDao bcHisYLZCMybatisDao) {
		BcHisYLZCMybatisDao = bcHisYLZCMybatisDao;
	}

	public void updateBcHisYLZC(BcHisYLZC entity){
		BcHisYLZCMybatisDao.updateBcHisYLZC(entity);
	}
	
	public long saveBcHisYLZC(BcHisYLZC entity){
		return BcHisYLZCMybatisDao.saveBcHisYLZC(entity);
	}
	
	public void deleteBcHisYLZC(Long id) {
		BcHisYLZCMybatisDao.deleteBcHisYLZC(id);
	}

	public List<BcHisYLZC> getAllBcHisYLZC(Map params,PageBounds pageBounds) {
		return (List<BcHisYLZC>) BcHisYLZCMybatisDao.findAll(params, pageBounds);
	}
	
	public BcHisYLZC findByID(long id) {
		return BcHisYLZCMybatisDao.getBcHisYLZCById(id);
	}

	public BcHisYLZC getBcHisYLZCById(long id){
		return BcHisYLZCMybatisDao.getBcHisYLZCById(id);
	}
	
	public String checkAuthCode(Map<String, Object> condition){
		return BcHisYLZCMybatisDao.checkAuthCode(condition);
	}
	
	
	public Map<String, Object> retreiveYLZC(BcHisYLZC entity) {
		Map<String, Object> resultsmap = new HashMap<String, Object>();
		Map<String, Object> paramsmap = new HashMap<String, Object>();
		paramsmap.put("s_idcard_num", entity.getIdCardNum());
		paramsmap.put("s_bankcard_num", entity.getBankCardNum());
		JSONObject jobject = JSONObject.fromObject(paramsmap);
		String invokeresults = null;
		if (PropertiesInfo.isUatFlag()) {
			invokeresults = UnionPayZCUtil.doQuery();
		} else{
			invokeresults = WSClient.invokeWSClient(BcHisYLZCService.WSURL, "http://service.bundcredit.com/", "query", entity.getBankCardNum());
		}
		resultsmap.put("results", invokeresults);
		resultsmap.put("paramjobject", jobject);
		return resultsmap;
	}
	
	public String chargeYLZC(BcHisYLZC ylEntity, String insCode) {
		Boolean cashflag = creditSearchService.checkIsCashPay(MenuConstant.yinLianZCCode);
		Float charge = creditSearchService.calculateCharge(MenuConstant.yinLianZCCode, false, insCode);
		
    	if(!creditSearchService.enoughBalance(insCode, MenuConstant.yinLianZCCode, false)){
    		return "查询失败，余额不足";
    	}
		
		String description = "银联信息查询：" + hideNumber(ylEntity.getBankCardNum());
		creditSearchService.commitFee(charge, insCode, cashflag, description);
		return null;
	}
	
	public static String hideNumber(String bankCardNum) {
		if(!StringUtils.isEmpty(bankCardNum)){
			bankCardNum = new StringBuffer().append(bankCardNum.substring(0,3)).append("****").append(bankCardNum.substring(12)).toString();
		}
    	return bankCardNum;
	}
	
	public static BcHisYLZC translateScore(BcHisYLZC ylEntity){
		BcHisYLZC entity = new BcHisYLZC();
		
		entity = ylEntity;
		entity.setBankCardType("credit");
		entity.setCommonLocation("上海");
		entity.setCstScore("长期忠诚客户");
		entity.setChvScore("高端人群");
		entity.setCotScore("低套现风险");
		entity.setCnpScore("生意达人");
		entity.setWlpScore("首选卡");
		entity.setSummaryScore(720);
		return entity;
		
	}
	
	
}
