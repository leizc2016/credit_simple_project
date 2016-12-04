package com.pactera.pds.u2.commerce.service.insdatamgr;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.pactera.pds.u2.commerce.entity.InstitutionUploadedFile;
import com.pactera.pds.u2.commerce.entity.PersonAccDetail;
import com.pactera.pds.u2.commerce.entity.PersonApplyDetail;
import com.pactera.pds.u2.commerce.entity.PersonBasicInfo;
import com.pactera.pds.u2.commerce.entity.PersonComment;
import com.pactera.pds.u2.commerce.entity.mybatis.BCAccountDetailTmp;
import com.pactera.pds.u2.commerce.entity.mybatis.BCApplyDetailTmp;
import com.pactera.pds.u2.commerce.entity.mybatis.BCCommentTmp;
import com.pactera.pds.u2.commerce.entity.mybatis.BCHistoryRecordTmp;
import com.pactera.pds.u2.commerce.entity.mybatis.BCUser;
import com.pactera.pds.u2.commerce.repository.mybatis.BCInsDataUploadMybatisDao;
import com.pactera.pds.u2.commerce.repository.mybatis.CreditSearchMybatisDao;
import com.pactera.pds.u2.commerce.repository.mybatis.InstitutionUploadedFileMybatisDao;
import com.pactera.pds.u2.commerce.utils.MockMultipartFile;
import com.pactera.pds.u2.commerce.utils.Sessions;


@Component
@Transactional
public class InstitutionUploadedFileService {
    
    @Autowired
    private InstitutionUploadedFileMybatisDao uploadDao;
    
    @Autowired
    private BCInsDataUploadMybatisDao insUploadDao;

  /*  @Autowired
    private InsFileScanner inScanner;
    */
    @Autowired
    private BCInsDataUploadMybatisDao applyDetailTmpDao;
    
    @Autowired
    private CreditSearchMybatisDao creditSearchMybatisDao;
    
    public InstitutionUploadedFile getById(Long fileId) {
        return uploadDao.get(fileId);
    }
    
    public List<InstitutionUploadedFile> findByInsCode(PageBounds pageBound) {
        return uploadDao.findByInsCode4page(Sessions.insCode(), pageBound);
    }
    
    public List<InstitutionUploadedFile> findAll() {
        return findAll(new PageBounds());
    }
    
    public List<InstitutionUploadedFile> findAll(PageBounds pb) {
        return uploadDao.findAll(pb);
    }
    public List<InstitutionUploadedFile> findCondition(Map<String, Object> file,PageBounds pb) {
        return uploadDao.findCondition(file,pb);
    }
    
    public List<InstitutionUploadedFile> findByInsCode(String insCode) {
        return uploadDao.findByInsCode(insCode);
    }
    
	// 导入数据到生产库
	public void import2Prod(Long fileId) {
		List Data = fileDataByFileId(fileId, new PageBounds());
		Integer fileType = getById(fileId).getFileType();
		for (Object obj : Data) {
			switch (fileType) {
			case 1: {
				// 申请数据导入
				BCApplyDetailTmp applyTmp = (BCApplyDetailTmp) obj;
				PersonBasicInfo b = new PersonBasicInfo();
				b.fromApplyTmp(applyTmp);
				PersonApplyDetail p = new PersonApplyDetail();
				p.fromApplyTmp(applyTmp);
				saveOrUpdateBasicInfo(b);
				saveOrUpdateApplyDetail(p);
				break;
			}
			case 2: {
				// 行为数据导入
				BCAccountDetailTmp detailTmp = (BCAccountDetailTmp) obj;
				PersonAccDetail p = new PersonAccDetail();
				p.fromDetailTmp(detailTmp);
				PersonBasicInfo b = new PersonBasicInfo();
				b.fromDetailTmp(detailTmp);
				saveOrUpdateBasicInfo(b);
				saveOrUpdateAccDetail(p);
				break;
			}
			case 3: {
				// 历史数据回溯导入
				BCHistoryRecordTmp historyTmp = (BCHistoryRecordTmp) obj;
				PersonBasicInfo basic = new PersonBasicInfo();
				basic.fromHistoryTmp(historyTmp);
				PersonApplyDetail apply = new PersonApplyDetail();
				apply.fromHistoryTmp(historyTmp);
				PersonAccDetail detail = new PersonAccDetail();
				detail.fromHistoryTmp(historyTmp);
				saveOrUpdateBasicInfo(basic);
				saveOrUpdateApplyDetail(apply);
				saveOrUpdateAccDetail(detail);
				break;
			}
			case 4: {
				// 备注信息导入
				BCCommentTmp commentTmp = (BCCommentTmp) obj;
				PersonComment personComment = new PersonComment();
				personComment.fromCommentTmp(commentTmp);
				PersonBasicInfo basicInfo = new PersonBasicInfo();
				basicInfo.fromCommentTmp(commentTmp);
				saveOrUpdatePersonComment(personComment);
				saveOrUpdateBasicInfo(basicInfo);
				break;
			}
			}
		}
	}
    
