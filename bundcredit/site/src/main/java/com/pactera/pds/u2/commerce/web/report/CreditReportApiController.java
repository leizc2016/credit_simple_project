package com.pactera.pds.u2.commerce.web.report;

import static com.pactera.pds.u2.commerce.utils.ConstantUtil.STATUS_CODE_MAP;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springside.modules.web.Servlets;

import com.pactera.pds.u2.commerce.entity.BundPersonComment;
import com.pactera.pds.u2.commerce.entity.Institution;
import com.pactera.pds.u2.commerce.entity.PersonAccDetail;
import com.pactera.pds.u2.commerce.entity.PersonApplyDetail;
import com.pactera.pds.u2.commerce.entity.PersonBasicInfo;
import com.pactera.pds.u2.commerce.entity.PersonSummaryReport;
import com.pactera.pds.u2.commerce.entity.mybatis.BCUser;
import com.pactera.pds.u2.commerce.service.account.BCAccountService;
import com.pactera.pds.u2.commerce.service.car.CacheResultWapper;
import com.pactera.pds.u2.commerce.service.car.CreditSearchCacheService;
import com.pactera.pds.u2.commerce.service.car.CreditSearchService;
import com.pactera.pds.u2.commerce.service.insdatamgr.InstitutionService;
import com.pactera.pds.u2.commerce.utils.DESUtil;
import com.pactera.pds.u2.commerce.utils.IdcardUtils;
import com.pactera.pds.u2.commerce.utils.MenuConstant;
import com.pactera.pds.u2.commerce.utils.Schedulee;
import com.pactera.pds.u2.commerce.utils.Sessions;
import com.pactera.pds.u2.commerce.utils.UuidUtil;


/**
 * 
 * 信用报告查询
 * 
 * @author foy make skeleton, 24Hour report cache
 * @author duanhui 征信报告查询实现
 *
 */
@Controller
@RequestMapping(value = "/creditreportApi")
public class CreditReportApiController {
    @Autowired
    CreditSearchCacheService cacheServcie;
    @Autowired
    private DESUtil des;
    @Autowired
    private Schedulee schedulee;
    
    @Autowired
	private CreditSearchService creditSearchService;
    
    @Autowired
    private InstitutionService insService;
    
    @Autowired
    private BCAccountService bcAccountService;
    
