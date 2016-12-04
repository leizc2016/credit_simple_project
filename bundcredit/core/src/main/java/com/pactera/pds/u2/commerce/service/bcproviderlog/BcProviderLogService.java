
package com.pactera.pds.u2.commerce.service.bcproviderlog;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.pactera.pds.u2.commerce.entity.BcHisYYSGEO;
import com.pactera.pds.u2.commerce.entity.BcProviderLog;
import com.pactera.pds.u2.commerce.entity.mybatis.BCUser;
import com.pactera.pds.u2.commerce.repository.mybatis.BcProviderLogMybatisDao;

@Component
// 类中所有public函数都纳入事务管理的标识.
@Transactional
public class BcProviderLogService {
	
	private BcProviderLogMybatisDao bcProviderLogDao;
	
	@Autowired
	public void setBcProviderLogDao(BcProviderLogMybatisDao bcProviderLogDao) {
		this.bcProviderLogDao = bcProviderLogDao;
	}

	public void saveBcProviderLog(BcProviderLog entity) {
		bcProviderLogDao.saveBcProviderLog(entity);
	}
	
	public void updateBcProviderLog(BcProviderLog entity){
		bcProviderLogDao.updateBcProviderLog(entity);
	}
	
	public void deleteBcProviderLog(Long id) {
		bcProviderLogDao.deleteBcProviderLog(id);
	}

	public List<BcProviderLog> getAllBcProviderLog(Map params,PageBounds pageBounds) {
		return (List<BcProviderLog>) bcProviderLogDao.findAll(params, pageBounds);
	}

	public void GEOlog(BcHisYYSGEO resu, BCUser user) {
		JSONObject requestData = new JSONObject();
		requestData.put("CID", resu.getCallNum());
		requestData.put("CIDName", resu.getUserName());
		requestData.put("CID2", resu.getContactNum());
		requestData.put("authID", resu.getAuthCode());	
		
		JSONObject resultBody = new JSONObject();
		resultBody.put("real_name_match", resu.getIsMatch());
		resultBody.put("online_time", resu.getNetTime());
		resultBody.put("consumption_level", resu.getSpendingLevel());
		resultBody.put("loc_worktime", resu.getHomeAddr());
		resultBody.put("loc_resttime", resu.getWorkingSite());
		resultBody.put("call_frequency_level", resu.getContactRate());
		
		BcProviderLog bcProviderLog = new BcProviderLog();
		bcProviderLog.setOperator(user.getLoginName());
		bcProviderLog.setProvider("GEO");
		bcProviderLog.setQueryCondition(requestData.toString());
		bcProviderLog.setQueryDate(new Date());
		bcProviderLog.setQueryResult(resultBody.toString());
		saveBcProviderLog(bcProviderLog);
	}
	


}
