<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
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
			<h1 class="page-header">后台用户创建</h1>
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="tab-content">
					<form id="safForm" action="${ctx}/admin/user/save"
						method="post" class="form-inline"  onsubmit="return validatePSW()">
						<fieldset>
							<div class="control-group">
								<label for="loginName" class="control-label">登录名:</label>
								<div class="controls">
									<input type="text" id="loginName" name="loginName"
										class="form-control required" minlength="3" />
								</div>
							</div>
							<div class="control-group">
								<label for="name" class="control-label">姓名:</label>
								<div class="controls">
									<input type="text" id="name" name="name" 
										class="form-control required"  />
								</div>
							</div>
							<div class="control-group">
							<label class="control-label">权限:</label>
							<div class="controls">
								<select  id="roles" style="width: 175px;" name="roles"
									class="form-control required" >
									<option value="">请选择</option>
									<option value="admin">超级管理员</option>
									<option value="commercial">商户管理</option>
									<option value="financial">财务管理</option>
									<option value="examine">数据审核</option>
									</select>
							</div>
						</div>
							<div class="control-group">
								<label for="plainPassword" class="control-label">密码:</label>
								<div class="controls">
									<input type="password" id="plainPassword" name="plainPassword"
										class="form-control required" />
								</div>
							</div>
							<div class="control-group">
								<label for="confirmPassword" class="control-label">确认密码:</label>
								<div class="controls">
									<input type="password" id="confirmPassword"
										name="confirmPassword" class="form-control required"
										equalTo="#plainPassword" />
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