	private void saveOrUpdatePersonComment(PersonComment personComment) {
        creditSearchMybatisDao.savePersonComment(personComment);
    }
    
    private String append24MonStatus(String newStatus, String StatusIn24Mon){
//    	if(StatusIn24Mon == null){
//    		StatusIn24Mon = "////////////////////////";//24个0
////    		StatusIn24Mon = "000000000000000000000000";//24个0
//    	}                  //6//000000000000000000000
    	StringBuffer sb = new  StringBuffer(newStatus);
    	sb.append(StatusIn24Mon);
    	String last24MonStatus = sb.substring(0, 24);
    	return last24MonStatus;
    }
    
    private String update24MonStatus(String newStatus, String StatusIn24Mon){
    	StringBuffer sb = new  StringBuffer(StatusIn24Mon);
    	String last24MonStatus =  sb.replace(0, 1, newStatus).toString();
    	return last24MonStatus;
    }
    
//    private int monthCount(Date uploadDate, Date lastPaybackDate){
//    	
//    }
    
    public  int getMonthBetweenDate(Date lastUploadDate,Date uploadDate){
    	  int months=-1;//相差月份
    	  Calendar c1 = Calendar.getInstance();
    	  c1.setTime(lastUploadDate);
    	  Calendar c2 = Calendar.getInstance();
    	  c2.setTime(uploadDate);
    	  int y1=c1.get(Calendar.YEAR);
    	  int y2=c2.get(Calendar.YEAR);
    	  int m1=c1.get(Calendar.MONTH);//起始日期月份
    	  int m2=c2.get(Calendar.MONTH);;//结束日期月份
    	  if(lastUploadDate.getTime()<=uploadDate.getTime()){
    		 months=m2-m1+(y2-y1)*12;
    	  }
    	  return months;
    	 }

    
//    public static void main(String args[]){
//    	SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
//    	Date d1 = null;
//    	Date d2 = null;
//    	  try {
//    	   d1 = sd.parse("2000-12-02");
//    	   d2 = sd.parse("2000-12-02");
//    	  } catch (ParseException e) {
//    	   e.printStackTrace();
//    	  }
//    	int count = getMonthBetweenDate(d1, d2);
//    	System.out.print(count);
//    }
    
    private void saveOrUpdateAccDetail(PersonAccDetail detail) {
    	PersonAccDetail accInDB = creditSearchMybatisDao.getPersonAccDetail(detail);
    	String statusIn24Month = getStatusIn24Month(detail, accInDB);
    	if(statusIn24Month == null) return;
    	if(accInDB == null){
    		detail.setLatest24monStatus(statusIn24Month);
    		creditSearchMybatisDao.saveAccDetailInfo(detail);
    	}else{
    		
    		accInDB.setLatest24monStatus(statusIn24Month);
    		accInDB.setNextLoanRepayDate(detail.getNextLoanRepayDate());
    		accInDB.setLoanStatus(detail.getLoanStatus());
    		accInDB.setLoanStatus1(detail.getLoanStatus1());
    		accInDB.setComment1(detail.getComment1());
    		accInDB.setLoanBalance(detail.getLoanBalance());
    		accInDB.setLoanBalance1(detail.getLoanBalance1());
    		accInDB.setNextLoanRepayAmt(detail.getNextLoanRepayAmt());
    		accInDB.setNextLoanRepayAmt1(detail.getNextLoanRepayAmt1());
    		accInDB.setComment2(detail.getComment2());
    		accInDB.setUpdateDate(detail.getUpdateDate());
    		accInDB.setUploadFileId(detail.getUploadFileId());
    		creditSearchMybatisDao.updateAccDetailInfo(accInDB);
    	}
    }

