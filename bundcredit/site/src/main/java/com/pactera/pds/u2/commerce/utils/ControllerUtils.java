package com.pactera.pds.u2.commerce.utils;

import org.springframework.util.StringUtils;

import com.pactera.pds.u2.commerce.entity.PersonBasicInfo;


public class ControllerUtils {

	public static boolean noResultAtAll(PersonBasicInfo personBasicInfo) {
		if(personBasicInfo == null 
				|| StringUtils.isEmpty(personBasicInfo.getIdCardNum())){
			return true;
		}
		return false;
	}
}
