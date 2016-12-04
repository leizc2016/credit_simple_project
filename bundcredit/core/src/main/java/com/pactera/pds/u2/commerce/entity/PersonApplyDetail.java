
package com.pactera.pds.u2.commerce.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pactera.pds.u2.commerce.entity.mybatis.BCApplyDetailTmp;
import com.pactera.pds.u2.commerce.entity.mybatis.BCHistoryRecordTmp;
import com.pactera.pds.u2.commerce.utils.CodeMapTable;
import com.pactera.pds.u2.commerce.utils.Sessions;

//JPA标识
@Entity
@Table(name = "bc_person_acc_detail")
public class PersonApplyDetail  implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String insCode;//机构
	private Date applyDatetime;//申请日期
	private Double applyAmount;//申请数额
	private Double applyAmount1;//申请数额1
	private String applyType;//申请类型
	private Double approveAmount;//获批数额 #
	private Double approveAmount1;//获批数额1
	private String applyProCity;//申请城市
	private String applyIp;//申请ip
	private String homeAddr;//住家地址
	private String selPhoneNum;//手机
	private String loanAccount;//贷款数额 #
//	private String loanStartDate;//贷款开始日期
//	private String loanEndDate;//贷款结束日期
	private Date loanStartDate;//贷款开始日期
	private Date loanEndDate;//贷款结束日期
	private String idCardNum;//证件号
//	private String applyResult;
	private String idCardType;//证件号类型
	private String fullName;//全名
	private String assType;// 关联类型
    private String assIDNum;// 关联人或企业证件号码
    private String assIDType;// 关联人或企业证件类型
    private String assName;// 关联个人姓名/关联企业名称
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
	public String getApplyResult() {
		if( (approveAmount == null || approveAmount == 0) && (loanAccount==null || "".equals(loanAccount))){
			return "未通过";
		}else{
			return "已通过";
		}
	}
	

    public Double getApplyAmount() {
        return applyAmount;
    }
    
    public void setApplyAmount(Double applyAmount) {
        this.applyAmount = applyAmount;
    }
    
    public Double getApplyAmount1() {
        return applyAmount1;
    }
    
    public void setApplyAmount1(Double applyAmount1) {
        this.applyAmount1 = applyAmount1;
    }
    //	public void setApplyResult(String applyResult) {
