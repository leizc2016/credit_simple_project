package com.pactera.pds.u2.commerce.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constant {

	public static final Map<String, String> nameMap 
	                  = new HashMap<String, String>(){
		{
			put("1", "匹配");
			put("2", "不匹配");
			put("3", "客户未实名");
		}
	};
	
	public static final Map<String, String>  timeInNet
		    = new HashMap<String, String>(){
		{
			put("0", "1年以下");
			put("1", "1-2年");
			put("2", "2-3年");
			put("3", "3年以上");
		}
	};
	
	public static final Map<String, String>  consumeClass
		    = new HashMap<String, String>(){
		{
			put("1", "消费低");
			put("2", "消费中等");
			put("3", "消费高");
		}
	};
	
	public static final Map<String, String>  oftenSite
		    = new HashMap<String, String>(){
		{
			put("1", "上班时间");
			put("2", "休息时间");
		}
	};
	
	public static final Map<String, String>  howOftenComunication
		    = new HashMap<String, String>(){
		{
			put("1", "无通话");
			put("2", "偶尔");
			put("3", "经常");
		}
	};
	
	public static final Map<String, String>  errorCode
	    = new HashMap<String, String>(){
		{
			put("10000", "认证用户不存在");
			put("10001", "认证密码不正确");
			put("10002", "用户认证错误");
			put("10003", "用户令牌已存在");
			put("10004", "用户认证成功");
			put("10005", "业务查询成功");
			put("10006", "业务查询失败");
			put("10007", "用户令牌已失效");
			put("10008", "没有相关记录");
			put("10009", "暂不支持此查询类型");
			put("10010", "报文不合法");
			put("10011", "未知错误");
			put("10012", "接口不存在");
			put("10013", "用户失效或禁用");
			put("10014", "用户令牌与机构不匹配");
			put("10015", "用户授权码错误");
			put("10016", "报文解密失败");
			put("10017", "手机号码错误");
			put("10018", "暂不支持此运营商");
			put("20000", "输入参数错误");
			
		}
	};
	
	public static final List<String>  avalaibleNum2provinces4telcom
    	= new ArrayList<String>(){
		{
			add("上海");
			add("浙江");
		}
	};
	public static final List<String>  avalaibleNum2provinces4union
	= new ArrayList<String>(){
	{
		add("上海");
		add("浙江");
	}
	};
	public static final List<String>  avalaibleNum2provinces4mobile
	= new ArrayList<String>(){
	{
		add("福建");
	}
	};
	public static final List<String>  CSavalaibleNum2provinces4telcom
	= new ArrayList<String>(){
		{
			add("上海");
			add("浙江");
			add("福建");
			add("江苏");
			add("广东");
		}
	};
	public static final List<String>  CSavalaibleNum2provinces4union
	= new ArrayList<String>(){
		{
			
		}
	};
	public static final List<String>  CSavalaibleNum2provinces4mobile
	= new ArrayList<String>(){
		{
			add("广东");
		}
	};
	
	public static String LAST_REQUEST_TIME_KEY = "lastRequestTimeKey";
}