    /**
     * 自有产品API查询的请求入口
     * 通过访问此入口可获取用户令牌以及登录状态
     */
    @RequestMapping(value = "/bundCreditLogin" )
    public @ResponseBody String bundCreditLogin(HttpServletRequest  request) {
    	Map<String, Object> loginParams 
		   = Servlets.getParametersStartingWith(request, "login_");
    	JSONObject rtnObj = new JSONObject();
    	if(!validateLoginParams(loginParams)){
    		rtnObj.put("status", "10004");
    		rtnObj.put("msg", STATUS_CODE_MAP.get("10004"));
    		return rtnObj.toString();
    	}
    	String name = (String)loginParams.get("name");
    	String psw  = (String)loginParams.get("psw");
    	BCUser user = bcAccountService.findUserByLoginName(name);
    	if(user == null || user.getAllow() != 1){
    		rtnObj.put("status", "10001");
    		rtnObj.put("msg", STATUS_CODE_MAP.get("10001"));
    		return rtnObj.toString();
    	}
    	Boolean flag = bcAccountService.validatePassword(user.getId(), psw);
    	if(flag){
    		String uuid = UuidUtil.getUuid();
    		user.setUuid(uuid);
    		user.setLastLoginTime(new Date());
    		bcAccountService.updateuser(user);
    		String token = user.getUuid();
    		rtnObj.put("status", "10000");
    		rtnObj.put("msg", STATUS_CODE_MAP.get("10000"));
    		rtnObj.put("token", token);
    		
    		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getName(), psw);
    		Subject currentUser = SecurityUtils.getSubject();
    		currentUser.login(usernamePasswordToken);
    		
    		/*Sessions.setSessionAttr("name", user.getName());//记录日志是，从session取出
            Sessions.setSessionAttr("id", user.getId());*/
              
    	}else{
    		rtnObj.put("status", "10002");
    		rtnObj.put("msg", STATUS_CODE_MAP.get("10002"));
    	}
    	return rtnObj.toString();
    }
    /**
     * 判断用户权限：
     * 只有管理员和征信查询角色可以执行征信查询操作
     * @param user
     * @return
     */
    private boolean validateRole(BCUser user) {
		
    	if("creditreport".equals(user.getRoles())
    			|| "admin".equals(user.getRoles())){
    		return true;
    	}
		return false;
	}
	private boolean validateLoginParams(Map<String, Object> loginParams) {
    	String name = (String)loginParams.get("name");
    	String psw  = (String)loginParams.get("psw");
    	if(StringUtils.isEmpty(name)|| StringUtils.isEmpty(psw)){
    		return false;
    	}
		return true;
	}
	/**
	 * 自有产品API查询
	 * 个人借贷查询与企业借贷查询
	 * @param request
	 * @return json
	 * @throws Exception
	 */
    @RequestMapping(value = "/bundCreditSearch" )
	public @ResponseBody String bundCreditSearch(ServletRequest request)
			throws Exception {
		Map<String, Object> searchParams = getParametersMap(request);

		JSONObject rtnObj = new JSONObject();
		rtnObj.put("result_code", "YW000");
		rtnObj.put("result_msg", STATUS_CODE_MAP.get("YW000"));
		String msg = validateRequestParams(searchParams,
				schedulee.getDeadtime());
		if (msg != null) {
			return msg;
		}
		String token = (String) searchParams.get("token");
		BCUser user = bcAccountService.findUserByUUID(token);
		if(!validateRole(user)){
			JSONObject result = new JSONObject();
			result.put("status", "10003");
			result.put("msg", STATUS_CODE_MAP.get("10003"));
			return result.toString();
		}
		
		Sessions.setSessionAttr("name", user.getName());//记录日志是，从session取出
		Sessions.setSessionAttr("id", user.getId());
		
		String insCode = user.getInsCode();
		String idCardNum = des.encrypt(((String) searchParams.get("idCardNum"))
				.trim().toUpperCase());
		String queryType = (String) searchParams.get("queryType");// 1：申请查询2：贷后查询
		String productType = (String) searchParams.get("productType");// 1：个人借贷查询
																		// 2：企业借贷查询
		String productCode = "1".equals(productType) ? MenuConstant.personalBorrowingCode
				: MenuConstant.corporateLendingCode;

		CacheResultWapper cache = cacheServcie.get24hCahce(idCardNum,
				productType, insCode);
		Boolean fromCache = creditSearchService.fromCache(cache);

		PersonBasicInfo personBasicInfo = fromCache ? cache.getBasicInfo()
				: creditSearchService.getPersonBasicInfo(idCardNum);
		PersonSummaryReport personSummaryReport = fromCache ? cache
				.getSummaryReport() : creditSearchService
				.getPersonSummaryReport(idCardNum);
		List<BundPersonComment> personComments = fromCache ? cache.getComments() : creditSearchService.bundCommentByIdCardNum(idCardNum);
		List<PersonAccDetail> personAccDetails = fromCache ? cache
				.getActionDetail() : creditSearchService
				.getPersonAccDetails(idCardNum);
		List<PersonApplyDetail> personApplyDetails = fromCache ? cache
				.getApplyDetail() : creditSearchService
				.getPersonApplyDetails(idCardNum);

		msg = validateRequestContents(cache, queryType, insCode,
				fromCache, idCardNum, productCode, personAccDetails);
		if (msg != null) {
			return msg;
		}

		JSONObject data = new JSONObject();
		creditSearchService.creditData(data, fromCache, cache, idCardNum,
				productCode, user, personBasicInfo, personSummaryReport, personComments,
				personAccDetails, personApplyDetails);
		
		creditSearchService.charge(personAccDetails, insCode,
				queryType, productCode, 
				searchParams.get("idCardNum").toString(),
				fromCache, idCardNum);
		

		if (creditSearchService.noResultAtAll(personBasicInfo)) {
			rtnObj.put("result_code", "YW005");
			rtnObj.put("result_msg", STATUS_CODE_MAP.get("YW005"));
			return rtnObj.toString();
		}
		if (rtnObj.get("result_code").toString().equals("YW000")) {
			setHeadInfo(data, searchParams, insCode,productCode);
			rtnObj.put("data", data);
		}
		
		creditSearchService.saveIntoCache(fromCache, cache,
				personBasicInfo,
				personSummaryReport,
				personApplyDetails,
				personAccDetails,
				personComments,
				idCardNum,
				queryType,
				insCode);
		
		return rtnObj.toString();
	}
    
    
    private void setHeadInfo(JSONObject data, Map<String, Object> searchParams, String insCode,String productCode) {
		
    	boolean cashflag = creditSearchService.checkIsCashPay(productCode);
    	Institution institution = insService.getByInscode(insCode);
    	if(cashflag){
    		data.put("rest_amount", institution.getCashBalance());
    		data.put("account_type", "cash");
    	}else{
    		data.put("rest_amount", institution.getBalance());
    		data.put("account_type", "bc");
    	}
    	data.put("query_type", searchParams.get("queryType"));
    	data.put("product_type", searchParams.get("productType"));
	}
	private Map<String, Object> getParametersMap(ServletRequest request) {
		
    	String queryType = request.getParameter("query_type");
    	String productType = request.getParameter("product_type");
    	String idCardType = request.getParameter("id_card_type");
    	String idCardNum = request.getParameter("id_card_num");
    	String token = request.getParameter("token");
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("queryType", queryType);
    	map.put("productType", productType);
    	map.put("idCardType", idCardType);
    	map.put("idCardNum", idCardNum);
    	map.put("token", token);
		return map;
	}
	

    public CreditSearchCacheService getCacheServcie() {
        return cacheServcie;
    }
    
    public void setCacheServcie(CreditSearchCacheService cacheServcie) {
        this.cacheServcie = cacheServcie;
    }
    
    private boolean validateFetchParams(Map<String, Object> loginParams) {
    	String token = (String)loginParams.get("token");
    	String idCardNum  = (String)loginParams.get("idCardNum");
    	String queryType  = (String)loginParams.get("queryType");
    	String productType  = (String)loginParams.get("productType");

    	if(StringUtils.isEmpty(token)
    			|| StringUtils.isEmpty(idCardNum)
    			|| StringUtils.isEmpty(queryType)
    			|| StringUtils.isEmpty(productType)){
    		return false;
    	}
    	if( !"1".equals(queryType) 
    			&& !"2".equals(queryType)){
    		return false;
    	}
    	if( !"1".equals(productType) 
    			&& !"2".equals(productType)){
    		return false;
    	}
		return true;
	}
    /**
     * 校验请求类型、证件号码是否正确以及token有效性
     * @param searchParams
     * @param deadTime
     * @return
     */
    public String validateRequestParams(Map<String, Object> searchParams, long deadTime) {
		JSONObject rtnObj = new JSONObject();
		if(!validateFetchParams(searchParams)){
    		rtnObj.put("result_code", "YW002");
    		rtnObj.put("result_msg", STATUS_CODE_MAP.get("YW002"));
    		return rtnObj.toString();
    	}
        if(!IdcardUtils.validateCard((String)searchParams.get("idCardNum"))){
    		rtnObj.put("result_code", "YW002");
    		rtnObj.put("result_msg", STATUS_CODE_MAP.get("YW002"));
    		return rtnObj.toString();
    	}
        
        String token = (String)searchParams.get("token");
        BCUser user = bcAccountService.findUserByUUID(token);
        long now = System.currentTimeMillis();
        if(user == null 
        		|| now - user.getLastLoginTime().getTime() 
        			> deadTime 
        		|| user.getAllow() != 1){
        	rtnObj.put("result_code", "YW001");
    		rtnObj.put("result_msg", STATUS_CODE_MAP.get("YW001"));
    		return rtnObj.toString();
        }
        user.setLastLoginTime(new Date());
		bcAccountService.updateuser(user);
        
		return null;
	}
    /**
     * 校验请求参数内容是否符合要求
     * @param cache
     * @param queryType
     * @param insCode
     * @param fromCache
     * @param idCardNum
     * @param productCode
     * @param personAccDetails
     * @return
     */
    public String validateRequestContents(CacheResultWapper cache, String queryType,
			String insCode, Boolean fromCache, String idCardNum, String productCode, List<PersonAccDetail> personAccDetails) {
//		List<PersonAccDetail> personAccDetails = fromCache ?cache.getActionDetail():getPersonAccDetails(idCardNum);
		JSONObject rtnObj = new JSONObject(); 
		if(CreditSearchService.AFTER_LOAN.equals(queryType)
				 && !creditSearchService.afterLoan(personAccDetails, insCode)){
			 	rtnObj.put("result_code", "YW007");
	    		rtnObj.put("result_msg", STATUS_CODE_MAP.get("YW007"));
	    		return rtnObj.toString();
		 }
//		PersonBasicInfo personBasicInfo = fromCache ? cache.getBasicInfo():getPersonBasicInfo(idCardNum);
		Boolean noResult = creditSearchService.noFullCreditResult(personAccDetails, insCode);
		Float charge=creditSearchService.calculateCharge(queryType
				 , productCode, noResult, insCode);//计算出费用   
		Boolean payCash = creditSearchService.checkIsCashPay(productCode);
		if(!creditSearchService.balanceEnough(charge, insCode, payCash)){
        	rtnObj.put("result_code", "YW003");
    		rtnObj.put("result_msg", STATUS_CODE_MAP.get("YW003"));
    		return rtnObj.toString();
        }
		return null;
	}
    
    
    public static void main(String[] args) throws Exception {

		RestTemplate restTemplate = new RestTemplate();
		
		String comInfo = "{\"token\": \"58c81c40b0c34aaf8802d7c698e12f0f\", \"id_card_num\":\"510213197301064011\",\"product_type\":\"2\",\"query_type\":\"1\"}";
		JSONObject obj = JSONObject.fromObject(comInfo);
		String token = restTemplate.getForObject("http://localhost:8080/bundcredit-site/creditreportApi/bundCreditLogin?login_name=gaoxianglvp@163.com&login_psw=Gx_20573628", String.class);
		JSONObject tokenObj = JSONObject.fromObject(token);
		//将comInfo中的token替换为最新的token
		obj.put("token",tokenObj.getString("token"));
		String result = restTemplate.postForObject("http://localhost:8080/bundcredit-site/creditreportApi/bundCreditSearch", obj, String.class);
		System.out.println("Result is: " + result);
	}
}
