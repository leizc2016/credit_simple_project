<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>
<head>
</head>
<body>
	<c:set var="ctx" value="${pageContext.request.contextPath}" />
	<div class="modal nav-modal" style="display: block;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div class="modal-line"></div>
					<div class="nav-content clearfix">
						<shiro:hasAnyRoles name="admin,creditreport">
							<a href="${ctx}/creditreport/history"> <span
								class="icon icon-nav1"></span> <span>信贷经理</span>
							</a>
						</shiro:hasAnyRoles>
						<shiro:hasAnyRoles name="admin,fund">
							<a class="orange-bg" href="${ctx}/funds"> <span
								class="icon icon-nav2"></span> <span>资金管理员</span>
							</a>
						</shiro:hasAnyRoles>
						<shiro:hasAnyRoles name="admin,account">
							<a href="${ctx}/user/user_management" class="orange-bg"> <span
								class="icon icon-nav3"></span> <span>用户管理员</span>
							</a>
						</shiro:hasAnyRoles>
						<shiro:hasAnyRoles name="admin,insdata">
							<a href="${ctx}/insdata"> <span class="icon icon-nav4"></span>
								<span>数据上传员</span>
							</a>
						</shiro:hasAnyRoles>
					</div>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->

	<div id='example' class='modal hide fade in' style='display: none;'>
		<div class='modal-header'>
			<a class='close' data-dismiss='modal'>X</a>
			<h3>我是拟态框的头部</h3>
		</div>
		<div class='modal-body'>
			<h4>我是拟态框的中间部分</h4>
			<p>我是描述部分</p>
		</div>
		<div class='modal-footer'>
			<a href='#' class='btn btn-success'>成功</a> <a href='#' class='btn'
				data-dismiss='modal'>关闭</a>
		</div>
	</div>
	
</body>
</html>