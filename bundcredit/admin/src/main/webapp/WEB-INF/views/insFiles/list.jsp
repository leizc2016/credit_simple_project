<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div id="page-wrapper" style="min-height: 262px;">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">机构上传</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            	上传日志
                        </div>
                        <!-- /.panel-heading -->
                        <%-- <div class="panel-body">
                         <div class="input-append">
                          <form id="searchFormId" action="${ctx}/insadmin/files" class="form-inline offset-t20 offset-b20" >
									<label>上传时间：</label>
									    <div class="form-group">
							<input class="form-control input-sm w200" name="q_uploadDatetime"
								value="${uploadDatetime}">
						</div><label>机构代码：</label>
									       <div class="form-group">
							<input class="form-control input-sm w200" name="q_insCode"
								value="${insCode}">
						</div>
						<label>上传账户：</label>
						   <div class="form-group">
							<input class="form-control input-sm w200" name="q_uploadman"
								value="${uploadman}">
						</div>
						<label>文件类型：</label>
						   <div class="form-group">
							<input class="form-control input-sm w200" name="q_fileType"
								value="${fileType}">
						</div>
						<label>报告日期：</label>
						   <div class="form-group">
							<input class="form-control input-sm w200" name="reportDatetime"
								value="${reportDatetime}">
						</div>
							<button type="submit" class="btn btn-default">Search</button>
							   </form>
						</div> --%>
                            <div class="dataTable_wrapper">
                                <div class="row"><div class="col-sm-12">
                                <table id="dataTables-example" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="上传日志">
                                    <thead>
                                        <tr role="row">
                                        <th class="sorting_asc" tabindex="0" aria-controls="dataTables-example" rowspan="1" colspan="1" style="width: 100px;" aria-sort="ascending" aria-label="Rendering engine: activate to sort column descending">ID</th>
                                        <th class="sorting" tabindex="0" aria-controls="dataTables-example" rowspan="1" colspan="1" style="width: 100px;" aria-label="Browser: activate to sort column ascending">上传时间</th>
                                        <th class="sorting" tabindex="0" aria-controls="dataTables-example" rowspan="1" colspan="1" style="width: 100px;" aria-label="Platform(s): activate to sort column ascending">机构代码</th>
                                        <th class="sorting" tabindex="0" aria-controls="dataTables-example" rowspan="1" colspan="1" style="width: 100px;" aria-label="Engine version: activate to sort column ascending">用户名</th>
                                        <th class="sorting" tabindex="0" aria-controls="dataTables-example" rowspan="1" colspan="1" style="width: 100px;" aria-label="CSS grade: activate to sort column ascending">文件名</th>
                                        <th class="sorting" tabindex="0" aria-controls="dataTables-example" rowspan="1" colspan="1" style="width: 100px;" aria-label="Platform(s): activate to sort column ascending">文件类型</th>
                                        <th class="sorting" tabindex="0" aria-controls="dataTables-example" rowspan="1" colspan="1" style="width: 100px;" aria-label="Engine version: activate to sort column ascending">文件估值</th>
                                        <th class="sorting" tabindex="0" aria-controls="dataTables-example" rowspan="1" colspan="1" style="width: 100px;" aria-label="CSS grade: activate to sort column ascending">文件状态</th>
                                        <th class="sorting" tabindex="0" aria-controls="dataTables-example" rowspan="1" colspan="1" style="width: 100px;" aria-label="CSS grade: activate to sort column ascending">管理</th>
                                        </tr>
                                    </thead>
                                    <tbody>
										<c:forEach items="${insFiles}" var="f">
											<tr class="gradeA odd" role="row">
												<td class="sorting_1 center">${f.id}</td>
												<td class="center"><fmt:formatDate value="${f.uploadDatetime}" pattern="yyyy-MM-dd  HH:mm:ss" /></td><fmt:formatDate value="${accQueryHistory.queryDatetime}" pattern="yyyy-MM-dd" />
												<td class="center">${f.insCode}</td>
												<td class="center">${f.uploadman}</td>
												<td class="center">${f.fileName}</td>
												<td class="center">${f.fileTypeStr}</td>
												<td class="center">${f.valAmt}</td>
												<td class="center">${f.fileStatusStr}</td>
												<td class="center">
												
													<a  href="${ctx}/insadmin/${f.id}/file/detail">详情</a>
													<c:choose>
													<c:when test="${f.fileStatus == 205}">
													<a href="${ctx}/insadmin/${f.id}/file/evaluation">估值</a>
													</c:when>
													</c:choose>
													<c:choose>
													    <c:when test="${f.fileStatus == 202}">
														<a  href="${ctx}/insadmin/${f.id}/203/fileStatusUpdate">已处理</a>
														</c:when>
													    <c:when test="${f.fileStatus == 205}">	
														<a  href="${ctx}/insadmin/${f.id}/301/fileStatusUpdate">不通过</a>
														</c:when>
														<c:when test="${f.fileStatus == 301}">
														<a  href="${ctx}/insadmin/${f.id}/302/fileStatusUpdate">已处理</a>
														</c:when>
													</c:choose>
													<c:choose>
														<c:when test="${not empty f.validationLog}">
														<button type="button" class="btn btn-primary btn-xs" title="日志"  
													      data-container="body" data-html="true" data-toggle="popover" data-placement="left" 
													      data-content="${f.validationLog}">日志 </button>
														</c:when>
													</c:choose>
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
 <script>$(function () 
      { $("[data-toggle='popover']").popover();
      });
   </script>