<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%response.setStatus(200);%>

<!DOCTYPE html>
<html>
<head>
	<title></title>
</head>

<body>
	<h2>无权访问</h2>
	<p>Oh，超人，您是怎么进来的？<a href="<c:url value="/"/>">返回首页</a></p>
</body>
</html>