<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">修改个人信息</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            	个人信息
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-6">
                                    <form id="inputForm" role="form" method="post" action="${ctx}/profile" onsubmit="return validatePSW()">
                                    	<input type="hidden" id="id" name="id" value="${user.id}"/>
                                        <div class="form-group">
                                            <label for="name" class="control-label">姓名:</label>
                                            <input id="name" name="name" class="form-control required" placeholder="请输入姓名" value="${user.name}"/>
                                        </div>
										<div class="form-group">
											<label for="name" class="control-label">旧密码:</label>
											<input type="password" id="oldPassword" name="oldPassword" class="form-control required checkUserExist" placeholder="旧密码">
										</div>  
                                        <div class="form-group">
                                            <label for="plainPassword" class="control-label">新密码:</label>
                                            <input type="password" id="plainPassword" name="plainPassword" class="form-control" placeholder="留空如果不改密码"/>
                                        </div>   
                                        <div class="form-group">
                                            <label for="confirmPassword" class="control-label">确认新密码:</label>
                                            <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" placeholder="请输入确认密码" equalTo="#plainPassword"/>
                                        </div>                                                                                                                                                              
                                        <button type="submit" class="btn btn-default">修改</button>
                                        <input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
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
        	<script>
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#name").focus();
			$.validator.addMethod("checkUserExist",function(value,element){
		        var oldPassword = value;
		        var id=$("#id").val();
		        $.ajax({
		            type:"POST",
		            async:false, 
		            url:"${ctx}/admin/user/password_validate",
		            data:"id="+id+"&oldPassword="+oldPassword,
		            success:function(response){
		                if(response=='0'){
		                    res = false;
		                }else{
		                    res = true;
		                }
		            }
		        }); 
		        return res;
		    },"<font color='#E47068'>密码不正确</font>");
			//为inputForm注册validate函数
			$("#inputForm").validate();
		});
		
		function validatePSW(){
			var psw = $("#plainPassword").val();
			var psw2 = $("#confirmPassword").val();
			if (psw != "" && psw2 != "") {
				//alert(psw);
				var regex = new RegExp('(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{8,30}');
				if(!regex.test(psw)){
					alert('密码中必须包含大小写字母、数字、特称字符，至少8个字符，最多30个字符。');
					return false;
				}
			}
			//console.log(regex.test('a123456-'));
			return true;
		}
	</script>
        <!-- /#page-wrapper -->