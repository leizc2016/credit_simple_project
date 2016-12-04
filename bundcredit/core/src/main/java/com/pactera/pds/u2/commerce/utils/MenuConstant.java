package com.pactera.pds.u2.commerce.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuConstant {
	public static final String IPS_CS = "CS";
	public static final String IPS_GEO = "GEO";
    //产品code
	public static final String conciseCode = "JCCX";
	public static final String fullCode = "XBCX";
	public static final String yunYinShangCode = "YYSCX";
	public static final String yunYinShangCSCode = "YYSCX_CS";
	public static final String yunYinShangGEOCode = "YYSCX_GEO";
	public static final String yinLianCode = "YLCX";
	public static final String yinLianZCCode = "YLCX_ZC";
	public static final String QYCode = "QYCX";
	public static final String JRWW_CODE = "JRWW";
	/**
	 * 个人借贷查询
	 */
	public static final String personalBorrowingCode="GRJDCX";
	/**
	 * 企业借贷查询
	 */
	public static final String corporateLendingCode="QYJDCX";
	//基础查询URL
	public static String concisePath = "/creditreport/concise";
	public static String conciseSearchPath = "/creditreport/conciseSearch";
	//详版查询
	public static String fullPath = "/creditreport/full";
	public static String fullSearchPath = "/creditreport/fullSearch";
	//运营商
	public static String yunYinShangPath = "/creditreport/yunYingShang";
	public static String yunYinShangCSPath = "/creditreport/yunYingShangCS";
	public static String yunYinShangGEOPath = "/creditreport/yunYingShangGEO";
	public static String yunYinShangGEOSearchPath = "/creditreport/yunYingShangGEOSearch";
	public static String yunYinShangCSSearchPath = "/creditreport/yunYingShangCSSearch";
	//银联
	public static String yinLianPath = "/creditreport/yinLian";
	//工商查询
	public static String ENTERPRISE_PATH = "/creditreport/enterprise";
	
	//金融维稳
	public static String JRWW_PATH = "/creditreport/financialStableDistribute";
	
	//个人贷款查询
	public static String personalBorrowingPath = "/personalBorrow/toReport";
	public static String personalBorrowingSearchPath = "/personalBorrow/fullSearch";
	
	//企业贷款查询
	public static String corporateLendingPath = "/corporateLending/toReport";
	public static String corporateLendingSearchPath = "/corporateLending/fullSearch";
		
	public static Map<String, String> menuURL = new HashMap<String, String>(){
		{
			put(conciseCode, concisePath);
			put(fullCode, fullPath);
			put(yunYinShangCode, yunYinShangPath);
			put(yinLianCode, yinLianPath);
			put(QYCode, ENTERPRISE_PATH);
			put(JRWW_CODE, JRWW_PATH);
			put(yunYinShangCSCode, yunYinShangPath);
			put(yunYinShangGEOCode, yunYinShangPath);
			put(yinLianZCCode, yinLianPath);
			put(personalBorrowingCode, personalBorrowingPath);
			put(corporateLendingCode, corporateLendingPath);
		}
	};
	
	public static Map<String, String> productName = new HashMap<String, String>(){
		{
			put(conciseCode, "基础查询");
			put(fullCode, "详版查询");
			put(yunYinShangCode, "运营商信息查询");
			put(yunYinShangCSCode, "运营商信息查询");
			put(yunYinShangGEOCode, "运营商信息查询");
			put(yinLianCode, "银联信息查询");
			put(yinLianZCCode, "银联信息查询");
			put(QYCode, "工商信息查询");
			put(JRWW_CODE, "金融维稳");
			put(personalBorrowingCode, "个人借贷查询");
			put(corporateLendingCode, "企业借贷查询");
		}
	};
	
	public static List<String>  conciseURLs = new ArrayList<String>(){
		{
			add(concisePath);
			add(conciseSearchPath);
		}
	};
	public static List<String> fullURLs = new ArrayList<String>(){
		{
			add(fullPath);
			add(fullSearchPath);
		}
	};
	public static List<String> yunYinShangURLs = new ArrayList<String>(){
		{
			add(yunYinShangPath);
			add(yunYinShangCSPath);
			add(yunYinShangGEOPath);
			add(yunYinShangGEOSearchPath);
			add(yunYinShangCSSearchPath);
		}
	};
	public static List<String> yinLianURLs = new ArrayList<String>(){
		{
			add(yinLianPath);
		}
	};
	public static List<String> gsURLs = new ArrayList<String>(){
		{
			add(ENTERPRISE_PATH);
		}
	};
	public static List<String> jrwwURLs = new ArrayList<String>(){
		{
			add(JRWW_PATH);
		}
	};
	
	
	public static List<String>  personalBorrowURLs = new ArrayList<String>(){
		{
			add(personalBorrowingPath);
			add(personalBorrowingSearchPath);
		}
	};
	
	public static List<String>  corporateLendingURLs = new ArrayList<String>(){
		{
			add(corporateLendingPath);
			add(corporateLendingSearchPath);
		}
	};
	
	
	
	public static List<String> FINANCIAL_STABLE_URL = new ArrayList<String>();
	static {
		FINANCIAL_STABLE_URL.add(JRWW_PATH);
	}
//	public static String ACTIVE = "class=\"active\"";
	
	public static String getConciseMenu(String path, String contestPath){
		if(conciseURLs.contains(path)){
			return "<li role=\"presentation\" class=\"active\" ><a href=\"#\" class=\"tab-sm\">基<br>础<br>查<br>询</a></li>";
		}else{
			return "<li role=\"presentation\" ><a href=\""+contestPath+menuURL.get(conciseCode)+"\" class=\"tab-sm\">基<br>础<br>查<br>询</a></li>";
		}
	}
	
	/**
	 * 个人借贷查询菜单
	 * @param path
	 * @param contestPath
	 * @return
	 */
	public static String getPersonalBorrowingMenu(String path, String contestPath){
		if(personalBorrowURLs.contains(path)){
			return "<li role=\"presentation\" class=\"active\" ><a href=\"#\" class=\"tab-sm tab-lt\">个<br>人<br>借<br>贷<br>查<br>询</a></li>";
		}else{
			return "<li role=\"presentation\" ><a href=\""+contestPath+menuURL.get(personalBorrowingCode)+"\" class=\"tab-sm  tab-lt\">个<br>人<br>借<br>贷<br>查<br>询</a></li>";
		}
	}
	
	/**
	 * 企业借贷查询菜单
	 * @param path
	 * @param contestPath
	 * @return
	 */
	public static String getCorporateLendingMenu(String path, String contestPath){
		if(corporateLendingURLs.contains(path)){
			return "<li role=\"presentation\" class=\"active\" ><a href=\"#\" class=\"tab-sm tab-lt\">企<br>业<br>借<br>贷<br>查<br>询</a></li>";
		}else{
			return "<li role=\"presentation\" ><a href=\""+contestPath+menuURL.get(corporateLendingCode)+"\" class=\"tab-sm  tab-lt\">企<br>业<br>借<br>贷<br>查<br>询</a></li>";
		}
	}
	
	public static String getFullMenu(String path, String contestPath){
		if(fullURLs.contains(path)){
			return "<li role=\"presentation\" class=\"active\"><a href=\"#\" class=\"tab-sm\">详<br>版<br>查<br>询</a></li>";
		}else{
			return "<li role=\"presentation\"><a href=\""+contestPath+menuURL.get(fullCode)+"\" class=\"tab-sm\">详<br>版<br>查<br>询</a></li>";
		}
	}
	
	public static String getYunYinShangMenu(String path, String contestPath){
		if(yunYinShangURLs.contains(path)){
			return "<li role=\"presentation\" class=\"active\"><a href=\"#\" class=\"tab-sm tab-lt\">运<br>营<br>商<br>数<br>据<br>查<br>询</a></li>";
		}else{
			return "<li role=\"presentation\" ><a href=\""+contestPath+menuURL.get(yunYinShangCode)+"\" class=\"tab-sm tab-lt\">运<br>营<br>商<br>数<br>据<br>查<br>询</a></li>";
		}
	}
	
	public static String getYinLianMenu(String path, String contestPath){
		if(yinLianURLs.contains(path)){
			return "<li role=\"presentation\" class=\"active\" ><a href=\"#\" class=\"tab-sm tab-lt\">银<br>联<br>信<br>息<br>查<br>询</a></li>";
		}else{
			return "<li role=\"presentation\"><a href=\""+contestPath+menuURL.get(yinLianCode)+"\" class=\"tab-sm tab-lt\">银<br>联<br>信<br>息<br>查<br>询</a></li>";
		}
	}
	
	public static String getGSMenu(String path, String contestPath){
		if(gsURLs.contains(path)){
			return "<li role=\"presentation\" class=\"active\" ><a href=\"#\" class=\"tab-sm tab-lt\">企<br>业<br>信<br>息<br>查<br>询</a></li>";
		}else{
			return "<li role=\"presentation\"><a href=\""+contestPath+menuURL.get(QYCode)+"\" class=\"tab-sm tab-lt\">企<br>业<br>信<br>息<br>查<br>询</a></li>";
		}
	}
	
	public static String getFSMenu(String path, String contestPath){
		if(jrwwURLs.contains(path)){
			return "<li role=\"presentation\" class=\"active\" ><a href=\"#\" class=\"tab-sm\">金<br>融<br>维<br>稳</a></li>";
		}else{
			return "<li role=\"presentation\"><a href=\""+contestPath+menuURL.get(JRWW_CODE)+"\" class=\"tab-sm\">金<br>融<br>维<br>稳</a></li>";
		}
	}
	
	public static Map<String, String> getAllMenu(String path, String contestPath){
//    	String manuConcise = getConciseMenu(path, contestPath);
    	String manuFull = getFullMenu(path, contestPath);
    	String manuYunYinShang = getYunYinShangMenu(path, contestPath);
    	String manuYinLian = getYinLianMenu(path, contestPath);
    	String manuGS = getGSMenu(path, contestPath);
    	String menuFS = getFSMenu(path, contestPath);
    	Map<String, String> manuMap = new HashMap<String, String>();
//    	manuMap.put(MenuConstant.conciseCode, manuConcise);
    	manuMap.put(MenuConstant.personalBorrowingCode, getPersonalBorrowingMenu(path, contestPath));
    	manuMap.put(MenuConstant.corporateLendingCode, getCorporateLendingMenu(path, contestPath));
    	//manuMap.put(MenuConstant.fullCode, manuFull);
    	manuMap.put(MenuConstant.yunYinShangCode, manuYunYinShang);
    	manuMap.put(MenuConstant.yinLianCode, manuYinLian);
    	manuMap.put(MenuConstant.QYCode, manuGS);
    	manuMap.put(MenuConstant.JRWW_CODE, menuFS);
    
        return manuMap;
	}
	
}
