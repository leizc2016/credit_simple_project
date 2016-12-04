<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
	<link href="${ctx}/static/bc/css/search.css" rel="stylesheet">
</head>
<body>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>     
     <div role="tabpanel" class="query-content">

        <!-- Nav tabs -->
        <ul class="nav nav-tabs" role="tablist">
          <li role="presentation" class="active tab1"><a href="#tab1" aria-controls="tab1" role="tab" data-toggle="tab">交易查询</a></li>
        </ul>

        <!-- Tab panes -->
        <div class="tab-content">
          <div role="tabpanel" class="tab-pane active" id="tab1">
            <div class="title-line">交易查询</div>
            <div class="row">
              <div class="col-xs-3">
                <div class="logo-box"><img src="${ctx}/static/bc/img/logo.png" alt="" width="199" height="67"></div>
                <!-- <div class="text-center offset-t30"><a class="btn btn-primary btn-sm" onclick="alert('message*****')">充 &nbsp; 值</a></div>-->
              </div>
              <div class="col-xs-6">
                <div class="info-group offset-t30">
                  <span class="icon icon-accounts"></span>
                  <label>机构名称：</label>
                  <span>${institution.name}</span>
                </div>
                <div class="info-group">
                  <span class="icon icon-accounts"></span>
                  <label>机构账户：</label>
                  <span>${institution.insCode}</span>
                </div>
                <div class="info-group">
                  <span class="icon icon-accounts"></span>
                  <label>
                                                                          外滩征信账户余额：
                  </label>
                  <span>${institution.balance}</span>
                </div>
                <div class="info-group">
                  <span class="icon icon-accounts"></span>
                  <label>外滩征信账户信用额度：</label>
                  <span>${institution.lineOfCredit}</span>
                </div>
                <div class="info-group">
                  <span class="icon icon-accounts"></span>
                  <label>现金账户余额：</label>
                  <span>${institution.cashBalance}</span>
                </div>
                <div class="info-group">
                  <span class="icon icon-accounts"></span>
                  <label>现金账户信用额度：</label>
                  <span>${institution.cashCredit}</span>
                </div>
                <!-- 
                <div class="offset-t30 offset-l40"><a class="btn btn-primary btn-sm" onclick="alert('message*****')" >提 &nbsp; 现</a></div>
                 -->
              </div>
            </div><!-- /row end -->
              
            <div class="offset-t20">
              <div class="clearfix table-title-lg">交易查询明细表</div>
              <table id="contentTable" class="table table-striped table-bordered table-condensed">
                <thead class="select-th">
                  <tr>
                    <th width="10%">交易类型</th>
                    <th width="15%">操作员</th>
                    <th width="25%">描述</th>
                    <th width="20%">日期</th>
                    <th width="10%">交易金额</th>
                    <!-- 
                    <th width="10%"><select><option>实际账户</option></select></th>
                    <th width="10%">账户类型</th>
                     -->
                     
                    <th width="10%">终端</th>
                    <!-- <th width="20%">备注</th> -->
                  </tr>
                </thead>
                <tbody>
                <c:forEach items="${transLogs}" var="log">
                  <tr>
                    <td>${log.transType}</td>
                    <td>${log.opName}</td>
                    <td>${log.description}</td>
                    <td><fmt:formatDate value="${log.transDateTime}" pattern="yyyy-MM-dd  HH:mm:ss" /></td>
                    <td>${log.fee}</td>
                    <!-- 
                    <td></td>
                    <td>外滩征信/现金</td>
                     -->
                     
                    <td>${log.device}</td>
                    <!-- <td>${log.comments}</td> -->
                  </tr>
                 </c:forEach>
                </tbody>
              </table>
            </div>
            <tags:bcpagination/>
          </div><!-- /tab1 end -->
        </div>
      </div><!-- /tab end -->
</body></html>      