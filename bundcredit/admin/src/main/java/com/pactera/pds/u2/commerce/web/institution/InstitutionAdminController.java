package com.pactera.pds.u2.commerce.web.institution;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.google.common.collect.Maps;
import com.pactera.pds.u2.commerce.entity.BcHis;
import com.pactera.pds.u2.commerce.entity.InsProdPrice;
import com.pactera.pds.u2.commerce.entity.InsitutionTransaction;
import com.pactera.pds.u2.commerce.entity.Institution;
import com.pactera.pds.u2.commerce.entity.InstitutionUploadedFile;
import com.pactera.pds.u2.commerce.entity.QueryProduct;
import com.pactera.pds.u2.commerce.interceptor.PageHelper;
import com.pactera.pds.u2.commerce.service.car.CreditSearchService;
import com.pactera.pds.u2.commerce.service.insdatamgr.InstitutionService;
import com.pactera.pds.u2.commerce.service.insdatamgr.InstitutionUploadedFileService;
import com.pactera.pds.u2.commerce.service.product.QueryProductService;
import com.pactera.pds.u2.commerce.utils.DESUtil;


@Controller
@RequestMapping(value = "/insadmin")
public class InstitutionAdminController {
    
    @Autowired
    private InstitutionUploadedFileService fileService;
    
    @Autowired
    private InstitutionService insService;
    
    @Autowired
    private CreditSearchService creditSearchService;
    
    @Autowired
    private DESUtil des;
    
    @Autowired
    private QueryProductService queryProductService;
    
    // 工作查询，即查询历史
    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public String creditReportHistory(Model model, ServletRequest request) {
    	Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
    	PageBounds pageBound = PageHelper.composeFromRequest4site(request, model);
    	String insCode = request.getParameter("search_insCode");
    	String queryCondition  = request.getParameter("search_queryCondition");
    	Map<String, Object> params = Maps.newHashMap();
		params.put("insCode", insCode);
		params.put("queryCondition", queryCondition);
        List<BcHis> bcHises = creditSearchService.bcHis(params, pageBound);
        model.addAttribute("bcHises", bcHises);
        model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
        model.addAttribute("insCode", insCode);
        model.addAttribute("queryCondition", queryCondition);
        return "/institution/historyList";
    }
    
    @RequestMapping(value = "/{insCode}/discount", method = RequestMethod.GET)
    public String institutionDiscount(@PathVariable("insCode") String insCode, Model model) {
        List<InsProdPrice> insProdPrices = insService.getProductPriceByIns(insCode);
        model.addAttribute("insProdPrices", insProdPrices);
        model.addAttribute("insCode", insCode);
        return "/institution/discount_list";
    }
    
    @RequestMapping(value = "/discount/update", method = RequestMethod.POST)
    public String discountUpdate(Model model, ServletRequest request, RedirectAttributes redirectAttributes) {
        String[] queryDiscounts = (String[]) request.getParameterValues("queryDiscount");
        String[] prodCodes = (String[]) request.getParameterValues("prodCode");
        String[] insCodes = (String[]) request.getParameterValues("insCode");
        String[] insProdPriceIds = (String[]) request.getParameterValues("insProdPriceId");
        
        insService.saveOrUpdateInsProdPrice(insProdPriceIds, queryDiscounts, prodCodes, insCodes,"-1");
        // List<InsProdPrice> insProdPrices = insService.getProductPriceByIns(insCode);
        // model.addAttribute("insProdPrices", insProdPrices);
        redirectAttributes.addFlashAttribute("message", "操作成功");
        return "redirect:/insadmin/" + insCodes[0] + "/discount";
    }
    
    @RequestMapping(value = "/files", method = RequestMethod.GET)
    public String listFiles(@RequestParam(value = "q_uploadDatetime",defaultValue = "") String uploadDatetime,
        @RequestParam(value = "q_insCode", defaultValue = "") String insCode,
        @RequestParam(value = "q_uploadman", defaultValue = "") String uploadman,
        /*@RequestParam(value = "q_fileType" ,defaultValue = "") String fileType, */
        Model model, ServletRequest request) {
        PageBounds pageBound = PageHelper.composeFromRequest(request, model);
        Map<String, Object> file = new HashMap<String, Object>();
       file.put("uploadDatetime", uploadDatetime);
        file.put("insCode", insCode);
        file.put("uploadman", uploadman);
        System.out.println(uploadman+"uploadman");
//        file.put("fileType", fileType);
        model.addAttribute("insFiles", fileService.findCondition(file, pageBound));
        model.addAllAttributes(file);
        return "/insFiles/list";
    }
    
