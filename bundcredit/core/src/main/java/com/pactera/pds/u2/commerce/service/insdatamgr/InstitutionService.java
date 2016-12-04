package com.pactera.pds.u2.commerce.service.insdatamgr;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.pactera.pds.u2.commerce.entity.InsProdPrice;
import com.pactera.pds.u2.commerce.entity.InsitutionTransaction;
import com.pactera.pds.u2.commerce.entity.Institution;
import com.pactera.pds.u2.commerce.entity.InstitutionUploadedFile;
import com.pactera.pds.u2.commerce.repository.mybatis.InstitutionMybatisDao;
import com.pactera.pds.u2.commerce.utils.Sessions;


//机构管理
@Component("insService")
@Transactional
public class InstitutionService {
    
	private static Logger logger = LoggerFactory.getLogger(InstitutionService.class);
	
    @Autowired
    private InstitutionMybatisDao insDao;
    
    @Autowired
    InstitutionUploadedFileService insFile;
    
    public Institution getByInscode(String insCode) {
        return insDao.get(insCode);
    }
    
  //定时把机构上传的数据文件拆分导入临时表
    public void executeFileIntoProductDB() {
        InstitutionUploadedFile f = insFile.findOne(InstitutionUploadedFile.DATA_MANNUAL_VALIDATION_SUCCESS);
        if (f != null) {
        	insFile.updateFileStatus(InstitutionUploadedFile.DATA_INTO_PRODUCT_DB_PROCESSING, f.getId());
            try {
            	insFile.import2Prod(f.getId());
            	insFile.updateFileStatus(InstitutionUploadedFile.DATA_INTO_PRODUCT_DB_SUCCESS, f.getId());
            } catch (Exception e) {
            	insFile.updateFileStatus(InstitutionUploadedFile.DATA_INTO_PRODUCT_DB_FAILED, f.getId());
//                e.printStackTrace();
            	logger.error("导入正式库出错", e);
                return;
            }
        }
    }
    
    // 估值且导入生产数据库
    public boolean evaluationAndImport2Prod(
    		Long fileId, 
    		Float money, 
    		String comments) {
        boolean result = false;
        InstitutionUploadedFile file = insFile.getById(fileId);
        String insCode = file.getInsCode();
//        insFile.import2Prod(fileId);
        // 文件估值增加
        insFile.updateValue(fileId, money);
        insFile.updateFileStatus(InstitutionUploadedFile.DATA_MANNUAL_VALIDATION_SUCCESS, fileId);
        // 增加机构balance
//        insDao.addBalance(insCode, money, 0f);
        String description = "文件 " + file.getFileName() + " 估价为 " + money;
        String transType = InsitutionTransaction.TRANS_TYPES.上传数据.name();
        updateBalance(
        		insCode, 
        		money, 
        		description, 
        		comments, 
        		transType, 
        		false);
        // 填写数据上传增值日志
//        fileValueTransactionLog(insCode, value, comments);
//        generateTransactionLog(
//        		insCode, 
//        		money, 
//        		description, 
//        		comments, 
//        		transType);
        return result;
    }
    
    // 文件估值日志
//    public Long fileValueTransactionLog(String insCode, Float value, String comments) {
//        InsitutionTransaction log = new InsitutionTransaction();
//        log.setTransType(InsitutionTransaction.TRANS_TYPES.上传数据.name());
//        log.setInsCode(insCode);
//        log.setFee(value);
//        log.setDescription("机构数据估值" + value + "元");
//        log.setInsAccId(Sessions.id());
//        log.setOpName(Sessions.name());
//        log.setTransDateTime(new Timestamp(System.currentTimeMillis()));
//        log.setDevice("PC");
//        log.setComments(comments);
//        log.setIpAddr(Sessions.ip());
//        return insDao.logTran(log);
//    }
    
