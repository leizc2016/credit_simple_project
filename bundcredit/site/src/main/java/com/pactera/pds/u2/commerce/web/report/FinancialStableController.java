package com.pactera.pds.u2.commerce.web.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.pactera.pds.u2.commerce.entity.BcHis;
import com.pactera.pds.u2.commerce.entity.BcHisEnterpriseQCC;
import com.pactera.pds.u2.commerce.entity.enterpriseinfo.BeExecutorBean;
import com.pactera.pds.u2.commerce.entity.enterpriseinfo.BusinessInfoBean;
import com.pactera.pds.u2.commerce.entity.enterpriseinfo.CourtAnnouncementBean;
import com.pactera.pds.u2.commerce.entity.enterpriseinfo.CourtJudgmentDocBean;
import com.pactera.pds.u2.commerce.entity.enterpriseinfo.DishPersonBean;
import com.pactera.pds.u2.commerce.entity.enterpriseinfo.OrgCodeBean;
import com.pactera.pds.u2.commerce.entity.financialstable.CompanyInfoBean;
import com.pactera.pds.u2.commerce.interceptor.PageHelper;
import com.pactera.pds.u2.commerce.service.enterpriseinfo.EnterpriseInfoService;
import com.pactera.pds.u2.commerce.service.enterpriseinfo.FinancialStableInfoService;
import com.pactera.pds.u2.commerce.service.financialstable.CompanyInfoService;
import com.pactera.pds.u2.commerce.utils.JsonBinder;
import com.pactera.pds.u2.commerce.utils.Sessions;

/**
 * FinancialStableController
 * 
 */
@Controller
@RequestMapping(value = "/creditreport")
public class FinancialStableController {
	@Autowired
	private FinancialStableInfoService financialStableInfoService;
	@Autowired
	private CompanyInfoService companyInfoService;
	@Autowired
	private EnterpriseInfoService enterpriseInfoService;
	private static JsonBinder binder = JsonBinder.buildNonDefaultBinder();
	   
    @RequestMapping(value = "/financialStableDistribute", method = RequestMethod.GET)
    public String financialStableDistribute( Model model, ServletRequest request,RedirectAttributes redirectAttributes) throws NumberFormatException, Exception {
    	String funcNum = request.getParameter("funcNum");
    	if(StringUtils.isNotBlank(funcNum)){
    		if(funcNum.equals("1")){
    			return "/report/fs/newCompany";
    		} else if(funcNum.equals("2")){
    			redirectAttributes.addAttribute("q_companyName", "投资管理");
    			redirectAttributes.addAttribute("q_districtCode", "黄浦");
    			redirectAttributes.addAttribute("q_businessdistrict", "黄浦");
    			return "redirect:/creditreport/financialStableSearch";
    		}else if(funcNum.equals("3")){
    			return "/report/areaEnterpriseInfo";
    		}else if(funcNum.equals("4")){
    			return "/report/fs/comments";
    		}else if(funcNum.equals("5")){
    			return "/report/fs/warning";
    		}
    	}
        return "/report/financialStableStartPage";
    }
    
    // 金融维稳报表
    @RequestMapping(value = "/financialStableSearch", method = RequestMethod.GET)
    public String financialStableSearch( Model model, ServletRequest request) throws NumberFormatException, Exception {
    	String companyName = request.getParameter("q_companyName") == null ? "": request.getParameter("q_companyName").trim();
    	String districtCode = request.getParameter("q_districtCode") == null ? "": request.getParameter("q_districtCode").trim();
    	String businessdistrict = request.getParameter("q_businessdistrict") == null ? "": request.getParameter("q_businessdistrict").trim();
    	PageBounds pageBound = PageHelper.composeFromRequest4site(request, model);
    	Map<String,String> params = new HashMap<String,String>();
    	params.put("name", companyName);
    	params.put("opAddress", businessdistrict);
    	List<CompanyInfoBean> companyInfoBeans = new ArrayList<CompanyInfoBean>();
    	if(!districtCode.equals("") && districtCode.equals("黄浦")){
    		companyInfoBeans = companyInfoService.getAllCompanyInfos(params,pageBound);
    	}
    	if(null  != companyInfoBeans && companyInfoBeans.size() == 0){
    		List<BusinessInfoBean> financialStableBeans = financialStableInfoService.getFinancialStableInfo(companyName, districtCode);
    		if(null  != financialStableBeans && financialStableBeans.size() >0){
    			for(BusinessInfoBean businessInfoBean : financialStableBeans){
    				CompanyInfoBean companyInfoBean = new CompanyInfoBean();
    				companyInfoBean.setName(businessInfoBean.getName());
    				companyInfoBean.setRegID(businessInfoBean.getRegID());
    				companyInfoBean.setAddress(businessInfoBean.getAddress());
    				companyInfoBeans.add(companyInfoBean);
    			}
    		}
    	}
    	if(companyInfoBeans.size() > 0){
    		for(CompanyInfoBean companyInfoBean : companyInfoBeans){
    			//随机生成风险指标次数
    			int number = new Random().nextInt(5);
    			//得到失信次数
    			List<DishPersonBean> dishPersonBeans = enterpriseInfoService.getDishPersonBeanInfo(companyInfoBean.getName());
    			if(null  != dishPersonBeans && dishPersonBeans.size() > 0){
    				number = number + 1;
    				companyInfoBean.setIsExistDishPersons(1);
    			}
    			if(number == 0){
    				companyInfoBean.setMonitorRcd("无建议");
    			}else{
    				companyInfoBean.setMonitorRcd("与专业部门协同走访");
    			}
    			companyInfoBean.setRiskNums(number);
    		}
    	}
    	model.addAttribute("financialStableBeans", companyInfoBeans);
    	model.addAttribute("companyName",companyName);
    	model.addAttribute("districtCode",districtCode);
    	model.addAttribute("businessdistrict",businessdistrict);
        return "/report/financialStable";
    }
    
