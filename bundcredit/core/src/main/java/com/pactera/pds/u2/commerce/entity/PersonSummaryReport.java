package com.pactera.pds.u2.commerce.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.pactera.pds.u2.commerce.utils.Sessions;

public class PersonSummaryReport implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCardNum;//证件号
	private Integer loanCnt;//总贷款账户数
	private Integer openLoanCnt;//未结清账户数
	private Double openLoanTotalAmount;//当前总余额
	private Double openLoanTotalAmount1;//当前总余额1
	private Double nextLoanRepayAmount;//下期应还款总额
	private Double nextLoanRepayAmount1;//下期应还款总额1
	private Integer overdueLoanAccCnt;//发生过逾期的贷款账户数
	private Integer overdue90LoanAccCnt;//发生过90天以上逾期的贷款账户数
	private Integer in90LoanApplyCnt;//90天内申请次数
	
	public Integer getLoanCnt() {
		return loanCnt;
	}
	public void setLoanCnt(Integer loanCnt) {
		this.loanCnt = loanCnt;
	}
	public Integer getOpenLoanCnt() {
		return openLoanCnt;
	}
	public void setOpenLoanCnt(Integer openLoanCnt) {
		this.openLoanCnt = openLoanCnt;
	}
    public String getOpenLoanTotalAmountStr(){
        return Sessions.combinInterval(openLoanTotalAmount, openLoanTotalAmount1);
    }	
    public String getNextLoanRepayAmountStr(){
        return Sessions.combinInterval(nextLoanRepayAmount, nextLoanRepayAmount1);
    }   	
	public Integer getOverdueLoanAccCnt() {
		return overdueLoanAccCnt;
	}
	public void setOverdueLoanAccCnt(Integer overdueLoanAccCnt) {
		this.overdueLoanAccCnt = overdueLoanAccCnt;
	}
	public Integer getOverdue90LoanAccCnt() {
		return overdue90LoanAccCnt;
	}
	public void setOverdue90LoanAccCnt(Integer overdue90LoanAccCnt) {
		this.overdue90LoanAccCnt = overdue90LoanAccCnt;
	}
	public Integer getIn90LoanApplyCnt() {
		return in90LoanApplyCnt;
	}
	public void setIn90LoanApplyCnt(Integer in90LoanApplyCnt) {
		this.in90LoanApplyCnt = in90LoanApplyCnt;
	}
	public Long getIdCardNum() {
		return idCardNum;
	}
	public void setIdCardNum(Long idCardNum) {
		this.idCardNum = idCardNum;
	}
	public Double getOpenLoanTotalAmount() {
		return openLoanTotalAmount;
	}
	public void setOpenLoanTotalAmount(Double openLoanTotalAmount) {
		this.openLoanTotalAmount = openLoanTotalAmount;
	}
	public Double getOpenLoanTotalAmount1() {
		return openLoanTotalAmount1;
	}
	public void setOpenLoanTotalAmount1(Double openLoanTotalAmount1) {
		this.openLoanTotalAmount1 = openLoanTotalAmount1;
	}
	public Double getNextLoanRepayAmount() {
		return nextLoanRepayAmount;
	}
	public void setNextLoanRepayAmount(Double nextLoanRepayAmount) {
		this.nextLoanRepayAmount = nextLoanRepayAmount;
	}
	public Double getNextLoanRepayAmount1() {
		return nextLoanRepayAmount1;
	}
	public void setNextLoanRepayAmount1(Double nextLoanRepayAmount1) {
		this.nextLoanRepayAmount1 = nextLoanRepayAmount1;
	}
    
	
}
