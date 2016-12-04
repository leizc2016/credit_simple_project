package com.pactera.pds.u2.commerce.service.car;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.google.common.collect.Maps;
import com.pactera.pds.u2.commerce.entity.AccQueryHistory;
import com.pactera.pds.u2.commerce.entity.BcHis;
import com.pactera.pds.u2.commerce.entity.BcHisConcise;
import com.pactera.pds.u2.commerce.entity.BcHisEnterpriseQCC;
import com.pactera.pds.u2.commerce.entity.BcHisFull;
import com.pactera.pds.u2.commerce.entity.BcHisYYSCS;
import com.pactera.pds.u2.commerce.entity.BcHisYYSGEO;
import com.pactera.pds.u2.commerce.entity.BundPersonComment;
import com.pactera.pds.u2.commerce.entity.EnterpriseInfo;
import com.pactera.pds.u2.commerce.entity.InsitutionTransaction;
import com.pactera.pds.u2.commerce.entity.Institution;
import com.pactera.pds.u2.commerce.entity.PersonAccDetail;
import com.pactera.pds.u2.commerce.entity.PersonApplyDetail;
import com.pactera.pds.u2.commerce.entity.PersonBasicInfo;
import com.pactera.pds.u2.commerce.entity.PersonSummaryReport;
import com.pactera.pds.u2.commerce.entity.QueryProduct;
import com.pactera.pds.u2.commerce.entity.enterpriseinfo.BusinessInfoBean;
import com.pactera.pds.u2.commerce.entity.mybatis.BCUser;
import com.pactera.pds.u2.commerce.repository.mybatis.CreditSearchMybatisDao;
import com.pactera.pds.u2.commerce.repository.mybatis.InstitutionMybatisDao;
import com.pactera.pds.u2.commerce.repository.mybatis.UserMybatisDao;
import com.pactera.pds.u2.commerce.service.account.BCAccountService;
import com.pactera.pds.u2.commerce.service.bchis.BcHisYYSGEOService;
import com.pactera.pds.u2.commerce.service.insdatamgr.InstitutionService;
import com.pactera.pds.u2.commerce.utils.CSHttpsUtil;
import com.pactera.pds.u2.commerce.utils.Constant;
import com.pactera.pds.u2.commerce.utils.ConstantUtil;
import com.pactera.pds.u2.commerce.utils.DESUtil;
import com.pactera.pds.u2.commerce.utils.GEOUtils;
import com.pactera.pds.u2.commerce.utils.IdcardUtils;
import com.pactera.pds.u2.commerce.utils.MenuConstant;
import com.pactera.pds.u2.commerce.utils.MobileEmailValidateUtil;
import com.pactera.pds.u2.commerce.utils.PropertiesInfo;
import com.pactera.pds.u2.commerce.utils.Sessions;


@Component
// 类中所有public函数都纳入事务管理的标识.
@Transactional
public class CreditSearchService {
    
    @Autowired
    private CreditSearchMybatisDao creditSearchMybatisDao;
    
    @Autowired
    CreditSearchCacheService cacheServcie;
    
    @Autowired
    private InstitutionMybatisDao insDao;
    
    @Autowired
    private UserMybatisDao userDao;
    
    
    @Autowired
    private InstitutionService insService;
    
    @Autowired
    private BCAccountService bcAccountService;
    
    public List<PersonBasicInfo> listAllPerson(Map<String, Object> person, PageBounds pb) {
        return creditSearchMybatisDao.listAllPerson(person, pb);
    };
    
    public List<BundPersonComment> bundCommentByIdCardNum(String idCardNum) {
        return creditSearchMybatisDao.bundCommentByIdCardNum(idCardNum);
    };
    
    public Long addBundComment(BundPersonComment bp) {
        return creditSearchMybatisDao.addBundComment(bp);
    };
    
    // public List<Car> searchCredit(Map<String, Object> searchParams) {
    //
    // int page = 1; //椤靛彿
    // int pageSize = 2; //姣忛〉鏁版嵁鏉℃暟
    // Map<String, Object> params = Maps.newHashMap();
    // String sortString2 = "title.desc";
    // PageBounds pageBounds2 = new PageBounds(page, pageSize , Order.formString(sortString2));
    // List<Car> result2 = creditSearchMybatisDao.findAll(params, pageBounds2);
    // PersonBasicInfo person = creditSearchMybatisDao.getPersonBasicInfo(510108L);
    // return result2;
    // }
    
    public PersonBasicInfo getPersonBasicInfo(String idCardNum) {
        PersonBasicInfo person = creditSearchMybatisDao.getPersonBasicInfo(idCardNum);
        return person;
    }
    
    public EnterpriseInfo getEnterpriseInfo(String regID) {
        EnterpriseInfo enterpriseInfo = creditSearchMybatisDao.getEnterpriseInfo(regID);
        return enterpriseInfo;
    }
    
