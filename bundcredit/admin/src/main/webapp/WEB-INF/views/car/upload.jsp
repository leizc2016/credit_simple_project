<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>任务管理</title>
</head>

<body>
	 <form  method="post" enctype="multipart/form-data" action="${ctx}/car/multipleSave">  
		<input type="file" value="上传0" name="file" />  
		<input type="file" value="上传1" name="file" />
		<input type="file" value="上传2" name="file" />
		<input type="file" value="上传3" name="file" />
		    <button type="submit">提交</button>  
	</form>  
	<script>
		$(document).ready(function() {
		});
	</script>
</body>
</html>
