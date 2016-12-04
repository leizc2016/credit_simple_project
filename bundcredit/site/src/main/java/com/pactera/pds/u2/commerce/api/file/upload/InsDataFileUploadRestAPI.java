package com.pactera.pds.u2.commerce.api.file.upload;

import static com.pactera.pds.u2.commerce.utils.ConstantUtil.STATUS_CODE_MAP;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;

import javax.validation.Validator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springside.modules.beanvalidator.BeanValidators;
import org.springside.modules.web.MediaTypes;

import com.pactera.pds.u2.commerce.entity.mybatis.BCUser;
import com.pactera.pds.u2.commerce.service.account.BCAccountService;
import com.pactera.pds.u2.commerce.service.insdatamgr.InstitutionUploadedFileService;
import com.pactera.pds.u2.commerce.service.storage.SimpleFileService;
import com.pactera.pds.u2.commerce.utils.Schedulee;

@RestController
@RequestMapping(value = "/api")
public class InsDataFileUploadRestAPI {
	
	private static Logger logger = LoggerFactory.getLogger(InsDataFileUploadRestAPI.class);
	
	@Autowired
	private Validator validator;
	@Autowired
    private BCAccountService bcAccountService;
	@Autowired
    private Schedulee schedulee;
	@Autowired
	private SimpleFileService simpleFileService;
	@Autowired
    private InstitutionUploadedFileService insFileService;
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaTypes.JSON_UTF_8)
	public String fileUpload(@RequestBody Object data) {
		
		BeanValidators.validateWithException(validator, data);
		
		JSONObject requestData = JSONObject.fromObject(data);
		JSONObject result = new JSONObject();
//		JSONObject head = new JSONObject();
//		JSONObject body = new JSONObject();
		if (!validateJson(requestData, result)){
			return result.toString();
		}
		
		String token = requestData.getString("token");
		String type = requestData.getString("data_type");
		JSONArray jsonArray = requestData.getJSONArray("data");
		
		if (isTokenExpired(token)) {
			result.put("statusCode", "YW001");
			result.put("msg", STATUS_CODE_MAP.get("YW001"));
//			result.put("head", head);
//			result.put("body", body);
			return result.toString();
		}
		
		BCUser user = bcAccountService.findUserByUUID(token);
		if(!validateRole(user)){
			result.put("status", "10003");
			result.put("msg", STATUS_CODE_MAP.get("10003"));
			return result.toString();
		}
		
		try {
			saveJsonToTxt(type, jsonArray, user);
		} catch (Exception e) {
			logger.error("Failed to save Json to txt file.");
			logger.error(e.getLocalizedMessage());
			e.printStackTrace();
			result.put("statusCode", "YW006");
			result.put("msg", STATUS_CODE_MAP.get("YW006"));
//			result.put("head", head);
//			result.put("body", body);
			return result.toString();
		}
		
		result.put("statusCode", "YW000");
		result.put("msg", STATUS_CODE_MAP.get("YW000"));
//		result.put("head", head);
//		result.put("body", body);
		return result.toString();
	}
	
	/**
     * 判断数据上传用户权限：
     * 只有管理员和数据上传权限可以执行上传操作
     * @param user
     * @return
     */
    private boolean validateRole(BCUser user) {
		// TODO Auto-generated method stub
    	if("insdata".equals(user.getRoles())
    			|| "admin".equals(user.getRoles())){
    		return true;
    	}
    	
		return false;
	}
	
	private boolean validateJson(JSONObject requestData, JSONObject result) {
//		JSONObject resultBody = new JSONObject();
//		JSONObject resultHead = new JSONObject();
		String errorCode = "YW002";
		StringBuilder errorMessage = new StringBuilder();
		errorMessage.append(STATUS_CODE_MAP.get(errorCode) + ": ");
		int failedCount = 0;
		
		if(requestData.isNullObject()){
			failedCount++;
			errorMessage.append("JSON文件为空");
			
			result.put("statusCode", errorCode);
			String errorMsg = errorMessage.toString().trim();
//			errorMsg = errorMsg.substring(0, errorMsg.length()-1);
			result.put("msg", errorMsg);
//			result.put("head", resultHead);
//			result.put("body", resultBody);
			return false;
		}
		
		if(!requestData.containsKey("token")){
			failedCount++;
			errorMessage.append("JOSN文件中未发现token");
		}
		
		if (!requestData.containsKey("data_type")) {
			failedCount++;
			errorMessage.append("JOSN文件中未发现data_type");
		} else {
			String type = requestData.getString("data_type");
			if (!"1".equals(type) && !"2".equals(type) && !"4".equals(type)){
				failedCount++;
				errorMessage.append("data_type必须是1,2或者4");
			}
		}
		
		if(!requestData.containsKey("data")){
			failedCount++;
			errorMessage.append("JOSN文件中未发现data");
		} else{
			JSONArray jsonArray = requestData.getJSONArray("data");
			
			if(!jsonArray.isArray()){
				failedCount++;
				errorMessage.append("JOSN文件中data不是数组");
			}
		}
		
		if (failedCount > 0) {
			result.put("statusCode", errorCode);
			String errorMsg = errorMessage.toString().trim();
//			errorMsg = errorMsg.substring(0, errorMsg.length()-1);
			result.put("msg", errorMsg);
//			result.put("head", resultHead);
//			result.put("body", resultBody);
			return false;
		}
		
		return true;
	}



	private void saveJsonToTxt(String fileType, JSONArray jsonArray, BCUser user) throws Exception {
		logger.info("Entry saveJsonToTxt method.");
		OutputStreamWriter output = null;
		BufferedWriter bw = null;
		logger.info("Path is: " + simpleFileService.getRootFilePath() + "/txt/");
		String path = simpleFileService.getRootFilePath() + "/txt/";
		String fileName = user.getInsCode() + System.currentTimeMillis() + ".txt";
		logger.info("fileName is" + fileName);
		String filePath = path + fileName;
		logger.info("Will create new file: " + filePath);
		output = new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8");
		bw = new BufferedWriter(output);
		logger.info("Will wirte json to new file: " + filePath);
		int iSize = jsonArray.size();
		logger.info("Json Array Size:" + iSize);
		for (int i = 0; i < iSize; i++) {
			JSONObject jsonObj = jsonArray.getJSONObject(i);
			logger.info("Json object is: " + jsonObj.toString());
			convertJsonToline(fileType, jsonObj, bw);
			if (i < (iSize - 1)){
				bw.newLine();
			}
		}
		bw.close();
		output.close();
		
		logger.info("Change file to Multipart File" + filePath);
		MultipartFile mFile = insFileService.convertFileToMultipartFile(filePath);
		
		logger.info("Sava file DB: " + filePath);
		insFileService.saveJsonFile(mFile, user, fileType);
		logger.info("Sava file to DB succeed: " + filePath);
	}
	
	private void convertJsonToline(String fileType, JSONObject jsonObj, BufferedWriter bw) throws Exception {
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



	private boolean isTokenExpired(String token){
		BCUser user = bcAccountService.findUserByUUID(token);
        long now = System.currentTimeMillis();
        if(user == null || now - user.getLastLoginTime().getTime() > schedulee.getDeadtime()){
    		return true;
        }
        user.setLastLoginTime(new Date());
		bcAccountService.updateuser(user);
		return false;
	}



	public static void main(String[] args) throws Exception {

		RestTemplate restTemplate = new RestTemplate();
		
		String task = "{ \"token\": \"58c81c40b0c34aaf8802d7c698e12f0f\", \"datatype\": \"1\",\"data\": [{\"firstName\": \"John\", \"lastName\": \"Doe\" }, { \"firstName\": \"Anna\",\"lastName\": \"Smith\" },{ \"firstName\": \"Peter\",\"lastName\": \"Jones\" }]}";
		String applyInfo = "{\"token\": \"58c81c40b0c34aaf8802d7c698e12f0f\",\"data_type\": \"1\",\"data\": [ {\"id_card_type\": \"0\", \"id_card_num\": \"341122198612062616\", \"full_name\": \"李磊\", \"app_type\": \"N\", \"app_datetime\": \"20150817\",\"apply_amount_min\": \"4000\",\"apply_amount_max\":\"5000\",\"approval_amount_min\": \"3600\", \"approval_amount_max\": \"3800\",\"loan_account\": \"102569\", \"loan_start_date\": \"20150901\", \"loan_end_date\": \"20151201\",\"app_pro_city\": \"上海\",\"home_addr\": \"上海市黄浦区北京东路355号\", \"cellphone_num\": \"13060256963\",\"ip_add\": \"192.168.2.1\",\"relation_type\": \"G\",\"relation_card_type\": \"0\",\"relation_card_num\":\"310110198801011000\",\"relation_name\": \"李磊\" } ]}";
		String accInfo = "{\"token\": \"58c81c40b0c34aaf8802d7c698e12f0f\",\"data_type\": \"2\",\"data\": [{\"id_card_type\": \"0\",\"id_card_num\": \"341122198612062616\",\"full_name\": \"刘楠\",\"loan_acc_id\": \"634663\",\"loan_balance_min\": \"6000\",\"loan_balance_max\": \"5000\",\"recent_repay_date\": \"20140628\",\"recent_repay_min\": \"3500\",\"recent_repay_max\": \"3500\",\"loan_status\": \"N\"}]}";
		String comInfo = "{\"token\": \"fb551ae29fb8471fa14421830d1e7227\",\"data_type\": \"5\",\"data\": [{\"id_card_type\": \"0\",\"id_card_num\": \"341122198612062616\",\"full_name\": \"刘楠\",\"type\": \"1\",\"content\": \"借款人已丧失还款能力\",\"note_time\": \"20150809\"},{\"id_card_type\": \"1\",\"id_card_num\": \"341122198612062616\",\"full_name\": \"刘楠\",\"type\": \"1\",\"content\": \"不开心\",\"note_time\": \"20150809\"}]}";
		JSONObject obj = JSONObject.fromObject(comInfo);
		String token = restTemplate.getForObject("http://localhost:8080/bundcredit-site/creditreportApi/bundCreditLogin?login_name=845321673@qq.com&login_psw=Gd@123456", String.class);
		JSONObject tokenObj = JSONObject.fromObject(token);
		//将comInfo中的token替换为最新的token
		obj.put("token",tokenObj.getString("token"));
		String result = restTemplate.postForObject("http://127.0.0.1:8080/bundcredit-site/api/upload", obj, String.class);
		System.out.println("Result is: " + result);
	}

}
