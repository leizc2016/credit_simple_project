package com.pactera.pds.u2.commerce.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.pactera.pds.u2.commerce.entity.mybatis.BCUser;


/**
 * session工具类
 * 
 * 提供shiro session工具
 * 
 * @author foy
 *
 */
public class Sessions {
    
    private static char[] _C="ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    
    /**
     * 
     * @return 当前用户，登陆或者匿名用户对象
     */
    public static Subject me() {
        return SecurityUtils.getSubject();
    }
    
    /**
     * 当前用户真实姓名
     * 
     * @return
     */
    public static String name() {
        Subject me = me();
        String name = null;
        if (me.isAuthenticated()) {
            name = (String) Beans.getProperty(me.getPrincipal(), "name");
        }
        return name;
    }
    
    // 更新当前登陆用户名
    public static void updateName(String name) {
        Subject me = me();
        if (me.isAuthenticated()) {
            Beans.setProperty(me.getPrincipal(), "name", name);
        }
    }
    
    // 获取当前登陆用户的机构代码
    public static String insCode() {
        Subject me = me();
        String insCode = null;
        if (me.isAuthenticated()) {
            insCode = (String) Beans.getProperty(me.getPrincipal(), "insCode");
        }
        return insCode;
    }
    //获取当前登陆机构名称
    public static String insName(){
        Subject me = me();
        String insName = null;
        if (me.isAuthenticated()) {
            insName = (String) Beans.getProperty(me.getPrincipal(), "insName");
        }
        return insName;        
    }
    /**
     * 当前登陆用户ID
     * 
     * @return
     */
    public static Long id() {
        Subject me = me();
        Long id = null;
        if (me.isAuthenticated()) {
            id = (Long) Beans.getProperty(me.getPrincipal(), "id");
        }
        return id;
    }
    
    /**
     * 当前登陆用户Login Name
     * 
     * @return Login Name
     */
    public static String loginName() {
        Subject me = me();
        String role = null;
        if (me.isAuthenticated()) {
        	role = (String) Beans.getProperty(me.getPrincipal(), "loginName");
        }
        return role;
    }
    
    
    
    /**
     * 
     * @return 当前用的shrio管理session对象
     */
    public static Session session() {
        return me().getSession();
    }
    
    /**
     * 取session中对应key的值
     * 
     * @param key
     * @return
     */
    public static Object getSessionAttr(Object key) {
        return session().getAttribute(key);
    }
    
    public static String getSessionStringAttr(Object key) {
        Object val = getSessionAttr(key);
        if (val instanceof String) {
            return (String) val;
        }
        return null == val ? null : val.toString();
    }
    
    public static void setSessionAttr(Object key, Object val) {
        session().setAttribute(key, val);
    }
    
    public static String ip() {
        return IpAddress.getIpAddr(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
    }
    //机构脱敏
    public static String maskInsCode(String code){
        return insCode().equals(code) ? insName():randomInsName();
    }
    
    public static String randomInsName(){
        return "机构"+randomChar()+randomChar()+randomChar();
    }
    private static char randomChar(){
        return _C[(int)(Math.random()*_C.length)];
    }
    
//    public static Map<String, BCUser> table = new HashMap<String, BCUser>();
//    public static Map<String, BCUser> table = new HashMap<String, BCUser>();
    //数值区间运算
    public static String combinInterval(Comparable low, Comparable high){
    	if(low == null && high == null){
    		return "";
    	}
    	if(low == null){
    		return high+"~"+high;
    	}
    	if(high == null){
    		return low+"~"+low;
    	}
        if(high.compareTo(low)<0){
        	 return high+"~"+low;
        }
        if(high.compareTo(low) == 0){
        	return low + "";
        }
        return low+"~"+high;
    }
    public static void main(String[] args){
        for(int i=0;i<1000;i++){
            System.out.println(randomInsName());
        }
    }
    
    public static Date convert2date(String dateStr){
    	if(StringUtils.isEmpty(dateStr))
    		return null;
		Date date = null;
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		try {
			date = df.parse(dateStr);
		} catch (ParseException e) {
		} 
		return date;
	}
}
