package com.pactera.pds.u2.commerce.repository.mybatis;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.pactera.pds.u2.commerce.entity.InsProdPrice;
import com.pactera.pds.u2.commerce.entity.InsitutionTransaction;
import com.pactera.pds.u2.commerce.entity.Institution;


@MyBatisRepository
public interface InstitutionMybatisDao {
    Institution get(String insCode);
    
    void save(Institution ins);
    
    void update(Institution ins);
    
    void addBalance(String insCode, Float payNotInCash, Float payInCash);
    
    void updateCredit(String insCode,int haiNaCredit, int cashCredit);
    
    List<Institution> findAll(PageBounds pb);
    List<InsitutionTransaction> allTrans(PageBounds pb);
    List<InsitutionTransaction> findTransactionsByConditions(Map<String, Object> conditions,PageBounds pb);
    List<InsitutionTransaction> allTransByInsCode(String insCode,PageBounds pb);
    Long logTran(InsitutionTransaction transaction);
    List<InsProdPrice> getProductPriceByIns(String insCode);

	void insertInsProdPrice(String prodCode, String insCode, String queryDiscount,int defaultFlag);

	void updateInsProdPrice(String prodCode, String insCode, String queryDiscount,int defaultFlag);

	void updateInsProdPrice1(String prodCode, String insCode, String queryDiscount);
	
	void deleteInsProdPrice(String insCode,String prodCode);
	String getDefaultISP(String insCode);
	
}