    @RequestMapping(value = "{fileId}/files/enter", method = RequestMethod.GET)
    public String listFilesEnter(@PathVariable("fileId") Long fileId, Model model, RedirectAttributes redirectAttributes) {
        // PageBounds pageBound=PageHelper.composeFromRequest(request,model);
        InstitutionUploadedFile file = fileService.getById(fileId);
        int isenter = file.getIsEnter();
        if (isenter == 1) {
            file.setIsEnter(0);
        } else {
            file.setIsEnter(1);
        }
        fileService.saveInsfile(file);
        redirectAttributes.addFlashAttribute("message", "操作成功");
        return "redirect:/insadmin/files";
    }
    
    @RequestMapping(value = "{fileId}/{fileStatusId}/fileStatusUpdate", method = RequestMethod.GET)
    public String fileStatusUpdate(@PathVariable("fileId") Long fileId, @PathVariable("fileStatusId") int fileStatusId,
        RedirectAttributes redirectAttributes) {
        // PageBounds pageBound=PageHelper.composeFromRequest(request,model);
        fileService.updateFileStatus(fileStatusId, fileId);
        redirectAttributes.addFlashAttribute("message", "操作成功");
        return "redirect:/insadmin/files";
    }
    
    @RequestMapping(value = "{fileId}/file/detail", method = RequestMethod.GET)
    public String fileDataDetails(@PathVariable("fileId") Long fileId, Model model, ServletRequest request) {
        PageBounds pageBound = PageHelper.composeFromRequest(request, model);
        model.addAttribute("details", fileService.fileDataByFileId(fileId, pageBound));
        return "/insFiles/detail";
    }
    
    @RequestMapping(value = "{fileId}/file/evaluation", method = RequestMethod.GET)
    public String fileEvalForm(@PathVariable("fileId") Long fileId, Model model) {
        model.addAttribute("file", fileService.getById(fileId));
        return "/insFiles/evaluation";
    }
    
    @RequestMapping(value = "{fileId}/file/evaluation", method = RequestMethod.POST)
    public String fileEvaluation(@PathVariable("fileId") Long fileId, @RequestParam("value") Float value,
        @RequestParam("comments") String comments, Model model, RedirectAttributes redirectAttributes) {
        insService.evaluationAndImport2Prod(fileId, value, comments);
        redirectAttributes.addFlashAttribute("message", "估值成功，文件编号:" + fileId + "价值:" + value + "备注:" + comments);
        return "redirect:/insadmin/files";
    }
    
    @RequestMapping(value = "/institutions", method = RequestMethod.GET)
    public String listInstitution(Model model, ServletRequest request) {
        // return "/insFiles/contents";
        PageBounds pageBound = PageHelper.composeFromRequest(request, model);
        model.addAttribute("institutions", insService.findAll(pageBound));
        return "/institution/list";
    }
    @RequestMapping(value = "/raninstitutionst", method = RequestMethod.GET)
    public String listInstitutiontran(Model model, ServletRequest request) {
        // return "/insFiles/contents";
        PageBounds pageBound = PageHelper.composeFromRequest(request, model);
        model.addAttribute("institutions", insService.findAll(pageBound));
        return "/institution/listtran";
    }
    @RequestMapping(value = "{insCode}/institution/{page}", method = RequestMethod.GET)
    public String updateInstitution(@PathVariable("insCode") String insCode, @PathVariable("page") String page, Model model) {
        // return "/insFiles/contents";
    	List<QueryProduct> queryProducts = queryProductService.getQueryProduct();
    	model.addAttribute("queryProducts", queryProducts);
        model.addAttribute("institution", insService.getByInscode(insCode));
        return "/institution/" + page;
    }
    
    @RequestMapping(value="/institution/getProductPriceByIns",method = RequestMethod.GET)
    @ResponseBody
    public List<InsProdPrice> getProductPriceByIns(@RequestParam(value="insCode") String insCode){
    	return  insService.getProductPriceByIns(insCode);
    }
    
