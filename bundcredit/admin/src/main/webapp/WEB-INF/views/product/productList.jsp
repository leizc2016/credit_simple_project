<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div id="page-wrapper" style="min-height: 262px;">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">产品管理</h1>
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
                            	产品列表
                        </div>
                     
                        <!-- /.panel-heading -->
                        <div class="panel-body">
          					    <a class="btn btn-primary btn-sm offset-r10" href="${ctx}/admin/product/addpage">创建产品</a>
          					    <!--<a class="btn btn-primary btn-sm">账户权限管理</a>-->
                            <div class="dataTable_wrapper">
                                <div class="row"><div class="col-sm-12">
                                <table id="contentTable" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="上传日志">
                                    <thead>
                                        <tr role="row">
	                                        <th style="width: 350px;">产品名</th>
	                                        <th style="width: 350px;">产品编号</th>
	                                        <th style="width: 350px;">查询基础费用</th>
	                                        <th style="width: 350px;">有增量查询结果费用</th>
	                                       	<th style="width: 350px;">无增量查询费用</th>
	                                        <th style="width: 350px;">支付方式</th>
	                                        <th style="width: 150px;">管理</th>
                                        </tr>
                                    </thead>
                                    <tbody>
										<c:forEach items="${queryProducts.content}" var="queryProduct">
											<tr>
												<td>${queryProduct.name}</td>
												<td>${queryProduct.productCode}</td>
												<td>${queryProduct.queryPrice}</td>
												<td>${queryProduct.queryReturnPrice}</td>
												<td>${queryProduct.queryNoreturnPrice}</td>
												<td>
												  <c:if test="${queryProduct.cashFlag==0}">外滩币支付</c:if>
												  <c:if test="${queryProduct.cashFlag==1}">现金支付</c:if>
												<td>
													<a class="btn btn-success btn-xs" href="${ctx}/admin/product/update/${queryProduct.id}">更新</a>
													<a class="btn btn-success btn-xs" href="${ctx}/admin/product/delete/${queryProduct.id}">删除</a>
												</td>
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
