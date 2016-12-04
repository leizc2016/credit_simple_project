<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
	<title></title>
</head>

<body>
	<div class="query-content" role="tabpanel">
        <!-- Nav tabs -->
        <ul role="tablist" class="nav nav-tabs">
          <li class="tab1 active" role="presentation"><a href="#">征信查询</a></li>
          <li class="tab2" role="presentation"><a href="${ctx}/creditreport/history">查询历史</a></li>
        </ul>

        

        <!-- Tab panes -->
        <div class="tab-content">
		<div class="font-mask"></div>
        <div role="tabpanel" class="tab-pane active padding0" id="tab2">
        <div role="tabpanel" class="tabs-left sub-content">
        <ul class="nav nav-tabs" role="tablist">
            ${manuAllowed}
        </ul>
         <div role="tabpanel" class="tab-pane active" id="tab-c">
          <!-- <div class="search-wrap" style="background:none;"> -->
        <form class="form-inline search-bar" id="form1" action="${ctx}/creditreport/selectISPProvider">
           <!-- <div class="offset-t30 offset-l30">
              <h1 class="gray user-title">手机号及授权码输入</h1>
              <div class="form-group-user">
                <span class="icon-sm icon-account"></span>
                <input type="text" id=cellNum name=cellNum class="form-control required cellNum" placeholder="手机号">
              </div>
              <div class="form-group-user">
                <span class="icon-sm icon-password"></span>
                <input type="text" class="form-control required" id=authCode name=authCode placeholder="授权码">
              </div>
              <div class="offset-t20">
                <a class="btn btn-warning btn-sm btn-confirm btn-wlg" onclick="goTo()">确定</a>
              </div>
            </div> -->
            	  <div class="form-group offset-r30" style=“margin-top:10px;margin-bottom:10px;width:50%;">
                    <label for="">手机号码：</label>
                    <input placeholder="手机号" name="cellNum" id="cellNum" class="form-control required input-sm input-xs cellNum" value="${bcHisYYSGEO.callNum}" type="text">
                  </div><br>
                  
                  <div class="form-group offset-r30" style=”margin-top:10px;margin-bottom:10px;">
                    <label for="">&#8195;授权码：</label>
                    <input placeholder="授权码" name="authCode" id="authCode" class="form-control required input-sm input-xs" value="${bcHisYYSGEO.authCode}" type="text">
                  </div><br>
                  <input type="submit" class="btn btn-primary btn-sm btn-search"  style="margin-left:100px;" value="下一步"/>
            </form>
          </div>
          </div>
          <!-- /tab5 end -->
        </div>
        <div style="width:500px;height:600px;"></div>
        </div></div>   
      <script type="text/javascript">
          if('${message_info}'){
        	  alert('${message_info}');
          }
          $(document).ready(function() {
  			$("#form1").validate();
  		});
      </script>  
</body>
</html>
