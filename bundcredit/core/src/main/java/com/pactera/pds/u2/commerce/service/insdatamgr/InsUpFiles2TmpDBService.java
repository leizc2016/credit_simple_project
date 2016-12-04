package com.pactera.pds.u2.commerce.service.insdatamgr;

import java.io.FileInputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pactera.pds.u2.commerce.entity.InstitutionUploadedFile;
import com.pactera.pds.u2.commerce.repository.mybatis.BCInsDataUploadMybatisDao;
import com.pactera.pds.u2.commerce.repository.mybatis.InstitutionUploadedFileMybatisDao;


/**
 * 拆分机构上传数据到临时数据服务方法
 * 
 * @author foy
 *
 */
@Component("insUpFiles2TmpDBService")
@Transactional("bctmpTxManager")
public class InsUpFiles2TmpDBService {
    
    @Autowired
    private InstitutionUploadedFileService fs;
    
    @Autowired
    private BCInsDataUploadMybatisDao uploadTmpDao;
    
    @Autowired
    private InstitutionUploadedFileMybatisDao uploadDao;
    
    @Autowired
    private InsFileScanner inScanner;
    
    private void processImport(List batch, Integer type) {
        switch (type) {
        case 1: {
            uploadTmpDao.addApplyBatch(batch);
            break;
        }
        case 2: {
            uploadTmpDao.addDetailBatch(batch);
            break;
        }
        case 3: {
            uploadTmpDao.addHistoryBatch(batch);
            break;
        }
        case 4: {
            uploadTmpDao.addCommentBatch(batch);
            break;
        }
        }
    }
    
    public void executeValidateUploadedFileAndImportToTmpDB() {
    	// Validate Uploaded File
    	executeFileScanAndValidate();
    	// Import file records to Temp DB
    	executeFileScanAndImport();
    }
    
    //定时把机构上传的数据文件拆分导入临时表
    public void executeFileScanAndImport() {
        InstitutionUploadedFile f = fs.findOne(InstitutionUploadedFile.DATA_AUTO_VALIDATION_SUCCESS);
        if (f != null) {
            fs.updateFileStatus(InstitutionUploadedFile.DATA_INTO_TEMP_DB_PROCESSING, f.getId());
            try {
            	List batch = null;
            	if(f.getFileName().endsWith("txt")){
                     batch = inScanner.processImportData(new FileInputStream(f.me()), f.getFileType(), f.getInsCode(), f.getId());
            	} else if(f.getFileName().endsWith("xls") || f.getFileName().endsWith("xlsx")) {
            		batch = inScanner.processImportExcelData(f);
            	}
                processImport(batch, f.getFileType());
                fs.updateFileStatus(InstitutionUploadedFile.DATA_INTO_TEMP_DB_SUCCESS, f.getId());
            } catch (Exception e) {
                fs.updateFileStatus(InstitutionUploadedFile.DATA_INTO_TEMP_DB_FAILED, f.getId());
                e.printStackTrace();
                return;
            }
        }
    }
    
    public void executeFileScanAndValidate() {
        InstitutionUploadedFile f = fs.findOne(InstitutionUploadedFile.FILE_UPLOADED);
        if (f != null) {
            fs.updateFileStatus(InstitutionUploadedFile.DATA_AUTO_VALIDATION_PROCESSING, f.getId());
            try {
            	boolean result = false;
            	if(f.getFileName().endsWith("txt")){
            		result = inScanner.validateImportData(f, uploadDao, uploadTmpDao);
            	} else if(f.getFileName().endsWith("xls") || f.getFileName().endsWith("xlsx")) {
            		result = inScanner.validateImportExcelData(f, uploadDao, uploadTmpDao);
            	} else if(f.getFileName().endsWith("zip")){
            		result = inScanner.uncompressZipData(f);
            	}
                if(result){
                	if(f.getFileName().endsWith("zip")){
                		fs.updateFileStatus(InstitutionUploadedFile.UNZIP_ZIP_FILE_SUCCESS, f.getId());
                	} else{
                		fs.updateFileStatus(InstitutionUploadedFile.DATA_AUTO_VALIDATION_SUCCESS, f.getId());
                	}
                }else{
                	if(f.getFileName().endsWith("zip")){
                    	fs.updateFileStatus(InstitutionUploadedFile.UNZIP_ZIP_FILE_FAILED, f.getId());
                	} else{
                		fs.updateFileLog(f.getValidationLog(), f.getId());
                    	fs.updateFileStatus(InstitutionUploadedFile.DATA_AUTO_VALIDATION_FAILED, f.getId());
                	}
                	
                }
            } catch (Exception e) {
                fs.updateFileStatus(InstitutionUploadedFile.DATA_AUTO_VALIDATION_FAILED, f.getId());
                e.printStackTrace();
                return;
            }
        }
    }
    
    public InstitutionUploadedFileService getFs() {
        return fs;
    }
    
    public void setFs(InstitutionUploadedFileService fs) {
        this.fs = fs;
    }
    
    public BCInsDataUploadMybatisDao getApplyDetailTmpDao() {
        return uploadTmpDao;
    }
    
    public void setApplyDetailTmpDao(BCInsDataUploadMybatisDao uploadTmpDao) {
        this.uploadTmpDao = uploadTmpDao;
    }
    
    public InsFileScanner getInScanner() {
        return inScanner;
    }
    
    public void setInScanner(InsFileScanner inScanner) {
        this.inScanner = inScanner;
    }
}
