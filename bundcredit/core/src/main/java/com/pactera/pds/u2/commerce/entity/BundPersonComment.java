package com.pactera.pds.u2.commerce.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

//海纳备注
public class BundPersonComment extends IdEntity implements Serializable{
	

	
    private String idCardNum;
    //备注类型
    private String commentType;
    //备注内容
    private String content;
    //更新时间
    private Timestamp lastUpdateDatetime;
    
    public String getCommentType() {
        return commentType;
    }
    
    public String getCommentTypeStr() {
  	return COMMENT_TYPE_MAP.get(commentType);
  }
    
    public String getContent() {
        return content;
    }
    
    public Timestamp getLastUpdateDatetime() {
        return lastUpdateDatetime;
    }
    
    public void setCommentType(String commentType) {
        this.commentType = commentType;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public void setLastUpdateDatetime(Timestamp lastUpdateDatetime) {
        this.lastUpdateDatetime = lastUpdateDatetime;
    }

    
    public String getIdCardNum() {
        return idCardNum;
    }

    
    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }
    
	private final Map<String, String> COMMENT_TYPE_MAP = new HashMap<String, String>() {
		{
			put("1", "贷款黑名单");
			put("2", "从业人员黑名单");
			put("3", "法院黑名单");
			put("4", "个人申诉");
			put("5", "其他");
		}
	};
}
