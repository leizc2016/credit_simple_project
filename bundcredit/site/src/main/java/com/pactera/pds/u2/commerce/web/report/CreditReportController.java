package com.pactera.pds.u2.commerce.web.report;

import static com.pactera.pds.u2.commerce.utils.ConstantUtil.BILL_AMOUNT_MAP;
import static com.pactera.pds.u2.commerce.utils.ConstantUtil.BILL_DATA_MAP;
import static com.pactera.pds.u2.commerce.utils.ConstantUtil.MATCH_FLAG_MAP;
import static com.pactera.pds.u2.commerce.utils.ConstantUtil.RECENT_CALL_FREQUENCY_MAP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.google.common.collect.Maps;
import com.pactera.pds.u2.commerce.entity.AccQueryHistory;
import com.pactera.pds.u2.commerce.entity.BcHis;
import com.pactera.pds.u2.commerce.entity.BcHisConcise;
import com.pactera.pds.u2.commerce.entity.BcHisEnterpriseQCC;
import com.pactera.pds.u2.commerce.entity.BcHisFull;
import com.pactera.pds.u2.commerce.entity.BcHisYLZC;
import com.pactera.pds.u2.commerce.entity.BcHisYYSCS;
import com.pactera.pds.u2.commerce.entity.BcHisYYSGEO;
import com.pactera.pds.u2.commerce.entity.BcProviderLog;
import com.pactera.pds.u2.commerce.entity.BundPersonComment;
import com.pactera.pds.u2.commerce.entity.InsitutionTransaction;
import com.pactera.pds.u2.commerce.entity.PersonAccDetail;
import com.pactera.pds.u2.commerce.entity.PersonApplyDetail;
import com.pactera.pds.u2.commerce.entity.PersonBasicInfo;
import com.pactera.pds.u2.commerce.entity.PersonSummaryReport;
import com.pactera.pds.u2.commerce.entity.enterpriseinfo.BeExecutorBean;
import com.pactera.pds.u2.commerce.entity.enterpriseinfo.BusinessInfoBean;
import com.pactera.pds.u2.commerce.entity.enterpriseinfo.CourtAnnouncementBean;
import com.pactera.pds.u2.commerce.entity.enterpriseinfo.CourtJudgmentDocBean;
import com.pactera.pds.u2.commerce.entity.enterpriseinfo.DishPersonBean;
import com.pactera.pds.u2.commerce.entity.mybatis.BCUser;
import com.pactera.pds.u2.commerce.interceptor.PageHelper;
import com.pactera.pds.u2.commerce.service.account.BCAccountService;
import com.pactera.pds.u2.commerce.service.bchis.BcHisEnterpriseQCCService;
import com.pactera.pds.u2.commerce.service.bchis.BcHisService;
import com.pactera.pds.u2.commerce.service.bchis.BcHisYLZCService;
import com.pactera.pds.u2.commerce.service.bchis.BcHisYYSCSService;
import com.pactera.pds.u2.commerce.service.bchis.BcHisYYSGEOService;
import com.pactera.pds.u2.commerce.service.bcproviderlog.BcProviderLogService;
import com.pactera.pds.u2.commerce.service.car.CacheResultWapper;
import com.pactera.pds.u2.commerce.service.car.CreditSearchCacheService;
import com.pactera.pds.u2.commerce.service.car.CreditSearchService;
import com.pactera.pds.u2.commerce.service.enterpriseinfo.EnterpriseInfoService;
import com.pactera.pds.u2.commerce.service.insdatamgr.InstitutionService;
import com.pactera.pds.u2.commerce.utils.Constant;
import com.pactera.pds.u2.commerce.utils.ConstantUtil;
import com.pactera.pds.u2.commerce.utils.ControllerUtils;
//import com.pactera.pds.u2.commerce.utils.Constant4Payment;
import com.pactera.pds.u2.commerce.utils.DESUtil;
import com.pactera.pds.u2.commerce.utils.IdcardUtils;
import com.pactera.pds.u2.commerce.utils.MenuConstant;
import com.pactera.pds.u2.commerce.utils.ControllerConstant;
import com.pactera.pds.u2.commerce.utils.Sessions;


/**
 * 
 * 信用报告查询
 * 
 * @author foy make skeleton, 24Hour report cache
 * @author duanhui 征信报告查询实现
 *
 */
@Controller
@RequestMapping(value = "/creditreport")
public class CreditReportController {
    @Autowired
    private CreditSearchCacheService cacheServcie;
    @Autowired
    private BcHisService bcHisService;
    @Autowired
    private BcHisYYSCSService bcHisYYSCSService;
    @Autowired
    private BcProviderLogService bcProviderLogService;
    @Autowired
    private BCAccountService bcAccountService;
    @Autowired
    private BcHisYLZCService bcHisYLZCService;
    @Autowired
    private BcHisYYSGEOService bcHisYYSGEOService;
    
    @Autowired
	private CreditSearchService creditSearchService;
    
    @Autowired
    private InstitutionService insService;
    
    @Autowired
    private EnterpriseInfoService enterpriseInfoService;
    
    @Autowired
    private BcHisEnterpriseQCCService bcHisEnterpriseQCCService;
    
    
//    private Boolean payInCash = true;
    // 工作查询，即查询历史
    public String creditReportHistory(Model model,
			ServletRequest request) {
    	PageBounds pageBound = PageHelper.composeFromRequest4site(request, model);
    	Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
    	String pageId = (String)searchParams.get("productCode");
    	String queryType = (String)searchParams.get("queryType");
    	Long insAccId = Sessions.id();
    	List<AccQueryHistory> accQueryHistories 
    		= creditSearchService.getAccQueryHistorys(insAccId, pageId, queryType, pageBound);
		model.addAttribute("accQueryHistories", accQueryHistories);
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
        return "/report/queryHistory";
    }
    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public String bcHis(Model model,
			ServletRequest request) {
    	PageBounds pageBound = PageHelper.composeFromRequest4site(request, model);
    	String productCode = request.getParameter("productCode");
    	String queryType = request.getParameter("queryType");
    	Long userId = Sessions.id();
    	
    	List<BcHis> bcHisList = null;
    	bcHisList = creditSearchService.bcHis(userId, productCode, pageBound, queryType);
    	
		model.addAttribute("bcHisList", bcHisList);
		model.addAttribute("productCode", productCode);
		model.addAttribute("queryType", queryType);
        return "/report/bcHis";
    }
    
