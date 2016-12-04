<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div id="page-wrapper" style="min-height: 262px;">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">机构数据详情</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            	文件详情
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                                <div class="row"><div class="col-sm-12">
                                <table id="dataTables-example" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="上传日志">
                                    <thead>
                                        <tr role="row">
                                        <th class="sorting_asc" tabindex="0" aria-controls="dataTables-example" rowspan="1" colspan="1" style="width: 100px;" aria-sort="ascending" aria-label="Rendering engine: activate to sort column descending">ID</th>
                                        <th class="sorting" tabindex="0" aria-controls="dataTables-example" rowspan="1" colspan="1" style="width: 100px;" aria-label="Browser: activate to sort column ascending">证件类型</th>
                                        <th class="sorting" tabindex="0" aria-controls="dataTables-example" rowspan="1" colspan="1" style="width: 100px;" aria-label="Platform(s): activate to sort column ascending">证件号码</th>
                                        <th class="sorting" tabindex="0" aria-controls="dataTables-example" rowspan="1" colspan="1" style="width: 100px;" aria-label="Engine version: activate to sort column ascending">名字</th>
                                        <th class="sorting" tabindex="0" aria-controls="dataTables-example" rowspan="1" colspan="1" style="width: 100px;" aria-label="CSS grade: activate to sort column ascending">上传机构代码</th>
                                        <th class="sorting" tabindex="0" aria-controls="dataTables-example" rowspan="1" colspan="1" style="width: 100px;" aria-label="Platform(s): activate to sort column ascending">上传文件编号</th>
                                        </tr>
                                    </thead>
                                    <tbody>
										<c:forEach items="${details}" var="f">
											<tr class="gradeA odd" role="row">
												<td class="sorting_1 center">${f.id}</td>
												<td class="center">${f.idCardType}</td>
												<!-- <td class="center">${f.idCardNum}</td> -->
												<td class="center">${f.idCardNumString}</td>
												<td class="center">${f.fullName}</td>
												<td class="center">${f.insCode}</td>
												<td class="center">${f.uploadFileId}</td>
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