    // 充值日志记录
    public Long generateTransactionLog(
    		String insCode, 
    		Float money, 
    		String description,
//    		Long insAccId,
//    		String opName,
    		String comments, 
    		String transType,
    		Boolean payInCash) {
        InsitutionTransaction log = new InsitutionTransaction();
        Institution ins = getByInscode(insCode);
        log.setTransType(transType);
        log.setInsCode(insCode);
        log.setFee(money);
        log.setDescription(description);
//        log.setDescription("后台充值" + fee + "元");
        log.setInsAccId(Sessions.id());
//        log.setInsAccId(insAccId);
        log.setOpName(Sessions.name());
//        log.setOpName(opName);
        log.setTransDateTime(new Timestamp(System.currentTimeMillis()));
        log.setDevice("PC");
        log.setComments(comments);
        log.setIpAddr(Sessions.ip());
//        if(payInCash(transType)){
        if(payInCash){
        	log.setBalance(ins.getCashBalance());
        }else{
        	log.setBalance(ins.getBalance());
        }
        return insDao.logTran(log);
    }
    
    public Long generateTransactionLog4Credit(
    		String insCode, 
    		String description,
    		String comments, 
    		String transType,
    		Boolean payInCash) {
        InsitutionTransaction log = new InsitutionTransaction();
        log.setTransType(transType);
        log.setInsCode(insCode);
        log.setFee(0F);
        log.setDescription(description);
        log.setInsAccId(Sessions.id());
        log.setOpName(Sessions.name());
        log.setTransDateTime(new Timestamp(System.currentTimeMillis()));
        log.setDevice("PC");
        log.setComments(comments);
        log.setIpAddr(Sessions.ip());
        log.setBalance(0d);
        Institution ins = getByInscode(insCode);
        if(payInCash){
        	log.setBalance(ins.getCashBalance());
        }else{
        	log.setBalance(ins.getBalance());
        }
        return insDao.logTran(log);
    }
    
    
 // 扣费日志记录
//    public Long updateValueTransactionLog(String insCode, Float fee, String comments) {
//        InsitutionTransaction log = new InsitutionTransaction();
//        log.setTransType(InsitutionTransaction.TRANS_TYPES.查询扣款.name());
//        log.setInsCode(insCode);
//        log.setFee(fee);
//        log.setDescription("查询扣款" + fee + "元");
//        log.setInsAccId(Sessions.id());
//        log.setOpName(Sessions.name());
//        log.setTransDateTime(new Timestamp(System.currentTimeMillis()));
//        log.setDevice("PC");
//        log.setComments(comments);
//        log.setIpAddr(Sessions.ip());
//        return insDao.logTran(log);
//    }
//    
    public List<InsitutionTransaction> findAllInsTransactions(PageBounds pb) {
        return insDao.allTrans(pb);
    }
    
    public List<InsitutionTransaction> findTransactionsByConditions(Map<String, Object> conditions,PageBounds pb) {
        return insDao.findTransactionsByConditions(conditions,pb);
    }
    public List<InsitutionTransaction> findTransactionsByInscode(String insCode,PageBounds pb) {
        return insDao.allTransByInsCode(insCode, pb);
    }
    
    // TODO 查询扣款日志
    public Long queryChargeTransactionLog() {
        return 0l;
    }
    
    public void updateCredit(
    		String insCode, 
    		int haiNaCredit, 
    		int cashCredit) {
        insDao.updateCredit(insCode, haiNaCredit, cashCredit);
        String description = "外滩币账户信用额度调整为" + haiNaCredit;
        String transType = InsitutionTransaction.TRANS_TYPES.外滩币账户信用额度调整.name();
        generateTransactionLog4Credit(insCode, description, description, transType,false);
        description = "现金账户信用额度调整为" + cashCredit;
        transType = InsitutionTransaction.TRANS_TYPES.现金账户信用额度调整.name();
        generateTransactionLog4Credit(insCode, description, description, transType,true);
    }
    
