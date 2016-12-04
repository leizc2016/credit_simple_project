package com.pactera.pds.u2.commerce.service;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.pactera.pds.u2.commerce.entity.mybatis.BCUser;
import com.pactera.pds.u2.commerce.service.account.BCAccountService;
import com.pactera.pds.u2.commerce.service.insdatamgr.InstitutionUploadedFileService;
import com.pactera.pds.u2.commerce.service.storage.SimpleFileService;

@Component("JHHCrawlDataService")
@Transactional
public class JHHCrawlDataService {
	private static Logger logger = LoggerFactory.getLogger(JHHCrawlDataService.class);
	
	@Autowired
    private BCAccountService bcAccountService;
	@Autowired
	private SimpleFileService simpleFileService;
	@Autowired
    private InstitutionUploadedFileService insFileService;
	
	private StringBuffer errorMsg;
	public void crawlData() {
		JSONObject data = dmpJHHApi();
		
		if (!validateJson(data,errorMsg)){
			logger.error("Origina Json content is: " + data.toString());
			logger.error("Error Message is: " + errorMsg.toString());
			return;
		}
		
		String type = data.getString("data_type");
		JSONArray dataArray = data.getJSONArray("data");
		
		BCUser user = bcAccountService.findUserByLoginName("admin");
		
		try {
			saveJsonToTxt(type, dataArray, user);
		} catch (Exception e) {
			logger.error("Failed to save Json to txt file.");
			e.printStackTrace();
			return;
		}

	}
	
	private void saveJsonToTxt(String type, JSONArray dataArray, BCUser user) throws Exception {
		OutputStreamWriter output = null;
		BufferedWriter bw = null;
		String path = simpleFileService.getRootFilePath() + "/txt/";
		String fileName = user.getInsCode() + System.currentTimeMillis() + ".txt";
		String filePath = path + fileName;
		output = new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8");
		bw = new BufferedWriter(output);

		int iSize = dataArray.size();
		System.out.println("Size:" + iSize);
		for (int i = 0; i < iSize; i++) {
			JSONObject jsonObj = dataArray.getJSONObject(i);
			convertJsonToline(type, jsonObj, bw);
			if (i < (iSize - 1)){
				bw.newLine();
			}
		}
		bw.close();
		output.close();
		
		MultipartFile mFile = insFileService.convertFileToMultipartFile(filePath);
		
		insFileService.saveJsonFile(mFile, user, type);
		
	}

