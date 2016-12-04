<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!-- <head>
	<!-- <script src="${ctx}/static/jquery-validation/1.11.1/idCard_validation.js" type="text/javascript"></script> 
	<script type="text/javascript">
		$(document).ready(function() {
			$("#searchHistoryFormId").validate();
			});
	</script>
</head>  -->
<div id="page-wrapper" style="min-height: 262px;">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">工作查询</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
			<c:if test="${not empty message}">
				<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
			</c:if>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            	工作查询列表
                        </div>
                        <div class="panel-body">
                       
                         <div class="input-append">
                          <form id="searchHistoryFormId" action="${ctx}/insadmin/history" class="form-inline offset-t20 offset-b20">
									    <div class="form-group">
						</div><label>机构：</label>
									       <div class="form-group">
							<input class="form-control input-sm w200" name="search_insCode" value="${insCode}">
						</div>
						<label>查询号码：</label>
						   <div class="form-group">
							<input type="text" class="form-control input-sm input-xs isIdCardNo"  name="search_queryCondition" value="${queryCondition}">
						</div>
							<button type="submit" class="btn btn-default" id="search_bn">Search</button>
							   </form>
						</div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                                <div class="row"><div class="col-sm-12">
                                <table id="dataTables-example" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="上传日志">
                                    <thead>
                                        <tr role="row">
                                        <th>查询人姓名</th>
                                        <th>机构</th>
                                        <th>查询号码</th>
                                        <th>查询时间</th>
                                        <th>查询类型</th>
                                        <th>产品名称</th>
                                        </tr>
                                    </thead>
                                    <tbody>
										<c:forEach items="${bcHises}" var="bcHis">
											<tr class="gradeA odd" role="row">
												<td>${bcHis.userName}</td>
												<td>${bcHis.insCode}</td>
												<!-- <td>${bcHis.queryCondition}</td> -->
												<td>${bcHis.encryptQueryCondition}</td>
												<td><fmt:formatDate value="${bcHis.queryDate}" pattern="yyyy-MM-dd" /></td>
												<td>${bcHis.queryTypeName}</td>
												<td>${bcHis.productName}</td>
											</tr>
										</c:forEach>                                             
                                    </tbody>
                                </table>
                                </div>
                                <tags:bcpagination/>
                            </div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
        </div>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		$("#searchHistoryFormId").validate();
		$.validator.addMethod("isIdCardNo", function(value, element) {

			return this.optional(element) || isIdCardNo(value) || isMobileNo(value);
		}, "<font color='#E47068'>请输入正确的查询号码</font>");
	});
	
	function isMobileNo(num) {
		var checkNum = /^[0-9]+$/;
		if (num.length == 11) {
			if (checkNum.test(num)) {

				return true;
			}
		}
		return false;
	}
</script>