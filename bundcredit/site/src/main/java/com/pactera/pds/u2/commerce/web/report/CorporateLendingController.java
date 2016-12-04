package com.pactera.pds.u2.commerce.web.report;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springside.modules.web.Servlets;

import com.pactera.pds.u2.commerce.entity.BundPersonComment;
import com.pactera.pds.u2.commerce.entity.InsitutionTransaction;
import com.pactera.pds.u2.commerce.entity.PersonAccDetail;
import com.pactera.pds.u2.commerce.entity.PersonApplyDetail;
import com.pactera.pds.u2.commerce.entity.PersonBasicInfo;
import com.pactera.pds.u2.commerce.entity.PersonSummaryReport;
import com.pactera.pds.u2.commerce.service.car.CacheResultWapper;
import com.pactera.pds.u2.commerce.service.car.CreditSearchCacheService;
import com.pactera.pds.u2.commerce.service.car.CreditSearchService;
import com.pactera.pds.u2.commerce.service.insdatamgr.InstitutionService;
import com.pactera.pds.u2.commerce.utils.Constant;
import com.pactera.pds.u2.commerce.utils.ControllerUtils;
import com.pactera.pds.u2.commerce.utils.DESUtil;
import com.pactera.pds.u2.commerce.utils.IdcardUtils;
import com.pactera.pds.u2.commerce.utils.MenuConstant;
import com.pactera.pds.u2.commerce.utils.ControllerConstant;
import com.pactera.pds.u2.commerce.utils.Sessions;

/**
 * 企业贷款查询
 * @author baifan
 *
 */
@Controller
@RequestMapping(value = "/corporateLending")
public class CorporateLendingController {
    
    @Autowired
    private CreditSearchCacheService cacheServcie;
    
    @Autowired
	private CreditSearchService creditSearchService;
    
    @Autowired
    private InstitutionService insService;
    
	// 详版报表
    @RequestMapping(value = "/toReport", method = RequestMethod.GET)
    public String fullCreditReport( Model model,
			ServletRequest request) {
    	Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
    	model.addAttribute("searchTypes", ControllerConstant.searchTypes);
        return "/report/corporateLendingReport";
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
        	return "/report/corporateLendingReport";
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
			return "/report/corporateLendingReport";
		}
		//海纳备注
		Long userId = Sessions.id();
		//扣款
		Boolean noResult =creditSearchService.noFullCreditResult(personAccDetails, Sessions.insCode()); //noFullCreditResult(personBasicInfo);//是否查询出结果
		Float charge = 0f;
		String insCode = Sessions.insCode();
		//判断是否现金支付
		boolean cashflag = creditSearchService.checkIsCashPay(MenuConstant.corporateLendingCode);
		if(!fromCache){
		    charge=creditSearchService.calculateCharge(searchType
		    		, MenuConstant.corporateLendingCode, noResult, insCode);//计算出费用   
            //验证余额大于费用
            if(!creditSearchService.balanceEnough(charge, insCode, cashflag)){
                model.addAttribute("message_info", "查询失败，余额不足");
                return "/report/corporateLendingReport";
            }		    
		}
//		creditSearchService.insertAccQueryHistory(searchType, idCardNum, productFullCreditId, insAccId);
		creditSearchService.recordFullFoot(
				new Integer(searchType),  idCardNumDecode, 
				MenuConstant.corporateLendingCode, userId);
		if(fromCache){
			generateTrans(
					charge, 
					idCardNumDecode, 
					insCode,
					cashflag);
//		    insService.updateValueTransactionLog(Sessions.insCode(),charge,"使用24小时征信报告查询缓存，扣费0元.");
		}else{
			String description = "企业借贷报告查询："+ IdcardUtils.hideIdCardBirthInfo(idCardNumDecode);
			charge = creditSearchService.adjustCharge(personAccDetails, charge, idCardNum, searchType, Sessions.insCode(), MenuConstant.corporateLendingCode);
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
        return "/report/corporateLendingReport";
    }
    
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
    }
    
    private List<BundPersonComment> getBCPersonComent(String idCardNum,Model model,CacheResultWapper cache){
        List<BundPersonComment> personComments=cache != null ? cache.getComments():creditSearchService.bundCommentByIdCardNum(idCardNum);
        model.addAttribute("bcComment", personComments);
        return personComments;
    }
}
