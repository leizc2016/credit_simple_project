<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>任务管理</title>
</head>

<body>
	<form id="inputForm" action="${ctx}/car/${action}" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${car.id}"/>
		<fieldset>
			<legend><small>管理汽车</small></legend>
			<div class="control-group">
				<label for="task_title" class="control-label">汽车名称:</label>
				<div class="controls">
					<input type="text" id="car_title" name="title"  value="${car.title}" class="input-large required" minlength="3"/>
				</div>
			</div>	
			<div class="control-group">
				<label for="description" class="control-label">汽车描述:</label>
				<div class="controls">
					<textarea id="description" name="description" class="input-large">${car.description}</textarea>
				</div>
			</div>	
			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit" value="提交"/>&nbsp;	
				<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
			</div>
		</fieldset>
	</form>
	<script>
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#car_title").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate();
		});
	</script>
</body>
</html>