    public PersonSummaryReport getPersonSummaryReport(String idCardNum) {
        // PersonSummaryReport summaryReport = creditSearchMybatisDao.getPersonSummaryReport(idCardNum);
        PersonSummaryReport summaryReport = creditSearchMybatisDao.getPersonSummaryReportPart2(idCardNum);
        if (summaryReport == null) {
            summaryReport = new PersonSummaryReport();
        }
        Integer accountCnt = creditSearchMybatisDao.getPersonSummaryReportPartCount(idCardNum);
        if (accountCnt > 0) {
            Integer loanCnt = creditSearchMybatisDao.getPersonSummaryReportPart1(idCardNum);
            // 发生过逾期的贷款账户数
            Integer overdueLoanAccCnt = creditSearchMybatisDao.getPersonSummaryReportPart5(idCardNum);
            Integer in90LoanApplyCnt = creditSearchMybatisDao.getPersonSummaryReportPart3(idCardNum);
            Integer openLoanCnt = creditSearchMybatisDao.getPersonSummaryReportPart4(idCardNum);
            summaryReport.setOverdueLoanAccCnt(overdueLoanAccCnt);
            summaryReport.setLoanCnt(loanCnt);
            summaryReport.setIn90LoanApplyCnt(in90LoanApplyCnt);
            summaryReport.setOpenLoanCnt(openLoanCnt);
        }
        return summaryReport;
    }
    
    public List<PersonAccDetail> getPersonAccDetails(String idCardNum) {
        int page = 1; // 椤靛彿
        int pageSize = 10000; // 姣忛〉鏁版嵁鏉℃暟
        Map<String, Object> params = Maps.newHashMap();
        String sortString2 = "";
        PageBounds pageBounds = new PageBounds(page, pageSize, Order.formString(sortString2));
        List<PersonAccDetail> personAccDetails = creditSearchMybatisDao.getPersonAccDetails(idCardNum, pageBounds);
        return personAccDetails;
    }
    
    public List<PersonApplyDetail> getPersonApplyDetails(String idCardNum) {
        int page = 1; // 椤靛彿
        int pageSize = 10000; // 姣忛〉鏁版嵁鏉℃暟
        Map<String, Object> params = Maps.newHashMap();
        String sortString2 = "";
        PageBounds pageBounds = new PageBounds(page, pageSize, Order.formString(sortString2));
        List<PersonApplyDetail> personApplyDetail = creditSearchMybatisDao.getPersonApplyDetails(idCardNum, pageBounds);
        return personApplyDetail;
    }
    
    public void insertAccQueryHistory(String queryType, String idCardNum, Integer pageId, Long insAccId) {
        AccQueryHistory accQueryHistory = new AccQueryHistory();
        accQueryHistory.setQueryDatetime(new Date());
        accQueryHistory.setQueryType(queryType);
        accQueryHistory.setIdCardNum(idCardNum);
        accQueryHistory.setPageId(pageId);
        accQueryHistory.setInsAccId(insAccId);
        creditSearchMybatisDao.insertAccQueryHistory(accQueryHistory);
    }
    
    public void recordConciseFoot(Integer queryType, String idCardNum, String productCode, Long userId) throws Exception {
        BcHisConcise bcHisConcise = new BcHisConcise();
        bcHisConcise.setIdCardNum(DESUtil.encrypt(idCardNum));
        bcHisConcise.setQueryType(queryType);
        creditSearchMybatisDao.recordBcHisConcise(bcHisConcise);
        BcHis bcHis = new BcHis();
        bcHis.setQueryType(queryType);
        bcHis.setConditionId(bcHisConcise.getId());
        bcHis.setProductCode(productCode);
        bcHis.setQueryCondition(idCardNum);
        bcHis.setQueryDate(new Date());
        bcHis.setUserId(userId);
        creditSearchMybatisDao.recordBcHis(bcHis);
    }
    
    public void recordFullFoot(Integer queryType, String idCardNum, String productCode, Long userId) throws Exception {
        // String uuid = UuidUtil.getUuid();
        BcHisFull bcHisFull = new BcHisFull();
        // bcHisConcise.setId(uuid);
        bcHisFull.setIdCardNum(DESUtil.encrypt(idCardNum));
        bcHisFull.setQueryType(queryType);
        creditSearchMybatisDao.recordBcHisFull(bcHisFull);
        BcHis bcHis = new BcHis();
        bcHis.setQueryType(queryType);
        bcHis.setConditionId(bcHisFull.getId());
        bcHis.setProductCode(productCode);
        bcHis.setQueryCondition(idCardNum);
        bcHis.setQueryDate(new Date());
        bcHis.setUserId(userId);
        creditSearchMybatisDao.recordBcHis(bcHis);
    }
    
    public List<AccQueryHistory> getAccQueryHistorys(Long insAccId, String pageId, String queryType) {
        int page = 1; // 椤靛彿
        int pageSize = 10; // 姣忛〉鏁版嵁鏉℃暟
        Map<String, Object> params = Maps.newHashMap();
        String sortString2 = "";
        PageBounds pageBounds = new PageBounds(page, pageSize, Order.formString(sortString2));
        params.put("insAccId", insAccId);
        params.put("pageId", pageId);
        params.put("queryType", queryType);
        List<AccQueryHistory> accQueryHistories = creditSearchMybatisDao.getAccQueryHistorys(params, pageBounds);
        return accQueryHistories;
    }
    