    public void updateName(String insCode, String newName) {
        Institution ins = insDao.get(insCode);
        ins.setName(newName);
        insDao.update(ins);
    }
    
//    public void addBalance(String insCode, Float addValue, Float cashBalance) {
//        insDao.addBalance(insCode, addValue, cashBalance);
////        InsitutionTransaction.TRANS_TYPES.充值.name()
//        generateTransactionLog(insCode, addValue, "机构" + insCode + "充值" + addValue + "元。");
//    }
    
    public void updateBalance(
    		String insCode, 
    		Float money, 
    		String description, 
    		String comments, 
    		String transType,
    		Boolean payIncash) {
    	if(payIncash){
    		insDao.addBalance(insCode, 0F, money);
    	}else{
    		insDao.addBalance(insCode, money, 0F);
    	}
        generateTransactionLog(
        		insCode,
        		money, 
        		description,
        		comments, 
        		transType,
        		payIncash);
//        generateTransactionLog(insCode,money,"机构"+insCode + "查询证件" + idCardNum +"扣费"+addValue+"元。");
    }
    public void saveOrUpdate(Institution ins) {
    	Institution insDB = getByInscode(ins.getInsCode());
        insDao.update(ins);
        updateBalanceLog(ins, insDB);
        updateCreditLog(ins, insDB);
    }
    
    private void updateCreditLog(Institution ins, Institution insDB) {
    	int cashCreditChange = ins.getCashCredit() - insDB.getCashCredit();
    	int waiTanCreditChange = ins.getLineOfCredit() - insDB.getLineOfCredit();
    	String insCode = ins.getInsCode();
    	if(cashCreditChange != 0){
    		String description = "现金账户信用额度调整为" + ins.getCashCredit();
    		String comments = description;
    		String transType = InsitutionTransaction.TRANS_TYPES.现金账户信用额度调整.name();
    		generateTransactionLog4Credit(insCode, description, comments, transType, true);
    	}
    	if(waiTanCreditChange != 0){
    		String description = "外滩币账户信用额度调整为" + ins.getLineOfCredit();
    		String comments = description;
    		String transType = InsitutionTransaction.TRANS_TYPES.外滩币账户信用额度调整.name();
    		generateTransactionLog4Credit(insCode, description, comments, transType, false);
    	}
	}
    
    private void updateBalanceLog(Institution ins, Institution insDB) {
    	
    	
    	Double cash = ins.getCashBalance() - insDB.getCashBalance();
    	Double BCmoney = ins.getBalance() - insDB.getBalance();
    	String insCode = ins.getInsCode();
    	String description = "机构数据更新";
    	String comments = description;
    	if(cash != 0D){
    		String transType = InsitutionTransaction.TRANS_TYPES.充值现金.name();
    		Boolean payIncash = true;
    		generateTransactionLog(
    				insCode,
    				new Float(cash), 
    				description,
    				comments, 
    				transType,
    				payIncash);
    	}
    	if(BCmoney != 0D){
    		String transType = InsitutionTransaction.TRANS_TYPES.充值外滩币.name();
    		Boolean payIncash = false;
    		generateTransactionLog(
    				insCode,
    				new Float(BCmoney), 
    				description,
    				comments, 
    				transType,
    				payIncash);
    	}
		
	}

	public void save(Institution ins) {
        insDao.save(ins);
        newBalanceLog(ins);
        newCreditLog(ins);
    }
    
    
    private void newCreditLog(Institution ins) {
    	int cashCredit = ins.getCashCredit();
    	int waiTanCredit = ins.getLineOfCredit();
    	String insCode = ins.getInsCode();
    	if(cashCredit != 0){
    		String description = "现金账户信用额度调整为" + cashCredit;
    		String comments = description;
    		String transType = InsitutionTransaction.TRANS_TYPES.现金账户信用额度调整.name();
    		generateTransactionLog4Credit(insCode, description, comments, transType, true);
    	}
    	if(waiTanCredit != 0){
    		String description = "外滩币账户信用额度调整为" + waiTanCredit;
    		String comments = description;
    		String transType = InsitutionTransaction.TRANS_TYPES.外滩币账户信用额度调整.name();
    		generateTransactionLog4Credit(insCode, description, comments, transType, false);
    	}
	}

