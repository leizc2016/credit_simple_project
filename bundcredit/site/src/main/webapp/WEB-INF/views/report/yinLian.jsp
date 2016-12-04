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
        <div role="tabpanel" class="tab-pane active padding0" id="tab2">
        <div role="tabpanel" class="tabs-left sub-content">
        <ul class="nav nav-tabs" role="tablist">
            ${manuAllowed}
        </ul>
        <div role="tabpanel" class="tab-pane active" id="tab-c">
        <form class="form-inline search-bar" id="form1" action="${ctx}/creditreport/checkUserInputForYL">
     	   <div class="form-group offset-r30" style=“margin-top:10px;margin-bottom:10px;width:50%;">
             <label for="">身份证号码：</label>
             <input placeholder="身份证号码" name="idCardNum" id="yl_idCard" class="form-control input-sm input-xs required isIdCardNo" value="${idCardNum}" type="text">
           </div><br>
           
           <div class="form-group offset-r30" style=”margin-top:10px;margin-bottom:10px;width:50%;">
             <label for="">银行卡号码：</label>
             <input placeholder="请输入银行卡号码" name="bankCardNum" id="yl_card" class="form-control input-sm input-xs required digits" value="${bankCardNum}" type="text">
           </div><br>
           
           <div class="form-group offset-r30" style=”margin-top:10px;margin-bottom:10px;width:50%;">
             <label for="">&#8195;&#8195;授权码：</label>
             <input placeholder="请输入授权码" name="authCode" id="yl_authCode" class="form-control input-sm input-xs required" value="${authCode}" type="text">
           </div><br>
           <input type="hidden" name="lastRequestTimeKey"  value='dc5fe29d-70af-11e5-a0f3-bae5d92fd22e'>	
           <input type="button" class="btn btn-primary btn-sm btn-search"  style="margin-left:100px;" value="下一步" onclick = "submitForm()" />
<!--            <input type="submit" class="btn btn-primary btn-sm btn-search"  style="margin-left:100px;" value="下一步"/> -->
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
      	
	      var submited_flag = false;//重复提交标示
	      function submitForm(){
	      	  if(!$("#form1").valid()){//表单验证
	      		  return;
	      	  }
	      	  if(!submitedFlag()){//是否已提交过了
		          document.getElementById('form1').submit();
	      	  }
	      }
      </script>  
</body>
</html>
