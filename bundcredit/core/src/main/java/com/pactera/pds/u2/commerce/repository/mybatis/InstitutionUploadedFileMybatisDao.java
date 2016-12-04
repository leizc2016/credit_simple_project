package com.pactera.pds.u2.commerce.repository.mybatis;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.pactera.pds.u2.commerce.entity.InstitutionUploadedFile;

@MyBatisRepository
public interface InstitutionUploadedFileMybatisDao {
    InstitutionUploadedFile get(Long id);
    void save(InstitutionUploadedFile df);
    List<InstitutionUploadedFile> findByInsCode(String insCode);
    InstitutionUploadedFile findByUploadTimeAsc(Integer fileStatus);
    void updateFileStatus(Integer fileStatus,Long id);
    List<InstitutionUploadedFile> findAll(PageBounds pb);
    void updateFileValue(Long fileId,Float value);
	void updateFileLog(String log, Long id);
    void updateFileState(int isEnter, long id);
    int hasItem4FailedApply(String idCardType, String idCardNum,
			String insCode, Date applyDatetimeObj);

	int hasItem4SuccessfulApply(String idCardType, String idCardNum,
			String insCode, String loanAccount);

	int hasItem4AccountDetail(String idCardType, String idCardNum,
			String insCode, Date lastestRepayDateObj, String loanAccount);
	
	int hasItem4comment(String idCardType, String idCardNum,
			String insCode, Date commentTime);
	
	List<InstitutionUploadedFile> findAvaliableFiles();
	
    List<InstitutionUploadedFile> findCondition(Map<String, Object> file, PageBounds pb);
	List<InstitutionUploadedFile> findByInsCode4page(String insCode,
			PageBounds pageBound);
}
