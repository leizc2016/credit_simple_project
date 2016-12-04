package com.pactera.pds.u2.commerce.entity.enterpriseinfo;

import java.io.Serializable;
import java.util.Date;

import  org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
public class CourtAnnouncementBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1733093831402372518L;
	private Integer id;
    private String announcementType;//公告类型	
    private String announcement;//公告人
    private String litigant;//当事人
    private String announcementDate;//公告时间
    private String content;//公告内容
    private String businessName;
	private Date createTime;
	private Date updateTime;
	
	
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getAnnouncementType() {
		return announcementType;
	}
	public void setAnnouncementType(String announcementType) {
		this.announcementType = announcementType;
	}
	public String getLitigant() {
		return litigant;
	}
	public void setLitigant(String litigant) {
		this.litigant = litigant;
	}
	public String getAnnouncement() {
		return announcement;
	}
	public void setAnnouncement(String announcement) {
		this.announcement = announcement;
	}
	public String getAnnouncementDate() {
		return announcementDate;
	}
	public void setAnnouncementDate(String announcementDate) {
		this.announcementDate = announcementDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
    
}