    @RequestMapping(value = "andins", method = RequestMethod.GET)
    public String andins(Model model) {
        // return "/insFiles/contents";
    	List<QueryProduct> queryProducts = queryProductService.getQueryProduct();
    	model.addAttribute("queryProducts", queryProducts);
        return "/institution/add";
    }
    
    @RequestMapping(value = "insave", method = RequestMethod.POST)
    public String insave(Institution institution,
        @RequestParam(value = "queryDiscounts", defaultValue = "") String queryDiscounts,
        @RequestParam(value = "isDefault", defaultValue = "") String isDefault,Model model,
        RedirectAttributes redirectAttributes) {
        if (insService.getByInscode(institution.getInsCode()) != null) {
            redirectAttributes.addFlashAttribute("message", "机构号重复!");
        } else {
            insService.save(institution);
            String[] queryDiscountArray = queryDiscounts.split(",");
            String[] pcodeArray = institution.getProductCode().split(",");
            for(int i=0;i<queryDiscountArray.length;i++){
            	   if(StringUtils.isNotBlank(isDefault) && isDefault.equals(pcodeArray[i])){
            		   insService.saveInsProdPrice(pcodeArray[i], institution.getInsCode(), queryDiscountArray[i],1);
                   }else{
                	   insService.saveInsProdPrice(pcodeArray[i], institution.getInsCode(), queryDiscountArray[i],0);
                   }
            }
            redirectAttributes.addFlashAttribute("message", "修改机构基础信息成功!");
        }
        return "redirect:/insadmin/institutions";
    }
    
    @RequestMapping(value = "{insCode}/institution", method = RequestMethod.POST)
    public String updateIns(@PathVariable("insCode") String insCode, 
    		@RequestParam(value = "queryDiscounts", defaultValue = "") String queryDiscounts,
            @RequestParam(value = "isDefault", defaultValue = "") String isDefault,Model model,
        RedirectAttributes redirectAttributes, ServletRequest request,Institution institution) {
    	insService.saveOrUpdate(institution);
        //insService.updateName(insCode, newName);
        String[] queryDiscountArray = queryDiscounts.split(",",-1);
        String[] prodCodes = (String[]) request.getParameterValues("prodCode");
        String[] insCodes = (String[]) request.getParameterValues("insCodeArray");
        String[] insProdPriceIds = (String[])request.getParameterValues("insProdPriceId");
        insService.saveOrUpdateInsProdPrice(insProdPriceIds, queryDiscountArray, prodCodes, insCodes,isDefault);
        redirectAttributes.addFlashAttribute("message", "修改机构基础信息成功!");
        return "redirect:/insadmin/institutions";
    }
    
    @RequestMapping(value = "{insCode}/institution/credit", method = RequestMethod.POST)
    public String updateLineOfCredit(
    		@PathVariable("insCode") String insCode, 
    		@RequestParam("lineOfCredit") int credit,
    		@RequestParam("cashCredit") int cashCredit,
            Model model, 
            RedirectAttributes redirectAttributes) {
        insService.updateCredit(insCode, credit, cashCredit);
        redirectAttributes.addFlashAttribute("message", "更新信用额度成功!");
        return "redirect:/insadmin/raninstitutionst";
    }
    
    @RequestMapping(value = "{insCode}/institution/balance", method = RequestMethod.POST)
    public String addBalance(@PathVariable("insCode") String insCode
    		, @RequestParam("balance") Float vitualMoney
    		, @RequestParam("cashBalance") Float realMoney
    		, Model model
    		, RedirectAttributes redirectAttributes) {
    	if(vitualMoney != null 
    			&& vitualMoney.floatValue() != 0F){
    		String description = "海纳征信币充值："+vitualMoney;
    		String comments = "海纳征信币充值："+vitualMoney;
    		String transType = InsitutionTransaction.TRANS_TYPES.充值外滩币.name();
    		insService.updateBalance(
    				insCode, 
    				vitualMoney, 
    				description, 
    				comments, 
    				transType,
    				false);
    	}
    	if(realMoney != null 
    			&& realMoney.floatValue() != 0F){
    		String description = "现金充值："+realMoney;
    		String comments = "现金充值："+realMoney;
    		String transType = InsitutionTransaction.TRANS_TYPES.充值现金.name();
    		insService.updateBalance(
    				insCode, 
    				realMoney, 
    				description, 
    				comments, 
    				transType,
    				true);
    	}
//        insService.addBalance(insCode, balance, cashBalance);
        redirectAttributes.addFlashAttribute("message", "充值成功!");
        return "redirect:/insadmin/raninstitutionst";
    }
    