    private String getStatusIn24Month(PersonAccDetail detail,
			PersonAccDetail accInDB) {
    	Date lastUploadDate = null;
    	String statusIn24Month = null;
    	Date uploadDate = getById(detail.getUploadFileId()).getUploadDatetime();
    	int months = 0;
    	if(accInDB != null){
    		lastUploadDate = getById(accInDB.getUploadFileId()).getUploadDatetime();
    		statusIn24Month = accInDB.getLatest24monStatus();
    		months = getMonthBetweenDate(lastUploadDate, uploadDate);
    	}else{
    		statusIn24Month = "////////////////////////";//24个0
    	}
		String newStatus = detail.getLoanStatus();
//		String latest24monStatus;
		if(months==0){
			statusIn24Month = update24MonStatus(newStatus, statusIn24Month);
		}else if(months == 1){
			statusIn24Month = append24MonStatus(newStatus, statusIn24Month);
		}else if(months > 1){
			newStatus = appendXieLine(newStatus, months);
			statusIn24Month = append24MonStatus(newStatus, statusIn24Month);
		}else{
			return null;
			//上传日期 小于 最后还款日期不被处理
		}
		return statusIn24Month;
	}

	private String appendXieLine(String newStatus, int months) {
		for(int i=1; i<months; i++){
			newStatus += "/";
		}
		return newStatus;
	}

	private void saveOrUpdateApplyDetail(PersonApplyDetail p) {
        creditSearchMybatisDao.saveApplyDetailInfo(p);
    }

    private void saveOrUpdateBasicInfo(PersonBasicInfo b) {
        if((""+b.getIdCardNum2()).isEmpty()){
            return;
        }
        if(null == creditSearchMybatisDao.getPersonBasicInfo(b.getIdCardNum2())){
        	b.setLastUpdateDatetime(new Date());
            creditSearchMybatisDao.savePersonBasicInfo(b);
        }else{
        	creditSearchMybatisDao.updatePersonBasicInfo(b);
        }
    }

    public List fileDataByFileId(Long fileId, PageBounds pg) {
        Integer fileType = getById(fileId).getFileType();
        switch (fileType) {
        case 1:
            return insUploadDao.findApplyRecByFileId(fileId, pg);
        case 2:
            return insUploadDao.findDetailRecByFileId(fileId, pg);
        case 3:
            return insUploadDao.findHistoryRecByFileId(fileId, pg);
        case 4:
            return insUploadDao.findCommentByFileId(fileId, pg);
        }
        return null;
    }
    
    
    /**
     * Convert File to MultipartFile
     * 
     * @param fileList File list
     * @return MultipartFile list
     */
    public List<MultipartFile> convertFileToMultipartFile(List<String> fileList) {
    	 List<MultipartFile> mFileList = new ArrayList<MultipartFile>();
    		
    		for(String fileStr: fileList){
    			File file = new File(fileStr);
    			Path path = Paths.get(fileStr);
    			String name = file.getName();
    			String originalFileName = file.getName();
    			String contentType = "text/plain";
    			byte[] content = null;
    			
    			try {
    			    content = Files.readAllBytes(path);
    			} catch (final IOException e) {
    			}
    			
    			MultipartFile mFile = new MockMultipartFile(name,                                                                                                                                                                                                                                                                                                                 
    	                originalFileName, contentType, content);
    			
    			mFileList.add(mFile);
    		}
    		
    		return mFileList;
    	
    }
    
    /**
     * Convert File to MultipartFile
     * 
     * @param fileList File list
     * @return MultipartFile list
     */
    public MultipartFile convertFileToMultipartFile(String filepath) {
    	 
    		
    		
    			File file = new File(filepath);
    			Path path = Paths.get(filepath);
    			String name = file.getName();
    			String originalFileName = file.getName();
    			String contentType = "text/plain";
    			byte[] content = null;
    			
    			try {
    			    content = Files.readAllBytes(path);
    			} catch (final IOException e) {
    			}
    			
    			MultipartFile mFile = new MockMultipartFile(name,                                                                                                                                                                                                                                                                                                                 
    	                originalFileName, contentType, content);
    			
    		
    		return mFile;
    	
    }
   
    
    public boolean saveUploadMetaFiles(Integer fileType, List<MultipartFile> attachments) {
        boolean result = true;
        for (MultipartFile mf : attachments) {
        	int fileStatus = InstitutionUploadedFile.FILE_UPLOADED;//100;
        	  InstitutionUploadedFile insFile = InstitutionUploadedFile.fromMultipartFile(mf);
//        	 if(4 == fileType){//备注信息不需要审核
//             	/*try {
//     				List batch = inScanner.processImportData(new FileInputStream(insFile.me()), insFile.getFileType(), insFile.getInsCode(), insFile.getId());
//     				applyDetailTmpDao.addBundBatch(batch);
//                 } catch (FileNotFoundException e) {
//     				// TODO Auto-generated catch block
//     				e.printStackTrace();
//     			}   */   
//         		fileStatus = InstitutionUploadedFile.DATA_INTO_PRODUCT_DB_SUCCESS;//400;
//             }
          
            insFile.setInsAccId(Sessions.id());
            insFile.setInsCode(Sessions.insCode());
            insFile.setFileType(fileType);
            insFile.setFileStatus(fileStatus);
           
                
            if (insFile.save()) {
                uploadDao.save(insFile);
            }
        }
        return result;
    }
    
