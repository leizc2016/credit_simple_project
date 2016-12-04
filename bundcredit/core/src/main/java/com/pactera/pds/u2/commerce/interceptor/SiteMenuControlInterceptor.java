package com.pactera.pds.u2.commerce.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.pactera.pds.u2.commerce.entity.Institution;
import com.pactera.pds.u2.commerce.service.insdatamgr.InstitutionService;
import com.pactera.pds.u2.commerce.utils.MenuConstant;
import com.pactera.pds.u2.commerce.utils.Sessions;

public class SiteMenuControlInterceptor extends HandlerInterceptorAdapter{
	public boolean preHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o) throws Exception {
        return true;
    }
	
	@Autowired
    private InstitutionService insService;
    
    public void postHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView)
        throws Exception {
    	
    	String requestPath = request.getServletPath();
    	String contestPath = request.getContextPath();
    	Map<String, String> allMenuMap = MenuConstant.getAllMenu(requestPath, contestPath);
    	
    	String insCode = Sessions.insCode();
    	Institution institution = insService.getByInscode(insCode);
    	String productCode = institution.getProductCode();
    	String[] codes = processProductCodes(productCode);
//    	String []codes = productCode.split(",");
    	StringBuffer manuAllowed = new StringBuffer();
    	for(String code : codes){
    		if (allMenuMap.get(code) != null){
    			manuAllowed.append(allMenuMap.get(code));
    		}
    	}
    	modelAndView.addObject("manuAllowed", manuAllowed.toString());
    }
    
    public void afterCompletion(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o, Exception e)
        throws Exception {
        
    }
    
    private String[] processProductCodes(String codes){
    	codes = codes.replaceAll(MenuConstant.yunYinShangCSCode, MenuConstant.yunYinShangCode);
    	codes = codes.replaceAll(MenuConstant.yunYinShangGEOCode, MenuConstant.yunYinShangCode);
    	codes = codes.replaceAll(MenuConstant.yinLianZCCode, MenuConstant.yinLianCode);
    	String[] codeArr = codes.split(",");
    	String[] newArr = removeDuplicateElements(codeArr);
		return newArr;
    }

	private String[] removeDuplicateElements(String[] array) {

		List<String> list = new ArrayList<String>();
		for (int i = 0; i < array.length; i++) {
			if (!list.contains(array[i])) {
				list.add(array[i]);
			}
		}
		String[] newStr = list.toArray(new String[1]);

		return newStr;
	}
}
