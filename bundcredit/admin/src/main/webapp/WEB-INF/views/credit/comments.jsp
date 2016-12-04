<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div id="page-wrapper" style="min-height: 262px;">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">个人备注</h1>
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
                            	备注列表
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                                <div class="row"><div class="col-sm-12">
                                <table id="dataTables-example" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="上传日志" style="table-layout:fixed;word-wrap:break-word;">
                                    <thead>
                                        <tr role="row">
                                        <th class="sorting_asc" tabindex="0" aria-controls="dataTables-example" rowspan="1" colspan="1" style="width: 177px;" aria-sort="ascending" aria-label="Rendering engine: activate to sort column descending">证件号</th>
                                        <th class="sorting" tabindex="0" aria-controls="dataTables-example" rowspan="1" colspan="1" style="width: 199px;" aria-label="Browser: activate to sort column ascending">备注类型</th>
                                        <th class="sorting" tabindex="0" aria-controls="dataTables-example" rowspan="1" colspan="1" style="width: 181px;" aria-label="Platform(s): activate to sort column ascending">备注内容</th>
                                        <th class="sorting" tabindex="0" aria-controls="dataTables-example" rowspan="1" colspan="1" style="width: 153px;" aria-label="Engine version: activate to sort column ascending">最近更新时间</th>
                                        
                                        </tr>
                                    </thead>
                                    <tbody>
										<c:forEach items="${comments}" var="c">
											<tr class="gradeA odd" role="row">
												<td class="sorting_1 center">${c.idCardNum}</td>
												<td class="center">${c.commentType}</td>
												<td class="center">${c.content}</td>
												<td class="center">${c.lastUpdateDatetime}</td>
											</tr>
										</c:forEach>                                             
                                    </tbody>
                                </table>
                                </div>
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