    @RequestMapping(value = "/getDishNums", method = RequestMethod.GET)
    @ResponseBody
    public String getDishNums(Model model, @RequestParam("keys") String keys){
    	String[] keysArray = keys.split(",");
    	Map<String,String> keysMap = new LinkedHashMap<String,String>();
    	for(String key : keysArray){
    		keysMap.put(key, key);
    	}
    	return financialStableInfoService.getDishPersonsNum(binder.toJson(keysMap));
    }
    
    // 企业信息报表
    @RequestMapping(value = "financialstable/enterpriseSearch", method = RequestMethod.GET)
    public String enterpriseCreditReportSearch( Model model,
			ServletRequest request) throws NumberFormatException, Exception {
    	String registerNum = request.getParameter("registerNum") == null ? "" :request.getParameter("registerNum").trim();
    	String idCardNum = request.getParameter("idCardNum") == null ? "" :request.getParameter("idCardNum").trim();
    	String fullName = request.getParameter("fullName") == null ? "" :request.getParameter("fullName").trim();
    	try {
    		//企业信息查询
        	//工商信息 （注册号registerNum)businessInfoBean
             BusinessInfoBean businessInfoBean =  enterpriseInfoService.getBusinessInfo(registerNum);
             model.addAttribute("businessInfoBean",businessInfoBean);
             
             //组织机构代码
             OrgCodeBean orgCodeBean = null;
             if(null != businessInfoBean && null != businessInfoBean.getName()){
            	 orgCodeBean=  enterpriseInfoService.getOrgCodeInfo(businessInfoBean.getName());
                 model.addAttribute("orgCodeBean", orgCodeBean);
             }
             
        	//被执行人enterpriseExecutorBeans
             List<BeExecutorBean> beExecutorBeans= null;
             if(null != businessInfoBean && null != businessInfoBean.getName()){
            	 beExecutorBeans=  enterpriseInfoService.getBeExecutorBeanInfo(businessInfoBean.getName());
                 model.addAttribute("enterpriseExecutorBeans",beExecutorBeans);
             }
           //失信人enterpriseExecutorBeans
             List<DishPersonBean> dishPersonBeans = null;
             if(null != businessInfoBean && null != businessInfoBean.getName()){
            	 dishPersonBeans=  enterpriseInfoService.getDishPersonBeanInfo(businessInfoBean.getName());
                 model.addAttribute("enterpriseDishPersonBeans",dishPersonBeans);
             }
        	//案号查法院文书enterpriseCourtJudgmentDocBeans
          /*if(null != beExecutorBeans && beExecutorBeans.size() != 0){
            	 List<CourtJudgmentDocBean> courtJudgmentDocBeans = new ArrayList<CourtJudgmentDocBean>();
            	 CourtJudgmentDocBean courtJudgmentDocBean = enterpriseInfoService.getCourtJudgmentDocInfo(beExecutorBeans.get(0).getCaseCode());
            	 if(null != courtJudgmentDocBean){
            		 courtJudgmentDocBeans.add(courtJudgmentDocBean);
            	 }
            	 model.addAttribute("enterpriseCourtJudgmentDocBeans",courtJudgmentDocBeans);
             }
        	//企业名称查法院公告enterpriseCourtAnnouncementBeans
             if(null != businessInfoBean && null != businessInfoBean.getName()){
            	 List<CourtAnnouncementBean>  courtAnnouncementBeans = enterpriseInfoService.getCourtAnnouncementInfo(businessInfoBean.getName());
            	 model.addAttribute("enterpriseCourtAnnouncementBeans",courtAnnouncementBeans);
             }*/
        	//个人信息查询
        	if(StringUtils.isNotBlank(idCardNum)){
        		//身份证查被执行人personExecutorBeans
        		idCardNum = new StringBuffer().append(idCardNum.substring(0,11)).append("****").append(idCardNum.substring(14)).toString();
        		 List<BeExecutorBean> personExecutorBeans=  enterpriseInfoService.getBeExecutorBeanInfo(idCardNum);
                 model.addAttribute("personExecutorBeans",personExecutorBeans);
            	//法院文书personCourtJudgmentDocBeans
                /* if(null != personExecutorBeans && personExecutorBeans.size() != 0){
                	 List<CourtJudgmentDocBean> courtJudgmentDocBeans = new ArrayList<CourtJudgmentDocBean>();
                	 CourtJudgmentDocBean courtJudgmentDocBean = enterpriseInfoService.getCourtJudgmentDocInfo(personExecutorBeans.get(0).getCaseCode());
                	 if(null != courtJudgmentDocBean){
                		 courtJudgmentDocBeans.add(courtJudgmentDocBean);
                	 }
                	 model.addAttribute("personCourtJudgmentDocBeans",courtJudgmentDocBeans);
                 }*/
        	}
		} catch (Exception e) {
			e.printStackTrace();
			String messageInfo = "查询异常";
			model.addAttribute("message_info", messageInfo);
			return "/report/financialStable";
		}
        return "/report/enterpriseInfoReport";
    }
    
}
