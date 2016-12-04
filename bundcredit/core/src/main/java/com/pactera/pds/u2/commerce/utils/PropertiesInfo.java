package com.pactera.pds.u2.commerce.utils;


//@Component("propertiesInfo")
public class PropertiesInfo {

    private static boolean uatFlag;
    private static String apiUrl;
    private static String ylzcApiUrl;
    
    public static boolean isUatFlag() {
        return uatFlag;
    }

    
    private static void setUatFlag(boolean flag) {
        uatFlag = flag;
    }


	public static String getApiUrl() {
		return apiUrl;
	}


	public static void setApiUrl(String apiUrl) {
		PropertiesInfo.apiUrl = apiUrl;
	}


	public static String getYlzcApiUrl() {
		return ylzcApiUrl;
	}


	public static void setYlzcApiUrl(String ylzcApiUrl) {
		PropertiesInfo.ylzcApiUrl = ylzcApiUrl;
	}
    
    
	
}
