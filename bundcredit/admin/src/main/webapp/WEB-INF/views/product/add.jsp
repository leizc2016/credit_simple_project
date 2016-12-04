<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!--   <div role="tabpanel" class="query-content"> -->

<!--   
        <ul class="nav nav-tabs" role="tablist">
          <li role="presentation" class="active tab1"><a href="#tab1" aria-controls="tab1" role="tab" data-toggle="tab">个人中心</a></li>
        </ul>
 -->
<!-- Tab panes -->
<!-- <form id="safForm" action="#"> -->
<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">产品创建</h1>
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<div class="row">
		<c:if test="${not empty message}">
			<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
		</c:if>
	</div>
	<!-- /.row -->
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="tab-content">
					<form id="productForm" action="${ctx}/admin/product/saveOrUpdate"
						method="post" class="form-inline">
						<fieldset>
							<div class="control-group">
								<label for="loginName" class="control-label">产品名:</label>
								<div class="controls">
									<input type="text" id="name" name="name"
										class="form-control required" value="${queryProduct.name}"/>
								</div>
							</div>
							<div class="control-group">
								<label for="loginName" class="control-label">产品编号:</label>
								<div class="controls">
									<input type="text" id="productCode" name="productCode"
										class="form-control required" value="${queryProduct.productCode}"/>
								</div>
							</div>
							<div class="control-group">
								<label for="name" class="control-label">查询基础费用:</label>
								<div class="controls">
									<input type="text" id="queryPrice" name="queryPrice" 
										class="form-control required number"  value="${queryProduct.queryPrice}"/>
								</div>
							</div>
							<div class="control-group">
							<label class="control-label">支付方式:</label>
							<div class="controls">
								<select  id="roles" style="width: 175px;" name="cashFlag"
									class="form-control required">
									<option value="">请选择</option>
									<option value="0" <c:if test="${queryProduct.cashFlag==0}">selected</c:if>>外滩币支付</option>
									<option value="1" <c:if test="${queryProduct.cashFlag==1}">selected</c:if>>现金支付</option>
								</select>
							</div>
						</div>
							<div class="control-group">
								<label for="plainPassword" class="control-label">有增量查询结果费用:</label>
								<div class="controls">
									<input type="text" id="queryReturnPrice" name="queryReturnPrice"
										class="form-control required number" value="${queryProduct.queryReturnPrice}"/>
								</div>
							</div>
							<div class="control-group">
								<label for="confirmPassword" class="control-label">无增量查询费用:</label>
								<div class="controls">
									<input type="text" id="queryNoreturnPrice"
										name="queryNoreturnPrice" class="form-control required number" value="${queryProduct.queryNoreturnPrice}"/>
								</div>
							</div>
							<input type="hidden" name="id" value="${queryProduct.id}"/>					
							<div class="form-actions">
								<input id="submit_btn" class="btn btn-primary" type="submit"
									value="提交" />&nbsp; <input id="cancel_btn" class="btn"
									type="button" value="返回" onclick="history.back()" />
							</div>
						</fieldset>
					</form>
					<!-- /.panel-body -->
				</div>
				<!-- /.panel -->
			</div>
			<!-- /.col-lg-12 -->
		</div>
		<!-- /.row -->
	</div>
</div>

<script>
	$(document).ready(function() {
		$("#productForm").validate();
	});
</script>
