package com.pactera.pds.u2.commerce.interceptor;

import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.ui.Model;
import org.springside.modules.web.Servlets;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;


public class PageHelper {
    
    public static int DEFAULT_PAGE_NUMBER = 1;
    
    public static int DEFAULT_PAGE_SIZE = 15;
    
    //生成pageNumber,pageSize等分页数据和组合其他q_打头查询参数
    public static PageBounds composeFromRequest(ServletRequest request,Model model) {
        int pn = pageParm(request,"pn", DEFAULT_PAGE_NUMBER);
        int ps = pageParm(request,"ps", DEFAULT_PAGE_SIZE);
        PageBounds pb = new PageBounds(pn, ps);
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "q_");
        model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "q_"));
        return pb;
    }
    
    public static PageBounds composeFromRequest4site(ServletRequest request,Model model) {
        int pn = pageParm(request,"pn", DEFAULT_PAGE_NUMBER);
        int ps = pageParm(request,"ps", 10);
        PageBounds pb = new PageBounds(pn, ps);
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "q_");
        model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "q_"));
        return pb;
    }
    
    private static int pageParm(ServletRequest request,String name, int dn) {
        int n = dn;
        try {
            n = Integer.parseInt(request.getParameter(name));
        } catch (Exception e) {
        }
        return n;
    }
}
