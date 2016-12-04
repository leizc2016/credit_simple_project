package com.pactera.pds.u2.commerce.service.car;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

import com.pactera.pds.u2.commerce.entity.BundPersonComment;
import com.pactera.pds.u2.commerce.entity.PersonAccDetail;
import com.pactera.pds.u2.commerce.entity.PersonApplyDetail;
import com.pactera.pds.u2.commerce.entity.PersonBasicInfo;
import com.pactera.pds.u2.commerce.entity.PersonSummaryReport;


//征信查询缓存
public class CacheResultWapper implements Serializable {
    
    private PersonBasicInfo basicInfo;
    
    private PersonSummaryReport summaryReport;
    
    private List<BundPersonComment> comments;
    
    private List<PersonApplyDetail> applyDetail;
    
    private List<PersonAccDetail> actionDetail;
    
    private Timestamp cacheTime;
    
    public CacheResultWapper() {
        cacheTime = new Timestamp(System.currentTimeMillis());
    }
    
    public String serializeEncoding() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);
            return Base64.encodeBase64String(bos.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("serialize CacheResultWapper error", e);
        }
    }
    
    public static CacheResultWapper deserializeEncoding(String str) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(Base64.decodeBase64(str));
            ObjectInputStream ois = new ObjectInputStream(bis);
            return (CacheResultWapper) ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException("deserialize CacheResultWapper error", e);
        }
    }
    
    public static void main(String[] args) {
        CacheResultWapper c = new CacheResultWapper();
        PersonBasicInfo bp = new PersonBasicInfo();
        c.setBasicInfo(bp);
        bp.setFullName("测试序列号");
        String str = c.serializeEncoding();
        System.out.println(str);
        CacheResultWapper dc = CacheResultWapper.deserializeEncoding(str);
        System.out.println(dc.getBasicInfo().getFullName());
    }
    
    public PersonBasicInfo getBasicInfo() {
        return basicInfo;
    }
    
    public PersonSummaryReport getSummaryReport() {
        return summaryReport;
    }
    
    public List<BundPersonComment> getComments() {
        return comments;
    }
    
    public List<PersonApplyDetail> getApplyDetail() {
        return applyDetail;
    }
    
    public List<PersonAccDetail> getActionDetail() {
        return actionDetail;
    }
    
    public void setBasicInfo(PersonBasicInfo basicInfo) {
        this.basicInfo = basicInfo;
    }
    
    public void setSummaryReport(PersonSummaryReport summaryReport) {
        this.summaryReport = summaryReport;
    }
    
    public void setComments(List<BundPersonComment> comments) {
        this.comments = comments;
    }
    
    public void setApplyDetail(List<PersonApplyDetail> applyDetail) {
        this.applyDetail = applyDetail;
    }
    
    public void setActionDetail(List<PersonAccDetail> actionDetail) {
        this.actionDetail = actionDetail;
    }
    
    public Timestamp getCacheTime() {
        return cacheTime;
    }
    
    public void setCacheTime(Timestamp cacheTime) {
        this.cacheTime = cacheTime;
    }
}
