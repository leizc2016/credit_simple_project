<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!-- Static navbar -->
    <nav class="navbar navbar-default navbar-static-top">
      <div class="container">
        <div class="navbar-header">
          <a class="navbar-brand" href="#">外滩征信</a>
          <span class="font-top">小微金融互联网征信系统</span>
        </div>
        <div id="navbar">
          <ul class="nav navbar-nav navbar-right">
          <shiro:user>
          	<shiro:hasAnyRoles name="admin,creditreport">
          		<li><a href="${ctx}/creditreport/history">征信查询</a></li>
          	</shiro:hasAnyRoles>
            <shiro:hasAnyRoles name="admin,insdata">
            	<li><a href="${ctx}/insdata">数据上传</a></li>
            </shiro:hasAnyRoles>
          	<shiro:hasAnyRoles name="admin,fund">
            	<li><a href="${ctx}/funds">资金管理</a></li>
            </shiro:hasAnyRoles>
            <shiro:hasAnyRoles name="admin,account">
            	<li><a href="${ctx}/user/user_management">用户管理</a></li>
            </shiro:hasAnyRoles>
            <li><a class="font12 gray offset-l30" href="${ctx}/userperson/personmanagerpage"><span class="icon icon-top-user"></span>个人账户</a></li>
            <li><a class="font12 gray" href="${ctx}/logout"><span class="icon icon-top-logout"></span>
            退出</a></li>
           </shiro:user>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
    <div class="banner-blue"></div>

