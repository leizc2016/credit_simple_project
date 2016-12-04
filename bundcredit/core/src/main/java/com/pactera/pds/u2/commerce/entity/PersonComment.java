
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

import com.pactera.pds.u2.commerce.entity.mybatis.BCApplyDetailTmp;
import com.pactera.pds.u2.commerce.entity.mybatis.BCCommentTmp;
import com.pactera.pds.u2.commerce.entity.mybatis.BCHistoryRecordTmp;
import com.pactera.pds.u2.commerce.utils.CodeMapTable;
import com.pactera.pds.u2.commerce.utils.Sessions;

//JPA标识
@Entity
@Table(name = "bc_bund_person_comment")
public class PersonComment implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String insCode;//机构
	private String idCardNum;//证件号
	private String idCardType;//证件号类型
	private String fullName;// 姓名
	private String commentType;//备注类型
	private String commentContent;//备注内容
	private Date commentTime;//备注日期
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getInsCode() {
		return insCode;
	}
	public void setInsCode(String insCode) {
		this.insCode = insCode;
	}
	public Date getCommentTime() {
		return commentTime;
	}
	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}
	public String getIdCardNum() {
		return idCardNum;
	}
	public void setIdCardNum(String idCardNum) {
		this.idCardNum = idCardNum;
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
	public String getCommentType() {
		return commentType;
	}
	public void setCommentType(String commentType) {
		this.commentType = commentType;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	//从历史回溯导入数据
	public void fromCommentTmp(BCCommentTmp tmp){
        this.insCode=tmp.getInsCode();
        this.idCardNum=tmp.getIdCardNum();
        this.idCardType=tmp.getIdCardType();
        this.fullName=tmp.getFullName();
        this.commentType=tmp.getCommentType();
        this.commentContent=tmp.getCommentContent();
        this.commentTime=Sessions.convert2date(tmp.getCommentTime());
	}
	
}
