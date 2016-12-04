package com.pactera.pds.u2.commerce.entity.mybatis;

import com.pactera.pds.u2.commerce.entity.IdEntity;

/**
 * 历史数据回溯
 * 
 * @author foy
 *
 */
public class BCHistoryRecordTmp extends IdEntity{
    
    private String idCardType;// 证件类型
    
    private String insCode;// 上传机构
    
    private String fullName;// 姓名
    
    private String loanAccount;// 账户代码
    
    private Double loanBalance1;// 当前余额1
    
    private Double loanBalance2;// 当前余额2
    
    private Double nextLoanreplyAmt1;// 下次还款金额1
    
    private Double nextLoanreplyAmt2;// 下次还款金额2
    
    private String nextLoanReplyDate;// 下次还款日期
    
    private String loanStatus;// 当前状态
    
    private String idCardNum;// 证件号码
    
    private Long uploadFileId;// 上传文件id
    
    private String applyDatetime;// 申请时间
    
    private Double applyAmount1;// 申请金额1
    
    private String applyType;// 申请类型
    
    private Double approvalAmount1;// 审批金额1
    
    private String loanStartDate;// 贷款期起
    
    private String loanEndDate;// 贷款截止
    
    private String applyProCity;// 申请省市
    
    private String homeAddr;// 家庭地址
    
    private String selPhoneNum;// 手机号码
    
    private String applyIp;// IP地址
    
    private Double applyAmount2;// 申请金额2
    
    private Double approvalAmount2;// 审批金额2

    
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

    
    public String getLoanStatus() {
        return loanStatus;
    }

    
    public String getIdCardNum() {
        return idCardNum;
    }

    
    public Long getUploadFileId() {
        return uploadFileId;
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

    
    public Double getApplyAmount2() {
        return applyAmount2;
    }

    
    public Double getApprovalAmount2() {
        return approvalAmount2;
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

    
    public void setLoanStatus(String loanStatus) {
        this.loanStatus = loanStatus;
    }

    
    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }

    
    public void setUploadFileId(Long uploadFileId) {
        this.uploadFileId = uploadFileId;
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

    
    public void setApplyAmount2(Double applyAmount2) {
        this.applyAmount2 = applyAmount2;
    }

    
    public void setApprovalAmount2(Double approvalAmount2) {
        this.approvalAmount2 = approvalAmount2;
    }
}
