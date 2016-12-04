package com.pactera.pds.u2.commerce.entity;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.pactera.pds.u2.commerce.service.storage.FileService;
import com.pactera.pds.u2.commerce.utils.Base64Utils;


/**
 * 机构上传的用户申请数据、用户行为数据、历史回溯数据
 * 
 * @author foy
 *
 */
public class InstitutionUploadedFile {
    
    public static final Map<Integer,String>FILE_TYPE_MAP=new HashMap<Integer,String>(){{
        put(1, "申请记录");
        put(2, "行为信息");
        put(3, "更新信息");
        put(4, "备注信息");
    }};
    
    
    public static final Integer FILE_UPLOADED=100;
    
    public static final Integer DATA_AUTO_VALIDATION_PROCESSING=200;
    public static final Integer DATA_AUTO_VALIDATION_SUCCESS=201;
    public static final Integer DATA_AUTO_VALIDATION_FAILED=202;
    public static final Integer DATA_AUTO_VALIDATION_FAILED_COMFIRMED=203;
    public static final Integer DATA_INTO_TEMP_DB_PROCESSING=204;
    public static final Integer DATA_INTO_TEMP_DB_SUCCESS=205;
    public static final Integer DATA_INTO_TEMP_DB_FAILED=206;
    
    public static final Integer DATA_MANNUAL_VALIDATION_SUCCESS=300;
    public static final Integer DATA_MANNUAL_VALIDATION_FAILED=301;
    public static final Integer DATA_MANNUAL_VALIDATION_FAILED_COMFIRMED=302;
    
    public static final Integer DATA_INTO_PRODUCT_DB_SUCCESS=400;
    public static final Integer DATA_INTO_PRODUCT_DB_PROCESSING=401;
    public static final Integer DATA_INTO_PRODUCT_DB_FAILED=402;
    
    public static final Integer UNZIP_ZIP_FILE_SUCCESS=500;
    public static final Integer UNZIP_ZIP_FILE_FAILED=502;
    
    public static final Map<Integer,String> FILE_STATUS_MAP_ADMIN=new HashMap<Integer,String>(){{
        
        put(FILE_UPLOADED,"数据已上传，系统未审核");
        
        put(DATA_AUTO_VALIDATION_PROCESSING,"数据文件已上传，系统审核中");
        put(DATA_AUTO_VALIDATION_FAILED,"数据文件未通过系统审核,人工未处理");
        put(DATA_AUTO_VALIDATION_FAILED_COMFIRMED,"未通过系统审核,人工已处理");
        put(DATA_AUTO_VALIDATION_SUCCESS,"数据文件通过系统审核，待入临时库");
        put(DATA_INTO_TEMP_DB_PROCESSING,"数据录入临时库中");
        put(DATA_INTO_TEMP_DB_FAILED,"数据录入临时库失败");
        
        put(DATA_INTO_TEMP_DB_SUCCESS,"数据待估值");
        
        put(DATA_MANNUAL_VALIDATION_FAILED,"数据未通过人工审核，人工未处理");
        put(DATA_MANNUAL_VALIDATION_FAILED_COMFIRMED,"数据未通过人工审核，人工已处理");
        put(DATA_MANNUAL_VALIDATION_SUCCESS,"数据通过人工审核，待录入生产库");
        
        put(DATA_INTO_PRODUCT_DB_SUCCESS,"数据已录入生产库");
        put(DATA_INTO_PRODUCT_DB_PROCESSING,"数据录入生产库中");
        put(DATA_INTO_PRODUCT_DB_FAILED,"数据录入生产库失败");
        
        put(UNZIP_ZIP_FILE_SUCCESS,"数据解压成功");
        put(UNZIP_ZIP_FILE_FAILED,"数据解压失败");
        
    }};
    
