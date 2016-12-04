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
                            	调整信用额度
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-6">
                                    <form role="form" method="post" action="${ctx}/insadmin/${institution.insCode}/institution/credit" onsubmit="return validateInput()" id="creditupdateform">
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
                                            <label>信用额度-海纳征信币账户</label>
                                            <input id="lineOfCredit" name="lineOfCredit" class="form-control range digits required" placeholder="请输入信用额度" maxlength="12" value="${institution.lineOfCredit}"/>
                                        </div>
                                        <div class="form-group">
                                            <label>信用额度-现金账户</label>
                                            <input id="cashCredit" name="cashCredit" class="form-control range digits required" placeholder="请输入信用额度" maxlength="12" value="${institution.cashCredit}"/>
                                        </div>
                                        <button type="submit" class="btn btn-default">提交修改</button>
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
    		$("#creditupdateform").validate();
    		
    		$.validator.addMethod("range", function (value, element) {
    	        return this.optional(element) || value>=0 && value<=100000;
    	    }, "<font color='#E47068'>请输入一个介于 0 和100000之间的值</font>");
    	});
        
     function back(){
    	 window.location.href='${ctx}/insadmin/raninstitutionst';
     }
     
     function validateInput(){
  		var lineOfCredit = $("#lineOfCredit").val();
  		var cashCredit = $("#cashCredit").val();
  		if(!lineOfCredit){
  			$("#lineOfCredit").val(0)
  			lineOfCredit = 0;
  		}
  		if(!cashCredit){
  			$("#cashCredit").val(0)
  			cashCredit = 0;
  		}
  		//alert(psw);
  		var regex = new RegExp('^[0-9]*$');
  		if(!regex.test(lineOfCredit)){
  			alert('保存失败，信用额度-海纳征信币账户');
  			return false;
  		}
  		if(!regex.test(cashCredit)){
  			alert('保存失败，信用额度-现金账户输入有误');
  			return false;
  		}
  		//console.log(regex.test('a123456-'));
  		return true;
  	}
     </script>