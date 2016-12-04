package com.pactera.pds.u2.commerce.entity.mybatis;

import com.pactera.pds.u2.commerce.entity.IdEntity;
import com.pactera.pds.u2.commerce.utils.DESUtil;


/**
 * 信贷用户行为数据
 * 
 * @author foy
 *
 */
public class BCAccountDetailTmp extends IdEntity{
    
    private String idCardType;// 证件类型
    
    private String insCode;// 上传机构
    
    private String fullName;// 姓名
    
    private String loanAccount;// 账户代码
    
    private Double loanBalance1;// 当前余额1
    
    private Double loanBalance2;// 当前余额2
    
    private Double nextLoanreplyAmt1;// 下次还款金额1
    
    private Double nextLoanreplyAmt2;// 下次还款金额2
    
    private String nextLoanReplyDate;// 下次还款日期
    
    private String loanStatus1;// 当前状态
    
    private String idCardNum;// 证件号码
    
    private Long uploadFileId;// 上传文件id
    
    private String loanStatus2;// 当前状态
    
    private String comment1;
    private String comment2;
    
	private static DESUtil desutil;
	
	static void setDes(DESUtil des){
	    desutil=des;
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


	public String getLoanStatus1() {
		return loanStatus1;
	}


	public void setLoanStatus1(String loanStatus1) {
		this.loanStatus1 = loanStatus1;
	}


	public String getIdCardType() {
        return idCardType;
    }

    
    public String getInsCode() {
        return insCode;
    }

    
    public String getFullName() {
        return fullName;
    }

    
    public String getLoanAccount() {
        return loanAccount;
    }

    
    public Double getLoanBalance1() {
        return loanBalance1;
    }

    
    public Double getLoanBalance2() {
        return loanBalance2;
    }

    
    public Double getNextLoanreplyAmt1() {
        return nextLoanreplyAmt1;
    }

    
    public Double getNextLoanreplyAmt2() {
        return nextLoanreplyAmt2;
    }

    
    public String getNextLoanReplyDate() {
        return nextLoanReplyDate;
    }
    
    
    public String getIdCardNum() {
        return idCardNum;
    }
    
	public String getIdCardNumString() throws Exception {
        return  desutil.decrypt(idCardNum);
    }

    
    public Long getUploadFileId() {
        return uploadFileId;
    }

    
    public void setIdCardType(String idCardType) {
        this.idCardType = idCardType;
    }

    
    public void setInsCode(String insCode) {
        this.insCode = insCode;
    }

    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    
    public void setLoanAccount(String loanAccount) {
        this.loanAccount = loanAccount;
    }

    
    public void setLoanBalance1(Double loanBalance1) {
        this.loanBalance1 = loanBalance1;
    }

    
    public void setLoanBalance2(Double loanBalance2) {
        this.loanBalance2 = loanBalance2;
    }

    
    public void setNextLoanreplyAmt1(Double nextLoanreplyAmt1) {
        this.nextLoanreplyAmt1 = nextLoanreplyAmt1;
    }

    
    public void setNextLoanreplyAmt2(Double nextLoanreplyAmt2) {
        this.nextLoanreplyAmt2 = nextLoanreplyAmt2;
    }

    
    public void setNextLoanReplyDate(String nextLoanReplyDate) {
        this.nextLoanReplyDate = nextLoanReplyDate;
    }

    

    
    public String getLoanStatus2() {
		return loanStatus2;
	}


	public void setLoanStatus2(String loanStatus2) {
		this.loanStatus2 = loanStatus2;
	}


	public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }

    
    public void setUploadFileId(Long uploadFileId) {
        this.uploadFileId = uploadFileId;
    }
}