    public static final Map<Integer,String> FILE_STATUS_MAP_SITE2=new HashMap<Integer,String>(){{
        
        put(FILE_UPLOADED,"上传成功，系统审核中");
        
        put(DATA_AUTO_VALIDATION_PROCESSING,"上传成功，系统审核中");
        put(DATA_AUTO_VALIDATION_FAILED,"系统审核失败，请联系客服");
        put(DATA_AUTO_VALIDATION_FAILED_COMFIRMED,"系统审核失败，请联系客服");
        put(DATA_AUTO_VALIDATION_SUCCESS,"上传成功，系统审核中");
        put(DATA_INTO_TEMP_DB_PROCESSING,"上传成功，系统审核中");
        put(DATA_INTO_TEMP_DB_FAILED,"系统审核失败，请联系客服");
        
        put(DATA_INTO_TEMP_DB_SUCCESS,"上传成功，系统审核中");
        
        put(DATA_MANNUAL_VALIDATION_FAILED,"系统审核失败，请联系客服");
        put(DATA_MANNUAL_VALIDATION_FAILED_COMFIRMED,"系统审核失败，请联系客服");
        put(DATA_MANNUAL_VALIDATION_SUCCESS,"系统审核成功");
        
        put(DATA_INTO_PRODUCT_DB_SUCCESS,"系统审核成功");
        put(DATA_INTO_PRODUCT_DB_PROCESSING,"系统审核成功");
        put(DATA_INTO_PRODUCT_DB_FAILED,"系统审核成功");
        
        put(UNZIP_ZIP_FILE_SUCCESS,"数据解压成功");
        put(UNZIP_ZIP_FILE_FAILED,"数据解压失败，请联系客服");
        
    }};
    
    
    
    
//    public static final Integer FILE_SAVED=100;
//    public static final Integer FILE_TYPE_ERROR=101;
//    public static final Integer AUTO_AUDIT_PROCESSING=102;
//    public static final Integer AUTO_VALIDATION_DONE=103;
//    public static final Integer AUTO_AUDIT_DONE=200;
//    public static final Integer PENDING_MANUAL_AUDIT=300;
//    public static final Integer AUDIT_DONE=400;
//    public static final Integer AUDIT_FAILUE=500;
//    public static final Integer REJECTED_AUTO_AUDIT=501;
//    
//    
//    public static final Map<Integer,String> FILE_STATUS_MAP=new HashMap<Integer,String>(){{
//        
//        put(100,"数据已上传，系统未审核");
//        put(101,"未通过自动检查");
//        put(102,"自动处理中...");
//        put(103,"自动处理中...");
//        put(200,"数据通过系统自动检查，人工未审核");
//        put(300,"数据通过系统自动检查，人工未审核");
//        put(301,"人工审核不通过");
//        put(400,"数据已入生产库");
//        put(500,"数据未通过人工审核");
//        put(501,"未通过自动检查");
//        
//    }};
//    
//    public static final Map<Integer,String> FILE_STATUS_MAP_SITE=new HashMap<Integer,String>(){{
//        
//        put(100,"数据上传成功，审核中");
//        put(101,"数据未通过审核，请******");
//        put(102,"数据上传成功，审核中");
//        put(103,"数据上传成功，审核中");
//        put(200,"数据上传成功，审核中");
//        put(300,"数据上传成功，审核中");
//        put(400,"数据审核完毕");
//        put(500,"数据未通过审核，请******");
//        put(501,"数据未通过审核，请******");
//        
//    }};
   
    
    private static Logger logger = LoggerFactory.getLogger(InstitutionUploadedFile.class);
    
    // 文件id
    private long id;
    
    // 上传时间
    private Timestamp uploadDatetime;
    
    // 机构代码
    private String insCode;
    
    // 上传者的账户id
    private long insAccId;
    
    // 原始文件名
    private String fileName;
    
    // 文件唯一编码
    private String fileUqKey;
    
    // 文件估算值
    private int valAmt;
    
