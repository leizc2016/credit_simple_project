<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <div role="tabpanel" class="tab-pane active" id="tab-c">
        <form class="form-inline search-bar" id="form_enterprise" action="${ctx}/creditreport/enterpriseSearch" onsubmit="submitForm()">
     	   <div class="form-group offset-r30" style=“margin-top:10px;margin-bottom:10px;width:50%;”>
     	   
     	     <label for="">工商注册号：</label>
             	<input placeholder="工商注册号" name="registerNum" id="qy_registerNum" class="form-control input-sm input-xs required" value="${registerNum}" type="text">
             </div><br>
             
             <div class="form-group offset-r30" style=“margin-top:10px;margin-bottom:10px;width:50%;”>
             <label for="">身份证号码：</label>
             	<input placeholder="身份证号码" name="idCardNum" id="yl_idCard" class="form-control input-sm input-xs isIdCardNo" value="${idCardNum}" type="text">
             </div><br>
           
           <div class="form-group offset-r30" style=“margin-top:10px;margin-bottom:10px;width:50%;”>
             <label for="">&#8195;法人姓名：</label>
             <input placeholder="法人姓名" name="fullName" id="yl_fullname" class="form-control input-sm input-xs" value="${fullname}" type="text">
           </div><br>
         
           <input type="hidden" name="lastRequestTimeKey"  value='5596e89f-70bc-11e5-a0f3-bae5d92fd22e'  >	
           <input type="button" class="btn btn-primary btn-sm btn-search"  style="margin-left:100px;" value="下一步" onclick="submitForm()"/>
  		 </form>
         </div>
         </div>
          <!-- /tab5 end -->
        </div>
        <div style="width:500px;height:600px;"></div>
        </div>
        </div>  
      <script type="text/javascript">
          if('${message_info}'){
        	  alert('${message_info}');
          }
          $(document).ready(function() {
  			$("#form_enterprise").validate();
  		  });
          
          var submited_flag = false;//重复提交标示
          function submitForm(){
	       	  if(!$("#form_enterprise").valid()){//表单验证
	       		  return;
	       	  }
	       	  if(!submitedFlag()){//是否已提交过了
	 	          document.getElementById('form_enterprise').submit();
	       	  }
          }
      </script>
</body>
</html>
