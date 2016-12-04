<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<title>海纳征信后台管理系统</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />

<link type="image/x-icon" href="${ctx}/static/images/favicon.ico" rel="shortcut icon">
<link href="${ctx}/static/bootstrap/3.3.1/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/jquery-validation/1.11.1/validate.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/styles/default.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.11.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.11.1/messages_bs_zh.js" type="text/javascript"></script>


</head>

<body>
	<div class="container">
		<div id="header">
	<div id="title">
	    <h1><a >海纳征信</a><small>--后台管理系统</small>
		</h1>
	</div>
</div>
		<div id="content">
			
<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">首次登录密码修改</h1>
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="tab-content">
					<form id="safForm" action="${ctx}/login/passsave"
						method="post" class="form-inline"  onsubmit="return validatePSW()">
						<fieldset>
							<div class="control-group">
								<label for="plainPassword" class="control-label">密码:</label>
								<div class="controls">
							 <input type="password" id="plainPassword" name="plainPassword"  class="form-control required" minlength="4" placeholder="密码"/>
								</div>
							</div>
							<div class="control-group">
								<label for="confirmPassword" class="control-label">确认密码:</label>
								<div class="controls">
									 <input type="password" id="confirmPassword" name="confirmPassword" class="form-control required" minlength="4" placeholder="再次输入密码"  equalTo="#plainPassword" />
								</div>
							</div>
							<div class="form-actions">
							                                       <input id="submit_btn" class="btn btn-primary" type="submit"
									value="提交" />&nbsp;
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

		</div>
		<%@ include file="/WEB-INF/layouts/footer.jsp"%>
	</div>
	<script src="${ctx}/static/bootstrap/3.3.1/js/bootstrap.min.js" type="text/javascript"></script>
</body>
</html>