	/**
	 * Save files to local driver and save related records to table.
	 * 
	 * @param file InstitutionUploadedFile
	 * @param attachments MultipartFile list
	 * @return true: Save success, false: Save failed.
	 */
	public boolean saveZipFiles(InstitutionUploadedFile file,
			List<MultipartFile> attachments) {
		boolean result = true;
		for (MultipartFile mf : attachments) {
			int fileStatus = InstitutionUploadedFile.FILE_UPLOADED;// 100;
			InstitutionUploadedFile insFile = InstitutionUploadedFile.fromMultipartFile(mf);

			insFile.setFileName(file.getFileName() + "-" + mf.getName());
			insFile.setInsAccId(file.getInsAccId());
			insFile.setInsCode(file.getInsCode());
			insFile.setFileType(file.getFileType());
			insFile.setFileStatus(fileStatus);

			if (insFile.save()) {
				uploadDao.save(insFile);
			}
		}
		return result;
	}
	
	public boolean saveJsonFile(MultipartFile mFile, BCUser user, String fileType) {
		boolean result = true;

		int fileStatus = InstitutionUploadedFile.FILE_UPLOADED;// 100;
		InstitutionUploadedFile insFile = InstitutionUploadedFile.fromMultipartFile(mFile);

		insFile.setFileName(mFile.getOriginalFilename());
		insFile.setInsAccId(user.getId());
		insFile.setInsCode(user.getInsCode());
		insFile.setFileType(Integer.parseInt(fileType));
		insFile.setFileStatus(fileStatus);

		if (insFile.save()) {
			uploadDao.save(insFile);
		}
		return result;
	}
    
    public void saveInsfile(InstitutionUploadedFile insFile){
        
        uploadDao.updateFileState(insFile.getIsEnter(),insFile.getId());
    }
    public InstitutionUploadedFile findOne() {
        while (true) {
            InstitutionUploadedFile f = uploadDao.findByUploadTimeAsc(InstitutionUploadedFile.FILE_UPLOADED);
            if (f == null) return null;
            if (ensureFileOK(f)) {
                return f;
            }
        }
    }
    
    public InstitutionUploadedFile findOne(int fileStatus) {
        while (true) {
            InstitutionUploadedFile f = uploadDao.findByUploadTimeAsc(fileStatus);
            if (f == null) return null;
//            if (ensureFileOK(f)) {
//                return f;
//            }
            return f;
        }
    }
    
    public void updateFileStatus(Integer fileStatus, Long id) {
        uploadDao.updateFileStatus(fileStatus, id);
    }
    
    public void updateFileLog(String log, Long id) {
        uploadDao.updateFileLog(log, id);
    }
    
    public void updateValue(Long fileId, Float value) {
        uploadDao.updateFileValue(fileId, value);
    }
    
    private boolean ensureFileOK(InstitutionUploadedFile f) {
        if (null == f) {
            return false;
        }
        String fileName = f.getFileName();
        if (!StringUtils.isEmpty(fileName)) {
            if (fileName.endsWith(".txt")) {
                return true;
            } else {
                uploadDao.updateFileStatus(InstitutionUploadedFile.DATA_AUTO_VALIDATION_FAILED, f.getId());
            }
        }
        return false;
    }
    
    public InstitutionUploadedFileMybatisDao getUploadDao() {
        return uploadDao;
    }
    
    public void setUploadDao(InstitutionUploadedFileMybatisDao uploadDao) {
        this.uploadDao = uploadDao;
    }

    
    public CreditSearchMybatisDao getCreditSearchMybatisDao() {
        return creditSearchMybatisDao;
    }

    
    public void setCreditSearchMybatisDao(CreditSearchMybatisDao creditSearchMybatisDao) {
        this.creditSearchMybatisDao = creditSearchMybatisDao;
    }
}
