package com.pactera.pds.u2.commerce.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConstantUtil {

//	public static final Map<String, String> nameMap 
//	                  = new HashMap<String, String>(){
//		{
//			put("1", "匹配");
//			put("2", "不匹配");
//			put("3", "客户未实名");
//		}
//	};
//	
//	public static final Map<String, String>  timeInNet
//		    = new HashMap<String, String>(){
//		{
//			put("0", "1年以下");
//			put("1", "1-2年");
//			put("2", "2-3年");
//			put("3", "3年以上");
//		}
//	};
//	
//	public static final Map<String, String>  consumeClass
//		    = new HashMap<String, String>(){
//		{
//			put("1", "消费低");
//			put("2", "消费中等");
//			put("3", "消费高");
//		}
//	};
//	
//	public static final Map<String, String>  oftenSite
//		    = new HashMap<String, String>(){
//		{
//			put("1", "上班时间");
//			put("2", "休息时间");
//		}
//	};
//	
//	public static final Map<String, String>  howOftenComunication
//		    = new HashMap<String, String>(){
//		{
//			put("1", "无通话");
//			put("2", "偶尔");
//			put("3", "经常");
//		}
//	};
	
//	public static final Map<String, String>  STATUS_CODE_MAP
//	    = new HashMap<String, String>(){
//
//
//		{
//			put("10000", "认证用户不存在");
//			put("10001", "认证密码不正确");
//			put("10002", "用户认证错误");
//			put("10003", "用户令牌已存在");
//			put("10004", "用户认证成功");
//			put("10005", "业务查询成功");
//			put("10006", "业务查询失败");
//			put("10007", "用户令牌已失效");
//			put("10008", "没有相关记录");
//			put("10009", "暂不支持此查询类型");
//			put("10010", "报文不合法");
//			put("10011", "未知错误");
//			put("10012", "接口不存在");
//			put("10013", "用户失效或禁用");
//			put("10014", "用户令牌与机构不匹配");
//			put("10015", "用户授权码错误");
//			put("10016", "报文解密失败");
//			put("10017", "手机号码错误");
//			put("10018", "暂不支持此运营商");
//			put("20000", "输入参数错误");
//			
//			put("YW000", "业务查询成功");
//			put("YW001", "用户令牌已失效");
//			put("YW002", "参数不正确");
//			put("YW003", "查询余额不足");
//			put("YW004", "暂不支持此运营商");
//			put("YW005", "无相关记录");
//			put("YW006", "未知错误");
//		}
//	};
	
	
	
	public static final Map<String, String> STATUS_CODE_MAP = new HashMap<String, String>();
	static {
		STATUS_CODE_MAP.put("YW000", "操作成功");
		STATUS_CODE_MAP.put("YW001", "用户令牌已失效");
		STATUS_CODE_MAP.put("YW002", "参数不正确");
		STATUS_CODE_MAP.put("YW003", "查询余额不足");
		STATUS_CODE_MAP.put("YW004", "暂不支持此运营商");
		STATUS_CODE_MAP.put("YW005", "查无记录");
		STATUS_CODE_MAP.put("YW006", "未知错误");
		STATUS_CODE_MAP.put("YW007", "查询请求类型错误");
		STATUS_CODE_MAP.put("YW008", "请后续获取异步数据");
		
		//token获取请求状态代码
		STATUS_CODE_MAP.put("10000", "登录成功");
		STATUS_CODE_MAP.put("10001", "用户不存在或禁用");
		STATUS_CODE_MAP.put("10002", "密码错误");
		STATUS_CODE_MAP.put("10003", "该用户无此权限");
		STATUS_CODE_MAP.put("10004", "参数有误");
		STATUS_CODE_MAP.put("10005", "其他错误");
		
//		STATUS_CODE_MAP.put("10000", "认证用户不存在");
//		STATUS_CODE_MAP.put("10001", "认证密码不正确");
//		STATUS_CODE_MAP.put("10002", "用户认证错误");
//		STATUS_CODE_MAP.put("10003", "用户令牌已存在");
//		STATUS_CODE_MAP.put("10004", "用户认证成功");
//		STATUS_CODE_MAP.put("10005", "业务查询成功");
//		STATUS_CODE_MAP.put("10006", "业务查询失败");
//		STATUS_CODE_MAP.put("10007", "用户令牌已失效");
//		STATUS_CODE_MAP.put("10008", "没有相关记录");
//		STATUS_CODE_MAP.put("10009", "暂不支持此查询类型");
//		STATUS_CODE_MAP.put("10010", "报文不合法");
//		STATUS_CODE_MAP.put("10011", "未知错误");
//		STATUS_CODE_MAP.put("10012", "接口不存在");
//		STATUS_CODE_MAP.put("10013", "用户失效或禁用");
//		STATUS_CODE_MAP.put("10014", "用户令牌与机构不匹配");
//		STATUS_CODE_MAP.put("10015", "用户授权码错误");
//		STATUS_CODE_MAP.put("10016", "报文解密失败");
//		STATUS_CODE_MAP.put("10017", "手机号码错误");
//		STATUS_CODE_MAP.put("10018", "暂不支持此运营商");
//		STATUS_CODE_MAP.put("20000", "输入参数错误");
	}
	
	public static final Map<String, String> MATCH_FLAG_MAP = new HashMap<String, String>();
	static {
		MATCH_FLAG_MAP.put("0", "不匹配");
		MATCH_FLAG_MAP.put("1", "匹配");
	}
	
	public static final Map<String, String> GEO_MATCH_FLAG_MAP = new HashMap<String, String>();
	static {
		GEO_MATCH_FLAG_MAP.put("1", "匹配");
		GEO_MATCH_FLAG_MAP.put("2", "不匹配");
		GEO_MATCH_FLAG_MAP.put("3", "未实名");
	}
	
	public static final Map<String, String> GEO_ONLINE_TIME_MAP = new HashMap<String, String>();
	static {
		GEO_ONLINE_TIME_MAP.put("0", "1年以下");
		GEO_ONLINE_TIME_MAP.put("1", "1-2年");
		GEO_ONLINE_TIME_MAP.put("2", "2-3年");
		GEO_ONLINE_TIME_MAP.put("3", "3年以上");
	}
	
	public static final Map<String, String> GEO_CONS_LEVEL_MAP = new HashMap<String, String>();
	static {
		GEO_CONS_LEVEL_MAP.put("1", "0-50");
		GEO_CONS_LEVEL_MAP.put("2", "50-200");
		GEO_CONS_LEVEL_MAP.put("3", "200以上");
	}
	
	public static final Map<String, String> GEO_CALL_FREQUENCY_MAP = new HashMap<String, String>();
	static {
		GEO_CALL_FREQUENCY_MAP.put("0", "0");
		GEO_CALL_FREQUENCY_MAP.put("1", "1-4");
		GEO_CALL_FREQUENCY_MAP.put("2", "5次以上");
	}
	
	public static final Map<String, String> RECENT_CALL_FREQUENCY_MAP = new HashMap<String, String>();
	static {
		RECENT_CALL_FREQUENCY_MAP.put("0", "0");
		RECENT_CALL_FREQUENCY_MAP.put("1", "1-5");
		RECENT_CALL_FREQUENCY_MAP.put("6", "6-10");
		RECENT_CALL_FREQUENCY_MAP.put("11", "11-20");
		RECENT_CALL_FREQUENCY_MAP.put("21", "21-40");
		RECENT_CALL_FREQUENCY_MAP.put("41", "41-80");
		RECENT_CALL_FREQUENCY_MAP.put("81", "81-160");
		RECENT_CALL_FREQUENCY_MAP.put("161", "161及以上");
	}
	
	
	public static final Map<String, String> BILL_AMOUNT_MAP = new HashMap<String, String>();
	static {
		BILL_AMOUNT_MAP.put("0", "0");
		BILL_AMOUNT_MAP.put("1", "1-5");
		BILL_AMOUNT_MAP.put("501", "5-10");
		BILL_AMOUNT_MAP.put("1001", "11-20");
		BILL_AMOUNT_MAP.put("2001", "20-40");
		BILL_AMOUNT_MAP.put("4001", "40-80");
		BILL_AMOUNT_MAP.put("8001", "80-160");
		BILL_AMOUNT_MAP.put("16001", "160-320");
		BILL_AMOUNT_MAP.put("32001", "320-640");
		BILL_AMOUNT_MAP.put("64001", "640及以上");
	}
	
	public static final Map<String, String> BILL_DATA_MAP = new HashMap<String, String>();
	static {
		BILL_DATA_MAP.put("0", "0");
		BILL_DATA_MAP.put("1", "1-5");
		BILL_DATA_MAP.put("5000001", "5-10");
		BILL_DATA_MAP.put("10000001", "11-20");
		BILL_DATA_MAP.put("20000000", "20-40");
		BILL_DATA_MAP.put("40000001", "40-80");
		BILL_DATA_MAP.put("80000001", "80-160");
		BILL_DATA_MAP.put("160000001", "160-320");
		BILL_DATA_MAP.put("320000001", "320-640");
		BILL_DATA_MAP.put("640000001", "640-1280");
		BILL_DATA_MAP.put("1280000001", "1280-2560");
		BILL_AMOUNT_MAP.put("2560000001", "2560-5210");
		BILL_DATA_MAP.put("5120000001", "5120-10240");
		BILL_DATA_MAP.put("10240000001", "1024-2048");
		BILL_DATA_MAP.put("20480000001", "20480及以上");
	}
	
	public static final Map<String, String> CS_ONLINETIME_MAP = new HashMap<String, String>();
	static {
		CS_ONLINETIME_MAP.put("0", "0");
		CS_ONLINETIME_MAP.put("1", "1-5");
		CS_ONLINETIME_MAP.put("6", "6-10");
		CS_ONLINETIME_MAP.put("11", "11-20");
		CS_ONLINETIME_MAP.put("21", "21-40");
		CS_ONLINETIME_MAP.put("41", "41-80");
		CS_ONLINETIME_MAP.put("81", "81-160");
		CS_ONLINETIME_MAP.put("161", "161-320");
		CS_ONLINETIME_MAP.put("321", "321-640");
		CS_ONLINETIME_MAP.put("641", "641-1280");
		CS_ONLINETIME_MAP.put("1281", "1281-2560");
		CS_ONLINETIME_MAP.put("2561", "2561及以上");
	}
	
	
	public static final Map<String, String> CS_CODE_MAP = new HashMap<String, String>();
	static {
		CS_CODE_MAP.put("0", "YW000");
		CS_CODE_MAP.put("-1", "YW006");
		CS_CODE_MAP.put("-2", "YW006");
		CS_CODE_MAP.put("-3", "YW005");
		CS_CODE_MAP.put("-4", "YW006");
		CS_CODE_MAP.put("-5", "YW006");
	}
	
	public static final Map<String, String> YLZC_CODE_MAP = new HashMap<String, String>();
	static {
		YLZC_CODE_MAP.put("0", "YW000");
		YLZC_CODE_MAP.put("10005", "YW005");
		YLZC_CODE_MAP.put("100010", "YW006");
	}
	
	public static final Map<String, String> YLZC_CHV_SCORE = new HashMap<String, String>();
	static {
		YLZC_CHV_SCORE.put("1", "高端人群");
		YLZC_CHV_SCORE.put("2", "文艺小资");
		YLZC_CHV_SCORE.put("3", "白领人士");
		YLZC_CHV_SCORE.put("4", "潜力客户");
		YLZC_CHV_SCORE.put("5", "职场新人");
		YLZC_CHV_SCORE.put("6", "商旅人士");
		YLZC_CHV_SCORE.put("7", "居家人士");
		YLZC_CHV_SCORE.put("8", "个体商户");
		YLZC_CHV_SCORE.put("9", "低端消费人群");
		YLZC_CHV_SCORE.put("null", "无交易记录人群");
	}
	
	public static final Map<String, String> YLZC_CST_SCORE = new HashMap<String, String>();
	static {
		YLZC_CST_SCORE.put("1", "不活跃客户");
		YLZC_CST_SCORE.put("2", "长期忠诚客户");
		YLZC_CST_SCORE.put("3", "活跃上升客户");
		YLZC_CST_SCORE.put("4", "活跃下降客户");
		YLZC_CST_SCORE.put("5", "自激活或新客户");
		YLZC_CST_SCORE.put("6", "睡眠客户");
		YLZC_CST_SCORE.put("null", "活跃度未知客户");
	}
	
	public static final Map<String, String> YLZC_CNP_SCORE = new HashMap<String, String>();
	static {
		YLZC_CNP_SCORE.put("1", "取现转账");
		YLZC_CNP_SCORE.put("2", "购物消费");
		YLZC_CNP_SCORE.put("3", "商务差旅");
		YLZC_CNP_SCORE.put("4", "生意达人");
		YLZC_CNP_SCORE.put("5", "卫生医疗");
		YLZC_CNP_SCORE.put("6", "家居装修");
		YLZC_CNP_SCORE.put("7", "爱车一族");
		YLZC_CNP_SCORE.put("8", "育婴早教");
		YLZC_CNP_SCORE.put("null", "最近半年无消费客户");
	}
	
	public static final Map<String, String> YLZC_WLP_SCORE = new HashMap<String, String>();
	static {
		YLZC_WLP_SCORE.put("1", "持卡人很少使用该卡，交易特征为划卡次数很少，用卡商户类型少，总交易金额低，刷卡消费不稳定");
		YLZC_WLP_SCORE.put("2", "该卡主要用于某一两次大额支付服务，用卡次数少、商户类型少，总划卡金额及单笔高，刷卡消费比较不稳定");
		YLZC_WLP_SCORE.put("3", "该卡主要用于某类特定商户的交易，划卡次数多，用卡商户类型少，刷卡消费比较稳定，但用卡范围比较固定");
		YLZC_WLP_SCORE.put("4", "该卡是持卡人次首选卡，消费频率高、类别范围广，与发卡银行业务粘合度高，刷卡消费稳定");
		YLZC_WLP_SCORE.put("5", "该卡是持卡人首选卡，消费频率高、类别范围广，与发卡银行业务粘合度高，刷卡消费稳定");
		YLZC_WLP_SCORE.put("null", "最近半年未使用该卡");
	}
	
	public static final Map<String, String> GEO_CODE_MAP = new HashMap<String, String>();
	static {
		GEO_CODE_MAP.put("10000", "YW007");
		GEO_CODE_MAP.put("10001", "YW007");
		GEO_CODE_MAP.put("10002", "YW007");
		GEO_CODE_MAP.put("10003", "YW007");
//		GEO_CODE_MAP.put("10004", "YW000");
		GEO_CODE_MAP.put("10005", "YW000");
		GEO_CODE_MAP.put("10006", "YW007");
		GEO_CODE_MAP.put("10007", "YW007");
		GEO_CODE_MAP.put("10008", "YW006");
		GEO_CODE_MAP.put("10009", "YW006");
		GEO_CODE_MAP.put("10010", "YW007");
		GEO_CODE_MAP.put("10011", "YW007");
		GEO_CODE_MAP.put("10012", "YW007");
		GEO_CODE_MAP.put("10013", "YW007");
		GEO_CODE_MAP.put("10014", "YW007");
		GEO_CODE_MAP.put("10015", "YW007");
		GEO_CODE_MAP.put("10016", "YW007");
		GEO_CODE_MAP.put("10017", "YW007");
		GEO_CODE_MAP.put("10018", "YW005");
	}
	
	public static final Map<String, String> CS_IDNUM_FLAG = new HashMap<String, String>();
	static {
		CS_IDNUM_FLAG.put("0", "匹配");
		CS_IDNUM_FLAG.put("1", "不匹配");
	}
	
	public static final List<String> GEO_ISPSCOPE_TELCOM = new ArrayList<String>();
	static {
		GEO_ISPSCOPE_TELCOM.add("上海");
		GEO_ISPSCOPE_TELCOM.add("浙江");
		GEO_ISPSCOPE_TELCOM.add("江苏");
	}
	
	public static final List<String> GEO_ISPSCOPE_UNION = new ArrayList<String>();
	static {
		GEO_ISPSCOPE_UNION.add("上海");
		GEO_ISPSCOPE_UNION.add("浙江");
		GEO_ISPSCOPE_UNION.add("江苏");
	}

	public static final List<String> GEO_ISPSCOPE_MOBILE = new ArrayList<String>();
	static {
		GEO_ISPSCOPE_MOBILE.add("福建");
		GEO_ISPSCOPE_MOBILE.add("浙江");
	}
	
	public static final List<String> CS_ISPSCOPE_TELCOM = new ArrayList<String>();
	static {
		CS_ISPSCOPE_TELCOM.add("上海");
		CS_ISPSCOPE_TELCOM.add("浙江");
		CS_ISPSCOPE_TELCOM.add("福建");
		CS_ISPSCOPE_TELCOM.add("江苏");
		CS_ISPSCOPE_TELCOM.add("广东");
	}
	
	public static final List<String> CS_ISPSCOPE_UNION = new ArrayList<String>();
	static {

	}
	
	public static final List<String> CS_ISPSCOPE_MOBILE = new ArrayList<String>();
	static {
		CS_ISPSCOPE_MOBILE.add("广东");
	}
}
