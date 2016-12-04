<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<title></title>
</head>

<body>
	<div class="container wrap">

		<div role="tabpanel" class="query-content">

			<!-- Nav tabs -->
			<ul class="nav nav-tabs" role="tablist">
				<li role="presentation" class="active tab1"><a href="#tab1"
					aria-controls="tab1" role="tab" data-toggle="tab">个人中心</a></li>
			</ul>

			<!-- Tab panes -->
			<div class="tab-content">
				<div role="tabpanel" class="tab-pane active" id="tab1">
					<div class="offset-t30 offset-l30">
						<h1 class="gray user-title">修改密码</h1>
						<div class="form-group-user">
							<span class="icon-sm icon-account"></span> <input
								name="loginName" value="${bcuser.loginName}" type="text" class="form-control"
								placeholder="用户名"> <span class="erro" hidden="true">错误信息</span>
						</div>
						<div class="form-group-user">
							<span class="icon-sm icon-password"></span> <input
								name="plainPassword" type="password" class="form-control"
								placeholder="旧密码">
						</div>
						<div class="form-group-user">
							<span class="icon-sm icon-key"></span> <input name="password"
								type="password" class="form-control" placeholder="新密码">
						</div>
						<div class="form-group-user">
							<span class="icon-sm icon-key"></span> <input name="passworEnter"
								type="password" class="form-control" placeholder="再次输入新密码">
						</div>
						<div class="offset-t20">
							<button id ="submit" class="btn btn-warning btn-sm btn-confirm btn-wlg">确定</button>
						</div>
					</div>
				</div>
				<!-- /tab1 end -->
			</div>

		</div>
		<!-- /tab end -->

	</div>
	<!-- /container -->
	
	<div class="footer">沪ICP备15026862</div>
	
		<!-- /* 
			onload = function() {
				var trs = $(".table > tbody > tr")
				for (var i = 0; i < trs.length; i++) {
					if (i % 2 == 0)
						trs[i].className = "tr1";
					else
						trs[i].className = "tr2";
				}
			} */ -->
</body>
</html>