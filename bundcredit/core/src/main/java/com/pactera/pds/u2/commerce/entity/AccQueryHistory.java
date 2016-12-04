
package com.pactera.pds.u2.commerce.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.pactera.pds.u2.commerce.utils.DESUtil;

//JPA标识
@Entity
@Table(name = "bc_acc_query_history")
public class AccQueryHistory   extends IdEntity{
	
	private String insAccName;
	private String insCode;
	private String lastLabel;
	private String queryTypeName;
	private String urlByCondition;
	private Date queryDatetime;
	private String queryType;
	private String idCardNum;
	private Integer pageId;
	private Long insAccId;
	private String productName;
	private static DESUtil desutil;
    static void setDes(DESUtil des){
        desutil=des;
    }
	public String getProductName() {
		if(1==pageId)
			return "基础信息";
		if(2==pageId)
			return "详细信息";
		return "出错";
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setQueryTypeName(String queryTypeName) {
		this.queryTypeName = queryTypeName;
	}

	
    public String getInsCode() {
        return insCode;
    }
    
    public void setInsCode(String insCode) {
        this.insCode = insCode;
    }
    public void setUrlByCondition(String urlByCondition) {
		this.urlByCondition = urlByCondition;
	}

	public String getQueryTypeName() {
		if("1".equals(queryType))
			return "申请查询";
		if("2".equals(queryType))
			return "贷后查询";
		return "空";
	}
	
	public String getUrlByCondition() {
		if(1==pageId)
			return "/creditreport/conciseSearch?search_idCardNum="+getIdCardNumString()+"&search_search_type=" + queryType;
		if(2==pageId)
			return "/creditreport/fullSearch?search_idCardNum="+getIdCardNumString()+"&search_search_type=" + queryType;
		return "";
	}
	
	public String getInsAccName() {
		return insAccName;
	}
	public void setInsAccName(String insAccName) {
		this.insAccName = insAccName;
	}
	public String getLastLabel() {
		return lastLabel;
	}
	public void setLastLabel(String lastLabel) {
		this.lastLabel = lastLabel;
	}
	public Long getInsAccId() {
		return insAccId;
	}
	public void setInsAccId(Long insAccId) {
		this.insAccId = insAccId;
	}
	public Date getQueryDatetime() {
		return queryDatetime;
	}
	public void setQueryDatetime(Date queryDatetime) {
		this.queryDatetime = queryDatetime;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public String getIdCardNum() {
	  /*  String temp=null;
        try {
            temp=desutil.decrypt(idCardNum);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
		return idCardNum;
	}
	@Transient
	public String getIdCardNumString() {
        String temp=null;
        try {
            temp=desutil.decrypt(idCardNum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }
	public void setIdCardNum(String idCardNum) {
		this.idCardNum = idCardNum;
	}
	public Integer getPageId() {
		return pageId;
	}
	
/*    public String getIdCardNumStr() {
        String temp=null;
        try {
            temp=desutil.decrypt(idCardNum);
        } catch (Exception e) {
            e.printStackTrace();
        }
         return temp;
    }*/
    public void setPageId(Integer pageId) {
		this.pageId = pageId;
	}
	
}
