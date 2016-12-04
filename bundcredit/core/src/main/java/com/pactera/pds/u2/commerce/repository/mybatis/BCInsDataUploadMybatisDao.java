package com.pactera.pds.u2.commerce.repository.mybatis;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.pactera.pds.u2.commerce.entity.mybatis.*;

@MyBatisTempDataRepository
public interface BCInsDataUploadMybatisDao {
    
    void addApplyBatch(List trainRecordList);
    
    void addDetailBatch(List trainRecordList);
    
    void addHistoryBatch(List trainRecordList);
    
    void addCommentBatch(List commentList);
    
    void addBundBatch(List trainRecordList);
    
    List<BCApplyDetailTmp> findApplyRecByFileId(@Param("fileId") Long fileId,@Param("bounds") PageBounds pg);
    
    List<BCAccountDetailTmp> findDetailRecByFileId(@Param("fileId") Long fileId,@Param("bounds") PageBounds pg);
    
    List<BCHistoryRecordTmp> findHistoryRecByFileId(@Param("fileId") Long fileId,@Param("bounds") PageBounds pg);
    
    List<BCCommentTmp> findCommentByFileId(@Param("fileId") Long fileId,@Param("bounds") PageBounds pg);
    
	int hasItem4FailedApplyTmp(String idCardType, String idCardNum, String insCode, String applyDatetime, String fileIDs);

	int hasItem4SuccessfulApplyTmp(String idCardType, String idCardNum, String insCode, String loanAccount, String filesIDs);

	int hasItem4AccountDetailTmp(String idCardType, String idCardNum, String insCode, String lastestRepayDate, String loanAccount, String filesIDs);
	
	int hasItem4CommentTmp(String idCardType, String idCardNum, String insCode, String commentTime, String filesIDs);
	
}