    @RequestMapping(value="distributeSearch")
    public String distributeSearch(Model model,
    		ServletRequest request,
    		RedirectAttributes redirectAttributes) throws Exception{
    	Long conditionId = Long.parseLong(request.getParameter("conditionId"));
    	String productCode = request.getParameter("productCode");
    	String productCode2nd = request.getParameter("productCode2nd");
    	int queryType = Integer.parseInt(request.getParameter("queryType"));
    	if(MenuConstant.conciseCode.equals(productCode)){
    		BcHisConcise bcHisConcise = creditSearchService.oneBcHisConcise(conditionId);
    		redirectAttributes.addAttribute("search_idCardNum", 
    				DESUtil.decrypt(bcHisConcise.getIdCardNum()));
    		redirectAttributes.addAttribute("search_search_type", bcHisConcise.getQueryType());
    		return "redirect:/creditreport/conciseSearch";
    	}
    	if(MenuConstant.fullCode.equals(productCode)){
    		BcHisFull bcHisFull = creditSearchService.oneBcHisFull(conditionId);
    		redirectAttributes.addAttribute("search_idCardNum", 
    				DESUtil.decrypt(bcHisFull.getIdCardNum()));
    		redirectAttributes.addAttribute("search_search_type", bcHisFull.getQueryType());
    		return "redirect:/creditreport/fullSearch";
    	}
    	if(MenuConstant.personalBorrowingCode.equals(productCode)){
    		BcHisFull bcHisFull = creditSearchService.oneBcHisFull(conditionId);
    		redirectAttributes.addAttribute("search_idCardNum", 
    				DESUtil.decrypt(bcHisFull.getIdCardNum()));
    		redirectAttributes.addAttribute("search_search_type", bcHisFull.getQueryType());
    		return "redirect:/personalBorrow/fullSearch";
    	}
    	if(MenuConstant.corporateLendingCode.equals(productCode)){
    		BcHisFull bcHisFull = creditSearchService.oneBcHisFull(conditionId);
    		redirectAttributes.addAttribute("search_idCardNum", 
    				DESUtil.decrypt(bcHisFull.getIdCardNum()));
    		redirectAttributes.addAttribute("search_search_type", bcHisFull.getQueryType());
    		return "redirect:/corporateLending/fullSearch";
    	}
    	if((MenuConstant.yunYinShangCSCode.equals(productCode) || MenuConstant.yunYinShangGEOCode.equals(productCode)) && MenuConstant.IPS_CS.equals(productCode2nd)){
    		BcHisYYSCS bchisyyscs  = bcHisYYSCSService.getBcHisYYSCSById(conditionId);
    		redirectAttributes.addAttribute("authCode",bchisyyscs.getAuthCode());
    		redirectAttributes.addAttribute("cellNum",bchisyyscs.getCellNum());
    		redirectAttributes.addAttribute("userName",bchisyyscs.getUserName());
    		redirectAttributes.addAttribute("idCardNum",bchisyyscs.getIdCardNum());
    		redirectAttributes.addAttribute("contactNum",bchisyyscs.getContactNum());
    		redirectAttributes.addAttribute("homeAddr",bchisyyscs.getHomeAddr());
    		redirectAttributes.addAttribute("homeProvince",bchisyyscs.getHomeProvince());
    		redirectAttributes.addAttribute("homeCity",bchisyyscs.getHomeCity());
    		redirectAttributes.addAttribute("homeDistrict",bchisyyscs.getHomeDistrict());
    		redirectAttributes.addAttribute("workingSite",bchisyyscs.getWorkingSite());
    		redirectAttributes.addAttribute("workingProvince",bchisyyscs.getWorkingProvince());
    		redirectAttributes.addAttribute("workingCity",bchisyyscs.getWorkingCity());
    		redirectAttributes.addAttribute("workingDistrict",bchisyyscs.getWorkingDistrict());
    		if(!StringUtils.isEmpty(bchisyyscs.getAsynData())){
    			//解析详细信息
    			Map<String,Object> asynDataMaps = CreditReportController.getCsAsynData(bchisyyscs.getAsynData());
    			redirectAttributes.addAttribute("homeAddMatch",asynDataMaps.get("home_add_match"));
    			redirectAttributes.addAttribute("workAddMatch",asynDataMaps.get("work_add_match"));
    			redirectAttributes.addAttribute("recentCallFrequency",asynDataMaps.get("recent_call_frequency").toString());
    			redirectAttributes.addAttribute("billData",asynDataMaps.get("bill_data").toString());
    			redirectAttributes.addAttribute("billAmount",asynDataMaps.get("bill_amount").toString());
    		}
    		if(queryType == 1){
    			Map<String, Object> params = new HashMap<String, Object>();
    			params.put("conditionId", conditionId);
    			params.put("productCode", productCode);
    			BcHis bcHis = bcHisService.getBcHisByConditionId(params);
    			creditSearchService.chargeCS(bchisyyscs,queryType);
    			bcHis.setFlag(3);
    			bcHisService.updateBcHis(bcHis);
    			redirectAttributes.addAttribute("showWarn",2);
    			redirectAttributes.addAttribute("matchLevel",MATCH_FLAG_MAP.get(bchisyyscs.getMatchLevel()));
        		redirectAttributes.addAttribute("matchIdCard",MATCH_FLAG_MAP.get(bchisyyscs.getMatchIdCard()));
        		redirectAttributes.addAttribute("netTime",bchisyyscs.getNetTime());
        		redirectAttributes.addAttribute("cellAccount",bchisyyscs.getCellAccount());
        		redirectAttributes.addAttribute("fixedAccount",bchisyyscs.getFixedAccount());
    		}else if(queryType == 2){
    			redirectAttributes.addAttribute("showWarn",1);
    		}else{
    			redirectAttributes.addAttribute("showWarn",3);
    		}
    		return "redirect:/creditreport/yunYingShangCS";
    	}else if(MenuConstant.yinLianZCCode.equals(productCode)){
    		BcHisYLZC bcHisYLZC  = bcHisYLZCService.getBcHisYLZCById(conditionId);
    		redirectAttributes.addAttribute("idCardNum", bcHisYLZC.getIdCardNum());
    		redirectAttributes.addAttribute("bankCardNum", bcHisYLZC.getBankCardNum());
    		redirectAttributes.addAttribute("authCode", bcHisYLZC.getAuthCode());
    		return "redirect:/creditreport/yinLianReport";
    	}else if(MenuConstant.yunYinShangGEOCode.equals(productCode)){
    		BcHisYYSGEO geoHis=bcHisYYSGEOService.queryBcHisYYSGEOById(conditionId);
//    		DmpSearch geo = creditSearchService.retreiveGEO(
//    				geoHis.getCallNum(),
//    				geoHis.getAuthCode(),
//					"",
//					geoHis.getContactNum());
    		/*model.addAttribute("authCode",geoHis.getAuthCode());
    		model.addAttribute("cellNum",geoHis.getCallNum());*/
    		
            BcHisYYSGEO bcHisYYSGEO = new BcHisYYSGEO();
            bcHisYYSGEO.setCallNum(geoHis.getCallNum());
            bcHisYYSGEO.setAuthCode(geoHis.getAuthCode());
            model.addAttribute("bcHisYYSGEO", bcHisYYSGEO);
            
//    		model.addAttribute("geo",null);
    		if(queryType == 1){
    			Map<String, Object> params = new HashMap<String, Object>();
    			params.put("conditionId", conditionId);
    			params.put("productCode", productCode);
    			BcHis bcHis = bcHisService.getBcHisByConditionId(params);
    			bcHis.setFlag(3);
    			bcHisService.updateBcHis(bcHis);
    		}
    		return "/report/yunYingShangGEO";
    	}else if(MenuConstant.QYCode.equals(productCode)){
    		BcHisEnterpriseQCC qcc = bcHisEnterpriseQCCService.queryBcHisEnterPriseQCCById(conditionId);
    		redirectAttributes.addAttribute("registerNum", qcc.getRegisterNum());
    		redirectAttributes.addAttribute("idCardNum", qcc.getIdCardNum());
    		redirectAttributes.addAttribute("fullName", qcc.getFullName());
        	return "redirect:/creditreport/enterpriseSearch";
    	}
    	return null;
    }
    

//    private static Map<String, String> searchTypes = Maps.newLinkedHashMap();
//	static {
//		searchTypes.put("text_credit", "申请查询");
//		searchTypes.put("text_after_loan", "贷后查询");
//		searchTypes.put("key_credit", "1");
//		searchTypes.put("key_after_loan", "2");
//	}

	
	
	//钟旭 强伟 jolin 贝贝 张岩
    private List<BundPersonComment> getBCPersonComent(String idCardNum,Model model,CacheResultWapper cache){
        List<BundPersonComment> personComments=cache != null ? cache.getComments():creditSearchService.bundCommentByIdCardNum(idCardNum);
        model.addAttribute("bcComment", personComments);
        return personComments;
    }
    // 简版报告
    @RequestMapping(value = "/concise", method = RequestMethod.GET)
    public String conciseCreditReport(Model model,
			ServletRequest request) {
//    	Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
//		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
//    	model.addAttribute("searchTypes", searchTypes);
//    	dbRequestMsg(request, model);
        return "/report/conciseReport";
    }
    
//    private void dbRequestMsg(ServletRequest request, Model model) {
//		// TODO Auto-generated method stub
//	 String dbRequestError =  request.getParameter("db_request_error");
//	 if("1".equals(dbRequestError)){
//		 model.addAttribute("message_info", "发生错误，请求重复");
//	 }
//	}
    
