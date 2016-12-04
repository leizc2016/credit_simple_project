package com.pactera.pds.u2.commerce.security;

import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;


/**
 * 判断当前用户是否具有给定一组角色中的任意一个角色
 * 
 * @author foy
 *
 */
public class AnyRoleAuthorizationFilter extends AuthorizationFilter {
    
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        
        Subject subject = getSubject(request, response);
        String[] rolesArray = (String[]) mappedValue;
        
        if (rolesArray == null || rolesArray.length == 0) {
            // no roles specified, so nothing to check - allow access.
            return true;
        }
        
        Set<String> roles = CollectionUtils.asSet(rolesArray);
        // check uer has any roles
        for (String role : roles) {
            if (subject.hasRole(role)) {
                return true;
            }
        }
        return false;
    }
    
}