	private void convertJsonToline(String fileType, JSONObject jsonObj,
			BufferedWriter bw) throws Exception {
		if ("1".endsWith(fileType)) {
			if (jsonObj.containsKey("id_card_num")){
				bw.write(jsonObj.getString("id_card_num"));
			} else{
				bw.write("");
			}
			bw.write("|");
			if (jsonObj.containsKey("id_card_type")){
				bw.write(jsonObj.getString("id_card_type"));
			} else{
				bw.write("");
			}
			bw.write("|");
			if (jsonObj.containsKey("full_name")){
				bw.write(jsonObj.getString("full_name"));
			} else{
				bw.write("");
			}
			bw.write("|");
			if (jsonObj.containsKey("app_datetime")){
				bw.write(jsonObj.getString("app_datetime"));
			} else{
				bw.write("");
			}
			bw.write("|");
			if (jsonObj.containsKey("apply_amount_min")){
				bw.write(jsonObj.getString("apply_amount_min"));
			} else{
				bw.write("");
			}
			bw.write("|");
			if (jsonObj.containsKey("approval_amount_max")){
				bw.write(jsonObj.getString("approval_amount_max"));
			} else{
				bw.write("");
			}
			bw.write("|");
			if (jsonObj.containsKey("app_type")){
				bw.write(jsonObj.getString("app_type"));
			} else{
				bw.write("");
			}
			bw.write("|");
			if (jsonObj.containsKey("approval_amount_min")){
				bw.write(jsonObj.getString("approval_amount_min"));
			} else{
				bw.write("");
			}
			bw.write("|");
			if (jsonObj.containsKey("approval_amount_max")){
				bw.write(jsonObj.getString("approval_amount_max"));
			} else{
				bw.write("");
			}
			bw.write("|");
			if (jsonObj.containsKey("loan_account")){
				bw.write(jsonObj.getString("loan_account"));
			} else{
				bw.write("");
			}
			bw.write("|");
			if (jsonObj.containsKey("loan_start_date")){
				bw.write(jsonObj.getString("loan_start_date"));
			} else{
				bw.write("");
			}
			
			bw.write("|");
			if (jsonObj.containsKey("loan_end_date")){
				bw.write(jsonObj.getString("loan_end_date"));
			} else{
				bw.write("");
			}
			bw.write("|");
			if (jsonObj.containsKey("app_pro_city")){
				bw.write(jsonObj.getString("app_pro_city"));
			} else{
				bw.write("");
			}
			bw.write("|");
			if (jsonObj.containsKey("home_addr")){
				bw.write(jsonObj.getString("home_addr"));
			} else{
				bw.write("");
			}
			bw.write("|");
			if (jsonObj.containsKey("cellphone_num")){
				bw.write(jsonObj.getString("cellphone_num"));
			} else{
				bw.write("");
			}
			bw.write("|");
			if (jsonObj.containsKey("ip_addr")){
				bw.write(jsonObj.getString("ip_addr"));
			} else{
				bw.write("");
			}
			bw.write("|");
			if (jsonObj.containsKey("relation_type")){
				bw.write(jsonObj.getString("relation_type"));
			} else{
				bw.write("");
			}
			bw.write("|");
			if (jsonObj.containsKey("relation_card_num")){
				bw.write(jsonObj.getString("relation_card_num"));
			} else{
				bw.write("");
			}
			bw.write("|");
			if (jsonObj.containsKey("relation_card_type")){
				bw.write(jsonObj.getString("relation_card_type"));
			} else{
				bw.write("");
			}
			bw.write("|");
			if (jsonObj.containsKey("relation_name")){
				bw.write(jsonObj.getString("relation_name"));
			} else{
				bw.write("");
			}
		} else if ("2".endsWith(fileType)) {
			if (jsonObj.containsKey("id_card_num")){
				bw.write(jsonObj.getString("id_card_num"));
			} else{
				bw.write("");
			}
			bw.write("|");
			if (jsonObj.containsKey("id_card_type")){
				bw.write(jsonObj.getString("id_card_type"));
			} else{
				bw.write("");
			}
			bw.write("|");
			if (jsonObj.containsKey("full_name")){
				bw.write(jsonObj.getString("full_name"));
			} else{
				bw.write("");
			}
			bw.write("|");
			if (jsonObj.containsKey("loan_acc_id")){
				bw.write(jsonObj.getString("loan_acc_id"));
			} else{
				bw.write("");
			}
			bw.write("|");
			if (jsonObj.containsKey("loan_balance_min")){
				bw.write(jsonObj.getString("loan_balance_min"));
			} else{
				bw.write("");
			}
			bw.write("|");
			if (jsonObj.containsKey("loan_balance_max")){
				bw.write(jsonObj.getString("loan_balance_max"));
			} else{
				bw.write("");
			}
			bw.write("|");
			if (jsonObj.containsKey("recent_repay_date")){
				bw.write(jsonObj.getString("recent_repay_date"));
			} else{
				bw.write("");
			}
			bw.write("|");
			if (jsonObj.containsKey("recent_repay_min")){
				bw.write(jsonObj.getString("recent_repay_min"));
			} else{
				bw.write("");
			}
			bw.write("|");
			if (jsonObj.containsKey("recent_repay_max")){
				bw.write(jsonObj.getString("recent_repay_max"));
			} else{
				bw.write("");
			}
			bw.write("|");
			if (jsonObj.containsKey("loan_status")){
				bw.write(jsonObj.getString("loan_status"));
			} else{
				bw.write("");
			}
			
		} else if ("4".endsWith(fileType)) {
			if (jsonObj.containsKey("id_card_num")){
				bw.write(jsonObj.getString("id_card_num"));
			} else{
				bw.write("");
			}
			bw.write("|");
			if (jsonObj.containsKey("id_card_type")){
				bw.write(jsonObj.getString("id_card_type"));
			} else{
				bw.write("");
			}
			bw.write("|");
			if (jsonObj.containsKey("full_name")){
				bw.write(jsonObj.getString("full_name"));
			} else{
				bw.write("");
			}
			bw.write("|");
			if (jsonObj.containsKey("type")){
				bw.write(jsonObj.getString("type"));
			} else{
				bw.write("");
			}
			bw.write("|");
			if (jsonObj.containsKey("content")){
				bw.write(jsonObj.getString("content"));
			} else{
				bw.write("");
			}
			bw.write("|");
			if (jsonObj.containsKey("note_time")){
				bw.write(jsonObj.getString("note_time"));
			} else{
				bw.write("");
			}
		}
		bw.write("|END");
		
	}

