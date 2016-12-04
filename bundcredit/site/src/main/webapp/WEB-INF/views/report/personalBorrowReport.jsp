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
                  
                  <form class="form-inline search-bar" id="full_form_id" action='${ctx}/personalBorrow/fullSearch' >
                    <div class="form-group offset-r30">
                      <label for="">证件号:</label>
                      <input type="text" placeholder="" name="search_idCardNum" id="search_idCardNum" class="form-control required input-sm input-xs isIdCardNo" value='${param.search_idCardNum}'  >
                    </div>
                    <div class="radio offset-r30">
                      <label>
                        <input type="radio" name="search_search_type" value='1' <c:if test="${param.search_search_type=='1'  or param.search_search_type==null}">
						checked	</c:if>  > 申请查询
                      </label>
                    </div>
                    <div class="radio offset-r30">
                      <label>
                        <input type="radio" name="search_search_type" value="2" <c:if test="${param.search_search_type=='2'}">
						checked	</c:if>  > 贷后查询
                      </label>
                    </div>
                    
                   
                    <input type="hidden" name="lastRequestTimeKey"  value='073bfd5e748c409aa964ab772a97rrrr'  >	
                    <input type="button" value="搜 &nbsp; 索" class="btn btn-primary btn-sm btn-search" onclick = "submitForm()" />
                    
                  </form>
                  <div class="title-line offset-b30 offset-t30">基本信息</div>
                  <div class="row">
                    <div class="col-xs-4">
                      <span class="icon icon-name"></span>
                      <span>姓名：<span>${personBasicInfo.fullName}</span></span>
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
                  <div class="offset-t10">
                    <div class="clearfix table-title-lg">
                      <div class="pull-left">当前账户活动明细</div>
                      <div class="pull-right">
                        <a><span class="icon icon-add"></span></a>
                      </div>
                    </div>
                    <table class="table table-bordered table-center">
                      <thead>
                        <tr>
                          <th width="100">机构</th>
                          <th width="100">账户代码</th>
                          <th width="100">更新日期</th>
                          <th width="70">初始额度</th>
                          <th width="100">贷款期起</th>
                          <th width="90">贷款期止</th>
                          <th width="100">当前余额</th>
                          <th width="110">最近还款日期</th>
                          <th width="100">最近还款额</th>
                          <th width="85">状态</th>
                          <th width="195">近24个月状态</th>
                        </tr>
                      </thead>
                      <tbody>
                      <!-- 
                        <tr class="tr1">
                          <td>org0000001</td>
                          <td>org0000001_acc0000000001</td>
                          <td>2015-04-02</td>
                          <td>6000</td>
                          <td>2015-04-02</td>
                          <td>2015-04-02</td>
                          <td>2000</td>
                          <td>2015-04-02</td>
                          <td>3999</td>
                          <td>正常还贷</td>
                          <td>0101010101010101<a>（更多）</a></td>
                        </tr>
                       -->
                        <c:forEach items="${personAccDetails}" var="personAccDetail">
						<tr class="tr1">
							<td>${personAccDetail.insCodeStr}</td>
							<td>${personAccDetail.loanAccId}</td>
							<td><fmt:formatDate value="${personAccDetail.updateDate}" pattern="yyyy-MM-dd " /></td>
							<td>${personAccDetail.totalAllowedAmountStr}</td>
							<td><fmt:formatDate value="${personAccDetail.loanBeginDate2}" pattern="yyyy-MM-dd" /></td>
							<td><fmt:formatDate value="${personAccDetail.loanEndDate2}" pattern="yyyy-MM-dd" /></td>
							<td>${personAccDetail.loanBalanceStr}</td>
							<td><fmt:formatDate value="${personAccDetail.nextLoanRepayDate}" pattern="yyyy-MM-dd" /></td>
							<td>${personAccDetail.nextLoanRepayAmtStr}</td>
							<td>${personAccDetail.loanStatus}</td>
							<td>${personAccDetail.latest24monStatus}</td>
						</tr>
					</c:forEach>
                      </tbody>
                    </table>
                  </div><!-- /table row end -->
                  <div class="offset-t30">
                    <div class="clearfix table-title-lg">
                      <div class="pull-left">申请纪录</div>
                      <div class="pull-right">
                        <a><span class="icon icon-add"></span></a>
                      </div>
                    </div>
                    <table class="table table-bordered table-center">
                      <thead>
                        <tr>
                          <th width="85">机构</th>
                          <th width="140">申请时间</th>
                          <th width="120">申请金额</th>
                          <th width="120">申请类型</th>
                          <th width="120">审批结果</th>
                          <th width="120">审批金额</th>
                          <th width="140">申请省市</th>
                          <th width="170">家庭地址</th>
                          <th width="170">手机号码</th>
                        </tr>
                      </thead>
                      <tbody>
                      <!-- 
                        <tr class="tr1">
                          <td>org0000001</td>
                          <td>2015-04-02</td>
                          <td>6000</td>
                          <td>完成</td>
                          <td>6000</td>
                          <td>上海市黄浦区</td>
                          <td>星中路1800弄</td>
                          <td>13514250690</td>
                        </tr>
                       -->
                        <c:forEach items="${personApplyDetails}" var="personApplyDetail">
						<tr class="tr1">
							<td>${personApplyDetail.insCodeStr}</td>
							<td><fmt:formatDate value="${personApplyDetail.applyDatetime}" pattern="yyyy-MM-dd" /></td>
							<td>${personApplyDetail.applyAmountStr}</td>
							<!-- <td>${personApplyDetail.applyType}</td> -->
							<td>${personApplyDetail.applyTypeStr}</td>
							<td>${personApplyDetail.applyResult}</td>
							<td>${personApplyDetail.approveAmountStr}</td>
							<td>${personApplyDetail.applyProCity}</td>
							<!-- 
							<td>${personApplyDetail.applyIp}</td>
							 -->
							<td>${personApplyDetail.homeAddr}</td>
							<td>${personApplyDetail.selPhoneNum}</td>
						</tr>
					</c:forEach>
                      </tbody>
                    </table>
                  </div><!-- /table row end -->
                  <!-- 
                  <div class="title-line offset-b30 offset-t30">申请纪录</div>
                  <div class="no-record">暂无纪录</div>
                   -->
                </div>
              </div>

            </div>
          </div><!-- /tab2 end -->
        </div>

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
        	  $("#full_form_id").validate();
      	  });
       
      	
      	var submited_flag = false;//重复提交标示
        function submitForm(){
      	  if(!$("#full_form_id").valid()){//表单验证
      		  return;
      	  }
      	  if(!submitedFlag()){//是否已提交过了
	          document.getElementById('full_form_id').submit();
      	  }
        }
      </script>  
</body>
</html>
