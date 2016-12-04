
package com.pactera.pds.u2.commerce.entity;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.util.StringUtils;

import com.pactera.pds.u2.commerce.entity.mybatis.BCAccountDetailTmp;
import com.pactera.pds.u2.commerce.entity.mybatis.BCHistoryRecordTmp;
import com.pactera.pds.u2.commerce.utils.Sessions;

//JPA标识
@Entity
@Table(name = "bc_person_acc_detail")
public class PersonAccDetail  implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String insCode;//机构名称
	private String loanAccId;//到款账户id
	private Date updateDate;//更新日期
	private Double totalAllowedAmount;//总贷款金额
	private String loanBeginDate;//贷款开始日期
	private String loanEndDate;//贷款终止日期
	private Double loanBalance;//余额
	private Date nextLoanRepayDate;//下次还款日期
	private Double nextLoanRepayAmt;//下次还款数
	private String idCardType;//证件类型
	private String fullName;//全名
	private String idCardNum;//证件号
	private Double loanBalance1; //当前余额上限
	private String loanStatus;//贷款状态1
	private Double nextLoanRepayAmt1;//下期应还款上限
	private Double totalAllowedAmount1;//总贷款上限
	private String latest24monStatus;//近24个月状态
	private String loanStatus1;//贷款状态2
	private String comment1;
    private String comment2;
	
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
	private Long uploadFileId;
	public Long getUploadFileId() {
		return uploadFileId;
	}
	public void setUploadFileId(Long uploadFileId) {
		this.uploadFileId = uploadFileId;
	}
	public String getInsCode() {
		return insCode;
	}
	public String getInsCodeStr(){
	    return Sessions.maskInsCode(insCode);
	}
    public String getLoanBalanceStr(){
        return Sessions.combinInterval(loanBalance, loanBalance1);
    }
    public String getNextLoanRepayAmtStr(){
        return Sessions.combinInterval(nextLoanRepayAmt, nextLoanRepayAmt1);
    }
    public String getTotalAllowedAmountStr(){
        return Sessions.combinInterval(totalAllowedAmount, totalAllowedAmount1);
    }    
	public void setInsCode(String insCode) {
		this.insCode = insCode;
	}
	public String getLoanAccId() {
		return loanAccId;
	}
	public void setLoanAccId(String loanAccId) {
		this.loanAccId = loanAccId;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Double getTotalAllowedAmount() {
		return totalAllowedAmount;
	}
	public void setTotalAllowedAmount(Double totalAllowedAmount) {
		this.totalAllowedAmount = totalAllowedAmount;
	}
//	public Date getLoanBeginDate() {
//		return loanBeginDate;
//	}
//	public void setLoanBeginDate(Date loanBeginDate) {
//		this.loanBeginDate = loanBeginDate;
//	}
//	public Date getLoanEndDate() {
//		return loanEndDate;
//	}
//	public void setLoanEndDate(Date loanEndDate) {
//		this.loanEndDate = loanEndDate;
//	}
	public Double getLoanBalance() {
		return loanBalance;
	}

	public void setLoanBalance(Double loanBalance) {
		this.loanBalance = loanBalance;
	}
	public Date getNextLoanRepayDate() {
		return nextLoanRepayDate;
	}
	public void setNextLoanRepayDate(Date nextLoanRepayDate) {
		this.nextLoanRepayDate = nextLoanRepayDate;
	}
	public Double getNextLoanRepayAmt() {
		return nextLoanRepayAmt;
	}
	public void setNextLoanRepayAmt(Double nextLoanRepayAmt) {
		this.nextLoanRepayAmt = nextLoanRepayAmt;
	}
	//从行为记录导入数据
    public void fromDetailTmp(BCAccountDetailTmp tmp){
        this.idCardNum=tmp.getIdCardNum();
        this.insCode=tmp.getInsCode();
        this.loanAccId=tmp.getLoanAccount();
        //TODO 查找贷款期起和截止时间
        this.loanStatus=tmp.getLoanStatus1();
        this.nextLoanRepayAmt=tmp.getNextLoanreplyAmt1();
        //TODO 时间用字符串
       // this.nextLoanRepayDate=new Date(tmp.getNextLoanReplyDate());
        this.idCardType = tmp.getIdCardType();
        this.fullName = tmp.getFullName();
        this.loanBalance = tmp.getLoanBalance1();
        this.loanBalance1 = tmp.getLoanBalance2() == null
        		?tmp.getLoanBalance1()
        		:tmp.getLoanBalance2();
        this.nextLoanRepayDate = Sessions.convert2date(tmp.getNextLoanReplyDate());
//        DateFormat df = new SimpleDateFormat("yyyyMMdd");
//		try {
//			Date daystart = df.parse(tmp.getNextLoanReplyDate());
//			this.nextLoanRepayDate=daystart;
////			this.nextLoanRepayDate = tmp.getNextLoanReplyDate();
//		} catch (ParseException e) {
////			e.printStackTrace();
//		} 
		this.loanStatus1 = tmp.getLoanStatus2();
        this.updateDate = new Date();
        
        
        this.nextLoanRepayAmt1 = tmp.getNextLoanreplyAmt2() == null
        		?tmp.getNextLoanreplyAmt1():tmp.getNextLoanreplyAmt2();
        this.uploadFileId = tmp.getUploadFileId(); 
        this.comment1 = tmp.getComment1();
	    this.comment2 = tmp.getComment2();
    }
    //从历史回溯导入数据
    public void fromHistoryTmp(BCHistoryRecordTmp tmp){
        this.idCardNum=tmp.getIdCardNum();
        this.insCode=tmp.getInsCode();
        this.loanAccId=tmp.getLoanAccount();
        //TODO 查找贷款期起和截止时间
        this.loanStatus=tmp.getLoanStatus();
        this.nextLoanRepayAmt=tmp.getNextLoanreplyAmt1();///错误的
      //TODO 时间用字符串
        //this.nextLoanRepayDate=new Date(tmp.getNextLoanReplyDate());   
    }
    
    public String getIdCardType() {
        return idCardType;
    }
    
    public void setIdCardType(String idCardType) {
        this.idCardType = idCardType;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getIdCardNum() {
        return idCardNum;
    }
    
    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }
    
    public Double getLoanBalance1() {
        return loanBalance1;
    }
    
    
    public Double getNextLoanRepayAmt1() {
        return nextLoanRepayAmt1;
    }
    
    public Double getTotalAllowedAmount1() {
        return totalAllowedAmount1;
    }
    
    public void setLoanBalance1(Double loanBalance1) {
        this.loanBalance1 = loanBalance1;
    }
    
    
    public void setNextLoanRepayAmt1(Double nextLoanRepayAmt1) {
        this.nextLoanRepayAmt1 = nextLoanRepayAmt1;
    }
    
    public void setTotalAllowedAmount1(Double totalAllowedAmount1) {
        this.totalAllowedAmount1 = totalAllowedAmount1;
    }
    
    public String getLatest24monStatus() {
        return latest24monStatus;
    }
    
    public String getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	public String getLoanStatus1() {
		return loanStatus1;
	}
	public void setLoanStatus1(String loanStatus1) {
		this.loanStatus1 = loanStatus1;
	}
	public void setLatest24monStatus(String latest24monStatus) {
        this.latest24monStatus = latest24monStatus;
    }
	public String getLoanBeginDate() {
		return loanBeginDate;
	}
	public void setLoanBeginDate(String loanBeginDate) {
		this.loanBeginDate = loanBeginDate;
	}
	public String getLoanEndDate() {
		return loanEndDate;
	}
	public void setLoanEndDate(String loanEndDate) {
		this.loanEndDate = loanEndDate;
	}
	public Date getLoanBeginDate2() {
		if(StringUtils.isEmpty(loanBeginDate)){
			return null;
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date daystart = df.parse(loanBeginDate);
			return daystart;
		} catch (ParseException e) {
			e.printStackTrace();   
		} 
		return null;
	}
	public Date getLoanEndDate2() {
		if(StringUtils.isEmpty(loanEndDate)){
			return null;
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date daystart = df.parse(loanEndDate);
			return daystart;
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		return null;
	}
}
