<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
	<title></title>
	<script language="javascript" src="${ctx}/static/jquery/jquery-1.6.2.js"></script>
	<script language="javascript" src="${ctx}/static/jquery/jquery.jqprint-0.3.js"></script>
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
        <div role="tabpanel" class="tab-pane active padding0" id="tab2">
        <div role="tabpanel" class="tabs-left sub-content">
        <ul class="nav nav-tabs" role="tablist">
            ${manuAllowed}
        </ul>
        <div class="tab-content">
        <div class="font-mask"></div>
          <div role="tabpanel" class="tab-pane active" id="tab-c">
              <div class="search-wrap">
                <form class="form-inline search-bar" id="form1" action="" method="post">
                  <div class="rows offset-b20">
                    <div class="form-group offset-r30">
                      <label>手机号码：</label>
                      <label style="font-weight: 100;">${bcHisYYSGEO.callNum}</label>
                      <input type="hidden" name="callNum" value="${bcHisYYSGEO.callNum}" />
                    </div>
                    <div class="form-group form-group-lt">
                      <label class="lt">机构授权码：</label>
                      <span style="line-height: 25px;">${bcHisYYSGEO.authCode}</span>
                      <input type="hidden" name="authCode" value="${bcHisYYSGEO.authCode}" />
                    </div>
                  </div>
                  <div class="rows offset-b20">
                    <div class="form-group offset-r30">
                      <label>姓名：</label>
                      <input placeholder="" name="cellOwner" id="cellOwner" class="form-control input-sm input-xs" maxlength="20" value="${cellOwner}" type="text">
                    </div>
                    <div class="form-group offset-r30" style="width: 40%;">
                      <label class="lt">关联手机号码：</label>
                      <input placeholder="" name="contactCell" id="contactCell" class="form-control input-sm input-xs cellNum" value="${contactCell}" type="text">
                    </div>
                  </div>
                  <div class="text-center">
                    <input type="button" value="查 &nbsp; 询" class="btn btn-primary btn-sm btn-search" onclick = "onSearch()" />&nbsp; &nbsp; &nbsp; &nbsp;
                    <button class="btn btn-primary btn-sm btn-search" onclick="onReturn()" >返 &nbsp; 回</button>
                  </div>
                  <input type="hidden" name="lastRequestTimeKey"  value='073bfd5e748c409aa964ab772a97ffff'  >	
                </form>
              </div>
              <div class="clearfix">
                <div class="title-line offset-b20 offset-t30">查询内容</div>
                <div class="table-title-xs"> <span>基础信息</span> </div>
                <table class="table table-bordered table-xs">
                  <tbody>
                    <tr>
                      <td>实名匹配</td>
                      <td>${bcHisYYSGEO.isMatch}</td>
                    </tr>
                    <tr>
                      <td>在网时间</td>
                      <td>${bcHisYYSGEO.netTime}</td>
                    </tr>
                    <tr>
                      <td>消费档次</td>
                      <td>${bcHisYYSGEO.spendingLevel}</td>
                    </tr>
                    <!--  <tr>
                      <td>工作地址</td>
                      <td>${geo.office}</td>
                    </tr>
                    <tr>
                      <td>家庭地址</td>
                      <td>${geo.rest}</td>
                    </tr>-->
                    <tr>
                      <td>通话频率</td>
                      <td>${bcHisYYSGEO.contactRate}</td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div><!-- /tab2 end -->
            
        </div></div></div></div>
        <div class="text-left">
        	<button class="btn btn-primary btn-sm btn-search" onclick='print();' >打 &nbsp; 印</button>
        </div>
      </div>     
      <script type="text/javascript">
          if('${message_info}'){
        	  alert('${message_info}');
          }
          
          function onReturn(){
        	  $("#contactCell").val('');
        	  $("#form1").attr("action", "${ctx}/creditreport/yunYingShang");
        	  $("#form1").submit();
          }
          function onSearch2(){
        	  $("#form1").submit();
          }
          
          var submited_flag = false;//重复提交标示
          function onSearch(){
        	  if(!$("#form1").valid()){//表单验证
        		  return;
        	  }
        	  if(!submitedFlag()){//是否已提交过了
        	      $("#form1").attr("action", "${ctx}/creditreport/yunYingShangGEOSearch");
	        	  document.getElementById('form1').submit();
        	  }
          }
          
          $(document).ready(function() {
    			$("#form1").validate();
    		});
          function print(){
				$("#tab-a").jqprint({
					debug: false, 
					importCSS: true, 
					printContainer: true, 
					operaSupport: true
				});
			}
      </script>  
</body>
</html>
