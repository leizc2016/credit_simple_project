package com.pactera.pds.u2.commerce.service;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.pactera.pds.u2.commerce.entity.BcHis;
import com.pactera.pds.u2.commerce.entity.BcHisYYSCS;
import com.pactera.pds.u2.commerce.entity.BcProviderLog;
import com.pactera.pds.u2.commerce.service.bchis.BcHisService;
import com.pactera.pds.u2.commerce.service.bchis.BcHisYYSCSService;
import com.pactera.pds.u2.commerce.service.bcproviderlog.BcProviderLogService;
import com.pactera.pds.u2.commerce.utils.CSHttpsUtil;
import com.pactera.pds.u2.commerce.utils.Sessions;

@Component("CSAsynSearchService")
@Transactional
public class CSAsynSearchService {
	private static Logger logger = LoggerFactory.getLogger(CSAsynSearchService.class);
	
	@Autowired
	private BcHisService hisService;
	
	@Autowired
	private BcHisYYSCSService bcHisService;
	
    @Autowired
    private BcProviderLogService bcProviderLogService;
	
	public void doCSAsynQuery() {
		
		List<BcHis> hisList = hisService.getIncompleteCSRecords();
		
		for (BcHis his : hisList) {
			long queryID = his.getConditionId();
			
			BcHisYYSCS hisCS = bcHisService.findByID(queryID);
			
			JSONObject asynResObj = doCSAsynSearch(hisCS);
			
			String code = asynResObj.getString("code");
			
			if ("0".equals(code)){
				// Update Asyn data to DB
				hisCS.setAsynData(asynResObj.toString());
				
				bcHisService.updateBcHisYYSCS(hisCS);
				
				// Set bc_his flag to 4. It means Asyn search succeed.
				his.setFlag(2);
				
				hisService.updateBcHis(his);
				
			} else if("-3".equals(code)){
				continue;
			} else{
				// Update Asyn data to DB
				hisCS.setAsynData(asynResObj.toString());
				
				bcHisService.updateBcHisYYSCS(hisCS);
				
				// Set bc_his flag to 4. It means Asyn search failed.
				his.setFlag(4);
				hisService.updateBcHis(his);
				logger.error("CS异步查询出错。CaseID: " + hisCS.getAsynCode() + "Result: " + asynResObj.toString());
			}
		}

	}
	
	private JSONObject doCSAsynSearch(BcHisYYSCS hisCS) {
		JSONObject requestData = new JSONObject();
		requestData.put("s_shouji", hisCS.getCellNum());
		requestData.put("s_caseid", hisCS.getAsynCode());
		
		JSONObject requestResultData = null;
		
		try {
			requestResultData = JSONObject.fromObject(CSHttpsUtil.asyncQuery(requestData));
		} catch (Exception e) {
			requestResultData.put("code", "100");

		}
		
		saveProviderLog(requestData, requestResultData);
		
		if (StringUtils.isEmpty(requestResultData)) {
			requestResultData.put("code", "100");
		}
		
		return requestResultData;
	}
	
	private void saveProviderLog(JSONObject requestData, JSONObject requestResultData) {

		// Save provider log
		BcProviderLog bcProviderLog = new BcProviderLog();
		bcProviderLog.setQueryDate(new Date());
		if (requestResultData != null) {
			bcProviderLog.setQueryCondition(requestData.toString());
		} else {
			bcProviderLog.setQueryCondition("");
		}
		if (null != requestResultData) {
			bcProviderLog.setQueryResult(requestResultData.toString());
		} else {
			bcProviderLog.setQueryResult("");
		}
		bcProviderLog.setProvider("CS");
		bcProviderLog.setOperator("ScheduleJob");
		bcProviderLogService.saveBcProviderLog(bcProviderLog);
	}

}