	private boolean validateJson(JSONObject data, StringBuffer errorMsg) {
		int failedCount = 0;
		
		if(data.isNullObject()){
			failedCount++;
			errorMsg.append("JSON文件为空");
			return false;
		}
		
		if (!data.containsKey("data_type")) {
			failedCount++;
			errorMsg.append("JOSN文件中未发现data_type");
		} else {
			String type = data.getString("data_type");
			if (!"1".equals(type) && !"2".equals(type) && !"4".equals(type)){
				failedCount++;
				errorMsg.append("data_type必须是1,2或者4");
			}
		}
		
		if(!data.containsKey("data")){
			failedCount++;
			errorMsg.append("JOSN文件中未发现data");
		} else{
			JSONArray jsonArray = data.getJSONArray("data");
			
			if(!jsonArray.isArray()){
				failedCount++;
				errorMsg.append("JOSN文件中data不是数组");
			}
		}
		
		if (failedCount > 0) {
			return false;
		}
		
		return true;
	}

	private JSONObject dmpJHHApi(){
		String applyInfo = "{\"token\": \"58c81c40b0c34aaf8802d7c698e12f0f\",\"data_type\": \"1\",\"data\": [ {\"id_card_type\": \"0\", \"id_card_num\": \"341122198612062616\", \"full_name\": \"李磊\", \"app_type\": \"N\", \"app_datetime\": \"20150817\",\"apply_amount_min\": \"4000\",\"apply_amount_max\":\"5000\",\"approval_amount_min\": \"3600\", \"approval_amount_max\": \"3800\",\"loan_account\": \"102569\", \"loan_start_date\": \"20150901\", \"loan_end_date\": \"20151201\",\"app_pro_city\": \"上海\",\"home_addr\": \"上海市黄浦区北京东路355号\", \"cellphone_num\": \"13060256963\",\"ip_add\": \"192.168.2.1\",\"relation_type\": \"G\",\"relation_card_type\": \"0\",\"relation_card_num\":\"310110198801011000\",\"relation_name\": \"李磊\" } ]}";
		String accInfo = "{\"token\": \"58c81c40b0c34aaf8802d7c698e12f0f\",\"data_type\": \"2\",\"data\": [{\"id_card_type\": \"0\",\"id_card_num\": \"341122198612062616\",\"full_name\": \"刘楠\",\"loan_acc_id\": \"634663\",\"loan_balance_min\": \"6000\",\"loan_balance_max\": \"5000\",\"recent_repay_date\": \"20140628\",\"recent_repay_min\": \"3500\",\"recent_repay_max\": \"3500\",\"loan_status\": \"N\"}]}";
		String comInfo = "{\"token\": \"58c81c40b0c34aaf8802d7c698e12f0f\",\"data_type\": \"4\",\"data\": [{\"id_card_type\": \"0\",\"id_card_num\": \"341122198612062616\",\"full_name\": \"刘楠\",\"type\": \"1\",\"content\": \"借款人已丧失还款能力\",\"note_time\": \"20150809\"},{\"id_card_type\": \"1\",\"id_card_num\": \"341122198612062616\",\"full_name\": \"刘楠\",\"type\": \"1\",\"content\": \"不开心\",\"note_time\": \"20150809\"}]}";
		
		return JSONObject.fromObject(applyInfo);
		
	}
}
