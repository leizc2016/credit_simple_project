
package com.pactera.pds.u2.commerce.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

//JPA标识
@Entity
@Table(name = "bc_product")
public class QueryProduct extends IdEntity implements Serializable{
	private static final long serialVersionUID = 4407327179619342669L;
	private String name;//产品名
	private Float queryNoreturnPrice;//无增量查询费用
	private Float queryPrice;//查询基础费用
	private Float queryReturnPrice;//有增量查询结果费用
	private int   cashFlag;  //支付方式
	private String  productCode;//产品编号
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Float getQueryNoreturnPrice() {
		return queryNoreturnPrice;
	}
	public void setQueryNoreturnPrice(Float queryNoreturnPrice) {
		this.queryNoreturnPrice = queryNoreturnPrice;
	}
	public Float getQueryPrice() {
		return queryPrice;
	}
	public void setQueryPrice(Float queryPrice) {
		this.queryPrice = queryPrice;
	}
	public Float getQueryReturnPrice() {
		return queryReturnPrice;
	}
	public void setQueryReturnPrice(Float queryReturnPrice) {
		this.queryReturnPrice = queryReturnPrice;
	}
	public int getCashFlag() {
		return cashFlag;
	}
	public void setCashFlag(int cashFlag) {
		this.cashFlag = cashFlag;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
}
