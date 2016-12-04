package com.pactera.pds.u2.commerce.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.pactera.pds.u2.commerce.entity.Institution;
import com.pactera.pds.u2.commerce.service.insdatamgr.InstitutionService;
import com.pactera.pds.u2.commerce.utils.MenuConstant;
import com.pactera.pds.u2.commerce.utils.Sessions;
public class SiteTabControlInterceptor extends HandlerInterceptorAdapter{
	public boolean preHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o) throws Exception {
        return true;
    }
	
	@Autowired
    private InstitutionService insService;
    
	
    public void postHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView)
        throws Exception {
    	String insCode = Sessions.insCode();
    	Institution institution = insService.getByInscode(insCode);
    	String productCode = institution.getProductCode();
    	String []codes = productCode.split(",");
    	String tapAllowed = "<li class=\"tab1\" role=\"presentation\"><a href=\"javascript:alert('没有访问权限')\">征信查询</a></li>";
    	if(codes.length > 0 && !"".equals(codes[0])){
    		tapAllowed = "<li class=\"tab1\" role=\"presentation\"><a href=\""+request.getContextPath()+MenuConstant.menuURL.get(codes[0])+"\">征信查询</a></li>";
    	}
    	modelAndView.addObject("tapAllowed", tapAllowed);
    }
    
    public void afterCompletion(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o, Exception e)
        throws Exception {
        
    }
}
