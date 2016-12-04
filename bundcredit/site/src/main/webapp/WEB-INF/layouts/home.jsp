<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

    <title>外滩海纳-小微金融互联网征信系统</title>

    <!-- Bootstrap CSS -->
    <link href="${ctx}/static/bc/plugin/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/static/bc/plugin/bootstrap/css/tab-fix.css" rel="stylesheet">
    <link href="${ctx}/static/bc/plugin/bootstrap/css/bootstrap.custom.css" rel="stylesheet">

    <!-- Custom styles -->
    <link href="${ctx}/static/bc/css/common.css" rel="stylesheet">
    <link href="${ctx}/static/bc/css/user.css" rel="stylesheet">
	<link href="${ctx}/static/jquery-validation/1.11.1/validate.css" type="text/css" rel="stylesheet" />
    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="${ctx}/static/bc/plugin/bootstrap/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="${ctx}/static/bc/plugin/bootstrap/js/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="${ctx}/static/bc/plugin/bootstrap/js/html5shiv.min.js"></script>
      <script src="${ctx}/static/bc/plugin/bootstrap/js/respond.min.js"></script>
    <![endif]-->
    <script src="${ctx}/static/bc/plugin/jquery/jquery.min.js"></script>
    <script src="${ctx}/static/jquery-validation/1.11.1/jquery.validate.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/jquery-validation/1.11.1/messages_bs_zh.js" type="text/javascript"></script>
 	<sitemesh:head/>
 	<style type="text/css">
 	/*  2015-07-27  */
 	body {overflow:hidden; background:#F7F7F7;}
 	.navbar,.footer {width:100%; position:absolute; left:0; bottom:0;}
 	.navbar {top:0;}
 	</style>
  </head>

  <body>

    <!-- Static navbar -->
    <nav class="navbar navbar-default navbar-static-top">
      <div class="container">
        <div class="navbar-header">
          <a class="navbar-brand" href="#">外滩征信</a>
          <span class="font-top">小微金融互联网征信系统</span>
        </div>
      </div>
    </nav>

    <div class="homepage">
      <div id="carousel-example-generic" class="carousel slide" data-ride="carousel" data-interval="30000">
        <!-- Indicators -->
        <ol class="carousel-indicators">
          <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
          <li data-target="#carousel-example-generic" data-slide-to="1"></li>
          <li data-target="#carousel-example-generic" data-slide-to="2"></li>
        </ol>

        <!-- Wrapper for slides -->
        <div class="carousel-inner" role="listbox">
          <div class="item active">
            <img src="${ctx}/static/bc/img/banner01.jpg" alt="..." >
          </div>
          <div class="item">
            <img src="${ctx}/static/bc/img/banner02.jpg" alt="..." >
          </div>
          <div class="item">
            <img src="${ctx}/static/bc/img/banner05.jpg" alt="..." >
          </div>
        </div>
      </div>
	<sitemesh:body/>
    </div>
    <jsp:include page="bcfooter.jsp"></jsp:include>

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="${ctx}/static/bc/plugin/bootstrap/js/bootstrap.min.js"></script>
    <script src="${ctx}/static/bc/plugin/bootstrap/js/ie10-viewport-bug-workaround.js"></script>
    <script type="text/javascript">
      onload=function(){
        var trs=$(".table > tbody > tr")
        for(var i=0;i<trs.length;i++)
        {
     	if(i%2==0)
                trs[i].className="tr1";
            else
              trs[i].className="tr2";
        }
      } 

    </script>
  </body>
</html>