    public List<BcHis> getISPHis(Long userId, PageBounds pageBound, String queryType) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("userId", userId);
        params.put("queryType", queryType);
        List<BcHis> bcHisList = creditSearchMybatisDao.getISPHis(params, pageBound);
        return bcHisList;
    }
    
    public List<BcHis> bcHis(Long userId, String productCode, PageBounds pageBound, String queryType) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("userId", userId);
        params.put("productCode", productCode);
        params.put("queryType", queryType);
        List<BcHis> bcHisList = creditSearchMybatisDao.bcHis(params, pageBound);
        return bcHisList;
    }
    
    public List<BcHis> bcHis(Map<String, Object> params, PageBounds pageBound) {
        List<BcHis> bcHisList = creditSearchMybatisDao.bcHis(params, pageBound);
        return bcHisList;
    }
    
    public List<AccQueryHistory> getAccQueryHistorys(Long insAccId, String pageId, String queryType, PageBounds pageBound) {
        // int page = 1; //椤靛彿
        // int pageSize = 10; //姣忛〉鏁版嵁鏉℃暟
        // String sortString2 = "";
        // PageBounds pageBounds = new PageBounds(page, pageSize , Order.formString(sortString2));
        Map<String, Object> params = Maps.newHashMap();
        params.put("insAccId", insAccId);
        params.put("pageId", pageId);
        params.put("queryType", queryType);
        List<AccQueryHistory> accQueryHistories = creditSearchMybatisDao.getAccQueryHistorys(params, pageBound);
        return accQueryHistories;
    }
    
    // 按条件查询
    public List<AccQueryHistory> getAccQueryHistorysByConditions(Long insAccId, String pageId, String queryType,
        Map<String, Object> conditions, PageBounds pageBound) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("insAccId", insAccId);
        params.put("pageId", pageId);
        params.put("queryType", queryType);
        Set<String> keys = conditions.keySet();
        for (String key : keys) {
            params.put(key, conditions.get(key));
        }
        List<AccQueryHistory> accQueryHistories = creditSearchMybatisDao.getAccQueryHistorys(params, pageBound);
        return accQueryHistories;
    }
    
    public static String QUERY_CREDIT = "1";
    
    public static String AFTER_LOAN = "2";
    
    private Float afterLoanDiscount;
    
    public Float calculateCharge(String searchType, String productCode, Boolean noResult, String insCode) {
        // String insCode = Sessions.insCode();
        
        Float discount = this.getDiscountByQueryType(searchType, insCode, productCode);
        Float charge = this.getPrice(productCode, noResult, discount);
        // Float charge =price * discount;
        return charge;
    }
    
    public Float calculateCharge(String productCode, Boolean noResult, String insCode) {
        // String insCode = Sessions.insCode();
        
        Float discount = this.institutionDiscount(insCode, productCode);
        Float charge = this.getPrice(productCode, noResult, discount);
        // Float charge =price * discount;
        return charge;
    }
    
    // public boolean balanceEnough(Float charge, Boolean payInCash) {
    // Institution institution = insService.getByInscode(Sessions.insCode());
    // double balance;
    // if(payInCash){
    // balance = institution.getCashBalance();
    // }else{
    // balance = institution.getBalance();
    // }
    // Double availableBalance = balance+institution.getLineOfCredit();
    // if(availableBalance.compareTo(charge.doubleValue()) >= 0){
    // return true;
    // }
    // return false;
    // }
    
    public boolean balanceEnough(Float charge, String insCode, Boolean payInCash) {
        Institution institution = insService.getByInscode(insCode);
        double balance;
        Double availableBalance;
        if (payInCash) {
            balance = institution.getCashBalance();
            availableBalance = balance + institution.getCashCredit();
        } else {
            balance = institution.getBalance();
            availableBalance = balance + institution.getLineOfCredit();
        }
        if (availableBalance.compareTo(charge.doubleValue()) >= 0) {
            return true;
        }
        return false;
    }
    
    private Float getPrice(String productCode, Boolean noResult, Float discount) {
        QueryProduct product = creditSearchMybatisDao.getProduct(productCode);
        if (noResult) {
            return product.getQueryPrice() + product.getQueryNoreturnPrice() * discount;
        } else {
            return product.getQueryPrice() + product.getQueryReturnPrice() * discount;
        }
    }
    
    private Float getDiscountByQueryType(String searchType, String insCode, String productCode) {
        if (QUERY_CREDIT.equals(searchType)) {
            Map params = new HashMap();
            params.put("insCode", insCode);
            params.put("productCode", productCode);
            Float discount = creditSearchMybatisDao.getDiscountByInsCodeProduct(params);
            return discount;
        }
        if (AFTER_LOAN.equals(searchType)) {
            return afterLoanDiscount;
        }
        return null;
    }
    
    private Float institutionDiscount(String insCode, String productCode) {
        Map params = new HashMap();
        params.put("insCode", insCode);
        params.put("productCode", productCode);
        Float discount = creditSearchMybatisDao.getDiscountByInsCodeProduct(params);
        return discount;
    }
    
    public void setAfterLoanDiscount(Float afterLoanDiscount) {
        this.afterLoanDiscount = afterLoanDiscount;
    }
    
    public BcHisYYSGEO retreiveGEO(BcHisYYSGEO bcHisYYSGEO, String cellOwner, String contactCell) throws Exception {
        JSONObject queryJosn = GEOUtils.requestObj(bcHisYYSGEO.getCallNum(), bcHisYYSGEO.getAuthCode(), cellOwner, contactCell);
        if (PropertiesInfo.isUatFlag()) {
            return GEOUtils.getDefaultBcHisYYSGEO(bcHisYYSGEO);
        }
        
        for (int infoID = 1; infoID <= 5; infoID++) {
            if (infoID == 4) {
                continue;
            }
            GEOUtils.setInfoID(queryJosn, infoID);
            String result = GEOUtils.invokeGEO(queryJosn);
            JSONObject resultJson = JSONObject.fromObject(result);
            JSONObject head = resultJson.getJSONObject("head");
            JSONObject body = resultJson.getJSONObject("body");
            String errorCode = head.getString("errorCode");
            // String errorMsg = head.getString("errorMsg");
            String resultData = null;
            if (errorCode.equals("10009")) {// 暂不支持此功能
                continue;
            }
            if (errorCode.equals("10005")) {
                resultData = body.getString("resultData");
            } else {
                return null;
            }
            if (infoID == 1) {
                if (!"".equals(cellOwner)) {
                    bcHisYYSGEO.setIsMatch(Constant.nameMap.get(resultData));
                }
            } else if (infoID == 2) {
                bcHisYYSGEO.setNetTime(Constant.timeInNet.get(resultData));
            } else if (infoID == 3) {
                bcHisYYSGEO.setSpendingLevel(Constant.consumeClass.get(resultData));
                
            } /*
               * else if (infoID == 4 ) { String[] resultArray = StringUtils.split(resultData, " "); if (resultArray!=null &&
               * resultArray.length>0) {
               * 
               * for (int i = 0; i < resultArray.length; i++) { String status = resultArray[i] .substring(resultArray[i].length() -
               * 1); String site = resultArray[i] .substring(0, resultArray[i].length() - 2); if ("1".equals(status)) {
               * bcHisYYSGEO.setOffice(site); } if ("2".equals(status)) { bcHisYYSGEO.setRest(site); } } } }
               */
            else if (infoID == 5) {
                bcHisYYSGEO.setContactRate(Constant.howOftenComunication.get(resultData));
            }
        }
        // bcHisYYSGEO.setCid(cellNum);
        
        // bcHisYYSGEO.setAuthCode(authCode);
        // bcHisYYSGEO.setSearchDate(new Date());
        // bcHisYYSGEO.setInfoid("0");
        // dmpSearchService.save(geo);
        // bcHisYYSGEOService.saveyBcHisYYSGEO(bcHisYYSGEO);
        return bcHisYYSGEO;
    }
    
    public String chargeGEO(BcHisYYSGEO bcHisYYSGEO, String insCode) {
        Boolean noResult = noResult(bcHisYYSGEO);
        Boolean payCash = checkIsCashPay(MenuConstant.yunYinShangGEOCode);
        Float charge = this.calculateCharge(MenuConstant.yunYinShangGEOCode, noResult, insCode);
        // if(!balanceEnough(
        // charge,
        // insCode,
        // payCash)){
        // return "查询失败，余额不足";
        // }
        String description = "GEO运营商查询：" + MobileEmailValidateUtil.hideMobileMidNumber(bcHisYYSGEO.getCallNum());
        this.commitFee(charge, insCode, payCash, description);
        return null;
    }
    
    public boolean enoughBalance(String insCode, String productCode, Boolean noResult) {
        Boolean payCash = checkIsCashPay(productCode);
        Float charge = calculateCharge(productCode, noResult, insCode);
        if (!balanceEnough(charge, insCode, payCash)) {
            return false;
        }
        return true;
    }
    
    public String chargeCS(BcHisYYSCS bchisyyscs) {
        
        Boolean noResult = noCSResult(bchisyyscs);
        Boolean payCash = checkIsCashPay(MenuConstant.yunYinShangCSCode);
        Float charge = this.calculateCharge(MenuConstant.yunYinShangCSCode, noResult, Sessions.insCode());
        if (!balanceEnough(charge, Sessions.insCode(), payCash)) {
            return "查询失败，余额不足";
        }
        
        String description = "运营商查询：" + MobileEmailValidateUtil.hideMobileMidNumber(bchisyyscs.getCellNum());
        this.commitFee(charge, Sessions.insCode(), payCash, description);
        return null;
    }
    
    public String chargeCS(BcHisYYSCS bchisyyscs, int queryType) {
        if (3 == queryType) {
            return chargeCS(bchisyyscs);
        } else {
            Boolean noResult = noCSResult(bchisyyscs);
            Boolean payCash = checkIsCashPay(MenuConstant.yunYinShangCSCode);
            Float charge = this.calculateCharge(MenuConstant.yunYinShangCSCode, noResult, Sessions.insCode());
            if (!balanceEnough(charge, Sessions.insCode(), payCash)) {
                return "查询失败，余额不足";
            }
            
            String description = "运营商详情查询：" + MobileEmailValidateUtil.hideMobileMidNumber(bchisyyscs.getCellNum());
            this.commitFee(0F, Sessions.insCode(), payCash, description);
            return null;
        }
    }
    
    public String chargeCS(BcHisYYSCS bchisyyscs, String insCode) {
        Boolean noResult = noCSResult(bchisyyscs);
        Boolean payCash = checkIsCashPay(MenuConstant.yunYinShangCSCode);
        Float charge = this.calculateCharge(MenuConstant.yunYinShangCSCode, noResult, insCode);
        if (!balanceEnough(charge, insCode, payCash)) {
            return "查询失败，余额不足";
        }
        String description = "运营商API查询：" + MobileEmailValidateUtil.hideMobileMidNumber(bchisyyscs.getCellNum());
        this.commitFee(charge, insCode, payCash, description);
        return null;
    }
    
    public String chargeQCC(BusinessInfoBean bean, String registerNum, String insCode) {
        Boolean noResult = noQCCResult(bean);
        Boolean payCash = checkIsCashPay(MenuConstant.QYCode);
        Float charge = this.calculateCharge(MenuConstant.QYCode, noResult, insCode);
        if (!balanceEnough(charge, insCode, payCash)) {
            return "查询失败，余额不足";
        }
        String description = "企业信息查询：" + MobileEmailValidateUtil.hideMobileMidNumber(registerNum);
        this.commitFee(charge, insCode, payCash, description);
        return null;
    }
    
    public Map<String, Object> retreiveCS(String cellNum, String idCard, String refTelephoneNum, String workAddr, String homeAddr,
        long caseid, String authCode) throws Exception {
        Map<String, Object> resultsmap = new HashMap<String, Object>();
        Map<String, Object> paramsmap = new HashMap<String, Object>();
        paramsmap.put("s_shouji", cellNum);
        paramsmap.put("s_shenfenID", idCard);
        paramsmap.put("s_contact1", refTelephoneNum);
        paramsmap.put("s_add_baitian", workAddr);
        paramsmap.put("s_add_yewan", homeAddr);
        paramsmap.put("s_caseid", caseid);
        paramsmap.put("SQ", authCode);
        JSONObject jobject = JSONObject.fromObject(paramsmap);
        String invokeresults = CSHttpsUtil.syncQuery(jobject);
        resultsmap.put("results", invokeresults);
        resultsmap.put("paramjobject", jobject);
        return resultsmap;
    }
    
    public void commitFee(Float charge,
    // String cellNum,
        String insCode, Boolean payInCash, String description) {
        // String description = productType + "运营商查询：" + cellNum;
        String comments = description;
        String transType = null;
        if (payInCash) {
            transType = InsitutionTransaction.TRANS_TYPES.查询现金扣款.name();
        } else {
            transType = InsitutionTransaction.TRANS_TYPES.查询外滩币扣款.name();
        }
        insService.updateBalance(insCode, 0 - charge, description, comments, transType, payInCash);// 扣费
    }
    
    private Boolean noCSResult(BcHisYYSCS bchisyyscs) {
        if (bchisyyscs.getNetTime() == null || "".equals(bchisyyscs.getNetTime())) {
            return true;
        }
        return false;
    }
    
    /**
     * 
     * @param bchisyyscs
     * @return
     */
    private Boolean noQCCResult(BusinessInfoBean bean) {
        if (null == bean) {
            return true;
        }
        return false;
    }
    
    private Boolean noResult(BcHisYYSGEO bcHisYYSGEO) {
        if (bcHisYYSGEO.getIsMatch() == null || "".equals(bcHisYYSGEO.getIsMatch())) {
            return true;
        }
        return false;
    }
    
    public String selectISPProvider(String mobile) {
        String url = "http://v.showji.com/Locating/" + "showji.com20150416273007.aspx" + "?m=" + mobile + "&output=json";
        RestTemplate restTemplate = new RestTemplate();
        String output = restTemplate.getForObject(url, String.class);
        
        JSONObject responseData = JSONObject.fromObject(output);
        Boolean flag_cs = false;
        Boolean flag_geo = false;
        String finalProvider = "";
        String provider = (String) responseData.get("Corp");
        String province = (String) responseData.get("Province");
        
        if ("中国移动".equals(provider)) {
            flag_cs = ConstantUtil.CS_ISPSCOPE_MOBILE.contains(province);
            flag_geo = ConstantUtil.GEO_ISPSCOPE_MOBILE.contains(province);
        } else if ("中国电信".equals(provider)) {
            flag_cs = ConstantUtil.CS_ISPSCOPE_TELCOM.contains(province);
            flag_geo = ConstantUtil.GEO_ISPSCOPE_TELCOM.contains(province);
        } else if ("中国联通".equals(provider)) {
            flag_cs = ConstantUtil.CS_ISPSCOPE_UNION.contains(province);
            flag_geo = ConstantUtil.GEO_ISPSCOPE_UNION.contains(province);
        }
        
        String insCode = Sessions.insCode();
        Institution institution = insService.getByInscode(insCode);
        String allPrdCode = institution.getProductCode();
        
        if (!allPrdCode.contains(MenuConstant.yunYinShangCSCode)) {
            flag_cs = false;
        }
        
        if (!allPrdCode.contains(MenuConstant.yunYinShangGEOCode)) {
            flag_geo = false;
        }
        
        if (flag_cs && flag_geo) {
            String defaultISP = insService.getDefaultISP(Sessions.insCode());
            
            if ((MenuConstant.yunYinShangCSCode).equals(defaultISP)) {
                finalProvider = MenuConstant.IPS_CS;
                
            } else if ((MenuConstant.yunYinShangGEOCode).equals(defaultISP)) {
                finalProvider = MenuConstant.IPS_GEO;
            }
            return finalProvider;
        }
        
        if (flag_cs) {
            finalProvider = MenuConstant.IPS_CS;
        }
        
        if (flag_geo) {
            finalProvider = MenuConstant.IPS_GEO;
        }
        
        // if("中国移动".equals(provider)){
        // flag = Constant.avalaibleNum2provinces4mobile.contains(requestData.get("Province"));
        // }else if("中国电信".equals(provider)){
        // flag = Constant.avalaibleNum2provinces4telcom.contains(requestData.get("Province"));
        // }else if("中国联通".equals(provider)){
        // flag = Constant.avalaibleNum2provinces4union.contains(requestData.get("Province"));
        // }
        // if(flag){
        // queryprovider = "GEO";
        // }else{
        // if ("中国移动".equals(provider)) {
        // flag = Constant.CSavalaibleNum2provinces4mobile.contains(responseData.get("Province"));
        // } else if ("中国电信".equals(provider)) {
        // flag = Constant.CSavalaibleNum2provinces4telcom.contains(responseData.get("Province"));
        // } else if ("中国联通".equals(provider)) {
        // flag = Constant.CSavalaibleNum2provinces4union.contains(responseData.get("Province"));
        // }
        // if (flag) {
        // queryprovider = "CS";
        // }
        // }
        return finalProvider;
        // {"Mobile":"18018608288","QueryResult":"True","TO":"中国电信","Corp":"中国电信","Province":"上海","City":"上海","AreaCode":"021","PostCode":"200000","VNO":"","Card":""}
    }
    
    // 判读查询是否现金支付(1:现金支付 0:外滩币支付)
    public boolean checkIsCashPay(String productCode) {
        QueryProduct qproduct = creditSearchMybatisDao.getProduct(productCode);
        if (qproduct.getCashFlag() == 1) {
            return true;
        }
        return false;
    }
    
    public Boolean doubleRequest(String lastRequestTimeKey) {
        // TODO Auto-generated method stub
        Date lastRequestTime = null;
        if (lastRequestTimeKey != null && !"".equals(lastRequestTimeKey)) {
            lastRequestTime = (Date) Sessions.getSessionAttr(lastRequestTimeKey);
        }
        Boolean doubleRequest = isDoubleRequest(lastRequestTime);
        if (lastRequestTimeKey != null && !"".equals(lastRequestTimeKey)) {
            Sessions.setSessionAttr(lastRequestTimeKey, new Date());
        }
        return doubleRequest;
    }
    
    private Boolean isDoubleRequest(Date lastRequestTime) {
        // TODO Auto-generated method stub
        Boolean isDoubleRequest = false;
        if (lastRequestTime == null) {
            isDoubleRequest = false;
            return isDoubleRequest;
        }
        if (System.currentTimeMillis() - lastRequestTime.getTime() < 2000) {
            isDoubleRequest = true;
        } else {
            isDoubleRequest = false;
        }
        return isDoubleRequest;
    }
    
    public BcHisConcise oneBcHisConcise(Long conditionId) {
        BcHisConcise bcHisConcise = creditSearchMybatisDao.oneBcHisConcise(conditionId);
        return bcHisConcise;
    }
    
    public BcHisFull oneBcHisFull(Long conditionId) {
        BcHisFull bcHisFull = creditSearchMybatisDao.oneBcHisFull(conditionId);
        return bcHisFull;
    }
    
    public Boolean noFullCreditResult(List<PersonAccDetail> personAccDetails, String insCode) {
        for (PersonAccDetail accDetail : personAccDetails) {
            if (!insCode.equals(accDetail.getInsCode())) {
                return false;
            }
        }
        return true;
    }
    
    public Boolean afterLoan(List<PersonAccDetail> personAccDetails, String insCode) {
        for (PersonAccDetail accDetail : personAccDetails) {
            if (insCode.equals(accDetail.getInsCode()) && !"C".equalsIgnoreCase(accDetail.getLoanStatus())) {// E未还清
                return true;
            }
        }
        return false;
    }
    
    /**
     * 最新校验是否可以贷后查询，afterLoan方法保留，以后可能会用afterLoan中的判断
     * 
     * @param personApplyDetails
     * @param insCode
     * @return
     */
    public Boolean afterLoan1(List<PersonApplyDetail> personApplyDetails, String insCode) {
        for (PersonApplyDetail applyDetail : personApplyDetails) {
            if (insCode.equals(applyDetail.getInsCode())) {
                return true;
            }
        }
        return false;
    }
    
    public Boolean fromCache(CacheResultWapper cache) {
        if (cache != null) {
            return true;
        }
        return false;
    }
    
    public boolean noResultAtAll(PersonBasicInfo personBasicInfo) {
        if (personBasicInfo == null || StringUtils.isEmpty(personBasicInfo.getIdCardNum())) {
            return true;
        }
        return false;
    }
    
    public void creditData(JSONObject data, Boolean fromCache, CacheResultWapper cache, String idCardNum, String productCode,
        BCUser user, PersonBasicInfo personBasicInfo, PersonSummaryReport personSummaryReport,
        List<BundPersonComment> personComments, List<PersonAccDetail> personAccDetails, List<PersonApplyDetail> personApplyDetails) {
        // PersonBasicInfo personBasicInfo = fromCache ? cache.getBasicInfo():getPersonBasicInfo(idCardNum);
        inputPersonBasicInfo2Data(data, personBasicInfo);
        // PersonSummaryReport personSummaryReport = fromCache ? cache.getSummaryReport():getPersonSummaryReport(idCardNum);
        inputPersonSummaryReport2Data(data, personSummaryReport);
        // List<BundPersonComment> personComments =fromCache != null ? cache.getComments():bundCommentByIdCardNum(idCardNum);
        inputPersonComments2Data(data, personComments);
        
        // List<PersonAccDetail> personAccDetails = fromCache ?cache.getActionDetail():getPersonAccDetails(idCardNum);
        if (MenuConstant.fullCode.equals(productCode)) {// 详版
            // List<PersonApplyDetail> personApplyDetails = fromCache ? cache.getApplyDetail():getPersonApplyDetails(idCardNum);
            inputPersonApplyDetails2Data(data, personApplyDetails, user.getInsCode());
            inputPersonAccDetails2Data(data, personAccDetails, user.getInsCode());
        }
        
    }
    
    private void inputPersonComments2Data(JSONObject data, List<BundPersonComment> personComments) {
        JSONArray array = new JSONArray();
        for (BundPersonComment bundPersonComment : personComments) {
            JSONObject bundPersonCommentObj = new JSONObject();
            // PersonAccDetailObj.put("insCode", personAccDetail.getInsCodeStr());
            bundPersonCommentObj.put("comment_type", bundPersonComment.getCommentType());
            bundPersonCommentObj.put("comment_time", format(bundPersonComment.getLastUpdateDatetime()));
            bundPersonCommentObj.put("comment_detail", bundPersonComment.getContent());
            array.add(bundPersonCommentObj);
        }
        data.put("comment", array);
    }
    
    private void inputPersonAccDetails2Data(JSONObject data, List<PersonAccDetail> personAccDetails, String insCodeVisitor) {
        JSONArray array = new JSONArray();
        for (PersonAccDetail personAccDetail : personAccDetails) {
            JSONObject PersonAccDetailObj = new JSONObject();
            String insCodeName = getInsCodeName(insCodeVisitor, personAccDetail.getInsCode());
            PersonAccDetailObj.put("ins_code", insCodeName);
            PersonAccDetailObj.put("acc_code", personAccDetail.getLoanAccId());
            PersonAccDetailObj.put("update_date", format(personAccDetail.getUpdateDate()));
            PersonAccDetailObj.put("approved_amount", personAccDetail.getTotalAllowedAmountStr());
            PersonAccDetailObj.put("loan_begin_date", personAccDetail.getLoanBeginDate());
            PersonAccDetailObj.put("loan_end_date", personAccDetail.getLoanEndDate());
            PersonAccDetailObj.put("outstangding_balance", personAccDetail.getLoanBalanceStr());
            PersonAccDetailObj.put("next_loan_repay_date", format(personAccDetail.getNextLoanRepayDate()));
            PersonAccDetailObj.put("next_loan_repay_amt", personAccDetail.getNextLoanRepayAmt());
            PersonAccDetailObj.put("loan_status", personAccDetail.getLoanStatus());
            PersonAccDetailObj.put("latest_24mon_status", personAccDetail.getLatest24monStatus());
            array.add(PersonAccDetailObj);
        }
        data.put("acc_detail", array);
    }
    
    private void inputPersonApplyDetails2Data(JSONObject data, List<PersonApplyDetail> personApplyDetails, String insCodeVisitor) {
        JSONArray array = new JSONArray();
        for (PersonApplyDetail personApplyDetail : personApplyDetails) {
            JSONObject personApplyDetailObj = new JSONObject();
            String insCodeName = getInsCodeName(insCodeVisitor, personApplyDetail.getInsCode());
            personApplyDetailObj.put("Ins_code", insCodeName);
            personApplyDetailObj.put("app_amount", personApplyDetail.getApplyAmountStr());
            personApplyDetailObj.put("app_time", format(personApplyDetail.getApplyDatetime()));
            personApplyDetailObj.put("app_type", personApplyDetail.getApplyType());
            personApplyDetailObj.put("app_result", personApplyDetail.getApplyResult());
            personApplyDetailObj.put("approve_amount", personApplyDetail.getApproveAmountStr());
            personApplyDetailObj.put("app_city", personApplyDetail.getApplyProCity());
            personApplyDetailObj.put("address", personApplyDetail.getHomeAddr());
            personApplyDetailObj.put("mobile", personApplyDetail.getSelPhoneNum());
            array.add(personApplyDetailObj);
        }
        data.put("apply_record", array);
    }
    
    private void inputPersonSummaryReport2Data(JSONObject data, PersonSummaryReport personSummaryReport) {
        if (personSummaryReport == null) return;
        JSONObject personSummaryReportObj = new JSONObject();
        personSummaryReportObj.put("total_accounts", personSummaryReport.getLoanCnt());
        personSummaryReportObj.put("open_accounts", personSummaryReport.getOpenLoanCnt());
        personSummaryReportObj.put("outstangding_balance", personSummaryReport.getOpenLoanTotalAmountStr());
        personSummaryReportObj.put("total_next_due_amount", personSummaryReport.getNextLoanRepayAmountStr());
        personSummaryReportObj.put("bad_accounts", personSummaryReport.getOverdueLoanAccCnt());
        personSummaryReportObj.put("bad_accounts_90", personSummaryReport.getOverdue90LoanAccCnt());
        personSummaryReportObj.put("total_app_90", personSummaryReport.getIn90LoanApplyCnt());
        data.put("report", personSummaryReportObj);
        
    }
    
    private void inputPersonBasicInfo2Data(JSONObject data, PersonBasicInfo personBasicInfo) {
        if (personBasicInfo == null) return;
        JSONObject personBasicInfoObj = new JSONObject();
        try {
            personBasicInfoObj.put("id_card_num", personBasicInfo.getIdCardNumString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        personBasicInfoObj.put("id_card_type", "0");
        personBasicInfoObj.put("full_name", personBasicInfo.getFullName());
        personBasicInfoObj.put("location", personBasicInfo.getLocation());
        personBasicInfoObj.put("report_time", format(personBasicInfo.getReportDatetime()));
        personBasicInfoObj.put("updatetime", format(personBasicInfo.getLastUpdateDatetime()));
        data.put("person_info:", personBasicInfoObj);
    }
    
    private String format(Date date) {
        if (date == null) return null;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }
    
    private String getInsCodeName(String insCodeVisitor, String insCode) {
        String insCodeName = Sessions.randomInsName();
        if (insCodeVisitor.equals(insCode)) {
            Institution ins = insService.getByInscode(insCode);
            insCodeName = ins.getName();
            return insCodeName;
        }
        return insCodeName;
    }
    
    public void charge(List<PersonAccDetail> personAccDetails, String insCode, String queryType, String productCode,
        String idCardNum, Boolean fromCache, String idCardNumEnCode) {
        Boolean noResult = noFullCreditResult(personAccDetails, insCode);// 是否查询出结果
        
        Float charge = calculateCharge(queryType, productCode, noResult, insCode);// 计算出费用
        String description = "接口查询" + IdcardUtils.hideIdCardBirthInfo(idCardNum);
        String comments = description;
        String transType = null;
        boolean cashflag = checkIsCashPay(productCode);
        if (cashflag) {
            transType = InsitutionTransaction.TRANS_TYPES.查询现金扣款.name();
        } else {
            transType = InsitutionTransaction.TRANS_TYPES.查询外滩币扣款.name();
        }
        
        if (fromCache) {
            description = "使用24小时征信报告查询缓存，扣费0元. 查询身份证：" + IdcardUtils.hideIdCardBirthInfo(idCardNum);
            comments = description;
            insService.generateTransactionLog(insCode, 0F, description, comments, transType, cashflag);
        } else {
            charge = adjustCharge(personAccDetails, charge, idCardNumEnCode, queryType, insCode, productCode);
            insService.updateBalance(insCode, 0 - charge, description, comments, transType, cashflag);// 扣费
        }
        
    }
    
    public void saveIntoCache(Boolean fromCache, CacheResultWapper cache, PersonBasicInfo personBasicInfo,
        PersonSummaryReport personSummaryReport, List<PersonApplyDetail> personApplyDetails,
        List<PersonAccDetail> personAccDetails, List<BundPersonComment> personComments, String idCardNum, String queryType,
        String insCode) {
        if (!fromCache) {
            cache = new CacheResultWapper();
            cache.setBasicInfo(personBasicInfo);
            cache.setSummaryReport(personSummaryReport);
            cache.setApplyDetail(personApplyDetails);
            cache.setActionDetail(personAccDetails);
            cache.setComments(personComments);
            cacheServcie.safeSave24HCache(idCardNum, queryType, cache, insCode);
        }
        
    }
    
    public Float adjustCharge(List<PersonAccDetail> personAccDetails, Float charge, String idCardNum, String queryType,
        String insCode, String productCode) {// queryType 1:申请查询 2：贷后查询
        if (MenuConstant.fullCode.equals(productCode)) {
            CacheResultWapper cache = cacheServcie.get24hCahce(idCardNum, "1", Sessions.insCode());
            if (cache != null) {
                Boolean noResult = noFullCreditResult(personAccDetails, insCode);
                Float conciseCharge = calculateCharge(queryType, MenuConstant.conciseCode, noResult, insCode);
                if (charge > conciseCharge) {
                    charge = charge - conciseCharge;
                }
            }
        }
        
        return charge;
    }
    
  /*  public void yysGEOHis(DmpSearch geo) {
        // TODO Auto-generated method stu
        BcHisYYSGEO hisGEO = new BcHisYYSGEO();
        hisGEO.setCallNum(geo.getCid());
        hisGEO.setAuthCode(geo.getAuthid());
        hisGEO.setUserName(geo.getCidname());
        hisGEO.setContactNum(geo.getCid2());
        // hisGEO.setIsMatch(geo.getMatch());
        // hisGEO.setNetTime(geo.getTime());
        // hisGEO.setSpendingLevel(geo.getConsumption());
        // hisGEO.setHomeAddr(geo.getRest());
        // hisGEO.setWorkingSite(geo.getOffice());
        // hisGEO.setContactRate(geo.getRate());
        creditSearchMybatisDao.saveGEOHis(hisGEO);
        geo.setId(hisGEO.getId());
    }*/
    
    public void saveGEOHis(BcHisYYSGEO bcHisYYSGEO) {
        creditSearchMybatisDao.saveGEOHis(bcHisYYSGEO);
    }
    
}
