
package com.pactera.pds.u2.commerce.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pactera.pds.u2.commerce.entity.mybatis.BCAccountDetailTmp;
import com.pactera.pds.u2.commerce.entity.mybatis.BCApplyDetailTmp;
import com.pactera.pds.u2.commerce.entity.mybatis.BCCommentTmp;
import com.pactera.pds.u2.commerce.entity.mybatis.BCHistoryRecordTmp;
import com.pactera.pds.u2.commerce.utils.DESUtil;

//JPA标识
@Entity
@Table(name = "bc_person_basic_info")
public class PersonBasicInfo  implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String idCardNum;//证件号
	private String fullName;//名称
	private String location;//所在省市
	private Date reportDatetime;//报告日期
	private Date lastUpdateDatetime;//上次更新日期
	private static DESUtil desutil;
	
	static void setDes(DESUtil des){
	    desutil=des;
	}
	public String getIdCardNum() {
	    /*String temp=null;
		try {
		    temp=desutil.decrypt();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
		 return idCardNum;
	}
	
	public String getIdCardNum2() {
		 return idCardNum;
	}
	
	public String getIdCardNumString() {
		
        try {
			return  desutil.decrypt(idCardNum);
		} catch (Exception e) {
			 return null;
		}
    }
	public void setIdCardNum(String idCardNum) {
		this.idCardNum = idCardNum;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Date getReportDatetime() {
		
		return new Date();
//		return reportDatetime;
	}
	public void setReportDatetime(Date reportDatetime) {
		this.reportDatetime = reportDatetime;
	}
	public Date getLastUpdateDatetime() {
		return lastUpdateDatetime;
	}
	public void setLastUpdateDatetime(Date lastUpdateDatetime) {
		this.lastUpdateDatetime = lastUpdateDatetime;
	}
	
	//从申请记录导入数据
    public void fromApplyTmp(BCApplyDetailTmp tmp){
        this.idCardNum=tmp.getIdCardNum();
        this.fullName=tmp.getFullName();
//        this.location=tmp.getHomeAddr();
        this.location=tmp.getApplyProCity();
        this.reportDatetime = new Date();
    }
    
  //从行为记录导入数据
    public void fromDetailTmp(BCAccountDetailTmp tmp){
        this.idCardNum=tmp.getIdCardNum();
        this.fullName=tmp.getFullName();
//        this.location=tmp.get;
        this.reportDatetime = new Date();
    }
    //从历史回溯导入数据
    public void fromHistoryTmp(BCHistoryRecordTmp tmp){
        this.idCardNum=tmp.getIdCardNum();
        this.fullName=tmp.getFullName();
        this.location=tmp.getHomeAddr();
    }
    
    //从备注信息导入数据
    public void fromCommentTmp(BCCommentTmp tmp){
        this.idCardNum=tmp.getIdCardNum();
        this.fullName=tmp.getFullName();
    }
	
}
