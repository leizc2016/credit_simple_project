<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    
	<title>外滩海纳-小微金融互联网征信系统<sitemesh:title/></title>
    <!-- Bootstrap CSS -->
    
    <link href="${ctx}/static/bc/plugin/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/static/bc/plugin/bootstrap/css/tab-fix.css" rel="stylesheet">
    <link href="${ctx}/static/bc/plugin/bootstrap/css/bootstrap.custom.css" rel="stylesheet">
    <!-- Custom styles -->
    <link href="${ctx}/static/bc/css/common.css" rel="stylesheet">
    <!-- 
     -->
    <link href="${ctx}/static/bc/css/user.css" rel="stylesheet">
	<sitemesh:head/>
    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="${ctx}/static/bc/plugin/bootstrap/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="${ctx}/static/bc/plugin/bootstrap/js/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="${ctx}/static/bc/plugin/bootstrap/js/html5shiv.min.js"></script>
      <script src="${ctx}/static/bc/plugin/bootstrap/js/respond.min.js"></script>
    <![endif]-->
    <link href="${ctx}/static/jquery-validation/1.11.1/validate.css" type="text/css" rel="stylesheet" />
     <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="${ctx}/static/bc/plugin/jquery/jquery.min.js"></script>
    <%--<script src="${ctx}/static/jquery/jquery-1.9.1.min.js"></script> --%>
    <script src="${ctx}/static/bc/plugin/bootstrap/js/bootstrap.min.js"></script>
    <script src="${ctx}/static/bc/plugin/bootstrap/js/ie10-viewport-bug-workaround.js"></script>
    <script src="${ctx}/static/jquery-validation/1.11.1/jquery.validate.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/jquery-validation/1.11.1/messages_bs_zh.js" type="text/javascript"></script>
    <script type="text/javascript">
    $(document).ready(function() {
    	jQuery.validator.addMethod("cellNum", function(value, element) {
    		 //return this.optional(element) || /^[\u0391-\uFFE5\w]+$/.test(value);
    		 return this.optional(element) || /^0?1[3|4|5|8][0-9]\d{8}$/.test(value);
    	}, "请输入正确手机号码");   
    	$.validator.addMethod("isIdCardNo", function (value, element) {
  	    	
  	        return this.optional(element) || isIdCardNo(value);
  	    }, "<font color='#E47068'>请输入正确的身份证号码</font>");
		});
    function submitedFlag(){
  	  if(!submited_flag){
  		  submited_flag = true;
  		  return false;
  	  }
  	  alert("请求已提交");
  	  return submited_flag;
    }
    </script>
</head>
 <body onload="">
    <jsp:include page="bcheader.jsp"></jsp:include>
    <div class="container wrap">
		<sitemesh:body/> 
    </div> <!-- /container -->
    <jsp:include page="bcfooter.jsp"></jsp:include>
   
    
  </body>
</html>