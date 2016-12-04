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
                <img alt="" src="${ctx}/static/images/comments.jpg">
                </div>
              </div>

            </div>
          </div><!-- /tab2 end  -->
        </div>
        <div class="text-left">
        	<button class="btn btn-primary btn-sm btn-search" onclick='document.execCommand("print")' >打 &nbsp; 印</button>
        </div>
      </div>     
      <script type="text/javascript">
          if('${message_info}'){
        	  alert('${message_info}');
          }
          
          $(document).ready(function() {
        	  $("#financial_form_id").validate();
          });
        	
          var submited_flag = false;//重复提交标示
          function submitForm(){
        	  if(!$("#financial_form_id").valid()){//表单验证
        		  return;
        	  }
        	  if(!submitedFlag()){//是否已提交过了
        		  $("#riskDetail").hide();
	        	  document.getElementById('financial_form_id').submit();
        	  }
          }
          function riskScan(){
        	  $("#riskDetail").hide();
        	  var arrayObj = new Array();
        	  $("td[id='name']").each(function(index){
        		  arrayObj.push($(this).text());
        	  });
        	  if(arrayObj.length == 0){
        		  alert("未选择风险扫描公司!");
        		  return false;
        	  }
        	  $.ajax({
 				 type:"GET",
 				 url:"${ctx}/creditreport/getDishNums",
 				 data:{"keys":arrayObj.join(',')},
 				 dataType:"json",
 				 success:function(res){
 					 $("input[id='regId']").each(function(index){
 						 var rid = Math.floor(Math.random()*4+1)
 						 var url = "${ctx}/creditreport/enterpriseSearch?registerNum=" + $(this).val() +"&idCardNum=#dishPersonInfo";
 						 if(res[index] != 0){
 							$("#dish_" + $(this).val()).html("失信被执行<a target='_blank' style='color:red' href=" + url + ">" + res[index] + "</a>次");
 						 }else{
 							$("#dish_" + $(this).val()).html("失信被执行<a target='_blank' href=" + url + ">" + res[index] + "</a>次");
 						 }
 						 $("#risk_" + $(this).val()).html("高风险指标<a style='color:red' onClick='showRiskDetail(" + rid + "," + $(this).val() + ")'>" + rid + "</a>个");
 		        	 });
 				 },
 				 error:function(){
 					 alert("风险扫描出错!");
 				 }
 			});
        } 
        
        function showRiskDetail(rdNum,regId,isExistDishPersons){
        	$("#companyname").html($('.cname_'+regId).html());
        	for(var j=1;j<=5;j++){
        		$("#td"+j).text('否');
        	}
        	if(isExistDishPersons == 1){
        		$("#td5").text('是');
        		rdNum = rdNum - 1;
        	}
        	for(var i=1;i<=rdNum;i++){
        		$("#td"+i).text('是');
        	}
        	$("#riskDetail").show();
        	return false;
        }
      </script>  
</body>
</html>
