
package com.pactera.pds.u2.commerce.repository.mybatis;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.pactera.pds.u2.commerce.entity.AccQueryHistory;
import com.pactera.pds.u2.commerce.entity.BcHis;
import com.pactera.pds.u2.commerce.entity.BcHisConcise;
import com.pactera.pds.u2.commerce.entity.BcHisFull;
import com.pactera.pds.u2.commerce.entity.BcHisYYSGEO;
import com.pactera.pds.u2.commerce.entity.BundPersonComment;
import com.pactera.pds.u2.commerce.entity.Car;
import com.pactera.pds.u2.commerce.entity.EnterpriseInfo;
import com.pactera.pds.u2.commerce.entity.PersonAccDetail;
import com.pactera.pds.u2.commerce.entity.PersonApplyDetail;
import com.pactera.pds.u2.commerce.entity.PersonBasicInfo;
import com.pactera.pds.u2.commerce.entity.PersonComment;
import com.pactera.pds.u2.commerce.entity.PersonSummaryReport;
import com.pactera.pds.u2.commerce.entity.QueryProduct;

/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * 
 */
@MyBatisRepository
public interface CreditSearchMybatisDao {

	List<Car> findAll(Map<String, Object> params, PageBounds pageBounds);
	
	PersonBasicInfo getPersonBasicInfo(String idCardNum);
	
	EnterpriseInfo getEnterpriseInfo(String regID);
	
	PersonSummaryReport getPersonSummaryReport(String idCardNum);
	void savePersonBasicInfo(PersonBasicInfo p);
	void saveApplyDetailInfo(PersonApplyDetail p);
	void saveAccDetailInfo(PersonAccDetail p);
	void savePersonComment(PersonComment p);
	
	PersonSummaryReport getPersonSummaryReport(Long idCardNum);
	
	List<PersonAccDetail> getPersonAccDetails(String idCardNum, PageBounds pageBounds);
	
	List<PersonApplyDetail> getPersonApplyDetails(String idCardNum, PageBounds pageBounds);
	
	void insertAccQueryHistory(AccQueryHistory accQueryHistory);
	
	List<AccQueryHistory> getAccQueryHistorys(Map<String, Object> params, PageBounds pageBounds);
	
	List<PersonBasicInfo> listAllPerson(Map<String, Object> person,PageBounds pb);
	List<BundPersonComment> bundCommentByIdCardNum(String idCardNum);
	Long addBundComment(BundPersonComment bp);

	Float getDiscountByInsCodeProduct(Map<String, Object> params);

	QueryProduct getProduct(String productCode);
	
	Map<String,Object> getIns24hCahceStr(String insCode,String idCardNum,String queryType);
	void save24hCache(String insCode,String idCardNum,String queryType,String cacheObj);
	void update24hCache(String insCode,String idCardNum,String queryType,String cacheObj);
	void delete24hCache(String insCode,String idCardNum,String queryType);
	void save24hCacheLog(String insCode,String idCardNum,String queryType,String cacheObj);

	PersonAccDetail getPersonAccDetail(PersonAccDetail detail);

	void updateAccDetailInfo(PersonAccDetail accInDB);

	Integer getPersonSummaryReportPart1(String idCardNum);

	Integer getPersonSummaryReportPart3(String idCardNum);

	PersonSummaryReport getPersonSummaryReportPart2(String idCardNum);

	Integer getPersonSummaryReportPart4(String idCardNum);
	
	Integer getPersonSummaryReportPart5(String idCardNum);

	Integer getPersonSummaryReportPartCount(String idCardNum);

	void updatePersonBasicInfo(PersonBasicInfo b);

	void recordBcHisConcise(BcHisConcise bcHisConcise);

	void recordBcHis(BcHis bcHis);

	void recordBcHisFull(BcHisFull bcHisFull);

	List<BcHis> bcHis(Map<String, Object> params, PageBounds pageBound);
	
	List<BcHis> getISPHis(Map<String, Object> params, PageBounds pageBound);

	BcHisConcise oneBcHisConcise(Long conditionId);

	BcHisFull oneBcHisFull(Long conditionId);

	void saveGEOHis(BcHisYYSGEO hisGEO);
}
