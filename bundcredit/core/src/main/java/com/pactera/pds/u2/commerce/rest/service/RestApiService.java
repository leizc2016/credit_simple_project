package com.pactera.pds.u2.commerce.rest.service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.pactera.pds.u2.commerce.entity.BundPersonComment;
import com.pactera.pds.u2.commerce.entity.InstitutionUploadedFile;
import com.pactera.pds.u2.commerce.entity.PersonAccDetail;
import com.pactera.pds.u2.commerce.entity.PersonApplyDetail;
import com.pactera.pds.u2.commerce.entity.PersonBasicInfo;
import com.pactera.pds.u2.commerce.entity.mybatis.BCAccountDetailTmp;
import com.pactera.pds.u2.commerce.entity.mybatis.BCApplyDetailTmp;
import com.pactera.pds.u2.commerce.repository.mybatis.BCInsDataUploadMybatisDao;
import com.pactera.pds.u2.commerce.repository.mybatis.CreditSearchMybatisDao;
import com.pactera.pds.u2.commerce.repository.mybatis.InstitutionUploadedFileMybatisDao;
import com.pactera.pds.u2.commerce.utils.DESUtil;
import com.pactera.pds.u2.commerce.utils.Sessions;


//@Service
@Component("restApiService")
public class RestApiService {
	@Autowired
	private DESUtil des;
	@Autowired
	BCInsDataUploadMybatisDao dataUploadMybatisDao;
	@Autowired
	CreditSearchMybatisDao creditSearchMybatisDao;
	@Autowired
    private InstitutionUploadedFileMybatisDao uploadDao;
    
	public static Map<String, Object> parseJSON2Map(JSONObject jsonStr){ 
		    Map<String, Object> map = new HashMap<String, Object>(); 
		    //最外层解析 
		    JSONObject json =jsonStr;
		    for(Object k : json.keySet()){ 
		      Object v = json.get(k);  
		      //如果内层还是数组的话，继续解析 
		      if(v instanceof JSONArray){ 
		        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>(); 
		        Iterator<JSONObject> it = ((JSONArray)v).iterator(); 
		        while(it.hasNext()){ 
		          JSONObject json2 = it.next(); 
		          list.add(parseJSON2Map(json2)); 
		        } 
		        map.put(k.toString(), list); 
		      } else { 
		        map.put(k.toString(), v); 
		      } 
		    } 
		    return map; 
		  } 
	    
	private String loanstatusSet = "_0_1_2_3_4_5_6_7_C_c_D_d_G_g_O_o_/_*_N_n";
    private String applyTypeSet = "_N_A_H_O_P_E_X_n_a_h_o_p_e_x_";
    private boolean validateOthers4Apply(JSONObject m) {
    	String applyType = m.getString("applyType");
    	if(applyTypeSet.indexOf(applyType) == -1){
    		return false;
    	}
		return true;
	}
    private boolean validateOthers4Acc(JSONObject m) {
    	String loanstatus = m.getString("loanStatus1");
    	if(loanstatusSet.indexOf(loanstatus) == -1){
    		return false;
    	}
		return true;
	}
    private boolean validateNull4Count(JSONObject m) {
		String idCardNum = m.getString("idCardNum");
		String commentType = m.getString("commentType");
		String commentContent = m.getString("content");
		String lastUpdateDatetime  = m.getString("lastUpdateDatetime");
		if(StringUtils.isEmpty(idCardNum) || 
				StringUtils.isEmpty(commentType) || 
				StringUtils.isEmpty(commentContent) || 
				StringUtils.isEmpty(lastUpdateDatetime)){
			return false;
		}
		return true;
	}
	private boolean validateNull4Acc(JSONObject m) {
		String loanAccount = m.getString("loanAccount");
		String idCardType = m.getString("idCardType");
		String idCardNum = m.getString("idCardNum");
		String insCode = m.getString("insCode");
		String lastestRepayDate  = m.getString("nextLoanReplyDate");
		String loanstatus = m.getString("loanStatus1");
		if(StringUtils.isEmpty(loanAccount) || 
				StringUtils.isEmpty(idCardType) || 
				StringUtils.isEmpty(idCardNum) || 
				StringUtils.isEmpty(insCode) || 
				StringUtils.isEmpty(lastestRepayDate) || 
				StringUtils.isEmpty(loanstatus)){
			return false;
		}
		return true;
	}
	
