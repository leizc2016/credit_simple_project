<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">数据估值</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            	机构数据估值
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-6">
                                    <form id="inputForm" role="form" method="post" action="${ctx}/insadmin/${file.id}/file/evaluation">
                                        <div class="form-group">
                                            <label>文件编号</label>
                                            <p class="form-control-static">${file.id}</p>
                                        </div>  
                                        <div class="form-group">
                                            <label>上传时间</label>
                                            <p class="form-control-static">${file.uploadDatetime}</p>
                                        </div> 
                                        <div class="form-group">
                                            <label>所属机构</label>
                                            <p class="form-control-static">${file.insCode}</p>
                                        </div>                                          
                                        <div class="form-group">
                                            <label>上传人员编号</label>
                                            <p class="form-control-static">${file.insAccId}</p>
                                        </div>  
                                        <div class="form-group">
                                            <label>上传是文件名</label>
                                            <p class="form-control-static">${file.fileName}</p>
                                        </div>  
                                        <div class="form-group">
                                            <label>文件唯一编码</label>
                                            <p class="form-control-static">${file.fileUqKey}</p>
                                        </div>
                                        <div class="form-group">
                                            <label>文件类型</label>
                                            <p class="form-control-static">${file.fileTypeStr}</p>
                                        </div> 
                                        <div class="form-group">
                                            <label>文件状态</label>
                                            <p class="form-control-static">${file.fileStatusStr}</p>
                                        </div>                                                                                                                                                                                                                                                                                                                                                               
                                        <div class="form-group">
                                            <label for="value" class="control-label">文件估值</label>
                                            <input id="value" name="value" type="number" class="form-control required" range="0,100000" placeholder="请输入文件估值" value="${file.valAmt}"/>
                                        </div>
                                        <div class="form-group">
                                            <label for="comments" class="control-label">备注</label>
                                            <input id="comments" name="comments" minlength="6" maxlength="22" class="form-control required" placeholder="请输备注信息" value=""/>
                                        </div>
                                        <p class="alert alert-danger">估值并导入生产库会把数据导入生产库，请谨慎操作</p>                           
                                        <button type="submit" class="btn btn-default">估值并导入生产库</button>
                                        <button type="reset" class="btn btn-default" onclick="history.back()">返回</button>
                                    </form>
                                </div>
                            </div>
                            <!-- /.row (nested) -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
        </div>
        <!-- /#page-wrapper -->
    <script>
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#value").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate();
		});
	</script>        