    // 文件类型：申请记录，行为记录，历史回溯记录，非结构化数据
    private Integer fileType;
    
    // 文件当前状态：提交，导入临时库中，等待估值，完成
    private Integer fileStatus;
    
    // spring 进来的文件
    private MultipartFile multipartFile;
    
    // 文件保存接口
    private static FileService fileService;
    
    //日志
    private String validationLog;
    
    //状态
    private int isEnter;
    
    //上传人姓名
    private String uploadman;
   
    public String getUploadman() {
		return uploadman;
	}


	public void setUploadman(String uploadman) {
		this.uploadman = uploadman;
	}


	public int getIsEnter() {
        return isEnter;
    }

    
    public void setIsEnter(int isEnter) {
        this.isEnter = isEnter;
    }

    public String getValidationLog() {
		return validationLog;
	}

	public void setValidationLog(String validationLog) {
		this.validationLog = validationLog;
	}

	static void setFileService(FileService fs) {
        Assert.notNull(fs, "文件服务不能为空！");
        fileService = fs;
    }
    
    public static InstitutionUploadedFile fromMultipartFile(MultipartFile mf) {
        InstitutionUploadedFile insFile = new InstitutionUploadedFile();
        insFile.multipartFile = mf;
        insFile.fileName=mf.getOriginalFilename();
        insFile.uploadDatetime=new Timestamp(System.currentTimeMillis());
        return insFile;
    }
    
    /*
     * 保存本文件
     */
    public boolean save() {
        if (StringUtils.isEmpty(fileUqKey)) {
            genFileUqKey();
        }
        try {
            fileService.saveFile(fileUqKey, fileName, multipartFile);
        } catch (IOException e) {
            if (logger.isErrorEnabled()) {
                logger.error("保存文件失败.", "文件名:" + fileName, e);
            }
            return false;
        }
        return true;
    }

    public File me() {
        return fileService.getFile(fileUqKey, fileName);
    }
    
    private void genFileUqKey() {
        StringBuffer sb = new StringBuffer();
        sb.append(insCode).append("_").append(insAccId).append("_").append(uploadDatetime.toString()).append("_").append(fileType)
            .append("_").append(fileName);
        fileUqKey = Base64Utils.encodeBase64(sb.toString());
    }
    
    public long getId() {
        return id;
    }
    
    public Timestamp getUploadDatetime() {
        return uploadDatetime;
    }
    
    public String getInsCode() {
        return insCode;
    }
    
    public long getInsAccId() {
        return insAccId;
    }
    
    public String getFileName() {
        return fileName;
    }
    
    public String getFileUqKey() {
        return fileUqKey;
    }
    
    public int getValAmt() {
        return valAmt;
    }
    
    public Integer getFileType() {
        return fileType;
    }
    
    public Integer getFileStatus() {
        return fileStatus;
    }
    public String getFileStatusStr(){
        String status=FILE_STATUS_MAP_ADMIN.get(fileStatus);
        if(StringUtils.isEmpty(status)){
            status=""+fileStatus;
        }
        return status;
    }
    
    public String getFileStatusStr4Site(){
        String status=FILE_STATUS_MAP_SITE2.get(fileStatus);
        if(StringUtils.isEmpty(status)){
            status=""+fileStatus;
        }
        return status;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public void setInsCode(String insCode) {
        this.insCode = insCode;
    }
    
    public void setInsAccId(long insAccId) {
        this.insAccId = insAccId;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public void setValAmt(int valAmt) {
        this.valAmt = valAmt;
    }
    
    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }
    public String getFileTypeStr(){
        String fileTypeStr=FILE_TYPE_MAP.get(fileType);
        if(StringUtils.isEmpty(fileTypeStr)){
            fileTypeStr=""+fileType;
        }
        return fileTypeStr;
    }
    public void setFileStatus(Integer fileStatus) {
        this.fileStatus = fileStatus;
    }
}
