package com.pactera.pds.u2.commerce.web.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.google.common.collect.Maps;
import com.pactera.pds.u2.commerce.entity.BcHis;
import com.pactera.pds.u2.commerce.entity.BcHisEnterpriseQCC;
import com.pactera.pds.u2.commerce.entity.enterpriseinfo.BeExecutorBean;
import com.pactera.pds.u2.commerce.entity.enterpriseinfo.BusinessInfoBean;
import com.pactera.pds.u2.commerce.entity.enterpriseinfo.CourtAnnouncementBean;
import com.pactera.pds.u2.commerce.entity.enterpriseinfo.CourtJudgmentDocBean;
import com.pactera.pds.u2.commerce.entity.enterpriseinfo.DishPersonBean;
import com.pactera.pds.u2.commerce.entity.enterpriseinfo.OrgCodeBean;
import com.pactera.pds.u2.commerce.service.BcCourtBulletinService;
import com.pactera.pds.u2.commerce.service.BcCourtDishonestExecutorService;
import com.pactera.pds.u2.commerce.service.BcCourtExecutorService;
import com.pactera.pds.u2.commerce.service.BcCourtJudgmentDocService;
import com.pactera.pds.u2.commerce.service.BcEnterpriseInfoService;
import com.pactera.pds.u2.commerce.service.BcOrganizationCodeService;
import com.pactera.pds.u2.commerce.service.account.BCAccountService;
import com.pactera.pds.u2.commerce.service.bchis.BcHisEnterpriseQCCService;
import com.pactera.pds.u2.commerce.service.bchis.BcHisService;
import com.pactera.pds.u2.commerce.service.bchis.BcHisYLZCService;
import com.pactera.pds.u2.commerce.service.bchis.BcHisYYSCSService;
import com.pactera.pds.u2.commerce.service.bcproviderlog.BcProviderLogService;
import com.pactera.pds.u2.commerce.service.car.CreditSearchCacheService;
import com.pactera.pds.u2.commerce.service.car.CreditSearchService;
import com.pactera.pds.u2.commerce.service.enterpriseinfo.EnterpriseInfoService;
import com.pactera.pds.u2.commerce.service.insdatamgr.InstitutionService;
import com.pactera.pds.u2.commerce.utils.Constant;
//import com.pactera.pds.u2.commerce.utils.Constant4Payment;
import com.pactera.pds.u2.commerce.utils.DESUtil;
import com.pactera.pds.u2.commerce.utils.JsonBinder;
import com.pactera.pds.u2.commerce.utils.MenuConstant;
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
public class EnterpriseReportController {
    @Autowired
    private BcHisService bcHisService;
    @Autowired
	private CreditSearchService creditSearchService;
    @Autowired
    private EnterpriseInfoService enterpriseInfoService;
    @Autowired
    private BcHisEnterpriseQCCService bcHisEnterpriseQCCService;
    @Autowired
    private BcEnterpriseInfoService bcEnterpriseInfoService;
    @Autowired
    private BcOrganizationCodeService bcOrganizationCodeService;
    @Autowired
    private BcCourtExecutorService bcCourtExecutorService;
    @Autowired
    private BcCourtDishonestExecutorService bcCourtDishonestExecutorService;
    @Autowired
    private BcCourtJudgmentDocService bcCourtJudgmentDocService;
    @Autowired
    private BcCourtBulletinService bcCourtBulletinService;
    private static JsonBinder binder = JsonBinder.buildNonDefaultBinder(); 
    private static Map<String, String> searchTypes = Maps.newLinkedHashMap();
	static {
		searchTypes.put("text_credit", "申请查询");
		searchTypes.put("text_after_loan", "贷后查询");
		searchTypes.put("key_credit", "1");
		searchTypes.put("key_after_loan", "2");
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
	
    // 工商报告
    @RequestMapping(value = "/enterprise", method = RequestMethod.GET)
    public String enterpriseCreditReport( Model model, ServletRequest request) {
    	String registerNum = request.getParameter("registerNum");
    	String idCardNum = request.getParameter("idCardNum");
		String fullname = request.getParameter("fullname");
		String messageInfo = request.getParameter("message_info");
		model.addAttribute("idCardNum", idCardNum);
		model.addAttribute("registerNum", registerNum);
		model.addAttribute("fullname", fullname);
		model.addAttribute("message_info", messageInfo);
        return "/report/enterpriseInfo";
    }
    
    // 企业信息报表
    @RequestMapping(value = "/enterpriseSearch", method = RequestMethod.GET)
    public String enterpriseCreditReportSearch( Model model,
			ServletRequest request) throws NumberFormatException, Exception {

    	String registerNum = request.getParameter("registerNum") == null ? "" :request.getParameter("registerNum").trim();
    	String idCardNum = request.getParameter("idCardNum") == null ? "" :request.getParameter("idCardNum").trim();
    	String fullName = request.getParameter("fullName") == null ? "" :request.getParameter("fullName").trim();
    	if(!dbRequestCheck(request, model)){
    		model.addAttribute("idCardNum", idCardNum);
    		model.addAttribute("registerNum", registerNum);
    		model.addAttribute("fullname", fullName);
        	return "/report/enterpriseInfo";
        }
    	
    	if(!creditSearchService.enoughBalance(
    			Sessions.insCode(),
    			MenuConstant.QYCode, 
    			false)){
			model.addAttribute("message_info", "查询失败，余额不足");
			model.addAttribute("idCardNum", idCardNum);
    		model.addAttribute("registerNum", registerNum);
    		model.addAttribute("fullname", fullName);
			return "/report/enterpriseInfo";
    	}
    	try {
    		//企业信息查询
        	//工商信息 （注册号registerNum)businessInfoBean
    		//bc_enterprise_info
    		 BusinessInfoBean businessInfoBean =  bcEnterpriseInfoService.queryBcEnterpriseInfoById(registerNum);
    		 if(businessInfoBean==null){
    			businessInfoBean =  enterpriseInfoService.getBusinessInfo(registerNum);
    			if(businessInfoBean == null){
    				String result = enterpriseInfoService.getBusinessInfoFromApi(registerNum);
    				if(null !=null && result.equals("-1")){
	       				 model.addAttribute("message_info", "查询失败，请联系客服!");
	   					 model.addAttribute("idCardNum", idCardNum);
	   			    	 model.addAttribute("registerNum", registerNum);
	   			    	 model.addAttribute("fullname", fullName);
	   					 return "/report/enterpriseInfo";
       			 	}else if(null != result){
       			 		businessInfoBean = binder.fromJson(result, BusinessInfoBean.class);
       			 	}
    			}
    			 if(businessInfoBean!=null){
    				 bcEnterpriseInfoService.saveBcEnterpriseInfo(businessInfoBean);
    			 }else if(businessInfoBean == null){
					 model.addAttribute("message_info", "根据工商注册号" +registerNum+ "查询企业不存在");
					 model.addAttribute("idCardNum", idCardNum);
			    	 model.addAttribute("registerNum", registerNum);
			    	 model.addAttribute("fullname", fullName);
					 return "/report/enterpriseInfo";
    			 }
    		 }
             model.addAttribute("businessInfoBean",businessInfoBean);
             //组织机构代码 bc_organization_code
             OrgCodeBean orgCodeBean = null;
             if(null != businessInfoBean && null != businessInfoBean.getName()){
            	 OrgCodeBean queryOrgCodeBean =new OrgCodeBean();
            	 queryOrgCodeBean.setBusinessName(businessInfoBean.getName());
            	 orgCodeBean=bcOrganizationCodeService.queryBcOrganizationCodeById(queryOrgCodeBean);
            	 if(orgCodeBean==null){
            		 orgCodeBean=  enterpriseInfoService.getOrgCodeInfo(businessInfoBean.getName());
            		 bcOrganizationCodeService.saveBcOrganizationCode(orgCodeBean,businessInfoBean.getName());
            	 }
                 model.addAttribute("orgCodeBean", orgCodeBean);
             }
             
        	//被执行人enterpriseExecutorBeans   bc_court_executor
             List<BeExecutorBean> beExecutorBeans= null;
             if(null != businessInfoBean && null != businessInfoBean.getName()){
            	 beExecutorBeans= bcCourtExecutorService.queryBcCourtExecutorByBusinessName(businessInfoBean.getName());
            	 if(beExecutorBeans==null||beExecutorBeans.size()==0){
            		 beExecutorBeans=  enterpriseInfoService.getBeExecutorBeanInfo(businessInfoBean.getName());
            		 bcCourtExecutorService.saveBcCourtExecutors(beExecutorBeans,businessInfoBean.getName());
            	 }
                 model.addAttribute("enterpriseExecutorBeans",beExecutorBeans);
             }
           //失信人enterpriseExecutorBeans bc_court_dishonest_executor
             List<DishPersonBean> dishPersonBeans = null;
             if(null != businessInfoBean && null != businessInfoBean.getName()){
            	 DishPersonBean queryDishPersonFirst=new DishPersonBean();
            	 queryDishPersonFirst.setBusinessName(businessInfoBean.getName());
            	 dishPersonBeans=  bcCourtDishonestExecutorService.queryBcCourtDishonestExecutorByBusinessName(queryDishPersonFirst);
            	 if(dishPersonBeans==null||dishPersonBeans.size()==0){
            		 dishPersonBeans=  enterpriseInfoService.getDishPersonBeanInfo(businessInfoBean.getName());
            		 bcCourtDishonestExecutorService.saveBcCourtDishonestExecutors(dishPersonBeans,businessInfoBean.getName());
            	 }
            	 
                 model.addAttribute("enterpriseDishPersonBeans",dishPersonBeans);
             }
        	//案号查法院文书enterpriseCourtJudgmentDocBeans   bc_court_judgment_doc
             if(null != beExecutorBeans && beExecutorBeans.size() != 0){
            	List<CourtJudgmentDocBean> courtJudgmentDocBeans = new ArrayList<CourtJudgmentDocBean>();
            	for (BeExecutorBean beEx : beExecutorBeans) {
	               	 String caseCode=beEx.getCaseCode();
	               	 if(caseCode==null||"".equals(caseCode)){
	               		 continue;
	               	 }
//	                 String tempCaseCode=caseCode.replaceAll("\\(", "（").replaceAll("\\)", "）").replaceAll(" ", "").replaceAll("O", "0").replaceAll("o", "0");
	               	courtJudgmentDocBeans = bcCourtJudgmentDocService.queryBcCourtJudgmentDocByBusinessName(businessInfoBean.getName());
//	               	 if(courtJudgmentDocBean==null){
//	               		 courtJudgmentDocBean = enterpriseInfoService.getCourtJudgmentDocInfo(caseCode);
//	               		 bcCourtJudgmentDocService.saveBcCourtJudgmentDoc(courtJudgmentDocBean);
//	               	 }
	               	
//	               	 if(null != courtJudgmentDocBean){
//	               		 courtJudgmentDocBeans.add(courtJudgmentDocBean);
//	               	 }
				}
            	 
            	 model.addAttribute("enterpriseCourtJudgmentDocBeans",courtJudgmentDocBeans);
             }
        	//企业名称查法院公告enterpriseCourtAnnouncementBeans  bc_court_bulletin
             if(null != businessInfoBean && null != businessInfoBean.getName()){
            	 List<CourtAnnouncementBean>  courtAnnouncementBeans = bcCourtBulletinService.queryBcCourtBulletinsByBusinessName(businessInfoBean.getName());
            	 if(courtAnnouncementBeans==null||courtAnnouncementBeans.size()==0){
            		 courtAnnouncementBeans = enterpriseInfoService.getCourtAnnouncementInfo(businessInfoBean.getName());
            		 bcCourtBulletinService.saveBcCourtBulletins(courtAnnouncementBeans,businessInfoBean.getName());
            	 }
            	
            	 model.addAttribute("enterpriseCourtAnnouncementBeans",courtAnnouncementBeans);
             }
        	//个人信息查询
        	if(StringUtils.isNotBlank(idCardNum)){
        		String tempIdCardNum=idCardNum;
        		//身份证查被执行人personExecutorBeans
        		idCardNum = new StringBuffer().append(idCardNum.substring(0,11)).append("****").append(idCardNum.substring(14)).toString();
        		
        		List<BeExecutorBean> personExecutorBeans= bcCourtExecutorService.queryBcCourtExecutorByCode(idCardNum);
        		if(personExecutorBeans==null||personExecutorBeans.size()==0){
        			personExecutorBeans=enterpriseInfoService.getBeExecutorBeanInfo(idCardNum);
        			bcCourtExecutorService.saveBcCourtExecutors(beExecutorBeans,"");
        		}
        		model.addAttribute("personExecutorBeans",personExecutorBeans);
        		DishPersonBean queryDishPerson=new DishPersonBean();
        		queryDishPerson.setCode(tempIdCardNum);
        		//查询个人失信人
        		List<DishPersonBean> dishPersons=  bcCourtDishonestExecutorService.queryBcCourtDishonestExecutorByBusinessName(queryDishPerson);
        		if(null == dishPersons || dishPersons.size() == 0){
        			dishPersons= enterpriseInfoService.getDishPersonBeanInfoByIdCardNum(idCardNum);
        			if(null != dishPersons && null != businessInfoBean){
        				bcCourtDishonestExecutorService.saveBcCourtDishonestExecutors(dishPersonBeans, businessInfoBean.getName());
        			}
        		}
        		 model.addAttribute("personDishPersons",dishPersons);
            	//法院文书personCourtJudgmentDocBeans
                 if(null != personExecutorBeans && personExecutorBeans.size() != 0){
                	 List<CourtJudgmentDocBean> courtJudgmentDocBeans = new ArrayList<CourtJudgmentDocBean>();
//                	 String caseCode=personExecutorBeans.get(0).getCaseCode();
                	 courtJudgmentDocBeans = bcCourtJudgmentDocService.queryBcCourtJudgmentDocByBusinessName(businessInfoBean.getName());
//                	 if(courtJudgmentDocBean==null){
//                		 courtJudgmentDocBean = enterpriseInfoService.getCourtJudgmentDocInfo(caseCode);
//                		 bcCourtJudgmentDocService.saveBcCourtJudgmentDoc(courtJudgmentDocBean);
//                	 }
//                	 if(null != courtJudgmentDocBean){
//                		 courtJudgmentDocBeans.add(courtJudgmentDocBean);
//                	 }
                	 model.addAttribute("personCourtJudgmentDocBeans",courtJudgmentDocBeans);
                 }
        	}
        	
        	//扣费
    		String message=creditSearchService.chargeQCC(businessInfoBean, registerNum,Sessions.insCode());
    		 if(message!=null){
				 model.addAttribute("message_info", message);
				 return "/report/enterpriseReport";
    		 }
    		 
        	BcHisEnterpriseQCC  qcc=new BcHisEnterpriseQCC();
        	qcc.setRegisterNum(registerNum);
        	qcc.setIdCardNum(idCardNum);
        	qcc.setFullName(fullName);
        	//插入历史
        	bcHisEnterpriseQCCService.saveBcHisEnterPriseQCC(qcc);
        	
        	BcHis bcHis=new BcHis();
        	bcHis.setQueryCondition(registerNum);
        	bcHis.setQueryDate(new Date());
        	bcHis.setProductCode("QYCX");
        	bcHis.setProductCode2nd("QYCX");
        	bcHis.setFlag(3);
        	bcHis.setConditionId(qcc.getId());
        	bcHis.setUserId(Sessions.id());
        	bcHis.setQueryType(3);
        	bcHisService.saveBcHis(bcHis);
        	
		} catch (Exception e) {
			e.printStackTrace();
			String messageInfo = "查询异常";
			model.addAttribute("message_info", messageInfo);
			return "/report/enterpriseReport";
		}
        return "/report/enterpriseReport";
    }
}
