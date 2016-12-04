<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">添加备注</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            	添加备注内容
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-6">
                                    <form role="form" method="post" action="${ctx}/creditadmin/comment">
                                        <div class="form-group">
                                            <label>证件号</label>
                                            <input type="hidden" name="idCardNum" class="form-control" value="${comment.idCardNum}"/>
                                        </div>  
                                        <div class="form-group">
                                            <label>备注类型</label>
                                            <select name="commentType" class="form-control" value="${comment.commentType}">
                                            	<option value="1">贷款黑名单</option>
                                            	<option value="2">从业人员黑名单</option>
                                            	<option value="3">法院黑名单</option>
                                            	<option value="4">个人申诉</option>
                                            	<option value="5">其他</option>
                                            </select>
                                        </div>                                         
                                        <div class="form-group">
                                            <label>备注内容</label>
                                            <input name="content" class="form-control" placeholder="请输入备注内容" value="${comment.content}" maxlength="200"/>
                                        </div>                                                                                                                                                               
                                        
                                        <button type="submit" class="btn btn-default">提交修改</button>
                                        <a href="${ctx}/creditadmin/persons" class="btn btn-default">返回</a>
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