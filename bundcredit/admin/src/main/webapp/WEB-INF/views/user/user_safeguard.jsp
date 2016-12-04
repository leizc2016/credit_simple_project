<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!--   <div role="tabpanel" class="query-content"> -->

<!--   
        <ul class="nav nav-tabs" role="tablist">
          <li role="presentation" class="active tab1"><a href="#tab1" aria-controls="tab1" role="tab" data-toggle="tab">个人中心</a></li>
        </ul>
 -->
<!-- Tab panes -->
<!-- <form id="safForm" action="#"> -->
<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">机构用户管理</h1>
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
     </c:if>
	<!-- /.row -->
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="tab-content">
				<div class="offset-t30 offset-b20">
              <a class="btn btn-primary btn-sm offset-r10" href="${ctx}/user_Safeguard/userpage/${insCode}" >创建账户</a>
              <!--<a class="btn btn-primary btn-sm">账户权限管理</a>-->
            </div>
			<table class="table table-striped table-bordered table-condensed">
              <thead>
                <tr>
                  <th>姓名</th>
                  <th>登录名</th>
                  <th>角色</th>
                  <th>创建时间</th>
                  <!--  <th>UUID</th> -->
                  <th>禁用账户</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach items="${bcusers}" var="bc">
                <tr>
                  <td><a href="${ctx}/user_Safeguard/update/${bc.id}">${bc.name}</a></td>
                  <td>${bc.loginName}</td>
                  <td>${bc.roles}</td>
                  <td><fmt:formatDate value="${bc.registerDate}" pattern="yyyy-MM-dd  HH:mm:ss" /></td>
                  <!-- <td>${bc.uuid}</td> -->
                  <td><%-- <a class="btn btn-success btn-xs" href="${ctx}/user_Safeguard/delete/${bc.id}">删除</a> --%> 
                   <c:if test="${bc.allow==1}">
                  <a class="btn btn-success btn-xs" href="${ctx}/user_Safeguard/forbidden/${bc.id}">禁用账户</a>
                  </c:if>
                   <c:if test="${bc.allow==0}">
              		   <a class="btn btn-success btn-xs" href="${ctx}/user_Safeguard/forbidden/${bc.id}">启用账户</a>
                  </c:if> 
                  </td>
                </tr>	
                </c:forEach>
              </tbody>
            </table>
					<!-- /.panel-body -->
				</div>
				<!-- /.panel -->
			</div>
			<!-- /.col-lg-12 -->
		</div>
		<!-- /.row -->
	</div>
</div>

<script>
	$(document).ready(function() {
		$("#safForm").validate();
	});
</script>
