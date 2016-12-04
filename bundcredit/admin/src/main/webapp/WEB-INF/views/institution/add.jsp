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
			<h1 class="page-header">机构创建</h1>
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="tab-content">
					
					<form id="safForm" action="${ctx}/insadmin/insave"
						method="post" class="form-inline">
						<fieldset>
							<div class="control-group">
								<label for="insCode" class="control-label">机构代码:</label>
								<div class="controls">
									<input type="text" id="insCode" name="insCode"
										class="form-control required"  placeholder="机构代码" />
								</div>
							</div>
							<div class="control-group">
								<label for="name" class="control-label">机构名称:</label>
								<div class="controls">
									<input type="text" id="name" name="name" 
										class="form-control required"   placeholder="机构名称"/>
								</div>
							</div>
							<div class="control-group">
								<label for="lineOfCredit" class="control-label">外滩账户信用额度:</label>
								<div class="controls">
									<input type="text" id="lineOfCredit" name="lineOfCredit"
										class="form-control range digits required" placeholder="外滩账户信用额度" />
								</div>
							</div>
						    <div class="control-group">
								<label for="balance" class="control-label">外滩币账户余额:</label>
								<div class="controls">
									<input type="text" id="balance" name="balance"
										class="form-control number required" maxlength="12"placeholder="外滩币账户余额" />
								</div>
							</div>
							
							<div class="control-group">
								<label for="lineOfCredit" class="control-label">现金账户信用额度:</label>
								<div class="controls">
									<input type="text" id="cashCredit" name="cashCredit"
										class="form-control range digits required" placeholder="现金账户信用额度"/>
								</div>
							</div>
						    <div class="control-group">
								<label for="balance" class="control-label">现金账户余额:</label>
								<div class="controls">
									<input type="text" id="cashBalance" name="cashBalance"
										class="form-control number required" maxlength="12"placeholder="现金账户余额" />
								</div>
							</div>
							<div class="control-group">
								<label for="balance" class="control-label">机构查询权限:</label>
								<div class="controls">
									<table>
										<c:forEach items="${queryProducts}" var="queryProduct">
										  <tr>
										  	<td>
										  		<label><input name="productCode" type="checkbox" value="${queryProduct.productCode}"  onclick="checkBoxOnClick();" 
										  		        id="ckbox-${queryProduct.productCode}"/>${queryProduct.name}</label>
										  	</td>
										  	<td>
										  		<span class="control-group" style="text-align:left;padding-left:10px;">
													<label for="balance" class="control-label">机构折扣:</label>
												</span>
										  	</td>
										  	<td>
										  		<input type="text" id="queryDiscounts-${queryProduct.productCode}" name="queryDiscounts" disabled="disabled"
													   class="form-control chargeRange number required" maxlength="12" placeholder="${queryProduct.name}机构折扣"/>
										  	</td>
										  	<td>
									  			<c:if test="${queryProduct.productCode eq 'YYSCX_CS' || queryProduct.productCode eq 'YYSCX_GEO'}">
													<span style="text-align:left;padding-left:10px;display:none;" id="isDefault-${queryProduct.productCode}">
														<input type="radio" name="isDefault" value="${queryProduct.productCode}" id="radio-${queryProduct.productCode}">默认供应商
													</span>
												</c:if>
										  	</td>
										  </tr>
										</c:forEach>
									</table>
								</div>
							</div>
							<div class="form-actions">
								<input id="submit_btn" class="btn btn-primary" type="button" value="提交" onclick="checkRadio()"/>&nbsp; 
								<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()" />
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
		$("#safForm").validate();
		
		$.validator.addMethod("range", function (value, element) {
	        return this.optional(element) || value>=0 && value<=100000;
	    }, "<font color='#E47068'>请输入一个介于 0 和100000之间的值</font>");
		
		
		$.validator.addMethod("chargeRange", function (value, element) {
	        return this.optional(element) || value>=0 && value<=1;
	    }, "<font color='#E47068'>请输入一个介于 0 和1之间的值</font>");
		
		$("#isDefault-YYSCX_CS").hide();
		$("#isDefault-YYSCX_GEO").hide();
		$("#radio-YYSCX_CS").attr("checked",false);
		$("#radio-YYSCX_GEO").attr("checked",false);
		$("input[name='queryDiscounts']").each(function(index,elem) {
			$(elem).val('');
			$(elem).attr("disabled",true);
		});
		
		$("input:checkbox[name='productCode']").each(function(index,elem) {
			//alert($(elem).val());
			$(elem).attr("checked", false);
		});
	});
	
	function checkRadio(){
		if($("#ckbox-YYSCX_CS").is(':checked') && $("#ckbox-YYSCX_GEO").is(':checked') &&
				   !$("#radio-YYSCX_CS").is(':checked') && !$("#radio-YYSCX_GEO").is(':checked')){
        		alert("请选择默认供应商!");
   				return false;
		}else{
			$("#safForm").submit();
		}
	}
	
	function checkBoxOnClick(){	
		var chk_value =''; 
		$("input:checkbox[name='productCode']").each(function(index,elem) {
			//alert($(elem).val());
			if($(elem).is(":checked")){
				chk_value = chk_value + $(elem).val() + ";";
				$("#queryDiscounts-" + $(elem).val()).attr("disabled",false);
				if($("#queryDiscounts-" + $(elem).val()).val() == ''){
				   $("#queryDiscounts-" + $(elem).val()).val("1");
				}
			}else{
				$("#queryDiscounts-" + $(elem).val()).attr("disabled",true);
				$("#queryDiscounts-" + $(elem).val()).val("");
			}
		})
		
		if(chk_value.indexOf("YYSCX_CS") > -1 && chk_value.indexOf("YYSCX_GEO") >-1){
			$("#isDefault-YYSCX_CS").show();
			$("#isDefault-YYSCX_GEO").show();
		}else{
			$("#isDefault-YYSCX_CS").hide();
			$("#isDefault-YYSCX_GEO").hide();
		}
	}
	
	function isRadioChecked(){
		if($("#ckbox-YYSCX_CS").is(':checked') && $("#ckbox-YYSCX_GEO").is(':checked')){
			$("#isDefault-YYSCX_CS").show();
 			$("#isDefault-YYSCX_GEO").show();
		}
		
	}
</script>
