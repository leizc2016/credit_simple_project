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
            </div>
            <table class="table table-bordered">
              <thead>
                <tr>
                  <th width="15%">操作人</th>
                  <th width="20%">查询号码</th>
                  <th width="20%">查询时间</th>
                  <th width="15%">查询类型
                       <div class="pull-right">
		                    <select class="form-control input-xs" name='queryType' id='queryType' onchange="queryByQueryType()" >
		                      <option value=''>所有</option>
		                      <option value='1'  <c:if test="${queryType=='1'}">selected</c:if>>
		                      		申请查询
							  </option>
		                      <option value='2'  <c:if test="${queryType=='2'}">selected</c:if>>
		                      		贷后查询
		                      </option>
		                      <option value='3'  <c:if test="${queryType=='3'}">selected</c:if>>
		                      		第三方查询
		                      </option>
							<!--   <option value='YLCX'  <c:if test="${productCode=='YLCX'}">selected</c:if>>
		                      		银联
		                      </option> -->
		                    </select>
		                  </div>
                  </th>
                  <th width="15%">产品名称
                  <div class="pull-right">
                    <select class="form-control input-xs" name='productCode' id='productCode' onchange="queryByProductCode()" >
                      <option value=''>所有</option>
                      <option value='JCCX'  <c:if test="${productCode=='JCCX'}">selected</c:if>>
                      		基础查询
					  </option>
                      <option value='XBCX'  <c:if test="${productCode=='XBCX'}">selected</c:if>>
                      		详版查询
                      </option>
                      <option value='YYSCX'  <c:if test="${productCode=='YYSCX'}">selected</c:if>>
                      		运营商查询
                      </option>
                      <option value='YLCX'  <c:if test="${productCode=='YLCX'}">selected</c:if>>
                      		银联查询
                      </option>
                    </select>
                  </div>
                  </th>
                  <th width="15%">操作</th>
                </tr>
              </thead>
              <tbody>
                
                <c:forEach items="${bcHisList}" var="bcHis">
						<tr class="tr2">
							<td>${bcHis.userName}</td>
							<td>${bcHis.queryCondition}</td>
							<td><fmt:formatDate value="${bcHis.queryDate}" pattern="yyyy年MM月dd日  HH点mm分" /></td>
							<td>${bcHis.queryTypeName}</td>
							<td>${bcHis.productName}</td>
							<td>
								<c:choose>
									<c:when test="${not empty bcHis.productCode && bcHis.productCode == 'YYSCX_CS' && bcHis.productCode2nd == 'CS' && bcHis.flag == 1}">
										数据获取中
									</c:when>
									<c:when test="${not empty bcHis.productCode && bcHis.productCode == 'YYSCX_CS' && bcHis.productCode2nd == 'CS' && bcHis.flag == 2}">
										<a href="#" onclick="onSearch('${bcHis.conditionId}', '${bcHis.productCode}','${bcHis.productCode2nd}',1)">查看详情</a>
									</c:when>
									<c:otherwise>
										<a href="#" onclick="onSearch('${bcHis.conditionId}', '${bcHis.productCode}','${bcHis.productCode2nd}',3)">查询</a>
									</c:otherwise>
								</c:choose>
							</td>
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
          function queryByProductCode(){
        	  var queryType = document.getElementById('queryType').value;
        	  var productCode = document.getElementById('productCode').value;
        	  var url = "${ctx}/creditreport/history?productCode="+productCode + "&queryType="+queryType;
        	  window.location.href=url;
          }
          function queryByQueryType(){
        	  var queryType = document.getElementById('queryType').value;
        	  var productCode = document.getElementById('productCode').value;
        	  var url = "${ctx}/creditreport/history?productCode="+productCode + "&queryType="+queryType;
        	  window.location.href=url;
          }
          function onSearch(conditionId,productCode,productCode2nd,type){
        	  if((type == '3' && productCode2nd=='CS') || productCode.indexOf("YLCX") != -1||(type == '3' && productCode2nd=='GEO')||(productCode2nd=='QYCX')||(productCode=='GRJDCX')||(productCode=='QYJDCX')){
        		  if(confirm("此次查询将扣费，是否继续执行？")){
        			  var url = "${ctx}/creditreport/distributeSearch?conditionId="+conditionId+"&productCode="+productCode +"&productCode2nd="+productCode2nd + "&queryType=" + type;
                	  window.location.href=url;
        		  }
        	  }else{
        		  var url = "${ctx}/creditreport/distributeSearch?conditionId="+conditionId+"&productCode="+productCode +"&productCode2nd="+productCode2nd + "&queryType=" + type;
            	  window.location.href=url;
        	  }
          }
      </script>
</body>
</html>
