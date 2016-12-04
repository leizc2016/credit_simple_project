<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">机构</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            	机构信息维护
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-6">
                                    <form id="updatesafForm" role="form" method="post" action="${ctx}/insadmin/${institution.insCode}/institution">
                                        <div class="form-group">
                                            <label>机构代码</label>
                                            <p class="form-control-static"  id='inscode'>${institution.insCode}</p>
                                        </div>  
                                         <div class="form-group">
                                            <label>机构名称</label>
                                            <input name="name" class="form-control" placeholder="请输入机构名称" value="${institution.name}"/>
                                        </div>
                                        <div class="form-group">
                                            <label>海纳币余额</label>
                                            <input name="balance" class="form-control" placeholder="请输入海纳币余额" value="${institution.balance}"/>
                                        </div>   
                                        <div class="form-group">
                                            <label>海纳币信用额度</label>
                                            <input name="lineOfCredit" class="form-control range digits required" placeholder="请输入海纳币信用额度" value="${institution.lineOfCredit}"/>
                                        </div>                                                                                                                                                              
                                        <div class="form-group">
                                            <label>现金账户余额</label>
                                            <input name="cashBalance" class="form-control" placeholder="请输入现金账户余额" value="${institution.cashBalance}"/>
                                        </div>   
                                        <div class="form-group">
                                            <label>现金账户信用额度</label>
                                            <input name="cashCredit" class="form-control range digits required" placeholder="请输入现金账户信用额度" value="${institution.cashCredit}"/>
                                        </div> 
                                        
                                        <div class="form-group">
											<label for="balance" class="control-label">机构查询权限:</label>
											<div class="controls">
												<table>
													<c:forEach items="${queryProducts}" var="queryProduct">
													  <tr>
													  	<td>
													  		<label><input name="productCode" id="cbox-${queryProduct.productCode}" type="checkbox" value="${queryProduct.productCode}"  onclick="checkBoxOnClick();"/>${queryProduct.name}</label>
													  	</td>
													  	<td>
													  		<span class="control-group" style="text-align:left;padding-left:10px;">
																<label for="balance" class="control-label">机构折扣:</label>
															</span>
													  	</td>
													  	<td>
													  		<input type="text" id="queryDiscount-${queryProduct.productCode}" name="queryDiscount" disabled="disabled"
																   class="form-control chargeRange number required" maxlength="12" placeholder="${queryProduct.name}机构折扣" onblur='fillData("${queryProduct.productCode}")'/>
															<input type="hidden" name='prodCode' id="prodCode-${queryProduct.productCode}" value='${queryProduct.productCode}'/>
														    <input type="hidden" name='insCodeArray'  id='insCode-${queryProduct.productCode}' value="${institution.insCode}"/>
														    <input type="hidden" name='insProdPriceId' value='' id='insProdPriceId-${queryProduct.productCode}'/>
														    <input type="hidden" name='queryDiscounts' value='' id='queryDiscounts-${queryProduct.productCode}'/>
													  	</td>
													  	<td>
												  			<c:if test="${queryProduct.productCode eq 'YYSCX_CS' || queryProduct.productCode eq 'YYSCX_GEO'}">
																<span style="text-align:left;padding-left:10px;display:none;" id="isDefault-${queryProduct.productCode}">
																	<input type="radio" name="isDefault" value="${queryProduct.productCode}" id="updateradio-${queryProduct.productCode}">默认供应商
																</span>
															</c:if>
													  	</td>
													  </tr>
													</c:forEach>
												</table>
											</div>
										</div>
										<div>
											<input type="hidden" name='insCode'  value="${institution.insCode}"/>
										</div>
                                        <button type="button" class="btn btn-default" onclick="updatecheckRadio()">提交修改</button>
                                        <input id="cancel_btn" class="btn btn-default" type="button" value="返回" onclick="back()"/>
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
        <script type="text/javascript">
	        $(document).ready(function() {
	    		$("#updatesafForm").validate();
	    		
	    		$.validator.addMethod("range", function (value, element) {
	    	        return this.optional(element) || value>=0 && value<=100000;
	    	    }, "<font color='#E47068'>请输入一个介于 0 和100000之间的值</font>");
	    		
	    		$.validator.addMethod("chargeRange", function (value, element) {
	    	        return this.optional(element) || value>=0 && value<=1;
	    	    }, "<font color='#E47068'>请输入一个介于 0 和1之间的值</font>");
	    		
	    		$("#isDefault-YYSCX_CS").hide();
	    		$("#isDefault-YYSCX_GEO").hide();
	    		$("#updateradio-YYSCX_CS").attr("checked",false);
	    		$("#updateradio-YYSCX_GEO").attr("checked",false);
	    		$("input[name='queryDiscount']").each(function(index,elem) {
	    			$(elem).val('');
	    			$(elem).attr("disabled",true);
	    		});
	    		
	    		$("input:checkbox[name='productCode']").each(function(index,elem) {
	    			//alert($(elem).val());
	    			$(elem).attr("checked", false);
	    		});
	    		
	    		var radiopcode= '';
	    		$.ajax({
	    				 type:"GET",
	    				 url:"${ctx}/insadmin/institution/getProductPriceByIns",
	    				 data:{insCode:$("#inscode").html()},
	    				 dataType:"json",
	    				 async: false,
	    				 success:function(res){
	    					 $(res).each(function(index,obj) {
	    						 var pcode = obj.prodCode;
	    						 var qdiscount = obj.queryDiscount;
	    						 var inscode = obj.insCode;
	    						 var insProdPriceId = obj.insProdPriceId;
	    						 var defaultFlag = obj.defaultFlag;
	    						 if(qdiscount !=null && qdiscount !==''){
	    							 $("#cbox-"+pcode).prop("checked",true);
	    							 $("#queryDiscount-"+pcode).attr("disabled",false);
	    							 $("#queryDiscount-"+pcode).val(qdiscount);
	    							 $("#queryDiscounts-"+pcode).val(qdiscount);
	    							 $("#insProdPriceId-"+pcode).val(insProdPriceId);
	    						 }
	    						 if(defaultFlag == 1){
	    							 radiopcode = pcode;
	    						 }
	                         });
	    				 },
	    				 error:function(){
	    					 alert("查找机构折扣信息出错!");
	    				 }
	    		});
	    		
	    		if(radiopcode != ''){
	    			$("#updateradio-" + radiopcode).prop("checked", true);
	    		}
	    		
	    		if($("#cbox-YYSCX_CS").is(':checked') && $("#cbox-YYSCX_GEO").is(':checked')){
	    			$("#isDefault-YYSCX_CS").show();
		 			$("#isDefault-YYSCX_GEO").show();
	    		}
	    	});
	        
	        function updatecheckRadio(){
	        	if($("#cbox-YYSCX_CS").is(':checked') && $("#cbox-YYSCX_GEO").is(':checked') &&
       			   !$("#updateradio-YYSCX_CS").is(':checked') && !$("#updateradio-YYSCX_GEO").is(':checked')){
	        		alert("请选择默认供应商!");
       				return false;
       			}else{
       				$("#updatesafForm").submit();
       			}
	        }
	        
		     function back(){
		    	 window.location.href='${ctx}/insadmin/institutions';
		     }
		     
		     function checkBoxOnClick(){
		    	 var chk_value ='';
		 		$("input:checkbox[name='productCode']").each(function(index,elem) {
		 			//alert($(elem).val());
		 			if($(elem).is(":checked")){
		 				chk_value = chk_value + $(elem).val() + ";";
		 				$("#queryDiscount-" + $(elem).val()).attr("disabled",false);
		 				if($("#queryDiscount-" + $(elem).val()).val() == ''){
		 					$("#queryDiscount-" + $(elem).val()).val("1");
		 					$("#queryDiscounts-" + $(elem).val()).val("1");
		 				}
		 			}else{
		 				$("#queryDiscount-" + $(elem).val()).attr("disabled",true);
		 				$("#queryDiscount-" + $(elem).val()).val("");
		 				$("#queryDiscounts-" + $(elem).val()).val("");
		 			}
		 		})
		 		
		 		if(chk_value.indexOf("YYSCX_CS") > -1 && chk_value.indexOf("YYSCX_GEO") >-1){
		 			$("#isDefault-YYSCX_CS").show();
		 			$("#isDefault-YYSCX_GEO").show();
		 		}else{
		 			$("#updateradio-YYSCX_CS").prop("checked", false);
		 			$("#updateradio-YYSCX_GEO").prop("checked", false);
		 			$("#isDefault-YYSCX_CS").hide();
		 			$("#isDefault-YYSCX_GEO").hide();
		 		}
		 	}
		     
		    function fillData(pcode){
		    	$("#queryDiscounts-" + pcode).val($("#queryDiscount-" + pcode).val());
		    }
	    </script>