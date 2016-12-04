<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div id="page-wrapper" style="min-height: 262px;">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">机构维护</h1>
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
                            	机构列表
                        </div>
                     
                        <!-- /.panel-heading -->
                        <div class="panel-body">
          					    <a class="btn btn-primary btn-sm offset-r10" href="${ctx}/insadmin/andins" >新增机构</a>
                            <div class="dataTable_wrapper">
                                <div class="row"><div class="col-sm-12">
                                <table id="dataTables-example" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="上传日志">
                                    <thead>
                                        <tr role="row">
                                        <th    >机构代码</th>
                                        <th style="width: 100px;"  >机构名称</th>
                                        <th style="width: 120px;">海纳币信用额度</th>
                                        <th style="width: 110px;">海纳币余额</th>
                                        <th style="width: 150px;">现金账户信用额度</th>
                                        <th style="width: 120px;">现金账户余额</th>
                                        <th style="width: 220px;">管理</th>
                                        </tr>
                                    </thead>
                                    <tbody>
										<c:forEach items="${institutions}" var="ins">
											<tr class="gradeA odd" role="row">
												<td class="sorting_1 center">${ins.insCode}</td>
												<td class="center">${ins.name}</td>
												<td class="center">${ins.lineOfCredit}</td>
												<td class="center">${ins.balance}</td>
												<td class="center">${ins.cashCredit}</td>
												<td class="center">${ins.cashBalance}</td>												<td>
												<a href="${ctx}/user_Safeguard/${ins.insCode}">用户管理</a>
												<a href="${ctx}/insadmin/${ins.insCode}/institution/update">更新</a>
												<a href="${ctx}/insadmin/transactions4commercial?q_insCode=${ins.insCode}">交易记录</a>
											<%-- 	<a href="${ctx}/insadmin/${ins.insCode}/institution/credit">调整信用额度</a>
												<a href="${ctx}/insadmin/${ins.insCode}/institution/balance">充值</a>
												<a href="${ctx}/insadmin/${ins.insCode}/discount">机构折扣</a> --%>
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