	private void newBalanceLog(Institution ins) {
    	Double cash = ins.getCashBalance();
    	Double BCmoney = ins.getBalance();
    	String insCode = ins.getInsCode();
    	String description = "机构数据新增";
    	String comments = description;
    	if(cash != 0D){
    		String transType = InsitutionTransaction.TRANS_TYPES.充值现金.name();
    		Boolean payIncash = true;
    		generateTransactionLog(
    				insCode,
    				new Float(cash), 
    				description,
    				comments, 
    				transType,
    				payIncash);
    	}
    	if(BCmoney != 0D){
    		String transType = InsitutionTransaction.TRANS_TYPES.充值外滩币.name();
    		Boolean payIncash = false;
    		generateTransactionLog(
    				insCode,
    				new Float(BCmoney), 
    				description,
    				comments, 
    				transType,
    				payIncash);
    	}
		
	
		
	}

	public List<Institution> findAll() {
        return insDao.findAll(new PageBounds());
    }
    
    public List<Institution> findAll(PageBounds pg) {
        return insDao.findAll(pg);
    }
    
    public InstitutionMybatisDao getInsDao() {
        return insDao;
    }
    
    public void setInsDao(InstitutionMybatisDao insDao) {
        this.insDao = insDao;
    }
    
    public InstitutionUploadedFileService getInsFile() {
        return insFile;
    }
    
    public void setInsFile(InstitutionUploadedFileService insFile) {
        this.insFile = insFile;
    }

	public List<InsProdPrice> getProductPriceByIns(String insCode) {
		List<InsProdPrice> list = insDao.getProductPriceByIns(insCode);
		return list;
	}

	public void saveOrUpdateInsProdPrice(String[] insProdPriceIds,
			String[] queryDiscounts, String[] prodCodes, String[] insCodes,String isDefault) {
		int index = 0;
		for(String insProdPriceId : insProdPriceIds){
//			if("".equals(queryDiscounts[index]) || !isDecimal(queryDiscounts[index])){
//				queryDiscounts[index] = "1.0";
//			}
			if((insProdPriceId == null || "".equals(insProdPriceId)) && StringUtils.isNotBlank(queryDiscounts[index]) ){
				if(StringUtils.isNotBlank(isDefault) && prodCodes[index].equals(isDefault)){
					insDao.insertInsProdPrice(prodCodes[index], insCodes[index], queryDiscounts[index],1);
				}else{
					insDao.insertInsProdPrice(prodCodes[index], insCodes[index], queryDiscounts[index],0);
				}
			}else if(StringUtils.isNotBlank(queryDiscounts[index])){
				if(StringUtils.isNotBlank(isDefault) && prodCodes[index].equals(isDefault)){
					insDao.updateInsProdPrice( prodCodes[index], insCodes[index], queryDiscounts[index],1);
				}else if(StringUtils.isNotBlank(isDefault) &&isDefault.equals("-1")){
					insDao.updateInsProdPrice1( prodCodes[index], insCodes[index], queryDiscounts[index]);
				}else{
					insDao.updateInsProdPrice( prodCodes[index], insCodes[index], queryDiscounts[index],0);
				}
			}else{
				insDao.deleteInsProdPrice(insCodes[index], prodCodes[index]);
			}
			index++;
		}
	}
	public void saveInsProdPrice(
      String prodCodes, String insCodes,String queryDiscounts,int defaultFlag) {
	    insDao.insertInsProdPrice(prodCodes, insCodes, queryDiscounts,defaultFlag);
	}
	
	public String getDefaultISP(String insCode) {
		return insDao.getDefaultISP(insCode);
	}
	
//	public static boolean isDecimal(String str){  
//		  return Pattern.compile("([1-9]+[0-9]*|0)(\\.[\\d]+)?").matcher(str).matches();  
//		 }  
}