    private boolean dbRequestCheck(ServletRequest request,
    		Model model) {
    	Boolean dbRequestError = 
        		creditSearchService.doubleRequest(request.getParameter(Constant.LAST_REQUEST_TIME_KEY));
        if(dbRequestError){
        	model.addAttribute("message_info", "发生错误，请求重复");
        	return false;//new ModelAndView("redirect:/concise").addAllObjects(params); 
        }
		return true;
	}
	// 简版报告
    @RequestMapping(value = "/conciseSearch", method = RequestMethod.GET)
    public String conciseCreditReportSearch(Model model,
			ServletRequest request,
			@RequestParam(value="useCache",defaultValue="true") boolean useCache,
			RedirectAttributes redirectAttributes) throws NumberFormatException, Exception {
        
        if(!dbRequestCheck(request, model)){
        	return "/report/conciseReport";
        }
    	PersonBasicInfo personBasicInfo;
        PersonSummaryReport personSummaryReport;
        List<PersonAccDetail>  personAccDetails;
        List<BundPersonComment> personComements;
        List<PersonApplyDetail> personApplyDetails;
        CacheResultWapper cache = null;
        boolean fromCache=false;
    	Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
    	model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
    	model.addAttribute("searchTypes", ControllerConstant.searchTypes);
    	String tempCard=null;
    	String idCardNumDecode = null;
        try {
        	idCardNumDecode = (String)searchParams.get("idCardNum");
        	idCardNumDecode = idCardNumDecode.trim().toUpperCase();
            tempCard = DESUtil.encrypt(idCardNumDecode);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    	String idCardNum =tempCard ;
    	String searchType = (String)searchParams.get("search_type");
//    	String idCardType = (String)searchParams.get("idCardType");
    	String queryType=""+ControllerConstant.productConciselId;
    	if(useCache ){
    	    cache=cacheServcie.get24hCahce(idCardNum, queryType, Sessions.insCode());  
    	}
    	
    	if(cache != null){
    	    fromCache=true;
    	    model.addAttribute("fromCache", fromCache);
    	    model.addAttribute("cahceTime", cache.getCacheTime());
    	}
    	//如果属于贷后查询，验证是否符合贷后查询
    	 personAccDetails = fromCache?cache.getActionDetail():creditSearchService.getPersonAccDetails(idCardNum);
		/*if(creditSearchService.AFTER_LOAN.equals(searchType) && !creditSearchService.afterLoan(personAccDetails, Sessions.insCode())){
			model.addAttribute("message_info", "查询失败，请选择申请查询");
			return "/report/conciseReport";
		}*/
    	 personApplyDetails = fromCache ? cache.getApplyDetail():creditSearchService.getPersonApplyDetails(idCardNum);
    	 if(CreditSearchService.AFTER_LOAN.equals(searchType) && !creditSearchService.afterLoan1(personApplyDetails, Sessions.insCode())){
 			model.addAttribute("message_info", "查询失败，请选择申请查询");
 			return "/report/conciseReport";
 		}
    	 personBasicInfo = fromCache? cache.getBasicInfo():creditSearchService.getPersonBasicInfo(idCardNum);
		 personSummaryReport = fromCache? cache.getSummaryReport():creditSearchService.getPersonSummaryReport(idCardNum);
		Long userId = Sessions.id();
		//添加查询记录到工作查询
		//扣款
		Boolean noResult = creditSearchService.noFullCreditResult(personAccDetails, Sessions.insCode());//是否没查出结果
		Float charge=0f;
		String insCode = Sessions.insCode();
		//判断是否现金支付
		boolean cashflag = creditSearchService.checkIsCashPay(MenuConstant.conciseCode);
		if(!fromCache){
    		charge = creditSearchService.calculateCharge(searchType
    				, MenuConstant.conciseCode, noResult, insCode);//计算出费用
    		if(!creditSearchService.balanceEnough(charge, insCode, cashflag)){
    			model.addAttribute("message_info", "查询失败，余额不足");
    			return "/report/conciseReport";
    		}
		}
//		creditSearchService.insertAccQueryHistory(searchType, idCardNum, productConciselId, insAccId);
		creditSearchService.recordConciseFoot(
				new Integer(searchType),  idCardNumDecode, 
				MenuConstant.conciseCode, userId);
		if(fromCache){
			generateTrans(
					charge, 
					idCardNumDecode, 
					insCode,
					cashflag);
//			insService.updateValueTransactionLog(Sessions.insCode(),charge,"使用24小时征信报告查询缓存，扣费0元.");  
		}else{
			String description = "简版征信报告查询：" + IdcardUtils.hideIdCardBirthInfo(idCardNumDecode);
			creditSearchService.commitFee(
        			charge, 
        			insCode,
        			cashflag,
        			description);
//		    insService.updateBalance(Sessions.insCode(), 0-charge, idCardNumDecode);//扣费
		}
		
		personComements=getBCPersonComent(idCardNum,model,cache);
		model.addAttribute("personBasicInfo", personBasicInfo);
		model.addAttribute("personSummaryReport", personSummaryReport);
		if(ControllerUtils.noResultAtAll(personBasicInfo)){
			model.addAttribute("message_info", "无此用户信息");
		}
		if(!fromCache){
		    cache=new CacheResultWapper();
		    cache.setBasicInfo(personBasicInfo);
		    cache.setSummaryReport(personSummaryReport);
		    cache.setActionDetail(personAccDetails);
		    cache.setComments(personComements);
		    cache.setApplyDetail(personApplyDetails);
		    cacheServcie.safeSave24HCache(idCardNum, queryType, cache, insCode);
		}
        return "/report/conciseReport";
    }
    

	//    private Boolean noConciseResult(PersonBasicInfo personBasicInfo) {
//		if(personBasicInfo == null || personBasicInfo.getIdCardNum() == null){
//			return true;
//		}
//		return false;
//	}
	// 详版报表
    @RequestMapping(value = "/full", method = RequestMethod.GET)
    public String fullCreditReport( Model model,
			ServletRequest request) {
    	Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
    	model.addAttribute("searchTypes", ControllerConstant.searchTypes);
        return "/report/fullReport";
    }
//     //工商报告
//    @RequestMapping(value = "/enterprise", method = RequestMethod.GET)
//    public String enterpriseCreditReport( Model model,
//			ServletRequest request) {
//    	Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
//		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
//    	model.addAttribute("searchTypes", searchTypes);
//        return "/report/enterpriseReport";
//    }
//    
//    // 企业信息报表
//    @RequestMapping(value = "/enterpriseSearch", method = RequestMethod.GET)
//    public String enterpriseCreditReportSearch( Model model,
//			ServletRequest request,@RequestParam(value="useCache",defaultValue="true") boolean useCache) throws NumberFormatException, Exception {
//    	EnterpriseInfo enterpriseInfo;
////    	PersonBasicInfo personBasicInfo;
//        PersonSummaryReport personSummaryReport;
//        List<PersonApplyDetail> personApplyDetails;
//        List<PersonAccDetail>  personAccDetails;
//        List<BundPersonComment> personComements;
//        CacheResultWapper cache=null;
//        boolean fromCache=false;
//        
//        if(!dbRequestCheck(request, model)){
//        	return "/report/enterpriseReport";
//        }
//        
//		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
//		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
//		String regID = (String)searchParams.get("regid");
//
//		personAccDetails = fromCache ?cache.getActionDetail():creditSearchService.getPersonAccDetails(regID);
//		model.addAttribute("searchTypes", searchTypes);
//
////		 personBasicInfo = fromCache ? cache.getBasicInfo():creditSearchService.getPersonBasicInfo(idCardNum);
//		 enterpriseInfo = creditSearchService.getEnterpriseInfo(regID);
//		 personSummaryReport = fromCache ? cache.getSummaryReport():creditSearchService.getPersonSummaryReport(regID);
//		 personApplyDetails = fromCache ? cache.getApplyDetail():creditSearchService.getPersonApplyDetails(regID);
//
//		
//		personComements = getBCPersonComent(regID,model,cache);
//
////		model.addAttribute("personBasicInfo", personBasicInfo);
//		model.addAttribute("enterpriseInfo", enterpriseInfo);
//		model.addAttribute("personSummaryReport", personSummaryReport);
//		model.addAttribute("personAccDetails", personAccDetails);
//		model.addAttribute("personApplyDetails", personApplyDetails);
////		if(noResultAtAll(personBasicInfo)){
////			model.addAttribute("message_info", "无此用户信息");
////		}
//        return "/report/enterpriseReport";
//    }
    
    @RequestMapping(value = "/yunYingShang")
    public String yunYinShangReport( 
    		Model model,
			ServletRequest request) {
//    	Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
//		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
//    	model.addAttribute("searchTypes", searchTypes);
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("message_info", request.getParameter("message_info"));
    	searchParams.put("cellNum", request.getParameter("cellNum"));
    	searchParams.put("authCode", request.getParameter("authCode"));
    	model.addAllAttributes(searchParams);
    	//model.addAttribute("message_info", request.getParameter("message_info"));
        return "/report/yunYingShangCell";
    }
    
    @RequestMapping(value = "/yunYingShangCS", method = RequestMethod.GET)
    public String yunYinShangCS( Model model,
			ServletRequest request,BcHisYYSCS bcHisYYSCS) {
    	model.addAttribute("bcHisYYSCS", bcHisYYSCS);
        return "/report/yunYingShangCS";
    }
    
    @RequestMapping(value = "/yunYingShangGEO", method = RequestMethod.GET)
    public String yunYinShangGEO( Model model,
			ServletRequest request,
			@RequestParam(value="cellNum") String cellNum,
			@RequestParam(value="authCode") String authCode) {
//    	Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
//		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
    	//model.addAttribute("cellNum", cellNum);
    	//model.addAttribute("authCode", authCode);
    	
        BcHisYYSGEO bcHisYYSGEO = new BcHisYYSGEO();
        bcHisYYSGEO.setCallNum(cellNum);
        bcHisYYSGEO.setAuthCode(authCode);
        model.addAttribute("bcHisYYSGEO", bcHisYYSGEO);
         
        return "/report/yunYingShangGEO";
    }
    
    @RequestMapping(value = "/yunYingShangGEOSearch", method = RequestMethod.POST)
    public String yunYinShangGEOSearch( Model model,
			ServletRequest request,BcHisYYSGEO bcHisYYSGEO,
			@RequestParam(value="cellOwner") String cellOwner,
			@RequestParam(value="contactCell") String contactCell) {
    	model.addAttribute("cellOwner", cellOwner);
    	model.addAttribute("contactCell", contactCell);
    	if(!dbRequestCheck(request, model)){
        	return "/report/yunYingShangGEO";
        }
    	if(!creditSearchService.enoughBalance(
    			Sessions.insCode(),
    			MenuConstant.yunYinShangGEOCode, 
    			false)){
    		model.addAttribute("message_info", "查询失败，余额不足");
    		return "/report/yunYingShangGEO";
    	}
    	int flag=1;
    	Date QueryDate = new Date();
    	try {
    	    BCUser bcUser = bcAccountService.getBcUser(Sessions.id());
            bcHisYYSGEO.setUserName(bcUser.getLoginName());
            bcHisYYSGEO.setContactNum(contactCell);
            
    	    BcHisYYSGEO newBcHisYYSGEO = creditSearchService.retreiveGEO(bcHisYYSGEO,
					cellOwner,
					contactCell);
			if(newBcHisYYSGEO == null){
				model.addAttribute("message_info", "第三方查询出错");
				flag=3;
			}else{
				String msg = creditSearchService.chargeGEO(bcHisYYSGEO, Sessions.insCode());
				bcProviderLogService.GEOlog(bcHisYYSGEO, bcUser);
				if(msg != null){
					model.addAttribute("message_info", msg);
				}
			}
		} catch (Exception e) {
			flag=3;
			e.printStackTrace();
			//bcHisYYSGEO = new DmpSearch();
			model.addAttribute("message_info", "第三方查询出错");
		}finally{
			//creditSearchService.yysGEOHis(bcHisYYSGEO);
		    creditSearchService.saveGEOHis(bcHisYYSGEO);
			//插入查询历史主表
        	BcHis bcHis = new BcHis();
        	bcHis.setConditionId(bcHisYYSGEO.getId());
        	bcHis.setFlag(flag);
        	bcHis.setQueryCondition(bcHisYYSGEO.getCallNum());
        	bcHis.setProductCode(MenuConstant.yunYinShangGEOCode);
        	bcHis.setProductCode2nd("GEO");
        	bcHis.setQueryDate(QueryDate);
        	bcHis.setUserId(Sessions.id());
        	bcHis.setQueryType(3);
        	long rid = bcHisService.saveBcHis(bcHis);
        	if(rid == 1L){
        		//保存查询日志
    			BcProviderLog bcProviderLog = new BcProviderLog();
    			bcProviderLog.setQueryDate(QueryDate);
				bcProviderLog.setQueryCondition(bcHisYYSGEO.getCallNum()+":"+bcHisYYSGEO.getAuthCode());
				bcProviderLog.setQueryResult("");
    			bcProviderLog.setProvider("GEO");
    			bcProviderLog.setOperator(Sessions.loginName());
    			bcProviderLogService.saveBcProviderLog(bcProviderLog);
        	}
		}
    	model.addAttribute("bcHisYYSGEO", bcHisYYSGEO);
        return "/report/yunYingShangGEO";
    }
    
    
	@RequestMapping(value = "/yunYingShangCSSearch", method = RequestMethod.POST)
    public String yunYinShangCSSearch( Model model,
			ServletRequest request,BcHisYYSCS bcHisYYSCS,
			@RequestParam(value="cellNum") String cellNum,
			@RequestParam(value="authCode") String authCode) {
    	if(!dbRequestCheck(request, model)){
        	return "/report/yunYingShangCS";
        }
    	if(!creditSearchService.enoughBalance(
    			Sessions.insCode(),
    			MenuConstant.yunYinShangCSCode, 
    			false)){
    		model.addAttribute("message_info", "查询失败，余额不足");
    		return "/report/yunYingShangCS";
    	}
    	Date QueryDate = new Date();
    	//调用畅圣的接口
    	String message_info = "";
    	int flag = 1;
    	Map<String, Object> returnmap = null;
    	String result = null;
    	try {
    		StringBuffer sbuffer = new StringBuffer();
    		StringBuffer sbuffer1 = new StringBuffer();
    		sbuffer.append(bcHisYYSCS.getWorkingProvince());
    		sbuffer1.append(bcHisYYSCS.getHomeProvince());
    		if(null != bcHisYYSCS.getWorkingCity() && !bcHisYYSCS.getWorkingCity().equals("市辖区")){
    			sbuffer.append(bcHisYYSCS.getWorkingCity());
    		}
    		if(null != bcHisYYSCS.getWorkingDistrict() && !bcHisYYSCS.getWorkingDistrict().equals("市辖区")){
    			sbuffer.append(bcHisYYSCS.getWorkingDistrict());
    		}
    		if(null != bcHisYYSCS.getWorkingSite()){
    			sbuffer.append(bcHisYYSCS.getWorkingSite());
    		}
    		if(null != bcHisYYSCS.getHomeCity() && !bcHisYYSCS.getHomeCity().equals("市辖区")){
    			sbuffer1.append(bcHisYYSCS.getHomeCity());
    		}
    		if(null != bcHisYYSCS.getHomeDistrict() && !bcHisYYSCS.getHomeDistrict().equals("市辖区")){
    			sbuffer1.append(bcHisYYSCS.getHomeDistrict());
    		}
    		if(null != bcHisYYSCS.getHomeAddr()){
    			sbuffer1.append(bcHisYYSCS.getHomeAddr());
    		}
    		bcHisYYSCS.setAsynCode(String.valueOf(System.currentTimeMillis()));
    		returnmap = creditSearchService.retreiveCS(bcHisYYSCS.getCellNum(), bcHisYYSCS.getIdCardNum(), 
    				bcHisYYSCS.getContactNum(),sbuffer.toString(), sbuffer1.toString(), Long.parseLong(bcHisYYSCS.getAsynCode()),
    				bcHisYYSCS.getAuthCode());
    		result =returnmap.get("results").toString();
    		Map<String,String> resultsmap = toHashMap(result);
    		String code = resultsmap.get("code");
    		if("0".equals(code)){
    			bcHisYYSCS.setMatchLevel(resultsmap.get("i_shimin").toString());
    			bcHisYYSCS.setMatchIdCard(resultsmap.get("i_shenfenzhen").toString());
    			
    			String onlineTime = resultsmap.get("i_zaiwangshijian");
    			if (ConstantUtil.CS_ONLINETIME_MAP.containsKey(onlineTime)) {
    				bcHisYYSCS.setNetTime(ConstantUtil.CS_ONLINETIME_MAP.get(onlineTime));
    			}  else {
    				bcHisYYSCS.setNetTime("未知");
    			}

    			if(resultsmap.get("i_shoujishu")  != null){
    				bcHisYYSCS.setCellAccount(Integer.parseInt(resultsmap.get("i_shoujishu").toString()));
    			}else{
    				bcHisYYSCS.setCellAccount(0);
    			}
    			if(resultsmap.get("i_guhuashu")  != null){
    				bcHisYYSCS.setFixedAccount(Integer.parseInt(resultsmap.get("i_guhuashu").toString()));
    			}else{
    				bcHisYYSCS.setFixedAccount(0);
    			}
        		message_info = chargeCS(bcHisYYSCS);
    		}else if(ConstantUtil.CS_CODE_MAP.containsKey(code)){
    			message_info = ConstantUtil.STATUS_CODE_MAP.get(ConstantUtil.CS_CODE_MAP.get(code));
    		}else{
    			message_info = "查询失败，接口返回码异常!";
    		}
		} catch (Exception e) {
			flag = 3;
			e.printStackTrace();
			message_info = "调用第三方查询出错!";
		}finally{
	    	long id = bcHisYYSCSService.saveBcHisYYSCS(bcHisYYSCS);
	    	if(id == 1L){
	    		//插入查询历史主表
	        	BcHis bcHis = new BcHis();
	        	bcHis.setConditionId(bcHisYYSCS.getId());
	        	bcHis.setFlag(flag);
	        	bcHis.setQueryCondition(bcHisYYSCS.getCellNum());
	        	bcHis.setProductCode(MenuConstant.yunYinShangCSCode);
	        	bcHis.setProductCode2nd("CS");
	        	bcHis.setQueryDate(QueryDate);
	        	bcHis.setUserId(Sessions.id());
	        	bcHis.setQueryType(3);
	        	long rid = bcHisService.saveBcHis(bcHis);
	        	if(rid == 1L){
	        		//保存查询日志
	    			BcProviderLog bcProviderLog = new BcProviderLog();
	    			bcProviderLog.setQueryDate(QueryDate);
	    			if(returnmap != null){
	    				bcProviderLog.setQueryCondition(returnmap.get("paramjobject").toString());
	    			}else{
	    				bcProviderLog.setQueryCondition("");
	    			}
	    			if(null != result){
	    				bcProviderLog.setQueryResult(result);
	    			}else{
	    				bcProviderLog.setQueryResult("");
	    			}
	    			bcProviderLog.setProvider("CS");
	    			bcProviderLog.setOperator(Sessions.loginName());
	    			bcProviderLogService.saveBcProviderLog(bcProviderLog);
	        	}
	    	}
		}
    	String matchLevel = bcHisYYSCS.getMatchLevel();
    	String matchIdCard = bcHisYYSCS.getMatchIdCard();
    	if(!StringUtils.isEmpty(matchLevel)){
    		bcHisYYSCS.setMatchLevel(MATCH_FLAG_MAP.get(matchLevel));
    	}
    	if(!StringUtils.isEmpty(matchIdCard)){
    		bcHisYYSCS.setMatchIdCard(MATCH_FLAG_MAP.get(matchIdCard));
    	}
    	model.addAttribute("bcHisYYSCS", bcHisYYSCS);
    	model.addAttribute("cellNum", cellNum);
    	model.addAttribute("authCode", authCode);
    	bcHisYYSCS.setShowWarn(1);
    	model.addAttribute("message_info", message_info);
        return "/report/yunYingShangCS";
    }
    private String chargeCS(BcHisYYSCS bchisyyscs) {
    	return creditSearchService.chargeCS(bchisyyscs);
	}
 
	@RequestMapping(value = "/yinLian", method = RequestMethod.GET)
    public String yinLian( Model model,ServletRequest request) {
		String idCardNum = request.getParameter("idCardNum");
		String bankCardNum = request.getParameter("bankCardNum");
		String authCode = request.getParameter("authCode");
		String messageInfo = request.getParameter("message_info");
		model.addAttribute("idCardNum", idCardNum);
		model.addAttribute("bankCardNum", bankCardNum);
		model.addAttribute("authCode", authCode);
		model.addAttribute("message_info", messageInfo);
        return "/report/yinLian";
    }
	
	    //验证授权码及银联查询条件是否能查询
		@RequestMapping(value = "/checkUserInputForYL", method = RequestMethod.GET )
		public ModelAndView checkUserInputForYL(Model model,ServletRequest request){
			String idCardNum = request.getParameter("idCardNum");
			String bankCardNum = request.getParameter("bankCardNum");
			String authCode = request.getParameter("authCode");
			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("bankCardNum", bankCardNum);
			params.put("authCode", authCode);
			params.put("insCode", Sessions.insCode());
			params.put("idCardNum", idCardNum);
			
			//Check duplicate commit
		   	if(!dbRequestCheck(request, model)){
	        	return new ModelAndView("redirect:/creditreport/yinLian").addAllObjects(params);
	        }
		   	
		   	//Check account balance.
	    	if(!creditSearchService.enoughBalance(
	    			Sessions.insCode(),
	    			MenuConstant.yinLianZCCode, 
	    			false)){
	    		model.addAttribute("message_info", "查询失败，余额不足");
	    		return new ModelAndView("redirect:/creditreport/yinLian").addAllObjects(params);
	    	}
		   	
		   	//Check auth code.
			if(!checkAuthCode(params,"YLZC")){
				params.put("message_info","机构授权码重复，请重新输入!");
				return new ModelAndView("redirect:/creditreport/yinLian").addAllObjects(params);
			}else{
				return new ModelAndView("redirect:/creditreport/yinLianReport").addAllObjects(params);
			}
		}
	
	// 银联报表
	@RequestMapping(value = "/yinLianReport", method = RequestMethod.GET)
    public ModelAndView yinLianReport( Model model,
			ServletRequest request,BcHisYLZC bcHisYLZC) {
		Map<String,Object> params = new HashMap<String,Object>();
		if(!creditSearchService.enoughBalance(
    			Sessions.insCode(),
    			MenuConstant.yinLianZCCode, 
    			false)){
			params.put("message_info", "查询失败，余额不足");
			params.put("idCardNum", bcHisYLZC.getIdCardNum());
			params.put("bankCardNum", bcHisYLZC.getBankCardNum());
			params.put("authCode", bcHisYLZC.getAuthCode());
    		return new ModelAndView("redirect:/creditreport/yinLian").addAllObjects(params);
    	}
		BcHisYLZC ylEntity = new BcHisYLZC();
		Map<String, Object> returnmap = null;
		String result = null;
		String message_info = "";
		Date QueryDate = new Date();
		ylEntity.setAuthCode(bcHisYLZC.getAuthCode());
		ylEntity.setIdCardNum(bcHisYLZC.getIdCardNum());
		ylEntity.setBankCardNum(bcHisYLZC.getBankCardNum());
		try {
			
		returnmap = bcHisYLZCService.retreiveYLZC(ylEntity);
		
		result =returnmap.get("results").toString();
		
		Map<String,String> resultsmap = toHashMap(result);
		String code = resultsmap.get("error_code");
		if(null != code && "0".equals(code)){
			JSONArray jsonArray = JSONArray.fromObject(resultsmap.get("data"));
			JSONObject jsonObj = jsonArray.getJSONObject(0);
			ylEntity.setChvScore(jsonObj.getString("chv_score"));
			ylEntity.setDsiScore(jsonObj.getString("dsi_score"));
			ylEntity.setCntScore(jsonObj.getString("cnt_score"));
			if (jsonObj.get("cna_score") != null && !("null").equals(jsonObj.get("cna_score"))){
				ylEntity.setCnaScore(jsonObj.getString("cna_score"));
			} else{
				ylEntity.setCnaScore("0");
			}
			
			ylEntity.setCnpScore(jsonObj.getString("cnp_score"));
			ylEntity.setCotScore(jsonObj.getString("cot_score"));
			ylEntity.setCstScore(jsonObj.getString("cst_score"));
			ylEntity.setWlpScore(jsonObj.getString("wlp_score"));
			ylEntity.setRskScore(jsonObj.getString("rsk_score"));
			if(jsonObj.getString("dc_flag").equals("debit")){
				ylEntity.setBankCardType("借记卡");
			}else if(jsonObj.getString("dc_flag").equals("credit")){
				ylEntity.setBankCardType("信用卡");
			}
			ylEntity.setCommonLocation(jsonObj.getString("loc_6_var11"));
			ylEntity.setRecentLocation(jsonObj.getString("loc_1_var1"));
			ylEntity.setSummaryScore(jsonObj.getString("rsk_score") == null ? 0 : Long.parseLong(jsonObj.getString("summary_score")));
			ylEntity.setDepositCount12MonthsDetail(jsonObj.getString("rfm_12_var27") == null || jsonObj.getString("rfm_12_var27").equals("null")
					|| jsonObj.getString("rfm_12_var27").equals("\"null\"")? 0 : Long.parseLong(jsonObj.getString("rfm_12_var27")));
			ylEntity.setDepositMoney12MonthsDetail(jsonObj.getString("rfm_12_var23")== null || jsonObj.getString("rfm_12_var23").equals("null")
					|| jsonObj.getString("rfm_12_var23").equals("\"null\"")? 0.0f : Float.parseFloat(jsonObj.getString("rfm_12_var23")));
			ylEntity.setConsumeMoney12MonthsDetail(jsonObj.getString("rfm_12_var1") == null || jsonObj.getString("rfm_12_var1").equals("null")
					|| jsonObj.getString("rfm_12_var1").equals("\"null\"")? 0.0f : Float.parseFloat(jsonObj.getString("rfm_12_var1")));
			ylEntity.setConsumeCount12MonthsDetail(jsonObj.getString("rfm_12_var2") == null || jsonObj.getString("rfm_12_var2").equals("null")
					|| jsonObj.getString("rfm_12_var2").equals("\"null\"")? 0 : Long.parseLong(jsonObj.getString("rfm_12_var2")));
			ylEntity.setDrawCount12MonthsDetail(jsonObj.getString("rfm_12_var29") == null || jsonObj.getString("rfm_12_var29").equals("null")
					|| jsonObj.getString("rfm_12_var29").equals("\"null\"")? 0 : Long.parseLong(jsonObj.getString("rfm_12_var29")));
			ylEntity.setDrawMoney12MonthsDetail(jsonObj.getString("rfm_12_var30") == null || jsonObj.getString("rfm_12_var30").equals("null")
					|| jsonObj.getString("rfm_12_var30").equals("\"null\"")? 0.0f : Float.parseFloat(jsonObj.getString("rfm_12_var30")));
			ylEntity.setTransIn12MonthsDetail(jsonObj.getString("rfm_12_var47") == null || jsonObj.getString("rfm_12_var47").equals("null")
					|| jsonObj.getString("rfm_12_var47").equals("\"null\"")? 0.0f : Float.parseFloat(jsonObj.getString("rfm_12_var47")));
			ylEntity.setTransInCount12MonthsDetail(jsonObj.getString("rfm_12_var48") == null || jsonObj.getString("rfm_12_var48").equals("null")
					|| jsonObj.getString("rfm_12_var48").equals("\"null\"")? 0 : Long.parseLong(jsonObj.getString("rfm_12_var48")));
			ylEntity.setTransOut12MonthsDetail(jsonObj.getString("rfm_12_var44") == null || jsonObj.getString("rfm_12_var44").equals("null")
					|| jsonObj.getString("rfm_12_var44").equals("\"null\"")? 0.0f : Float.parseFloat(jsonObj.getString("rfm_12_var44")));
			ylEntity.setTransOutCount12MonthsDetail(jsonObj.getString("rfm_12_var45") == null || jsonObj.getString("rfm_12_var45").equals("null")
					|| jsonObj.getString("rfm_12_var45").equals("\"null\"")? 0 : Long.parseLong(jsonObj.getString("rfm_12_var45")));
			// charge
			message_info = bcHisYLZCService.chargeYLZC(ylEntity, Sessions.insCode());
			if(message_info!=null){
				model.addAttribute("message_info", message_info);
		        return new ModelAndView("/report/yinLianReport");
			}
		}else if(ConstantUtil.YLZC_CODE_MAP.containsKey(code)){
			message_info = ConstantUtil.STATUS_CODE_MAP.get(ConstantUtil.YLZC_CODE_MAP.get(code));
			params.put("message_info",message_info);
			return new ModelAndView("redirect:/creditreport/yinLian").addAllObjects(params);
		}else{
			message_info = "查询失败，接口返回码异常!";
			params.put("message_info",message_info);
			return new ModelAndView("redirect:/creditreport/yinLian").addAllObjects(params);
		}
		} catch (Exception e) {
			e.printStackTrace();
			message_info = "调用第三方查询出错!";
		}finally{
			
			// save history log
	    	long id = bcHisYLZCService.saveBcHisYLZC(ylEntity);;
	    	if(id == 1L){
	    		//插入查询历史主表
	        	BcHis bcHis = new BcHis();
	        	bcHis.setConditionId(ylEntity.getId());
	        	bcHis.setFlag(0);
	        	bcHis.setQueryCondition(ylEntity.getBankCardNum());
	        	bcHis.setProductCode(MenuConstant.yinLianZCCode);
	        	bcHis.setProductCode2nd("ZC");
	        	bcHis.setQueryDate(QueryDate);
	        	bcHis.setUserId(Sessions.id());
	        	bcHis.setQueryType(3);
	        	long rid = bcHisService.saveBcHis(bcHis);
	        	if(rid == 1L){
	        		//保存查询日志
	    			BcProviderLog bcProviderLog = new BcProviderLog();
	    			bcProviderLog.setQueryDate(QueryDate);
	    			if(returnmap != null){
	    				bcProviderLog.setQueryCondition(returnmap.get("paramjobject").toString());
	    			}else{
	    				bcProviderLog.setQueryCondition("");
	    			}
	    			if(null != result){
	    				bcProviderLog.setQueryResult(result);
	    			}else{
	    				bcProviderLog.setQueryResult("");
	    			}
	    			bcProviderLog.setProvider("ZC");
	    			bcProviderLog.setOperator(Sessions.loginName());
	    			bcProviderLogService.saveBcProviderLog(bcProviderLog);
	        	}
	    	}
		}
		
		//BcHisYLZC entity = bcHisYLZCService.translateScore(ylEntity);
		
		model.addAttribute("bcHisYLZC", ylEntity);
    	model.addAttribute("message_info", message_info);
        return new ModelAndView("/report/yinLianReport");
    }
	
    // 详版报表
    @RequestMapping(value = "/fullSearch", method = RequestMethod.GET)
    public String fullCreditReportSearch( Model model,
			ServletRequest request,@RequestParam(value="useCache",defaultValue="true") boolean useCache) throws NumberFormatException, Exception {
        PersonBasicInfo personBasicInfo;
        PersonSummaryReport personSummaryReport;
        List<PersonApplyDetail> personApplyDetails;
        List<PersonAccDetail>  personAccDetails;
        List<BundPersonComment> personComements;
        CacheResultWapper cache=null;
        boolean fromCache=false;
        
        if(!dbRequestCheck(request, model)){
        	return "/report/fullReport";
        }
        
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
//		String idCardNum = (String)searchParams.get("idCardNum");
		String tempCard=null;
		String idCardNumDecode = null;
        try {
        	idCardNumDecode = (String)searchParams.get("idCardNum");
        	idCardNumDecode = idCardNumDecode.trim().toUpperCase();
            tempCard = DESUtil.encrypt(idCardNumDecode);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String idCardNum =tempCard;
		
		String searchType = (String)searchParams.get("search_type");
		String queryType=""+ControllerConstant.productFullCreditId;
		if(useCache){
		    cache=cacheServcie.get24hCahce(idCardNum, queryType, Sessions.insCode()); 
		}
		
	      if(cache != null){
	          fromCache=true;
	          model.addAttribute("fromCache", fromCache);
	          model.addAttribute("cahceTime", cache.getCacheTime());
	      }
		personAccDetails = fromCache ?cache.getActionDetail():creditSearchService.getPersonAccDetails(idCardNum);
		model.addAttribute("searchTypes", ControllerConstant.searchTypes);
		//验证贷后查询是否符合条件
		/*if(creditSearchService.AFTER_LOAN.equals(searchType) && !creditSearchService.afterLoan(personAccDetails, Sessions.insCode())){
			model.addAttribute("message_info", "查询失败，请选择申请查询");
			return "/report/fullReport";
		}*/
		 personBasicInfo = fromCache ? cache.getBasicInfo():creditSearchService.getPersonBasicInfo(idCardNum);
		 personSummaryReport = fromCache ? cache.getSummaryReport():creditSearchService.getPersonSummaryReport(idCardNum);
		 personApplyDetails = fromCache ? cache.getApplyDetail():creditSearchService.getPersonApplyDetails(idCardNum);
		//验证贷后查询是否符合条件
		if(CreditSearchService.AFTER_LOAN.equals(searchType) && !creditSearchService.afterLoan1(personApplyDetails, Sessions.insCode())){
			model.addAttribute("message_info", "查询失败，请选择申请查询");
			return "/report/fullReport";
		}
		//海纳备注
		Long userId = Sessions.id();
		//扣款
		Boolean noResult =creditSearchService.noFullCreditResult(personAccDetails, Sessions.insCode()); //noFullCreditResult(personBasicInfo);//是否查询出结果
		Float charge = 0f;
		String insCode = Sessions.insCode();
		//判断是否现金支付
		boolean cashflag = creditSearchService.checkIsCashPay(MenuConstant.fullCode);
		if(!fromCache){
		    charge=creditSearchService.calculateCharge(searchType
		    		, MenuConstant.fullCode, noResult, insCode);//计算出费用   
            //验证余额大于费用
            if(!creditSearchService.balanceEnough(charge, insCode, cashflag)){
                model.addAttribute("message_info", "查询失败，余额不足");
                return "/report/fullReport";
            }		    
		}
//		creditSearchService.insertAccQueryHistory(searchType, idCardNum, productFullCreditId, insAccId);
		creditSearchService.recordFullFoot(
				new Integer(searchType),  idCardNumDecode, 
				MenuConstant.fullCode, userId);
		if(fromCache){
			generateTrans(
					charge, 
					idCardNumDecode, 
					insCode,
					cashflag);
//		    insService.updateValueTransactionLog(Sessions.insCode(),charge,"使用24小时征信报告查询缓存，扣费0元.");
		}else{
			String description = "详版征信报告查询："+ IdcardUtils.hideIdCardBirthInfo(idCardNumDecode);
			charge = creditSearchService.adjustCharge(personAccDetails, charge, idCardNum, searchType, Sessions.insCode(), MenuConstant.fullCode);
			creditSearchService.commitFee(charge, 
					insCode,
					cashflag,
        			description);
//		    insService.updateBalance(Sessions.insCode(), 0-charge, idCardNumDecode);//扣费 
		}
		
		personComements = getBCPersonComent(idCardNum,model,cache);
        if(!fromCache){
            cache=new CacheResultWapper();
            cache.setBasicInfo(personBasicInfo);
            cache.setSummaryReport(personSummaryReport);
            cache.setApplyDetail(personApplyDetails);
            cache.setActionDetail(personAccDetails);
            cache.setComments(personComements);
            cacheServcie.safeSave24HCache(idCardNum, queryType, cache, insCode);
        }
		model.addAttribute("personBasicInfo", personBasicInfo);
		model.addAttribute("personSummaryReport", personSummaryReport);
		model.addAttribute("personAccDetails", personAccDetails);
		model.addAttribute("personApplyDetails", personApplyDetails);
		if(ControllerUtils.noResultAtAll(personBasicInfo)){
			model.addAttribute("message_info", "无此用户信息");
		}
        return "/report/fullReport";
    }
    
//    private void commitFee(Float charge, String idCardNumDecode, String insCode, Boolean payInCash, String productType){
//    	String description = productType + "征信报告查询：" + idCardNumDecode;
//    	String comments = description;
//    	String transType = null;
//    	if(payInCash){
//    		transType = InsitutionTransaction.TRANS_TYPES.查询现金扣款.name();
//    	}else{
//    		transType = InsitutionTransaction.TRANS_TYPES.查询外滩币扣款.name();
//    	}
//    	insService.updateBalance(
//    			insCode, 
//    			0-charge, 
//    			description,
//    			comments,
//    			transType,
//    			payInCash);//扣费 
//    }
    
    private void generateTrans(
    		Float charge, 
    		String idCardNumDecode, 
    		String insCode,
    		Boolean payInCash){
    	String description = "使用24小时征信报告查询缓存，扣费0元. 查询身份证：" + IdcardUtils.hideIdCardBirthInfo(idCardNumDecode);
    	String comments = description;
    	String transType = null;
    	if(payInCash){
    		transType = InsitutionTransaction.TRANS_TYPES.查询现金扣款.name();
    	}else{
    		transType = InsitutionTransaction.TRANS_TYPES.查询外滩币扣款.name();
    	}
    	insService.generateTransactionLog(
        		insCode,
        		0F, 
        		description,
        		comments, 
        		transType, 
        		payInCash);
//    	insService.updateBalance(
//    			insCode, 
//    			0-charge, 
//    			description,
//    			comments,
//    			transType,
//    			payInCash);//扣费 
    }
    
    
 // 详版报表
    @RequestMapping(value = "/bundCreditSearch", method = RequestMethod.GET )
    public @ResponseBody String bundCreditSearch(ServletRequest request) {
//        PersonBasicInfo personBasicInfo;
//        PersonSummaryReport personSummaryReport;
//        List<PersonApplyDetail> personApplyDetails;
//        List<PersonAccDetail>  personAccDetails;
//        List<BundPersonComment> personComements;
//		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
////		String idCardNum = (String)searchParams.get("search_id_card_num");
//		String tempCard = null;
//		try {
//            tempCard = des.encrypt((String)searchParams.get("idCardNum"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        String idCardNum =tempCard;
//		String searchStatus = (String)searchParams.get("search_status");
//		Integer queryType=  (Integer)searchParams.get("query_type");//1,2""+productFullCreditId;
//		
//		personAccDetails = creditSearchService.getPersonAccDetails(idCardNum);
//		//验证贷后查询是否符合条件
//		if(CreditSearchService.AFTER_LOAN.equals(searchStatus) && !this.afterLoan(personAccDetails)){
////			model.addAttribute("message_info", "查询失败，请选择申请查询");
//			return "/report/fullReport";
//		}
//		 personBasicInfo = creditSearchService.getPersonBasicInfo(idCardNum);
//		 personSummaryReport = creditSearchService.getPersonSummaryReport(idCardNum);
//		 personApplyDetails = creditSearchService.getPersonApplyDetails(idCardNum);
//		
//		//海纳备注
//		Long insAccId = Sessions.id();
//		//扣款
//		Boolean noResult = noFullCreditResult(personAccDetails);//是否查询出结果
//		Float charge = 0f;
//	    charge=creditSearchService.calculateCharge(searchStatus, queryType, noResult);//计算出费用   
//        //验证余额大于费用
//        if(!balanceEnough(charge)){
////                model.addAttribute("message_info", "查询失败，余额不足");
//            return "/report/fullReport";
//        }		    
//		insService.updateBalance(Sessions.insCode(), 0-charge, idCardNum);//扣费 
////		personComements = getBCPersonComent(idCardNum,model,cache);
//		personComements= creditSearchService.bundCommentByIdCardNum(idCardNum);
//		if(noResultAtAll(personBasicInfo)){
////			model.addAttribute("message_info", "无此用户信息");
//		}
//		
		
		
        return "123";
    }
    
    
//    private boolean balanceEnough(Float charge, Boolean payInCash) {
//		Institution institution = insService.getByInscode(Sessions.insCode());
//		double balance;
//		Double availableBalance;
//		if(payInCash){
//			balance = institution.getCashBalance();
//			availableBalance = balance+institution.getCashCredit();
//		}else{
//			balance = institution.getBalance();
//			availableBalance = balance+institution.getLineOfCredit();
//		}
//		if(availableBalance.compareTo(charge.doubleValue()) >= 0){
//			return true;
//		}
//		return false;
//	}
//	private Boolean noFullCreditResult(PersonBasicInfo personBasicInfo) {
////    	for(PersonAccDetail accDetail :personAccDetails){
////    		if(!Sessions.insCode().equals(accDetail.getInsCode())){
////    			return false;
////    		}
////    	}
//		if(personBasicInfo == null || personBasicInfo.getIdCardNum() == null ){
//			return true;
//		}
//		return false;
//	}
//    private Boolean afterLoan(List<PersonAccDetail> personAccDetails) {
//    	for(PersonAccDetail accDetail :personAccDetails){
//    		if(Sessions.insCode().equals(accDetail.getInsCode()) 
//    				&& !"C".equalsIgnoreCase(accDetail.getLoanStatus())){
//    			return true;
//    		}
//    	}
//		return false;
//	}
	/**
	 * 取出Shiro中的当前用户Id.
	 */
//	private Long getCurrentUserId() {
////		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
//		return Sessions.id();
//	}
	
	//验证手机号是否能查询
	@RequestMapping(value = "/selectISPProvider", method = RequestMethod.GET )
	public ModelAndView selectISPProvider(@RequestParam(value="cellNum") String cellNum,
			@RequestParam(value="authCode") String authCode,Model model){
		String queryprovider = creditSearchService.selectISPProvider(cellNum);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("cellNum", cellNum);
		params.put("authCode", authCode);
		params.put("insCode", Sessions.insCode());
		
		if(!checkAuthCode(params,queryprovider)){
			params.put("message_info","机构授权码重复，请重新输入!");
			return new ModelAndView("redirect:/creditreport/yunYingShang").addAllObjects(params);
		}
		if(queryprovider.equals("GEO")){
			//判断授权码是否重复
			return new ModelAndView("redirect:/creditreport/yunYingShangGEO").addAllObjects(params); 
		}else if(queryprovider.equals("CS")){
			return new ModelAndView("redirect:/creditreport/yunYingShangCS").addAllObjects(params); 
		}else{
			params.put("message_info","您输入的手机号码暂不支持查询，敬请谅解!");
			return new ModelAndView("redirect:/creditreport/yunYingShang").addAllObjects(params);
		}
	}
	
	
	public CreditSearchCacheService getCacheServcie() {
        return cacheServcie;
    }
    
    public void setCacheServcie(CreditSearchCacheService cacheServcie) {
        this.cacheServcie = cacheServcie;
    }
    
    /** 
     * 将json格式的字符串解析成Map对象 <li> 
     * json格式：{"name":"admin","retries":"3fff","testname" 
     * :"ddd","testretries":"fffffffff"} 
     */  
    @SuppressWarnings("rawtypes")
	private static HashMap<String, String> toHashMap(String object)  
    {  
        HashMap<String, String> data = new HashMap<String, String>();  
        JSONObject jsonObject = JSONObject.fromObject(object);  
        Iterator it = jsonObject.keys();  
        while (it.hasNext())  
        {  
            String key = String.valueOf(it.next());  
            String value = jsonObject.get(key).toString();  
            data.put(key, value);  
        }  
        return data;  
    }  
    
	private static Map<String, Object> getCsAsynData(String asyndata){
    	Map<String,Object> resultBody = new HashMap<String,Object>();;
    	JSONObject requestResultData = JSONObject.fromObject(asyndata); 
    	if (null != requestResultData && !StringUtils.isEmpty(requestResultData)){
    		String code = requestResultData.getString("code");
    		if (("0").equals(code)){
    			
    			String homeAddFlag = requestResultData.getString("i_yewan");
    			String workAddFlag = requestResultData.getString("i_baitian");
    			JSONArray callNums = requestResultData.getJSONArray("lianxi1");
    			JSONArray billAmount = requestResultData.getJSONArray("zhangdan_6");
    			JSONArray internetData = requestResultData.getJSONArray("liuliang_6");
    			if(MATCH_FLAG_MAP.containsKey(homeAddFlag)){
    				resultBody.put("home_add_match", MATCH_FLAG_MAP.get(homeAddFlag));
    			} else {
    				resultBody.put("home_add_match", "未知");
    			}
    			
    			if(MATCH_FLAG_MAP.containsKey(workAddFlag)){
    				resultBody.put("work_add_match", MATCH_FLAG_MAP.get(workAddFlag));
    			} else {
    				resultBody.put("work_add_match", "未知");
    			}
    			
    			resultBody.put("recent_call_frequency", handleJsonArray(callNums, RECENT_CALL_FREQUENCY_MAP));
    			resultBody.put("bill_amount", handleJsonArray(billAmount, BILL_AMOUNT_MAP));
    			resultBody.put("bill_data", handleJsonArray(internetData, BILL_DATA_MAP));
    			resultBody.put("message_info", "");
    		} else{
    			if(ConstantUtil.CS_CODE_MAP.containsKey(code)){
    				resultBody.put("message_info",ConstantUtil.STATUS_CODE_MAP.get(ConstantUtil.CS_CODE_MAP.get(code)));
        		}else{
        			resultBody.put("message_info","查询失败，接口返回码异常!");
        		}
    		}
    	}else{
    		resultBody.put("message_info","无详情信息!");
    	}
    	return resultBody;
    }
 
    private static String handleJsonArray(JSONArray jsonArray, Map<String, String> codeMap) {
		String[] arr = new String[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			if (codeMap.containsKey(jsonArray.getString(i))) {
			arr[i] = codeMap.get(jsonArray.getString(i));
			} else {
				arr[i] = jsonArray.getString(i);
			}
		}

		return Arrays.toString(arr);

	}
    
    private boolean checkAuthCode(Map<String,Object> params,String type){
    	String dbnum = null;
    	if("CS".equals(type)){
    		dbnum = bcHisYYSCSService.checkAuthCode(params);
    	}else if("GEO".equals(type)){
    		//GEO验证
    	}else if("YLZC".equals(type)){
    		dbnum = bcHisYLZCService.checkAuthCode(params);
    	}
    	if(StringUtils.isEmpty(dbnum)){
    		return true;
    	}else if("CS".equals(type) && dbnum.equals(params.get("cellNum"))){
    		return true;
    	}else if("YLZC".equals(type) && dbnum.equals(params.get("bankCardNum"))){
    		return true;
    	}
    	return false;
    }
}
