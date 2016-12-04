<%@ page import="java.util.Date"%>
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
                  <div class="title-line">报告内容</div>
                  <div class="row">
                    <div class="col-xs-3">
                      <span class="icon icon-addr"></span>
                      <span>所在省市：<span>${businessInfoBean.city}</span></span>
                    </div>
                    <div class="col-xs-4">
                      <span class="icon icon-time"></span>
                      <span>报告时间：<span><fmt:formatDate value="<%=new Date()%>" pattern="yyyy-MM-dd HH:mm:ss" /></span></span>
                    </div>
                   <!--   <div class="col-xs-5">
                      <span class="icon icon-edit"></span>
                      <span>数据最后更新时间：<span><fmt:formatDate value="${enterpriseInfo.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></span></span>
                    </div>-->
                  </div>
                  <!-- 工商信息 -->
                  <div class="title-line offset-b20 offset-t30">工商信息</div>
                   <div class="offset-t10">
                    <div class="clearfix table-title-lg">
                      <div class="pull-left">基本信息</div>
                      <div class="pull-right">
                        <a><span class="icon icon-add"></span></a>
                      </div>
                    </div>
                    <table class="table table-bordered table-center">
                      <tbody>
                     	<tr>
                          <td width="15%">注册号</td>
                          <td width="35%">${businessInfoBean.regID}</td>
                          <td width="15%">名称</td>
                          <td width="35%">${businessInfoBean.name}</td>
                        </tr>
                        <tr class="tr2">
                          <td>类型</td>
                          <td>${businessInfoBean.type}</td>
                          <td>法定代表人</td>
                          <td>${businessInfoBean.legalPerson}</td>
                        </tr>
                        <tr class="tr1">
                          <td>注册资本</td>
                          <td>${businessInfoBean.registeredCapital}</td>
                          <td>成立日期</td>
                          <td>${businessInfoBean.setupDate}</td>
                        </tr>
                        <tr class="tr2">
                          <td>住所</td>
                          <td>${businessInfoBean.address}</td>
                          <td>登记状态</td>
                          <td>${businessInfoBean.state}</td>
                        </tr>
                        <tr class="tr1">
                          <td>经营期限</td>
                          <td>${businessInfoBean.operatingPeriod}</td>
                        </tr>
                        <tr class="tr1">
                          <td>登记机关</td>
                          <td>${businessInfoBean.bureau}</td>
                          <td>核准日期</td>
                          <td>${businessInfoBean.awardDate}</td>
                        </tr>
                        <tr class="tr2">
                          <td>经营范围</td>
                          <td colspan="3" style="text-align:left;">${businessInfoBean.scope}</td>
                        </tr>
                      </tbody>
                    </table>
                  </div><!-- /table row end -->
                  <!-- 股东信息 -->
                  <div class="offset-t30" id="stockholder">
                    <div class="clearfix table-title-lg">
                      <div class="pull-left">股东信息</div>
                      <div class="pull-right">
                        <a><span class="icon icon-add"></span></a>
                      </div>
                    </div>
                    <table class="table table-bordered table-center">
                    	<thead>
                        <tr>
                          <th width="85">股东类型</th>
                          <th width="140">股东</th>
                          <th width="120">证件类型</th>
                          <th width="120">证件号码</th>
                          <th width="120">出资额（实缴)</th>
                          <th width="120">出资日期</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${businessInfoBean.partnerInformationBeans}" var="partnerInfo">
						<tr class="tr1">
							<td>${partnerInfo.partnerType}</td>
							<td>${partnerInfo.partnerName}</td>
							<td>${partnerInfo.identifyType}</td>
							<td>${partnerInfo.identifyNo}</td>
							<td>${partnerInfo.realCapi}</td>
							<td>${partnerInfo.capiDate}</td>
						</tr>
						</c:forEach>
                      	</tbody>
                    </table>
                  </div>
                  <!-- /table row end -->
 
                  <!-- 变更信息 -->
                  <div class="offset-t30" id="changeHistory">
                    <div class="clearfix table-title-lg">
                      <div class="pull-left">变更信息</div>
                      <div class="pull-right">
                        <a><span class="icon icon-add"></span></a>
                      </div>
                    </div>
                    <table class="table table-bordered table-center">
                        <tr>
                          <th width="15%">变更日期</th>
                          <th width="15%">变更项目</th>
                          <th width="35%">变更前</th>
                          <th width="35%">变更后</th>
                        </tr>
                        <c:forEach items="${businessInfoBean.changeRecordsBeans}" var="changeRecord">
						<tr class="tr1">
							<td>${changeRecord.changeDate}</td>
							<td>${changeRecord.projectName}</td>
							<td>${changeRecord.beforeContent}</td>
							<td>${changeRecord.afterContent}</td>
						</tr>
						</c:forEach>
                    </table>
                  </div>
                  <!-- /table row end -->
                  
                  
                  <!-- 组织机构代码 -->
                  <div class="offset-t30" id="organizationCode">
                    <div class="clearfix table-title-lg">
                      <div class="pull-left">组织机构代码</div>
                      <div class="pull-right">
                        <a><span class="icon icon-add"></span></a>
                      </div>
                    </div>
                    <table class="table table-bordered table-center">
                    	<thead>
                        <tr>
                          <th width="85">机构代码</th>
                          <th width="140">机构名称</th>
                          <th width="120">登记证号</th>
                          <th width="120">有效期</th>
                        </tr>
                        </thead>
                        <tbody>
                        
						<tr class="tr1">
							<td>${orgCodeBean.orgCode}</td>
							<td>${orgCodeBean.orgName}</td>
							<td>${orgCodeBean.orgRegID}</td>
							<td>自${orgCodeBean.startDate}至${orgCodeBean.endDate}</td>
						</tr>
                      	</tbody>
                    </table>
                  </div>
                  <!-- /table row end -->
                  
                  <div class="title-line offset-b20 offset-t30">企业被执行信息</div>
                  
                  <!-- 被执行信息  -->
                  <div class="offset-t30" id="executorInfo">
                    <div class="clearfix table-title-lg">
                      <div class="pull-left">被执行信息</div>
                      <div class="pull-right">
                        <a><span class="icon icon-add"></span></a>
                      </div>
                    </div>
                    <table class="table table-bordered table-center">
                    	<thead>
                        <tr>
                          <th width="85">案号</th>
                          <th width="140">被执行名称</th>
                          <th width="120">组织机构代码</th>
                          <th width="120">执行法院</th>
                          <th width="120">立案时间</th>
                          <th width="120">执行标的</th>
                          <!-- <th width="120">失信详情</th> -->
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${enterpriseExecutorBeans}" var="executor">
						<tr class="tr1">
							<td>${executor.caseCode}</td>
							<td>${executor.name}</td>
							<td>${executor.code}</td>
							<td>${executor.execCourt}</td>
							<td>${executor.filingTime}</td>
							<td>${executor.executiveSubject}</td>
							<!-- <td><a href="#">查看详情</a></td> -->
						</tr>
						</c:forEach>
                        </tbody>
                    </table>
                  </div>
                  <!-- 失信人信息  -->
                  <div class="offset-t30" id="dishPersonInfo">
                    <div class="clearfix table-title-lg">
                      <div class="pull-left">失信人信息</div>
                      <div class="pull-right">
                        <a><span class="icon icon-add"></span></a>
                      </div>
                    </div>
                    <table class="table table-bordered table-center">
                    	<thead>
                        <tr>
                          <th width="145">名称</th>
                          <th width="100">法人名称</th>
                          <th width="140">身份证号码/组织机构代码</th>
                          <th width="140">执行依据文号</th>
                          <th width="140">案号</th>
                          <th width="140">做出执行依据单位</th>
                          <th width="140">法律生效文书确定的义务</th>
                          <th width="100">被执行人的履行情况</th>
                          <th width="140">执行法院</th>
                          <th width="80">省份</th>
                          <th width="80">立案时间</th>
                          <th width="80">发布时间</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${enterpriseDishPersonBeans}" var="dishPerson">
						<tr class="tr1">
							<td>${dishPerson.name}</td>
							<td>${dishPerson.legalPerson}</td>
							<td>${dishPerson.code}</td>
							<td>${dishPerson.basisDocNo}</td>
							<td>${dishPerson.caseCode}</td>
							<td>${dishPerson.basisDept}</td>
							<td>${dishPerson.docContent}</td>
							<td>${dishPerson.execStatus}</td>
							<td>${dishPerson.execCourt}</td>
							<td>${dishPerson.province}</td>
							<td>${dishPerson.filingTime}</td>
							<td>${dishPerson.publishDate}</td>
						</tr>
						</c:forEach>
                        </tbody>
                    </table>
                  </div>
                  <!-- /table row end -->
                  <!-- 法院裁决文书  -->
                  <div class="offset-t30" id="judgementDoc">
                    <div class="clearfix table-title-lg">
                      <div class="pull-left">法院裁决文书</div>
                      <div class="pull-right">
                        <a><span class="icon icon-add"></span></a>
                      </div>
                    </div>
                    <table class="table table-bordered table-center">
                    	<thead>
                        <tr>
                          <th width="85">案号</th>
                          <th width="140">标题</th>
                          <th width="120">执行法院</th>
                          <th width="120">日期</th>
                          <th width="120">裁决文书</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${enterpriseCourtJudgmentDocBeans}" var="judgmentDoc">
						<tr class="tr1">
							<td>${judgmentDoc.caseCode}</td>
							<td>${judgmentDoc.title}</td>
							<td>${judgmentDoc.courtName}</td>
							<td>${judgmentDoc.judgeDate}</td>
							<td><a href="${judgmentDoc.judgmentDocUrl}" target="_blank">查看</a></td>
						</tr>
						</c:forEach>
                        </tbody>
                    </table>
                  </div>
                  <!-- /table row end -->
                  
                  <!-- /table row end -->
                  <!-- 法院公告  -->
                  <div class="offset-t30" id="courtBulletin">
                    <div class="clearfix table-title-lg">
                      <div class="pull-left">法院公告</div>
                      <div class="pull-right">
                        <a><span class="icon icon-add"></span></a>
                      </div>
                    </div>
                    <table class="table table-bordered table-center">
                    	<thead>
                        <tr>
                          <th width="85">公告类型</th>
                          <th width="140">公告人</th>
                          <th width="120">当事人</th>
                          <th width="120">公告时间</th>
                          <th width="120">公告内容</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${enterpriseCourtAnnouncementBeans}" var="courtAnnouncement">
						<tr class="tr1">
							<td>${courtAnnouncement.announcementType}</td>
							<td>${courtAnnouncement.announcement}</td>
							<td>${courtAnnouncement.litigant}</td>
							<td>${courtAnnouncement.announcementDate}</td>
							<td>${courtAnnouncement.content}</td>
						</tr>
						</c:forEach>
                        </tbody>
                    </table>
                  </div>
                  <!-- /table row end -->
                  
                  <div class="title-line offset-b20 offset-t30">法人被执行信息</div>
                  
                  <!-- 被执行信息  -->
                  <div class="offset-t30" id="executorInfo">
                    <div class="clearfix table-title-lg">
                      <div class="pull-left">被执行信息</div>
                      <div class="pull-right">
                        <a><span class="icon icon-add"></span></a>
                      </div>
                    </div>
                    <table class="table table-bordered table-center">
                    	<thead>
                        <tr>
                          <th width="85">案号</th>
                          <th width="140">被执行名称</th>
                          <th width="120">身份证号码</th>
                          <th width="120">执行法院</th>
                          <th width="120">立案时间</th>
                          <th width="120">执行标的</th>
                          <!-- <th width="120">失信详情</th> -->
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${personExecutorBeans}" var="executor">
						<tr class="tr1">
							<td>${executor.caseCode}</td>
							<td>${executor.name}</td>
							<td>${executor.code}</td>
							<td>${executor.execCourt}</td>
							<td>${executor.filingTime}</td>
							<td>${executor.executiveSubject}</td>
							<!-- <td><a href="#">查看详情</a></td> -->
						</tr>
						</c:forEach>
                        </tbody>
                    </table>
                  </div>
                  <!-- /table row end -->
                   <!-- 个人失信人信息  -->
                  <div class="offset-t30" id="dishPersonInfo">
                    <div class="clearfix table-title-lg">
                      <div class="pull-left">失信人信息</div>
                      <div class="pull-right">
                        <a><span class="icon icon-add"></span></a>
                      </div>
                    </div>
                    <table class="table table-bordered table-center">
                    	<thead>
                        <tr>
                          <th width="145">名称</th>
                          <th width="100">法人名称</th>
                          <th width="140">身份证号码/组织机构代码</th>
                          <th width="140">执行依据文号</th>
                          <th width="140">案号</th>
                          <th width="140">做出执行依据单位</th>
                          <th width="140">法律生效文书确定的义务</th>
                          <th width="100">被执行人的履行情况</th>
                          <th width="140">执行法院</th>
                          <th width="80">省份</th>
                          <th width="80">立案时间</th>
                          <th width="80">发布时间</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${personDishPersons}" var="dishPerson">
						<tr class="tr1">
							<td>${dishPerson.name}</td>
							<td>${dishPerson.legalPerson}</td>
							<td>${dishPerson.code}</td>
							<td>${dishPerson.basisDocNo}</td>
							<td>${dishPerson.caseCode}</td>
							<td>${dishPerson.basisDept}</td>
							<td>${dishPerson.docContent}</td>
							<td>${dishPerson.execStatus}</td>
							<td>${dishPerson.execCourt}</td>
							<td>${dishPerson.province}</td>
							<td>${dishPerson.filingTime}</td>
							<td>${dishPerson.publishDate}</td>
						</tr>
						</c:forEach>
                        </tbody>
                    </table>
                  </div>
                  <!-- 法院裁决文书  -->
                  <div class="offset-t30" id="judgementDoc">
                    <div class="clearfix table-title-lg">
                      <div class="pull-left">法院裁决文书</div>
                      <div class="pull-right">
                        <a><span class="icon icon-add"></span></a>
                      </div>
                    </div>
                    <table class="table table-bordered table-center">
                    	<thead>
                        <tr>
                          <th width="85">案号</th>
                          <th width="140">标题</th>
                          <th width="120">执行法院</th>
                          <th width="120">日期</th>
                          <th width="120">裁决文书</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${personCourtJudgmentDocBeans}" var="judgmentDoc">
						<tr class="tr1">
							<td>${judgmentDoc.caseCode}</td>
							<td>${judgmentDoc.title}</td>
							<td>${judgmentDoc.courtName}</td>
							<td>${judgmentDoc.judgeDate}</td>
							<td><a href="${judgmentDoc.judgmentDocUrl}" target="_blank">查看</a></td>
						</tr>
						</c:forEach>
                        </tbody>
                    </table>
                  </div>
                  <!-- /table row end -->
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
