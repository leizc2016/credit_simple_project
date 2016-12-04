package com.pactera.pds.u2.commerce.entity.mybatis;

import com.pactera.pds.u2.commerce.entity.IdEntity;
import com.pactera.pds.u2.commerce.utils.DESUtil;


/**
 * 
 * 备注记录
 */
public class BCCommentTmp extends IdEntity{
	
	private String idCardType;// 证件类型
    
    private String idCardNum;// 证件号码
    
    private String fullName;// 姓名
    
	private String insCode;// 上传机构
    
    private Long uploadFileId;// 上传文件id
    
    private String commentType; //备注类型  
    
    private String commentContent; //备注内容
    
    private String commentTime; //备注时间
    
    
	private static DESUtil desutil;
	
	static void setDes(DESUtil des){
	    desutil=des;
	}
    
    public String getIdCardType() {
		return idCardType;
	}

	public void setIdCardType(String idCardType) {
		this.idCardType = idCardType;
	}

	public String getIdCardNum() {
		return idCardNum;
	}
	
	public String getIdCardNumString() throws Exception {
        return  desutil.decrypt(idCardNum);
    }

	public void setIdCardNum(String idCardNum) {
		this.idCardNum = idCardNum;
	}
	
    public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getInsCode() {
		return insCode;
	}

	public void setInsCode(String insCode) {
		this.insCode = insCode;
	}

	public Long getUploadFileId() {
		return uploadFileId;
	}

	public void setUploadFileId(Long uploadFileId) {
		this.uploadFileId = uploadFileId;
	}

	public String getCommentType() {
		return commentType;
	}

	public void setCommentType(String commentType) {
		this.commentType = commentType;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public String getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
	}


    
    
}
