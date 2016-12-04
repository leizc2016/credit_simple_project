<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
     
      <div class="homepage-login">
      <form id="loginForm" action="${ctx}/login" method="post">
        <h2 class="white user-title">请登陆</h2>
        <c:set var="errorCode" value="${requestScope.shiroLoginFailure}"/>
        <c:choose>
        	<c:when test="${'org.apache.shiro.authc.AccountException' eq errorCode}">
        		<span class="error">账户已被禁用，请联系管理员！</span>
        	</c:when>
        	<c:when test="${errorCode != null}">
        	  	<span class="error">登录失败，请重试.</span>
        	</c:when>
        	<c:otherwise></c:otherwise>
        </c:choose>
               
        <div class="form-group-user">
          <span class="icon-sm icon-account"></span>
          <input type="text" id="username" name="username"  value="" class="form-control required" placeholder="用户名"/>
        </div>
        <div class="form-group-user">
          <span class="icon-sm icon-password"></span>
          <input type="password" id="password" name="password" class="form-control required" minlength="4" placeholder="密码"/>
        </div>
        <div class="offset-t20 clearfix">
        <!-- 
          <label class="pull-left remember white"><input type="checkbox" id="rememberMe" name="rememberMe"><span>Remember me</span></label>
         -->
          <a class="btn btn-primary btn-sm btn-confirm pull-right" id="loginBtn">确定<span class="icon icon-arrow-b offset-l10"></span></a>
        </div>
        </form>
        <script>
		$(document).ready(function() {
			$("#loginBtn").on("click",function(){$("#loginForm").submit();});
			$("#loginForm").validate();
		});
	</script>
      </div>