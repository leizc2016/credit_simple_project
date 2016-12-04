<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
	
	$(document).ready(function() {
		$("#searchFormId").validate();
		
		$.validator.addMethod("checkDateFormat", function (value, element) {
	        return this.optional(element) || checkDateFormat(value);
	    }, "<font color='#E47068'>请输入正确的日期格式（yyyy-mm-dd)</font>");
		
		$.validator.addMethod("comparetransdate", function (value, element) {
			var sDate = new Date(document.getElementById("transDateTimefrom").value);
			var eDate = new Date(value);
	        return this.optional(element) || sDate<=eDate;
	    }, "<font color='#E47068'>结束日期不能小于开始日期</font>");
	    
	});
	
	//校验日期格式是否合法
	function checkDateFormat(str){     
		var r = str.match(/^(\d{4})(-|\/)(\d{2})\2(\d{2})$/);     
	    if(r==null){
	        return false;     
	    }else{
	        var d= new Date(r[1], r[3]-1, r[4]);     
	        return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]); 
	    }   
	} 
</script>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div id="page-wrapper" style="min-height: 262px;">
         <c:if test="${not empty param.q_insCode && !(param.q_insCode eq '_') && !(institution eq null)}">
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
                            	机构信息
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-6">
                                    <form role="form">
                                        <div class="form-group">
                                            <label>机构代码</label>
                                            <p class="form-control-static">${institution.insCode}</p>
                                        </div>  
                                        <div class="form-group">
                                            <label>机构名称</label>
                                            <p class="form-control-static">${institution.name}</p>
                                        </div>                                        
                                        <div class="form-group">
                                            <label>海纳币余额</label>
                                            <p class="form-control-static">${institution.balance}</p>
                                        </div>   
                                        <div class="form-group">
                                            <label>海纳币信用额度</label>
                                            <p class="form-control-static">${institution.lineOfCredit}</p>
                                        </div>
                                        <div class="form-group">
                                            <label>现金账户余额</label>
                                            <p class="form-control-static">${institution.cashBalance}</p>
                                        </div>   
                                        <div class="form-group">
                                            <label>现金账户信用额度</label>
                                            <p class="form-control-static">${institution.cashCredit}</p>
                                        </div>                                                                                                                                                               
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
   </c:if>         
            
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">交易日志</h1>
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
                            	日志
                        </div>
                         <div class="panel-body">
                       
                         <div class="input-append">
                          <form id="searchFormId" action="${ctx}/insadmin/transactions" class="form-inline offset-t20 offset-b20" >
									<label>交易时间：</label>
									    <div class="form-group">
							<input class="form-control input-sm w200 checkDateFormat" name="q_transDateTimefrom" id="transDateTimefrom"
								value="${transDateTimefrom}"> -
							<input class="form-control input-sm w200 checkDateFormat comparetransdate" name="q_transDateTimeto" id="transDateTimeto"
								value="${transDateTimeto}">
						</div><label>交易机构：</label>
									       <div class="form-group">
							<input class="form-control input-sm w200" name="q_insCode"
								value="${insCode}">
						</div>
						<label>交易发起人：</label>
						   <div class="form-group">
							<input class="form-control input-sm w200" name="q_opName"
								value="${opName}">
						</div>
							<button type="submit" class="btn btn-default">Search</button>
							   </form>
						</div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                                <div class="row"><div class="col-sm-12">
                                <table id="dataTables-example" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="上传日志">
                                    <thead>
                                        <tr role="row">
                                        <th width="10%">交易类型</th>
                                        <th>交易描述</th>
                                        <th width="10%">交易时间</th>
                                        <th >交易费用</th>
                                        <th>交易备注</th>
                                        <th>交易机构</th>
                                        <th width="10%">交易发起人</th> 
                                        <th>余额</th> 
                                        </tr>
                                    </thead>
                                    <tbody>
										<c:forEach items="${transactions}" var="t">
											<tr class="gradeA odd" role="row">
												<td class="center">${t.transType}</td>
												<td class="center">${t.description}</td>
												<td class="center"><fmt:formatDate value="${t.transDateTime}" pattern="yyyy-MM-dd  HH:mm:ss" /></td>
												<td class="center">${t.fee}</td>
												<td class="center">${t.comments}</td>
												<td class="center">${t.insCode}</td>
												<td class="center">${t.opName}</td>
												<td class="center">${t.balance}</td>
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