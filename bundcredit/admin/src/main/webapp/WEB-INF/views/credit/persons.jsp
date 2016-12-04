<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div id="page-wrapper" style="min-height: 262px;">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">征信备注</h1>
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
                            	人员列表
                        </div>	
                       
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                       
                         <div class="input-append">
                          <form id="searchFormId" action="${ctx}/creditadmin/persons" class="form-inline offset-t20 offset-b20" >
									<label>姓名：</label>
									    <div class="form-group">
							<input class="form-control input-sm w200" name="q_fullName"
								value="${fullName}">
						</div><label>地址：</label>
									       <div class="form-group">
							<input class="form-control input-sm w200" name="q_location"
								value="${location}">
						</div>
						<label>证件号：</label>
						   <div class="form-group">
							<input class="form-control input-sm w200" name="q_idCardNum"
								value="${idCardNum}">
						</div>
						<%-- <label>报告日期：</label>
						   <div class="form-group">
							<input class="form-control input-sm w200" name="reportDatetime"
								value="${reportDatetime}">
						</div> --%>
							<button type="submit" class="btn btn-default">Search</button>
							   </form>
						</div>
									
                            <div class="dataTable_wrapper">
                            
                                <div class="row"><div class="col-sm-12">
                                <table id="dataTables-example" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="上传日志">
                                    <thead>
                                        <tr role="row">
                                        <th class="sorting_asc" tabindex="0" aria-controls="dataTables-example" rowspan="1" colspan="1" style="width: 177px;" aria-sort="ascending" aria-label="Rendering engine: activate to sort column descending">证件号</th>
                                        <th class="sorting" tabindex="0" aria-controls="dataTables-example" rowspan="1" colspan="1" style="width: 199px;" aria-label="Browser: activate to sort column ascending">姓名</th>
                                        <th class="sorting" tabindex="0" aria-controls="dataTables-example" rowspan="1" colspan="1" style="width: 181px;" aria-label="Platform(s): activate to sort column ascending">住址</th>
                                        <th class="sorting" tabindex="0" aria-controls="dataTables-example" rowspan="1" colspan="1" style="width: 153px;" aria-label="Engine version: activate to sort column ascending">最近更新时间</th>
                                        <th>管理</th>
                                        </tr>
                                    </thead>
                                    <tbody>
										<c:forEach items="${persons}" var="p">
											<tr class="gradeA odd" role="row">
												<td class="sorting_1 center">${p.idCardNumString}</td>
												<td class="center">${p.fullName}</td>
												<td class="center">${p.location}</td>
												<td class="center"><fmt:formatDate value="${p.lastUpdateDatetime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
												<td>
												<a href="${ctx}/creditadmin/${p.idCardNum}/comments?idCardNumString=${p.idCardNumString}">查看备注</a>
												<a href="${ctx}/creditadmin/comment?idCardNum=${p.idCardNum}">添加备注</a>
												</td>
											</tr>
										</c:forEach>                                             
                                    </tbody>
                                </table>
                                </div>
                                </div>
                                <tags:bcpagination/>
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
<script type="text/javascript" charset="utf-8">
	function doSearch() {
		var form = document.getElementById("searchFormId");
		form.action = "${ctx}/member/orderList";
		form.submit();
	}
</script>