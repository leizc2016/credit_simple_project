<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title></title>
</head>
<body>

    <div class="container wrap">

      <div role="tabpanel" class="query-content">

        <!-- Nav tabs -->
        <ul class="nav nav-tabs" role="tablist">
          <li role="presentation" class="active tab1"><a href="#tab1" aria-controls="tab1" role="tab" data-toggle="tab">用户管理</a></li>
        </ul>
        <!-- Tab panes -->
        <div class="tab-content">
        <c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
     	</c:if>
     	<c:if test="${not empty error_message}">
		<div id="message" class="alert alert-danger"><button data-dismiss="alert" class="close">×</button>${error_message}</div>
     	</c:if>
          <div role="tabpanel" class="tab-pane active" id="tab1">
            <div class="title-line">用户管理</div>
          <div class="offset-t30 offset-b20">
              <a class="btn btn-primary btn-sm offset-r10" href="${ctx}/user/user_management/userpage">创建账户</a>
            <!--   <a class="btn btn-primary btn-sm">账户权限管理</a> -->
            </div>
          <div class="clearfix table-title-lg">
              <div class="pull-left">账户信息</div>
            </div>
            <table class="table table-bordered text-center">
              <thead>
                <tr>
                  <th>姓名</th>
                  <th>用户名</th>
                  <th>角色</th>
                  <th>创建时间</th>
                  <!--<th>UUID</th>-->
                  <th>账户管理</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach items="${bcusers}" var="bc">
                <tr>
                  <td>${bc.name}</td>
                  <td>${bc.loginName}</td>
                  <td>${bc.roles}</td>
                  <td><fmt:formatDate value="${bc.registerDate}" pattern="yyyy-MM-dd  HH:mm:ss" /></td>
                  <!--<td>${bc.uuid }</td>-->
                  <td><a class="btn btn-success btn-xs" href="${ctx}/user/user_management/user_update/${bc.id}">账户修改</a>
                 <c:if test="${bc.allow==1}">
                  <a class="btn btn-success btn-xs" href="${ctx}/user/user_management/forbidden/${bc.id}">禁用账户</a>
                  </c:if>
                   <c:if test="${bc.allow==0}">
              		   <a class="btn btn-success btn-xs" href="${ctx}/user/user_management/forbidden/${bc.id}">启用账户</a>
                  </c:if> 
                  </td>
                </tr>
                </c:forEach>
              </tbody>
            </table>
          </div><!-- /tab1 end -->
        </div>

      </div><!-- /tab end -->

    </div> <!-- /container -->
</body>
</html>