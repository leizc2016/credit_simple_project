package com.bundcredit.entity;

import  org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
public class CourtAnnouncementBean {
    private String announcementType;//公告类型	
    private String announcement;//公告人
    private String litigant;//当事人
    private String announcementDate;//公告时间
    private String content;//公告内容
    
	public String getAnnouncementType() {
		return announcementType;
	}
	public void setAnnouncementType(String announcementType) {
		this.announcementType = announcementType;
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
	public String getLitigant() {
		return litigant;
	}
	public void setLitigant(String litigant) {
		this.litigant = litigant;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
    
}
