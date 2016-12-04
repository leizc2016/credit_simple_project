<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<html>
<head>
	<title></title>
	<script src="${ctx}/static/idcard_validation.js" type="text/javascript"></script>
</head>

<body>
	<div class="query-content" role="tabpanel">
        <!-- Nav tabs -->
        <ul role="tablist" class="nav nav-tabs">
          <li class="tab1 active" role="presentation"><a href="#">征信查询</a></li>
          <li class="tab2" role="presentation"><a href="${ctx}/creditreport/history">查询历史</a></li>
        </ul>
		<div style="text-align:right;"><a onclick="history.go(-1)">返 回</a></div>
        <!-- Tab panes -->
        <div class="tab-content">
        <div class="font-mask"></div>
          <div id="tab2" class="tab-pane active" role="tabpanel">
            <div class="tabs-left sub-content" role="tabpanel">

              <!-- Nav tabs -->
              <ul role="tablist" class="nav nav-tabs">
                   ${manuAllowed}
              </ul>

              <!-- Tab panes -->
              <div class="tab-content">
                <div id="tab-a" class="tab-pane active" role="tabpanel">
                <img alt="" src="${ctx}/static/images/warning.jpg">
                </div>
              </div>

            </div>
          </div><!-- /tab2 end  -->
          <div style="width:500px;height:400px;"></div>
        </div>
        
        <div class="text-left">
        	<button class="btn btn-primary btn-sm btn-search" onclick='document.execCommand("print")' >打 &nbsp; 印</button>
        </div>
      </div>
</body>
</html>
