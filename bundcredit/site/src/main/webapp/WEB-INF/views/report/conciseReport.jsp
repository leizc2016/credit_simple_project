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
              <div class="tab-content">
                <div id="tab-a" class="tab-pane active" role="tabpanel">
                <!-- 
                  <a data-target="#remark" data-toggle="modal" class="btn btn-warning btn-xs btn-remark" style="position: absolute; top: -40px; right: -40px;"><span class="icon icon-arrow"></span>备注</a>
                 -->
                  <!-- Modal -->
                  <div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="remark" class="modal">
                    <div class="modal-dialog">
                      <div class="modal-content">
                        <div class="table-title-orange"><span class="icon icon-arrow-b"></span>备注</div>
                        <div class="modal-body">
                          <table class="table moadal-table">
                            <tbody>
                              <tr class="tr1">
                                <td width="80" class="orange text-center">张三</td>
                                <td width="210" class="orange">410043992010082</td>
                                <td width="66"></td>
                                <td width="100"></td>
                              </tr>
                              <tr class="tr2">
                                <td class="text-center">王一</td>
                                <td>备注内容备注内容备注内容备注内容备注内容备注内容注内容备注内容注内容备注内容</td>
                                <td><span class="label label-primary">待查</span></td>
                                <td>2015/05/11</td>
                              </tr>
                              <tr class="tr1">
                                <td class="text-center">王二</td>
                                <td>备注内容（更多）</td>
                                <td><span class="label label-success">完成</span></td>
                                <td>2015/05/11</td>
                              </tr>
                              <tr class="tr2">
                                <td class="text-center">王三</td>
                                <td>备注内容备注内容备注内容备注内容备注内容备注内容注内容备注内容注内容备注内容</td>
                                <td><span class="label label-success">完成</span></td>
                                <td>2015/05/11</td>
                              </tr>
                            </tbody>
                          </table>
                          <div class="textarea-wrap">
                            <textarea rows="3" class="form-control"></textarea>
                            <div class="offset-t15">
                              <span>标签：</span>
                              <span class="label label-primary">待查</span>
                              <span class="label label-success">完成</span>
                            </div>
                          </div>
                          <div class="text-center">
                            <a class="btn btn-warning btn-sm btn-l">提交备注</a>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  
                  <form class="form-inline search-bar" 
                  	id="concise_form_id" 
                  	action='${ctx}/creditreport/conciseSearch'
                  	 >
                    <div class="form-group offset-r30">
                      <label for="">证件号:</label>
                      <input type="text" placeholder="" name="search_idCardNum" id="search_idCardNum" class="form-control required input-sm input-xs isIdCardNo" value='${param.search_idCardNum}'  >
                    </div>
                    <div class="radio offset-r30">
                      <label>
                        <input type="radio" name="search_search_type" value='1' <c:if test="${param.search_search_type=='1' or param.search_search_type==null }">
						checked	</c:if>  > 申请查询
                      </label>
                    </div>
                    <div class="radio offset-r30">
                      <label>
                        <input type="radio" name="search_search_type" value="2" <c:if test="${param.search_search_type=='2'}">
						checked	</c:if>  > 贷后查询 
                      </label>
                    </div>
                   	<input type="hidden" name="lastRequestTimeKey"  value='073bfd5e748c409aa964ab772a97dad2'  >	
                    <input type="button" value="搜 &nbsp; 索" class="btn btn-primary btn-sm btn-search" onclick = "submitForm()" />
                  </form>
                  <div class="title-line offset-b30 offset-t30">基本信息</div>
                  <div class="row">
                    <div class="col-xs-4">
                      <span class="icon icon-name"></span>
                      <span>姓名：${testPie}<span>${personBasicInfo.fullName}</span></span>
                    </div>
                    <div class="col-xs-5">
                      <span class="icon icon-card"></span>
                      <span>证件号码：<span>${personBasicInfo.idCardNumString}</span></span>
                    </div>
                    <div class="col-xs-3">
                      <span class="icon icon-addr"></span>
                      <span>所在省市：<span>${personBasicInfo.location}</span></span>
                    </div>
                    <div class="col-xs-4 offset-t30">
                      <span class="icon icon-time"></span>
                      <span>报告时间：<span><fmt:formatDate value="${personBasicInfo.reportDatetime}" pattern="yyyy-MM-dd  HH:mm:ss" /></span></span>
                    </div>
                    <div class="col-xs-5 offset-t30">
                      <span class="icon icon-edit"></span>
                      <span>数据最后更新时间：<span><fmt:formatDate value="${personBasicInfo.lastUpdateDatetime}" pattern="yyyy-MM-dd HH:mm:ss" /></span></span>
                    </div>
                  </div>
                  <div class="title-line offset-b20 offset-t30">报告内容</div>
                  <div class="clearfix">
				  <jsp:include page="includes/summary.jsp"/>
    			  <jsp:include page="includes/comments.jsp"></jsp:include>
                  </div><!-- /table row end -->
                  
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
        	  $("#concise_form_id").validate();
          });
        	
          var submited_flag = false;//重复提交标示
          function submitForm(){
        	  if(!$("#concise_form_id").valid()){//表单验证
        		  return;
        	  }
        	  if(!submitedFlag()){//是否已提交过了
	        	  document.getElementById('concise_form_id').submit();
        	  }
          }
        	//print pdf;
        	function printdiv(printpage)  
				{  
				var headstr = "<html><head><title></title></head><body>";  
				var footstr = "</body>";  
				var printData = document.getElementById(printpage).innerHTML; 
				console.log(printData);
				var oldstr = document.body.innerHTML;  
				document.body.innerHTML = headstr+printData+footstr;  
				window.print();  
				document.body.innerHTML = oldstr;  
				return false;  
				}  
      </script>  
</body>
</html>
