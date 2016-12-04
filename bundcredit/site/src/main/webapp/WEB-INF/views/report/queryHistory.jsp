<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title></title>
</head>

<body>
<div id="page-wrapper" style="min-height: 262px;">
	<div class="query-content" role="tabpanel">
        <!-- Nav tabs -->
        <ul role="tablist" class="nav nav-tabs">
          ${tapAllowed}
          <li class="active tab2" role="presentation"><a href="#">查询历史</a></li>
        </ul>
        <!-- Tab panes -->
        <div class="tab-content">
          <div id="tab1" class="tab-pane active" role="tabpanel">
            <div class="title-line">查询历史</div>
            <div class="clearfix table-title-lg">
              <div class="pull-left">历史记录</div>
              <!-- <div class="pull-right">
                <select class="form-control input-xs">
                  <option>近一周</option>
                </select>
              </div> -->
            </div>
            <table class="table table-bordered">
              <thead>
                <tr>
                  <th>操作人</th>
                  <th>被查人证件号码</th>
                  <th>查询时间</th>
                  <!-- 
                  <th>最新标签
                  <div class="pull-right">
                    <select class="form-control input-xs">
                      <option>所有</option>
                    </select>
                  </div>
                  </th>
                   -->
                  <th>查询类型
                  <div class="pull-right">
                    <select class="form-control input-xs" id='queryType' name='queryType' onchange="queryByQueryType()">
                      <option value='' >所有</option>
                      <option value='1' <c:if test="${param.search_queryType=='1'}">
						selected	</c:if> >申请查询</option>
                      <option value='2' <c:if test="${param.search_queryType=='2'}">
						selected	</c:if> >贷后查询</option>
                    </select>  
                  </div>
                  </th>
                  <th>产品名称
                  <div class="pull-right">
                    <select class="form-control input-xs" name='pageId' id='pageId' onchange="queryByPageId()" >
                      <option value=''>所有</option>
                      <option value='1'  <c:if test="${param.search_pageId=='1'}">
						selected	</c:if>>基础信息</option>
                      <option value='2'  <c:if test="${param.search_pageId=='2'}">
						selected	</c:if>>详细信息</option>
                    </select>
                  </div>
                  </th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
              <!-- 
                <tr class="tr1">
                  <td><a data-toggle="tab" role="tab" aria-controls="tab2" href="#tab2">张三</a></td>
                  <td>3107764854301</td>
                  <td>2015/05/03</td>
                  <td>完成</td>
                  <td>是</td>
                  <td>人行报告</td>
                  <td>人行报告</td>
                </tr>
                <tr class="tr2">
                  <td>张三</td>
                  <td>3107764854301</td>
                  <td>2015/05/03</td>
                  <td>完成</td>
                  <td>是</td>
                  <td>人行报告</td>
                  <td>人行报告</td>
                </tr>
               -->
                
                <c:forEach items="${accQueryHistories}" var="accQueryHistory">
						<tr class="tr2">
							<td>${accQueryHistory.insAccName}</td>
							<td>${accQueryHistory.idCardNumString}</td>
							<td><fmt:formatDate value="${accQueryHistory.queryDatetime}" pattern="yyyy年MM月dd日 " /></td>
							<!-- 
							<td>${accQueryHistory.lastLabel}</td>
							 -->
							<td>${accQueryHistory.queryTypeName}</td>
							<td>${accQueryHistory.productName}</td>
							<td><a href="${ctx}${accQueryHistory.urlByCondition}">查询</a></td>
						</tr>
				</c:forEach>
                
              </tbody>
            </table>
          </div><!-- /tab1 end -->
          
            <tags:bcpagination/>
        </div>

      </div>
      </div>
      <script type="text/javascript">
          function queryByPageId(){
        	  var pageId = document.getElementById('pageId').value;
        	  var queryType = document.getElementById('queryType').value;
        	  var url = "${ctx}/creditreport/history?search_queryType="+queryType+"&search_pageId="+pageId;
        	  window.location.href=url;
          }
          
          function queryByQueryType(){
        	  var pageId = document.getElementById('pageId').value;
        	  var queryType = document.getElementById('queryType').value;
        	  var url = "${ctx}/creditreport/history?search_queryType="+queryType+"&search_pageId="+pageId;
        	  window.location.href=url;
          }
      </script>
</body>
</html>
