package com.pactera.pds.u2.commerce.entity;


public class Institution {
    private String insCode;
    private String name;
    private int lineOfCredit;
    private Double balance;
    private int cashCredit;
    private Double cashBalance;
    private String productCode;
    
    
    
    public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getInsCode() {
        return insCode;
    }
    
    public String getName() {
        return name;
    }
    
    public int getLineOfCredit() {
        return lineOfCredit;
    }
    
    
    public void setInsCode(String insCode) {
        this.insCode = insCode;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setLineOfCredit(int lineOfCredit) {
        this.lineOfCredit = lineOfCredit;
    }

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public int getCashCredit() {
		return cashCredit;
	}

	public void setCashCredit(int cashCredit) {
		this.cashCredit = cashCredit;
	}

	public Double getCashBalance() {
		return cashBalance;
	}

	public void setCashBalance(Double cashBalance) {
		this.cashBalance = cashBalance;
	}
    
    
}
