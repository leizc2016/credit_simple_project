package com.pactera.pds.u2.commerce.entity.mybatis;

import com.pactera.pds.u2.commerce.entity.IdEntity;
import com.pactera.pds.u2.commerce.utils.DESUtil;


/**
 * 
 * 信贷申请记录
 * 
 * @author foy
 *
 */

public class BCApplyDetailTmp extends IdEntity{
    
    private String idCardType;// 证件类型
    
    private String idCardNum;// 证件号码
    
    private String fullName;// 姓名
    
    private String applyDatetime;// 申请时间
    
    private Double applyAmount1;// 申请金额1
    
    private String applyType;// 申请类型
    
    private Double approvalAmount1;// 审批金额1
    
    private String loanAccount;// 账户代码
    
    private String loanStartDate;// 贷款期起
    
    private String loanEndDate;// 贷款截止
    
    private String applyProCity;// 申请省市
    
    private String homeAddr;// 家庭地址
    
    private String selPhoneNum;// 手机号码
    
    private String applyIp;// IP地址
    
    private String insCode;// 上传机构
    
    private Long uploadFileId;// 上传文件id
    
    private Double applyAmount2;// 申请金额2
    
    private Double approvalAmount2;// 审批金额2
  
	private String assType;// 关联类型
    
    private String assIDNum;// 关联人或企业证件号码
    
    private String assIDType;// 关联人或企业证件类型
    
    private String assName;// 关联个人姓名/关联企业名称
    
    private String comment1;
    
    private String comment2;
    
	private static DESUtil desutil;
	
	static void setDes(DESUtil des){
	    desutil=des;
	}

    
	public String getIdCardNumString() {
		
        try {
			return  desutil.decrypt(idCardNum);
		} catch (Exception e) {
			 return null;
		}
    }
	
    public String getComment1() {
		return comment1;
	}


	public void setComment1(String comment1) {
		this.comment1 = comment1;
	}


	public String getComment2() {
		return comment2;
	}


	public void setComment2(String comment2) {
		this.comment2 = comment2;
	}


	public String getIdCardType() {
        return idCardType;
    }

    
    public String getIdCardNum() {
        return idCardNum;
    }
    
    public String getFullName() {
        return fullName;
    }

    
    public String getApplyDatetime() {
        return applyDatetime;
    }

    
    public Double getApplyAmount1() {
        return applyAmount1;
    }

    
    public String getApplyType() {
        return applyType;
    }

    
    public Double getApprovalAmount1() {
        return approvalAmount1;
    }

    
    public String getLoanAccount() {
        return loanAccount;
    }

    
    public String getLoanStartDate() {
        return loanStartDate;
    }

    
    public String getLoanEndDate() {
        return loanEndDate;
    }

    
    public String getApplyProCity() {
        return applyProCity;
    }

    
    public String getHomeAddr() {
        return homeAddr;
    }

    
    public String getSelPhoneNum() {
        return selPhoneNum;
    }

    
    public String getApplyIp() {
        return applyIp;
    }

    
    public String getInsCode() {
        return insCode;
    }

    
    public Long getUploadFileId() {
        return uploadFileId;
    }

    
    public Double getApplyAmount2() {
        return applyAmount2;
    }

    
    public Double getApprovalAmount2() {
        return approvalAmount2;
    }

    
    public void setIdCardType(String idCardType) {
        this.idCardType = idCardType;
    }

    
    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }

    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    
    public void setApplyDatetime(String applyDatetime) {
        this.applyDatetime = applyDatetime;
    }

    
    public void setApplyAmount1(Double applyAmount1) {
        this.applyAmount1 = applyAmount1;
    }

    
    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    
    public void setApprovalAmount1(Double approvalAmount1) {
        this.approvalAmount1 = approvalAmount1;
    }

    
    public void setLoanAccount(String loanAccount) {
        this.loanAccount = loanAccount;
    }

    
    public void setLoanStartDate(String loanStartDate) {
        this.loanStartDate = loanStartDate;
    }

    
    public void setLoanEndDate(String loanEndDate) {
        this.loanEndDate = loanEndDate;
    }

    
    public void setApplyProCity(String applyProCity) {
        this.applyProCity = applyProCity;
    }

    
    public void setHomeAddr(String homeAddr) {
        this.homeAddr = homeAddr;
    }

    
    public void setSelPhoneNum(String selPhoneNum) {
        this.selPhoneNum = selPhoneNum;
    }

    
    public void setApplyIp(String applyIp) {
        this.applyIp = applyIp;
    }

    
    public void setInsCode(String insCode) {
        this.insCode = insCode;
    }

    
    public void setUploadFileId(Long uploadFileId) {
        this.uploadFileId = uploadFileId;
    }

    
    public void setApplyAmount2(Double applyAmount2) {
        this.applyAmount2 = applyAmount2;
    }

    
    public void setApprovalAmount2(Double approvalAmount2) {
        this.approvalAmount2 = approvalAmount2;
    }
    
    public String getAssType() {
  		return assType;
  	}


  	public void setAssType(String assType) {
  		this.assType = assType;
  	}


  	public String getAssIDNum() {
  		return assIDNum;
  	}


  	public void setAssIDNum(String assIDNum) {
  		this.assIDNum = assIDNum;
  	}


  	public String getAssIDType() {
  		return assIDType;
  	}


  	public void setAssIDType(String assIDType) {
  		this.assIDType = assIDType;
  	}


  	public String getAssName() {
  		return assName;
  	}


  	public void setAssName(String assName) {
  		this.assName = assName;
  	}
    
    
}
