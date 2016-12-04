package com.pactera.pds.u2.commerce.interceptor;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


/**
 * MyBatis生成Paginator参数
 */
public class PageListAttrHandlerInterceptor extends HandlerInterceptorAdapter {
    public static String PAGINATOR="commonPaginator";
    public boolean preHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o) throws Exception {
        return true;
    }
    
    public void postHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView)
        throws Exception {
        Enumeration enumeration = request.getAttributeNames();
        while (enumeration.hasMoreElements()) {
            Object element = enumeration.nextElement();
            if (element instanceof String) {
                String name = (String) element;
                Object attr = request.getAttribute(name);
                if (attr instanceof PageList) {
                    PageList pageList = (PageList) attr;
                   
                    request.setAttribute(name + "Paginator", pageList.getPaginator());
                    request.setAttribute(PAGINATOR, pageList.getPaginator());
                }
            }
        }
        if (modelAndView != null) {
            Map<String, Object> model = modelAndView.getModel();
            Map<String, Object> newModel = new HashMap<String, Object>();
            for (Map.Entry<String, Object> item : model.entrySet()) {
                Object attr = item.getValue();
                if (attr instanceof PageList) {
                    PageList pageList = (PageList) attr;
                    
                    newModel.put(item.getKey() + "Paginator", pageList.getPaginator());
                    newModel.put(PAGINATOR, pageList.getPaginator());
                }
            }
            modelAndView.addAllObjects(newModel);
        }
    }
    
    public void afterCompletion(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o, Exception e)
        throws Exception {
        
    }
}