	//验证必填数据是否为空
	private boolean validateNull4Apply(JSONObject m) {
		String loanAccount = m.getString("loanAccount");
		String idCardType = m.getString("idCardType");
		String idCardNum = m.getString("idCardNum");
		String insCode = m.getString("insCode");
		String applyDatetime = m.getString("applyDatetime");
		String applyType = m.getString("applyType");
		if(StringUtils.isEmpty(loanAccount)){
			if(StringUtils.isEmpty(idCardType) || 
					StringUtils.isEmpty(idCardNum) || 
					StringUtils.isEmpty(insCode) || 
					StringUtils.isEmpty(applyDatetime) || 
					StringUtils.isEmpty(applyType) ){
				return false;
			}
		}else{
			if(StringUtils.isEmpty(loanAccount) || 
					StringUtils.isEmpty(idCardType) || 
					StringUtils.isEmpty(idCardNum) || 
					StringUtils.isEmpty(insCode) || 
					StringUtils.isEmpty(applyType) ){
				return false;
			}
		}
		
		return true;
	}
	public static List getJsonData(JSONArray array ){
		List batch=new ArrayList();
//		JSONArray array=JSONArray.fromObject(date);
		for (int i = 0; i < array.size(); i++) {
			Map<String, Object> map=parseJSON2Map(array.getJSONObject(i));
			batch.add(map);
		}
		return batch;
	}
	public boolean dataValidate(JSONArray array ,Integer type){
		for (int i = 0; i < array.size(); i++) {
			JSONObject json=array.getJSONObject(i);
			if(type==1 && (!validateNull4Apply(json) || !validateOthers4Apply(json) || !isRepeat4Apply(json)))
			{
				return false;
			}
			if(type ==2 && (!validateNull4Acc(json) || !validateOthers4Acc(json))){
				return false;
			}
			if(type ==3 && !validateNull4Count(json)){
				return false;
			}
		}
		return true;
	}
	public void getApply(String data,Integer type){
//		String data="";
		JSONArray json=JSONArray.fromObject(data);
		//保存apply_detail
		if (type == 1 && dataValidate(json,type)) {
//			List json=getJsonData(array);
			for (int i = 0; i < json.size(); i++) {
				BCApplyDetailTmp applyTmp=(BCApplyDetailTmp) JSONObject.toBean(json.getJSONObject(i), BCApplyDetailTmp.class);
				try {
					applyTmp.setIdCardNum(des.encrypt(applyTmp.getIdCardNum()));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            PersonBasicInfo b = new PersonBasicInfo();
	            b.fromApplyTmp(applyTmp);
	            PersonApplyDetail p=new PersonApplyDetail();
	            p.fromApplyTmp(applyTmp);
	            saveOrUpdateBasicInfo(b);
	            saveOrUpdateApplyDetail(p);
			}
		}
		
		//保存ACC_DETAIL
		if(type == 2 && dataValidate(json,type)){
			for (int i = 0; i < json.size(); i++) {
			   BCAccountDetailTmp detailTmp=(BCAccountDetailTmp)JSONObject.toBean(json.getJSONObject(i), BCAccountDetailTmp.class);
               PersonAccDetail p=new PersonAccDetail();
               p.fromDetailTmp(detailTmp);
               PersonBasicInfo b = new PersonBasicInfo();
               b.fromDetailTmp(detailTmp);
               saveOrUpdateBasicInfo(b);
               saveOrUpdateAccDetail(p);
			}
		}
		//保存备注
		if(type ==3 && dataValidate(json, type)){
			for (int i = 0; i < json.size(); i++) {
				JSONObject m=json.getJSONObject(i);
				BundPersonComment comment=new BundPersonComment();
				comment.setCommentType(m.getString("commentType"));
				comment.setIdCardNum(m.getString("idCardNum"));
				comment.setContent(m.getString("content"));	
				comment.setLastUpdateDatetime(Timestamp.valueOf(m.getString("lastUpdateDatetime")));
//						(BundPersonComment)JSONObject.toBean(json.getJSONObject(i), BundPersonComment.class);
				creditSearchMybatisDao.addBundComment(comment);
			}
		}
	}
	private void saveOrUpdateApplyDetail(PersonApplyDetail p) {
        creditSearchMybatisDao.saveApplyDetailInfo(p);
    }
	 private void saveOrUpdateBasicInfo(PersonBasicInfo b) {
	        if((""+b.getIdCardNum2()).isEmpty()){
	            return;
	        }
	        if(null == creditSearchMybatisDao.getPersonBasicInfo(b.getIdCardNum2())){
	        	b.setLastUpdateDatetime(new Date());
	            creditSearchMybatisDao.savePersonBasicInfo(b);
	        }else{
	        	creditSearchMybatisDao.updatePersonBasicInfo(b);
	        }
	    }
	  private void saveOrUpdateAccDetail(PersonAccDetail detail) {
	    	PersonAccDetail accInDB = creditSearchMybatisDao.getPersonAccDetail(detail);
	    	String statusIn24Month = "0000000000000000000000";
	    	if(statusIn24Month == null) return;
	    	if(accInDB == null){
	    		detail.setLatest24monStatus(statusIn24Month);
	    		creditSearchMybatisDao.saveAccDetailInfo(detail);
	    	}else{
	    		
	    		accInDB.setLatest24monStatus(statusIn24Month);
	    		accInDB.setNextLoanRepayDate(detail.getNextLoanRepayDate());
	    		accInDB.setLoanStatus(detail.getLoanStatus());
	    		accInDB.setLoanStatus1(detail.getLoanStatus1());
	    		accInDB.setComment1(detail.getComment1());
	    		accInDB.setComment2(detail.getComment2());
	    		creditSearchMybatisDao.updateAccDetailInfo(accInDB);
	    	}
	    }
	  private String getStatusIn24Month(PersonAccDetail detail,
				PersonAccDetail accInDB) {
	    	Date lastPaybackDate = null;
	    	String statusIn24Month = null;
	    	if(accInDB != null){
	    		lastPaybackDate = accInDB.getNextLoanRepayDate();
	    		statusIn24Month = accInDB.getLatest24monStatus();
	    	}else{
	    		lastPaybackDate = detail.getNextLoanRepayDate();
	    		statusIn24Month = "000000000000000000000000";//24个0
	    	}
			Date uploadDate = getById(detail.getUploadFileId()).getUploadDatetime();
			int months = getMonthBetweenDate(lastPaybackDate, uploadDate);
			String newStatus = detail.getLoanStatus();
			if(months==0){
				statusIn24Month = update24MonStatus(newStatus, statusIn24Month);
			}else if(months == 1){
				statusIn24Month = append24MonStatus(newStatus, statusIn24Month);
			}else if(months > 1){
				newStatus = appendXieLine(newStatus, months);
				statusIn24Month = append24MonStatus(newStatus, statusIn24Month);
			}else{
				return null;
				//上传日期 小于 最后还款日期不被处理
			}
			return statusIn24Month;
		}
	  public InstitutionUploadedFile getById(Long fileId) {
	        return uploadDao.get(fileId);
	    }
	    
	    public List<InstitutionUploadedFile> findByInsCode() {
	        return findByInsCode(Sessions.insCode());
	    }
	    public  int getMonthBetweenDate(Date lastPaybackDate,Date uploadDate){
	    	  int months=-1;//相差月份
	    	  Calendar c1 = Calendar.getInstance();
	    	  c1.setTime(lastPaybackDate);
	    	  Calendar c2 = Calendar.getInstance();
	    	  c2.setTime(uploadDate);
	    	  int y1=c1.get(Calendar.YEAR);
	    	  int y2=c2.get(Calendar.YEAR);
	    	  int m1=c1.get(Calendar.MONTH);//起始日期月份
	    	  int m2=c2.get(Calendar.MONTH);;//结束日期月份
	    	  if(lastPaybackDate.getTime()<=uploadDate.getTime()){
	    		 months=m2-m1+(y2-y1)*12;
	    	  }
	    	  return months;
	    	 }
	    public List<InstitutionUploadedFile> findByInsCode(String insCode) {
	        return uploadDao.findByInsCode(insCode);
	    }
	    private String update24MonStatus(String newStatus, String StatusIn24Mon){
	    	StringBuffer sb = new  StringBuffer(StatusIn24Mon);
	    	String last24MonStatus =  sb.replace(0, 1, newStatus).toString();
	    	return last24MonStatus;
	    }
	    private String append24MonStatus(String newStatus, String StatusIn24Mon){
	    	StringBuffer sb = new  StringBuffer(newStatus);
	    	sb.append(StatusIn24Mon);
	    	String last24MonStatus = sb.substring(0, 24);
	    	return last24MonStatus;
	    }
	    private String appendXieLine(String newStatus, int months) {
			for(int i=1; i<months; i++){
				newStatus += "/";
			}
			return newStatus;
		}
	    private boolean isRepeat4Apply(JSONObject m){
//	    	String loanAccount = m.getString("loanAccount");
			String idCardType = m.getString("idCardType");
			String idCardNum = m.getString("idCardNum");
			String insCode = m.getString("insCode");
			String applyDatetime = m.getString("applyDatetime");
			Date applyDatetimeObj = format(applyDatetime);
			try {
			if(	 uploadDao.hasItem4FailedApply(idCardType, des.encrypt(idCardNum), insCode, applyDatetimeObj)>0){
				return false;
			};
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	return true;
	    }
	    private Date format(String str){
	    	if(str == null || "".equals(str)){
	    		return null;
	    	}
	    	DateFormat format = new SimpleDateFormat("yyyyMMdd");
			Date applyDatetimeObj = null;
			try {
				applyDatetimeObj = format.parse(str);
			} catch (ParseException e) {
			}
			return applyDatetimeObj;
	    }
	    private String command(String url, JSONObject data) {
			RestTemplate restTemplate = new RestTemplate();
//			String result = restTemplate.getForObject(url, String.class);
			String result="[{\"idCardNum\":\"310110198801011001\",\"idCardType\":\"1\",\"fullName\":\"测试1\",\"applyDatetime\":\"20150101\",\"applyAmount1\":\"10000\",\"applyAmount2\":\"300000\",\"applyType\":\"A\",\"approvalAmount1\":\"300000\",\"approvalAmount2\":\"3000\",\"loanAccount\":\"test_pac_12345678\",\"loanStartDate\":\"20150101\",\"loanEndDate\":\"20150101\",\"applyProCity\":\"上海市黄浦区，黑龙江省哈尔滨市\",\"homeAddr\":\"北京东路280号701\",\"selPhoneNum\":\"13965656565\",\"applyIp\":\"192.168.1.1\",\"insCode\":\"insdf\"}]";
			String result1=	"[{\"idCardNum\":\"310110198801011000\",\"idCardType\":\"1\",\"fullName\":\"测试\",\"loanAccount\":\"test_12345678\",\"loanBalance1\":\"3000000\",\"loanBalance2\":\"4111111\",\"nextLoanReplyDate\":\"2015-1-1\",\"nextLoanreplyAmt1\":\"200\",\"nextLoanreplyAmt2\":\"300\",\"loanStatus1\":\"5\",\"loanStatus2\":\"逾期\",\"insCode\":\"insdf\"}]";
			String result2="[{\"idCardNum\":\"510321196707087832\",\"commentType\":\"3\",\"content\":\"测试\",\"lastUpdateDatetime\":\"2015-1-1 12:11:12\"}]";
			return result2;
		}
	    public void saveApply(){
	    	getApply(command("", null), 3);
	    }
}