    @RequestMapping(value = "/transactions", method = RequestMethod.GET)
    public String transactions(@RequestParam(value = "q_insCode", defaultValue = "_") String insCode,
    		@RequestParam(value = "q_transDateTimefrom", defaultValue = "") String transDateTimefrom,
    		@RequestParam(value = "q_transDateTimeto", defaultValue = "") String transDateTimeto, 
    		@RequestParam(value = "q_opName", defaultValue = "") String opName,Model model,
         RedirectAttributes redirectAttributes, ServletRequest request) {
         PageBounds pageBound = PageHelper.composeFromRequest(request, model);
         Map<String, Object> transaction=new HashMap<String, Object>();
        if ("_".equalsIgnoreCase(insCode) && StringUtils.isBlank(transDateTimefrom) && 
        	StringUtils.isBlank(transDateTimeto) && StringUtils.isBlank(opName)) {
            model.addAttribute("transactions", insService.findAllInsTransactions(pageBound));
        } else {
        	transaction.put("transDateTimefrom", transDateTimefrom);
            transaction.put("transDateTimeto", transDateTimeto);
            transDateTimefrom = transDateTimefrom + " 00:00:00";
            transDateTimeto = transDateTimeto + " 23:59:59";
            transaction.put("opName", opName);
            Date transDateTimefromDate = null;
            Date transDateTimetoDate = null;
            try {
            	transDateTimefromDate = format(transDateTimefrom);
            	transDateTimetoDate = format(transDateTimeto);
			} catch (ParseException e) {
				e.printStackTrace();
			}
            Map<String, Object> conditions = new HashMap<String, Object>();
            if("_".equalsIgnoreCase(insCode)){
            	conditions.put("insCode", "");
            	transaction.put("insCode", "");
            }else{
            	conditions.put("insCode", insCode);
            	transaction.put("insCode", insCode);
            }
            conditions.put("transDateTimefrom",transDateTimefromDate);
            conditions.put("transDateTimeto", transDateTimetoDate);
            conditions.put("opName", opName);
            model.addAttribute("transactions", insService.findTransactionsByConditions(conditions,pageBound));
            model.addAttribute("institution", insService.getByInscode(insCode));
        }
        model.addAllAttributes(transaction);
        return "/institution/transactions";
    }
    
    @RequestMapping(value = "/transactions4commercial", method = RequestMethod.GET)
    public String transactions4commercial(@RequestParam(value = "q_insCode", defaultValue = "_") String insCode, Model model,
        RedirectAttributes redirectAttributes, ServletRequest request) {
        PageBounds pageBound = PageHelper.composeFromRequest(request, model);
        if ("_".equalsIgnoreCase(insCode)) {
            model.addAttribute("transactions", insService.findAllInsTransactions(pageBound));
        } else {
            model.addAttribute("institution", insService.getByInscode(insCode));
            model.addAttribute("transactions", insService.findTransactionsByInscode(insCode, pageBound));
        }
        return "/institution/transactions";
    }
    
    public InstitutionUploadedFileService getFileService() {
        return fileService;
    }
    
    public void setFileService(InstitutionUploadedFileService fileService) {
        this.fileService = fileService;
    }
    
    public InstitutionService getInsService() {
        return insService;
    }
    
    public void setInsService(InstitutionService insService) {
        this.insService = insService;
    }
    public static void main(String[] args) {
//		1582750494 1144974336
		System.out.println(Md5Crypt.md5Crypt("Adfasdfasdf".getBytes(), "sd121212").hashCode());
	}
    
    private Date format(String datestr) throws ParseException{
		if(StringUtils.isBlank(datestr))return null;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.parse(datestr);
	}

	public DESUtil getDes() {
		return des;
	}

	public void setDes(DESUtil des) {
		this.des = des;
	}
    
    
}
