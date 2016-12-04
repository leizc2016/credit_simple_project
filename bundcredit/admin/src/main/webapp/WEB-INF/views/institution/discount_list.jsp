<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div id="page-wrapper" style="min-height: 262px;">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">机构折扣管理</h1>
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
                            	折扣列表
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
						<form id="chargeChangeForm" action="${ctx}/insadmin/discount/update" method="post" class="form-inline">
                            <div class="dataTable_wrapper">
                                <div class="row"><div class="col-sm-12">
		                                <c:forEach items="${insProdPrices}" var="insProdPrice" >
		                                		<c:if test="${not empty insProdPrice.queryDiscount}">
		                                   			<div class="control-group">
															<label for="task_title" class="control-label">${insProdPrice.prodName}:</label>
															<div class="controls">
															<input type="text"   name="queryDiscount"   value="${insProdPrice.queryDiscount}" maxlength="5" class="form-control required number chargeChangeRange" />
														    <input type="hidden" name='prodCode' value='${insProdPrice.prodCode}'  />
														    <input type="hidden" name='insCode' value='${insCode}' />
														    <input type="hidden" name='insProdPriceId' value='${insProdPrice.insProdPriceId}'  />
														</div>
													</div>
												 </c:if>	
										</c:forEach>
                                </div>
                            </div>
                        </div>
                                <div class="form-actions">
									<input id="submit_btn" class="btn btn-primary" type="button" value="提交" onclick="submitBtn();"/>&nbsp;	
									<input id="cancel_btn" class="btn btn-default" type="button" value="返回" onclick="back()"/>
								</div>
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
<html>
<script type="text/javascript">
     function back(){
    	 window.location.href='${ctx}/insadmin/raninstitutionst';
     }
     $(document).ready(function() {
			//为inputForm注册validate函数
		 $("#chargeChangeForm").validate();
    	 $.validator.addMethod("chargeChangeRange", function (value, element) {
 	        return this.optional(element) || value>=0 && value<=1;
 	     }, "<font color='#E47068'>请输入一个介于 0 和1之间的值</font>");
	 });
     
     
	function JqValidate() {
		var checkresult = true;
		$("input[name='queryDiscount']").each(function(index){
			if(this.value<0 || this.value>1){
				checkresult = false;
				this.focus();
				this.blur();
			}
		});
		return checkresult;
	}

	function submitBtn() {
		if (JqValidate()) {
			$('#chargeChangeForm').submit();
		}
	}
</script>
</html>