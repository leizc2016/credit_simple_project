<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
			<h1 class="page-header">机构用户修改</h1>
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="tab-content">
					
					<form id="safForm" action="${ctx}/user_Safeguard/update" method="post"
					class="form-inline" onsubmit="return validatePSW()" >
					<input type="hidden" name="id" value="${user.id}" />
					<fieldset>
						<!-- <legend>
							<small>用户管理</small>
						</legend> -->
						<div class="control-group">
								<label for="loginName" class="control-label">登录名:</label>
								<div class="controls">
								<input type="text" value="${user.loginName}" name="loginName" class="form-control"
									disabled="disabled" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">姓名:</label>
							<div class="controls">
								<input type="text" id="name" name="name" value="${user.name}"
									class="form-control required" placeholder="用户名"/>
							</div>
						</div>
							<div class="control-group">
							<label class="control-label">权限:</label>
							<div class="controls">
								<select  id="roles" style="width: 175px;" name="roles"
									class="form-control required" >
									<option value="">请选择</option>
									<option value="admin">超级管理员</option>
									<option value="creditreport">报告查询</option>
									<option value="fund">资金管理</option>
									<option value="insdata">数据上传</option>
									<option value="account">用户管理</option>
									</select>
							</div>
						</div>
						<div class="control-group">
							<label for="plainPassword" class="control-label">密码:</label>
							<div class="controls">
								<input type="password" id="plainPassword" name="plainPassword"
									class="form-control"
									placeholder="密码" />
							</div>
						</div>
						<div class="control-group">
							<label for="confirmPassword" class="control-label">确认密码:</label>
							<div class="controls">
								<input type="password" id="confirmPassword"
									name="confirmPassword" class="form-control"
									equalTo="#plainPassword" placeholder="再次输入密码" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">注册日期:</label>
							<div class="controls">
								<span class="help-inline" style="padding: 5px 0px"><fmt:formatDate
										value="${user.registerDate}" pattern="yyyy年MM月dd日  HH时mm分ss秒" /></span>
							</div>
						</div>
						<div class="form-actions">
							<input id="submit_btn" class="btn btn-primary" type="submit"
								value="提交" />&nbsp; <input id="cancel_btn" class="btn"
								type="button" value="返回" onclick="history.back()" />
						</div>
					</fieldset>
				</form>
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
		$("#roles").val("${user.roles}");
		$("#safForm").validate();
	});
	function validatePSW(){
		var psw = $("#plainPassword").val();
		//alert(psw);
		var regex = new RegExp('(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{8,30}');
		if(!regex.test(psw)){
			alert('密码中必须包含大小写字母、数字、特称字符，至少8个字符，最多30个字符。');
			return false;
		}
		//console.log(regex.test('a123456-'));
		return true;
	}
</script>
