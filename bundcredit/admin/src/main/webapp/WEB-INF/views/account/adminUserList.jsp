<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div id="page-wrapper" style="min-height: 262px;">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">帐户管理</h1>
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
                            	帐户列表
                        </div>
                     
                        <!-- /.panel-heading -->
                        <div class="panel-body">
          					    <a class="btn btn-primary btn-sm offset-r10" href="${ctx}/admin/user/addpage" >创建账户</a>
          					    <!--<a class="btn btn-primary btn-sm">账户权限管理</a>-->
                            <div class="dataTable_wrapper">
                                <div class="row"><div class="col-sm-12">
                                <table id="contentTable" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="上传日志">
                                    <thead>
                                        <tr role="row">
	                                        <th>登录名</th>
	                                        <th style="width: 350px;"  >姓名</th>
	                                        <th style="width: 350px;">注册时间</th>
	                                        <!-- <th style="width: 110px;">UUID</th> -->
	                                        <th style="width: 350px;">管理</th>
                                        </tr>
                                    </thead>
                                    <tbody>
										<c:forEach items="${users.content}" var="user">
											<tr>
												<td><a href="${ctx}/admin/user/update/${user.id}">${user.loginName}</a></td>
												<td>${user.name}</td>
												<td>
													<fmt:formatDate value="${user.registerDate}" pattern="yyyy-MM-dd HH:mm:ss" />
												</td>
												<!--  <td>${user.uuid}</td> -->
												<td>
												<%-- <a class="btn btn-success btn-xs" href="${ctx}/admin/user/delete/${user.id}">删除</a> --%>
												 <c:if test="${user.allow==1}">
								                  <a class="btn btn-success btn-xs" href="${ctx}/admin/user/forbidden/${user.id}">禁用账户</a>
								                  </c:if>
								                   <c:if test="${user.allow==0}">
								              		   <a class="btn btn-success btn-xs" href="${ctx}/admin/user/forbidden/${user.id}">启用账户</a>
								                  </c:if> 
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
