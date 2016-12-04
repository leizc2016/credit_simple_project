/**
 * 
 */
package com.pactera.pds.u2.commerce.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * 
 * 获取spring容器，以访问容器中定义的其他bean
 * 
 * @author dsdeng
 */
@Component
@Scope("prototype")
public class SpringContextUtil implements ApplicationContextAware {
    
    private static ApplicationContext applicationContext;
    
    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     * 
     * @param applicationContext
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextUtil.applicationContext = applicationContext;
    }
    
    /**
     * @return ApplicationContext
     */
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    
    /**
     * 获取对象 这里重写了bean方法，起主要作用
     * 
     * @param name
     * @return Object 一个以所给名字注册的bean的实例
     * @throws BeansException
     */
    public Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
    }
    
}
