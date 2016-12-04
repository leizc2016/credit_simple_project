package com.pactera.pds.u2.commerce.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.web.context.ContextLoader;


/**
 * 
 * @author foy
 *
 */
public class Beans {
    
    public static String webAppRootPath() {
        return ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/");
    }
    public static void setProperties(Object src,List<String> properties,Object[] vals){
        
    }
    public static void setProperty(Object src, String property, Object val) {
        try {
            PropertyUtils.setProperty(src, property, val);
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public static Object getProperty(Object src, String property) {
        Object val = null;
        try {
            val = PropertyUtils.getProperty(src, property);
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return val;
    }
}
