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
                            	充值
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-6">
                                    <form id='safForm' role="form" method="post" action="${ctx}/insadmin/${institution.insCode}/institution/balance" onsubmit="return validateInput()">
                                        <div class="form-group">
                                            <label>机构代码</label>
                                            <p class="form-control-static">${institution.insCode}</p>
                                        </div>  
                                        <div class="form-group">
                                            <label>机构名称</label>
                                            <p class="form-control-static">${institution.name}</p>
                                        </div>                                        
                                        <div class="form-group">
                                            <label>余额-海纳征信币账户</label>
                                            <p class="form-control-static">${institution.balance}</p>
                                        </div> 
                                        <div class="form-group">
                                            <label>余额-现金账户</label>
                                            <p class="form-control-static">${institution.cashBalance}</p>
                                        </div>  
                                        <div class="form-group">
                                            <label>信用额度-海纳征信币账户</label>
                                            <p class="form-control-static">${institution.lineOfCredit}</p>
                                        </div>
                                        <div class="form-group">
                                            <label>信用额度-现金账户</label>
                                            <p class="form-control-static">${institution.cashCredit}</p>
                                        </div>                                                                                                                                                            
                                        <div class="form-group">
                                            <label>海纳征信币充值金额</label>
                                            <input name="balance" id="balance" class="form-control" placeholder="请输入充值金额,如 1000" maxlength="12" value=""/>
                                        </div>
                                        <div class="form-group">
                                            <label>现金充值金额</label>
                                            <input name="cashBalance" id="cashBalance" class="form-control" placeholder="请输入充值金额,如 1000" maxlength="12" value=""/>
                                        </div>
                                        <button type="submit" class="btn btn-default">充值</button>
                                          <input id="cancel_btn" class="btn" type="button" value="返回" onclick="back()"/>
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
     function back(){
    	 window.location.href='${ctx}/insadmin/raninstitutionst';
     }
     
     function validateInput(){
 		var balance = $("#balance").val();
 		var cashBalance = $("#cashBalance").val();
 		if(!balance){
 			$("#balance").val(0)
 			balance = 0;
 		}
 		if(!cashBalance){
 			$("#cashBalance").val(0)
 			cashBalance = 0;
 		}
 		//alert(psw);
 		var regex = new RegExp('^[0-9]+(.[0-9]{2})?$');
 		if(!regex.test(balance)){
 			alert('保存失败，海纳币输入有误');
 			return false;
 		}
 		if(!regex.test(cashBalance)){
 			alert('保存失败，现金账户输入有误');
 			return false;
 		}
 		//console.log(regex.test('a123456-'));
 		return true;
 	}
     
     </script>