//		this.applyResult = applyResult;
//	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getInsCode() {
		return insCode;
	}
    public String getInsCodeStr(){
        return Sessions.maskInsCode(insCode);
    }

    
	public void setInsCode(String insCode) {
		this.insCode = insCode;
	}
	public Date getApplyDatetime() {
		return applyDatetime;
	}
	public void setApplyDatetime(Date applyDatetime) {
		this.applyDatetime = applyDatetime;
	}
	/*public Long getApplyAmount() {
		return applyAmount;
	}*/
    public String getApplyAmountStr(){
        return Sessions.combinInterval(applyAmount, applyAmount1);
    }	
	/*public void setApplyAmount(Long applyAmount) {
		this.applyAmount = applyAmount;
	}*/
	public String getApplyTypeStr() {
		return CodeMapTable.APPLY_CODE_MAP.get(applyType);
	}
    
	public String getApplyType() {
		return applyType;
	}
	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}
	public Double getApproveAmount() {
		return approveAmount;
	}
    public String getApproveAmountStr(){
        return Sessions.combinInterval(approveAmount, approveAmount1);
    }	
	public void setApproveAmount(Double approveAmount) {
		this.approveAmount = approveAmount;
	}
	public String getApplyProCity() {
		return applyProCity;
	}
	public void setApplyProCity(String applyProCity) {
		this.applyProCity = applyProCity;
	}
	public String getApplyIp() {
		return applyIp;
	}
	public void setApplyIp(String applyIp) {
		this.applyIp = applyIp;
	}
	public String getHomeAddr() {
		return homeAddr;
	}
	public void setHomeAddr(String homeAddr) {
		this.homeAddr = homeAddr;
	}
	public String getSelPhoneNum() {
		return selPhoneNum;
	}
	public void setSelPhoneNum(String selPhoneNum) {
		this.selPhoneNum = selPhoneNum;
	}
	//从申请记录导入数据
	public void fromApplyTmp(BCApplyDetailTmp tmp){
	    this.insCode=tmp.getInsCode();
	    this.applyAmount=tmp.getApplyAmount1();
	   // this.applyDatetime=new Date(tmp.getApplyDatetime());
	    this.applyIp=tmp.getApplyIp();
	    this.applyProCity=tmp.getApplyProCity();
	    this.applyType=tmp.getApplyType();
	    this.homeAddr=tmp.getHomeAddr();
	    this.selPhoneNum=tmp.getSelPhoneNum();
	    this.idCardNum=tmp.getIdCardNum();
	    //
	    this.idCardType = tmp.getIdCardType();
	    this.fullName = tmp.getFullName();
	    this.applyDatetime=Sessions.convert2date(tmp.getApplyDatetime());
//	    DateFormat df = new SimpleDateFormat("yyyyMMdd");
//	    try {
//			Date daystart = df.parse(tmp.getApplyDatetime());
//			this.applyDatetime=daystart;
//		} catch (ParseException e) {
//			e.printStackTrace();
//		} 
//	    this.applyDatetime=new Date(tmp.getApplyDatetime());
	    this.loanAccount = tmp.getLoanAccount();
	    this.loanStartDate = Sessions.convert2date(tmp.getLoanStartDate());
	    this.loanEndDate = Sessions.convert2date(tmp.getLoanEndDate());
	    this.approveAmount= tmp.getApprovalAmount1();
	    this.approveAmount1 =tmp.getApprovalAmount2();
	    this.applyAmount = tmp.getApplyAmount1();
	    this.applyAmount1 = tmp.getApplyAmount2();
	    this.comment1 = tmp.getComment1();
	    this.comment2 = tmp.getComment2();
//	    tmp.getApplyDatetime()
	    this.assType = tmp.getAssType();
	    this.assIDNum = tmp.getAssIDNum();
	    this.assIDType = tmp.getAssIDType();
	    this.assName = tmp.getAssName();
	}
	
	
	
	//从历史回溯导入数据
	public void fromHistoryTmp(BCHistoryRecordTmp tmp){
        this.insCode=tmp.getInsCode();
        this.applyAmount=tmp.getApplyAmount1();
       // this.applyDatetime=new Date(tmp.getApplyDatetime());
        this.applyIp=tmp.getApplyIp();
        this.applyProCity=tmp.getApplyProCity();
        this.applyType=tmp.getApplyType();
        this.approveAmount=tmp.getApplyAmount1();
        this.homeAddr=tmp.getHomeAddr();
        this.selPhoneNum=tmp.getSelPhoneNum();
        this.idCardNum=tmp.getIdCardNum();
	}
    
    public String getLoanAccount() {
        return loanAccount;
    }
    
    public void setLoanAccount(String loanAccount) {
        this.loanAccount = loanAccount;
    }
    
//    public String getLoanStartDate() {
//        return loanStartDate;
//    }
//    
//    public String getLoanEndDate() {
//        return loanEndDate;
//    }
//    
//    public void setLoanStartDate(String loanStartDate) {
//        this.loanStartDate = loanStartDate;
//    }
//    
//    public void setLoanEndDate(String loanEndDate) {
//        this.loanEndDate = loanEndDate;
//    }
    
    public String getIdCardNum() {
        return idCardNum;
    }
    
    public Date getLoanStartDate() {
		return loanStartDate;
	}
	public void setLoanStartDate(Date loanStartDate) {
		this.loanStartDate = loanStartDate;
	}
	public Date getLoanEndDate() {
		return loanEndDate;
	}
	public void setLoanEndDate(Date loanEndDate) {
		this.loanEndDate = loanEndDate;
	}
	public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }
    
 /*   public Long getApplyAmount1() {
        return applyAmount1;
    }*/
    
    public Double getApproveAmount1() {
        return approveAmount1;
    }
    
  /*  public void setApplyAmount1(Long applyAmount1) {
        this.applyAmount1 = applyAmount1;
    }*/
    
    public void setApproveAmount1(Double approveAmount1) {
        this.approveAmount1 = approveAmount1;
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
}
