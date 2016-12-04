package com.pactera.pds.u2.commerce.entity;

import java.sql.Timestamp;


//机构交易日志

public class InsitutionTransaction extends IdEntity {
    
    public static enum TRANS_TYPES {
        查询外滩币扣款, 
        查询现金扣款, 
        充值外滩币, 
        充值现金, 
        现金账户信用额度调整, 
        外滩币账户信用额度调整, 
        上传数据
    };
    
    // 交易类型:
    private String transType;
    
    // 交易账户id，可能是后台人员，可能是前台人员.
    private Long insAccId;
    
    // 交易描述，由调用函数填充
    private String description;
    
    // 交易时间
    private Timestamp transDateTime;
    
    // 交易费用：增加或者减少钱
    private Float fee;
    
    // 交易设备
    private String device;
    
    // 交易备注
    private String comments;
    
    // 交易浏览器ip
    private String ipAddr;
    
    // 机构
    private String insCode;
    
    // 操作者姓名
    private String opName;
    
    private String accountType;
    
    private Double balance;
    
    public String getTransType() {
        return transType;
    }
    
    public Long getInsAccId() {
        return insAccId;
    }
    
    public String getDescription() {
        return description;
    }
    
    public Timestamp getTransDateTime() {
        return transDateTime;
    }
    
    
    public String getDevice() {
        return device;
    }
    
    public String getComments() {
        return comments;
    }
    
    public String getIpAddr() {
        return ipAddr;
    }
    
    public String getInsCode() {
        return insCode;
    }
    
    public String getOpName() {
        return opName;
    }
    
    public void setTransType(String transType) {
        this.transType = transType;
    }
    
    public void setInsAccId(Long insAccId) {
        this.insAccId = insAccId;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setTransDateTime(Timestamp transDateTime) {
        this.transDateTime = transDateTime;
    }
    
    
    
    public Float getFee() {
		return fee;
	}

	public void setFee(Float fee) {
		this.fee = fee;
	}

	public void setDevice(String device) {
        this.device = device;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }
    
    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }
    
    public void setInsCode(String insCode) {
        this.insCode = insCode;
    }
    
    public void setOpName(String opName) {
        this.opName = opName;
    }

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
}
